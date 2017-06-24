package com.hx.blog_v2.interceptor;

import com.hx.attr_handler.attr_handler.interf.AttrHandler;
import com.hx.blog_v2.context.ConstantsContext;
import com.hx.blog_v2.context.WebContext;
import com.hx.blog_v2.domain.ErrorCode;
import com.hx.blog_v2.domain.dto.SessionUser;
import com.hx.blog_v2.domain.dto.TokenInfo;
import com.hx.blog_v2.service.interf.ExceptionLogService;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.ResultUtils;
import com.hx.common.interf.common.Result;
import com.hx.log.alogrithm.code.Codec;
import com.hx.log.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * 校验用户 token 的拦截器
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/12/2017 7:25 PM
 */
public class TokenInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private ExceptionLogService exceptionLogService;
    @Autowired
    private ConstantsContext constantsContext;
    /**
     * serverTokenHandler
     */
    private AttrHandler clientTokenHandler = new Md5TokenHandler();
    /**
     * serverTokenHandler
     */
    private AttrHandler serverTokenHandler = new Md5TokenHandler();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        addDummySessionIfNeed();
        String token = request.getHeader(BlogConstants.COOKIE_TOKEN);
        TokenInfo tokenInServer = (TokenInfo) WebContext.getAttributeFromSession(BlogConstants.SESSION_TOKEN);

        boolean valid = false;
        if (tokenInServer == null) {
            valid = true;
        }
        if (!valid) {
            String tokenHandled = serverTokenHandler.handle(token);
            if (Objects.equals(tokenInServer.getTokenNow(), tokenHandled)
                    || (Objects.equals(tokenInServer.getTokenLast(), tokenHandled))) {
                valid = true;
            }
        }

        updateTokenAndAddHeader(token, valid, response);
        if (!valid) {
            Result tokenCheckResult = ResultUtils.failed(ErrorCode.TOKEN_NOT_MATCH, " 系统繁忙, 请刷新后重试 ! ");
            WebContext.responseJson(tokenCheckResult);
            exceptionLogService.saveExceptionLog(null, tokenCheckResult, null);
            return false;
        }
        return true;
    }

    /**
     * 如果需要更新 token的话, 更新token
     * 并向 response 写出 token的 header
     *
     * @param oldToken oldToken
     * @param response response
     * @return void
     * @author Jerry.X.He
     * @date 6/18/2017 11:32 AM
     * @since 1.0
     */
    private void updateTokenAndAddHeader(String oldToken, boolean valid, HttpServletResponse response) {
        TokenInfo tokenInServer = (TokenInfo) WebContext.getAttributeFromSession(BlogConstants.SESSION_TOKEN);
        long nowTs = System.currentTimeMillis();
        if ((!valid) || (tokenInServer == null) ||
                ((nowTs - tokenInServer.getLastUpdated()) > constantsContext.tokenRefreshInterval)) {
            if (tokenInServer == null) {
                tokenInServer = new TokenInfo(Tools.NULL, Tools.NULL);
            }

            String newToken = Codec.byte2Hex(Codec.md5(String.valueOf(nowTs).getBytes()));
            tokenInServer.setTokenLast(tokenInServer.getTokenNow());
            tokenInServer.setOriginalToken(newToken);
            tokenInServer.setTokenNow(serverTokenHandler.handle(clientTokenHandler.handle(newToken)));
            tokenInServer.setLastUpdated(nowTs);
            WebContext.setAttributeForSession(BlogConstants.SESSION_TOKEN, tokenInServer);
            response.addHeader(BlogConstants.COOKIE_TOKEN, tokenInServer.getOriginalToken());
        }
    }

    /**
     * 如果 当前用户的 session:user 为空, 创建一个 空的
     *
     * @return void
     * @author Jerry.X.He
     * @date 6/24/2017 7:00 PM
     * @since 1.0
     */
    private void addDummySessionIfNeed() {
        SessionUser user = (SessionUser) WebContext.getAttributeFromSession(BlogConstants.SESSION_USER);
        if (user == null) {
            user = new SessionUser();
            user.setTitle(constantsContext.guestTitle);
            user.setRoleIds(constantsContext.guestRoles);
            user.setSystemUser(false);
            WebContext.setAttributeForSession(BlogConstants.SESSION_USER, user);
        }
    }

    /**
     * 标准化大小写, 随客户端, 统一使用小写
     *
     * @param str str
     * @return java.lang.String
     * @author Jerry.X.He
     * @date 6/18/2017 3:31 PM
     * @since 1.0
     */
    private static String stdCase(String str) {
        return str.toLowerCase();
    }

    /**
     * 基于 md5 算法的 TokenHandler
     *
     * @author Jerry.X.He <970655147@qq.com>
     * @version 1.0
     * @date 6/18/2017 10:53 AM
     */
    private static class Md5TokenHandler extends AttrHandler {
        @Override
        public String handle(String result) {
            if (result == null) {
                return Tools.NULL;
            }
            return stdCase(Codec.byte2Hex(Codec.md5(result.getBytes())));
        }

        @Override
        protected String handle0(String result) {
            return null;
        }

        @Override
        public String name() {
            return "md5TokenHandler";
        }
    }

}
