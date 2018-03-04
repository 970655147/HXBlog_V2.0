package com.hx.blog_v2.dao.interf;

import com.hx.blog_v2.domain.po.system.UploadFilePO;
import com.hx.common.interf.common.Result;
import org.springframework.web.multipart.MultipartFile;

/**
 * BlogPODao
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 10:37 AM
 */
public interface UploadFileDao extends BaseDao<UploadFilePO> {

    /**
     * 根据给定的条件, 获取一个 po
     *
     * @return
     * @author Jerry.X.He
     * @date 6/9/2017 9:07 PM
     * @since 1.0
     */
    Result get(MultipartFile file, String digest);


}
