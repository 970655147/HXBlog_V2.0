package com.hx.blog_v2.controller;

import com.hx.blog_v2.domain.form.LoginForm;
import com.hx.common.interf.common.Result;
import com.hx.common.util.ResultUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserController
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 4:43 PM
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(LoginForm params) {

        return ResultUtils.success("succ");

    }


}
