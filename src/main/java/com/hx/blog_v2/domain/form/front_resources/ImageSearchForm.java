package com.hx.blog_v2.domain.form.front_resources;

import com.hx.blog_v2.domain.BaseForm;
import com.hx.blog_v2.util.CacheConstants;
import com.hx.log.str.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * ImageSearchForm
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/31/2017 8:36 PM
 */
public class ImageSearchForm extends BaseForm {

    private String type;

    public ImageSearchForm(String type) {
        this.type = type;
    }

    public ImageSearchForm() {
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
