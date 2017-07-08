package com.hx.blog_v2.dao.interf;

import com.hx.blog_v2.domain.form.BeanIdForm;
import com.hx.blog_v2.domain.po.BlogPO;
import com.hx.common.interf.common.Result;

/**
 * BlogPODao
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 10:37 AM
 */
public interface BlogDao extends BaseDao<BlogPO> {

    /**
     * 查询给定的 博客的 tag列表
     *
     * @param params params
     * @return
     * @author Jerry.X.He
     * @date 7/8/2017 1:26 PM
     * @since 1.0
     */
    Result getTagIdsFor(BeanIdForm params);

}
