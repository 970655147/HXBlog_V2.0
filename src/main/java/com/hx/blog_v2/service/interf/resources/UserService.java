package com.hx.blog_v2.service.interf.resources;

import com.hx.blog_v2.domain.form.common.BeanIdForm;
import com.hx.blog_v2.domain.form.system.LoginForm;
import com.hx.blog_v2.domain.form.system.UpdatePwdForm;
import com.hx.blog_v2.domain.form.resources.UserSaveForm;
import com.hx.blog_v2.domain.po.resources.UserPO;
import com.hx.blog_v2.domain.vo.resources.AdminUserVO;
import com.hx.blog_v2.service.interf.BaseService;
import com.hx.common.interf.common.Page;
import com.hx.common.interf.common.Result;

/**
 * UserService
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 11:48 AM
 */
public interface UserService extends BaseService<UserPO> {

    /**
     * 增加一个 用户
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result add(UserSaveForm params);

    /**
     * 搜索符合的条件的用户
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result adminList(Page<AdminUserVO> page);

    /**
     * 更新给定的用户
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result update(UserSaveForm params);

    /**
     * 更新给定的用户的密码信息
     *
     * @return result
     * @author Jerry.X.He
     * @date 6/4/2017 7:14 PM
     * @since 1.0
     */
    Result updatePwd(UpdatePwdForm params);

    /**
     * 移除给定的用户
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result remove(BeanIdForm params);

    // -------------------- 核心业务方法 --------------------------

    /**
     * 处理登录的逻辑
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result login(LoginForm params);

    /**
     * 处理登出的逻辑
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result logout();

    /**
     * 获取所有的用户的 id -> name
     *
     * @return
     * @author Jerry.X.He
     * @date 6/11/2017 2:56 PM
     * @since 1.0
     */
    Result allId2Name();

}
