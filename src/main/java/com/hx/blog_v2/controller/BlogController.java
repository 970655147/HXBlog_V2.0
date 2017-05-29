package com.hx.blog_v2.controller;

import com.hx.blog_v2.domain.form.BeanIdForm;
import com.hx.blog_v2.domain.form.BlogSearchForm;
import com.hx.blog_v2.domain.vo.BlogVO;
import com.hx.blog_v2.service.interf.BlogService;
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
    public Result list(BlogSearchForm form, SimplePage<BlogVO> page) {

        return blogService.list(form, page);
    }

    @RequestMapping("/get")
    public Result get(BeanIdForm form) {

        return blogService.get(form);
    }

    @RequestMapping("/advices")
    public Result advices() {
        BeanIdForm from = new BeanIdForm("-1");
        return blogService.get(from);
    }

}
