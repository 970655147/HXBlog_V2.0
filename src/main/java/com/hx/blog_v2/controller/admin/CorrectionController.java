package com.hx.blog_v2.controller.admin;

import com.hx.blog_v2.domain.form.CorrectionSearchForm;
import com.hx.blog_v2.domain.form.DoCorrectionForm;
import com.hx.blog_v2.domain.validator.CorrectionSearchValidator;
import com.hx.blog_v2.domain.validator.DoCorrectionValidator;
import com.hx.blog_v2.service.interf.CorrectionService;
import com.hx.common.interf.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 校正数据的 controller
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/18/2017 6:56 PM
 */
@RestController("adminCorrectionController")
@RequestMapping("/admin/correction")
public class CorrectionController {

    @Autowired
    private CorrectionService correctionService;
    @Autowired
    private CorrectionSearchValidator correctionSearchValidator;
    @Autowired
    private DoCorrectionValidator doCorrectionValidator;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result list(CorrectionSearchForm params) {
        Result errResult = correctionSearchValidator.validate(params, null);
        if (!errResult.isSuccess()) {
            return errResult;
        }

        return correctionService.list(params);
    }

    @RequestMapping(value = "/doCorrection", method = RequestMethod.POST)
    public Result doCorrection(DoCorrectionForm params) {
        Result errResult = doCorrectionValidator.validate(params, null);
        if (!errResult.isSuccess()) {
            return errResult;
        }

        return correctionService.doCorrection(params);
    }


}
