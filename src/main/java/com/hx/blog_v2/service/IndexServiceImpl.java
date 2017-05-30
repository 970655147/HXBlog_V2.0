package com.hx.blog_v2.service;

import com.hx.blog_v2.dao.interf.BlogCommentDao;
import com.hx.blog_v2.dao.interf.BlogDao;
import com.hx.blog_v2.dao.interf.BlogExDao;
import com.hx.blog_v2.dao.interf.LinkDao;
import com.hx.blog_v2.domain.mapper.*;
import com.hx.blog_v2.domain.po.BlogTagPO;
import com.hx.blog_v2.domain.po.BlogTypePO;
import com.hx.blog_v2.domain.po.ResourcePO;
import com.hx.blog_v2.domain.vo.*;
import com.hx.blog_v2.service.interf.BaseServiceImpl;
import com.hx.blog_v2.service.interf.IndexService;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.CacheContext;
import com.hx.blog_v2.util.WebContext;
import com.hx.common.interf.common.Result;
import com.hx.common.util.ResultUtils;
import com.hx.json.JSONArray;
import com.hx.json.JSONObject;
import com.hx.log.alogrithm.tree.TreeUtils;
import com.hx.log.alogrithm.tree.interf.TreeInfoExtractor;
import com.hx.log.collection.CollectionUtils;
import com.hx.log.util.Log;
import com.hx.log.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * BlogServiceImpl
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 11:47 AM
 */
@Service
public class IndexServiceImpl extends BaseServiceImpl<Object> implements IndexService {

    @Autowired
    private CacheContext cacheContext;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Result index() {
        String hotBlogsSql = " select * from blog as b inner join blog_ex as e on b.id = e.blog_id " +
                " where b.deleted = 0 and b.id >= 0 order by e.comment_cnt desc limit 0, 5 ";
        String latestCommentsSql = " select * from blog_comment order by created_at limit 0, 5 ";
        String contextBlogSql = " select * from blog as b inner join blog_ex as e on b.id = e.blog_id " +
                " where b.id = " + BlogConstants.CONTEXT_BLOG_ID;
        String facetByMogroupnthSql = " select b.created_at_month as month, count(*) as cnt from blog as b " +
                "where b.deleted = 0 and b.id >= 0 group by b.created_at_month ";
        String todayVisitedSql = " select count(*) as cnt from visitor where created_at >= (select cast(cast(sysdate() as date) AS datetime))";

        List<BlogVO> hotBlogs = jdbcTemplate.query(hotBlogsSql, new BlogVOMapper());
        List<CommentVO> latestComments = jdbcTemplate.query(latestCommentsSql, new CommentVOMapper());
        BlogVO contextBlog = jdbcTemplate.queryForObject(contextBlogSql, new BlogVOMapper());
        List<FacetByMonthVO> facetByMonth = jdbcTemplate.query(facetByMogroupnthSql, new FacetByMonthMapper());
        Integer todayVisited = jdbcTemplate.queryForObject(todayVisitedSql, new OneIntMapper("cnt"));
        encapBlogVo(hotBlogs);

        JSONObject data = new JSONObject();
        data.put("title", "生活有度, 人生添寿");
        data.put("subTitle", "如果你浪费了自己的年龄, 那是挺可悲的 因为你的青春只能持续一点儿时间 -- 很短的一点儿时间 ");
        data.put("tags", tags2List(cacheContext.allBlogTags()));
        data.put("types", tags2List(cacheContext.allBlogTypes()));
        data.put("links", cacheContext.allLinks());

        data.put("hotBlogs", hotBlogs);
        data.put("latestComments", latestComments);
        data.put("facetByMonth", facetByMonth);
        data.put("goodSensed", contextBlog.getGoodCnt());
        data.put("todayVisited", todayVisited);
        // 本周, 本月, 合计

        return ResultUtils.success(data);
    }

    @Override
    public Result latest() {
        String recommendBlogsSql = " select * from blog as b inner join blog_ex as e on b.id = e.blog_id " +
                " where b.deleted = 0 and b.id >= 0 order by e.comment_cnt desc limit 0, 1 ";
        String latestBlogsSql = " select * from blog as b inner join blog_ex as e on b.id = e.blog_id " +
                " where b.deleted = 0 and b.id >= 0 order by b.created_at desc limit 0, 5 ";

        BlogVO recommendBlog = jdbcTemplate.queryForObject(recommendBlogsSql, new BlogVOMapper());
        List<BlogVO> latestBlogs = jdbcTemplate.query(latestBlogsSql, new BlogVOMapper());
        encapBlogVo(Collections.singletonList(recommendBlog));
        encapBlogVo(latestBlogs);

        JSONObject data = new JSONObject();
        data.put("recommend", recommendBlog);
        data.put("latestBlogs", latestBlogs);

        return ResultUtils.success(data);
    }

    @Override
    public Result adminMenus() {
        String resourceSql = " select * from resource where enable = 1 and deleted = 0 order by sort ";
        List<ResourceVO> resources =jdbcTemplate.query(resourceSql, new ResourceVOMapper());
        if(CollectionUtils.isEmpty(resources) ) {
            return ResultUtils.success(new JSONObject());
        }

        JSONObject root = TreeUtils.generateTree(resources, new TreeInfoExtractor<ResourceVO>() {
            @Override
            public void extract(ResourceVO bean, JSONObject obj) {
                obj.element("id", bean.getId());
                obj.element("name", bean.getName());
                obj.element("url", bean.getUrl());
                obj.element("iconClass", bean.getIconClass());
                obj.element("parentId", bean.getParentId());
            }
        }, "childs", "-1");
        TreeUtils.childArrayify(root, "childs");
        return ResultUtils.success(root);
    }

    @Override
    public Result adminStatistics() {
        JSONObject data = new JSONObject()
                .element("lastLoginIp", "127.0.0.1").element("lastLoginDate", "2017-05-30").element("lastLoginAddr", "四川成都")
                .element("loginIp", "127.0.0.1").element("loginDate", "2017-05-31").element("lastLoginAddr", "四川贵阳")
                .element("todayVisited", "120").element("todayGoodSensed", "110")
                .element("totalVisited", "2220").element("totalGoodSensed", "3330")
                .element("totalCommentCnt", "120").element("totalBlogCnt", "1330");

        return ResultUtils.success(data);
    }

    // -------------------- 辅助方法 --------------------------

    /**
     * 把给定的Map集合转换为List
     *
     * @param mapById mapById
     * @return java.util.List<com.hx.blog_v2.domain.po.BlogTagPO>
     * @author Jerry.X.He
     * @date 5/27/2017 9:21 PM
     * @since 1.0
     */
    private <T> List<T> tags2List(Map<String, T> mapById) {
        List<T> result = new ArrayList<>(mapById.size());
        for (Map.Entry<String, T> entry : mapById.entrySet()) {
            result.add(entry.getValue());
        }
        return result;
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
        for(BlogVO vo : voes) {
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
                vo.setContent(Tools.getContent(Tools.getFilePath(WebContext.getBlogRootPath(), vo.getContentUrl())));
            } catch (Exception e) {
                Log.err(Tools.errorMsg(e));
            }
        }
    }

}
