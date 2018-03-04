package com.hx.blog_v2.service.interf.front_resources;

import com.hx.blog_v2.domain.form.front_resources.AdvListForm;
import com.hx.blog_v2.domain.form.front_resources.AdvSaveForm;
import com.hx.blog_v2.domain.form.common.BeanIdForm;
import com.hx.blog_v2.domain.po.front_resources.AdvPO;
import com.hx.blog_v2.service.interf.BaseService;
import com.hx.common.interf.common.Result;

/**
 * AdvService
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 3/3/2018 5:45 PM
 */
public interface AdvService extends BaseService<AdvPO> {

    /**
     * 增加一个 Adv
     *
     * @return result
     * @author Jerry.X.He
     * @date 3/3/2018 7:42 PM
     * @since 1.0
     */
    Result add(AdvSaveForm params);


    /**
     * 根据给定的条件 获取需要展示的 广告信息
     *
     * @return result
     * @author Jerry.X.He
     * @date 3/3/2018 5:45 PM
     * @since 1.0
     */
    Result list(AdvListForm params);

    /**
     * 获取所有的 Adv
     *
     * @return result
     * @author Jerry.X.He
     * @date 3/3/2018 7:42 PM
     * @since 1.0
     */
    Result adminList();

    /**
     * 更新一个 Adv
     *
     * @return result
     * @author Jerry.X.He
     * @date 3/3/2018 7:42 PM
     * @since 1.0
     */
    Result update(AdvSaveForm params);

    /**
     * 移除一个 Adv
     *
     * @return result
     * @author Jerry.X.He
     * @date 3/3/2018 7:42 PM
     * @since 1.0
     */
    Result remove(BeanIdForm params);

    /**
     * 对当前所有的adv 进行重排
     *
     * @return result
     * @author Jerry.X.He
     * @date 3/3/2018 7:42 PM
     * @since 1.0
     */
    Result reSort();

}
