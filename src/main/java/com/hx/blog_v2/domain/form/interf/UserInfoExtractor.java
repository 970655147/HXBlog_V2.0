package com.hx.blog_v2.domain.form.interf;

/**
 * 提取用户信息的接口
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/5/2017 8:47 PM
 */
public interface UserInfoExtractor {

    /**
     * 获取用户名
     *
     * @author Jerry.X.He
     * @date 6/5/2017 8:48 PM
     * @since 1.0
     */
    String getName();

    /**
     * 获取邮箱信息
     *
     * @author Jerry.X.He
     * @date 6/5/2017 8:48 PM
     * @since 1.0
     */
    String getEmail();

    /**
     * 获取头像信息
     *
     * @author Jerry.X.He
     * @date 6/5/2017 8:48 PM
     * @since 1.0
     */
    String getHeadImgUrl();

    /**
     * 获取ip信息
     *
     * @author Jerry.X.He
     * @date 6/5/2017 8:48 PM
     * @since 1.0
     */
    String getRequestIp();

}
