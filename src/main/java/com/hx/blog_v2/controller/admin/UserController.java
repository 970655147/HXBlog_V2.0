package com.hx.blog_v2.controller.admin;

import com.hx.blog_v2.domain.form.BeanIdForm;
import com.hx.blog_v2.domain.form.LoginForm;
import com.hx.blog_v2.domain.form.UpdatePwdForm;
import com.hx.blog_v2.domain.form.UserSaveForm;
import com.hx.blog_v2.domain.vo.AdminUserVO;
import com.hx.blog_v2.service.interf.UserService;
import com.hx.common.interf.common.Result;
import com.hx.common.result.SimplePage;
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
@RestController("adminUserController")
@RequestMapping("/admin/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result add(UserSaveForm params) {

        return userService.add(params);
    }


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result list(SimplePage<AdminUserVO> page) {

        return userService.adminList(page);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result update(UserSaveForm params) {

        return userService.update(params);
    }

    @RequestMapping(value = "/updatePwd", method = RequestMethod.POST)
    public Result updatePwd(UpdatePwdForm params) {

        return userService.updatePwd(params);
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public Result remove(BeanIdForm params) {

        return userService.remove(params);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(LoginForm params) {

        return userService.login(params);

    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public Result logout() {

        return userService.logout();

    }

}
