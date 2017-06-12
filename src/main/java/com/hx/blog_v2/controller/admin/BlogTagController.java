package com.hx.blog_v2.controller.admin;

import com.hx.blog_v2.domain.form.BlogTagSaveForm;
import com.hx.blog_v2.service.interf.BlogTagService;
import com.hx.common.interf.common.Result;
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
@RestController("adminBlogTagController")
@RequestMapping("/admin/tag")
public class BlogTagController {

    @Autowired
    private BlogTagService blogTagService;


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result add(BlogTagSaveForm params) {


        return blogTagService.add(params);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result list() {

        return blogTagService.list();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result update(BlogTagSaveForm params) {

        return blogTagService.update(params);
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public Result remove(BlogTagSaveForm params) {

        return blogTagService.remove(params);
    }

    @RequestMapping(value = "/reSort", method = RequestMethod.POST)
    public Result reSort() {

        return blogTagService.reSort();
    }


}
