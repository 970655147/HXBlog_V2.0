package com.hx.blog_v2.domain.form;

import com.hx.blog_v2.domain.form.interf.UserInfoExtractor;
import com.hx.blog_v2.util.BizUtils;

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
    private String requestIp;
    private String ipFromSohu;
    private String ipAddr;
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

    public String getRequestIp() {
//        if (requestIp.equals(ipFromSohu)) {
//            return requestIp;
//        }
//
//        if(BizUtils.isLocalIp(requestIp)) {
//            return ipFromSohu;
//        }
        return requestIp;
    }

    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }

    public String getIpFromSohu() {
        return ipFromSohu;
    }

    public void setIpFromSohu(String ipFromSohu) {
        this.ipFromSohu = ipFromSohu;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public String getSense() {
        return sense;
    }

    public void setSense(String sense) {
        this.sense = sense;
    }

    public Integer getClicked() {
        return clicked;
    }

    public void setClicked(Integer clicked) {
        this.clicked = clicked;
    }

    public void setUserInfo(UserInfoExtractor extractor) {
        this.name =  extractor.getName();
        this.headImgUrl = extractor.getHeadImgUrl();
        this.email = extractor.getEmail();
        this.requestIp = extractor.getRequestIp();
    }

}
