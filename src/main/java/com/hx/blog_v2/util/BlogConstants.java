package com.hx.blog_v2.util;

import com.hx.blog_v2.domain.po.*;
import com.hx.json.config.interf.JSONBeanProcessor;
import com.hx.json.config.interf.JSONConfig;
import com.hx.json.config.interf.JSONKeyNodeParser;
import com.hx.json.config.interf.JSONValueNodeParser;
import com.hx.json.config.simple.*;
import com.hx.log.util.Constants;
import com.hx.log.util.Log;
import com.hx.log.util.Tools;
import com.hx.mongo.config.MysqlDbConfig;
import com.hx.mongo.config.interf.DbConfig;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.env.PropertyResolver;

import javax.annotation.PostConstruct;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.hx.log.util.Log.info;

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
            BlogVisitLogPO.class
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
    public static final JSONBeanProcessor UPDATE_BEAN_BEAN_PROCESSOR = regFilterBeanProcessor(Tools.asSet("created_at", "created_at_day", "deleted") );
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
     * 用户验证码的key
     */
    public static final String SESSION_CHECK_CODE = "session:check_code";

    /**
     * 作为记录当前项目的一些信息的 "id"
     */
    public static final String CONTEXT_BLOG_ID = "-1";
    /**
     * 作为意见收集信息的"帖子的id"
     */
    public static final String ADVICE_BLOG_ID = "-2";
    /**
     * 作为自我介绍的"帖子的id"
     */
    public static final String SELF_PROFILE_BLOG_ID = "-3";

    /**
     * 回复博主, 层主的时候的 parentCommentId
     */
    public static final String REPLY_2_FLOOR_OWNER = "-1";

    /**
     * 资源列表的数据 的 根节点的 parentId
     */
    public static final String RESOURCE_ROOT_PARENT_ID = "-1";
    /**
     * 叶子资源的层级
     */
    public static final int RESOURCE_LEAVE_LEVEL = 2;

    /**
     * 回复评论的前缀
     */
    public static final String REPLY_COMMENT_PREFIX = "[reply]";
    /**
     * 回复评论的后缀
     */
    public static final String REPLY_COMMENT_SUFFIX = "[/reply]";
    /**
     * 点赞的 sense
     */
    public static final String UP_PRISE_SENSE = "good";
    /**
     * 点赞的 sense
     */
    public static final String VIEW_SENSE = "view";

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
    @Value("${table.id}")
    public String tableId = "id";

    /**
     * HXMongo 相关
     */
    public static DbConfig MYSQL_DB_CONFIG = null;


    /**
     * 默认的字符集
     */
    @Value("${charset}")
    public String defaultCharset = Constants.DEFAULT_CHARSET;

    /**
     * 缓存的 blogId -> nextFloorId 的个数
     */
    @Value("${cache.blog_2_floorId}")
    public int maxCachedBlog2FloorId = 100;
    /**
     * 缓存的 blogId, floorId -> nextCommentId 的个数
     */
    @Value("${cache.blog_floor_2_commentId}")
    public int maxCachedBlogFloor2CommentId = 1000;
    /**
     * 缓存的 blogId, floorId -> nextCommentId 的个数
     */
    @Value("${cache.uploaded_image}")
    public int maxCachedUploadedImage = 100;
    /**
     * 缓存的 roleIds -> resourceIds 的个数
     */
    @Value("${cache.role_ids2resource_ids}")
    public int maxRoleIds2ResourceIds = 20;
    /**
     * 缓存的 (blogId, userName, email, sense) -> clicked 的个数
     */
    @Value("${cache.sense_2_clicked}")
    public int maxSense2Clicked = 1000;
    /**
     * 缓存的 blogId -> blogEx 的个数
     */
    @Value("${cache.blog_id_2_blog_ex}")
    public int maxBlogId2BlogEx = 1000;
    /**
     * 缓存的 blogId -> blogEx 的个数
     */
    @Value("${cache.request_ip_2_blog_visit_log}")
    public int maxRequestIp2BlogVisitLog = 1000;

    /**
     * 存放博客, 图像的地址
     */
    @Value("${blog.dir}")
    public String blogRootDir = "D:\\HXBlog_V2.0\\post";
    @Value("${files.dir}")
    public String fileRootDir = "D:\\HXBlog_V2.0\\files";

    /**
     * 图片类型 - 图片墙
     */
    @Value("${img.type.img_show}")
    public String imgTypeImgShow = "imgShow";
    /**
     * 图片类型 - 头像
     */
    @Value("${img.type.head_img}")
    public String imgTypeHeadImg = "headImg";
    /**
     * 系统所支持的图片类型枚举
     */
    public final Set<String> supportedImageTypes = Tools.asSet(imgTypeImgShow, imgTypeHeadImg);

    /**
     * 上传到服务器的图片的 url 前缀
     */
    @Value("${image.url.prefix}")
    public String imageUrlPrefix = "http://localhost/files/";

    /**
     * 用户密码的salt的位数
     */
    @Value("${user.pwd_salt_nums}")
    public int pwdSaltNums = 8;

    /**
     * 验证码的长度
     */
    @Value("${check_code.length}")
    public int checkCodeLength = 4;
    /**
     * 验证码的宽度
     */
    @Value("${check_code.img.width}")
    public int checkCodeImgWidth = 160;
    /**
     * 验证码的高度
     */
    @Value("${check_code.img.height}")
    public int checkCodeImgHeight = 80;
    /**
     * 验证码的高度
     */
    public Color checkCodeImgBgColor = Color.WHITE;
    /**
     * 验证码的高度
     */
    public Font checkCodeImgFont = new Font("微软雅黑", Font.ITALIC, 40);

    /**
     * 验证码备选字符的集合
     */
    @Value("${check_code.img.candidates}")
    public String checkCodeCandidatesStr = "123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTVUWXYZ";
    public List<Character> checkCodeCandidates = new ArrayList<>(checkCodeCandidatesStr.length());
    /**
     * 验证码备选字符的最少的干扰线的数量
     */
    @Value("${check_code.img.min_interference}")
    public int checkCodeMinInterference = 30;
    /**
     * 验证码备选字符的最少的干扰线的可控区间
     */
    @Value("${check_code.img.interference_off}")
    public int checkCodeInterferenceOff = 10;

    {
        for (int i = 0, len = checkCodeCandidatesStr.length(); i < len; i++) {
            checkCodeCandidates.add(checkCodeCandidatesStr.charAt(i));
        }
    }

    // -------------------- 辅助方法 --------------------------

    @PostConstruct
    public void init() {
        INSTANCE = this;
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
