package com.hx.blog_v2.controller.admin.msg;

import com.hx.blog_v2.context.WebContext;
import com.hx.blog_v2.domain.ErrorCode;
import com.hx.blog_v2.domain.form.common.BeanIdForm;
import com.hx.blog_v2.domain.form.message.MessageSaveForm;
import com.hx.blog_v2.domain.form.message.MessageSearchForm;
import com.hx.blog_v2.domain.validator.common.BeanIdValidator;
import com.hx.blog_v2.domain.validator.message.MessageSaveValidator;
import com.hx.blog_v2.domain.validator.message.MessageSearchValidator;
import com.hx.blog_v2.domain.validator.common.PageValidator;
import com.hx.blog_v2.domain.vo.message.MessageVO;
import com.hx.blog_v2.service.interf.message.MessageService;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.common.interf.common.Result;
import com.hx.common.result.SimplePage;
import com.hx.blog_v2.util.ResultUtils;
import com.hx.log.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * MessageController
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 4:57 PM
 */
@RestController("adminMessageController")
@RequestMapping("/admin/message")
public class MessageController {

    @Autowired
    private MessageService messageService;
    @Autowired
    private MessageSaveValidator messageSaveValidator;
    @Autowired
    private MessageSearchValidator messageSearchValidator;
    @Autowired
    private BeanIdValidator beanIdValidator;
    @Autowired
    private PageValidator pageValidator;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result add(MessageSaveForm params) {
        Result errResult = messageSaveValidator.validate(params, null);
        if (!errResult.isSuccess()) {
            return errResult;
        }
        if (!Tools.isEmpty(params.getId())) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " id 不为空 ! ");
        }

        params.setSenderId(WebContext.getStrAttrFromSession(BlogConstants.SESSION_USER_ID));
        return messageService.add(params);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result list(MessageSearchForm params, SimplePage<MessageVO> page) {
        Result errResult = messageSearchValidator.validate(params, null);
        if (!errResult.isSuccess()) {
            return errResult;
        }
        errResult = pageValidator.validate(page, null);
        if (!errResult.isSuccess()) {
            return errResult;
        }

        return messageService.list(params, page);
    }

    @RequestMapping(value = "/unread", method = RequestMethod.GET)
    public Result unread() {
        return messageService.unread();
    }

    @RequestMapping(value = "/adminList", method = RequestMethod.GET)
    public Result adminList(MessageSearchForm params, SimplePage<MessageVO> page) {
        Result errResult = messageSearchValidator.validate(params, null);
        if (!errResult.isSuccess()) {
            return errResult;
        }
        errResult = pageValidator.validate(page, null);
        if (!errResult.isSuccess()) {
            return errResult;
        }

        return messageService.adminList(params, page);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result update(MessageSaveForm params) {
        Result errResult = messageSaveValidator.validate(params, null);
        if (!errResult.isSuccess()) {
            return errResult;
        }
        if (Tools.isEmpty(params.getId())) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " id 为空 ! ");
        }

        return messageService.update(params);
    }

    @RequestMapping(value = "/markConsumed", method = RequestMethod.POST)
    public Result markConsumed(BeanIdForm params) {
        Result errResult = beanIdValidator.validate(params, null);
        if (!errResult.isSuccess()) {
            return errResult;
        }

        return messageService.markConsumed(params);
    }

    @RequestMapping(value = "/markAllConsumed", method = RequestMethod.POST)
    public Result markAllConsumed() {
        return messageService.markAllConsumed();
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public Result remove(BeanIdForm params) {
        Result errResult = beanIdValidator.validate(params, null);
        if (!errResult.isSuccess()) {
            return errResult;
        }

        return messageService.remove(params);
    }

}
