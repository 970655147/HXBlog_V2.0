package com.hx.blog_v2.controller.admin;

import com.hx.blog_v2.biz_handler.anno.BizHandle;
import com.hx.blog_v2.biz_handler.handler.AuthorityUpdateHandler;
import com.hx.blog_v2.domain.ErrorCode;
import com.hx.blog_v2.domain.form.BeanIdForm;
import com.hx.blog_v2.domain.form.RoleSaveForm;
import com.hx.blog_v2.domain.form.UserRoleUpdateForm;
import com.hx.blog_v2.domain.validator.BeanIdValidator;
import com.hx.blog_v2.domain.validator.PageValidator;
import com.hx.blog_v2.domain.validator.RoleSaveValidator;
import com.hx.blog_v2.domain.validator.UserRoleUpdateValidator;
import com.hx.blog_v2.domain.vo.UserRoleVO;
import com.hx.blog_v2.service.interf.RoleService;
import com.hx.common.interf.common.Result;
import com.hx.common.result.SimplePage;
import com.hx.common.util.ResultUtils;
import com.hx.log.util.Tools;
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
@RestController("adminRoleController")
@RequestMapping("/admin/role")
public class RoleController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private RoleSaveValidator roleSaveValidator;
    @Autowired
    private BeanIdValidator beanIdValidator;
    @Autowired
    private UserRoleUpdateValidator userRoleUpdateValidator;
    @Autowired
    private PageValidator pageValidator;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result add(RoleSaveForm params) {
        Result errResult = roleSaveValidator.validate(params, null);
        if (!errResult.isSuccess()) {
            return errResult;
        }
        if (!Tools.isEmpty(params.getId())) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " id 不为空 ! ");
        }

        return roleService.add(params);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result list() {
        return roleService.adminList();
    }

    @RequestMapping(value = "/userRole/list", method = RequestMethod.GET)
    public Result userRoleList(SimplePage<UserRoleVO> page) {
        Result errResult = pageValidator.validate(page, null);
        if (!errResult.isSuccess()) {
            return errResult;
        }

        return roleService.userRoleList(page);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result update(RoleSaveForm params) {
        Result errResult = roleSaveValidator.validate(params, null);
        if (!errResult.isSuccess()) {
            return errResult;
        }
        if (Tools.isEmpty(params.getId())) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " id 为空 ! ");
        }

        return roleService.update(params);
    }

    @RequestMapping(value = "/userRole/update", method = RequestMethod.POST)
    @BizHandle(handler = "authorityUpdateHandler", others = AuthorityUpdateHandler.USER_ROLE)
    public Result userRoleUpdate(UserRoleUpdateForm params) {
        Result errResult = userRoleUpdateValidator.validate(params, null);
        if (!errResult.isSuccess()) {
            return errResult;
        }

        return roleService.userRoleUpdate(params);
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public Result remove(BeanIdForm params) {
        Result errResult = beanIdValidator.validate(params, null);
        if (!errResult.isSuccess()) {
            return errResult;
        }

        return roleService.remove(params);
    }


    @RequestMapping(value = "/reSort", method = RequestMethod.POST)
    public Result reSort() {
        return roleService.reSort();
    }


}
