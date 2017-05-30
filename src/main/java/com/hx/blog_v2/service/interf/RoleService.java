package com.hx.blog_v2.service.interf;

import com.hx.blog_v2.domain.form.BeanIdForm;
import com.hx.blog_v2.domain.form.RoleSaveForm;
import com.hx.blog_v2.domain.form.UserRoleUpdateForm;
import com.hx.blog_v2.domain.po.RolePO;
import com.hx.blog_v2.domain.vo.UserRoleVO;
import com.hx.common.interf.common.Page;
import com.hx.common.interf.common.Result;

/**
 * BlogService
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 11:48 AM
 */
public interface RoleService extends BaseService<RolePO> {

    /**
     * 增加一个 角色
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result add(RoleSaveForm params);

    /**
     * 搜索符合的条件的角色列表
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result adminList();

    /**
     * 搜索符合的条件的 用户角色列表
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result userRoleList(Page<UserRoleVO> page);

    /**
     * 更新给定的角色
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result update(RoleSaveForm params);

    /**
     * 更新给定的用户的 角色
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result userRoleUpdate(UserRoleUpdateForm params);

    /**
     * 移除给定的角色
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result remove(BeanIdForm params);

}
