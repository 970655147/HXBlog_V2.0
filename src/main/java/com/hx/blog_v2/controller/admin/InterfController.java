package com.hx.blog_v2.controller.admin;

import com.hx.blog_v2.domain.form.BeanIdForm;
import com.hx.blog_v2.domain.form.InterfSaveForm;
import com.hx.blog_v2.domain.form.ResourceInterfUpdateForm;
import com.hx.blog_v2.service.interf.InterfService;
import com.hx.common.interf.common.Result;
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
@RestController("adminInterfController")
@RequestMapping("/admin/interf")
public class InterfController {

    @Autowired
    private InterfService interfService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result add(InterfSaveForm params) {

        return interfService.add(params);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result list() {

        return interfService.adminList();
    }

    @RequestMapping(value = "/resourceInterf/list", method = RequestMethod.GET)
    public Result resourceInterfList() {

        return interfService.resourceInterfList();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result update(InterfSaveForm params) {

        return interfService.update(params);
    }

    @RequestMapping(value = "/resourceInterf/update", method = RequestMethod.POST)
    public Result userRoleUpdate(ResourceInterfUpdateForm params) {

        return interfService.userRoleUpdate(params);
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public Result remove(BeanIdForm params) {

        return interfService.remove(params);
    }

}
