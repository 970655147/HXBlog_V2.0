package com.hx.blog_v2.domain.form.system;

import com.hx.blog_v2.domain.BaseForm;
import com.hx.blog_v2.util.CacheConstants;
import com.hx.log.str.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * CorrectionSearchForm
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/18/2017 7:22 PM
 */
public class CorrectionSearchForm extends BaseForm {

    public String type;

    public CorrectionSearchForm() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String generateCacheKey() {
        List<String> list = Arrays.asList(type);
        return StringUtils.join(list, CacheConstants.CACHE_LOCAL_SEP);
    }
}
