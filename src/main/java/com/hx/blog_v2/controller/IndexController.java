package com.hx.blog_v2.controller;

import com.hx.blog_v2.biz_handler.anno.BizHandle;
import com.hx.blog_v2.service.interf.IndexService;
import com.hx.blog_v2.util.BlogConstants;
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

    @RequestMapping("/index")
    @BizHandle(handler = "blogVisitLogHandler", others = BlogConstants.CONTEXT_BLOG_ID)
    public Result index() {
        return indexService.index();
    }

    @RequestMapping("/latest")
    public Result latest() {
        return indexService.latest();
    }

}
