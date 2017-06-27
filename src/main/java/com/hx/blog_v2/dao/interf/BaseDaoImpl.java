package com.hx.blog_v2.dao.interf;

import com.hx.blog_v2.domain.ErrorCode;
import com.hx.blog_v2.domain.form.BeanIdForm;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.common.interf.common.Result;
import com.hx.blog_v2.util.ResultUtils;
import com.hx.log.json.interf.JSONTransferable;
import com.hx.log.util.Tools;
import com.hx.mongo.config.interf.DbConfig;
import com.hx.mongo.connection.interf.ConnectionProvider;
import com.hx.mongo.criteria.LimitCriteria;
import com.hx.mongo.criteria.SortByCriteria;
import com.hx.mongo.criteria.interf.IQueryCriteria;
import com.hx.mongo.criteria.interf.IUpdateCriteria;
import com.hx.mongo.dao.MysqlBaseDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.sql.Connection;
import java.util.List;

/**
 * BaseDaoImpl
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/3/2017 7:26 PM
 */
public abstract class BaseDaoImpl<T extends JSONTransferable<T>> extends MysqlBaseDaoImpl<T> implements BaseDao<T> {

    public BaseDaoImpl(T bean, DbConfig config, ConnectionProvider<Connection> connectionProvider) {
        super(bean, config, connectionProvider);
    }

    public BaseDaoImpl(T bean, DbConfig config) {
        super(bean, config);
    }

    public BaseDaoImpl(T bean, ConnectionProvider<Connection> connectionProvider) {
        super(bean, connectionProvider);
    }

    public BaseDaoImpl(T bean) {
        super(bean);
    }


    @Override
    public Result get(BeanIdForm params) {
        T result = null;
        try {
            result = findById(params.getId(), BlogConstants.LOAD_ALL_CONFIG);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, Tools.errorMsg(e));
        }

        if (result != null) {
            return ResultUtils.success(result);
        }
        return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, "记录不存在 !");
    }

    @Override
    public Result get(IQueryCriteria query) {
        T result = null;
        try {
            result = findOne(query, BlogConstants.LOAD_ALL_CONFIG);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, Tools.errorMsg(e));
        }

        if (result != null) {
            return ResultUtils.success(result);
        }
        return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, "记录不存在 !");
    }

    @Override
    public Result list(IQueryCriteria query) {
        List<T> result = null;
        try {
            result = findMany(query, BlogConstants.LOAD_ALL_CONFIG);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, Tools.errorMsg(e));
        }

        if (result != null) {
            return ResultUtils.success(result);
        }
        return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " ??? ");
    }

    @Override
    public <T1> Result list(IQueryCriteria query, SortByCriteria sortBy, LimitCriteria limit) {
        List<T> result = null;
        try {
            result = findMany(query, limit, sortBy, BlogConstants.LOAD_ALL_CONFIG);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, Tools.errorMsg(e));
        }

        if (result != null) {
            return ResultUtils.success(result);
        }
        return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " ??? ");
    }

    @Override
    public Result add(T po) {
        try {
            insertOne(po, BlogConstants.ADD_BEAN_CONFIG);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, Tools.errorMsg(e));
        }

        return ResultUtils.success();
    }

    @Override
    public Result add(List<T> poes) {
        try {
            insertMany(poes, BlogConstants.ADD_BEAN_CONFIG);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, Tools.errorMsg(e));
        }

        return ResultUtils.success();
    }

    @Override
    public Result update(T po) {
        long modified = 0;
        try {
            modified = updateById(po, BlogConstants.UPDATE_BEAN_CONFIG)
                    .getModifiedCount();
            if (modified == 0) {
                return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, "该记录不存在 !");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, Tools.errorMsg(e));
        }

        return ResultUtils.success(modified);
    }

    @Override
    public Result update(IQueryCriteria query, IUpdateCriteria update, boolean withMulti) {
        long modified = 0;
        try {
            if (withMulti) {
                modified = updateMany(query, update).getModifiedCount();
            } else {
                modified = updateOne(query, update).getModifiedCount();
            }

            if (modified == 0) {
                return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, "记录不存在 !");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, Tools.errorMsg(e));
        }

        return ResultUtils.success(modified);
    }

    @Override
    public Result update(IQueryCriteria query, IUpdateCriteria update) {
        return update(query, update, false);
    }

    @Override
    public Result remove(IQueryCriteria query, boolean withMulti) {
        long modified = 0;
        try {
            if (withMulti) {
                modified = deleteMany(query).getDeletedCount();
            } else {
                modified = deleteOne(query).getDeletedCount();
            }

            if (modified == 0) {
                return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, "记录不存在 !");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, Tools.errorMsg(e));
        }

        return ResultUtils.success(modified);
    }

    @Override
    public Result remove(IQueryCriteria query) {
        return remove(query, false);
    }
}
