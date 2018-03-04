package com.hx.blog_v2.service.interf.blog;

import com.hx.blog_v2.domain.form.common.BeanIdForm;
import com.hx.blog_v2.domain.form.blog.BlogTagSaveForm;
import com.hx.blog_v2.domain.po.blog.BlogTagPO;
import com.hx.blog_v2.service.interf.BaseService;
import com.hx.common.interf.common.Result;

/**
 * BlogTagService
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 11:48 AM
 */
public interface BlogTagService extends BaseService<BlogTagPO> {

    /**
     * 增加一个 BlogTag
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result add(BlogTagSaveForm params);

    /**
     * 获取所有的 BlogTag
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result list();

    /**
     * 更新一个 BlogTag
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result update(BlogTagSaveForm params);

    /**
     * 移除一个 BlogTag
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result remove(BeanIdForm params);

    /**
     * 对当前所有的tag 进行重排
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result reSort();


}
