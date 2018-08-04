package com.hx.blog_v2.domain.form.system;

import com.hx.blog_v2.domain.BaseForm;
import org.springframework.web.multipart.MultipartFile;

/**
 * UploadedImageSaveForm
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/29/2017 4:20 PM
 */
public class UploadedImageSaveForm extends BaseForm {

    private MultipartFile file;

    public UploadedImageSaveForm(MultipartFile file) {
        this.file = file;
    }

    public UploadedImageSaveForm() {
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
