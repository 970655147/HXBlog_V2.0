package com.hx.blog_v2.controller;

import com.hx.blog_v2.service.interf.BlogService;
import com.hx.common.interf.common.Result;
import com.hx.log.validator.ValidateResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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

    @RequestMapping("/get/{id}")
    public Result get(@PathVariable("id") String id) {
        info("get blog of " + id);
        info("blogService is null ? " + blogService == null);

        return ValidateResultUtils.success("this is blog content !");
    }


}
