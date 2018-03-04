package com.hx.blog_v2.domain.common.resources;

import java.awt.*;

/**
 * CheckCode
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/3/2017 4:57 PM
 */
public class CheckCode {

    private String checkCode;
    private Image checkCodeImg;

    public CheckCode(String checkCode, Image checkCodeImg) {
        this.checkCode = checkCode;
        this.checkCodeImg = checkCodeImg;
    }

    public String getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }

    public Image getCheckCodeImg() {
        return checkCodeImg;
    }

    public void setCheckCodeImg(Image checkCodeImg) {
        this.checkCodeImg = checkCodeImg;
    }
}
