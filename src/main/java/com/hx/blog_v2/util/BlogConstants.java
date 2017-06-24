package com.hx.blog_v2.util;

import com.hx.blog_v2.domain.po.*;
import com.hx.json.config.interf.JSONBeanProcessor;
import com.hx.json.config.interf.JSONConfig;
import com.hx.json.config.interf.JSONKeyNodeParser;
import com.hx.json.config.interf.JSONValueNodeParser;
import com.hx.json.config.simple.*;
import com.hx.log.util.Log;
import com.hx.log.util.Tools;
import com.hx.mongo.config.MysqlDbConfig;
import com.hx.mongo.config.interf.DbConfig;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.util.Set;

/**
 * BlogConstants
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 10:01 AM
 */
public final class BlogConstants {

    /**
     * BlogConstants 实例
     */
    private static BlogConstants INSTANCE;

    public BlogConstants() {
        INSTANCE = this;
    }

    public static BlogConstants getInstance() {
        return INSTANCE;
    }

    /**
     * 日期相关
     */
    /**
     * yyyy-MM-dd
     */
    public static final String FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
    /**
     * yyyy-MM
     */
    public static final String FORMAT_YYYY_MM = "yyyy-MM";
    /**
     * yyyy-MM-dd hh:mm:ss
     */
    public static final String FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    /**
     * yyyy-MM-dd_hh-mm-ss
     */
    public static final String FORMAT_FILENAME = "yyyy-MM-dd_HH-mm-ss";

    /**
     * JSONTransferable
     */
    /**
     * 各个 PO 的 class
     */
    public static final Class[] ALL_PO_CLAZZ = new Class[]{
            BlogCommentPO.class, BlogExPO.class, BlogPO.class, BlogSensePO.class, BlogTagPO.class,
            BlogTypePO.class, ExceptionLogPO.class, ImagePO.class, LinkPO.class, MoodPO.class, RequestLogPO.class,
            UserPO.class, VisitorPO.class, RltBlogTagPO.class, UploadFilePO.class,
            RolePO.class, RltUserRoleRolePO.class, ResourcePO.class, RltRoleResourcePO.class, InterfPO.class, RltResourceInterfPO.class,
            BlogVisitLogPO.class, MessagePO.class, BlogCreateTypePO.class, SystemConfigPO.class
    };
    /**
     * 下划线的注册了各个PO 的 KeyNodeParser
     */
    public static final JSONKeyNodeParser UNDER_LINE_KEY_NODE_PARSER = regKeyNodeParser(1);
    /**
     * valueNodeParser
     */
    public static final JSONValueNodeParser DEFAULT_VALUE_NODE_PARSER = new SimpleValueNodeParser();
    /**
     * 默认的不过滤任何字段的 beanProcessor
     */
    public static final JSONBeanProcessor DEFAULT_BEAN_PROCESSOR = SimpleBeanProcessor.getInstance();
    /**
     * 向数据库中 增加bean 是需要过滤掉 "id"
     */
    public static final JSONBeanProcessor ADD_BEAN_BEAN_PROCESSOR = regFilterBeanProcessor(Tools.asSet("id"));
    /**
     * 向数据库中 增加bean 是需要过滤掉 "created_at", "deleted"
     */
    public static final JSONBeanProcessor UPDATE_BEAN_BEAN_PROCESSOR = regFilterBeanProcessor(Tools.asSet("created_at", "created_at_day"));
    /**
     * 向数据库中 增加bean 是需要过滤掉 "created_at", "deleted", "password"
     */
    public static final JSONBeanProcessor USER_UPDATE_BEAN_BEAN_PROCESSOR = regFilterBeanProcessor(
            Tools.asSet("created_at", "deleted", "user_name", "pwd_salt", "password",
                    "last_login_ip", "last_login_at")
    );

