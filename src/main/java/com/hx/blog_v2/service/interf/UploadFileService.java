package com.hx.blog_v2.service.interf;

import com.baidu.ueditor.ActionEnter;
import com.hx.blog_v2.domain.form.UploadedImageSaveForm;
import com.hx.blog_v2.domain.po.UploadFilePO;
import com.hx.blog_v2.util.WebContext;
import com.hx.common.interf.common.Result;
import com.hx.json.JSONObject;

import javax.servlet.http.HttpServletRequest;

/**
 * BlogService
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 11:48 AM
 */
public interface UploadFileService extends BaseService<UploadFilePO> {

    /**
     * 增加一个 心情
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result add(UploadedImageSaveForm params);

    /**
     * 处理 ueditor 上传的相关逻辑
     *
     * @return
     * @author Jerry.X.He
     * @date 5/29/2017 7:55 PM
     * @since 1.0
     */
    JSONObject ueditorUpload();

}
