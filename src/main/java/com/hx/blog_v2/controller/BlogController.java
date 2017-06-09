package com.hx.blog_v2.controller;

import com.hx.blog_v2.biz_handler.anno.BizHandle;
import com.hx.blog_v2.domain.form.BeanIdForm;
import com.hx.blog_v2.domain.form.BlogSearchForm;
import com.hx.blog_v2.domain.dto.SessionUser;
import com.hx.blog_v2.domain.vo.BlogVO;
import com.hx.blog_v2.service.interf.BlogService;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.WebContext;
import com.hx.common.interf.common.Result;
import com.hx.common.result.SimplePage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.hx.log.util.Log.info;

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

    @RequestMapping("/list")
    public Result list(BlogSearchForm prams, SimplePage<BlogVO> page) {

        return blogService.list(prams, page);
    }

    @RequestMapping("/get")
    @BizHandle(handler = "blogVisitLogHandler")
    public Result get(BeanIdForm prams) {

        Result result = blogService.get(prams);
        SessionUser user = (SessionUser) WebContext.getAttributeFromSession(BlogConstants.SESSION_USER);
        result.setExtra(user);
        return result;
    }

    @RequestMapping("/advices")
    public Result advices() {

        BeanIdForm prams = new BeanIdForm(BlogConstants.ADVICE_BLOG_ID);
        Result result = blogService.get(prams);
        SessionUser user = (SessionUser) WebContext.getAttributeFromSession(BlogConstants.SESSION_USER);
        result.setExtra(user);
        return result;
    }

}
