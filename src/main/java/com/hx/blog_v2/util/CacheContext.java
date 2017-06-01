package com.hx.blog_v2.util;

import com.hx.blog_v2.dao.interf.*;
import com.hx.blog_v2.domain.po.*;
import com.hx.common.interf.cache.Cache;
import com.hx.log.cache.mem.LRUMCache;
import com.hx.log.util.Log;
import com.hx.mongo.criteria.Criteria;
import com.hx.mongo.criteria.SortByCriteria;
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
    private RoleDao roleDao;
    @Autowired
    private ResourceDao resourceDao;
    @Autowired
    private InterfDao interfDao;
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
    private Map<String, LinkPO> linksById;
    /**
     * 所有的角色
     */
    private Map<String, RolePO> rolesById;
    /**
     * 所有的角色
     */
    private Map<String, ResourcePO> resourcesById;
    /**
     * 所有的接口
     */
    private Map<String, InterfPO> interfsById;
    /**
     * blogId -> 给定的博客的下一个层数索引
     */
    private final Cache<String, AtomicLong> blog2NextFloorId = new LRUMCache<>(BlogConstants.MAX_CACHED_BLOG_2_FLOOR_ID);
    /**
     * blogId, floorId -> 给定的博客给定的层级下一个评论索引
     */
    private final Cache<String, AtomicLong> blogFloor2NextCommentId = new LRUMCache<>(BlogConstants.MAX_CACHED_BLOG_2_FLOOR_ID);
    /**
     * digest -> uploadedImage 的缓存
     */
    private final Cache<String, UploadFilePO> digest2UploadedFiles = new LRUMCache<>(BlogConstants.MAX_CACHED_UPLOADED_IMAGE);

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
        linksById = new LinkedHashMap<>();
        rolesById = new LinkedHashMap<>();
        resourcesById = new LinkedHashMap<>();
        interfsById = new LinkedHashMap<>();

        refresh();
    }

    /**
     * 刷新当前系统的配置
     *
     * @return void
     * @author Jerry.X.He
     * @date 5/31/2017 7:49 PM
     * @since 1.0
     */
    public void refresh() {
        blogTagsById.clear();
        blogTypesById.clear();
        linksById.clear();
        rolesById.clear();
        resourcesById.clear();
        interfsById.clear();

        try {
            List<BlogTagPO> tagList = blogTagDao.findMany(Criteria.eq("deleted", "0"), BlogConstants.LOAD_ALL_CONFIG);
            for (BlogTagPO po : tagList) {
                blogTagsById.put(po.getId(), po);
            }
            List<BlogTypePO> typeList = blogTypeDao.findMany(Criteria.eq("deleted", "0"), BlogConstants.LOAD_ALL_CONFIG);
            for (BlogTypePO po : typeList) {
                blogTypesById.put(po.getId(), po);
            }
            List<LinkPO> linkList = linkDao.findMany(Criteria.eq("deleted", "0"), Criteria.limitNothing(),
                    Criteria.sortBy("sort", SortByCriteria.ASC), BlogConstants.LOAD_ALL_CONFIG);
            for (LinkPO po : linkList) {
                linksById.put(po.getId(), po);
            }
            List<RolePO> roleList = roleDao.findMany(Criteria.eq("deleted", "0"), BlogConstants.LOAD_ALL_CONFIG);
            for (RolePO po : roleList) {
                rolesById.put(po.getId(), po);
            }
            List<ResourcePO> resourceList = resourceDao.findMany(Criteria.eq("deleted", "0"), Criteria.limitNothing(),
                    Criteria.sortBy("sort", SortByCriteria.ASC), BlogConstants.LOAD_ALL_CONFIG);
            for (ResourcePO po : resourceList) {
                resourcesById.put(po.getId(), po);
            }
            List<InterfPO> interfList = interfDao.findMany(Criteria.eq("deleted", "0"), BlogConstants.LOAD_ALL_CONFIG);
            for (InterfPO po : interfList) {
                interfsById.put(po.getId(), po);
            }
        } catch (Exception e) {
            Log.err("error while load cached's data[tag, type, link, role] !");
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
    public Map<String, LinkPO> allLinks() {
        return linksById;
    }

    public LinkPO link(String id) {
        return linksById.get(id);
    }

    /**
     * 获取所有的角色
     *
     * @return java.util.List<com.hx.blog_v2.domain.po.LinkPO>
     * @author Jerry.X.He
     * @date 5/27/2017 9:25 PM
     * @since 1.0
     */
    public Map<String, RolePO> allRoles() {
        return rolesById;
    }

    public RolePO role(String id) {
        return rolesById.get(id);
    }

    /**
     * 获取所有的资源
     *
     * @return java.util.List<com.hx.blog_v2.domain.po.LinkPO>
     * @author Jerry.X.He
     * @date 5/27/2017 9:25 PM
     * @since 1.0
     */
    public Map<String, ResourcePO> allResources() {
        return resourcesById;
    }

    public ResourcePO resource(String id) {
        return resourcesById.get(id);
    }

    /**
     * 获取所有的接口列表
     *
     * @return java.util.List<com.hx.blog_v2.domain.po.LinkPO>
     * @author Jerry.X.He
     * @date 6/1/2017 7:53 PM
     * @since 1.0
     */
    public Map<String, InterfPO> allInterfs() {
        return interfsById;
    }

    public InterfPO interf(String id) {
        return interfsById.get(id);
    }

    /**
     * 根据digest 获取图片的信息
     *
     * @param digest digest
     * @return com.hx.blog_v2.domain.po.UploadFilePO
     * @author Jerry.X.He
     * @date 5/29/2017 4:27 PM
     * @since 1.0
     */
    public UploadFilePO getUploadedFile(String digest) {
        return digest2UploadedFiles.get(digest);
    }

    /**
     * 向缓存中添加一个图片信息
     *
     * @param digest digest
     * @param image  image
     * @return void
     * @author Jerry.X.He
     * @date 5/29/2017 4:28 PM
     * @since 1.0
     */
    public void putUploadedFile(String digest, UploadFilePO image) {
        digest2UploadedFiles.put(digest, image);
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

    // -------------------- 辅助方法 --------------------------


}
