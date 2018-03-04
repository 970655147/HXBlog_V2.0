package com.hx.blog_v2.domain.validator.interf;

import com.hx.blog_v2.context.ConstantsContext;
import com.hx.common.interf.common.Result;
import com.hx.common.interf.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 需要从 config 读取数据的 Validator
 * 这里 提供一个统一的模板
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/17/2017 9:49 AM
 */
public abstract class ConfigRefreshableValidator<T> implements Validator<T> {

    @Autowired
    protected ConstantsContext constantsContext;
    /**
     * 上一次刷新的时间戳
     */
    private long lastRefreshTs;

    @Override
    public Result validate(T obj, Object extra) {
        if (needRefresh() || (lastRefreshTs < constantsContext.ruleConfigLastRefreshTs())) {
            lastRefreshTs = constantsContext.ruleConfigLastRefreshTs();
            refreshConfig();
        }

        return doValidate(obj, extra);
    }

    /**
     * 处理实际需要校验的业务
     *
     * @return
     * @author Jerry.X.He
     * @date 6/17/2017 9:54 AM
     * @since 1.0
     */
    public abstract Result doValidate(T obj, Object extra);

    /**
     * 判断当前 Validator 是否需要 refreshConfig
     *
     * @return
     * @author Jerry.X.He
     * @date 6/17/2017 9:53 AM
     * @since 1.0
     */
    public boolean needRefresh() {
        return false;
    }

    /**
     * 刷新当前 Validator 的配置
     *
     * @return
     * @author Jerry.X.He
     * @date 6/17/2017 9:53 AM
     * @since 1.0
     */
    public abstract void refreshConfig();


}
