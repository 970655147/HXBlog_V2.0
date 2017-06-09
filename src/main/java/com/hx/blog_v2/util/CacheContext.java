package com.hx.blog_v2.util;

import com.hx.blog_v2.dao.interf.*;
import com.hx.blog_v2.domain.form.BlogSenseForm;
import com.hx.blog_v2.domain.form.BlogVisitLogForm;
import com.hx.blog_v2.domain.po.*;
import com.hx.common.interf.cache.Cache;
import com.hx.log.cache.CacheListenerAdapter;
import com.hx.log.cache.mem.LRUMCache;
import com.hx.log.util.Log;
import com.hx.log.util.Tools;
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

    /**
     * blogSense 的key 的分隔符
     */
    public static final String BLOG_SENSE_KEY_SEP = "&%$";

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
    private BlogSenseDao blogSenseDao;
    @Autowired
    private BlogExDao blogExDao;
    @Autowired
    private BlogVisitLogDao blogVisitLogDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private BlogConstants constants;

    /**
     * id -> blogTag
     */
    private Map<String, BlogTagPO> blogTagsById = new LinkedHashMap<>();
    /**
     * id -> blogType
     */
    private Map<String, BlogTypePO> blogTypesById = new LinkedHashMap<>();
    /**
     * 所有的友情链接
     */
    private Map<String, LinkPO> linksById = new LinkedHashMap<>();
    /**
     * 所有的角色
     */
    private Map<String, RolePO> rolesById = new LinkedHashMap<>();
    /**
     * 所有的角色
     */
    private Map<String, ResourcePO> resourcesById = new LinkedHashMap<>();
    /**
     * 所有的接口
     */
    private Map<String, InterfPO> interfsById = new LinkedHashMap<>();

    /**
     * blogId -> 给定的博客的下一个层数索引
     */
    private Cache<String, AtomicLong> blog2NextFloorId;
    /**
     * blogId, floorId -> 给定的博客给定的层级下一个评论索引
     */
    private Cache<String, AtomicLong> blogFloor2NextCommentId;
    /**
     * digest -> uploadedImage 的缓存
     */
    private Cache<String, UploadFilePO> digest2UploadedFiles;
    /**
     * roleIds -> resourceIds 的缓存
     */
    private Cache<String, List<String>> roles2ResourceIds;
    /**
     * (blogId, userName, email, sense) -> clicked 的缓存
     */
    private Cache<String, BlogSensePO> blogIdUserInfo2Sense;
    /**
     * blogId -> BlogEx 的缓存
     */
    private Cache<String, BlogExPO> blogId2BlogEx;
    /**
     * requestIp[_create_at_day] -> BlogVisitLogPO 的缓存
     */
    private Cache<String, BlogVisitLogPO> requestIp2BlogVisitLog;

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
        initICache();
        loadFullCachedResources();
    }

    /**
     * 清理当前 CacheContext
     *
     * @return void
     * @author Jerry.X.He
     * @date 6/9/2017 11:16 PM
     * @since 1.0
     */
    public void clear() {
        blogTagsById.clear();
        blogTypesById.clear();
        linksById.clear();
        rolesById.clear();
        resourcesById.clear();
        interfsById.clear();

        blog2NextFloorId.clear();
        blogFloor2NextCommentId.clear();
        digest2UploadedFiles.clear();
        roles2ResourceIds.clear();
        roles2ResourceIds.clear();
        blogIdUserInfo2Sense.clear();
        blogId2BlogEx.clear();
        requestIp2BlogVisitLog.clear();
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
        clear();
        loadFullCachedResources();
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
     * 获取参数相关的 BlogSense
     *
     * @param params params
     * @return java.lang.Boolean
     * @author Jerry.X.He
     * @date 6/6/2017 8:32 PM
     * @since 1.0
     */
    public BlogSensePO getBlogSense(BlogSenseForm params) {
        return blogIdUserInfo2Sense.get(generateBlogSenseKey(params));
    }

    /**
     * 向缓存中增加 BlogSense
     *
     * @param params params
     * @param po     po
     * @return void
     * @author Jerry.X.He
     * @date 6/6/2017 8:33 PM
     * @since 1.0
     */
    public void putBlogSense(BlogSenseForm params, BlogSensePO po) {
        blogIdUserInfo2Sense.put(generateBlogSenseKey(params), po);
    }

    /**
     * 获取blogId 对应的 BlogExPO
     *
     * @param blogId blogId
     * @return com.hx.blog_v2.domain.po.BlogExPO
     * @author Jerry.X.He
     * @date 6/6/2017 8:38 PM
     * @since 1.0
     */
    public BlogExPO getBlogEx(String blogId) {
        return blogId2BlogEx.get(blogId);
    }

    /**
     * 添加给定的 blogExPO 到缓存中
     *
     * @param blogExPO blogExPO
     * @return com.hx.blog_v2.domain.po.BlogExPO
     * @author Jerry.X.He
     * @date 6/6/2017 8:38 PM
     * @since 1.0
     */
    public void putBlogEx(BlogExPO blogExPO) {
        blogId2BlogEx.put(blogExPO.getBlogId(), blogExPO);
    }

    /**
     * 获取参数相关的 BlogSense
     *
     * @param params params
     * @return java.lang.Boolean
     * @author Jerry.X.He
     * @date 6/6/2017 8:32 PM
     * @since 1.0
     */
    public BlogVisitLogPO getBlogVisitLog(BlogVisitLogForm params) {
        return requestIp2BlogVisitLog.get(generateBlogVisitLogKey(params));
    }

    /**
     * 向缓存中增加 BlogSense
     *
     * @param params params
     * @param po     po
     * @return void
     * @author Jerry.X.He
     * @date 6/6/2017 8:33 PM
     * @since 1.0
     */
    public void putBlogVisitLog(BlogVisitLogForm params, BlogVisitLogPO po) {
        requestIp2BlogVisitLog.put(generateBlogVisitLogKey(params), po);
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
        String blogFloorId = blogFloorIdx(blogId, floorId);
        AtomicLong idx = blogFloor2NextCommentId.get(blogFloorId);
        if (idx != null) {
            return String.valueOf(idx.getAndIncrement());
        }

        synchronized (blogFloor2NextCommentId) {
            idx = blogFloor2NextCommentId.get(blogFloorId);
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

    /**
     * 根据给定的role集合, 获取所有的可以访问的资源
     *
     * @param roleIds roleIds
     * @return java.util.List<java.lang.String>
     * @author Jerry.X.He
     * @date 6/3/2017 3:02 PM
     * @since 1.0
     */
    public List<String> resourceIdsByRoleIds(String roleIds) {
        return roles2ResourceIds.get(roleIds);
    }

    public void putResourceIdsByRoleIds(String roleIds, List<String> resourceIds) {
        roles2ResourceIds.put(roleIds, resourceIds);
    }

    // -------------------- 辅助方法 --------------------------

    /**
     * 初始化 Cache 的资源
     *
     * @return void
     * @author Jerry.X.He
     * @date 6/9/2017 10:08 PM
     * @since 1.0
     */
    private void initICache() {
        blog2NextFloorId = new LRUMCache<>(constants.maxCachedBlog2FloorId, false);
        blogFloor2NextCommentId = new LRUMCache<>(constants.maxCachedBlogFloor2CommentId, false);
        digest2UploadedFiles = new LRUMCache<>(constants.maxCachedUploadedImage, false);
        roles2ResourceIds = new LRUMCache<>(constants.maxRoleIds2ResourceIds, false);
        blogIdUserInfo2Sense = new LRUMCache<>(constants.maxSense2Clicked, false);
        blogId2BlogEx = new LRUMCache<>(constants.maxBlogId2BlogEx, false);
        requestIp2BlogVisitLog = new LRUMCache<>(constants.maxRequestIp2BlogVisitLog, false);

        blogIdUserInfo2Sense.addCacheListener(new JSONTransferableCacheListener<>(blogSenseDao));
        blogId2BlogEx.addCacheListener(new JSONTransferableCacheListener<>(blogExDao));
        requestIp2BlogVisitLog.addCacheListener(new JSONTransferableCacheListener<>(blogVisitLogDao));
    }

    /**
     * 加载 全部缓存的数据
     *
     * @return void
     * @author Jerry.X.He
     * @date 6/3/2017 3:04 PM
     * @since 1.0
     */
    private void loadFullCachedResources() {
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
            e.printStackTrace();
            Log.err("error while load cached's data[tag, type, link, role] !");
        }
    }

    /**
     * 获取给定的博客, 给定的层数的索引[在blogFloor2NextCommentId中使用]
     *
     * @param blogId  blogId
     * @param floorId floorId
     * @return java.lang.String
     * @author Jerry.X.He
     * @date 6/4/2017 1:02 PM
     * @since 1.0
     */
    private String blogFloorIdx(String blogId, String floorId) {
        return blogId + "_" + floorId;
    }

    /**
     * 构造 (blogId, userName, email, sense) 的key
     *
     * @param params params
     * @return java.lang.String
     * @author Jerry.X.He
     * @date 6/6/2017 8:33 PM
     * @since 1.0
     */
    private String generateBlogSenseKey(BlogSenseForm params) {
        return params.getBlogId() + BLOG_SENSE_KEY_SEP + params.getName() + BLOG_SENSE_KEY_SEP + params.getEmail() + BLOG_SENSE_KEY_SEP + params.getSense();
    }

    /**
     * 构造 (blogId, userName, email, sense) 的key
     *
     * @param params params
     * @return java.lang.String
     * @author Jerry.X.He
     * @date 6/9/2017 9:02 PM
     * @since 1.0
     */
    private String generateBlogVisitLogKey(BlogVisitLogForm params) {
        StringBuilder sb = new StringBuilder();
        sb.append(params.getBlogId()).append(BLOG_SENSE_KEY_SEP).append(params.getRequestIp());
        if (!Tools.isEmpty(params.getCreatedAtDay())) {
            sb.append(BLOG_SENSE_KEY_SEP).append(params.getCreatedAtDay());
        }

        return sb.toString();
    }


}
