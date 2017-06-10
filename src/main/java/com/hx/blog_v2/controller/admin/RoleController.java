package com.hx.blog_v2.controller.admin;

import com.hx.blog_v2.domain.form.BeanIdForm;
import com.hx.blog_v2.domain.form.MoodSaveForm;
import com.hx.blog_v2.domain.form.RoleSaveForm;
import com.hx.blog_v2.domain.form.UserRoleUpdateForm;
import com.hx.blog_v2.domain.vo.AdminMoodVO;
import com.hx.blog_v2.domain.vo.AdminRoleVO;
import com.hx.blog_v2.domain.vo.UserRoleVO;
import com.hx.blog_v2.service.interf.MoodService;
import com.hx.blog_v2.service.interf.RoleService;
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
@RestController("adminRoleController")
@RequestMapping("/admin/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result add(RoleSaveForm params) {

        return roleService.add(params);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result list() {

        return roleService.adminList();
    }

    @RequestMapping(value = "/userRole/list", method = RequestMethod.GET)
    public Result userRoleList(SimplePage<UserRoleVO> page) {

        return roleService.userRoleList(page);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result update(RoleSaveForm params) {

        return roleService.update(params);
    }

    @RequestMapping(value = "/userRole/update", method = RequestMethod.POST)
    public Result userRoleUpdate(UserRoleUpdateForm params) {

        return roleService.userRoleUpdate(params);
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public Result remove(BeanIdForm params) {

        return roleService.remove(params);
    }


    @RequestMapping(value = "/reSort", method = RequestMethod.POST)
    public Result reSort() {

        return roleService.reSort();
    }


}
