package com.hx.blog_v2.dao.interf;

import com.hx.blog_v2.domain.form.common.BeanIdForm;
import com.hx.common.interf.common.Result;
import com.hx.log.json.interf.JSONTransferable;
import com.hx.mongo.criteria.LimitCriteria;
import com.hx.mongo.criteria.SortByCriteria;
import com.hx.mongo.criteria.interf.IQueryCriteria;
import com.hx.mongo.criteria.interf.IUpdateCriteria;
import com.hx.mongo.dao.interf.MysqlIBaseDao;

import java.util.List;

/**
 * BaseDao
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/10/2017 5:23 PM
 */
public interface BaseDao<T extends JSONTransferable<T>> extends MysqlIBaseDao<T> {

    /**
     * 根据给定的条件, 获取一个 po
     *
     * @return
     * @author Jerry.X.He
     * @date 6/9/2017 9:07 PM
     * @since 1.0
     */
    Result get(BeanIdForm params);

    Result get(IQueryCriteria query);

    /**
     * 根据给定的条件, 获取多个
     *
     * @return
     * @author Jerry.X.He
     * @date 6/9/2017 9:07 PM
     * @since 1.0
     */
    Result list(IQueryCriteria query);

    /**
     * 根据给定的条件, 获取多个po
     *
     * @return
     * @author Jerry.X.He
     * @date 6/9/2017 9:07 PM
     * @since 1.0
     */
    <T> Result list(IQueryCriteria query, SortByCriteria sortBy, LimitCriteria limit);

    /**
     * 向数据库中加入给定的 po
     *
     * @param po po
     * @return
     * @author Jerry.X.He
     * @date 6/9/2017 9:21 PM
     * @since 1.0
     */
    Result add(T po);

    /**
     * 向数据库中加入给定多个 po
     *
     * @param po po
     * @return
     * @author Jerry.X.He
     * @date 6/9/2017 9:21 PM
     * @since 1.0
     */
    Result add(List<T> po);

    /**
     * 向数据库中更新给定的 po
     *
     * @param po po
     * @return
     * @author Jerry.X.He
     * @date 6/9/2017 9:21 PM
     * @since 1.0
     */
    Result update(T po);

    /**
     * 向数据库中根据条件更新
     *
     * @param query     query
     * @param update    update
     * @param withMulti withMulti
     * @return
     * @author Jerry.X.He
     * @date 6/9/2017 9:21 PM
     * @since 1.0
     */
    Result update(IQueryCriteria query, IUpdateCriteria update, boolean withMulti);

    Result update(IQueryCriteria query, IUpdateCriteria update);

    /**
     * 向数据库中根据条件删除记录
     *
     * @param query     query
     * @param withMulti withMulti
     * @return
     * @author Jerry.X.He
     * @date 6/9/2017 9:21 PM
     * @since 1.0
     */
    Result remove(IQueryCriteria query, boolean withMulti);

    Result remove(IQueryCriteria query);

}
