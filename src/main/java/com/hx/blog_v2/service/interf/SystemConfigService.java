package com.hx.blog_v2.service.interf;

import com.hx.blog_v2.domain.form.BeanIdForm;
import com.hx.blog_v2.domain.form.SystemConfigSaveForm;
import com.hx.blog_v2.domain.form.SystemConfigSearchForm;
import com.hx.blog_v2.domain.po.SystemConfigPO;
import com.hx.blog_v2.domain.vo.SystemConfigVO;
import com.hx.common.interf.common.Page;
import com.hx.common.interf.common.Result;

/**
 * BlogService
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 11:48 AM
 */
public interface SystemConfigService extends BaseService<SystemConfigPO> {

    /**
     * 增加一个 图片墙照片
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result add(SystemConfigSaveForm params);

    /**
     * 搜索符合的条件的照片列表
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result adminList(SystemConfigSearchForm params, Page<SystemConfigVO> page);

    /**
     * 更新给定的照片
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result update(SystemConfigSaveForm params);

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
    Result reSort(SystemConfigSearchForm params);

}
