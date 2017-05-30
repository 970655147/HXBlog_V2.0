package com.hx.blog_v2.service;

import com.hx.blog_v2.dao.interf.ResourceDao;
import com.hx.blog_v2.dao.interf.RltRoleResourceDao;
import com.hx.blog_v2.domain.POVOTransferUtils;
import com.hx.blog_v2.domain.form.BeanIdForm;
import com.hx.blog_v2.domain.form.ResourceSaveForm;
import com.hx.blog_v2.domain.form.RoleResourceUpdateForm;
import com.hx.blog_v2.domain.mapper.ResourceVOMapper;
import com.hx.blog_v2.domain.mapper.RltRoleResourcePOMapper;
import com.hx.blog_v2.domain.mapper.RltRoleResourceVOMapper;
import com.hx.blog_v2.domain.po.*;
import com.hx.blog_v2.domain.vo.ResourceVO;
import com.hx.blog_v2.domain.vo.RltRoleResourceVO;
import com.hx.blog_v2.domain.vo.RoleResourceVO;
import com.hx.blog_v2.service.interf.BaseServiceImpl;
import com.hx.blog_v2.service.interf.ResourceService;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.CacheContext;
import com.hx.blog_v2.util.DateUtils;
import com.hx.common.interf.common.Result;
import com.hx.common.util.ResultUtils;
import com.hx.json.JSONArray;
import com.hx.json.JSONObject;
import com.hx.log.alogrithm.tree.TreeUtils;
import com.hx.log.alogrithm.tree.interf.TreeInfoExtractor;
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
public class ResourceServiceImpl extends BaseServiceImpl<ResourcePO> implements ResourceService {

    @Autowired
    private ResourceDao resourceDao;
    @Autowired
    private RltRoleResourceDao rltRoleResourceDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private CacheContext cacheContext;

    @Override
    public Result add(ResourceSaveForm params) {
        Map<String, ResourcePO> resourcesById = cacheContext.allResources();
        if(contains(resourcesById, params.getName())) {
            return ResultUtils.failed("该资源已经存在 !");
        }

        ResourcePO po = new ResourcePO(params.getName(), params.getIconClass(), params.getUrl(),
                params.getParentId(), params.getSort(), params.getEnable());
        try {
            resourceDao.save(po, BlogConstants.ADD_BEAN_CONFIG);
            resourcesById.put(po.getId(), po);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.failed(Tools.errorMsg(e));
        }
        return ResultUtils.success(po.getId());
    }

    @Override
    public Result adminList() {
        Map<String, ResourcePO> resourcesById = cacheContext.allResources();
        String keyOfRoot = idxOfResource(resourcesById, BlogConstants.RESOURCE_ROOT_PARENT_ID);
        if (keyOfRoot == null) {
            return ResultUtils.failed("没有 根节点, 请配置 根节点");
        }

        ResourcePO rootPo = resourcesById.get(keyOfRoot);
        List<ResourcePO> leaves = new ArrayList<>(resourcesById.size());
        for(Map.Entry<String, ResourcePO> entry : resourcesById.entrySet()) {
            ResourcePO resource = entry.getValue();
            if((! rootPo.getId().equals(resource.getId())) && (! rootPo.getId().equals(resource.getParentId())) ) {
                leaves.add(resource);
            }
        }
        return ResultUtils.success(leaves);
    }

    @Override
    public Result adminTreeList(boolean spread) {
        Map<String, ResourcePO> resourcesById = cacheContext.allResources();
        List<ResourceVO> resources = new ArrayList<>(resourcesById.size());
        for(Map.Entry<String, ResourcePO> entry : resourcesById.entrySet()) {
            resources.add(POVOTransferUtils.resourcePO2ResourceVO(entry.getValue()));
        }

        final boolean spreadTmp = spread;
        JSONObject root = TreeUtils.generateTree(resources, new TreeInfoExtractor<ResourceVO>() {
            @Override
            public void extract(ResourceVO bean, JSONObject obj) {
                obj.element("id", bean.getId());
                obj.element("name", bean.getName());
                obj.element("iconClass", bean.getIconClass());
                obj.element("url", bean.getUrl());
                obj.element("sort", bean.getSort());
                obj.element("parentId", bean.getParentId());
                obj.element("enable", bean.getEnable());
                obj.element("spread", spreadTmp);
            }
        }, "children", BlogConstants.RESOURCE_ROOT_PARENT_ID);
        TreeUtils.childArrayify(root, "children");
        return ResultUtils.success(root);
    }

    @Override
    public Result roleResourceList() {
        Map<String, ResourcePO> resourcesById = cacheContext.allResources();
        String roleResourceSql = " select * from rlt_role_resource where role_id in ( %s ) ";

        List<RoleResourceVO> roleResources = (List<RoleResourceVO>) POVOTransferUtils.rolePO2RoleResourceVOList(collectAllRole());
        String roleIds = collectRoleIds(roleResources);
        List<RltRoleResourcePO> rltRoleResources = jdbcTemplate.query(String.format(roleResourceSql, roleIds), new RltRoleResourcePOMapper());
        for (RltRoleResourcePO rltRoleResource : rltRoleResources) {
            int idx = idxOfRole(roleResources, rltRoleResource.getRoleId());
            if (idx >= 0) {
                RoleResourceVO roleResource = roleResources.get(idx);
                roleResource.getResourceIds().add(rltRoleResource.getResourceId());
                roleResource.getResourceNames().add(resourcesById.get(rltRoleResource.getResourceId()).getName());
            } else {
                Log.err(" there is an db query error ! ");
            }
        }

        return ResultUtils.success(roleResources);
    }

