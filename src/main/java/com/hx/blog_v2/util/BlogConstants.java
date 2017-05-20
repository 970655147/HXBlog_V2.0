package com.hx.blog_v2.util;

import com.hx.blog_v2.domain.po.BlogPO;
import com.hx.log.idx.IdxManager;
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
    public static final String FORMAT_YYYY_MM = "yyyy-MM-dd";
    /**
     * yyyy-MM-dd hh:mm:ss
     */
    public static final String FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd hh:mm:ss";

    /**
     * JSONTransferable
     */
    /**
     * 不过滤任何数据的 idxManager
     */
    public static IdxManager<Integer> IDX_MANAGER = new IdxManager<>();
    /**
     * 过滤掉id 的 idxManager
     */
    public static IdxManager<Integer> IDX_MANAGER_FILTER_ID = new IdxManager<>();

    static {
        IDX_MANAGER.putDoLoad(BlogPO.BEAN_KEY, BlogPO.UNDER_LINE);
        IDX_MANAGER.putDoFilter(BlogPO.BEAN_KEY, BlogPO.ALL);

        IDX_MANAGER_FILTER_ID.putDoLoad(BlogPO.BEAN_KEY, BlogPO.UNDER_LINE);
        IDX_MANAGER_FILTER_ID.putDoFilter(BlogPO.BEAN_KEY, BlogPO.FILTER_ID);
    }

    @Value("jdbc.ip")
    public static String DB_IP = "192.168.1.190";
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
    @Value("table.id")
    public static final String TABLE_ID = "id";

    /**
     * HXMongo 相关
     */
    public static final DbConfig MYSQL_DB_CONFIG = new MysqlDbConfig().ip(DB_IP).port(DB_PORT)
            .db(DB_DB).username(DB_USERNAME).password(DB_PASSWORD);


}
