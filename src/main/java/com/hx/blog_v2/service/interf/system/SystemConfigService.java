package com.hx.blog_v2.service.interf.system;

import com.hx.blog_v2.domain.form.common.BeanIdForm;
import com.hx.blog_v2.domain.form.system.SystemConfigSaveForm;
import com.hx.blog_v2.domain.form.system.SystemConfigSearchForm;
import com.hx.blog_v2.domain.po.system.SystemConfigPO;
import com.hx.blog_v2.domain.vo.system.SystemConfigVO;
import com.hx.blog_v2.service.interf.BaseService;
import com.hx.common.interf.common.Page;
import com.hx.common.interf.common.Result;

/**
 * SystemConfigService
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 11:48 AM
 */
public interface SystemConfigService extends BaseService<SystemConfigPO> {

    /**
     * 增加一个 系统配置
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result add(SystemConfigSaveForm params);

    /**
     * 搜索符合的条件的系统配置
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result adminList(SystemConfigSearchForm params, Page<SystemConfigVO> page);

    /**
     * 更新给定的系统配置
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result update(SystemConfigSaveForm params);

    /**
     * 移除给定的系统配置
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
