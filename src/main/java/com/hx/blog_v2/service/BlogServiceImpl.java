package com.hx.blog_v2.service;

import com.hx.blog_v2.context.CacheContext;
import com.hx.blog_v2.context.ConstantsContext;
import com.hx.blog_v2.context.WebContext;
import com.hx.blog_v2.dao.interf.*;
import com.hx.blog_v2.domain.ErrorCode;
import com.hx.blog_v2.domain.POVOTransferUtils;
import com.hx.blog_v2.domain.dto.SessionUser;
import com.hx.blog_v2.domain.form.BeanIdForm;
import com.hx.blog_v2.domain.form.BlogSaveForm;
import com.hx.blog_v2.domain.form.BlogSearchForm;
import com.hx.blog_v2.domain.form.BlogSenseForm;
import com.hx.blog_v2.domain.mapper.AdminBlogVOMapper;
import com.hx.blog_v2.domain.mapper.BlogVOMapper;
import com.hx.blog_v2.domain.mapper.OneIntMapper;
import com.hx.blog_v2.domain.po.*;
import com.hx.blog_v2.domain.vo.AdminBlogVO;
import com.hx.blog_v2.domain.vo.BlogVO;
import com.hx.blog_v2.service.interf.BaseServiceImpl;
import com.hx.blog_v2.service.interf.BlogService;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.DateUtils;
import com.hx.blog_v2.util.ResultUtils;
import com.hx.blog_v2.util.SqlUtils;
import com.hx.common.interf.common.Page;
import com.hx.common.interf.common.Result;
import com.hx.json.JSONArray;
import com.hx.log.collection.CollectionUtils;
import com.hx.log.file.FileUtils;
import com.hx.log.util.Log;
import com.hx.log.util.Tools;
import com.hx.mongo.criteria.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * BlogServiceImpl
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 11:47 AM
 */
@Service
public class BlogServiceImpl extends BaseServiceImpl<BlogPO> implements BlogService {

    @Autowired
    private BlogDao blogDao;
    @Autowired
    private BlogCommentDao commentDao;
    @Autowired
    private BlogExDao blogExDao;
    @Autowired
    private RltBlogTagDao rltBlogTagDao;
    @Autowired
    private BlogSenseDao senseDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private CacheContext cacheContext;
    @Autowired
    private BlogConstants constants;
    @Autowired
    private ConstantsContext constantsContext;

    @Override
    public Result save(BlogSaveForm params) {
        SessionUser user = (SessionUser) WebContext.getAttributeFromSession(BlogConstants.SESSION_USER);
        String contentUrl = generateBlogPath(params);
        BlogPO po = new BlogPO(params.getTitle(), user.getName(), params.getCoverUrl(),
                params.getBlogTypeId(), params.getSummary(), contentUrl);

        if (!Tools.isEmpty(params.getId())) {
            return update0(po, params);
        }
        return add0(po, params);
    }

    @Override
    public Result get(BeanIdForm params) {
        String blogSql = " select b.*, GROUP_CONCAT(rlt.tag_id) as tagIds from blog as b " +
                " inner join rlt_blog_tag as rlt on b.id = rlt.blog_id " +
                " where b.deleted = 0 and b.id = ? group by b.id ";
        Object[] sqlParams = new Object[]{params.getId()};
        // 如果 没有找到记录, 或者 找到多条记录, 都会抛出异常, ex.*, b.* 这个顺序, 避免 po.id 被 exPo.id 覆盖
        List<BlogVO> vos = jdbcTemplate.query(blogSql, sqlParams, new BlogVOMapper());
        if (Tools.isEmpty(vos)) {
            return ResultUtils.failed("给定的博客[" + params.getId() + "]不存在 !");
        }

        BlogVO vo = vos.get(0);
        Result result = encapSense(vo);
        if (!result.isSuccess()) {
            return result;
        }
        result = encapBlogVo(vo);
        if (!result.isSuccess()) {
            return result;
        }

        return ResultUtils.success(vo);
    }

    @Override
    public Result adminGet(BeanIdForm params) {
        String sql = " select b.*, GROUP_CONCAT(rlt.tag_id) as tagIds from blog as b " +
                " inner join rlt_blog_tag as rlt on b.id = rlt.blog_id " +
                " where b.deleted = 0 and b.id = ? group by b.id ";
        Object[] sqlParams = new Object[]{params.getId()};
        // 如果 没有找到记录, 或者 找到多条记录, 都会抛出异常
        List<AdminBlogVO> vos = jdbcTemplate.query(sql, sqlParams, new AdminBlogVOMapper());
        if (Tools.isEmpty(vos)) {
            return ResultUtils.failed("给定的博客[" + params.getId() + "]不存在 !");
        }

        AdminBlogVO vo = vos.get(0);
        encapTypeTagInfo(vo);
        encapContent(vo);
        return ResultUtils.success(vo);
    }

