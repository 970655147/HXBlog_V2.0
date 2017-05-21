package com.hx.blog_v2.controller.admin;

import com.hx.common.interf.common.Result;
import com.hx.common.util.ResultUtils;
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
@RequestMapping("/admin/system")
public class SystemController {

    @RequestMapping(value = "/flushCache", method = RequestMethod.POST)
    public Result login() {

        return ResultUtils.success("succ");

    }

}
