package com.hx.blog_v2.service.interf;

import com.hx.blog_v2.domain.dto.EmailAuthInfo;
import com.hx.blog_v2.domain.po.EmailPO;
import com.hx.common.interf.common.Result;

/**
 * EmailService
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/26/2017 9:10 PM
 */
public interface EmailService extends BaseService<Object> {

    /**
     * 使用给定的认证信息 发送邮件
     *
     * @param authInfo authInfo
     * @param mailInfo mailInfo
     * @return
     * @author Jerry.X.He
     * @date 6/26/2017 9:23 PM
     * @since 1.0
     */
    Result sendEmail(EmailAuthInfo authInfo, EmailPO mailInfo);

    /**
     * 使用系统配置发送邮件
     *
     * @param mailInfo mailInfo
     * @return com.hx.common.interf.common.Result
     * @author Jerry.X.He
     * @date 6/26/2017 9:30 PM
     * @since 1.0
     */
    Result sendEmail(EmailPO mailInfo);

}
