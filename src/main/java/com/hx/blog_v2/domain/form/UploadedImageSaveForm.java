package com.hx.blog_v2.domain.form;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * UploadedImageSaveForm
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/29/2017 4:20 PM
 */
public class UploadedImageSaveForm {

    private CommonsMultipartFile file;

    public UploadedImageSaveForm() {
    }

    public CommonsMultipartFile getFile() {
        return file;
    }

    public void setFile(CommonsMultipartFile file) {
        this.file = file;
    }
}
