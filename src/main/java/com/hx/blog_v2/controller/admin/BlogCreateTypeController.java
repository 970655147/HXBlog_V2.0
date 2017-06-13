package com.hx.blog_v2.controller.admin;

import com.hx.blog_v2.domain.form.BeanIdForm;
import com.hx.blog_v2.domain.form.BlogCreateTypeSaveForm;
import com.hx.blog_v2.service.interf.BlogCreateTypeService;
import com.hx.common.interf.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * ImageController
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 4:57 PM
 */
@RestController("adminBlogCreateTypeController")
@RequestMapping("/admin/blog/createType")
public class BlogCreateTypeController {

    @Autowired
    private BlogCreateTypeService blogCreateTypeService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result add(BlogCreateTypeSaveForm params) {

        return blogCreateTypeService.add(params);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result list() {

        return blogCreateTypeService.adminList();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result update(BlogCreateTypeSaveForm params) {

        return blogCreateTypeService.update(params);
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public Result remove(BeanIdForm params) {

        return blogCreateTypeService.remove(params);
    }

    @RequestMapping(value = "/reSort", method = RequestMethod.POST)
    public Result reSort() {

        return blogCreateTypeService.reSort();
    }

}
