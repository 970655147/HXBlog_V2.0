package com.hx.blog_v2.controller;

import com.hx.blog_v2.domain.form.ImageSearchForm;
import com.hx.blog_v2.service.interf.ImageService;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.common.interf.common.Result;
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
@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @RequestMapping(value = "/imgShowList", method = RequestMethod.GET)
    public Result imgShowList() {

        ImageSearchForm params = new ImageSearchForm(BlogConstants.IMG_TYPE_IMG_SHOW);
        return imageService.imageList(params);
    }

    @RequestMapping(value = "/headImgList", method = RequestMethod.GET)
    public Result headImgList() {

        ImageSearchForm params = new ImageSearchForm(BlogConstants.IMG_TYPE_IMG_SHOW);
        return imageService.imageList(params);
    }

}
