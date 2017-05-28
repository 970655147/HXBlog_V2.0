package com.hx.blog_v2.domain.vo;

import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.DateUtils;
import com.hx.json.JSONObject;
import com.hx.log.json.interf.JSONTransferable;
import com.hx.log.util.Constants;
import com.hx.log.util.Tools;

import java.util.*;

/**
 * AdminImageVO
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/22/2017 8:03 PM
 */
public class AdminImageVO {

    private String id;
    private String title;
    private String url;
    private String createdAt;
    private String updatedAt;
    /**
     * 是否可用
     */
    private int enable;

    public AdminImageVO(String title, String url, int enable) {
        this();
        this.title = title;
        this.url = url;
        this.enable = enable;
    }

    public AdminImageVO() {
        Date now = new Date();
        createdAt = DateUtils.formate(now, BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS);
        updatedAt = createdAt;
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }

}
