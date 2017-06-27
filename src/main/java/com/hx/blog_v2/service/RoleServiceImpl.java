package com.hx.blog_v2.service;

import com.hx.blog_v2.context.CacheContext;
import com.hx.blog_v2.context.ConstantsContext;
import com.hx.blog_v2.dao.interf.RltUserRoleDao;
import com.hx.blog_v2.dao.interf.RoleDao;
import com.hx.blog_v2.domain.POVOTransferUtils;
import com.hx.blog_v2.domain.form.BeanIdForm;
import com.hx.blog_v2.domain.form.RoleSaveForm;
import com.hx.blog_v2.domain.form.UserRoleUpdateForm;
import com.hx.blog_v2.domain.mapper.OneIntMapper;
import com.hx.blog_v2.domain.mapper.RltUserRolePOMapper;
import com.hx.blog_v2.domain.mapper.UserRoleVOMapper;
import com.hx.blog_v2.domain.po.RltUserRoleRolePO;
import com.hx.blog_v2.domain.po.RolePO;
import com.hx.blog_v2.domain.vo.AdminRoleVO;
import com.hx.blog_v2.domain.vo.UserRoleVO;
import com.hx.blog_v2.service.interf.BaseServiceImpl;
import com.hx.blog_v2.service.interf.RoleService;
import com.hx.blog_v2.util.BizUtils;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.DateUtils;
import com.hx.blog_v2.util.ResultUtils;
import com.hx.common.interf.common.Page;
import com.hx.common.interf.common.Result;
import com.hx.json.JSONArray;
import com.hx.log.collection.CollectionUtils;
import com.hx.log.util.Log;
import com.hx.log.util.Tools;
import com.hx.mongo.criteria.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * BlogServiceImpl
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 11:47 AM
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<RolePO> implements RoleService {

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private RltUserRoleDao rltUserRoleDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private CacheContext cacheContext;
    @Autowired
    private ConstantsContext constantsContext;

    @Override
    public Result add(RoleSaveForm params) {
        RolePO poByName = BizUtils.findByLogisticId(cacheContext.allRoles(), params.getName());
        if (poByName != null) {
            return ResultUtils.failed("角色[" + params.getName() + "]已经存在 !");
        }

        RolePO po = new RolePO(params.getName(), params.getDesc(), params.getSort(), params.getEnable());
        Result result = roleDao.add(po);
        if (!result.isSuccess()) {
            return result;
        }

        cacheContext.putRole(po);
        return ResultUtils.success(po.getId());
    }

    @Override
    public Result adminList() {
        Map<String, RolePO> rolesById = cacheContext.allRoles();

        List<AdminRoleVO> list = new ArrayList<>(rolesById.size());
        for (Map.Entry<String, RolePO> entry : rolesById.entrySet()) {
            RolePO role = entry.getValue();
            list.add(POVOTransferUtils.rolePO2AdminRoleVO(role));
        }
        return ResultUtils.success(list);
    }

    @Override
    public Result userRoleList(Page<UserRoleVO> page) {
        String userSql = " select * from user where deleted = 0 order by created_at desc limit ?, ? ";
        Object[] params = new Object[]{page.recordOffset(), page.getPageSize()};
        String userRoleSql = " select * from rlt_user_role where user_id in ( %s ) ";

        List<UserRoleVO> userRoles = jdbcTemplate.query(userSql, params, new UserRoleVOMapper());
        String userIds = collectUserIds(userRoles);
        List<RltUserRoleRolePO> rltUserRoles = jdbcTemplate.query(String.format(userRoleSql, userIds), new RltUserRolePOMapper());
        Map<String, RolePO> roleById = cacheContext.allRoles();
        for (RltUserRoleRolePO userRole : rltUserRoles) {
            int idx = idxOfUser(userRoles, userRole.getUserId());
            if (idx >= 0) {
                UserRoleVO userRoleVO = userRoles.get(idx);
                userRoleVO.getRoleIds().add(userRole.getRoleId());
                userRoleVO.getRoleNames().add(roleById.get(userRole.getRoleId()).getName());
            } else {
                Log.err(" there is an db query error ! ");
            }
        }

        page.setList(userRoles);
        return ResultUtils.success(page);
    }

    @Override
    public Result update(RoleSaveForm params) {
        RolePO po = cacheContext.allRoles().get(params.getId());
        if (po == null) {
            return ResultUtils.failed("该角色不存在 !");
        }
        RolePO poByName = BizUtils.findByLogisticId(cacheContext.allRoles(), params.getName());
        if ((poByName != null) && (!po.getId().equals(poByName.getId()))) {
            return ResultUtils.failed("该角色已经存在 !");
        }

        po.setName(params.getName());
        po.setDesc(params.getDesc());
        po.setSort(params.getSort());
        po.setEnable(params.getEnable());
        po.setUpdatedAt(DateUtils.formate(new Date(), BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS));

        Result result = roleDao.update(po);
        if (!result.isSuccess()) {
            return result;
        }
        cacheContext.putRole(po);
        return ResultUtils.success(po.getId());
    }

    @Override
    public Result userRoleUpdate(UserRoleUpdateForm params) {
        String[] roleIds = params.getRoleIds().split(",");
        List<RltUserRoleRolePO> userRoles = null;
        if (!Tools.isEmpty(roleIds)) {
            Tools.trimAllSpaces(roleIds);
            userRoles = new ArrayList<>(roleIds.length);
            for (String roleId : roleIds) {
                userRoles.add(new RltUserRoleRolePO(params.getUserId(), roleId));
            }
        }

        Result removeOldRltRresult = rltUserRoleDao.remove(Criteria.eq("user_id", params.getUserId()), true);
        if (!removeOldRltRresult.isSuccess()) {
            Log.info(" init roleInfo for user [" + params.getUserId() + "]");
        }
        if (!CollectionUtils.isEmpty(userRoles)) {
            Result addNewRoleResult = rltUserRoleDao.add(userRoles);
            if (!addNewRoleResult.isSuccess()) {
                return addNewRoleResult;
            }
        }
        return ResultUtils.success(params.getUserId());
    }

    @Override
    public Result remove(BeanIdForm params) {
        RolePO po = cacheContext.allRoles().get(params.getId());
        if (po == null) {
            return ResultUtils.failed("该角色不存在 !");
        }
        String countSql = " select count(*) as totalRecord from `user` where deleted = 0 and " +
                " id in ( select user_id from rlt_user_role where role_id = ? ) ";
        Integer totalRecord = jdbcTemplate.queryForObject(countSql, new Object[]{params.getId()},
                new OneIntMapper("totalRecord"));
        if (totalRecord > 0) {
            return ResultUtils.failed("该角色下面还有 " + totalRecord + "个用户, 请先迁移这部分用户 !");
        }

        po.setUpdatedAt(DateUtils.formate(new Date(), BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS));
        po.setDeleted(1);
        Result result = roleDao.update(po);
        if (!result.isSuccess()) {
            return result;
        }

        cacheContext.removeRole(params.getId());
        return ResultUtils.success(params.getId());
    }

    @Override
    public Result reSort() {
        Map<String, RolePO> roles = cacheContext.allRoles();
        List<RolePO> sortedRoles = BizUtils.resort(roles);
        int sort = constantsContext.reSortStart;
        for (RolePO role : sortedRoles) {
            boolean isSortChanged = sort != role.getSort();
            if (isSortChanged) {
                role.setSort(sort);
                roleDao.update(role);
            }
            sort += constantsContext.reSortOffset;
        }

        return ResultUtils.success("success");
    }

    // -------------------- 辅助方法 --------------------------

    /**
     * 收集给定的用户列表的所有用户的id
     *
     * @param allUser allUser
     * @return java.util.List<java.lang.String>
     * @author Jerry.X.He
     * @date 5/30/2017 5:35 PM
     * @since 1.0
     */
    private String collectUserIds(List<UserRoleVO> allUser) {
        JSONArray arr = new JSONArray();
        for (UserRoleVO user : allUser) {
            arr.add(user.getId());
        }
        String idsWithArr = arr.toString();
        return idsWithArr.substring(1, idsWithArr.length() - 1);
    }

    /**
     * 获取 用户角色 列表中给定的用户的索引
     *
     * @param userRoles userRoles
     * @param id        id
     * @return int
     * @author Jerry.X.He
     * @date 5/30/2017 5:43 PM
     * @since 1.0
     */
    private int idxOfUser(List<UserRoleVO> userRoles, String id) {
        int idx = 0;
        for (UserRoleVO userRole : userRoles) {
            if (userRole.getId().equals(id)) {
                return idx;
            }
            idx++;
        }

        return -1;
    }


}
