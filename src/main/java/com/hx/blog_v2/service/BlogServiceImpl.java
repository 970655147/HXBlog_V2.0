package com.hx.blog_v2.service;

import com.hx.blog_v2.dao.interf.BlogCommentDao;
import com.hx.blog_v2.dao.interf.BlogDao;
import com.hx.blog_v2.dao.interf.BlogExDao;
import com.hx.blog_v2.dao.interf.RltBlogTagDao;
import com.hx.blog_v2.domain.dto.SessionUser;
import com.hx.blog_v2.domain.form.BeanIdForm;
import com.hx.blog_v2.domain.form.BlogSaveForm;
import com.hx.blog_v2.domain.form.BlogSearchForm;
import com.hx.blog_v2.domain.mapper.AdminBlogVOMapper;
import com.hx.blog_v2.domain.mapper.BlogVOMapper;
import com.hx.blog_v2.domain.mapper.CommentPOMapper;
import com.hx.blog_v2.domain.mapper.OneIntMapper;
import com.hx.blog_v2.domain.po.*;
import com.hx.blog_v2.domain.vo.AdminBlogVO;
import com.hx.blog_v2.domain.vo.BlogVO;
import com.hx.blog_v2.service.interf.BaseServiceImpl;
import com.hx.blog_v2.service.interf.BlogService;
import com.hx.blog_v2.util.*;
import com.hx.common.interf.common.Page;
import com.hx.common.interf.common.Result;
import com.hx.common.util.ResultUtils;
import com.hx.json.JSONArray;
import com.hx.json.JSONObject;
import com.hx.log.collection.CollectionUtils;
import com.hx.log.file.FileUtils;
import com.hx.log.util.Log;
import com.hx.log.util.Tools;
import com.hx.mongo.criteria.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

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
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private CacheContext cacheContext;
    @Autowired
    private BlogConstants constants;

    @Override
    public Result save(BlogSaveForm params) {
        SessionUser user = (SessionUser) WebContext.getAttributeFromSession(BlogConstants.SESSION_USER);
        String contentUrl = generateBlogPath(params);
        BlogPO po = new BlogPO(params.getTitle(), user.getUserName(), params.getCoverUrl(),
                params.getBlogTypeId(), params.getSummary(), contentUrl);

        if (!Tools.isEmpty(params.getId())) {
            return update0(po, params);
        }
        return add0(po, params);
    }

    @Override
    public Result adminGet(BeanIdForm params) {
        String sql = " select b.*, GROUP_CONCAT(rlt.tag_id) as tagIds from blog as b " +
                " inner join rlt_blog_tag as rlt on b.id = rlt.blog_id " +
                " where b.deleted = 0 and b.id = ? group by b.id ";
        Object[] sqlParams = new Object[]{params.getId()};

        AdminBlogVO vo = null;
        try {
            // 如果 没有找到记录, 或者 找到多条记录, 都会抛出异常
            vo = jdbcTemplate.queryForObject(sql, sqlParams, new AdminBlogVOMapper());
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.failed("给定的博客[" + params.getId() + "]不存在 !");
        }
        encapTypeTagInfo(vo);
        encapContent(vo);
        return ResultUtils.success(vo);
    }

    @Override
    public Result get(BeanIdForm params) {
        String blogSql = " select e.*, b.*, GROUP_CONCAT(rlt.tag_id) as tagIds from blog as b " +
                " inner join rlt_blog_tag as rlt on b.id = rlt.blog_id " +
                " inner join blog_ex as e on b.id = e.blog_id " +
                " where b.deleted = 0 and b.id = ? group by b.id ";
        String commentsSql = " select * from blog_comment where deleted = 0 and blog_id = ? order by created_at asc ";
        Object[] sqlParams = new Object[]{params.getId()};

        BlogVO vo = null;
        List<BlogCommentPO> comments = null;
        try {
            // 如果 没有找到记录, 或者 找到多条记录, 都会抛出异常, ex.*, b.* 这个顺序, 避免 po.id 被 exPo.id 覆盖
            vo = jdbcTemplate.queryForObject(blogSql, sqlParams, new BlogVOMapper());
            comments = jdbcTemplate.query(commentsSql, sqlParams, new CommentPOMapper());
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.failed("给定的博客[" + params.getId() + "]不存在 !");
        }
        encapTypeTagInfo(vo);
        encapContent(vo);

        JSONArray commentsArr = generateCommentTree(comments);
        JSONObject data = new JSONObject()
                .element("blog", vo).element("comments", commentsArr);
        return ResultUtils.success(data);
    }

    @Override
    public Result list(BlogSearchForm params, Page<BlogVO> page) {
        String selectSql = " select e.*, b.*, GROUP_CONCAT(rlt.tag_id) as tagIds from blog as b " +
                " inner join rlt_blog_tag as rlt on b.id = rlt.blog_id " +
                " inner join blog_ex as e on b.id = e.blog_id where b.deleted = 0 and b.id >= 0 ";
        String selectSqlSuffix = " group by b.id limit ?, ? ";
        String countSql = " select count(*) as totalRecord from blog as b where b.deleted = 0 and b.id >= 0 ";

        StringBuilder condSqlSb = new StringBuilder();
        List<Object> selectParams = new ArrayList<>(3);
        encapQueryForAdminList(params, condSqlSb, selectParams);
        String condSql = condSqlSb.toString();
        Object[] countParams = selectParams.toArray();
        selectParams.add(page.recordOffset());
        selectParams.add(page.getPageSize());

        List<BlogVO> list = jdbcTemplate.query(selectSql + condSql + selectSqlSuffix, selectParams.toArray(), new BlogVOMapper());
        Integer totalRecord = jdbcTemplate.queryForObject(countSql + condSql, countParams, new OneIntMapper("totalRecord"));
        encapBlogVo(list);
        page.setList(list);
        page.setTotalRecord(totalRecord);
        return ResultUtils.success(page);
    }

    @Override
    public Result adminList(BlogSearchForm params, Page<AdminBlogVO> page) {
        String selectSql = " select b.*, GROUP_CONCAT(rlt.tag_id) as tagIds from blog as b " +
                " inner join rlt_blog_tag as rlt on b.id = rlt.blog_id where b.deleted = 0 ";
        String selectSqlSuffix = " group by b.id limit ?, ? ";
        String countSql = " select count(*) as totalRecord from blog as b where b.deleted = 0 ";
        List<Object> selectParamsList = new ArrayList<>(3);
        StringBuilder condSqlSb = new StringBuilder();

        encapQueryForAdminList(params, condSqlSb, selectParamsList);
        String condSql = condSqlSb.toString();
        Object[] countParams = selectParamsList.toArray();
        selectParamsList.add(page.recordOffset());
        selectParamsList.add(page.getPageSize());

        List<AdminBlogVO> list = jdbcTemplate.query(selectSql + condSql + selectSqlSuffix, selectParamsList.toArray(), new AdminBlogVOMapper());
        Integer totalRecord = jdbcTemplate.queryForObject(countSql + condSql, countParams, new OneIntMapper("totalRecord"));
        encapTypeTagInfo(list);
        page.setList(list);
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
     * 封装 type, tag 的信息
     *
     * @param list adminTreeList
     * @return void
     * @author Jerry.X.He
     * @date 5/21/2017 6:29 PM
     * @since 1.0
     */
    private void encapTypeTagInfo(List<AdminBlogVO> list) {
        for (AdminBlogVO vo : list) {
            encapTypeTagInfo(vo);
        }
    }

    private void encapTypeTagInfo(AdminBlogVO vo) {
        BlogTypePO type = cacheContext.blogType(vo.getBlogTypeId());
        if (type != null) {
            vo.setBlogTypeName(type.getName());
        }
        if (vo.getBlogTagIds() != null) {
            List<String> tagIds = vo.getBlogTagIds();
            List<String> tagNames = new ArrayList<>(tagIds.size());
            for (String tagId : tagIds) {
                BlogTagPO tag = cacheContext.blogTag(tagId);
                tagNames.add(tag == null ? Tools.NULL : tag.getName());
            }
            vo.setBlogTagNames(tagNames);
        }
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
    private void encapContent(AdminBlogVO vo) {
        if (!Tools.isEmpty(vo.getContentUrl())) {
            try {
                vo.setContent(Tools.getContent(Tools.getFilePath(constants.blogRootDir, vo.getContentUrl())));
            } catch (Exception e) {
                Log.err(Tools.errorMsg(e));
            }
        }
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

        try {
            // 对于createAt的处理 先放在这里, 等之后 HXMongo 的review[abort JSONTransferable] 之后吧,
            long matched = blogDao.updateById(po, BlogConstants.UPDATE_BEAN_CONFIG)
                    .getModifiedCount();
            if (matched == 0) {
                return ResultUtils.failed("没有找到对应的博客 !");
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
            rltBlogTagDao.deleteMany(Criteria.eq("blog_id", po.getId()));
            if (!CollectionUtils.isEmpty(blogTags)) {
                rltBlogTagDao.insertMany(blogTags, BlogConstants.ADD_BEAN_CONFIG);
            }

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
     * 封装给定的博客列表的信息
     *
     * @param voes voes
     * @return void
     * @author Jerry.X.He
     * @date 5/27/2017 11:26 PM
     * @since 1.0
     */
    private void encapBlogVo(List<BlogVO> voes) {
        for (BlogVO vo : voes) {
            encapTypeTagInfo(vo);
            encapContent(vo);
        }
    }

    private void encapTypeTagInfo(BlogVO vo) {
        BlogTypePO type = cacheContext.blogType(vo.getBlogTypeId());
        if (type != null) {
            vo.setBlogTypeName(type.getName());
        }
        if (vo.getBlogTagIds() != null) {
            List<String> tagIds = vo.getBlogTagIds();
            List<String> tagNames = new ArrayList<>(tagIds.size());
            for (String tagId : tagIds) {
                BlogTagPO tag = cacheContext.blogTag(tagId);
                tagNames.add(tag == null ? Tools.NULL : tag.getName());
            }
            vo.setBlogTagNames(tagNames);
        }
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
    private void encapContent(BlogVO vo) {
        if (!Tools.isEmpty(vo.getContentUrl())) {
            try {
                vo.setContent(Tools.getContent(Tools.getFilePath(constants.blogRootDir, vo.getContentUrl())));
            } catch (Exception e) {
                Log.err(Tools.errorMsg(e));
            }
        }
    }

    /**
     * 生成评论树
     *
     * @param comments comments
     * @return com.hx.json.JSONArray
     * @author Jerry.X.He
     * @date 5/28/2017 2:48 PM
     * @since 1.0
     */
    public JSONArray generateCommentTree(List<BlogCommentPO> comments) {
        Map<String, List<BlogCommentPO>> treeMap = new LinkedHashMap<>();
        for (BlogCommentPO comment : comments) {
            List<BlogCommentPO> floorComments = treeMap.get(comment.getFloorId());
            if (floorComments == null) {
                floorComments = new ArrayList<>();
                treeMap.put(comment.getFloorId(), floorComments);
            }

            floorComments.add(comment);
        }

        JSONArray arr = new JSONArray();
        for (Map.Entry<String, List<BlogCommentPO>> entry : treeMap.entrySet()) {
            arr.add(entry.getValue());
        }
        return arr;
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
        }
    }

}