    @Override
    public Result list(BlogSearchForm params, Page<BlogVO> page) {
        String selectSql = " select b.*, GROUP_CONCAT(rlt.tag_id) as tagIds from blog as b " +
                " inner join rlt_blog_tag as rlt on b.id = rlt.blog_id " +
                " where b.deleted = 0 and b.id >= 0 ";
        String selectSqlSuffix = " group by b.id order by b.created_at desc limit ?, ?";
        String countSql = " select count(*) as totalRecord from blog as b where b.deleted = 0 and b.id >= 0 ";

        StringBuilder condSqlSb = new StringBuilder();
        List<Object> selectParams = new ArrayList<>(3);
        encapQueryForAdminList(params, condSqlSb, selectParams);
        String condSql = condSqlSb.toString();
        Object[] countParams = selectParams.toArray();
        selectParams.add(page.recordOffset());
        selectParams.add(page.getPageSize());

        Integer totalRecord = jdbcTemplate.queryForObject(countSql + condSql, countParams, new OneIntMapper("totalRecord"));
        if (totalRecord <= 0) {
            page.setList(Collections.<BlogVO>emptyList());
        } else {
            List<BlogVO> list = jdbcTemplate.query(selectSql + condSql + selectSqlSuffix, selectParams.toArray(), new BlogVOMapper());
            encapBlogVo(list);
            page.setList(list);
        }
        page.setTotalRecord(totalRecord);
        return ResultUtils.success(page);
    }

    @Override
    public Result adminList(BlogSearchForm params, Page<AdminBlogVO> page) {
        String selectSql = " select b.*, GROUP_CONCAT(rlt.tag_id) as tagIds from blog as b " +
                " inner join rlt_blog_tag as rlt on b.id = rlt.blog_id where b.deleted = 0 ";
        String selectSqlSuffix = " group by b.id order by b.created_at desc limit ?, ? ";
        String countSql = " select count(*) as totalRecord from blog as b where b.deleted = 0 ";
        List<Object> selectParamsList = new ArrayList<>(3);
        StringBuilder condSqlSb = new StringBuilder();

        encapQueryForAdminList(params, condSqlSb, selectParamsList);
        String condSql = condSqlSb.toString();
        Object[] countParams = selectParamsList.toArray();
        selectParamsList.add(page.recordOffset());
        selectParamsList.add(page.getPageSize());

        Integer totalRecord = jdbcTemplate.queryForObject(countSql + condSql, countParams, new OneIntMapper("totalRecord"));
        if (totalRecord <= 0) {
            page.setList(Collections.<AdminBlogVO>emptyList());
        } else {
            List<AdminBlogVO> list = jdbcTemplate.query(selectSql + condSql + selectSqlSuffix, selectParamsList.toArray(), new AdminBlogVOMapper());
            encapTypeTagInfo(list);
            page.setList(list);
        }

        page.setTotalRecord(totalRecord);
        return ResultUtils.success(page);
    }

    @Override
    public Result remove(BeanIdForm params) {
        String updatedAt = DateUtils.formate(new Date(), BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS);
        try {
            long deleted = blogDao.updateOne(Criteria.eq("id", params.getId()),
                    Criteria.set("deleted", "1").add("updated_at", updatedAt)
            ).getModifiedCount();
            if (deleted == 0) {
                return ResultUtils.failed("博客[" + params.getId() + "]不存在 !");
            }

            long commentDeleted = commentDao.updateOne(Criteria.eq("id", params.getId()),
                    Criteria.set("deleted", "1").add("updated_at", updatedAt))
                    .getModifiedCount();
            Log.log(" 删除了 blog[{}], 级联删除 {} 条评论 ! ", params.getId(), commentDeleted);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.failed(Tools.errorMsg(e));
        }
        return ResultUtils.success(params.getId());
    }


    // -------------------- 辅助方法 --------------------------

