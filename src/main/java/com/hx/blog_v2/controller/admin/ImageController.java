package com.hx.blog_v2.controller.admin;

import com.hx.blog_v2.domain.form.BeanIdForm;
import com.hx.blog_v2.domain.form.ImageSaveForm;
import com.hx.blog_v2.domain.form.ImageSearchForm;
import com.hx.blog_v2.domain.vo.AdminImageVO;
import com.hx.blog_v2.service.interf.ImageService;
import com.hx.common.interf.common.Result;
import com.hx.common.result.SimplePage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * ImageController
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 4:57 PM
 */
@RestController("adminImageController")
@RequestMapping("/admin/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result add(ImageSaveForm params) {

        return imageService.add(params);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result list(ImageSearchForm params, SimplePage<AdminImageVO> page) {

        return imageService.adminList(params, page);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result update(ImageSaveForm params) {

        return imageService.update(params);
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public Result remove(BeanIdForm params) {

        return imageService.remove(params);
    }

    @RequestMapping(value = "/reSort", method = RequestMethod.POST)
    public Result reSort(ImageSearchForm params) {

        return imageService.reSort(params);
    }

}
