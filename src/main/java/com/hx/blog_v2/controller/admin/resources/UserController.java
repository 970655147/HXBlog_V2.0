package com.hx.blog_v2.controller.admin.resources;

import com.hx.blog_v2.biz_log.anno.BizLogger;
import com.hx.blog_v2.domain.ErrorCode;
import com.hx.blog_v2.domain.form.common.BeanIdForm;
import com.hx.blog_v2.domain.form.system.LoginForm;
import com.hx.blog_v2.domain.form.system.UpdatePwdForm;
import com.hx.blog_v2.domain.form.resources.UserSaveForm;
import com.hx.blog_v2.domain.validator.common.BeanIdValidator;
import com.hx.blog_v2.domain.validator.common.PageValidator;
import com.hx.blog_v2.domain.validator.others.UpdatePwdValidator;
import com.hx.blog_v2.domain.validator.resources.UserSaveValidator;
import com.hx.blog_v2.domain.validator.system.LoginFormValidator;
import com.hx.blog_v2.domain.vo.resources.AdminUserVO;
import com.hx.blog_v2.service.interf.resources.UserService;
import com.hx.common.interf.common.Result;
import com.hx.common.result.SimplePage;
import com.hx.blog_v2.util.ResultUtils;
import com.hx.log.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserController
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
    @Autowired
    private UserSaveValidator userSaveValidator;
    @Autowired
    private UpdatePwdValidator updatePwdValidator;
    @Autowired
    private LoginFormValidator loginFormValidator;
    @Autowired
    private BeanIdValidator beanIdValidator;
    @Autowired
    private PageValidator pageValidator;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @BizLogger
    public Result add(UserSaveForm params) {
        Result errResult = userSaveValidator.validate(params, null);
        if (!errResult.isSuccess()) {
            return errResult;
        }
        if (!Tools.isEmpty(params.getId())) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " id 不为空 ! ");
        }

        return userService.add(params);
    }


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @BizLogger(req = false, resp = false)
    public Result list(SimplePage<AdminUserVO> page) {
        Result errResult = pageValidator.validate(page, null);
        if (!errResult.isSuccess()) {
            return errResult;
        }

        return userService.adminList(page);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @BizLogger
    public Result update(UserSaveForm params) {
        Result errResult = userSaveValidator.validate(params, null);
        if (!errResult.isSuccess()) {
            return errResult;
        }
        if (Tools.isEmpty(params.getId())) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " id 为空 ! ");
        }

        return userService.update(params);
    }

    @RequestMapping(value = "/updatePwd", method = RequestMethod.POST)
    @BizLogger
    public Result updatePwd(UpdatePwdForm params) {
        Result errResult = updatePwdValidator.validate(params, null);
        if (!errResult.isSuccess()) {
            return errResult;
        }

        return userService.updatePwd(params);
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @BizLogger
    public Result remove(BeanIdForm params) {
        Result errResult = beanIdValidator.validate(params, null);
        if (!errResult.isSuccess()) {
            return errResult;
        }

        return userService.remove(params);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @BizLogger(req = false, resp = false)
    public Result login(LoginForm params) {
        Result errResult = loginFormValidator.validate(params, null);
        if (!errResult.isSuccess()) {
            return errResult;
        }

        return userService.login(params);

    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @BizLogger(req = false, resp = false)
    public Result logout() {
        return userService.logout();
    }

}
