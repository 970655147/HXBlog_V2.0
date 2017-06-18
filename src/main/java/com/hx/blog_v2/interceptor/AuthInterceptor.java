package com.hx.blog_v2.interceptor;

import com.hx.blog_v2.context.WebContext;
import com.hx.blog_v2.dao.interf.RltResourceInterfDao;
import com.hx.blog_v2.dao.interf.RltRoleResourceDao;
import com.hx.blog_v2.domain.ErrorCode;
import com.hx.blog_v2.domain.dto.SessionUser;
import com.hx.blog_v2.domain.form.BeanIdsForm;
import com.hx.blog_v2.service.interf.ExceptionLogService;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.common.interf.common.Result;
import com.hx.common.str.AntPathMatcher;
import com.hx.common.str.interf.PathMatcher;
import com.hx.blog_v2.util.ResultUtils;
import com.hx.json.JSONArray;
import com.hx.log.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 权限认证的 interceptor
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/12/2017 7:25 PM
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private RltRoleResourceDao rltRoleResourceDao;
    @Autowired
    private RltResourceInterfDao rltResourceInterfDao;
    @Autowired
    private ExceptionLogService exceptionLogService;

    /**
     * pathMatcher
     */
    private PathMatcher antPathMatcher = new AntPathMatcher();
    /**
     * 多个 pattern 之间的分隔符
     */
    public static String MULTI_PATTERN_SEP = "|";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        SessionUser user = (SessionUser) WebContext.getAttributeFromSession(BlogConstants.SESSION_USER);
        if ((user == null) || (!user.isSystemUser())) {
            Result result = ResultUtils.failed(ErrorCode.NOT_LOGIN, " 您还没有登录, 或者登录过期, 请先登录 ! ");
            WebContext.responseJson(result);
            exceptionLogService.saveExceptionLog(null, result, null);
            return false;
        }
        Result preCheckResult = checkResourceAndInterfs(user);
        if (!preCheckResult.isSuccess()) {
            WebContext.responseJson(preCheckResult);
            exceptionLogService.saveExceptionLog(null, preCheckResult, null);
            return false;
        }

        List<String> interfs = (List<String>) preCheckResult.getData();
        boolean allowVisit = false;
        String requestUri = request.getRequestURI();
        for (String interf : interfs) {
            if (isMatch(interf, requestUri)) {
                allowVisit = true;
                break;
            }
        }

        if (!allowVisit) {
            Result result = ResultUtils.failed(ErrorCode.NOT_AUTHORIZED, "您没有权限, 请联系管理员 !");
            WebContext.responseJson(result);
            exceptionLogService.saveExceptionLog(null, preCheckResult, null);
            return false;
        }
        return true;
    }

    /**
     * 预校验当前用户的权限, 检查是否有资源, 是否存在接口 等等
     * 如果 校验通过 返回所有的可以访问的接口信息
     *
     * @param user user
     * @return com.hx.common.interf.common.Result
     * @author Jerry.X.He
     * @date 6/12/2017 7:56 PM
     * @since 1.0
     */
    private Result checkResourceAndInterfs(SessionUser user) {
        String roleIds = user.getRoleIds();
        if (Tools.isEmpty(roleIds)) {
            return ResultUtils.failed(ErrorCode.NOT_AUTHORIZED, "您没有权限, 请联系管理员 !");
        }
        Result getResourceIdsResult = rltRoleResourceDao.getResourceIdsByRoleIds(new BeanIdsForm(roleIds));
        if ((!getResourceIdsResult.isSuccess())) {
            return ResultUtils.failed(ErrorCode.NOT_AUTHORIZED, "您没有权限, 请联系管理员 !");
        }
        List<String> resourceIds = (List<String>) getResourceIdsResult.getData();
        if (Tools.isEmpty(resourceIds)) {
            return ResultUtils.failed(ErrorCode.NOT_AUTHORIZED, "您没有权限, 请联系管理员 !");
        }

        String resourceIdsStr = JSONArray.fromObject(resourceIds).toString();
        Result getInterfsResult = rltResourceInterfDao.getInterfsByResourceIds(new BeanIdsForm(resourceIdsStr));
        if ((!getInterfsResult.isSuccess())) {
            return ResultUtils.failed(ErrorCode.NOT_AUTHORIZED, "您没有权限, 请联系管理员 !");
        }
        List<String> interfs = (List<String>) getInterfsResult.getData();
        if (Tools.isEmpty(interfs)) {
            return ResultUtils.failed(ErrorCode.NOT_AUTHORIZED, "您没有权限, 请联系管理员 !");
        }

        return ResultUtils.success(interfs);
    }

    /**
     * 判断给定的 url 是否匹配 给定的 pattern
     * 约定使用 |, 分割 多个 pattern
     *
     * @param pattern pattern
     * @param url     url
     * @return boolean
     * @author Jerry.X.He
     * @date 6/12/2017 8:31 PM
     * @since 1.0
     */
    private boolean isMatch(String pattern, String url) {
        return antPathMatcher.match(pattern, url);
//        boolean containsOr = pattern.contains(MULTI_PATTERN_SEP);
//        if (!containsOr) {
//            return antPathMatcher.match(pattern, url);
//        } else {
//            for (String pat : pattern.split(MULTI_PATTERN_SEP)) {
//                return antPathMatcher.match(pat, url);
//            }
//
//            return false;
//        }
    }

}
