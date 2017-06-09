package com.hx.blog_v2.util;

import com.hx.blog_v2.domain.dto.SessionUser;
import com.hx.blog_v2.domain.form.interf.UserInfoExtractor;
import com.hx.log.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * 处理业务的相关通用的方法
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/5/2017 8:44 PM
 */
public final class BizUtils {

    // disable constructor
    private BizUtils() {
        Tools.assert0("can't instantiate !");
    }

    /**
     * 如果可以更新用户信息的话, 更新用户信息
     *
     * @param user   user
     * @param params params
     * @return com.hx.blog_v2.domain.dto.SessionUser
     * @author Jerry.X.He
     * @date 6/5/2017 8:50 PM
     * @since 1.0
     */
    public static SessionUser updateUserIfBe(SessionUser user, UserInfoExtractor params) {
        if (user == null) {
            user = new SessionUser(params.getName(), params.getEmail(), params.getHeadImgUrl(), "guest", "guest", false);
        } else {
            if (!user.isSystemUser()) {
                user.setName(params.getName());
                user.setEmail(params.getEmail());
            }
            user.setHeadImgUrl(params.getHeadImgUrl());
        }

        return user;
    }

    /**
     * 获取当前请求的ip
     *
     * @return java.lang.String
     * @author Jerry.X.He
     * @date 6/9/2017 9:24 PM
     * @since 1.0
     */
    public static String getIp() {
        HttpServletRequest req = WebContext.getRequest();

        String ip = req.getHeader("X-Forwarded-For");
        if (Tools.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = req.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = req.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = req.getHeader("HTTP_CLIENT_IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = req.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = req.getRemoteAddr();
            }
        } else if (ip.length() > 15) {
            String[] ips = ip.split(",");
            for (int index = 0; index < ips.length; index++) {
                String strIp = ips[index];
                if (!("unknown".equalsIgnoreCase(strIp))) {
                    ip = strIp;
                    break;
                }
            }
        }

        return ip;
    }


}
