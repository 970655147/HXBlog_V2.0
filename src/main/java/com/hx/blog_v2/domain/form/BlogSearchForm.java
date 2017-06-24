package com.hx.blog_v2.domain.form;

/**
 * BlogSearchForm
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/28/2017 11:09 AM
 */
public class BlogSearchForm {

    private String id;
    private String typeId;
    private String tagId;
    private String keywords;
    private String createdAtMonth;

    public BlogSearchForm() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getCreatedAtMonth() {
        return createdAtMonth;
    }

    public void setCreatedAtMonth(String createdAtMonth) {
        this.createdAtMonth = createdAtMonth;
    }
}
