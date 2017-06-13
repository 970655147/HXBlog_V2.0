package com.hx.blog_v2.util;

import com.hx.blog_v2.dao.interf.*;
import com.hx.blog_v2.domain.dto.StatisticsInfo;
import com.hx.blog_v2.domain.form.BlogSenseForm;
import com.hx.blog_v2.domain.form.BlogVisitLogForm;
import com.hx.blog_v2.domain.po.*;
import com.hx.common.interf.cache.Cache;
import com.hx.log.cache.mem.LRUMCache;
import com.hx.log.util.Log;
import com.hx.log.util.Tools;
import com.hx.mongo.criteria.Criteria;
import com.hx.mongo.criteria.SortByCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
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
    private BlogCreateTypeDao blogCreateTypeDao;
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
     * 所有的资源
     */
    private Map<String, ResourcePO> resourcesById = new LinkedHashMap<>();
    /**
     * 所有的接口
     */
    private Map<String, InterfPO> interfsById = new LinkedHashMap<>();
    /**
     * 所有的博客创建类型
     */
    private Map<String, BlogCreateTypePO> createTypesById = new LinkedHashMap<>();

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
     * resourceIds -> interfs 的缓存
     */
    private Cache<String, List<String>> resource2Interfs;
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
     * 今天的统计数据
     */
    private StatisticsInfo todaysStatistics = new StatisticsInfo();
    /**
     * 缓存的 7 天的 数据
     */
    private Queue<StatisticsInfo> allStatistics = new LinkedList<>();
    /**
     * 今天之前的合计的统计数据
     */
    private StatisticsInfo sumStatistics = new StatisticsInfo();

    /**
     * 目前的5s的统计数据
     */
    private StatisticsInfo now5SecStatistics = new StatisticsInfo();
    /**
     * 缓存的 7 个5s的实时 数据
     */
    private Queue<StatisticsInfo> all5SecStatistics = new LinkedList<>();
    /**
     * 上一次访问 all5SecStatistics 的时间戳
     */
    private long fSecLastVisitDate;
    /**
     * 切换 all5SecStatistics 的任务是否启动
     */
    private ScheduledFuture fSecTaskFuture;

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
        initStastics();
        initSchedule();
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
        createTypesById.clear();

        blog2NextFloorId.clear();
        blogFloor2NextCommentId.clear();
        digest2UploadedFiles.clear();
        roles2ResourceIds.clear();
        resource2Interfs.clear();
        blogIdUserInfo2Sense.clear();
        blogId2BlogEx.clear();
        requestIp2BlogVisitLog.clear();

        todaysStatistics = new StatisticsInfo();
        allStatistics.clear();
        sumStatistics = new StatisticsInfo();
        now5SecStatistics = new StatisticsInfo();
        all5SecStatistics.clear();
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
        initStastics();
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

    public void putBlogType(BlogTypePO po) {
        blogTypesById.put(po.getId(), po);
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

    public void putBlogTag(BlogTagPO po) {
        blogTagsById.put(po.getId(), po);
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

    public void putLink(LinkPO po) {
        linksById.put(po.getId(), po);
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

    public void putRole(RolePO po) {
        rolesById.put(po.getId(), po);
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

    public void putResource(ResourcePO po) {
        resourcesById.put(po.getId(), po);
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

    public void putInterf(InterfPO po) {
        interfsById.put(po.getId(), po);
    }

    /**
     * 获取所有的博客创建类型列表
     *
     * @return java.util.List<com.hx.blog_v2.domain.po.LinkPO>
     * @author Jerry.X.He
     * @date 6/1/2017 7:53 PM
     * @since 1.0
     */
    public Map<String, BlogCreateTypePO> allBlogCreateTypes() {
        return createTypesById;
    }

    public BlogCreateTypePO blogCreateType(String id) {
        return createTypesById.get(id);
    }

    public void putBlogCreateType(BlogCreateTypePO po) {
        createTypesById.put(po.getId(), po);
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
     * 获取今天的统计数据的结果
     *
     * @return com.hx.blog_v2.domain.dto.StatisticsInfo
     * @author Jerry.X.He
     * @date 6/10/2017 9:03 PM
     * @since 1.0
     */
    public StatisticsInfo todaysStatistics() {
        return todaysStatistics;
    }

    public StatisticsInfo sumStatistics() {
        return sumStatistics;
    }

    /**
     * 获取最近7天的的统计数据的结果
     *
     * @return com.hx.blog_v2.domain.dto.StatisticsInfo
     * @author Jerry.X.He
     * @date 6/10/2017 9:03 PM
     * @since 1.0
     */
    public Queue<StatisticsInfo> allStatistics() {
        return allStatistics;
    }

    /**
     * 一天末尾到第二天的数据的切换
     *
     * @return void
     * @author Jerry.X.He
     * @date 6/10/2017 9:06 PM
     * @since 1.0
     */
    public void switchStatistics() {
        if (allStatistics.size() > BlogConstants.MAX_CACHE_STASTICS_DAYS) {
            allStatistics.poll();
        }
        allStatistics.add(todaysStatistics);
        todaysStatistics = new StatisticsInfo();
    }

    /**
     * 获取今天的统计数据的结果
     *
     * @return com.hx.blog_v2.domain.dto.StatisticsInfo
     * @author Jerry.X.He
     * @date 6/10/2017 9:03 PM
     * @since 1.0
     */
    public StatisticsInfo now5SecStatistics() {
        return now5SecStatistics;
    }

    /**
     * 获取最近7天的的统计数据的结果
     *
     * @return com.hx.blog_v2.domain.dto.StatisticsInfo
     * @author Jerry.X.He
     * @date 6/10/2017 9:03 PM
     * @since 1.0
     */
    public Queue<StatisticsInfo> all5SecStatistics() {
        fSecLastVisitDate = System.currentTimeMillis();
        /**
         * 控制资源的采取, 如果长时间没有访问, "关闭" 该线程
         */
        if (all5SecStatistics.isEmpty() && (fSecTaskFuture == null)) {
            fSecTaskFuture = Tools.scheduleAtFixedRate(new Switch5SecStatisInfoRunnable(), 0,
                    BlogConstants.REAL_TIME_CHART_TIME_INTERVAL, TimeUnit.SECONDS);
        }
        return all5SecStatistics;
    }

    /**
     * 一天末尾到第二天的数据的切换
     *
     * @return void
     * @author Jerry.X.He
     * @date 6/10/2017 9:06 PM
     * @since 1.0
     */
    public void switch5SecStatistics() {
        if (all5SecStatistics.size() > BlogConstants.MAX_REAL_TIME_CACHE_STASTICS_TIMES) {
            all5SecStatistics.poll();
        }
        all5SecStatistics.add(now5SecStatistics);
        now5SecStatistics = new StatisticsInfo();
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

    /**
     * 根据给定的rresource集合, 获取所有的可以访问的 接口
     *
     * @param resourceIds resourceIds
     * @return java.util.List<java.lang.String>
     * @author Jerry.X.He
     * @date 6/3/2017 3:02 PM
     * @since 1.0
     */
    public List<String> interfsByResourceIds(String resourceIds) {
        return resource2Interfs.get(resourceIds);
    }

    public void putInterfsByResourceIds(String resourceIds, List<String> interfs) {
        resource2Interfs.put(resourceIds, interfs);
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
        resource2Interfs = new LRUMCache<>(constants.maxRoleIds2ResourceIds, false);
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
            List<BlogTagPO> tagList = blogTagDao.findMany(Criteria.eq("deleted", "0"), Criteria.limitNothing(),
                    Criteria.sortBy("sort", SortByCriteria.ASC), BlogConstants.LOAD_ALL_CONFIG);
            for (BlogTagPO po : tagList) {
                blogTagsById.put(po.getId(), po);
            }
            List<BlogTypePO> typeList = blogTypeDao.findMany(Criteria.eq("deleted", "0"), Criteria.limitNothing(),
                    Criteria.sortBy("sort", SortByCriteria.ASC), BlogConstants.LOAD_ALL_CONFIG);
            for (BlogTypePO po : typeList) {
                blogTypesById.put(po.getId(), po);
            }
            List<LinkPO> linkList = linkDao.findMany(Criteria.eq("deleted", "0"), Criteria.limitNothing(),
                    Criteria.sortBy("sort", SortByCriteria.ASC), BlogConstants.LOAD_ALL_CONFIG);
            for (LinkPO po : linkList) {
                linksById.put(po.getId(), po);
            }
            List<RolePO> roleList = roleDao.findMany(Criteria.eq("deleted", "0"), Criteria.limitNothing(),
                    Criteria.sortBy("sort", SortByCriteria.ASC), BlogConstants.LOAD_ALL_CONFIG);
            for (RolePO po : roleList) {
                rolesById.put(po.getId(), po);
            }
            List<ResourcePO> resourceList = resourceDao.findMany(Criteria.eq("deleted", "0"), Criteria.limitNothing(),
                    Criteria.sortBy("sort", SortByCriteria.ASC), BlogConstants.LOAD_ALL_CONFIG);
            resourceList = reSortResourceList(resourceList);
            for (ResourcePO po : resourceList) {
                resourcesById.put(po.getId(), po);
            }
            List<InterfPO> interfList = interfDao.findMany(Criteria.eq("deleted", "0"), Criteria.limitNothing(),
                    Criteria.sortBy("sort", SortByCriteria.ASC), BlogConstants.LOAD_ALL_CONFIG);
            for (InterfPO po : interfList) {
                interfsById.put(po.getId(), po);
            }
            List<BlogCreateTypePO> blogCreateTypeList = blogCreateTypeDao.findMany(Criteria.eq("deleted", "0"), Criteria.limitNothing(),
                    Criteria.sortBy("sort", SortByCriteria.ASC), BlogConstants.LOAD_ALL_CONFIG);
            for (BlogCreateTypePO po : blogCreateTypeList) {
                createTypesById.put(po.getId(), po);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.err("error while load cached's data[tag, type, link, role, interf] !");
        }
    }

    /**
     * 从数据库拉取统计信息
     *
     * @return void
     * @author Jerry.X.He
     * @date 6/10/2017 9:08 PM
     * @since 1.0
     */
    private void initStastics() {
        List<StatisticsInfo> allDayStatisInfo = BizUtils.collectRecentlyStatisticsInfo(jdbcTemplate, BlogConstants.MAX_CACHE_STASTICS_DAYS);
        allStatistics.addAll(allDayStatisInfo);
        if (!Tools.isEmpty(allDayStatisInfo)) {
            todaysStatistics = allDayStatisInfo.get(allDayStatisInfo.size() - 1);
        }
        sumStatistics = BizUtils.collectSumStatisticsInfo(jdbcTemplate);
    }

    /**
     * 初始化调度任务
     * 1. 每天 切换 daysStasticsInfo
     *
     * @return void
     * @author Jerry.X.He
     * @date 6/11/2017 12:00 AM
     * @since 1.0
     */
    private void initSchedule() {
        long msOffToNextDawn = DateUtils.beginOfDay(DateUtils.addDay(new Date(), 1)).getTime() - System.currentTimeMillis();
        Tools.scheduleAtFixedRate(new SwitchStatisInfoRunnable(), msOffToNextDawn, 1, TimeUnit.DAYS);

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
        return params.getBlogId() + BLOG_SENSE_KEY_SEP + params.getName() + BLOG_SENSE_KEY_SEP + params.getRequestIp() + BLOG_SENSE_KEY_SEP + params.getSense();
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

    /**
     * 对于 resource 进行资源重排, 一级资源|二级资源|..
     *
     * @param resoures resoures
     * @return java.util.List<com.hx.blog_v2.domain.po.ResourcePO>
     * @author Jerry.X.He
     * @date 6/12/2017 10:54 PM
     * @since 1.0
     */
    private List<ResourcePO> reSortResourceList(List<ResourcePO> resoures) {
        List<ResourcePO> result = new ArrayList<>(resoures.size());
        for (int i = 0; i <= BlogConstants.RESOURCE_LEAVE_LEVEL; i++) {
            for (ResourcePO po : resoures) {
                if (po.getLevel() == i) {
                    result.add(po);
                }
            }
        }
        return result;
    }

    /**
     * 切换 统计信息的 任务
     *
     * @author Jerry.X.He <970655147@qq.com>
     * @version 1.0
     * @date 6/11/2017 12:02 AM
     */
    private class SwitchStatisInfoRunnable implements Runnable {
        @Override
        public void run() {
            CacheContext.this.switchStatistics();
        }
    }

    /**
     * 切换 实时统计信息的 任务
     *
     * @author Jerry.X.He <970655147@qq.com>
     * @version 1.0
     * @date 6/11/2017 12:02 AM
     */
    private class Switch5SecStatisInfoRunnable implements Runnable {
        @Override
        public void run() {
            if ((System.currentTimeMillis() - fSecLastVisitDate) > ((BlogConstants.REAL_TIME_CHART_TIME_INTERVAL + 1) << 10)) {
                all5SecStatistics.clear();
                fSecTaskFuture.cancel(false);
                fSecTaskFuture = null;
                return;
            }

            CacheContext.this.switch5SecStatistics();
        }
    }


}
