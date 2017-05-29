package com.hx.blog_v2.service.interf;

import com.hx.blog_v2.domain.form.MoodSaveForm;
import com.hx.blog_v2.domain.form.UploadedImageSaveForm;
import com.hx.blog_v2.domain.po.UploadedImagePO;
import com.hx.common.interf.common.Result;

/**
 * BlogService
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 11:48 AM
 */
public interface UploadedImageService extends BaseService<UploadedImagePO> {

    /**
     * 增加一个 心情
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result add(UploadedImageSaveForm params);

}