    /**
     * 根据给定的id生成该博客需要保存的路径 [相对]
     *
     * @param params 博客的相关信息
     * @return java.lang.String
     * @author Jerry.X.He
     * @date 5/21/2017 3:09 PM
     * @since 1.0
     */
    private String generateBlogPath(BlogSaveForm params) {
        String fileName = DateUtils.formate(new Date(), BlogConstants.FORMAT_FILENAME) + "-" + params.getTitle();
        int bucket = (fileName.hashCode() & 63);
        return bucket + "/" + fileName + Tools.HTML;
    }

    /**
     * 处理保存博客的逻辑
     *
     * @param po     po
     * @param params params
     * @return com.hx.common.interf.common.Result
     * @author Jerry.X.He
     * @date 5/21/2017 10:05 PM
     * @since 1.0
     */
    private Result add0(BlogPO po, BlogSaveForm params) {
        boolean blogInserted = false, tagsInserted = false;
        try {
            blogDao.save(po, BlogConstants.ADD_BEAN_CONFIG);
            blogExDao.save(new BlogExPO(po.getId()), BlogConstants.ADD_BEAN_CONFIG);
            blogInserted = true;

            String[] tagIds = params.getBlogTagIds().split(",");
            if (!Tools.isEmpty(tagIds)) {
                Tools.trimAllSpaces(tagIds);
                List<RltBlogTagPO> blogTags = new ArrayList<>(tagIds.length);
                for (String tagId : tagIds) {
                    blogTags.add(new RltBlogTagPO(po.getId(), tagId));
                }
                rltBlogTagDao.save(blogTags, BlogConstants.ADD_BEAN_CONFIG);
                tagsInserted = true;
            }
            String blogFile = Tools.getFilePath(constants.blogRootDir, po.getContentUrl());
            FileUtils.createIfNotExists(blogFile, true);
            Tools.save(params.getContent(), blogFile);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (blogInserted) {
                    remove(new BeanIdForm(po.getId()));
                    blogExDao.deleteOne(Criteria.eq("blog_id", po.getId()));
                }
                if (tagsInserted) {
                    rltBlogTagDao.deleteMany(Criteria.eq("blog_id", po.getId()));
                }
            } catch (Exception e2) {
                e.printStackTrace();
            }
            return ResultUtils.failed(Tools.errorMsg(e));
        }

