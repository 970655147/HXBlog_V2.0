package com.hx.blog_v2.util;

import com.hx.blog_v2.domain.po.*;
import com.hx.log.idx.IdxManager;
import com.hx.log.util.Constants;
import com.hx.log.util.Log;
import com.hx.log.util.Tools;
import com.hx.mongo.config.MysqlDbConfig;
import com.hx.mongo.config.interf.DbConfig;
import org.springframework.beans.factory.annotation.Value;

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
     * 不过滤任何数据的 idxManager
     */
    public static IdxManager<Integer> IDX_MANAGER;
    /**
     * 过滤掉id 的 idxManager
     */
    public static IdxManager<Integer> IDX_MANAGER_FILTER_ID;

    static {
        IDX_MANAGER = new IdxManager<>();
        IDX_MANAGER.putDoLoad(BlogPO.BEAN_KEY, BlogPO.UNDER_LINE);
        IDX_MANAGER.putDoLoad(BlogExPO.BEAN_KEY, BlogExPO.UNDER_LINE);
        IDX_MANAGER.putDoLoad(BlogCommentPO.BEAN_KEY, BlogCommentPO.UNDER_LINE);
        IDX_MANAGER.putDoLoad(BlogSensePO.BEAN_KEY, BlogSensePO.UNDER_LINE);
        IDX_MANAGER.putDoLoad(BlogTagPO.BEAN_KEY, BlogTagPO.UNDER_LINE);
        IDX_MANAGER.putDoLoad(BlogTypePO.BEAN_KEY, BlogTypePO.UNDER_LINE);
        IDX_MANAGER.putDoLoad(ExceptionLogPO.BEAN_KEY, ExceptionLogPO.UNDER_LINE);
        IDX_MANAGER.putDoLoad(RequestLogPO.BEAN_KEY, RequestLogPO.UNDER_LINE);
        IDX_MANAGER.putDoLoad(RltBlogTagPO.BEAN_KEY, RltBlogTagPO.UNDER_LINE);
        IDX_MANAGER.putDoLoad(VisitorPO.BEAN_KEY, VisitorPO.UNDER_LINE);
        IDX_MANAGER.putDoLoad(MoodPO.BEAN_KEY, MoodPO.UNDER_LINE);

        IDX_MANAGER.putDoFilter(BlogPO.BEAN_KEY, BlogPO.ALL);
        IDX_MANAGER.putDoFilter(BlogExPO.BEAN_KEY, BlogExPO.ALL);
        IDX_MANAGER.putDoFilter(BlogCommentPO.BEAN_KEY, BlogCommentPO.ALL);
        IDX_MANAGER.putDoFilter(BlogSensePO.BEAN_KEY, BlogSensePO.ALL);
        IDX_MANAGER.putDoFilter(BlogTagPO.BEAN_KEY, BlogTagPO.ALL);
        IDX_MANAGER.putDoFilter(BlogTypePO.BEAN_KEY, BlogTypePO.ALL);
        IDX_MANAGER.putDoFilter(ExceptionLogPO.BEAN_KEY, ExceptionLogPO.ALL);
        IDX_MANAGER.putDoFilter(RequestLogPO.BEAN_KEY, RequestLogPO.ALL);
        IDX_MANAGER.putDoFilter(RltBlogTagPO.BEAN_KEY, RltBlogTagPO.ALL);
        IDX_MANAGER.putDoFilter(VisitorPO.BEAN_KEY, VisitorPO.ALL);
        IDX_MANAGER.putDoFilter(MoodPO.BEAN_KEY, MoodPO.ALL);

        IDX_MANAGER_FILTER_ID = new IdxManager<>(IDX_MANAGER);
        IDX_MANAGER_FILTER_ID.putDoFilter(BlogPO.BEAN_KEY, BlogPO.FILTER_ID);
        IDX_MANAGER_FILTER_ID.putDoFilter(BlogExPO.BEAN_KEY, BlogExPO.FILTER_ID);
        IDX_MANAGER_FILTER_ID.putDoFilter(BlogCommentPO.BEAN_KEY, BlogCommentPO.FILTER_ID);
        IDX_MANAGER_FILTER_ID.putDoFilter(BlogSensePO.BEAN_KEY, BlogSensePO.FILTER_ID);
        IDX_MANAGER_FILTER_ID.putDoFilter(BlogTagPO.BEAN_KEY, BlogTagPO.FILTER_ID);
        IDX_MANAGER_FILTER_ID.putDoFilter(BlogTypePO.BEAN_KEY, BlogTypePO.FILTER_ID);
        IDX_MANAGER_FILTER_ID.putDoFilter(ExceptionLogPO.BEAN_KEY, ExceptionLogPO.FILTER_ID);
        IDX_MANAGER_FILTER_ID.putDoFilter(RequestLogPO.BEAN_KEY, RequestLogPO.FILTER_ID);
        IDX_MANAGER_FILTER_ID.putDoFilter(RltBlogTagPO.BEAN_KEY, RltBlogTagPO.FILTER_ID);
        IDX_MANAGER_FILTER_ID.putDoFilter(VisitorPO.BEAN_KEY, VisitorPO.FILTER_ID);
        IDX_MANAGER_FILTER_ID.putDoFilter(MoodPO.BEAN_KEY, MoodPO.FILTER_ID);
    }

    @Value("jdbc.driverClazz")
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


    @Value("blog.dir")
    public static String BLOG_ROOT_DIR = "D:\\HXBlog_V2.0\\post";


}
