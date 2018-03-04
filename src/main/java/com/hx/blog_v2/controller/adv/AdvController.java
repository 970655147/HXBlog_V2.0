package com.hx.blog_v2.controller.adv;

import com.hx.blog_v2.domain.form.adv.AdvListForm;
import com.hx.blog_v2.domain.validator.adv.AdvListValidator;
import com.hx.blog_v2.service.interf.adv.AdvService;
import com.hx.common.interf.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AdvController
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 3/3/2018 5:24 PM
 */
@RestController
@RequestMapping("/adv")
public class AdvController {

    @Autowired
    private AdvService advService;
    @Autowired
    private AdvListValidator advListValidator;

    @RequestMapping("/list")
    public Result list(AdvListForm params) {
        Result errResult = advListValidator.validate(params, null);
        if(! errResult.isSuccess()) {
            return errResult;
        }

        return advService.list(params);
    }

}
