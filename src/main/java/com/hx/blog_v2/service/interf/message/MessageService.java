package com.hx.blog_v2.service.interf.message;

import com.hx.blog_v2.domain.form.common.BeanIdForm;
import com.hx.blog_v2.domain.form.message.MessageSaveForm;
import com.hx.blog_v2.domain.form.message.MessageSearchForm;
import com.hx.blog_v2.domain.po.message.MessagePO;
import com.hx.blog_v2.domain.vo.message.MessageVO;
import com.hx.blog_v2.service.interf.BaseService;
import com.hx.common.interf.common.Page;
import com.hx.common.interf.common.Result;

/**
 * MessageService
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 11:48 AM
 */
public interface MessageService extends BaseService<MessagePO> {

    /**
     * 增加一个 消息
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result add(MessageSaveForm params);

    /**
     * 搜索符合的条件的消息列表
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result list(MessageSearchForm params, Page<MessageVO> page);

    /**
     * 查询未读消息列表
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result unread();

    /**
     * 搜索符合的条件的消息列表
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result adminList(MessageSearchForm params, Page<MessageVO> page);

    /**
     * 更新给定的消息
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result update(MessageSaveForm params);

    /**
     * 将给定的消息 标记为 已读
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result markConsumed(BeanIdForm params);

    /**
     * 将给定所有的的消息 标记为 已读
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result markAllConsumed();

    /**
     * 移除给定的消息
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result remove(BeanIdForm params);

}
