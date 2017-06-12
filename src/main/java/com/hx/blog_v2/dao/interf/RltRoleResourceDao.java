package com.hx.blog_v2.dao.interf;

import com.hx.blog_v2.domain.form.BeanIdsForm;
import com.hx.blog_v2.domain.po.RltRoleResourcePO;
import com.hx.common.interf.common.Result;

/**
 * BlogTagDao
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 11:20 AM
 */
public interface RltRoleResourceDao extends BaseDao<RltRoleResourcePO> {

    /**
     * 获取 给定的角色对应的所有的 资源列表
     *
     * @param params params
     * @return
     * @author Jerry.X.He
     * @date 6/12/2017 7:28 PM
     * @since 1.0
     */
    Result getResourceIdsByRoleIds(BeanIdsForm params);

}