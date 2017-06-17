package com.hx.blog_v2.domain.validator;

import com.hx.blog_v2.domain.form.BeanIdsForm;
import com.hx.common.interf.common.Result;
import com.hx.common.interf.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * AdminCommentSearchValidator
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/15/2017 7:18 PM
 */
@Component
public class BeanIdsValidator implements Validator<BeanIdsForm> {

    @Autowired
    private BeanIdsStrValidator beanIdsStrValidator;

    @Override
    public Result validate(BeanIdsForm form, Object extra) {
        return beanIdsStrValidator.validate(form.getIds(), extra);
    }

}
