package com.hx.blog_v2.service;

import com.hx.blog_v2.domain.form.BlogSenseForm;
import com.hx.blog_v2.domain.po.BlogSensePO;
import com.hx.blog_v2.service.interf.BaseServiceImpl;
import com.hx.blog_v2.service.interf.BlogSenseService;
import com.hx.common.interf.common.Result;
import org.springframework.stereotype.Service;

/**
 * BlogServiceImpl
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 11:47 AM
 */
@Service
public class BlogSenseServiceImpl extends BaseServiceImpl<BlogSensePO> implements BlogSenseService {

    @Override
    public Result sense(BlogSenseForm params) {
        return null;
    }

}
