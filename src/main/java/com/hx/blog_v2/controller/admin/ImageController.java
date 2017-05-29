package com.hx.blog_v2.controller.admin;

import com.hx.blog_v2.domain.form.BeanIdForm;
import com.hx.blog_v2.domain.form.ImageSaveForm;
import com.hx.blog_v2.domain.form.MoodSaveForm;
import com.hx.blog_v2.domain.form.UploadedImageSaveForm;
import com.hx.blog_v2.domain.vo.AdminImageVO;
import com.hx.blog_v2.domain.vo.AdminMoodVO;
import com.hx.blog_v2.service.interf.ImageService;
import com.hx.blog_v2.service.interf.UploadedImageService;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.DateUtils;
import com.hx.blog_v2.util.WebContext;
import com.hx.common.interf.common.Result;
import com.hx.common.result.SimplePage;
import com.hx.common.util.ResultUtils;
import com.hx.json.JSONObject;
import com.hx.log.alogrithm.code.Codec;
import com.hx.log.file.FileUtils;
import com.hx.log.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.util.Date;

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
    private UploadedImageService uploadedImageService;
    @Autowired
    private ImageService imageService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Result upload(UploadedImageSaveForm form) {

        return uploadedImageService.add(form);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result add(ImageSaveForm params) {

        return imageService.add(params);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result list(SimplePage<AdminImageVO> page) {

        return imageService.adminList(page);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result update(ImageSaveForm params) {

        return imageService.update(params);
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public Result remove(BeanIdForm params) {

        return imageService.remove(params);
    }

}
