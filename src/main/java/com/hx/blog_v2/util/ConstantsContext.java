package com.hx.blog_v2.util;

import com.hx.blog_v2.dao.interf.SystemConfigDao;
import com.hx.blog_v2.domain.dto.ConfigType;
import com.hx.blog_v2.domain.dto.ImageType;
import com.hx.blog_v2.domain.po.SystemConfigPO;
import com.hx.log.util.Constants;
import com.hx.log.util.Log;
import com.hx.log.util.Tools;
import com.hx.mongo.criteria.Criteria;
import com.hx.mongo.criteria.SortByCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * 维护相关常量
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/14/2017 7:58 PM
 */
@Component
public class ConstantsContext {

    /**
     * 维护系统配置
     */
    private Map<String, Object> systemConfig = new LinkedHashMap<>();
    /**
     * 维护规则配置
     */
    private Map<String, Object> ruleConfig = new LinkedHashMap<>();
    /**
     * 维护前台首页相关配置
     */
    private Map<String, Object> frontendIdxConfig = new LinkedHashMap<>();

    @Autowired
    private SystemConfigDao systemConfigDao;

    /**
     * 初始化 ConstantsContext
     *
     * @return void
     * @author Jerry.X.He
     * @date 5/21/2017 6:36 PM
     * @since 1.0
     */
    @PostConstruct
    public void init() {
        loadFullCachedConfigs();
        refreshConfigs();
    }

    /**
     * 相关特殊的具体的常量
     */
    public String contextBlogId;
    public String adviceBlogId;
    public String selfProfileBlogId;
    public String resourceRootParentId;
    public int resourceLeaveLevel;
    public String replyCommentPrefix;
    public String replyCommentSuffix;
    public String upPriseSense;
    public String viewSense;
    public int reSortStart;
    public int reSortOffset;

    public String defaultCharset;
    public int maxCachedBlog2FloorId;
    public int maxCachedBlogFloor2CommentId;
    public int maxCachedUploadedImage;
    public int maxRoleIds2ResourceIds;
    public int maxSense2Clicked;
    public int maxBlogId2BlogEx;
    public int maxRequestIp2BlogVisitLog;

    public String imgTypeImgShow = ImageType.IMAGE_SHOW.getType();
    public String imgTypeHeadImg = ImageType.HEAD_IMG.getType();
    public final Set<String> supportedImageTypes = Tools.asSet(imgTypeImgShow, imgTypeHeadImg);
    public String imageUrlPrefix = "http://localhost/files/";
    public int pwdSaltNums;
    public int checkCodeLength;
    public int checkCodeImgWidth;
    public int checkCodeImgHeight;
    public Color checkCodeImgBgColor = Color.WHITE;
    public Font checkCodeImgFont = new Font("微软雅黑", Font.ITALIC, 40);
    public String checkCodeCandidatesStr = "123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTVUWXYZ";
    public List<Character> checkCodeCandidates = new ArrayList<>(checkCodeCandidatesStr.length());
    public int checkCodeMinInterference = 30;
    public int checkCodeInterferenceOff = 10;

    public int maxCacheStatisticsDays;
    public int realTimeChartTimeInterval;
    public int maxRealTimeCacheStasticsTimes;
    public String requestLogUriToIgnore;

    /**
     * 获取所有的 SystemConfig
     *
     * @return java.util.Map<java.lang.String,com.hx.blog_v2.domain.po.BlogTypePO>
     * @author Jerry.X.He
     * @date 5/21/2017 6:13 PM
     * @since 1.0
     */
    public Map<String, Object> allSystemConfig() {
        return systemConfig;
    }

    public String systemConfig(String key) {
        return Tools.optString(systemConfig, key, null);
    }

    public void putSystemConfig(String key, String value) {
        systemConfig.put(key, value);
    }

    /**
     * 获取所有的 RuleConfig
     *
     * @return java.util.Map<java.lang.String,com.hx.blog_v2.domain.po.BlogTypePO>
     * @author Jerry.X.He
     * @date 5/21/2017 6:13 PM
     * @since 1.0
     */
    public Map<String, Object> allRuleConfig() {
        return ruleConfig;
    }

    public String ruleConfig(String key) {
        return Tools.optString(ruleConfig, key, null);
    }

    public void putRuleConfig(String key, String value) {
        ruleConfig.put(key, value);
    }

    /**
     * 获取所有的 FrontendIdxConfig
     *
     * @return java.util.Map<java.lang.String,com.hx.blog_v2.domain.po.BlogTypePO>
     * @author Jerry.X.He
     * @date 5/21/2017 6:13 PM
     * @since 1.0
     */
    public Map<String, Object> allFrontendIdxConfig() {
        return frontendIdxConfig;
    }

    public String frontendIdxConfig(String key) {
        return Tools.optString(frontendIdxConfig, key, null);
    }

    public void putFrontendIdxConfig(String key, String value) {
        frontendIdxConfig.put(key, value);
    }

    // -------------------- 辅助方法 --------------------------

