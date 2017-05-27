package com.hx.blog_v2.util;

import com.hx.blog_v2.dao.interf.BlogTagDao;
import com.hx.blog_v2.dao.interf.BlogTypeDao;
import com.hx.blog_v2.dao.interf.LinkDao;
import com.hx.blog_v2.domain.po.BlogTagPO;
import com.hx.blog_v2.domain.po.BlogTypePO;
import com.hx.blog_v2.domain.po.LinkPO;
import com.hx.common.interf.cache.Cache;
import com.hx.log.cache.mem.LRUMCache;
import com.hx.log.util.Log;
import com.hx.mongo.criteria.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 缓存了一部分的数据
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/21/2017 6:02 PM
 */
@Component
public class CacheContext {

    @Autowired
    private BlogTagDao blogTagDao;
    @Autowired
    private BlogTypeDao blogTypeDao;
    @Autowired
    private LinkDao linkDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * id -> blogTag
     */
    private Map<String, BlogTagPO> blogTagsById;
    /**
     * id -> blogType
     */
    private Map<String, BlogTypePO> blogTypesById;
    /**
     * 所有的友情链接
     */
    private List<LinkPO> links;
    /**
     * blogId -> 给定的博客的下一个层数索引
     */
    private final Cache<String, AtomicLong> blog2NextFloorId = new LRUMCache<>(BlogConstants.MAX_CACHED_BLOG_2_FLOOR_ID);
    /**
     * blogId, floorId -> 给定的博客给定的层级下一个评论索引
     */
    private final Cache<String, AtomicLong> blogFloor2NextCommentId = new LRUMCache<>(BlogConstants.MAX_CACHED_BLOG_2_FLOOR_ID);

    /**
     * 初始化 CacheContext
     *
     * @return void
     * @author Jerry.X.He
     * @date 5/21/2017 6:36 PM
     * @since 1.0
     */
    @PostConstruct
    public void init() {
        blogTagsById = new LinkedHashMap<>();
        blogTypesById = new LinkedHashMap<>();
        try {
            List<BlogTagPO> list = blogTagDao.findMany(Criteria.allMatch(), BlogConstants.IDX_MANAGER.getDoLoad());
            for (BlogTagPO po : list) {
                blogTagsById.put(po.getId(), po);
            }
        } catch (Exception e) {
            Log.err("error while load blog_tag's data !");
        }
        try {
            List<BlogTypePO> list = blogTypeDao.findMany(Criteria.allMatch(), BlogConstants.IDX_MANAGER.getDoLoad());
            for (BlogTypePO po : list) {
                blogTypesById.put(po.getId(), po);
            }
        } catch (Exception e) {
            Log.err("error while load blog_type's data !");
        }
        try {
            links = linkDao.findMany(Criteria.allMatch(), BlogConstants.IDX_MANAGER.getDoLoad());
        } catch (Exception e) {
            Log.err("error while load link's data !");
        }
    }

    /**
     * 获取所有的 BlogType
     *
     * @return java.util.Map<java.lang.String,com.hx.blog_v2.domain.po.BlogTypePO>
     * @author Jerry.X.He
     * @date 5/21/2017 6:13 PM
     * @since 1.0
     */
    public Map<String, BlogTypePO> allBlogTypes() {
        return blogTypesById;
    }

    public BlogTypePO blogType(String id) {
        return blogTypesById.get(id);
    }

    /**
     * 获取所有的 BlogType
     *
     * @return java.util.Map<java.lang.String,com.hx.blog_v2.domain.po.BlogTypePO>
     * @author Jerry.X.He
     * @date 5/21/2017 6:13 PM
     * @since 1.0
     */
    public Map<String, BlogTagPO> allBlogTags() {
        return blogTagsById;
    }

    public BlogTagPO blogTag(String id) {
        return blogTagsById.get(id);
    }

    /**
     * 获取所有的友情链接
     *
     * @return java.util.List<com.hx.blog_v2.domain.po.LinkPO>
     * @author Jerry.X.He
     * @date 5/27/2017 9:25 PM
     * @since 1.0
     */
    public List<LinkPO> allLinks() {
        return links;
    }

    /**
     * 获取给定到的博客的下一个层数id
     *
     * @param blogId blogId
     * @return long
     * @author Jerry.X.He
     * @date 5/26/2017 7:34 PM
     * @since 1.0
     */
    public String nextFloorId(String blogId) {
        AtomicLong idx = blog2NextFloorId.get(blogId);
        if (idx != null) {
            return String.valueOf(idx.getAndIncrement());
        }

        synchronized (blog2NextFloorId) {
            idx = blog2NextFloorId.get(blogId);
            if (idx != null) {
                return String.valueOf(idx.getAndIncrement());
            }

            String sql = " select ifnull(max(floor_id), 0) as max_floor_id from blog_comment where blog_id = ? ";
            Map<String, Object> res = jdbcTemplate.queryForMap(sql, new Object[]{blogId});
            long maxFloorId = Long.parseLong(String.valueOf(res.get("max_floor_id")));
            blog2NextFloorId.put(blogId, new AtomicLong(maxFloorId + 2));
            return String.valueOf(maxFloorId + 1);
        }
    }

    /**
     * 获取给定到的博客的给定的层数的下一个回复的id
     *
     * @param blogId  blogId
     * @param floorId floorId
     * @return long
     * @author Jerry.X.He
     * @date 5/26/2017 7:35 PM
     * @since 1.0
     */
    public String nextCommentId(String blogId, String floorId) {
        AtomicLong idx = blogFloor2NextCommentId.get(blogId);
        if (idx != null) {
            return String.valueOf(idx.getAndIncrement());
        }

        synchronized (blogFloor2NextCommentId) {
            idx = blogFloor2NextCommentId.get(blogId);
            if (idx != null) {
                return String.valueOf(idx.getAndIncrement());
            }

            String sql = " select ifnull(max(comment_id), 0) as max_comment_id from blog_comment where blog_id = ? and floor_id = ? ";
            Map<String, Object> res = jdbcTemplate.queryForMap(sql, new Object[]{blogId, floorId});
            long maxCommentId = Long.parseLong(String.valueOf(res.get("max_comment_id")));
            blogFloor2NextCommentId.put(blogId, new AtomicLong(maxCommentId + 2));
            return String.valueOf(maxCommentId + 1);
        }
    }


}