    @Override
    public Result update(ResourceSaveForm params) {
        ResourcePO po = cacheContext.allResources().get(params.getId());
        if (po == null) {
            return ResultUtils.failed("该资源不存在 !");
        }

        po.setName(params.getName());
        po.setIconClass(params.getIconClass());
        po.setUrl(params.getUrl());
        po.setParentId(params.getParentId());
        po.setSort(params.getSort());
        po.setEnable(params.getEnable());
        po.setUpdatedAt(DateUtils.formate(new Date(), BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS));
        try {
            long modified = resourceDao.updateById(po, BlogConstants.UPDATE_BEAN_CONFIG)
                    .getModifiedCount();
            if (modified == 0) {
                return ResultUtils.failed("没有找到对应的资源 !");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.failed(Tools.errorMsg(e));
        }
        return ResultUtils.success(po.getId());
    }

    @Override
    public Result roleResourceUpdate(RoleResourceUpdateForm params) {
        String[] resourceIds = params.getResourceIds().split(",");
        List<RltRoleResourcePO> roleResources = null;
        if (!Tools.isEmpty(resourceIds)) {
            Tools.trimAllSpaces(resourceIds);
            roleResources = new ArrayList<>(resourceIds.length);
            for (String resourceId : resourceIds) {
                roleResources.add(new RltRoleResourcePO(params.getRoleId(), resourceId));
            }
        }

        try {
            rltRoleResourceDao.deleteMany(Criteria.eq("role_id", params.getRoleId()));
            if (!CollectionUtils.isEmpty(roleResources)) {
                rltRoleResourceDao.insertMany(roleResources, BlogConstants.ADD_BEAN_CONFIG);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.failed(Tools.errorMsg(e));
        }
        return ResultUtils.success(params.getRoleId());
    }

    @Override
    public Result remove(BeanIdForm params) {
        ResourcePO po = cacheContext.allResources().remove(params.getId());
        if (po == null) {
            return ResultUtils.failed("该资源不存在 !");
        }

        String updatedAt = DateUtils.formate(new Date(), BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS);
        try {
            long deleted = resourceDao.updateOne(Criteria.eq("id", params.getId()),
                    Criteria.set("deleted", "1").add("updated_at", updatedAt)
            ).getModifiedCount();
            if (deleted == 0) {
                return ResultUtils.failed("资源[" + params.getId() + "]不存在 !");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.failed(Tools.errorMsg(e));
        }
        return ResultUtils.success(params.getId());
    }

    // -------------------- 辅助方法 --------------------------

    /**
     * 获取所有的 RolePO
     *
     * @return java.util.List<com.hx.blog_v2.domain.po.RolePO>
     * @author Jerry.X.He
     * @date 5/30/2017 7:46 PM
     * @since 1.0
     */
    private List<RolePO> collectAllRole() {
        Map<String, RolePO> rolesById = cacheContext.allRoles();

        List<RolePO> list = new ArrayList<>(rolesById.size());
        for (Map.Entry<String, RolePO> entry : rolesById.entrySet()) {
            RolePO role = entry.getValue();
            if (role.getEnable() != 0) {
                list.add(role);
            }
        }
        return list;
    }

    /**
     * 收集给定的用户列表的所有用户的id
     *
     * @param allRoles allRoles
     * @return java.util.List<java.lang.String>
     * @author Jerry.X.He
     * @date 5/30/2017 5:35 PM
     * @since 1.0
     */
    private String collectRoleIds(List<RoleResourceVO> allRoles) {
        JSONArray arr = new JSONArray();
        for (RoleResourceVO user : allRoles) {
            arr.add(user.getId());
        }
        String idsWithArr = arr.toString();
        return idsWithArr.substring(1, idsWithArr.length() - 1);
    }

    /**
     * 获取 用户角色 列表中给定的用户的索引
     *
     * @param roleResources roleResources
     * @param id            id
     * @return int
     * @author Jerry.X.He
     * @date 5/30/2017 5:43 PM
     * @since 1.0
     */
    private int idxOfRole(List<RoleResourceVO> roleResources, String id) {
        int idx = 0;
        for (RoleResourceVO userRole : roleResources) {
            if (userRole.getId().equals(id)) {
                return idx;
            }
            idx++;
        }

        return -1;
    }

    /**
     * 获取 resourcesById 中parentId 为给定的parentId 的entry 的key
     *
     * @param resourcesById resourcesById
     * @param parentId      parentId
     * @return java.lang.String
     * @author Jerry.X.He
     * @date 5/30/2017 9:57 PM
     * @since 1.0
     */
    private String idxOfResource(Map<String, ResourcePO> resourcesById, String parentId) {
        for (Map.Entry<String, ResourcePO> entry : resourcesById.entrySet()) {
            if (entry.getValue().getParentId().equals(parentId)) {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * 判断当前所有的 Resource 中 是否有名字为 name的 BlogType
     *
     * @param resourcesById resourcesById
     * @param name      name
     * @return boolean
     * @author Jerry.X.He
     * @date 5/21/2017 6:20 PM
     * @since 1.0
     */
    private boolean contains(Map<String, ResourcePO> resourcesById, String name) {
        for (Map.Entry<String, ResourcePO> entry : resourcesById.entrySet()) {
            if (Tools.equalsIgnoreCase(entry.getValue().getName(), name)) {
                return true;
            }
        }
        return false;
    }

}