    /**
     * 从数据库加载所有数据的 JSONConfig
     */
    public static final JSONConfig LOAD_ALL_CONFIG = new SimpleJSONConfig(
            UNDER_LINE_KEY_NODE_PARSER,
            DEFAULT_VALUE_NODE_PARSER,
            DEFAULT_BEAN_PROCESSOR
    );
    /**
     * 向数据库添加记录的的 JSONConfig
     */
    public static final JSONConfig ADD_BEAN_CONFIG = new SimpleJSONConfig(
            UNDER_LINE_KEY_NODE_PARSER,
            DEFAULT_VALUE_NODE_PARSER,
            ADD_BEAN_BEAN_PROCESSOR
    );
    /**
     * 向数据库更新记录的的 JSONConfig
     */
    public static final JSONConfig UPDATE_BEAN_CONFIG = new SimpleJSONConfig(
            UNDER_LINE_KEY_NODE_PARSER,
            DEFAULT_VALUE_NODE_PARSER,
            UPDATE_BEAN_BEAN_PROCESSOR
    );
    /**
     * 向数据库更新记录的的 JSONConfig
     */
    public static final JSONConfig USER_UPDATE_BEAN_CONFIG = new SimpleJSONConfig(
            UNDER_LINE_KEY_NODE_PARSER,
            DEFAULT_VALUE_NODE_PARSER,
            USER_UPDATE_BEAN_BEAN_PROCESSOR
    );

    /**
     * WebContext 中threadLocalMap 的request的key
     */
    public static final String SESSION_REQUEST = "session:request";
    /**
     * WebContext 中threadLocalMap 的response的key
     */
    public static final String SESSION_RESPONSE = "session:response";
    /**
     * WebContext 中threadLocalMap 的session的key
     */
    public static final String SESSION_SESSION = "session:session";

    /**
     * 存放在session中的相关信息的key
     */
    /**
     * 用户认证信息的key
     */
    public static final String SESSION_USER = "session:user";
    /**
     * 用户认证信息的key
     */
    public static final String SESSION_USER_ID = "session:user_id";
    /**
     * 用户验证码的key
     */
    public static final String SESSION_CHECK_CODE = "session:check_code";
    /**
     * 用户带回来的token的key
     */
    public static final String SESSION_TOKEN = "session:token";
    /**
     * 暂存于 request 中的data的key
     */
    public static final String REQUEST_DATA = "request:data";
    /**
     * 暂存于 request 中的extra的key
     */
    public static final String REQUEST_EXTRA = "request:extra";
    /**
     * 用户带过来token的请求头
     */
    public static final String COOKIE_TOKEN = "hx_blog_token";

    /**
     * 相关常量的 key
     */
    /**
     * 作为记录当前项目的一些信息的 "id"
     */
    public static final String CONTEXT_BLOG_ID = "blog.context.id";
    /**
     * 作为意见收集信息的"帖子的id"
     */
    public static final String ADVICE_BLOG_ID = "blog.advice.id";
    /**
     * 作为自我介绍的"帖子的id"
     */
    public static final String SELF_PROFILE_BLOG_ID = "blog.self_profile.id";
    /**
     * 资源列表的数据 的 根节点的 parentId
     */
    public static final String RESOURCE_ROOT_PARENT_ID = "resource.root.parent_id";
    /**
     * 叶子资源的层级
     */
    public static final String RESOURCE_LEAVE_LEVEL = "resource.leave.level";
    /**
     * 回复评论的前缀
     */
    public static final String REPLY_COMMENT_PREFIX = "comment.reply.prefix";
    /**
     * 回复评论的后缀
     */
    public static final String REPLY_COMMENT_SUFFIX = "comment.reply.suffix";
    /**
     * 点赞的 sense
     */
    public static final String UP_PRISE_SENSE = "sense.up_prise";
    /**
     * 点赞的 sense
     */
    public static final String VIEW_SENSE = "sense.view";
    /**
     * 资源重排 sort 的起始值
     */
    public static final String RE_SORT_START = "resort.start";
    /**
     * 资源重排 sort 的重排偏移
     */
    public static final String RE_SORT_OFFSET = "resort.offset";
    /**
     * 默认的字符集
     */
    public static final String DEFAULT_CHARSET = "charset.default";

    /**
     * 缓存的 blogId -> nextFloorId 的个数
     */
    public static final String MAX_CACHED_BLOG_2_FLOOR_ID = "cache.max.blog_2_floor_id";
    /**
     * 缓存的 blogId, floorId -> nextCommentId 的个数
     */
    public static final String MAX_CACHED_BLOG_FLOOR_2_COMMENT_ID = "cache.max.blog_floor_2_comment_id";
    /**
     * 缓存的 blogId, floorId -> nextCommentId 的个数
     */
    public static final String SESSION_TIME_OUT = "session.timeout";
    /**
     * 缓存的 blogId, floorId -> nextCommentId 的个数
     */
    public static final String MAX_CACHED_UPLOADED_IMAGE = "cache.max.upload_image";
    /**
     * 缓存的 roleIds -> resourceIds 的个数
     */
    public static final String MAX_ROLE_IDS_2_RESOURCE_IDS = "cache.max.role_ids_2_resource_ids";
    /**
     * 缓存的 (blogId, userName, email, sense) -> clicked 的个数
     */
    public static final String MAX_SENSE_2_CLICKED = "cache.max.sense_2_clicked";
    /**
     * 缓存的 blogId -> blogEx 的个数
     */
    public static final String MAX_BLOG_ID_2_BLOG_EX = "cache.max.blog_id_2_blog_ex";
    /**
     * 缓存的 ip -> blogVisitLog 的个数
     */
    public static final String MAX_REQUEST_IP_2_BLOG_VISIT_LOG = "cache.max.request_ip_2_blog_visit_log";

