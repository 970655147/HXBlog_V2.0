package com.hx.blog_v2.service.interf.front_resources;

import com.hx.blog_v2.domain.form.common.BeanIdForm;
import com.hx.blog_v2.domain.form.front_resources.ImageSaveForm;
import com.hx.blog_v2.domain.form.front_resources.ImageSearchForm;
import com.hx.blog_v2.domain.po.front_resources.ImagePO;
import com.hx.blog_v2.domain.vo.front_resources.AdminImageVO;
import com.hx.blog_v2.service.interf.BaseService;
import com.hx.common.interf.common.Page;
import com.hx.common.interf.common.Result;

/**
 * ImageService
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 11:48 AM
 */
public interface ImageService extends BaseService<ImagePO> {

    /**
     * 增加一个 图片墙照片
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result add(ImageSaveForm params);

    /**
     * 搜索符合的条件的照片列表
     *
     * @param params 搜索的图片的参数
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result imageList(ImageSearchForm params);

    /**
     * 搜索符合的条件的照片列表
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result adminList(ImageSearchForm params, Page<AdminImageVO> page);

    /**
     * 更新给定的照片
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result update(ImageSaveForm params);

    /**
     * 移除给定的照片
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result remove(BeanIdForm params);

    /**
     * 重排给定的资源
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result reSort(ImageSearchForm params);

}
