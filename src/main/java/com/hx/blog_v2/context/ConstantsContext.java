package com.hx.blog_v2.context;

import com.hx.blog_v2.dao.interf.SystemConfigDao;
import com.hx.blog_v2.domain.dto.ConfigType;
import com.hx.blog_v2.domain.dto.ImageType;
import com.hx.blog_v2.domain.dto.SenseType;
import com.hx.blog_v2.domain.po.SystemConfigPO;
import com.hx.blog_v2.util.BizUtils;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.json.JSONArray;
import com.hx.json.JSONObject;
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
     * 刷新系统配置的的标志位
     */
    public static final int REFRESH_SYSTEM_CACHED = 0x0001;
    /**
     * 刷新规则配置的标志位
     */
    public static final int REFRESH_RULE_CACHED = 0x0010;
    /**
     * 刷新前台配置数据的的标志位
     */
    public static final int REFRESH_FRONT_IDX__CACHED = 0x0100;
    /**
     * 刷新所有的缓存的标志位
     */
    public static final int REFRESH_ALL_CONFIG = REFRESH_SYSTEM_CACHED | REFRESH_RULE_CACHED
            | REFRESH_FRONT_IDX__CACHED;

    /**
     * 敏感标签的默认配置
     */
    public static final JSONArray DEFAULT_ALLOW_TAG_SENSETIVE_TAGS = new JSONArray().element("script").element("iframe").element("el-*");
    /**
     * 敏感属性 -> 关键字 的默认配置
     */
    public static final JSONObject DEFAULT_SENSETIVE_TAG_2_ATTR = new JSONObject()
            .element("a", new JSONObject().element("href", new JSONArray().element("javascript")))
            .element("iframe", new JSONObject().element("src", new JSONArray().element("")));
    /**
     * 敏感属性的默认配置
     */
    public static final JSONArray DEFAULT_ALLOW_TAG_SENSETIVE_ATTRS = new JSONArray().element("on*");
    /**
     * 标签转义配置
     */
    public static final JSONObject DEFAULT_TRANSFER_TAG_NEED_FORMAT = new JSONObject()
            .element("<", "&lt;").element(">", "&gt;").element("&", "&amp;");
    /**
     * 标签转义配置
     */
    public static final JSONArray DEFAULT_PARAMS_NEED_TO_CUT = new JSONArray()
            .element("/admin/blog/add").element("/admin/blog/update").element("/comment/add");

    @Autowired
    private SystemConfigDao systemConfigDao;

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

    /**
     * 上一次刷新系统配置的缓存的时间戳
     */
    private long systemConfigLastRefreshTs;
    /**
     * 上一次刷新规则配置的缓存的时间戳
     */
    private long ruleConfigLastRefreshTs;
    /**
     * 上一次刷新首页配置的缓存的时间戳
     */
    private long frontIdxConfigLastRefreshTs;

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
    public int reSortStart;
    public int reSortOffset;

    public String defaultCharset;
    public int maxCachedBlog2FloorId;
    public int maxCachedBlogFloor2CommentId;
    public int sesionTimeOut;

    public int maxCachedUploadedImage;
    public int maxRoleIds2ResourceIds;
    public int maxSense2Clicked;
    public int maxBlogId2BlogEx;
    public int maxRequestIp2BlogVisitLog;

    public String imgTypeImgShow = ImageType.IMAGE_SHOW.code();
    public String imgTypeHeadImg = ImageType.HEAD_IMG.code();
    public String imageUrlPrefix;
    public String contextUrlPrefix;
    public String contextSystemUserId;
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
    public long tokenRefreshInterval;

    /**
     * 首页的标题配置
     */
    public String frontIdxPageTitle;
    public String frontIdxPageSubTitle;
    /**
     * 默认的用户的 title, 角色
     */
    public String guestDummyId;
    public String guestTitle;
    public String guestRoles;
    /**
     * 预处理 允许标签的场景
     * 博客, 评论内容 需要的相关配置
     */
    public List<String> allowTagSensetiveTags;
    public Map<String, Map<String, List<String>>> allowTagSensetiveTag2Attr;
    public List<String> allowTagSensetiveAttrs;
    /**
     * 预处理 不允许标签的内容需要的相关配置
     */
    public Map<String, String> forbiddenTagFormatMap;
    /**
     * 需要处理 参数部分的请求
     */
    public List<String> paramsNeedToCut;
    public int paramsToCutMaxLen;
    /**
     * 校验访问次数, 不合法的输出次数
     */
    public int maxVisitCntPerPeriod;
    public int maxNotFormatCntPerPeriod;
    /**
     * 校验访问次数的周期
     */
    public int visitCntValidatePeriod;
    /**
     * 有消息是否发送邮件
     */
    public boolean sendEmailIfWithNotify;
    /**
     * 发送邮件的用户名密码, 以及邮件服务器
     */
    public String emailAuthUserName;
    public String emailAuthPassword;
    public String emailAuthSmtp;

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
        int refreshFlag = REFRESH_ALL_CONFIG;
        loadFullCachedConfigs(refreshFlag);
        refreshConfigs(refreshFlag);
    }

    /**
     * 清理所有的缓存的 配置项
     *
     * @return void
     * @author Jerry.X.He
     * @date 6/17/2017 9:43 AM
     * @since 1.0
     */
    public void clear(int clearFlag) {
        if (BizUtils.flagExists(clearFlag, REFRESH_SYSTEM_CACHED)) {
            systemConfig.clear();
        }
        if (BizUtils.flagExists(clearFlag, REFRESH_RULE_CACHED)) {
            ruleConfig.clear();
        }
        if (BizUtils.flagExists(clearFlag, REFRESH_FRONT_IDX__CACHED)) {
            frontendIdxConfig.clear();
        }
    }

    /**
     * 刷新当前系统的配置
     *
     * @return void
     * @author Jerry.X.He
     * @date 6/17/2017 9:43 AM
     * @since 1.0
     */
    public void refresh() {
        int refreshFlag = REFRESH_ALL_CONFIG;
        clear(refreshFlag);
        loadFullCachedConfigs(refreshFlag);
        refreshConfigs(refreshFlag);

        systemConfigLastRefreshTs = System.currentTimeMillis();
        ruleConfigLastRefreshTs = systemConfigLastRefreshTs;
        frontIdxConfigLastRefreshTs = systemConfigLastRefreshTs;
    }

    /**
     * 刷新系统配置部分的数据
     *
     * @return void
     * @author Jerry.X.He
     * @date 6/17/2017 1:49 PM
     * @since 1.0
     */
    public void refreshSystemConfig() {
        int refreshFlag = REFRESH_SYSTEM_CACHED;
        clear(refreshFlag);
        loadFullCachedConfigs(refreshFlag);
        refreshConfigs(refreshFlag);

        systemConfigLastRefreshTs = System.currentTimeMillis();
    }

    /**
     * 刷新规则配置部分的数据
     *
     * @return void
     * @author Jerry.X.He
     * @date 6/17/2017 1:49 PM
     * @since 1.0
     */
    public void refreshRuleConfig() {
        int refreshFlag = REFRESH_SYSTEM_CACHED;
        clear(refreshFlag);
        loadFullCachedConfigs(refreshFlag);
        refreshConfigs(refreshFlag);

        ruleConfigLastRefreshTs = System.currentTimeMillis();
    }

    /**
     * 刷新前台配置部分的数据
     *
     * @return void
     * @author Jerry.X.He
     * @date 6/17/2017 1:49 PM
     * @since 1.0
     */
    public void refreshFrontIdxConfig() {
        int refreshFlag = REFRESH_FRONT_IDX__CACHED;
        clear(refreshFlag);
        loadFullCachedConfigs(refreshFlag);
        refreshConfigs(refreshFlag);

        frontIdxConfigLastRefreshTs = System.currentTimeMillis();
    }

    /**
     * 获取所有的 他缓存 容量信息
     *
     * @return com.hx.json.JSONArray
     * @author Jerry.X.He
     * @date 6/17/2017 4:38 PM
     * @since 1.0
     */
    public JSONArray cachedCapacities() {
        JSONArray result = new JSONArray();
        result.add(systemConfig.size());
        result.add(ruleConfig.size());
        result.add(frontendIdxConfig.size());
        return result;
    }

    /**
     * 获取上一次规则配置的刷新的时间戳
     *
     * @return long
     * @author Jerry.X.He
     * @date 6/17/2017 9:48 AM
     * @since 1.0
     */
    public long ruleConfigLastRefreshTs() {
        return ruleConfigLastRefreshTs;
    }

    public long systemConfigLastRefreshTs() {
        return systemConfigLastRefreshTs;
    }

    public long frontIdxConfigLastRefreshTs() {
        return frontIdxConfigLastRefreshTs;
    }

    /**
     * 获取给定的 type 对应的所有的 配置信息
     *
     * @param type type
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @author Jerry.X.He
     * @date 6/17/2017 6:05 PM
     * @since 1.0
     */
    public Map<String, Object> configByType(String type) {
        ConfigType typeE = ConfigType.of(type);

        if (ConfigType.SYSTEM == typeE) {
            return systemConfig;
        } else if (ConfigType.RULE == typeE) {
            return ruleConfig;
        } else if (ConfigType.FRONT_INDEX_CONFIG == typeE) {
            return frontendIdxConfig;
        } else {
            return Collections.emptyMap();
        }
    }

    /**
     * 获取所有的 给定的type下面的 key对应的配置
     *
     * @return java.util.Map<java.lang.String,com.hx.blog_v2.domain.po.BlogTypePO>
     * @author Jerry.X.He
     * @date 5/21/2017 6:13 PM
     * @since 1.0
     */
    public String configByTypeAndKey(String type, String key, String defaultValue) {
        ConfigType typeE = ConfigType.of(type);

        if (ConfigType.SYSTEM == typeE) {
            return systemConfig(key, defaultValue);
        } else if (ConfigType.RULE == typeE) {
            return ruleConfig(key, defaultValue);
        } else if (ConfigType.FRONT_INDEX_CONFIG == typeE) {
            return frontendIdxConfig(key, defaultValue);
        } else {
            return defaultValue;
        }
    }

    public String configByTypeAndKey(String type, String key) {
        return configByTypeAndKey(type, key, null);
    }

    public boolean putConfigByTypeAndKey(String type, String key, String value) {
        ConfigType typeE = ConfigType.of(type);

        if (ConfigType.SYSTEM == typeE) {
            putSystemConfig(key, value);
        } else if (ConfigType.RULE == typeE) {
            putRuleConfig(key, value);
        } else if (ConfigType.FRONT_INDEX_CONFIG == typeE) {
            putFrontendIdxConfig(key, value);
        } else {
            return false;
        }

        return true;
    }

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

    public String systemConfig(String key, String defaultValue) {
        return Tools.optString(systemConfig, key, defaultValue);
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

    public String ruleConfig(String key, String defaultValue) {
        return Tools.optString(ruleConfig, key, defaultValue);
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

    public String frontendIdxConfig(String key, String defaultValue) {
        return Tools.optString(frontendIdxConfig, key, defaultValue);
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
    private void loadFullCachedConfigs(int loadFlag) {
        try {
            List<SystemConfigPO> configs = systemConfigDao.findMany(
                    Criteria.and(Criteria.eq("deleted", "0")).add(Criteria.eq("enable", "1")),
                    Criteria.limitNothing(), Criteria.sortBy("sort", SortByCriteria.ASC), BlogConstants.LOAD_ALL_CONFIG);
            for (SystemConfigPO po : configs) {
                ConfigType type = ConfigType.of(po.getType());
                if (ConfigType.SYSTEM == type) {
                    if (BizUtils.flagExists(loadFlag, REFRESH_SYSTEM_CACHED)) {
                        systemConfig.put(po.getName(), po.getValue());
                    }
                } else if (ConfigType.RULE == type) {
                    if (BizUtils.flagExists(loadFlag, REFRESH_RULE_CACHED)) {
                        ruleConfig.put(po.getName(), po.getValue());
                    }
                } else if (ConfigType.FRONT_INDEX_CONFIG == type) {
                    if (BizUtils.flagExists(loadFlag, REFRESH_FRONT_IDX__CACHED)) {
                        frontendIdxConfig.put(po.getName(), po.getValue());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.err("error while load cached's configs !");
        }
    }

    /**
     * 刷新某些特殊的固定在 ConstantsContext 的系统配置
     *
     * @param refreshConfigs refreshConfigs
     * @return void
     * @author Jerry.X.He
     * @date 6/17/2017 1:53 PM
     * @since 1.0
     */
    private void refreshConfigs(int refreshConfigs) {
        if (BizUtils.flagExists(refreshConfigs, REFRESH_SYSTEM_CACHED)) {
            contextBlogId = Tools.optString(systemConfig, BlogConstants.CONTEXT_BLOG_ID, "-1");
            adviceBlogId = Tools.optString(systemConfig, BlogConstants.ADVICE_BLOG_ID, "-2");
            selfProfileBlogId = Tools.optString(systemConfig, BlogConstants.SELF_PROFILE_BLOG_ID, "-3");
            resourceRootParentId = Tools.optString(systemConfig, BlogConstants.RESOURCE_ROOT_PARENT_ID, "-1");
            resourceLeaveLevel = Tools.optInt(systemConfig, BlogConstants.RESOURCE_LEAVE_LEVEL, 2);
            replyCommentPrefix = Tools.optString(systemConfig, BlogConstants.REPLY_COMMENT_PREFIX, "[reply]");
            replyCommentSuffix = Tools.optString(systemConfig, BlogConstants.REPLY_COMMENT_SUFFIX, "[/reply]");
            reSortStart = Tools.optInt(systemConfig, BlogConstants.RE_SORT_START, 0);
            reSortOffset = Tools.optInt(systemConfig, BlogConstants.RE_SORT_OFFSET, 10);

            defaultCharset = Tools.optString(systemConfig, BlogConstants.DEFAULT_CHARSET, Constants.DEFAULT_CHARSET);
            maxCachedBlog2FloorId = Tools.optInt(systemConfig, BlogConstants.MAX_CACHED_BLOG_2_FLOOR_ID, 100);
            maxCachedBlogFloor2CommentId = Tools.optInt(systemConfig, BlogConstants.MAX_CACHED_BLOG_FLOOR_2_COMMENT_ID, 1000);
            sesionTimeOut = Tools.optInt(systemConfig, BlogConstants.SESSION_TIME_OUT, 30 * 60 * 1000);
            maxCachedUploadedImage = Tools.optInt(systemConfig, BlogConstants.MAX_CACHED_UPLOADED_IMAGE, 100);
            maxRoleIds2ResourceIds = Tools.optInt(systemConfig, BlogConstants.MAX_ROLE_IDS_2_RESOURCE_IDS, 20);
            maxSense2Clicked = Tools.optInt(systemConfig, BlogConstants.MAX_SENSE_2_CLICKED, 1000);
            maxBlogId2BlogEx = Tools.optInt(systemConfig, BlogConstants.MAX_BLOG_ID_2_BLOG_EX, 1000);
            maxRequestIp2BlogVisitLog = Tools.optInt(systemConfig, BlogConstants.MAX_REQUEST_IP_2_BLOG_VISIT_LOG, 1000);

            imageUrlPrefix = Tools.optString(systemConfig, BlogConstants.IMAGE_URL_PREFIX, "http://localhost/files/");
            contextUrlPrefix = Tools.optString(systemConfig, BlogConstants.CONTEXT_URL_PREFIX, "http://localhost:8080/");
            contextSystemUserId = Tools.optString(systemConfig, BlogConstants.CONTEXT_SYSTEM_USER_ID, "-1");
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
            tokenRefreshInterval = Tools.optLong(systemConfig, BlogConstants.TOKEN_REFRESH_INTERVAL, 60 * 1000);

            frontIdxPageTitle = Tools.optString(systemConfig, BlogConstants.FRONT_IDX_PAGE_TITLE, "生活有度, 人生添寿");
            frontIdxPageSubTitle = Tools.optString(systemConfig, BlogConstants.FRONT_IDX_PAGE_SUB_TITLE, "如果你浪费了自己的年龄, 那是挺可悲的 因为你的青春只能持续一点儿时间 -- 很短的一点儿时间");
            guestDummyId = Tools.optString(systemConfig, BlogConstants.DEFAULT_GUEST_DUMMY_ID, "-2");
            guestTitle = Tools.optString(systemConfig, BlogConstants.DEFAULT_GUEST_TITLE, "guest");
            guestRoles = Tools.optString(systemConfig, BlogConstants.DEFAULT_GUEST_ROLES, "guest");

            /**
             * 标签处理相关
             */
            try {
                allowTagSensetiveTags = Tools.optJSONArray(systemConfig, BlogConstants.ALLOW_TAG_SENSETIVE_TAGS, DEFAULT_ALLOW_TAG_SENSETIVE_TAGS);
            } catch (Exception e) {
                allowTagSensetiveTags = DEFAULT_ALLOW_TAG_SENSETIVE_TAGS;
            }
            try {
                allowTagSensetiveTag2Attr = Tools.optJSONObject(systemConfig, BlogConstants.ALLOW_TAG_SENSETIVE_TAG_2_ATTR, DEFAULT_SENSETIVE_TAG_2_ATTR);
            } catch (Exception e) {
                allowTagSensetiveTag2Attr = DEFAULT_SENSETIVE_TAG_2_ATTR;
            }
            try {
                allowTagSensetiveAttrs = Tools.optJSONArray(systemConfig, BlogConstants.ALLOW_TAG_SENSETIVE_ATTRS, DEFAULT_ALLOW_TAG_SENSETIVE_ATTRS);
            } catch (Exception e) {
                allowTagSensetiveAttrs = DEFAULT_ALLOW_TAG_SENSETIVE_ATTRS;
            }
            try {
                forbiddenTagFormatMap = Tools.optJSONObject(systemConfig, BlogConstants.FORBIDDEN_TAG_FORMAT_MAP, DEFAULT_TRANSFER_TAG_NEED_FORMAT);
            } catch (Exception e) {
                forbiddenTagFormatMap = DEFAULT_TRANSFER_TAG_NEED_FORMAT;
            }
            try {
                paramsNeedToCut = Tools.optJSONArray(systemConfig, BlogConstants.PARAMS_NEED_TO_CUT, DEFAULT_PARAMS_NEED_TO_CUT);
            } catch (Exception e) {
                paramsNeedToCut = DEFAULT_PARAMS_NEED_TO_CUT;
            }
            paramsToCutMaxLen = Tools.optInt(systemConfig, BlogConstants.PARAMS_TO_CUT_MAX_LEN, 200);
        }

        maxVisitCntPerPeriod = Tools.optInt(systemConfig, BlogConstants.MAX_VISIT_CNT_PER_PERIOD, 100);
        maxNotFormatCntPerPeriod = Tools.optInt(systemConfig, BlogConstants.MAX_NOT_FORMAT_CNT_PER_PERIOD, 20);
        visitCntValidatePeriod = Tools.optInt(systemConfig, BlogConstants.VISIT_CNT_VALIDATE_PERIOD, 60);

        sendEmailIfWithNotify = Tools.optBoolean(systemConfig, BlogConstants.SEND_EMAIL_IF_WITH_NOTIFY, true);
        emailAuthUserName = Tools.optString(systemConfig, BlogConstants.EMAIL_AUTH_USERNAME, "");
        emailAuthPassword = Tools.optString(systemConfig, BlogConstants.EMAIL_AUTH_PASSWORD, "");
        emailAuthSmtp = Tools.optString(systemConfig, BlogConstants.EMAIL_AUTH_SMTP, "smtp.qq.com");

    }

}
