package com.hx.blog_v2.domain.form.system;

import com.hx.blog_v2.domain.BaseForm;
import com.hx.blog_v2.domain.BasePageForm;
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
public class LogSearchForm extends BasePageForm {

    public String url;
    private String handler;
    private String params;
    private String name;
    private String email;
    private Integer isSystemUser;
    private String requestIp;
    private String msg;

    public LogSearchForm() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getIsSystemUser() {
        return isSystemUser;
    }

    public void setIsSystemUser(Integer isSystemUser) {
        this.isSystemUser = isSystemUser;
    }

    public String getRequestIp() {
        return requestIp;
    }

    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String generateCacheKey() {
        List<String> list = Arrays.asList(url, handler, params, name, email, String.valueOf(isSystemUser), requestIp, msg,
                super.generateCacheKey());
        return StringUtils.join(list, CacheConstants.CACHE_LOCAL_SEP);
    }
}
