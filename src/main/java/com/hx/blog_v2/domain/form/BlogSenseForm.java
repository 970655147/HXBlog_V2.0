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
    private String id;
    private String name;
    private String headImgUrl;
    private String email;
    /**
     * good or not good
     */
    private String sense;

    public BlogSenseForm() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
