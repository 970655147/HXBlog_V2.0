package com.hx.blog_v2.controller.admin;

import com.hx.blog_v2.domain.form.AdminBlogSearchForm;
import com.hx.blog_v2.domain.form.BeanIdForm;
import com.hx.blog_v2.domain.form.BlogSaveForm;
import com.hx.blog_v2.domain.vo.AdminBlogVO;
import com.hx.blog_v2.service.interf.BlogService;
import com.hx.common.interf.common.Result;
import com.hx.common.result.SimplePage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * SystemController
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 4:57 PM
 */
@RestController("adminBlogController")
@RequestMapping("/admin/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(BlogSaveForm params) {

        return blogService.save(params);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public Result get(BeanIdForm params) {

        return blogService.adminGet(params);
    }


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result list(AdminBlogSearchForm params, SimplePage<AdminBlogVO> page) {

        return blogService.adminList(params, page);
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public Result remove(BeanIdForm params) {

        return blogService.remove(params);
    }

}
