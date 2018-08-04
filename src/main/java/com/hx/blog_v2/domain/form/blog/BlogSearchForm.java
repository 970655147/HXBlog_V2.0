package com.hx.blog_v2.domain.form.blog;

import com.hx.blog_v2.domain.BasePageForm;
import com.hx.blog_v2.util.CacheConstants;
import com.hx.log.str.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * BlogSearchForm
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/28/2017 11:09 AM
 */
public class BlogSearchForm extends BasePageForm {

    private String id;
    private String author;
    private String typeId;
    private String tagId;
    private String state;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    @Override
    public String generateCacheKey() {
        List<String> list = Arrays.asList(id, author, typeId, tagId, state, keywords, createdAtMonth,
                super.generateCacheKey());
        return StringUtils.join(list, CacheConstants.CACHE_LOCAL_SEP);
    }

}
