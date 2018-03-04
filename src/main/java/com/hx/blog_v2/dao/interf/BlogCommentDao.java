package com.hx.blog_v2.dao.interf;

import com.hx.blog_v2.domain.form.common.BeanIdForm;
import com.hx.blog_v2.domain.po.blog.BlogCommentPO;
import com.hx.common.interf.common.Page;
import com.hx.common.interf.common.Result;

/**
 * BlogTagDao
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 11:20 AM
 */
public interface BlogCommentDao extends BaseDao<BlogCommentPO> {

    /**
     * 查询给定的 博客的 评论
     *
     * @param params params
     * @return
     * @author Jerry.X.He
     * @date 7/8/2017 1:26 PM
     * @since 1.0
     */
    Result getCommentFor(BeanIdForm params, Page<?> page);

}