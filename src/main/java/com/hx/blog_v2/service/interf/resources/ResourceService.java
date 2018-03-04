package com.hx.blog_v2.service.interf.resources;

import com.hx.blog_v2.domain.form.common.BeanIdForm;
import com.hx.blog_v2.domain.form.resources.ResourceSaveForm;
import com.hx.blog_v2.domain.form.rlt.RoleResourceUpdateForm;
import com.hx.blog_v2.domain.po.resources.ResourcePO;
import com.hx.blog_v2.service.interf.BaseService;
import com.hx.common.interf.common.Result;

/**
 * ResourceService
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 11:48 AM
 */
public interface ResourceService extends BaseService<ResourcePO> {

    /**
     * 增加一个 资源
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result add(ResourceSaveForm params);

    /**
     * 搜索所有的的资源列表
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result adminList();

    /**
     * 拉取所有可见的资源列表
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result treeList(boolean spread);

    /**
     * 拉取所有的资源列表
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result adminTreeList(boolean spread);

    /**
     * 搜索符合的条件的角色权限列表
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result roleResourceList();

    /**
     * 更新给定的照片
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/30/2017 7:39 PM
     * @since 1.0
     */
    Result update(ResourceSaveForm params);

    /**
     * 更新给定的角色对应的资源
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/30/2017 7:39 PM
     * @since 1.0
     */
    Result roleResourceUpdate(RoleResourceUpdateForm params);

    /**
     * 移除给定的照片
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result remove(BeanIdForm params);

    /**
     * 刷新当前资源层级的排序 [目前约定为 同级 sort 相差10, 从10开始]
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/30/2017 7:39 PM
     * @since 1.0
     */
    Result reSort();

}
