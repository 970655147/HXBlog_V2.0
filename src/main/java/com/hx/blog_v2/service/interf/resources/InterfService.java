package com.hx.blog_v2.service.interf.resources;

import com.hx.blog_v2.domain.form.common.BeanIdForm;
import com.hx.blog_v2.domain.form.resources.InterfSaveForm;
import com.hx.blog_v2.domain.form.rlt.ResourceInterfUpdateForm;
import com.hx.blog_v2.domain.po.resources.InterfPO;
import com.hx.blog_v2.service.interf.BaseService;
import com.hx.common.interf.common.Result;

/**
 * InterfService
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 11:48 AM
 */
public interface InterfService extends BaseService<InterfPO> {

    /**
     * 增加一个 接口
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result add(InterfSaveForm params);

    /**
     * 搜索所有的的 接口列表
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result adminList();

    /**
     * 搜索符合的条件的 资源接口映射
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result resourceInterfList();

    /**
     * 更新给定的接口
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result update(InterfSaveForm params);

    /**
     * 更新给定的用户的 资源接口映射
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result userRoleUpdate(ResourceInterfUpdateForm params);

    /**
     * 移除给定的接口
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result remove(BeanIdForm params);

    /**
     * 对资源进行重排
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result reSort();
}
