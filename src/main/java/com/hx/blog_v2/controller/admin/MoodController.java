package com.hx.blog_v2.controller.admin;

import com.hx.blog_v2.domain.ErrorCode;
import com.hx.blog_v2.domain.form.BeanIdForm;
import com.hx.blog_v2.domain.form.MoodSaveForm;
import com.hx.blog_v2.domain.validator.BeanIdValidator;
import com.hx.blog_v2.domain.validator.MoodSaveValidator;
import com.hx.blog_v2.domain.validator.PageValidator;
import com.hx.blog_v2.domain.vo.AdminMoodVO;
import com.hx.blog_v2.service.interf.MoodService;
import com.hx.common.interf.common.Result;
import com.hx.common.result.SimplePage;
import com.hx.blog_v2.util.ResultUtils;
import com.hx.log.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * SystemController
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 4:57 PM
 */
@RestController("adminMoodController")
@RequestMapping("/admin/mood")
public class MoodController {

    @Autowired
    private MoodService moodService;
    @Autowired
    private MoodSaveValidator moodSaveValidator;
    @Autowired
    private BeanIdValidator beanIdValidator;
    @Autowired
    private PageValidator pageValidator;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result add(MoodSaveForm params) {
        Result errResult = moodSaveValidator.validate(params, null);
        if (!errResult.isSuccess()) {
            return errResult;
        }
        if (!Tools.isEmpty(params.getId())) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " id 不为空 ! ");
        }

        return moodService.add(params);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result list(SimplePage<AdminMoodVO> page) {
        Result errResult = pageValidator.validate(page, null);
        if (!errResult.isSuccess()) {
            return errResult;
        }

        return moodService.adminList(page);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result update(MoodSaveForm params) {
        Result errResult = moodSaveValidator.validate(params, null);
        if (!errResult.isSuccess()) {
            return errResult;
        }
        if (Tools.isEmpty(params.getId())) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " id 为空 ! ");
        }

        return moodService.update(params);
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public Result remove(BeanIdForm params) {
        Result errResult = beanIdValidator.validate(params, null);
        if (!errResult.isSuccess()) {
            return errResult;
        }

        return moodService.remove(params);
    }

}
