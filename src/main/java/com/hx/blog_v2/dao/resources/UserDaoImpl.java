package com.hx.blog_v2.dao.resources;

import com.hx.blog_v2.dao.interf.BaseDaoImpl;
import com.hx.blog_v2.dao.interf.UserDao;
import com.hx.blog_v2.domain.po.resources.UserPO;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.MyMysqlConnectionProvider;
import com.hx.blog_v2.util.ResultUtils;
import com.hx.common.interf.common.Result;
import com.hx.log.util.Tools;
import com.hx.mongo.config.MysqlDbConfig;
import org.springframework.stereotype.Repository;

/**
 * UserDaoImpl
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 10:38 AM
 */
@Repository
public class UserDaoImpl extends BaseDaoImpl<UserPO> implements UserDao {

    public UserDaoImpl() {
        super(UserPO.PROTO_BEAN,
                new MysqlDbConfig(BlogConstants.MYSQL_DB_CONFIG).table(tableName()).id(id()),
                MyMysqlConnectionProvider.getInstance());
    }


    public static String tableName() {
        return BlogConstants.getInstance().tableUser;
    }

    public static String id() {
        return BlogConstants.getInstance().tableId;
    }

    @Override
    public Result update(UserPO po) {
        try {
            long matched = updateById(po, BlogConstants.USER_UPDATE_BEAN_CONFIG)
                    .getModifiedCount();
            if (matched == 0) {
                return ResultUtils.failed("该记录不存在 !");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.failed(Tools.errorMsg(e));
        }

        return ResultUtils.success();
    }

}
