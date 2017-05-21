package com.hx.blog_v2.domain.form;

import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.DateUtils;

import java.util.Date;

/**
 * 博客管理的搜索
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 9:42 AM
 */
public class AdminBlogSearchForm {

    private String keywords;
    private String blogTypeId;
    private String blogTagId;

    public AdminBlogSearchForm() {
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getBlogTypeId() {
        return blogTypeId;
    }

    public void setBlogTypeId(String blogTypeId) {
        this.blogTypeId = blogTypeId;
    }

    public String getBlogTagId() {
        return blogTagId;
    }

    public void setBlogTagId(String blogTagId) {
        this.blogTagId = blogTagId;
    }
}
