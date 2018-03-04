package com.hx.blog_v2.domain.vo.system;

import com.hx.blog_v2.domain.po.interf.LogisticalId;

/**
 * 一条校正信息
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/18/2017 7:17 PM
 */
public class CorrectionVO implements LogisticalId<String> {

    /**
     * 关联的核心id
     */
    private String id;
    private String contextInfo;
    private String expect;
    private String value;
    private String desc;

    public CorrectionVO(String id, String expect, String value) {
        this.id = id;
        this.expect = expect;
        this.value = value;
    }

    public CorrectionVO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContextInfo() {
        return contextInfo;
    }

    public void setContextInfo(String contextInfo) {
        this.contextInfo = contextInfo;
    }

    public String getExpect() {
        return expect;
    }

    public void setExpect(String expect) {
        this.expect = expect;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String logisticalId() {
        return id;
    }
}
