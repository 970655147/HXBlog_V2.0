package com.hx.blog_v2.domain.form;

import com.hx.blog_v2.domain.form.interf.UserInfoExtractor;

/**
 * BlogSenseForm
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/2/2017 11:43 PM
 */
public class BlogSenseForm implements UserInfoExtractor {

    /**
     * blogId
     */
    private String blogId;
    private String name;
    private String headImgUrl;
    private String email;
    /**
     * good or not good
     */
    private String sense;
    private Integer clicked;

    public BlogSenseForm() {
    }

    public String getBlogId() {
        return blogId;
    }

    public void setBlogId(String blogId) {
        this.blogId = blogId;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getSense() {
        return sense;
    }

    public void setSense(String sense) {
        this.sense = sense;
    }

    public Integer isClicked() {
        return clicked;
    }

    public void setClicked(Integer clicked) {
        this.clicked = clicked;
    }
}
