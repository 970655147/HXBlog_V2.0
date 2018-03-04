package com.hx.blog_v2.service.index;

import com.hx.blog_v2.context.CacheContext;
import com.hx.blog_v2.context.ConstantsContext;
import com.hx.blog_v2.context.WebContext;
import com.hx.blog_v2.dao.interf.BlogDao;
import com.hx.blog_v2.dao.interf.RltRoleResourceDao;
import com.hx.blog_v2.domain.POVOTransferUtils;
import com.hx.blog_v2.domain.common.system.SessionUser;
import com.hx.blog_v2.domain.common.common.StringStringPair;
import com.hx.blog_v2.domain.extractor.ResourceTreeInfoExtractor;
import com.hx.blog_v2.domain.form.common.BeanIdForm;
import com.hx.blog_v2.domain.form.common.BeanIdsForm;
import com.hx.blog_v2.domain.po.blog.BlogCommentPO;
import com.hx.blog_v2.domain.po.blog.BlogPO;
import com.hx.blog_v2.domain.po.resources.ResourcePO;
import com.hx.blog_v2.domain.vo.blog.BlogVO;
import com.hx.blog_v2.domain.vo.blog.CommentVO;
import com.hx.blog_v2.domain.vo.others.FacetByMonthVO;
import com.hx.blog_v2.domain.vo.resources.ResourceVO;
import com.hx.blog_v2.service.blog.BlogServiceImpl;
import com.hx.blog_v2.service.interf.BaseServiceImpl;
import com.hx.blog_v2.service.interf.index.IndexService;
import com.hx.blog_v2.service.interf.front_resources.LinkService;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.ResultUtils;
import com.hx.common.interf.cache.Cache;
import com.hx.common.interf.cache.CacheEntryFacade;
import com.hx.common.interf.common.Result;
import com.hx.json.JSONArray;
import com.hx.json.JSONObject;
import com.hx.log.alogrithm.tree.TreeUtils;
import com.hx.log.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * IndexServiceImpl
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 11:47 AM
 */
@Service
public class IndexServiceImpl extends BaseServiceImpl<Object> implements IndexService {

    @Autowired
    private LinkService linkService;
    @Autowired
    private BlogServiceImpl blogService;
    @Autowired
    private BlogDao blogDao;
    @Autowired
    private RltRoleResourceDao rltRoleResourceDao;
    @Autowired
    private CacheContext cacheContext;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ConstantsContext constantsContext;

    @Override
    public Result index() {
        List<BlogVO> hotBlogs = collectHotBlogs();
        List<CommentVO> latestComments = collectLatestComments();
        Result getContextBlogResult = blogDao.get(new BeanIdForm(constantsContext.contextBlogId));
        if (!getContextBlogResult.isSuccess()) {
            getContextBlogResult.setData(new BlogPO());
        }
        BlogPO contextBlogPO = (BlogPO) getContextBlogResult.getData();
        BlogVO contextBlog = POVOTransferUtils.blogPO2BlogVO(contextBlogPO);

        Integer todayVisited = cacheContext.todaysStatistics().getDayFlushViewCnt();
        Integer totalVisited = cacheContext.sumStatistics().getDayFlushViewCnt();
        encapBlogVo(hotBlogs);
        blogService.encapSense(contextBlog);
        encapBlogVo(contextBlog);
        List<FacetByMonthVO> facetByMonth = new ArrayList<>(cacheContext.getMonthFacet().size());
        for (Map.Entry<String, Integer> entry : cacheContext.getMonthFacet().entrySet()) {
            facetByMonth.add(new FacetByMonthVO(entry.getKey(), entry.getValue()));
        }

        JSONObject data = new JSONObject();
        data.put("title", constantsContext.frontIdxPageTitle);
        data.put("subTitle", constantsContext.frontIdxPageSubTitle);
        data.put("tags", tags2List(cacheContext.allBlogTags()));
        data.put("types", tags2List(cacheContext.allBlogTypes()));
        data.put("links", linkService.list().getData());

        data.put("hotBlogs", hotBlogs);
        data.put("latestComments", latestComments);
        data.put("facetByMonth", facetByMonth);
        data.put("goodSensed", contextBlog.getGoodSensed() != 0);
        data.put("goodCnt", contextBlog.getGoodTotalCnt());
        data.put("todayVisited", todayVisited);
        data.put("totalVisited", todayVisited + totalVisited);
        // 本周, 本月, 合计

        return ResultUtils.success(data);
    }

