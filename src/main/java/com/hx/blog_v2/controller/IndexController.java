package com.hx.blog_v2.controller;

import com.hx.blog_v2.service.interf.IndexService;
import com.hx.blog_v2.util.CacheContext;
import com.hx.common.interf.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * ViewController
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 9:04 AM
 */
@RestController
@RequestMapping("/index")
public class IndexController {

    @Autowired
    private IndexService indexService;

    @RequestMapping("index")
    public Result index() {

        return indexService.index();
    }

}
