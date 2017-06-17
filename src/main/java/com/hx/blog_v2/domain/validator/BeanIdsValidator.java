package com.hx.blog_v2.domain.validator;

import com.hx.blog_v2.domain.ErrorCode;
import com.hx.blog_v2.domain.form.BeanIdForm;
import com.hx.blog_v2.domain.form.BeanIdsForm;
import com.hx.common.interf.common.Result;
import com.hx.common.interf.validator.Validator;
import com.hx.common.util.ResultUtils;
import com.hx.log.util.Tools;
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
public class BeanIdsValidator extends ConfigRefreshableValidator<BeanIdsForm> implements Validator<BeanIdsForm> {

    @Autowired
    private BeanIdValidator beanIdValidator;
    /**
     * 分割 id 列表的字符串
     */
    private String idsSep = null;

    @Override
    public Result doValidate(BeanIdsForm form, Object extra) {
        String ids = form.getIds();
        if (Tools.isEmpty(ids)) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " ids 为空 ! ");
        }

        String[] idSplited = ids.split(idsSep);
        for (String id : idSplited) {
            Result errResult = beanIdValidator.validate(new BeanIdForm(id), extra);
            if (!errResult.isSuccess()) {
                return errResult;
            }
        }

        return ResultUtils.success();
    }


    @Override
    public boolean needRefresh() {
        return idsSep == null;
    }

    @Override
    public void refreshConfig() {
        idsSep = constantsContext.ruleConfig("ids.sep", ",");
    }
}