        return ResultUtils.success(po.getId());
    }

    /**
     * 处理保存博客的逻辑
     *
     * @param po     po
     * @param params params
     * @return com.hx.common.interf.common.Result
     * @author Jerry.X.He
     * @date 5/21/2017 10:05 PM
     * @since 1.0
     */
    private Result update0(BlogPO po, BlogSaveForm params) {
        po.setId(params.getId());
        po.setUpdatedAt(DateUtils.formate(new Date(), BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS));
        Result updateBlogResult = blogDao.update(po);
        if (!updateBlogResult.isSuccess()) {
            return updateBlogResult;
        }

        String[] tagIds = params.getBlogTagIds().split(",");
        List<RltBlogTagPO> blogTags = null;
        if (!Tools.isEmpty(tagIds)) {
            Tools.trimAllSpaces(tagIds);
            blogTags = new ArrayList<>(tagIds.length);
            for (String tagId : tagIds) {
                blogTags.add(new RltBlogTagPO(po.getId(), tagId));
            }
        }
        Result removeOldTagResult = rltBlogTagDao.remove(Criteria.eq("blog_id", po.getId()), true);
        if (!removeOldTagResult.isSuccess()) {
            return removeOldTagResult;
        }
        if (!CollectionUtils.isEmpty(blogTags)) {
            Result addNewTagResult = rltBlogTagDao.add(blogTags);
            if (!addNewTagResult.isSuccess()) {
                return addNewTagResult;
            }
        }

        try {
            String blogFile = Tools.getFilePath(constants.blogRootDir, po.getContentUrl());
            FileUtils.createIfNotExists(blogFile, true);
            Tools.save(params.getContent(), blogFile);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.failed(Tools.errorMsg(e));
        }

        return ResultUtils.success(po.getId());
    }

    /**
     * 封装查询条件, 以及查询参数 [adminList]
     *
     * @param params        params
     * @param condSqlSb     condSqlSb
     * @param sqlParamsList sqlParamsList
     * @return void
     * @author Jerry.X.He
     * @date 6/4/2017 5:14 PM
     * @since 1.0
     */
    private void encapQueryForAdminList(BlogSearchForm params, StringBuilder condSqlSb, List<Object> sqlParamsList) {
        if (!Tools.isEmpty(params.getId())) {
            condSqlSb.append(" and b.id = ? ");
            sqlParamsList.add(params.getId());
        } else {
            if (!Tools.isEmpty(params.getTypeId())) {
                condSqlSb.append(" and b.blog_type_id = ? ");
                sqlParamsList.add(params.getTypeId());
            }
            if (!Tools.isEmpty(params.getTagId())) {
                condSqlSb.append(" and b.id in (select blog_id from rlt_blog_tag where tag_id = ?) ");
                sqlParamsList.add(params.getTagId());
            }
            if (!Tools.isEmpty(params.getKeywords())) {
                condSqlSb.append(" and (b.title like ? or b.author like ?) ");
                sqlParamsList.add(SqlUtils.wrapWildcard(params.getKeywords()));
                sqlParamsList.add(SqlUtils.wrapWildcard(params.getKeywords()));
            }
            if (!Tools.isEmpty(params.getCreatedAtMonth())) {
                condSqlSb.append(" and b.created_at_month = ? ");
                sqlParamsList.add(params.getCreatedAtMonth());
            }
        }
    }

    // -------------------- BlogVO --------------------------

    /**
     * 封装给定的博客列表的信息
     *
     * @param list list
     * @return void
     * @author Jerry.X.He
     * @date 5/27/2017 11:26 PM
     * @since 1.0
     */
    private Result encapBlogVo(List<BlogVO> list) {
        JSONArray errors = new JSONArray();
        for (BlogVO vo : list) {
            Result result = encapBlogVo(vo);
            if (!result.isSuccess()) {
                errors.add(result);
            }
        }

        if (!errors.isEmpty()) {
            return ResultUtils.failed(list, errors);
        }
        return ResultUtils.success(list);
    }

    /**
     * 封装给定的 vo, 封装 exp, type, tags, content 等等
     *
     * @param vo vo
     * @return void
     * @author Jerry.X.He
     * @date 6/24/2017 5:17 PM
     * @since 1.0
     */
    private Result encapBlogVo(BlogVO vo) {
        Result result = encapBlogEx(vo);
        if (!result.isSuccess()) {
            return result;
        }

        result = encapTypeTagInfo(vo);
        if (!result.isSuccess()) {
            return result;
        }

        result = encapContent(vo);
        if (!result.isSuccess()) {
            return result;
        }
        return ResultUtils.success(vo);
    }

    /**
     * 封装 给定的 Blog 的 sense 以及 BlogEx
     *
     * @param vo vo
     * @return com.hx.common.interf.common.Result
     * @author Jerry.X.He
     * @date 6/6/2017 10:24 PM
     * @since 1.0
     */
    Result encapSenseAndBlogEx(BlogVO vo) {
        Result result = encapSense(vo);
        if (!result.isSuccess()) {
            return result;
        }

        result = encapBlogEx(vo);
        if (!result.isSuccess()) {
            return result;
        }
        return ResultUtils.success(vo);
    }

    /**
     * 封装 给定的 Blog 的 sense 以及 BlogEx
     *
     * @param vo vo
     * @return com.hx.common.interf.common.Result
     * @author Jerry.X.He
     * @date 6/6/2017 10:24 PM
     * @since 1.0
     */
    Result encapSense(BlogVO vo) {
        Result result = goodSensed(vo.getId());
        if (!result.isSuccess()) {
            return result;
        }

        vo.setGoodSensed((Integer) result.getData());
        return ResultUtils.success(vo);
    }

    /**
     * 封装 给定的 Blog 的 sense 以及 BlogEx
     *
     * @param vo vo
     * @return com.hx.common.interf.common.Result
     * @author Jerry.X.He
     * @date 6/6/2017 10:24 PM
     * @since 1.0
     */
    Result encapBlogEx(BlogVO vo) {
        Result getBlogExResult = blogExDao.get(new BeanIdForm(vo.getId()));
        if (!getBlogExResult.isSuccess()) {
            return getBlogExResult;
        }

        BlogExPO exPO = (BlogExPO) getBlogExResult.getData();
        vo = POVOTransferUtils.blogExPO2BlogVO(exPO, vo);
        return ResultUtils.success(vo);
    }

    /**
     * 封装给定的 blog 的 type, tags 的相关信息
     *
     * @param vo vo
     * @return void
     * @author Jerry.X.He
     * @date 6/24/2017 5:11 PM
     * @since 1.0
     */
    private Result encapTypeTagInfo(BlogVO vo) {
        BlogTypePO type = cacheContext.blogType(vo.getBlogTypeId());
        if (type != null) {
            vo.setBlogTypeName(type.getName());
        }
        if (vo.getBlogTagIds() != null) {
            vo.setBlogTagNames(getTagNamesByIds(vo.getBlogTagIds()));
        }

        return ResultUtils.success(vo);
    }

    /**
     * 封装给定的博客的内容信息
     *
     * @param vo vo
     * @return void
     * @author Jerry.X.He
     * @date 5/21/2017 8:48 PM
     * @since 1.0
     */
    private Result encapContent(BlogVO vo) {
        if (!Tools.isEmpty(vo.getContentUrl())) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " 该博客不合法, 没有 内容url ! ");
        }

        vo.setContent(getBlogContentByUrl(vo.getContentUrl()));
        return ResultUtils.success(vo);
    }

    // -------------------- AdminBlogVO --------------------------

    /**
     * 封装 type, tag 的信息
     *
     * @param list adminTreeList
     * @return void
     * @author Jerry.X.He
     * @date 5/21/2017 6:29 PM
     * @since 1.0
     */
    private Result encapTypeTagInfo(List<AdminBlogVO> list) {
        JSONArray errors = new JSONArray();
        for (AdminBlogVO vo : list) {
            Result result = encapTypeTagInfo(vo);
            if (!result.isSuccess()) {
                errors.add(result);
            }
        }

        if (!errors.isEmpty()) {
            return ResultUtils.failed(list, errors);
        }
        return ResultUtils.success(list);
    }

    private Result encapTypeTagInfo(AdminBlogVO vo) {
        BlogTypePO type = cacheContext.blogType(vo.getBlogTypeId());
        if (type != null) {
            vo.setBlogTypeName(type.getName());
        }
        if (vo.getBlogTagIds() != null) {
            vo.setBlogTagNames(getTagNamesByIds(vo.getBlogTagIds()));

        }

        return ResultUtils.success(vo);
    }

    /**
     * 封装给定的博客的内容信息
     *
     * @param vo vo
     * @return void
     * @author Jerry.X.He
     * @date 5/21/2017 8:48 PM
     * @since 1.0
     */
    private Result encapContent(AdminBlogVO vo) {
        if (!Tools.isEmpty(vo.getContentUrl())) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " 该博客不合法, 没有 内容url ! ");
        }

        vo.setContent(getBlogContentByUrl(vo.getContentUrl()));
        return ResultUtils.success(vo);
    }

    /**
     * 获取当前用户是否点了当前博客的赞
     *
     * @param blogId blogId
     * @return boolean
     * @author Jerry.X.He
     * @date 6/6/2017 9:19 PM
     * @since 1.0
     */
    public Result goodSensed(String blogId) {
        SessionUser user = (SessionUser) WebContext.getAttributeFromSession(BlogConstants.SESSION_USER);
        if (user == null) {
            return ResultUtils.success(0);
        }

        BlogSenseForm params = new BlogSenseForm();
        params.setBlogId(blogId);
        params.setSense(constantsContext.upPriseSense);
        params.setUserInfo(user);

        Result getSenseResult = senseDao.get(params);
        if (!getSenseResult.isSuccess()) {
            return ResultUtils.success(0);
        }
        BlogSensePO po = (BlogSensePO) getSenseResult.getData();
        cacheContext.putBlogSense(params, po);
        return ResultUtils.success(po.getScore());
    }

    /**
     * 根据给定的 tagId 列表, 获取 对应的 tagNames
     *
     * @param tagIds tagIds
     * @return java.util.List<java.lang.String>
     * @author Jerry.X.He
     * @date 6/24/2017 5:42 PM
     * @since 1.0
     */
    private List<String> getTagNamesByIds(List<String> tagIds) {
        List<String> tagNames = new ArrayList<>(tagIds.size());
        for (String tagId : tagIds) {
            BlogTagPO tag = cacheContext.blogTag(tagId);
            tagNames.add(tag == null ? Tools.NULL : tag.getName());
        }

        return tagNames;
    }

    /**
     * 根据给定的 url, 获取对应的博客的内容
     *
     * @param contentUrl contentUrl
     * @return java.lang.String
     * @author Jerry.X.He
     * @date 6/24/2017 5:44 PM
     * @since 1.0
     */
    private String getBlogContentByUrl(String contentUrl) {
        try {
            return (Tools.getContent(Tools.getFilePath(constants.blogRootDir, contentUrl)));
        } catch (Exception e) {
            Log.err(Tools.errorMsg(e));
            return " blog does not exists ! ";
        }
    }

}
