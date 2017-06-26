package com.hx.blog_v2.service;

import com.hx.blog_v2.context.CacheContext;
import com.hx.blog_v2.context.ConstantsContext;
import com.hx.blog_v2.dao.interf.EmailDao;
import com.hx.blog_v2.domain.dto.EmailAuthInfo;
import com.hx.blog_v2.domain.po.EmailPO;
import com.hx.blog_v2.service.interf.BaseServiceImpl;
import com.hx.blog_v2.service.interf.EmailService;
import com.hx.blog_v2.util.ResultUtils;
import com.hx.common.interf.common.Result;
import com.hx.log.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

/**
 * EmailServiceImpl
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/26/2017 9:10 PM
 */
@Service
public class EmailServiceImpl extends BaseServiceImpl<Object> implements EmailService {

    @Autowired
    private EmailDao emailDao;
    @Autowired
    private CacheContext cacheContext;
    @Autowired
    private ConstantsContext constantsContext;

    /**
     * 系统邮件配置
     */
    private EmailAuthInfo systemEmailAuthInfo;
    /**
     * 系统配置上一次刷新的时间
     */
    private long lastRefreshTs;

    @Override
    public Result sendEmail(EmailAuthInfo authInfo, EmailPO email) {
        // 配置发送邮件的 Context
        final Properties props = new Properties();
        props.put("mail.smtp.auth", authInfo.isAuth());
        props.put("mail.smtp.host", authInfo.getSmtp());
        props.put("mail.user", authInfo.getUserName());
        props.put("mail.password", authInfo.getPassword());
        Session mailSession = Session.getInstance(props, new SimpleMailAuthenticator(authInfo.getUserName(), authInfo.getPassword()));
        MimeMessage message = new MimeMessage(mailSession);

        try {
            message.setFrom(new InternetAddress(email.getFrom()));
            // to, cc
            List<String> toAddrUsers = email.getTo();
            InternetAddress[] toAddrs = new InternetAddress[toAddrUsers.size()];
            for (int i = 0, len = toAddrUsers.size(); i < len; i++) {
                toAddrs[i] = new InternetAddress(toAddrUsers.get(i));
            }
            message.setRecipients(Message.RecipientType.TO, toAddrs);
            List<String> ccAddrUsers = email.getCc();
            if (!Tools.isEmpty(ccAddrUsers)) {
                toAddrs = new InternetAddress[ccAddrUsers.size()];
                for (int i = 0, len = ccAddrUsers.size(); i < len; i++) {
                    toAddrs[i] = new InternetAddress(ccAddrUsers.get(i));
                }
                message.setRecipients(Message.RecipientType.CC, toAddrs);
            }

            message.setSubject(email.getSubject());
            message.setContent(email.getContent(), email.getContentType());

            Transport.send(message);
            Result saveEmailResult = emailDao.add(email);
            if (saveEmailResult.isSuccess()) {
                return saveEmailResult;
            }

            return ResultUtils.success();
        } catch (Exception e) {
            return ResultUtils.failed(Tools.errorMsg(e));
        }
    }

    @Override
    public Result sendEmail(EmailPO mailInfo) {
        if ((systemEmailAuthInfo == null) || (lastRefreshTs < constantsContext.systemConfigLastRefreshTs()) ) {
            lastRefreshTs = constantsContext.systemConfigLastRefreshTs();
            systemEmailAuthInfo = new EmailAuthInfo(true, constantsContext.emailAuthSmtp,
                    constantsContext.emailAuthUserName, constantsContext.emailAuthPassword);
        }
        return sendEmail(systemEmailAuthInfo, mailInfo);
    }

    /**
     * 一个简易的 MailAuthenticator
     *
     * @author Jerry.X.He
     * @date 2017/6/8 16:51
     */
    public static class SimpleMailAuthenticator extends Authenticator {
        private String userName;
        private String password;

        public SimpleMailAuthenticator(String userName, String password) {
            this.userName = userName;
            this.password = password;
        }

        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(userName, password);
        }
    }

}
