package com.hx.blog_v2.controller.admin;

import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.DateUtils;
import com.hx.blog_v2.util.WebContext;
import com.hx.common.interf.common.Result;
import com.hx.common.util.ResultUtils;
import com.hx.json.JSONObject;
import com.hx.log.alogrithm.code.Codec;
import com.hx.log.file.FileUtils;
import com.hx.log.util.Tools;
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
@RequestMapping("/admin/image")
public class ImageController {

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Result upload(@RequestParam("file") CommonsMultipartFile file) {
        String relativePath = generateImgPath(file);
        String filePath = Tools.getFilePath(WebContext.getImgRootPath(), relativePath);

        try {
            FileUtils.createIfNotExists(filePath, true);
            file.transferTo(new File(filePath));

            JSONObject data = new JSONObject()
                    .element("originFileName", file.getOriginalFilename()).element("length", file.getSize())
                    .element("contentType", file.getContentType()).element("url", "http://localhost/imgs/" + relativePath);
            return ResultUtils.success(data);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.failed("failed !");
        }
    }

    /**
     * 根据给定的id生成该图片需要保存的路径 [相对]
     *
     * @param file 给定的文件信息
     * @return java.lang.String
     * @author Jerry.X.He
     * @date 5/21/2017 3:09 PM
     * @since 1.0
     */
    private String generateImgPath(CommonsMultipartFile file) {
        String fileName = file.getOriginalFilename(), suffix = fileName.substring(fileName.indexOf("."));
        String dateStr = DateUtils.formate(new Date(), BlogConstants.FORMAT_YYYY_MM_DD );
        String folder = dateStr.replace("-", "/");
        return Tools.getFilePath(folder, Codec.byte2Hex(Codec.md5(fileName.getBytes())) + suffix);
    }

}