    /**
     * 上传到服务器的图片的 url 前缀
     */
    public static final String IMAGE_URL_PREFIX = "image.url.prefix";
    /**
     * 用户密码的salt的位数
     */
    public static final String PWD_SALT_NUMS = "user.pwd.salt_nums";
    /**
     * 验证码的长度
     */
    public static final String CHECK_CODE_LENGTH = "check_code.length";
    /**
     * 验证码的宽度
     */
    public static final String CHECK_CODE_IMG_WIDTH = "check_code.width";
    /**
     * 验证码的高度
     */
    public static final String CHECK_CODE_IMG_HEIGHT = "check_code.height";
    /**
     * 验证码备选字符的集合
     */
    public static final String CHECK_CODE_CANDIDATES_STR = "check_code.candidates_str";
    /**
     * 验证码备选字符的最少的干扰线的数量
     */
    public static final String CHECK_CODE_MIN_INTERFERENCE = "check_code.min.interference";
    /**
     * 验证码备选字符的最少的干扰线的可控区间
     */
    public static final String CHECK_CODE_INTERFERENCE_OFF = "check_code.max.interference";

    /**
     * 缓存的统计结果的数量
     */
    public static final String MAX_CACHE_STATISTICS_DAYS = "cache.max.statistics.days";
    /**
     * 实时统计的时间间隔
     */
    public static final String REAL_TIME_CHART_TIME_INTERVAL = "chart.real_time.time_interval";
    /**
     * 缓存的统计结果的数量
     */
    public static final String MAX_REAL_TIME_CACHE_STASTICS_TIMES = "cache.max.real_time.statistics_times";
    /**
     * 不需要记录在 requestLog 中的 url
     */
    public static final String REQUEST_LOG_URI_TO_IGNORE = "request_log.url.to_ignore";
    /**
     * 服务端刷新 token 的周期
     */
    public static final String TOKEN_REFRESH_INTERVAL = "request.token.refresh.interval";

    /**
     * 首页的标题
     */
    public static final String FRONT_IDX_PAGE_TITLE = "front.idx_page.title";
    /**
     * 首页的子标题
     */
    public static final String FRONT_IDX_PAGE_SUB_TITLE = "front.idx_page.sub_title";
    /**
     * 游客的 title
     */
    public static final String DEFAULT_GUEST_TITLE = "guest.title";
    /**
     * 游客的 角色id[无用]
     */
    public static final String DEFAULT_GUEST_ROLES = "guest.roles";

    // ----------------------------------------- configurable -------------------------------------------------

