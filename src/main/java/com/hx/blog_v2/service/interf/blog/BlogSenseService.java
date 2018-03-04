package com.hx.blog_v2.service.interf.blog;

import com.hx.blog_v2.domain.form.blog.BlogSenseForm;
import com.hx.blog_v2.domain.po.blog.BlogSensePO;
import com.hx.blog_v2.service.interf.BaseService;
import com.hx.common.interf.common.Result;

/**
 * BlogSenseService
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 11:48 AM
 */
public interface BlogSenseService extends BaseService<BlogSensePO> {

    /**
     * 为博客点赞 或者取消点赞
     *
     * @param params params
     * @return
     * @author Jerry.X.He
     * @date 6/5/2017 8:46 PM
     * @since 1.0
     */
    Result sense(BlogSenseForm params);

}
