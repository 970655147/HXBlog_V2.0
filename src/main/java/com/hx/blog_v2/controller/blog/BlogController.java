package com.hx.blog_v2.controller.blog;

import com.hx.blog_v2.biz_handler.anno.BizHandle;
import com.hx.blog_v2.domain.common.system.SessionUser;
import com.hx.blog_v2.domain.form.common.BeanIdForm;
import com.hx.blog_v2.domain.form.blog.BlogSearchForm;
import com.hx.blog_v2.domain.validator.common.BeanIdValidator;
import com.hx.blog_v2.domain.validator.blog.BlogSearchValidator;
import com.hx.blog_v2.domain.validator.common.PageValidator;
import com.hx.blog_v2.domain.vo.blog.BlogVO;
import com.hx.blog_v2.service.interf.blog.BlogService;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.context.WebContext;
import com.hx.common.interf.common.Result;
import com.hx.common.result.SimplePage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * BlogController
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 9:04 AM
 */
@RestController
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private BlogSearchValidator blogSearchValidator;
    @Autowired
    private BeanIdValidator beanIdValidator;
    @Autowired
    private PageValidator pageValidator;

    @RequestMapping("/list")
    public Result list(BlogSearchForm params, SimplePage<BlogVO> page) {
        Result errResult = blogSearchValidator.validate(params, null);
        if (!errResult.isSuccess()) {
            return errResult;
        }
        errResult = pageValidator.validate(page, null);
        if (!errResult.isSuccess()) {
            return errResult;
        }

        return blogService.list(params, page);
    }

    @RequestMapping("/get")
    @BizHandle(handler = "blogVisitLogHandler")
    public Result get(BeanIdForm params) {
        Result errResult = beanIdValidator.validate(params, null);
        if (!errResult.isSuccess()) {
            return errResult;
        }

        Result result = blogService.get(params);
        SessionUser user = (SessionUser) WebContext.getAttributeFromSession(BlogConstants.SESSION_USER);
        result.setExtra(user);
        return result;
    }


}