    /**
     * 加载 全部缓存的数据
     *
     * @return void
     * @author Jerry.X.He
     * @date 6/3/2017 3:04 PM
     * @since 1.0
     */
    private void loadFullCachedConfigs() {
        try {
            List<SystemConfigPO> configs = systemConfigDao.findMany(
                    Criteria.and(Criteria.eq("deleted", "0")).add(Criteria.eq("enable", "1")),
                    Criteria.limitNothing(), Criteria.sortBy("sort", SortByCriteria.ASC), BlogConstants.LOAD_ALL_CONFIG);
            for (SystemConfigPO po : configs) {
                ConfigType type = ConfigType.of(po.getType());
                switch (type) {
                    case SYSTEM:
                        systemConfig.put(po.getName(), po.getValue());
                        break;
                    case RULE:
                        ruleConfig.put(po.getName(), po.getValue());
                        break;
                    case FRONT_INDEX_CONFIG:
                        frontendIdxConfig.put(po.getName(), po.getValue());
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.err("error while load cached's configs !");
        }
    }

    private void refreshConfigs() {
        contextBlogId = Tools.optString(systemConfig, BlogConstants.CONTEXT_BLOG_ID, "-1");
        adviceBlogId = Tools.optString(systemConfig, BlogConstants.ADVICE_BLOG_ID, "-2");
        selfProfileBlogId = Tools.optString(systemConfig, BlogConstants.SELF_PROFILE_BLOG_ID, "-3");
        resourceRootParentId = Tools.optString(systemConfig, BlogConstants.RESOURCE_ROOT_PARENT_ID, "-1");
        resourceLeaveLevel = Tools.optInt(systemConfig, BlogConstants.RESOURCE_LEAVE_LEVEL, 2);
        replyCommentPrefix = Tools.optString(systemConfig, BlogConstants.REPLY_COMMENT_PREFIX, "[reply]");
        replyCommentSuffix = Tools.optString(systemConfig, BlogConstants.REPLY_COMMENT_SUFFIX, "[/reply]");
        upPriseSense = Tools.optString(systemConfig, BlogConstants.UP_PRISE_SENSE, "good");
        viewSense = Tools.optString(systemConfig, BlogConstants.VIEW_SENSE, "view");
        reSortStart = Tools.optInt(systemConfig, BlogConstants.RE_SORT_START, 0);
        reSortOffset = Tools.optInt(systemConfig, BlogConstants.RE_SORT_OFFSET, 10);

        defaultCharset = Tools.optString(systemConfig, BlogConstants.DEFAULT_CHARSET, Constants.DEFAULT_CHARSET);
        maxCachedBlog2FloorId = Tools.optInt(systemConfig, BlogConstants.MAX_CACHED_BLOG_2_FLOOR_ID, 100);
        maxCachedBlogFloor2CommentId = Tools.optInt(systemConfig, BlogConstants.MAX_CACHED_BLOG_FLOOR_2_COMMENT_ID, 1000);
        maxCachedUploadedImage = Tools.optInt(systemConfig, BlogConstants.MAX_CACHED_UPLOADED_IMAGE, 100);
        maxRoleIds2ResourceIds = Tools.optInt(systemConfig, BlogConstants.MAX_ROLE_IDS_2_RESOURCE_IDS, 20);
        maxSense2Clicked = Tools.optInt(systemConfig, BlogConstants.MAX_SENSE_2_CLICKED, 1000);
        maxBlogId2BlogEx = Tools.optInt(systemConfig, BlogConstants.MAX_BLOG_ID_2_BLOG_EX, 1000);
        maxRequestIp2BlogVisitLog = Tools.optInt(systemConfig, BlogConstants.MAX_REQUEST_IP_2_BLOG_VISIT_LOG, 1000);

        imageUrlPrefix = Tools.optString(systemConfig, BlogConstants.IMAGE_URL_PREFIX, "http://localhost/files/");
        pwdSaltNums = Tools.optInt(systemConfig, BlogConstants.PWD_SALT_NUMS, 8);
        checkCodeLength = Tools.optInt(systemConfig, BlogConstants.CHECK_CODE_LENGTH, 4);
        checkCodeImgWidth = Tools.optInt(systemConfig, BlogConstants.CHECK_CODE_IMG_WIDTH, 160);
        checkCodeImgHeight = Tools.optInt(systemConfig, BlogConstants.CHECK_CODE_IMG_HEIGHT, 80);
        checkCodeCandidatesStr = Tools.optString(systemConfig, BlogConstants.CHECK_CODE_CANDIDATES_STR, "123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTVUWXYZ");
        checkCodeMinInterference = Tools.optInt(systemConfig, BlogConstants.CHECK_CODE_MIN_INTERFERENCE, 30);
        checkCodeInterferenceOff = Tools.optInt(systemConfig, BlogConstants.CHECK_CODE_INTERFERENCE_OFF, 10);
        for (int i = 0, len = checkCodeCandidatesStr.length(); i < len; i++) {
            checkCodeCandidates.add(checkCodeCandidatesStr.charAt(i));
        }

        maxCacheStatisticsDays = Tools.optInt(systemConfig, BlogConstants.MAX_CACHE_STATISTICS_DAYS, 7);
        realTimeChartTimeInterval = Tools.optInt(systemConfig, BlogConstants.REAL_TIME_CHART_TIME_INTERVAL, 5);
        maxRealTimeCacheStasticsTimes = Tools.optInt(systemConfig, BlogConstants.MAX_REAL_TIME_CACHE_STASTICS_TIMES, 12);
        requestLogUriToIgnore = Tools.optString(systemConfig, BlogConstants.REQUEST_LOG_URI_TO_IGNORE, "/index/index;/image/headImgList;/index/latest;");
    }

}
