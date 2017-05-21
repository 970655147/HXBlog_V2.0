package com.hx.blog_v2.controller;

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
@RestController
@RequestMapping("/type")
public class BlogTypeController {

    @Autowired
    private BlogTypeService blogTypeService;


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result list() {

        return blogTypeService.list();
    }

}
