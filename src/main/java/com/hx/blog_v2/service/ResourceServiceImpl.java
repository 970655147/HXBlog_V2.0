package com.hx.blog_v2.service;

import com.hx.blog_v2.context.CacheContext;
import com.hx.blog_v2.context.ConstantsContext;
import com.hx.blog_v2.dao.interf.ResourceDao;
import com.hx.blog_v2.dao.interf.RltRoleResourceDao;
import com.hx.blog_v2.domain.POVOTransferUtils;
import com.hx.blog_v2.domain.extractor.AdminResourceTreeInfoExtractor;
import com.hx.blog_v2.domain.form.BeanIdForm;
import com.hx.blog_v2.domain.form.ResourceSaveForm;
import com.hx.blog_v2.domain.form.RoleResourceUpdateForm;
import com.hx.blog_v2.domain.mapper.OneIntMapper;
import com.hx.blog_v2.domain.mapper.RltRoleResourcePOMapper;
import com.hx.blog_v2.domain.po.ResourcePO;
import com.hx.blog_v2.domain.po.RltRoleResourcePO;
import com.hx.blog_v2.domain.po.RolePO;
import com.hx.blog_v2.domain.vo.ResourceVO;
import com.hx.blog_v2.domain.vo.RoleResourceVO;
import com.hx.blog_v2.service.interf.BaseServiceImpl;
import com.hx.blog_v2.service.interf.ResourceService;
import com.hx.blog_v2.util.BizUtils;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.DateUtils;
import com.hx.blog_v2.util.ResultUtils;
import com.hx.common.interf.common.Result;
import com.hx.json.JSONArray;
import com.hx.json.JSONObject;
import com.hx.log.alogrithm.tree.TreeUtils;
import com.hx.log.collection.CollectionUtils;
import com.hx.log.util.Log;
import com.hx.log.util.Tools;
import com.hx.mongo.criteria.Criteria;
import com.hx.mongo.criteria.interf.IQueryCriteria;
import com.hx.mongo.criteria.interf.IUpdateCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

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
    @Autowired
    private ConstantsContext constantsContext;

    @Override
    public Result add(ResourceSaveForm params) {
        Map<String, ResourcePO> resourcesById = cacheContext.allResources();
        ResourcePO poByName = BizUtils.findByLogisticId(resourcesById, params.getName());
        if (poByName != null) {
            return ResultUtils.failed("该资源已经存在 !");
        }
        ResourcePO parentPo = resourcesById.get(params.getParentId());
        if (parentPo == null) {
            return ResultUtils.failed("该资源父节点不存在 !");
        }

        ResourcePO po = new ResourcePO(params.getName(), params.getIconClass(), params.getUrl(),
                params.getParentId(), params.getSort(), parentPo.getLevel() + 1, params.getEnable());
        Result result = resourceDao.add(po);
        if (!result.isSuccess()) {
            return result;
        }

        cacheContext.putResource(po);
        return ResultUtils.success(po.getId());
    }

    @Override
    public Result adminList() {
        Map<String, ResourcePO> resourcesById = cacheContext.allResources();
        List<ResourcePO> leaves = new ArrayList<>(resourcesById.size());
        for (Map.Entry<String, ResourcePO> entry : resourcesById.entrySet()) {
            ResourcePO resource = entry.getValue();
            if (resource.getLevel() == constantsContext.resourceLeaveLevel) {
                leaves.add(resource);
            }
        }

        return ResultUtils.success(leaves);
    }

    @Override
    public Result treeList(boolean spread) {
        Map<String, ResourcePO> resourcesById = cacheContext.allResources();
        List<ResourceVO> resources = new ArrayList<>(resourcesById.size());
        for (Map.Entry<String, ResourcePO> entry : resourcesById.entrySet()) {
            if (entry.getValue().getEnable() == 1) {
                resources.add(POVOTransferUtils.resourcePO2ResourceVO(entry.getValue()));
            }
        }

        JSONObject root = TreeUtils.generateTree(resources, new AdminResourceTreeInfoExtractor(spread),
                "children", constantsContext.resourceRootParentId);
        TreeUtils.childArrayify(root, "children");
        return ResultUtils.success(root);
    }

    @Override
    public Result adminTreeList(boolean spread) {
        Map<String, ResourcePO> resourcesById = cacheContext.allResources();
        List<ResourceVO> resources = new ArrayList<>(resourcesById.size());
        for (Map.Entry<String, ResourcePO> entry : resourcesById.entrySet()) {
            resources.add(POVOTransferUtils.resourcePO2ResourceVO(entry.getValue()));
        }

        JSONObject root = TreeUtils.generateTree(resources, new AdminResourceTreeInfoExtractor(spread),
                "children", constantsContext.resourceRootParentId);
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
        ResourcePO po = cacheContext.resource(params.getId());
        if (po == null) {
            return ResultUtils.failed("该资源不存在 !");
        }
        ResourcePO poByName = BizUtils.findByLogisticId(cacheContext.allResources(), params.getName());
        if ((poByName != null) && (!po.getId().equals(poByName.getId()))) {
            return ResultUtils.failed("该资源已经存在 !");
        }
        ResourcePO parentPo = cacheContext.resource(po.getParentId());
        if (parentPo == null) {
            return ResultUtils.failed("该资源父节点不存在 !");
        }

        po.setName(params.getName());
        po.setIconClass(params.getIconClass());
        po.setUrl(params.getUrl());
        po.setParentId(params.getParentId());
        po.setSort(params.getSort());
        po.setEnable(params.getEnable());
        po.setLevel(parentPo.getLevel() + 1);
        po.setUpdatedAt(DateUtils.formate(new Date(), BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS));
        Result result = resourceDao.update(po);
        if (!result.isSuccess()) {
            return result;
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

        Result removeOldResult = rltRoleResourceDao.remove(Criteria.eq("role_id", params.getRoleId()), true);
        if (!removeOldResult.isSuccess()) {
            return removeOldResult;
        }
        if (!CollectionUtils.isEmpty(roleResources)) {
            Result addResourceResult = rltRoleResourceDao.add(roleResources);
            if (!addResourceResult.isSuccess()) {
                return addResourceResult;
            }
        }
        return ResultUtils.success(params.getRoleId());
    }

    @Override
    public Result remove(BeanIdForm params) {
        ResourcePO po = cacheContext.resource(params.getId());
        if (po == null) {
            return ResultUtils.failed("该资源不存在 !");
        }
        String countSql = " select count(*) as totalRecord from `role` where deleted = 0 " +
                " and id in ( select role_id from rlt_role_resource where resource_id = ? ) ";
        Integer totalRecord = jdbcTemplate.queryForObject(countSql, new Object[]{params.getId()},
                new OneIntMapper("totalRecord"));
        if (totalRecord > 0) {
            return ResultUtils.failed("该资源下面还有 " + totalRecord + "个角色, 请先迁移这部分角色 !");
        }

        String updatedAt = DateUtils.formate(new Date(), BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS);
        IQueryCriteria query = Criteria.eq("id", params.getId());
        IUpdateCriteria update = Criteria.set("deleted", "1").add("updated_at", updatedAt);
        Result result = resourceDao.update(query, update);
        if (!result.isSuccess()) {
            return result;
        }

        cacheContext.removeResource(params.getId());
        return ResultUtils.success(params.getId());
    }

    @Override
    public Result reSort() {
        Map<String, ResourcePO> resourcesById = cacheContext.allResources();
        List<ResourcePO> allResources = new ArrayList<>(resourcesById.size());
        for (Map.Entry<String, ResourcePO> entry : resourcesById.entrySet()) {
            allResources.add(entry.getValue());
        }
        Collections.sort(allResources);

        Map<String, Integer> parent2Sort = new HashMap<>();
        for (ResourcePO po : allResources) {
            Integer sortNow = parent2Sort.get(po.getParentId());
            if (sortNow == null) {
                sortNow = constantsContext.reSortStart;
            }

            Integer newSort = sortNow + constantsContext.reSortOffset;
            parent2Sort.put(po.getParentId(), newSort);
            if (po.getLevel() != newSort.intValue()) {
                po.setSort(sortNow);
                try {
                    resourceDao.updateOne(Criteria.eq("id", po.getId()), Criteria.set("sort", sortNow));
                } catch (Exception e) {
                    Log.err("update ResourcePO[" + po.getId() + "] failed !");
                }
            }
        }

        return ResultUtils.success("success");
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


}
