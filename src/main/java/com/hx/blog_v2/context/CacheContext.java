package com.hx.blog_v2.context;

import com.hx.blog_v2.dao.interf.*;
import com.hx.blog_v2.domain.dto.StatisticsInfo;
import com.hx.blog_v2.domain.form.BlogSenseForm;
import com.hx.blog_v2.domain.form.BlogVisitLogForm;
import com.hx.blog_v2.domain.po.*;
import com.hx.blog_v2.domain.vo.CommentVO;
import com.hx.blog_v2.util.*;
import com.hx.common.interf.cache.Cache;
import com.hx.common.interf.common.Result;
import com.hx.json.JSONArray;
import com.hx.json.JSONObject;
import com.hx.log.alogrithm.tree.TreeUtils;
import com.hx.log.alogrithm.tree.interf.TreeInfoExtractor;
import com.hx.log.cache.mem.FIFOMCache;
import com.hx.log.cache.mem.LRUMCache;
import com.hx.log.cache.mem.UniverseCache;
import com.hx.log.util.Log;
import com.hx.log.util.Tools;
import com.hx.mongo.criteria.Criteria;
import com.hx.mongo.criteria.LimitCriteria;
import com.hx.mongo.criteria.SortByCriteria;
import com.hx.mongo.criteria.interf.IQueryCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
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
    public static final String BLOG_SENSE_KEY_SEP = "$$$";
    /**
     * 刷新缓存了表的所有的数据的缓存的标志位
     */
    public static final int REFRESH_ALL_TABLE_CACHED = 0x0001;
    /**
     * 刷新部分缓存的标志位
     */
    public static final int REFRESH_LOCAL_CACHED = 0x0010;
    /**
     * 刷新统计数据的标志位
     */
    public static final int REFRESH_STATISTICS_CACHED = 0x0100;
    /**
     * 刷新其他数据的标志位
     */
    public static final int REFRESH_OTHER_CACHED = 0x1000;
    /**
     * 刷新所有的缓存的标志位
     */
    public static final int REFRESH_ALL_CONFIG = REFRESH_ALL_TABLE_CACHED | REFRESH_LOCAL_CACHED
            | REFRESH_STATISTICS_CACHED | REFRESH_OTHER_CACHED;

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
    private BlogDao blogDao;
    @Autowired
    private BlogCommentDao blogCommentDao;
    @Autowired
    private BlogExDao blogExDao;
    @Autowired
    private BlogVisitLogDao blogVisitLogDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ConstantsContext constantsContext;

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
     * requestIp[_create_at_day] -> BlogVisitLogPO 的缓存
     */
    private Cache<String, String> forceOffLineMap;

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
     * blogId -> blogId 的缓存
     */
    private Cache<String, BlogPO> id2Blog;
    /**
     * blogId -> tagIds 的缓存
     */
    private Cache<String, List<String>> blogId2TagIds;
    /**
     * blogId_pageNo -> [comment ] 的缓存
     */
    private Cache<String, List<List<CommentVO>>> blogIdPage2Comment;

    /**
     * 今天的统计数据
     */
    private StatisticsInfo todaysStatistics = new StatisticsInfo();
    /**
     * 缓存的 7-1 天的 数据
     */
    private LinkedList<StatisticsInfo> recentlyStatistics = new LinkedList<>();
    /**
     * 缓存 今天之前7-1 天的 数据的总和[每天刷新一次]
     */
    private StatisticsInfo recentlySumStatistics = null;
    /**
     * 今天之前的合计的统计数据
     */
    private StatisticsInfo sumStatistics = new StatisticsInfo();

    /**
     * 目前的5s的统计数据
     */
    private StatisticsInfo now5SecStatistics = new StatisticsInfo();
    /**
     * 缓存的 7 个5s的实时 数据[保存的所有的统计信息]
     */
    private Queue<StatisticsInfo> all5SecStatistics = new LinkedList<>();
    /**
     * month -> 月博客的数量
     */
    private Map<String, Integer> monthFacet = new LinkedHashMap<>();

    /**
     * 周期性的统计给定的 标志的访问的次数
     */
    private Map<String, AtomicInteger> visitPerPeroid = new HashMap<>();
    /**
     * 周期性的统计给定的 标志的访问的不合法次数
     */
    private Map<String, AtomicInteger> inputNotFormatPerPeroid = new HashMap<>();
    /**
     * 黑名单中的信息
     */
    private Cache<String, String> blackList;
    /**
     * 缓存的最近的博客的信息
     */
    private Cache<String, BlogPO> latestBlogs;
    /**
     * 缓存的最近的评论的信息
     */
    private Cache<String, BlogCommentPO> latestComments;

    /**
     * 上一次访问 all5SecStatistics 的时间戳
     */
    private long fSecLastVisitDate;
    /**
     * 切换 all5SecStatistics 的任务是否启动
     */
    private ScheduledFuture fSecTaskFuture;

    /**
     * 上一次刷新 全表缓存部分缓存的时间戳
     */
    private long tableCachedLastRefreshTs;
    /**
     * 上一次刷新 局部缓存部分缓存的时间戳
     */
    private long localCachedLastRefreshTs;
    /**
     * 上一次刷新 统计数据缓存部分缓存的时间戳
     */
    private long statsLastRefreshTs;
    /**
     * 上一次刷新 其他缓存部分缓存的时间戳
     */
    private long otherLastRefreshTs;

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
        loadStastics();
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
    public void clear(int clearFlag) {
        if (BizUtils.flagExists(clearFlag, REFRESH_ALL_TABLE_CACHED)) {
            blogTagsById.clear();
            blogTypesById.clear();
            linksById.clear();
            rolesById.clear();
            resourcesById.clear();
            interfsById.clear();
            createTypesById.clear();
        }

        if (BizUtils.flagExists(clearFlag, REFRESH_LOCAL_CACHED)) {
            digest2UploadedFiles.clear();
            roles2ResourceIds.clear();
            resource2Interfs.clear();
            blogIdUserInfo2Sense.clear();
            blogId2BlogEx.clear();
            requestIp2BlogVisitLog.clear();
            id2Blog.clear();
            blogId2TagIds.clear();
            blogIdPage2Comment.clear();
        }

        if (BizUtils.flagExists(clearFlag, REFRESH_STATISTICS_CACHED)) {
            todaysStatistics = new StatisticsInfo();
            recentlyStatistics.clear();
            recentlySumStatistics = null;
            sumStatistics = new StatisticsInfo();
            now5SecStatistics = new StatisticsInfo();
            all5SecStatistics.clear();
            monthFacet.clear();

            latestBlogs.clear();
            latestComments.clear();
        }

        if (BizUtils.flagExists(clearFlag, REFRESH_OTHER_CACHED)) {
            blog2NextFloorId.clear();
            blogFloor2NextCommentId.clear();
            forceOffLineMap.clear();
            blackList.clear();
        }
    }

    /**
     * 清理缓存的权限相关的数据
     *
     * @return void
     * @author Jerry.X.He
     * @date 6/17/2017 8:18 PM
     * @since 1.0
     */
    public void clearAuthorityCached() {
        roles2ResourceIds.clear();
        resource2Interfs.clear();
    }

    /**
     * 清理BlogEx相关的缓存的相关的数据
     *
     * @return void
     * @author Jerry.X.He
     * @date 6/17/2017 8:18 PM
     * @since 1.0
     */
    public void clearBlogExCached() {
        blogId2BlogEx.clear();
    }

    /**
     * 清理评分相关的缓存的相关的数据
     *
     * @return void
     * @author Jerry.X.He
     * @date 6/17/2017 8:18 PM
     * @since 1.0
     */
    public void clearScoreCached() {
        blogIdUserInfo2Sense.clear();
        blogId2BlogEx.clear();
    }

    /**
     * 清理上传文件相关的缓存的相关的数据
     *
     * @return void
     * @author Jerry.X.He
     * @date 6/17/2017 8:18 PM
     * @since 1.0
     */
    public void clearUploadedFiles() {
        digest2UploadedFiles.clear();
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
        clear(REFRESH_ALL_CONFIG);
        loadFullCachedResources();
        loadStastics();

        tableCachedLastRefreshTs = System.currentTimeMillis();
        localCachedLastRefreshTs = tableCachedLastRefreshTs;
        statsLastRefreshTs = tableCachedLastRefreshTs;
        otherLastRefreshTs = tableCachedLastRefreshTs;
    }

    /**
     * 刷新全部缓存在内存中的表的映射的数据
     *
     * @return void
     * @author Jerry.X.He
     * @date 6/17/2017 12:13 PM
     * @since 1.0
     */
    public void refreshTableCached() {
        clear(REFRESH_ALL_TABLE_CACHED);
        loadFullCachedResources();
        tableCachedLastRefreshTs = System.currentTimeMillis();
    }

    /**
     * 刷新局部缓存的数据
     *
     * @return void
     * @author Jerry.X.He
     * @date 6/17/2017 12:13 PM
     * @since 1.0
     */
    public void refreshLocalCached() {
        clear(REFRESH_LOCAL_CACHED);
        localCachedLastRefreshTs = System.currentTimeMillis();
    }

    /**
     * 刷新当前统计的的数据
     *
     * @return void
     * @author Jerry.X.He
     * @date 6/17/2017 12:13 PM
     * @since 1.0
     */
    public void refreshStatisticsInfo() {
        clear(REFRESH_STATISTICS_CACHED);
        loadStastics();
        statsLastRefreshTs = System.currentTimeMillis();
    }

    /**
     * 刷新其他缓存的数据
     * 一般对于业务没有影响的数据
     *
     * @return void
     * @author Jerry.X.He
     * @date 6/17/2017 12:13 PM
     * @since 1.0
     */
    public void refreshOthersCached() {
        clear(REFRESH_OTHER_CACHED);
        otherLastRefreshTs = System.currentTimeMillis();
    }

    /**
     * 获取上一次全表 refresh 的时间戳
     *
     * @return long
     * @author Jerry.X.He
     * @date 6/17/2017 4:28 PM
     * @since 1.0
     */
    public long tableCachedLastRefreshTs() {
        return tableCachedLastRefreshTs;
    }

    public long localCachedLastRefreshTs() {
        return localCachedLastRefreshTs;
    }

    public long statsLastRefreshTs() {
        return statsLastRefreshTs;
    }

    public long otherLastRefreshTs() {
        return otherLastRefreshTs;
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

    public BlogTypePO removeBlogType(String id) {
        return blogTypesById.remove(id);
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

    public BlogTagPO removeBlogTag(String id) {
        return blogTagsById.remove(id);
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

    public LinkPO removeLink(String id) {
        return linksById.remove(id);
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
     * 根据 name 查询 role
     *
     * @param name name
     * @return com.hx.blog_v2.domain.po.RolePO
     * @author Jerry.X.He
     * @date 6/27/2017 8:13 PM
     * @since 1.0
     */
    public RolePO roleByName(String name) {
        return BizUtils.findByLogisticId(rolesById, name);
    }

    public void putRole(RolePO po) {
        rolesById.put(po.getId(), po);
    }

    public RolePO removeRole(String id) {
        return rolesById.remove(id);
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

    public ResourcePO removeResource(String id) {
        return resourcesById.remove(id);
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

    public InterfPO removeInterf(String id) {
        return interfsById.remove(id);
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

    public BlogCreateTypePO removeBlogCreateType(String id) {
        return createTypesById.remove(id);
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

    public void putUploadedFile(String digest, UploadFilePO image) {
        digest2UploadedFiles.put(digest, image);
    }

    public UploadFilePO removeUploadedFile(String digest) {
        return digest2UploadedFiles.evict(digest);
    }

    public Cache<String, UploadFilePO> allUploadedFile() {
        return digest2UploadedFiles;
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

    public void putBlogSense(BlogSenseForm params, BlogSensePO po) {
        blogIdUserInfo2Sense.put(generateBlogSenseKey(params), po);
    }

    public BlogSensePO removeBlogSense(BlogSenseForm params) {
        return blogIdUserInfo2Sense.evict(generateBlogSenseKey(params));
    }

    public Cache<String, BlogSensePO> allBlogSense() {
        return blogIdUserInfo2Sense;
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

    public void putBlogEx(BlogExPO blogExPO) {
        blogId2BlogEx.put(blogExPO.getBlogId(), blogExPO);
    }

    public BlogExPO removeBlogEx(String blogId) {
        return blogId2BlogEx.evict(blogId);
    }

    public Cache<String, BlogExPO> allBlogEx() {
        return blogId2BlogEx;
    }

    /**
     * 获取参数相关的 BlogVisitLog
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

    public void putBlogVisitLog(BlogVisitLogForm params, BlogVisitLogPO po) {
        requestIp2BlogVisitLog.put(generateBlogVisitLogKey(params), po);
    }

    public BlogVisitLogPO removeBlogVisitLog(BlogVisitLogForm params) {
        return requestIp2BlogVisitLog.evict(generateBlogVisitLogKey(params));
    }

    public Cache<String, BlogVisitLogPO> allBlogVisitLog() {
        return requestIp2BlogVisitLog;
    }

    /**
     * 获取参数相关的 Blog
     *
     * @param id id
     * @return java.lang.Boolean
     * @author Jerry.X.He
     * @date 6/6/2017 8:32 PM
     * @since 1.0
     */
    public BlogPO getBlog(String id) {
        return id2Blog.get(id);
    }

    public void putBlog(String id, BlogPO po) {
        id2Blog.put(id, po);
    }

    public BlogPO removeBlog(String id) {
        return id2Blog.evict(id);
    }

    public Cache<String, BlogPO> allBlog() {
        return id2Blog;
    }

    /**
     * 获取参数相关的 tagIds
     *
     * @param blogId blogId
     * @return java.lang.Boolean
     * @author Jerry.X.He
     * @date 6/6/2017 8:32 PM
     * @since 1.0
     */
    public List<String> getTagIds(String blogId) {
        return blogId2TagIds.get(blogId);
    }

    public void putTagIds(String blogId, List<String> tagIds) {
        blogId2TagIds.put(blogId, tagIds);
    }

    public List<String> removeTagIds(String blogId) {
        return blogId2TagIds.evict(blogId);
    }

    public Cache<String, List<String>> allTagIds() {
        return blogId2TagIds;
    }

    /**
     * 获取参数相关的 评论列表
     *
     * @param blogId blogId
     * @param pageNo pageNo
     * @return java.lang.Boolean
     * @author Jerry.X.He
     * @date 6/6/2017 8:32 PM
     * @since 1.0
     */
    public List<List<CommentVO>> getComment(String blogId, String pageNo) {
        return blogIdPage2Comment.get(generateBlogIdPageNoKey(blogId, pageNo));
    }

    public void putComment(String blogId, String pageNo, List<List<CommentVO>> po) {
        blogIdPage2Comment.put(generateBlogIdPageNoKey(blogId, pageNo), po);
    }

    public List<List<CommentVO>> removeComment(String blogId, String pageNo) {
        return blogIdPage2Comment.evict(generateBlogIdPageNoKey(blogId, pageNo));
    }

    public Cache<String, List<List<CommentVO>>> allComment() {
        return blogIdPage2Comment;
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
    public LinkedList<StatisticsInfo> recentlyStatistics() {
        return recentlyStatistics;
    }

    /**
     * 获取最近7天的的统计数据的总和
     *
     * @return com.hx.blog_v2.domain.dto.StatisticsInfo
     * @author Jerry.X.He
     * @date 6/10/2017 9:03 PM
     * @since 1.0
     */
    public StatisticsInfo recentlySumStatistics() {
        if (recentlySumStatistics == null) {
            recentlySumStatistics = new StatisticsInfo();
            for (StatisticsInfo stats : recentlyStatistics) {
                recentlySumStatistics.merge(stats);
            }
        }

        return recentlySumStatistics;
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
                    constantsContext.realTimeChartTimeInterval, TimeUnit.SECONDS);
        }
        return all5SecStatistics;
    }

    /**
     * 获取 monthFacet
     *
     * @return void
     * @author Jerry.X.He
     * @date 6/24/2017 3:15 PM
     * @since 1.0
     */
    public Map<String, Integer> getMonthFacet() {
        return monthFacet;
    }

    /**
     * 更新 monthFacet
     *
     * @return void
     * @author Jerry.X.He
     * @date 6/24/2017 3:15 PM
     * @since 1.0
     */
    public Result updateBlogInMonthFacet(String month, boolean isAdd) {
        Integer cnt = monthFacet.get(month);
        if ((!isAdd) && (cnt == null)) {
            return ResultUtils.failed(" 没有这个月份的数据 ! ");
        }

        if (isAdd) {
            if (cnt == null) {
                cnt = 0;
            }
            monthFacet.put(month, cnt + 1);
        } else {
            monthFacet.put(month, cnt - 1);
        }
        return ResultUtils.success(isAdd ? cnt + 1 : cnt - 1);
    }

    /**
     * 获取周期统计 的访问信息
     *
     * @return java.util.Map<java.lang.String,java.lang.Integer>
     * @author Jerry.X.He
     * @date 6/25/2017 2:32 PM
     * @since 1.0
     */
    public Map<String, AtomicInteger> visitPerPeroid() {
        return visitPerPeroid;
    }

    /**
     * 获取周期统计 的异常信息
     *
     * @return java.util.Map<java.lang.String,java.lang.Integer>
     * @author Jerry.X.He
     * @date 6/25/2017 2:32 PM
     * @since 1.0
     */
    public Map<String, AtomicInteger> inputNotFormatPerPeroid() {
        return inputNotFormatPerPeroid;
    }

    /**
     * 获取黑名单的数据
     *
     * @return java.util.Map<java.lang.String,java.lang.Integer>
     * @author Jerry.X.He
     * @date 6/25/2017 2:32 PM
     * @since 1.0
     */
    public Cache<String, String> blackList() {
        return blackList;
    }

    /**
     * 获取所有的 局部缓存的 容量信息
     *
     * @return com.hx.json.JSONArray
     * @author Jerry.X.He
     * @date 6/17/2017 4:38 PM
     * @since 1.0
     */
    public JSONArray localCachedCapacities() {
        JSONArray result = new JSONArray();
        result.add(id2Blog.capacity());
        result.add(blogId2TagIds.capacity());
        result.add(blogIdPage2Comment.capacity());
        result.add(blogId2BlogEx.capacity());
        result.add(requestIp2BlogVisitLog.capacity());
        result.add(blogIdUserInfo2Sense.capacity());
        result.add(roles2ResourceIds.capacity());
        result.add(resource2Interfs.capacity());
        result.add(digest2UploadedFiles.capacity());
        result.add(888);
        return result;
    }

    /**
     * 获取所有的 局部缓存的 已经使用了的信息
     *
     * @return com.hx.json.JSONArray
     * @author Jerry.X.He
     * @date 6/17/2017 4:38 PM
     * @since 1.0
     */
    public JSONArray localCachedUsed() {
        JSONArray result = new JSONArray();
        result.add(id2Blog.size());
        result.add(blogId2TagIds.size());
        result.add(blogIdPage2Comment.size());
        result.add(blogId2BlogEx.size());
        result.add(requestIp2BlogVisitLog.size());
        result.add(blogIdUserInfo2Sense.size());
        result.add(roles2ResourceIds.size());
        result.add(resource2Interfs.size());
        result.add(digest2UploadedFiles.size());
        result.add(blackList.size());
        return result;
    }

    /**
     * 获取所有的 除了局部缓存的其他缓存 容量信息
     *
     * @return com.hx.json.JSONArray
     * @author Jerry.X.He
     * @date 6/17/2017 4:38 PM
     * @since 1.0
     */
    public JSONArray cachedCapacities() {
        JSONArray result = new JSONArray();
        result.add(blogTypesById.size());
        result.add(blogTagsById.size());
        result.add(linksById.size());
        result.add(rolesById.size());
        result.add(resourcesById.size());
        result.add(interfsById.size());
        result.add(createTypesById.size());
        return result;
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
     * 增加一条强制用户下线的信息
     *
     * @param userId userId
     * @param reason reason
     * @return void
     * @author Jerry.X.He
     * @date 6/17/2017 1:27 PM
     * @since 1.0
     */
    public void putForceOffLine(String userId, String reason) {
        forceOffLineMap.put(userId, reason, constantsContext.sesionTimeOut);
    }

    public String forceOffLine(String userId) {
        return forceOffLineMap.get(userId);
    }

    public void removeForceOffLine(String userId) {
        forceOffLineMap.evict(userId);
    }

    public Cache<String, String> allForceOffLine() {
        return forceOffLineMap;
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

    public Cache<String, List<String>> allResourceIdsByRoleIds() {
        return roles2ResourceIds;
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

    public Cache<String, List<String>> allInterfsByResourceIds() {
        return resource2Interfs;
    }

    /**
     * 获取最近的博客的记录
     *
     * @return com.hx.common.interf.cache.Cache<java.lang.String,com.hx.blog_v2.domain.po.BlogPO>
     * @author Jerry.X.He
     * @date 7/8/2017 10:34 AM
     * @since 1.0
     */
    public Cache<String, BlogPO> latestBlogs() {
        return latestBlogs;
    }

    /**
     * 加载最近的博客的记录
     *
     * @return com.hx.common.interf.cache.Cache<java.lang.String,com.hx.blog_v2.domain.po.BlogPO>
     * @author Jerry.X.He
     * @date 7/8/2017 10:34 AM
     * @since 1.0
     */
    public void loadLatestBlogs() {
        IQueryCriteria latestBlogCriteria = Criteria.and(Criteria.gte("id", 0), Criteria.eq("deleted", 0));
        LimitCriteria latestBlogLimit = Criteria.limit(0, Tools.optInt(constantsContext.allSystemConfig(), "latest.blog.cnt", 5));
        Result latestBlogResult = blogDao.list(latestBlogCriteria, Criteria.sortBy("created_at", SortByCriteria.DESC), latestBlogLimit);
        if (latestBlogResult.isSuccess()) {
            List<BlogPO> latestBlogsFromDb = (List<BlogPO>) latestBlogResult.getData();
            for (int i = latestBlogsFromDb.size() - 1; i >= 0; i--) {
                BlogPO po = latestBlogsFromDb.get(i);
                latestBlogs.put(po.getId(), po);
            }
        } else {
            Log.err(" error while load latest blog ");
        }
    }

    public void refreshLatestBlogs() {
        latestBlogs.clear();
        loadLatestBlogs();
    }

    /**
     * 获取最近的评论的记录
     *
     * @return com.hx.common.interf.cache.Cache<java.lang.String,com.hx.blog_v2.domain.po.BlogPO>
     * @author Jerry.X.He
     * @date 7/8/2017 10:34 AM
     * @since 1.0
     */
    public Cache<String, BlogCommentPO> latestComments() {
        return latestComments;
    }

    /**
     * 加载最近的评论的记录
     *
     * @return com.hx.common.interf.cache.Cache<java.lang.String,com.hx.blog_v2.domain.po.BlogPO>
     * @author Jerry.X.He
     * @date 7/8/2017 10:34 AM
     * @since 1.0
     */
    public void loadLatestComments() {
        IQueryCriteria latestCommentCriteria = Criteria.eq("deleted", 0);
        LimitCriteria latestCommentLimit = Criteria.limit(0, Tools.optInt(constantsContext.allSystemConfig(), "latest.blog.cnt", 5));
        Result latestCommentResult = blogCommentDao.list(latestCommentCriteria, Criteria.sortBy("created_at", SortByCriteria.DESC), latestCommentLimit);
        if (! latestCommentResult.isSuccess()) {
            Log.err(" error while load latest blog ");
        }

        List<BlogCommentPO> latestCommentsFromDb = (List<BlogCommentPO>) latestCommentResult.getData();
        for (int i = latestCommentsFromDb.size() - 1; i >= 0; i--) {
            BlogCommentPO po = latestCommentsFromDb.get(i);
            latestComments.put(po.getId(), po);
        }
    }

    public void refreshLatestComments() {
        latestComments.clear();
        loadLatestComments();
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
        blog2NextFloorId = new LRUMCache<>(constantsContext.maxCachedBlog2FloorId, false);
        blogFloor2NextCommentId = new LRUMCache<>(constantsContext.maxCachedBlogFloor2CommentId, false);
        forceOffLineMap = new UniverseCache<>(true);
        blackList = new UniverseCache<>(false);
        latestBlogs = new FIFOMCache<>(constantsContext.maxLatestBlog, false);
        latestComments = new FIFOMCache<>(constantsContext.maxLatestComment, false);

        digest2UploadedFiles = new LRUMCache<>(constantsContext.maxCachedUploadedImage, false);
        roles2ResourceIds = new LRUMCache<>(constantsContext.maxRoleIds2ResourceIds, false);
        resource2Interfs = new LRUMCache<>(constantsContext.maxRoleIds2ResourceIds, false);
        blogIdUserInfo2Sense = new LRUMCache<>(constantsContext.maxSense2Clicked, false);
        blogId2BlogEx = new LRUMCache<>(constantsContext.maxBlogId2BlogEx, false);
        requestIp2BlogVisitLog = new LRUMCache<>(constantsContext.maxRequestIp2BlogVisitLog, false);
        id2Blog = new LRUMCache<>(constantsContext.maxId2Blog, false);
        blogId2TagIds = new LRUMCache<>(constantsContext.maxBlogId2TagIds, false);
        blogIdPage2Comment = new LRUMCache<>(constantsContext.maxBlogIdPageNo2Comment, false);

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
    private void loadStastics() {
        List<StatisticsInfo> allDayStatisInfo = BizUtils.collectRecentlyStatisticsInfo(jdbcTemplate,
                constantsContext.maxCacheStatisticsDays);
        if (!Tools.isEmpty(allDayStatisInfo)) {
            todaysStatistics = allDayStatisInfo.remove(allDayStatisInfo.size() - 1);
        }
        recentlyStatistics.addAll(allDayStatisInfo);
        sumStatistics = BizUtils.collectSumStatisticsInfo(jdbcTemplate);

        Map<String, Integer> allMonthFacet = BizUtils.collectMonthFacet(jdbcTemplate);
        monthFacet.putAll(allMonthFacet);

        loadLatestBlogs();
        loadLatestComments();
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
        Tools.scheduleAtFixedRate(new SwitchStatisInfoRunnable(), msOffToNextDawn, BlogConstants.ONE_DAY, TimeUnit.MILLISECONDS);
        Tools.scheduleAtFixedRate(new ClearBlankListRunnable(), msOffToNextDawn, BlogConstants.ONE_DAY, TimeUnit.MILLISECONDS);
        Tools.scheduleAtFixedRate(new ClearVisitPeroidInfoRunnable(), constantsContext.visitCntValidatePeriod,
                constantsContext.visitCntValidatePeriod, TimeUnit.SECONDS);
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
     * 生成 blogId 和 pageNo 组合的key
     *
     * @param blogId blogId
     * @param pageNo pageNo
     * @return java.lang.String
     * @author Jerry.X.He
     * @date 7/8/2017 12:02 PM
     * @since 1.0
     */
    private String generateBlogIdPageNoKey(String blogId, String pageNo) {
        return blogId + BLOG_SENSE_KEY_SEP + pageNo;
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
     * 辅助key
     */
    private static final String RE_SORT_RES_TREE_THIS_STR = "__this";

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
        if (Tools.isEmpty(resoures)) {
            return Collections.emptyList();
        }

        final String childStr = "childs";
        JSONObject root = TreeUtils.generateTree(resoures, new TreeInfoExtractor<ResourcePO>() {
            @Override
            public void extract(ResourcePO bean, JSONObject obj) {
                obj.put(RE_SORT_RES_TREE_THIS_STR, bean);
            }
        }, childStr, constantsContext.resourceRootParentId);

        List<ResourcePO> result = new ArrayList<>(resoures.size());
        collectResultByLevel(root, childStr, result);
        return result;
    }

    /**
     * 从高级到低级依次收集 PO 到结果集合中
     *
     * @param node     node
     * @param childStr childStr
     * @param result   result
     * @return void
     * @author Jerry.X.He
     * @date 6/14/2017 7:47 PM
     * @since 1.0
     */
    private void collectResultByLevel(JSONObject node, String childStr, List<ResourcePO> result) {
        ResourcePO po = (ResourcePO) node.get(RE_SORT_RES_TREE_THIS_STR);
        result.add(po);
        JSONObject childs = node.getJSONObject(childStr);
        for (Map.Entry<String, Object> entry : childs.entrySet()) {
            collectResultByLevel((JSONObject) entry.getValue(), childStr, result);
        }
    }

    /**
     * 一天末尾到第二天的数据的切换
     *
     * @return void
     * @author Jerry.X.He
     * @date 6/10/2017 9:06 PM
     * @since 1.0
     */
    private void switchStatistics() {
        // -1 去掉今天占用的容量[总共需要缓存的天数的数据-1]
        if (recentlyStatistics.size() > (constantsContext.maxCacheStatisticsDays - 1)) {
            recentlyStatistics.poll();
        }

        recentlyStatistics.add(todaysStatistics);
        sumStatistics.merge(todaysStatistics);
        recentlySumStatistics = null;
        todaysStatistics = new StatisticsInfo();
    }

    /**
     * 一天末尾到第二天的数据的切换
     *
     * @return void
     * @author Jerry.X.He
     * @date 6/10/2017 9:06 PM
     * @since 1.0
     */
    private void switch5SecStatistics() {
        if (all5SecStatistics.size() > constantsContext.maxRealTimeCacheStasticsTimes) {
            all5SecStatistics.poll();
        }
        all5SecStatistics.add(now5SecStatistics);
        now5SecStatistics = new StatisticsInfo();
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
            if ((System.currentTimeMillis() - fSecLastVisitDate) >
                    ((constantsContext.realTimeChartTimeInterval + 1) << 10)) {
                all5SecStatistics.clear();
                fSecTaskFuture.cancel(false);
                fSecTaskFuture = null;
                return;
            }

            CacheContext.this.switch5SecStatistics();
        }
    }

    /**
     * 清理 RobotInterceptor 周期统计的信息
     *
     * @author Jerry.X.He <970655147@qq.com>
     * @version 1.0
     * @date 6/25/2017 2:31 PM
     */
    private class ClearVisitPeroidInfoRunnable implements Runnable {
        @Override
        public void run() {
            visitPerPeroid.clear();
            inputNotFormatPerPeroid.clear();
        }
    }

    /**
     * 清理 黑名单中的信息
     *
     * @author Jerry.X.He <970655147@qq.com>
     * @version 1.0
     * @date 6/25/2017 2:31 PM
     */
    private class ClearBlankListRunnable implements Runnable {
        @Override
        public void run() {
            blackList.clear();
        }
    }

}
