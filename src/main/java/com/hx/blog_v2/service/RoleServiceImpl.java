package com.hx.blog_v2.service;

import com.hx.blog_v2.dao.interf.RltUserRoleDao;
import com.hx.blog_v2.dao.interf.RoleDao;
import com.hx.blog_v2.domain.POVOTransferUtils;
import com.hx.blog_v2.domain.form.BeanIdForm;
import com.hx.blog_v2.domain.form.RoleSaveForm;
import com.hx.blog_v2.domain.form.UserRoleUpdateForm;
import com.hx.blog_v2.domain.mapper.RltUserRolePOMapper;
import com.hx.blog_v2.domain.mapper.UserRoleVOMapper;
import com.hx.blog_v2.domain.po.RltUserRoleRolePO;
import com.hx.blog_v2.domain.po.RolePO;
import com.hx.blog_v2.domain.vo.AdminRoleVO;
import com.hx.blog_v2.domain.vo.UserRoleVO;
import com.hx.blog_v2.service.interf.BaseServiceImpl;
import com.hx.blog_v2.service.interf.RoleService;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.CacheContext;
import com.hx.blog_v2.util.DateUtils;
import com.hx.common.interf.common.Page;
import com.hx.common.interf.common.Result;
import com.hx.common.util.ResultUtils;
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

    @Override
    public Result add(RoleSaveForm params) {
        Map<String, RolePO> roles = cacheContext.allRoles();
        if (contains(roles, params.getName())) {
            return ResultUtils.failed("该角色已经存在 !");
        }

        RolePO po = new RolePO(params.getName(), params.getDesc(), params.getEnable());
        try {
            roleDao.save(po, BlogConstants.ADD_BEAN_CONFIG);
            roles.put(po.getId(), po);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.failed(Tools.errorMsg(e));
        }
        return ResultUtils.success(po.getId());
    }

    @Override
    public Result adminList() {
        Map<String, RolePO> rolesById = cacheContext.allRoles();

        List<AdminRoleVO> list = new ArrayList<>(rolesById.size());
        for (Map.Entry<String, RolePO> entry : rolesById.entrySet()) {
            RolePO role = entry.getValue();
            if (role.getEnable() != 0) {
                list.add(POVOTransferUtils.rolePO2AdminRoleVO(role));
            }
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
            if(idx >= 0) {
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

        po.setName(params.getName());
        po.setDesc(params.getDesc());
        po.setEnable(params.getEnable());
        po.setUpdatedAt(DateUtils.formate(new Date(), BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS));
        try {
            long modified = roleDao.updateById(po, BlogConstants.UPDATE_BEAN_CONFIG)
                    .getModifiedCount();
            if (modified == 0) {
                return ResultUtils.failed("没有找到对应的角色 !");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.failed(Tools.errorMsg(e));
        }
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

        try {
            rltUserRoleDao.deleteMany(Criteria.eq("user_id", params.getUserId()));
            if(! CollectionUtils.isEmpty(userRoles)) {
                rltUserRoleDao.insertMany(userRoles, BlogConstants.ADD_BEAN_CONFIG);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.failed(Tools.errorMsg(e));
        }
        return ResultUtils.success(params.getUserId());
    }

    @Override
    public Result remove(BeanIdForm params) {
        RolePO po = cacheContext.allRoles().remove(params.getId());
        if (po == null) {
            return ResultUtils.failed("该角色不存在 !");
        }

        String updatedAt = DateUtils.formate(new Date(), BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS);
        try {
            long deleted = roleDao.updateOne(Criteria.eq("id", params.getId()),
                    Criteria.set("deleted", "1").add("updated_at", updatedAt)
            ).getModifiedCount();
            if (deleted == 0) {
                return ResultUtils.failed("角色[" + params.getId() + "]不存在 !");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.failed(Tools.errorMsg(e));
        }
        return ResultUtils.success(params.getId());
    }

    // -------------------- 辅助方法 --------------------------

    /**
     * 判断当前所有的 BlogType 中 是否有名字为 name的 BlogType
     *
     * @param blogTypes blogTypes
     * @param name      name
     * @return boolean
     * @author Jerry.X.He
     * @date 5/21/2017 6:20 PM
     * @since 1.0
     */
    private boolean contains(Map<String, RolePO> blogTypes, String name) {
        for (Map.Entry<String, RolePO> entry : blogTypes.entrySet()) {
            if (Tools.equalsIgnoreCase(entry.getValue().getName(), name)) {
                return true;
            }
        }
        return false;
    }

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
