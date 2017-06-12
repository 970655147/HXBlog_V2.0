package com.hx.blog_v2.dao.interf;

import com.hx.blog_v2.domain.form.BeanIdsForm;
import com.hx.blog_v2.domain.po.RltResourceInterfPO;
import com.hx.common.interf.common.Result;
import com.hx.mongo.dao.interf.MysqlIBaseDao;

/**
 * BlogTagDao
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 11:20 AM
 */
public interface RltResourceInterfDao extends BaseDao<RltResourceInterfPO> {

    /**
     * 获取 给定的资源对应的所有的 接口列表[可以为 pattern]
     *
     * @param params params
     * @return
     * @author Jerry.X.He
     * @date 6/12/2017 7:28 PM
     * @since 1.0
     */
    Result getInterfsByResourceIds(BeanIdsForm params);

}