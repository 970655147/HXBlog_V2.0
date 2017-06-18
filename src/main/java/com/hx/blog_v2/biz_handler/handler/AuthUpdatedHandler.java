package com.hx.blog_v2.biz_handler.handler;

import com.hx.blog_v2.biz_handler.handler.common.BizHandlerAdapter;
import com.hx.blog_v2.biz_handler.interf.BizContext;
import com.hx.blog_v2.context.CacheContext;
import com.hx.blog_v2.domain.form.ResourceInterfUpdateForm;
import com.hx.blog_v2.domain.form.RoleResourceUpdateForm;
import com.hx.blog_v2.domain.form.UserRoleUpdateForm;
import com.hx.blog_v2.domain.mapper.OneStringMapper;
import com.hx.common.interf.common.Result;
import com.hx.log.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * BlogSaveHandler
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

    @Override
    public void afterHandle(BizContext context) {
        Result result = (Result) context.result();
        if (result.isSuccess()) {
            String type = context.bizHandle().others()[0];
            List<String> userIdsToOffline = Collections.emptyList();
            if (USER_ROLE.equals(type)) {
                String userId = ((UserRoleUpdateForm) context.args()[0]).getUserId();
                userIdsToOffline = Collections.singletonList(userId);
            } else if (ROLE_RESOURCE.equals(type)) {
                String roleId = ((RoleResourceUpdateForm) context.args()[0]).getRoleId();
                String userByRole = " select user_id from rlt_user_role where role_id = ? ";
                userIdsToOffline = jdbcTemplate.query(userByRole, new Object[]{roleId}, new OneStringMapper("user_id"));
            } else if (RESOURCE_INTERF.equals(type)) {
                String resId = ((ResourceInterfUpdateForm) context.args()[0]).getResourceId();
                String userByRole = " select user_id from rlt_user_role where " +
                        " role_id in ( select role_id from rlt_role_resource where resource_id = ? ) ";
                userIdsToOffline = jdbcTemplate.query(userByRole, new Object[]{resId}, new OneStringMapper("user_id"));
            }

            if(!Tools.isEmpty(userIdsToOffline) && (! USER_ROLE.equals(type))) {
                cacheContext.clearAuthorityCached();
            }
            for (String userId : userIdsToOffline) {
                cacheContext.putForceOffLine(userId, " 您的权限发生了改变 ! ");
            }
        }
    }

}
