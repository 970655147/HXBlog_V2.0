package com.hx.blog_v2.domain.form.blog;

/**
 * BlogVisitLogForm
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/9/2017 9:00 PM
 */
public class BlogVisitLogForm {

    private String blogId;
    private String requestIp;
    private String createdAtDay;

    public BlogVisitLogForm(String blogId, String requestIp) {
        this.blogId = blogId;
        this.requestIp = requestIp;
    }

    public BlogVisitLogForm() {
    }

    public String getBlogId() {
        return blogId;
    }

    public void setBlogId(String blogId) {
        this.blogId = blogId;
    }

    public String getRequestIp() {
        return requestIp;
    }

    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }

    public String getCreatedAtDay() {
        return createdAtDay;
    }

    public void setCreatedAtDay(String createdAtDay) {
        this.createdAtDay = createdAtDay;
    }
}
