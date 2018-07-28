package com.hx.blog_v2.controller.admin.front_resource;

import com.hx.blog_v2.biz_log.anno.BizLogger;
import com.hx.blog_v2.domain.ErrorCode;
import com.hx.blog_v2.domain.form.front_resources.AdvSaveForm;
import com.hx.blog_v2.domain.form.common.BeanIdForm;
import com.hx.blog_v2.domain.validator.front_resources.AdvSaveValidator;
import com.hx.blog_v2.domain.validator.common.BeanIdValidator;
import com.hx.blog_v2.service.interf.front_resources.AdvService;
import com.hx.blog_v2.util.ResultUtils;
import com.hx.common.interf.common.Result;
import com.hx.log.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * AdvController
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 3/3/2018 7:28 PM
 */
@RestController("adminAdvController")
@RequestMapping("/admin/adv")
public class AdvController {

    @Autowired
    private AdvService advService;
    @Autowired
    private AdvSaveValidator advSaveValidator;
    @Autowired
    private BeanIdValidator beanIdValidator;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @BizLogger
    public Result add(AdvSaveForm params) {
        Result errResult = advSaveValidator.validate(params, null);
        if (!errResult.isSuccess()) {
            return errResult;
        }
        if (!Tools.isEmpty(params.getId())) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " id 不为空 ! ");
        }

        return advService.add(params);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @BizLogger(req = false, resp = false)
    public Result list() {
        return advService.adminList();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @BizLogger
    public Result update(AdvSaveForm params) {
        Result errResult = advSaveValidator.validate(params, null);
        if (!errResult.isSuccess()) {
            return errResult;
        }
        if (Tools.isEmpty(params.getId())) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " id 为空 ! ");
        }

        return advService.update(params);
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @BizLogger
    public Result remove(BeanIdForm params) {
        Result errResult = beanIdValidator.validate(params, null);
        if (!errResult.isSuccess()) {
            return errResult;
        }

        return advService.remove(params);
    }

    @RequestMapping(value = "/reSort", method = RequestMethod.POST)
    public Result reSort() {
        return advService.reSort();
    }


}