    @Override
    public Result latest() {
        BlogVO recommendBlog = collectRecommendBlogs();
        List<BlogVO> latestBlogs = collectLatestBlogs();
        if (recommendBlog != null) {
            encapBlogVo(recommendBlog);
        }
        encapBlogVo(latestBlogs);

        JSONObject data = new JSONObject();
        data.put("recommend", recommendBlog);
        data.put("latestBlogs", latestBlogs);

        return ResultUtils.success(data);
    }

    @Override
    public Result adminMenus() {
        SessionUser user = (SessionUser) WebContext.getAttributeFromSession(BlogConstants.SESSION_USER);
        Result getResourceIdsResult = rltRoleResourceDao.getResourceIdsByRoleIds(new BeanIdsForm(user.getRoleIds()));
        if (!getResourceIdsResult.isSuccess()) {
            return getResourceIdsResult;
        }

        List<String> resourceIds = (List<String>) getResourceIdsResult.getData();
        Map<String, ResourcePO> resourcesById = cacheContext.allResources();
        Set<String> needToGet = collectAllParentIds(resourceIds, resourcesById);
        List<ResourceVO> resources = new ArrayList<>(resourcesById.size());
        for (Map.Entry<String, ResourcePO> entry : resourcesById.entrySet()) {
            if (needToGet.contains(entry.getKey()) && (entry.getValue().getEnable() == 1)) {
                resources.add(POVOTransferUtils.resourcePO2ResourceVO(entry.getValue()));
            }
        }

        final String childStr = "childs";
        JSONObject root = TreeUtils.generateTree(resources, new ResourceTreeInfoExtractor(), childStr,
                constantsContext.resourceRootParentId);
        TreeUtils.childArrayify(root, childStr);
        return ResultUtils.success(root);
    }

    @Override
    public Result adminStatistics() {
        SessionUser user = (SessionUser) WebContext.getAttributeFromSession(BlogConstants.SESSION_USER);
        JSONObject data = new JSONObject()
                .element("loginInfo", new JSONObject().element("loginIp", user.getRequestIp())
                        .element("loginDate", user.getLoginDate()).element("loginAddr", user.getIpAddr()))
                .element("todayStats", cacheContext.todaysStatistics())
                .element("recentlyStats", cacheContext.recentlySumStatistics())
                .element("sumStats", cacheContext.sumStatistics());

        return ResultUtils.success(data);
    }

    // -------------------- 辅助方法 --------------------------