    @Value("${jdbc.driver_clazz}")
    public String dbDriver = "com.mysql.jdbc.Driver";
    @Value("${jdbc.ip}")
    public String dbIp = "192.168.0.190";
    @Value("${jdbc.port}")
    public int dbPort = 3306;
    @Value("${jdbc.db}")
    public String dbDb = "blog";
    @Value("${jdbc.username}")
    public String dbUsername = "root";
    @Value("${jdbc.password}")
    public String dbPassword = "root";
    /**
     * table, id 在各自的dao中配置
     */
    @Value("${table.blog}")
    public String tableBlog = "blog";
    @Value("${table.blog_ex}")
    public String tableBlogEx = "blog_ex";
    @Value("${table.blog_tag}")
    public String tableBlogTag = "blog_tag";
    @Value("${table.blog_type}")
    public String tableBlogType = "blog_type";
    @Value("${table.blog_comment}")
    public String tableBlogComment = "blog_comment";
    @Value("${table.rlt_blog_tag}")
    public String tableRltBlogTag = "rlt_blog_tag";
    @Value("${table.blog_sense}")
    public String tableBlogSense = "blog_sense";
    @Value("${table.visitor}")
    public String tableVisitor = "visitor";
    @Value("${table.request_log}")
    public String tableRequestLog = "request_log";
    @Value("${table.exception_log}")
    public String tableExceptionLog = "exception_log";
    @Value("${table.mood}")
    public String tableMood = "mood";
    @Value("${table.user}")
    public String tableUser = "user";
    @Value("${table.link}")
    public String tableLink = "link";
    @Value("${table.images}")
    public String tableImages = "images";
    @Value("${table.uploaded_files}")
    public String tableUploadedFiles = "uploaded_files";
    @Value("${table.role}")
    public String tableRole = "role";
    @Value("${table.resource}")
    public String tableResource = "resource";
    @Value("${table.rlt_user_role}")
    public String tableRltUserRole = "rlt_user_role";
    @Value("${table.rlt_role_resource}")
    public String tableRltRoleResource = "rlt_role_resource";
    @Value("${table.interf}")
    public String tableInterf = "interf";
    @Value("${table.rlt_resource_interf}")
    public String tableRltResourceInterf = "rlt_resource_interf";
    @Value("${table.blog_visit_log}")
    public String tableBlogVisitLog = "blog_visit_log";
    @Value("${table.message}")
    public String tableMessage = "message";
    @Value("${table.blog_create_type}")
    public String tableBlogCreateType = "blog_create_type";
    @Value("${table.system_config}")
    public String tableSystemConfig = "system_config";
    @Value("${table.id}")
    public String tableId = "id";

    /**
     * HXMongo 相关
     */
    public static DbConfig MYSQL_DB_CONFIG = null;

    /**
     * 存放博客, 图像的地址
     */
    @Value("${blog.dir}")
    public String blogRootDir = "D:\\HXBlog_V2.0\\post";
    @Value("${files.dir}")
    public String fileRootDir = "D:\\HXBlog_V2.0\\files";
    /**
     * 回复博主, 层主的时候的 parentCommentId
     */
    @Value("${comment.reply_2_floor_owner}")
    public String reply2FloorOwner = "-1";
    /**
     * 异常日志保留的异常 StackTrace 的行数
     */
    @Value("${exception_log.max_stacktrace}")
    public int exceptionLogMaxStackTrace = 20;
    /**
     * 查询 ip 的url的 logPattern
     */
    @Value("${ip_addr.req.log_pattern}")
    public String ipAddrReqLogPattern = "map('http://ip.taobao.com//service/getIpInfo.php?ip=' + $this)";

    // -------------------- 辅助方法 --------------------------

    @PostConstruct
    public void init() {
        MYSQL_DB_CONFIG = new MysqlDbConfig().ip(dbIp).port(dbPort)
                .db(dbDb).username(dbUsername).password(dbPassword);

        try {
            Class.forName(dbDriver);
        } catch (Exception e) {
            Log.err("error while load jdbc driver !");
        }
    }

    /**
     * 注册监听所有的 POClass 的给定索引的 JSONField 的 KeyNodeParser
     *
     * @param idx idx
     * @return com.hx.json.config.simple.RegisteredJSONFieldKeyNodeParser
     * @author Jerry.X.He
     * @date 5/29/2017 2:50 PM
     * @since 1.0
     */
    private static RegisteredJSONFieldKeyNodeParser regKeyNodeParser(int idx) {
        RegisteredJSONFieldKeyNodeParser result = RegisteredJSONFieldKeyNodeParser.of(ALL_PO_CLAZZ.length);
        for (Class clazz : ALL_PO_CLAZZ) {
            result.register(clazz, JSONFieldKeyNodeParser.of(idx));
        }
        return result;
    }

    /**
     * 注册监听所有的 POClass 的给定索引的 JSONField 的 KeyNodeParser
     *
     * @param filter 需要过滤的字段
     * @return com.hx.json.config.simple.RegisteredJSONFieldKeyNodeParser
     * @author Jerry.X.He
     * @date 5/29/2017 2:50 PM
     * @since 1.0
     */
    private static RegisteredBeanProcessor regFilterBeanProcessor(Set<String> filter) {
        RegisteredBeanProcessor result = RegisteredBeanProcessor.of(ALL_PO_CLAZZ.length);
        for (Class clazz : ALL_PO_CLAZZ) {
            result.register(clazz, new FilteredBeanProcessor(false, true, filter));
        }
        return result;
    }

}
