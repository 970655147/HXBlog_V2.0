package com.hx.blog_v2.controller.admin;

import com.hx.blog_v2.domain.form.BeanIdForm;
import com.hx.blog_v2.domain.form.MessageSaveForm;
import com.hx.blog_v2.domain.form.MessageSearchForm;
import com.hx.blog_v2.domain.vo.MessageVO;
import com.hx.blog_v2.service.interf.MessageService;
import com.hx.common.interf.common.Result;
import com.hx.common.result.SimplePage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * ImageController
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

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result add(MessageSaveForm params) {

        return messageService.add(params);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result list(MessageSearchForm params, SimplePage<MessageVO> page) {

        return messageService.list(params, page);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result update(MessageSaveForm params) {

        return messageService.update(params);
    }

    @RequestMapping(value = "/markConsumed", method = RequestMethod.POST)
    public Result markConsumed(BeanIdForm params) {

        return messageService.markConsumed(params);
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public Result remove(BeanIdForm params) {

        return messageService.remove(params);
    }

}
