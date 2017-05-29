package com.hx.blog_v2.util;

import com.hx.blog_v2.domain.po.*;
import com.hx.json.config.interf.JSONBeanProcessor;
import com.hx.json.config.interf.JSONConfig;
import com.hx.json.config.interf.JSONKeyNodeParser;
import com.hx.json.config.interf.JSONValueNodeParser;
import com.hx.json.config.simple.*;
import com.hx.log.util.Constants;
import com.hx.log.util.Tools;
import com.hx.mongo.config.MysqlDbConfig;
import com.hx.mongo.config.interf.DbConfig;
import org.springframework.beans.factory.annotation.Value;

import java.util.Set;

/**
 * BlogConstants
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 10:01 AM
 */
public final class BlogConstants {

    // disable constructor
    private BlogConstants() {
        Tools.assert0("can't instantiate !");
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
    public static final String FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd hh:mm:ss";
    /**
     * yyyy-MM-dd_hh-mm-ss
     */
    public static final String FORMAT_FILENAME = "yyyy-MM-dd_hh-mm-ss";

    /**
     * JSONTransferable
     */
    /**
     * 各个 PO 的 class
     */
    public static final Class[] ALL_PO_CLAZZ = new Class[]{
            BlogCommentPO.class, BlogExPO.class, BlogPO.class, BlogSensePO.class, BlogTagPO.class,
            BlogTypePO.class, ExceptionLogPO.class, ImagePO.class, LinkPO.class, MoodPO.class, RequestLogPO.class,
            UserPO.class, VisitorPO.class, RltBlogTagPO.class, UploadedImagePO.class
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
    public static final JSONBeanProcessor UPDATE_BEAN_BEAN_PROCESSOR = regFilterBeanProcessor(Tools.asSet("created_at", "deleted"));

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

    @Value("jdbc.driver_clazz")
    public static String DB_DRIVER = "com.mysql.jdbc.Driver";
    @Value("jdbc.ip")
    public static String DB_IP = "192.168.0.190";
    @Value("jdbc.port")
    public static int DB_PORT = 3306;
    @Value("jdbc.db")
    public static String DB_DB = "blog";
    /**
     * table, id 在各自的dao中配置
     */
    @Value("jdbc.username")
    public static String DB_USERNAME = "root";
    @Value("jdbc.password")
    public static String DB_PASSWORD = "root";

    @Value("table.blog")
    public static final String TABLE_BLOG = "blog";
    @Value("table.blog_ex")
    public static final String TABLE_BLOG_EX = "blog_ex";
    @Value("table.blog_tag")
    public static final String TABLE_BLOG_TAG = "blog_tag";
    @Value("table.blog_type")
    public static final String TABLE_BLOG_TYPE = "blog_type";
    @Value("table.blog_comment")
    public static final String TABLE_BLOG_COMMENT = "blog_comment";
    @Value("table.rlt_blog_tag")
    public static final String TABLE_RLT_BLOG_TAG = "rlt_blog_tag";
    @Value("table.blog_sense")
    public static final String TABLE_BLOG_SENSE = "blog_sense";
    @Value("table.visitor")
    public static final String TABLE_VISITOR = "visitor";
    @Value("table.request_log")
    public static final String TABLE_REQUEST_LOG = "request_log";
    @Value("table.exception_log")
    public static final String TABLE_EXCEPTION_LOG = "exception_log";
    @Value("table.mood")
    public static final String TABLE_MOOD = "mood";
    @Value("table.user")
    public static final String TABLE_USER = "user";
    @Value("table.link")
    public static final String TABLE_LINK = "link";
    @Value("table.images")
    public static final String TABLE_IMAGES = "images";
    @Value("table.uploaded_image")
    public static final String TABLE_UPLOADED_IMAGE = "uploaded_images";

    @Value("table.id")
    public static final String TABLE_ID = "id";

    /**
     * HXMongo 相关
     */
    public static final DbConfig MYSQL_DB_CONFIG = new MysqlDbConfig().ip(DB_IP).port(DB_PORT)
            .db(DB_DB).username(DB_USERNAME).password(DB_PASSWORD);


    /**
     * 默认的字符集
     */
    public static String DEFAULT_CHARSET = Constants.DEFAULT_CHARSET;

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
     * 缓存的 blogId -> nextFloorId 的个数
     */
    @Value("cache.blog_2_floorId")
    public static int MAX_CACHED_BLOG_2_FLOOR_ID = 100;
    /**
     * 缓存的 blogId, floorId -> nextCommentId 的个数
     */
    @Value("cache.blog_floor_2_commentId")
    public static int MAX_CACHED_BLOG_FLOOR_2_COMMENT_ID = 1000;
    /**
     * 缓存的 blogId, floorId -> nextCommentId 的个数
     */
    @Value("cache.uploaded_image")
    public static int MAX_CACHED_UPLOADED_IMAGE = 1000;

    @Value("blog.dir")
    public static String BLOG_ROOT_DIR = "D:\\HXBlog_V2.0\\post";
    @Value("img.dir")
    public static String IMG_ROOT_DIR = "D:\\HXBlog_V2.0\\imgs";

    /**
     * 作为意见收集信息的"帖子的id"
     */
    public static final String CONTEXT_BLOG_ID = "-1";

    /**
     * 回复博主, 层主的时候的 parentCommentId
     */
    public static String REPLY_2_FLOOR_OWNER = "-1";

    /**
     * 上传到服务器的图片的 url 前缀
     */
    @Value("image.url.prefix")
    public static String IMAGE_URL_RREFIX = "http://localhost/imgs/";


    // -------------------- 辅助方法 --------------------------

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
