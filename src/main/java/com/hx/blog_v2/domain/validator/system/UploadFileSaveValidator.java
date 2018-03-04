package com.hx.blog_v2.domain.validator.system;

import com.baidu.ueditor.utils.FileUtils;
import com.hx.blog_v2.domain.ErrorCode;
import com.hx.blog_v2.domain.form.system.UploadedFileSaveForm;
import com.hx.blog_v2.domain.validator.interf.ConfigRefreshableValidator;
import com.hx.common.interf.common.Result;
import com.hx.common.interf.validator.Validator;
import com.hx.blog_v2.util.ResultUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * UploadFileSaveValidator
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/15/2017 8:25 PM
 */
@Component
public class UploadFileSaveValidator extends ConfigRefreshableValidator<UploadedFileSaveForm> implements Validator<UploadedFileSaveForm> {

    /**
     * 最小长度, 最大长度
     */
    private long minLen = -1;
    private long maxLen = -1;
    private String supportedSuffixes = null;

    @Override
    public Result doValidate(UploadedFileSaveForm form, Object extra) {
        MultipartFile file = form.getFile();
        String fileName = file.getOriginalFilename();
        String suffix = FileUtils.getSuffixByFilename(fileName, ".");

        if (!supportedSuffixes.contains(suffix)) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " 给定的文件类型当前系统不支持 ! ");
        }
        long sz = file.getSize();
        if ((sz >= minLen) && (sz <= maxLen)) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " 给定的文件超过了系统可接受的大小 ! ");
        }

        return ResultUtils.success();
    }

    @Override
    public boolean needRefresh() {
        return (minLen < 0) || (supportedSuffixes == null);
    }

    @Override
    public void refreshConfig() {
        minLen = Long.parseLong(constantsContext.ruleConfig("file.upload.min.length", "3"));
        maxLen = Long.parseLong(constantsContext.ruleConfig("file.upload.max.length", "20971520"));
        supportedSuffixes = constantsContext.ruleConfig("file.upload.supported.types", ".txt|.html|.css|.js|.java|.php|.c|.h|.cpp");
    }
}