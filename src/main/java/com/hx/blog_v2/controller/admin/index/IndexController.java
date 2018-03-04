package com.hx.blog_v2.controller.admin.index;

import com.hx.blog_v2.context.WebContext;
import com.hx.blog_v2.service.interf.index.IndexService;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.common.interf.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * IndexController
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 9:04 AM
 */
@RestController("adminIndexController")
@RequestMapping("/admin/index")
public class IndexController {

    @Autowired
    private IndexService indexService;

    @RequestMapping("/menus")
    public Result index() {
        return indexService.adminMenus();
    }

    @RequestMapping("/statistics")
    public Result statistics() {
        Result result = indexService.adminStatistics();
        result.setExtra(WebContext.getAttributeFromSession(BlogConstants.SESSION_USER));
        return result;
    }

}
