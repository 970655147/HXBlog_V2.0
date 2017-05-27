package com.hx.blog_v2.service;

import com.hx.blog_v2.dao.interf.BlogDao;
import com.hx.blog_v2.dao.interf.BlogExDao;
import com.hx.blog_v2.dao.interf.RltBlogTagDao;
import com.hx.blog_v2.domain.form.AdminBlogSearchForm;
import com.hx.blog_v2.domain.form.BeanIdForm;
import com.hx.blog_v2.domain.form.BlogSaveForm;
import com.hx.blog_v2.domain.mapper.AdminBlogVOMapper;
import com.hx.blog_v2.domain.po.*;
import com.hx.blog_v2.domain.vo.AdminBlogVO;
import com.hx.blog_v2.service.interf.BaseServiceImpl;
import com.hx.blog_v2.service.interf.BlogService;
import com.hx.blog_v2.util.*;
import com.hx.common.interf.common.Page;
import com.hx.common.interf.common.Result;
import com.hx.common.util.ResultUtils;
import com.hx.log.file.FileUtils;
import com.hx.log.util.Log;
import com.hx.log.util.Tools;
import com.hx.mongo.criteria.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    private BlogExDao blogExDao;
    @Autowired
    private RltBlogTagDao rltBlogTagDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private CacheContext cacheContext;

    @Override
    public Result save(BlogSaveForm params) {
        String contentUrl = generateBlogPath(params);
        BlogPO po = new BlogPO(params.getTitle(), params.getAuthor(), params.getCoverUrl(),
                params.getBlogTypeId(), params.getSummary(), contentUrl);

        if (!Tools.isEmpty(params.getId())) {
            return update0(po, params);
        }
        return add0(po, params);
    }

    @Override
    public Result get(BeanIdForm params) {
        StringBuilder sql = new StringBuilder(
                " select b.*, GROUP_CONCAT(rlt.tag_id) as tagIds from blog as b inner join rlt_blog_tag as rlt on b.id = rlt.blog_id " +
                        " where b.deleted = 0 and b.id = ? group by b.id ");
        Object[] sqlParams = new Object[]{params.getId()};

        AdminBlogVO vo = null;
        try {
            // 如果 没有找到记录, 或者 找到多条记录, 都会抛出异常
            vo = jdbcTemplate.queryForObject(sql.toString(), sqlParams, new AdminBlogVOMapper());
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.failed("给定的博客[" + params.getId() + "]不存在 !");
        }
        encapTypeTagInfo(vo);
        encapContent(vo);
        return ResultUtils.success(vo);
    }

    @Override
    public Result adminList(AdminBlogSearchForm params, Page<AdminBlogVO> page) {
        StringBuilder sql = new StringBuilder(
                " select b.*, GROUP_CONCAT(rlt.tag_id) as tagIds from blog as b inner join rlt_blog_tag as rlt on b.id = rlt.blog_id " +
                        " where b.deleted = 0 and b.id >= 0 ");
        List<Object> sqlParams = new ArrayList<>(3);
        if (!Tools.isEmpty(params.getBlogTypeId())) {
            sql.append(" and b.blog_type_id = ? ");
            sqlParams.add(params.getBlogTypeId());
        }
        if (!Tools.isEmpty(params.getBlogTagId())) {
            sql.append(" and b.id in (select blog_id from rlt_blog_tag where tag_id = ?) ");
            sqlParams.add(params.getBlogTagId());
        }
        if (!Tools.isEmpty(params.getKeywords())) {
            sql.append(" and (b.title like ? or b.author like ?) ");
            sqlParams.add(SqlUtils.wrapWildcard(params.getKeywords()));
            sqlParams.add(SqlUtils.wrapWildcard(params.getKeywords()));
        }
        sql.append(" group by b.id limit ?, ? ");
        sqlParams.add(page.recordOffset());
        sqlParams.add(page.getPageSize());

        List<AdminBlogVO> list = jdbcTemplate.query(sql.toString(), sqlParams.toArray(), new AdminBlogVOMapper());
        encapTypeTagInfo(list);
        page.setList(list);
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
     * @param list list
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
                vo.setContent(Tools.getContent(Tools.getFilePath(WebContext.getBlogRootPath(), vo.getContentUrl())));
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
            blogDao.save(po, BlogConstants.IDX_MANAGER_FILTER_ID.getDoLoad(), BlogConstants.IDX_MANAGER_FILTER_ID.getDoFilter());
            blogExDao.save(new BlogExPO(po.getId()), BlogConstants.IDX_MANAGER_FILTER_ID.getDoLoad(), BlogConstants.IDX_MANAGER_FILTER_ID.getDoFilter());
            blogInserted = true;

            String[] tagIds = params.getBlogTagIds().split(",");
            if (!Tools.isEmpty(tagIds)) {
                Tools.trimAllSpaces(tagIds);
                List<RltBlogTagPO> blogTags = new ArrayList<>(tagIds.length);
                for (String tagId : tagIds) {
                    blogTags.add(new RltBlogTagPO(po.getId(), tagId));
                }
                rltBlogTagDao.save(blogTags, BlogConstants.IDX_MANAGER_FILTER_ID.getDoLoad(), BlogConstants.IDX_MANAGER_FILTER_ID.getDoFilter());
                tagsInserted = true;
            }
            String blogFile = Tools.getFilePath(WebContext.getBlogRootPath(), po.getContentUrl());
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
            long matched = blogDao.updateById(po, BlogConstants.IDX_MANAGER_FILTER_ID.getDoLoad(), BlogConstants.IDX_MANAGER_FILTER_ID.getDoFilter())
                    .getMatchedCount();
            if (matched == 0) {
                return ResultUtils.failed("没有找到对应的博客 !");
            }

            String[] tagIds = params.getBlogTagIds().split(",");
            if (!Tools.isEmpty(tagIds)) {
                Tools.trimAllSpaces(tagIds);
                List<RltBlogTagPO> blogTags = new ArrayList<>(tagIds.length);
                for (String tagId : tagIds) {
                    blogTags.add(new RltBlogTagPO(po.getId(), tagId));
                }
                rltBlogTagDao.deleteMany(Criteria.eq("blog_id", po.getId()));
                rltBlogTagDao.save(blogTags, BlogConstants.IDX_MANAGER_FILTER_ID.getDoLoad(), BlogConstants.IDX_MANAGER_FILTER_ID.getDoFilter());
            }
            String blogFile = Tools.getFilePath(WebContext.getBlogRootPath(), po.getContentUrl());
            FileUtils.createIfNotExists(blogFile, true);
            Tools.save(params.getContent(), blogFile);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.failed(Tools.errorMsg(e));
        }

        return ResultUtils.success(po.getId());
    }

}
