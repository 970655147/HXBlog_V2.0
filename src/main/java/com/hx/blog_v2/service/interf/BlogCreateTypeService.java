package com.hx.blog_v2.service.interf;

import com.hx.blog_v2.domain.form.BeanIdForm;
import com.hx.blog_v2.domain.form.BlogCreateTypeSaveForm;
import com.hx.blog_v2.domain.po.BlogCreateTypePO;
import com.hx.common.interf.common.Result;

/**
 * BlogService
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 11:48 AM
 */
public interface BlogCreateTypeService extends BaseService<BlogCreateTypePO> {

    /**
     * 增加一个 图片墙照片
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result add(BlogCreateTypeSaveForm params);

    /**
     * 搜索符合的条件的照片列表
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result adminList();

    /**
     * 更新给定的照片
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result update(BlogCreateTypeSaveForm params);

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
     * 重排给定的资源
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result reSort();

}
