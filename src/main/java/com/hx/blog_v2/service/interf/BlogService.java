package com.hx.blog_v2.service.interf;

import com.hx.blog_v2.domain.form.BeanIdForm;
import com.hx.blog_v2.domain.form.BlogSaveForm;
import com.hx.blog_v2.domain.form.BlogSearchForm;
import com.hx.blog_v2.domain.po.BlogPO;
import com.hx.blog_v2.domain.vo.AdminBlogVO;
import com.hx.blog_v2.domain.vo.BlogVO;
import com.hx.common.interf.common.Page;
import com.hx.common.interf.common.Result;

/**
 * BlogService
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 11:48 AM
 */
public interface BlogService extends BaseService<BlogPO> {

    /**
     * 增加一个 Blog
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result save(BlogSaveForm params);

    /**
     * 获取一个 Blog
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result adminGet(BeanIdForm params);

    /**
     * 获取一个 Blog
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result get(BeanIdForm params);

    /**
     * 搜索符合的条件的博客列表
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/28/2017 11:41 AM
     * @since 1.0
     */
    Result list(BlogSearchForm params, Page<BlogVO> page);

    /**
     * 搜索符合的条件的博客列表
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result adminList(BlogSearchForm params, Page<AdminBlogVO> page);

    /**
     * 移除给定的博客
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result remove(BeanIdForm params);

}