    /**
     * 把给定的Map集合转换为List
     *
     * @param mapById mapById
     * @return java.util.List<com.hx.blog_v2.domain.po.blog.BlogTagPO>
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
     * @param list list
     * @return void
     * @author Jerry.X.He
     * @date 5/27/2017 11:26 PM
     * @since 1.0
     */
    Result encapBlogVo(List<BlogVO> list) {
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
    Result encapBlogVo(BlogVO vo) {
        Result result = blogService.encapBlogEx(vo);
        if (!result.isSuccess()) {
            return result;
        }

        result = blogService.encapTypeTagInfo(vo);
        if (!result.isSuccess()) {
            return result;
        }
        return ResultUtils.success(vo);
    }

    /**
     * 根据给定的叶节点的资源, 收集其所有的上层节点
     *
     * @param resourceIds   resourceIds
     * @param resourcesById resourcesById
     * @return java.util.Set<java.lang.String>
     * @author Jerry.X.He
     * @date 6/3/2017 3:41 PM
     * @since 1.0
     */
    private Set<String> collectAllParentIds(List<String> resourceIds, Map<String, ResourcePO> resourcesById) {
        Set<String> needToGet = new HashSet<>(Tools.estimateMapSize(resourcesById.size()));
        needToGet.addAll(resourceIds);
        List<String> parentIds = new ArrayList<>(resourcesById.size());
        for (Map.Entry<String, ResourcePO> entry : resourcesById.entrySet()) {
            ResourcePO po = entry.getValue();
            if (constantsContext.resourceRootParentId.equals(po.getParentId())) {
                continue;
            }

            if (resourceIds.contains(po.getId())) {
                parentIds.add(po.getParentId());
            }
        }
        if (!parentIds.isEmpty()) {
            needToGet.addAll(collectAllParentIds(parentIds, resourcesById));
        }

        return needToGet;
    }

    /**
     * 获取最近添加的博客
     *
     * @return java.util.List<com.hx.blog_v2.domain.po.blog.BlogPO>
     * @author Jerry.X.He
     * @date 7/8/2017 10:57 AM
     * @since 1.0
     */
    private List<BlogVO> collectLatestBlogs() {
        Cache<String, BlogPO> latestBlogs = cacheContext.latestBlogs();
        List<BlogVO> result = new ArrayList<>(latestBlogs.size());
        for (String key : latestBlogs.keys()) {
            BlogPO po = latestBlogs.get(key);
            if (po.getId().compareTo("0") > 0) {
                BlogVO vo = POVOTransferUtils.blogPO2BlogVO(po);
                result.add(vo);
            }
        }

        return result;
    }

    /**
     * 获取最近添加的评论
     *
     * @return java.util.List<com.hx.blog_v2.domain.po.blog.BlogPO>
     * @author Jerry.X.He
     * @date 7/8/2017 10:57 AM
     * @since 1.0
     */
    private List<CommentVO> collectLatestComments() {
        Cache<String, BlogCommentPO> latestComments = cacheContext.latestComments();
        List<CommentVO> result = new ArrayList<>(latestComments.size());
        for (String key : latestComments.keys()) {
            BlogCommentPO po = latestComments.get(key);
            CommentVO vo = POVOTransferUtils.blogCommentPO2CommentVO(po);
            result.add(vo);
        }

        return result;
    }

    /**
     * 收集 热帖
     *
     * @return java.util.List<com.hx.blog_v2.domain.vo.blog.BlogVO>
     * @author Jerry.X.He
     * @date 7/8/2017 1:52 PM
     * @since 1.0
     */
    private List<BlogVO> collectHotBlogs() {
        Cache<String, BlogPO> allBlogs = cacheContext.allBlog();
        PriorityQueue<StringStringPair> hotBlogKeys = new PriorityQueue<>(constantsContext.maxHotBlogCnt + 1);
        for (String key : allBlogs.keys()) {
            CacheEntryFacade<String, BlogPO> entry = allBlogs.getEntry(key);
            if (entry.value().getId().compareTo("0") > 0) {
                hotBlogKeys.add(new StringStringPair(String.valueOf(entry.accessCount()), key));
                if (hotBlogKeys.size() > constantsContext.maxHotBlogCnt) {
                    hotBlogKeys.poll();
                }
            }
        }

        List<BlogVO> result = new ArrayList<>(hotBlogKeys.size());
        for (StringStringPair pair : hotBlogKeys) {
            BlogPO po = cacheContext.getBlog(pair.getRight());
            result.add(POVOTransferUtils.blogPO2BlogVO(po));
        }
        return result;
    }

    /**
     * 收集 推荐的博客
     *
     * @return java.util.List<com.hx.blog_v2.domain.vo.blog.BlogVO>
     * @author Jerry.X.He
     * @date 7/8/2017 1:52 PM
     * @since 1.0
     */
    private BlogVO collectRecommendBlogs() {
        Cache<String, BlogPO> allBlogs = cacheContext.allBlog();
        String maxAccessBlogId = null;
        long maxAccessCnt = -1;
        for (String key : allBlogs.keys()) {
            CacheEntryFacade<String, BlogPO> entry = allBlogs.getEntry(key);
            if ((entry.value().getId().compareTo("0") > 0)
                    && (entry.accessCount() > maxAccessCnt)) {
                maxAccessCnt = entry.accessCount();
                maxAccessBlogId = entry.key();
            }
        }

        if (Tools.isEmpty(maxAccessBlogId)) {
            return null;
        }
        BlogPO po = cacheContext.getBlog(maxAccessBlogId);
        return POVOTransferUtils.blogPO2BlogVO(po);
    }


}
