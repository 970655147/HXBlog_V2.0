package com.hx.blog_v2.domain.form;

/**
 * 增加 mood 的时候的表单
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/22/2017 8:12 PM
 */
public class ImageSaveForm {

    private String id;
    private String title;
    private String url;
    private String type;
    private int enable;

    public ImageSaveForm() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }
}
