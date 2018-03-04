package com.hx.blog_v2.biz_handler.handler;

import com.hx.blog_v2.biz_handler.handler.common.BizHandlerAdapter;
import com.hx.blog_v2.biz_handler.interf.BizContext;
import com.hx.blog_v2.context.CacheContext;
import com.hx.blog_v2.domain.form.rlt.UserRoleUpdateForm;
import com.hx.common.interf.common.Result;
import com.hx.log.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * AuthUpdatedHandler
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/11/2017 12:30 AM
 */
@Component
public class AuthUpdatedHandler extends BizHandlerAdapter {

    /**
     * 三个常量, 标记三种场景
     */
    public static final String USER_ROLE = "userRole";
    public static final String ROLE_RESOURCE = "roleResource";
    public static final String RESOURCE_INTERF = "resourceInter";

    @Autowired
    private CacheContext cacheContext;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 是否需要刷新缓存的权限
     */
    private boolean needClearCache = false;
    /**
     * 需要被强制下线的用户[更新了 role]
     */
    private Set<String> userNeedToForceOffline = new HashSet<>();

    @Override
    public void afterHandle(BizContext context) {
        Result result = (Result) context.result();
        if (result.isSuccess()) {
            String type = context.bizHandle().others()[0];
            if (USER_ROLE.equals(type)) {
                String userId = ((UserRoleUpdateForm) context.args()[0]).getUserId();
                userNeedToForceOffline.add(userId);
            } else if (ROLE_RESOURCE.equals(type)) {
            } else if (RESOURCE_INTERF.equals(type)) {
            }

            needClearCache = true;
        }
    }

    /**
     * 根据需要刷新权限, 如果更新了用户的 role, 强制用户下线
     * 如果更新了 用户相关的 resource, interface, 刷新缓存
     *
     * @return void
     * @author Jerry.X.He
     * @date 6/24/2017 8:38 AM
     * @since 1.0
     */
    public void refreshAuthority() {
        if (needClearCache) {
            cacheContext.clearAuthorityCached();
            needClearCache = false;
        }
        if (!Tools.isEmpty(userNeedToForceOffline)) {
            for (String userId : userNeedToForceOffline) {
                cacheContext.putForceOffLine(userId, " 您的权限发生了改变 ! ");
            }
            userNeedToForceOffline.clear();
        }
    }


}
