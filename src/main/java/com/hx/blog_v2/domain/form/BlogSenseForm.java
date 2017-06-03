package com.hx.blog_v2.domain.form;

/**
 * BlogSenseForm
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/2/2017 11:43 PM
 */
public class BlogSenseForm {

    /**
     * blogId
     */
    private String id;
    /**
     * good or not good
     */
    private boolean sense;

    public BlogSenseForm() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isSense() {
        return sense;
    }

    public void setSense(boolean sense) {
        this.sense = sense;
    }
}
