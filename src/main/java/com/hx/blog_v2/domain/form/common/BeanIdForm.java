package com.hx.blog_v2.domain.form.common;

import com.hx.blog_v2.domain.BaseForm;
import com.hx.blog_v2.util.CacheConstants;
import com.hx.log.str.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * 通用的移除一条记录的参数
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 9:42 AM
 */
public class BeanIdForm extends BaseForm {

    private String id;
    /**
     * 是否需要校验给定的数据是当前用户的
     */
    private boolean checkSelf;


    public BeanIdForm(String id) {
        this.id = id;
    }

    public BeanIdForm() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isCheckSelf() {
        return checkSelf;
    }

    public void setCheckSelf(boolean checkSelf) {
        this.checkSelf = checkSelf;
    }

    @Override
    public String generateCacheKey() {
        List<String> list = Arrays.asList(id, String.valueOf(checkSelf));
        return StringUtils.join(list, CacheConstants.CACHE_LOCAL_SEP);
    }

}
