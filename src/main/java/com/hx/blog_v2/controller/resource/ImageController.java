package com.hx.blog_v2.controller.resource;

import com.hx.blog_v2.biz_log.anno.BizLogger;
import com.hx.blog_v2.domain.common.resources.CheckCode;
import com.hx.blog_v2.domain.form.front_resources.ImageSearchForm;
import com.hx.blog_v2.service.interf.front_resources.ImageService;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.domain.BaseVO;
import com.hx.blog_v2.util.CheckCodeUtils;
import com.hx.blog_v2.context.ConstantsContext;
import com.hx.blog_v2.context.WebContext;
import com.hx.common.interf.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.awt.image.BufferedImage;

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
    @Autowired
    private ConstantsContext constantsContext;
    @Autowired
    private CheckCodeUtils checkCodeUtils;

    @RequestMapping(value = "/imgShowList", method = RequestMethod.GET)
    @BizLogger(req = false, resp = false)
    public Result imgShowList() {
        ImageSearchForm params = new ImageSearchForm(constantsContext.imgTypeImgShow);
        return imageService.imageList(params);
    }

    @RequestMapping(value = "/headImgList", method = RequestMethod.GET)
    @BizLogger(req = false, resp = false)
    public Result headImgList() {
        ImageSearchForm params = new ImageSearchForm(constantsContext.imgTypeHeadImg);
        return imageService.imageList(params);
    }

    @RequestMapping("/checkCode")
    @BizLogger(req = false, resp = false)
    public void checkCode() {
        CheckCode checkCode = checkCodeUtils.getCheckCode(constantsContext.checkCodeImgWidth, constantsContext.checkCodeImgHeight,
                constantsContext.checkCodeImgBgColor, constantsContext.checkCodeImgFont, constantsContext.checkCodeLength,
                constantsContext.checkCodeCandidates, constantsContext.checkCodeMinInterference, constantsContext.checkCodeInterferenceOff);
        WebContext.setAttributeForSession(BlogConstants.SESSION_CHECK_CODE, checkCode.getCheckCode());

        WebContext.responseNoCache();
        WebContext.responseImage((BufferedImage) checkCode.getCheckCodeImg(), "jpg");
    }

}
