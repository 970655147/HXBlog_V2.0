package com.hx.blog_v2.controller;

import com.hx.blog_v2.domain.form.BeanIdForm;
import com.hx.blog_v2.domain.form.ImageSaveForm;
import com.hx.blog_v2.domain.vo.AdminImageVO;
import com.hx.blog_v2.domain.vo.ImageVO;
import com.hx.blog_v2.service.interf.ImageService;
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
@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result list() {

        return imageService.list();
    }

}
