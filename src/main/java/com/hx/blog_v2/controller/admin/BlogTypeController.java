package com.hx.blog_v2.controller.admin;

import com.hx.blog_v2.domain.form.BlogTypeSaveForm;
import com.hx.blog_v2.service.interf.BlogTypeService;
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
@RestController("adminBlogTypeController")
@RequestMapping("/admin/type")
public class BlogTypeController {

    @Autowired
    private BlogTypeService blogTypeService;


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result add(BlogTypeSaveForm params) {


        Result result = blogTypeService.add(params);
        return result;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result update(BlogTypeSaveForm params) {

        return blogTypeService.update(params);
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public Result remove(BlogTypeSaveForm params) {

        return blogTypeService.remove(params);
    }

}
