package com.hx.blog_v2.controller.admin;

import com.hx.blog_v2.domain.form.BeanIdForm;
import com.hx.blog_v2.domain.form.LinkSaveForm;
import com.hx.blog_v2.domain.vo.AdminLinkVO;
import com.hx.blog_v2.service.interf.LinkService;
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
@RestController("adminLinkController")
@RequestMapping("/admin/link")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result add(LinkSaveForm params) {

        return linkService.add(params);
    }


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result list(SimplePage<AdminLinkVO> page) {

        return linkService.adminList(page);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result update(LinkSaveForm params) {

        return linkService.update(params);
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public Result remove(BeanIdForm params) {

        return linkService.remove(params);
    }

}
