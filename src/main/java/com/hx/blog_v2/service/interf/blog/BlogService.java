package com.hx.blog_v2.service.interf.blog;

import com.hx.blog_v2.domain.form.common.BeanIdForm;
import com.hx.blog_v2.domain.form.blog.BlogSaveForm;
import com.hx.blog_v2.domain.form.blog.BlogSearchForm;
import com.hx.blog_v2.domain.po.blog.BlogPO;
import com.hx.blog_v2.domain.vo.blog.AdminBlogVO;
import com.hx.blog_v2.domain.vo.blog.BlogVO;
import com.hx.blog_v2.service.interf.BaseService;
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
    Result get(BeanIdForm params);

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

    /**
     * 转换 blog 的状态
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result transfer(BlogSaveForm params);

}
