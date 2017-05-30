package com.hx.blog_v2.service;

import com.hx.blog_v2.dao.interf.MoodDao;
import com.hx.blog_v2.dao.interf.ResourceDao;
import com.hx.blog_v2.domain.form.BeanIdForm;
import com.hx.blog_v2.domain.form.MoodSaveForm;
import com.hx.blog_v2.domain.form.ResourceSaveForm;
import com.hx.blog_v2.domain.mapper.AdminMoodVOMapper;
import com.hx.blog_v2.domain.mapper.MoodVOMapper;
import com.hx.blog_v2.domain.mapper.ResourceVOMapper;
import com.hx.blog_v2.domain.po.MoodPO;
import com.hx.blog_v2.domain.po.ResourcePO;
import com.hx.blog_v2.domain.vo.AdminMoodVO;
import com.hx.blog_v2.domain.vo.MoodVO;
import com.hx.blog_v2.domain.vo.ResourceVO;
import com.hx.blog_v2.service.interf.BaseServiceImpl;
import com.hx.blog_v2.service.interf.ResourceService;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.DateUtils;
import com.hx.common.interf.common.Result;
import com.hx.common.result.SimplePage;
import com.hx.common.util.ResultUtils;
import com.hx.json.JSONObject;
import com.hx.log.alogrithm.tree.TreeUtils;
import com.hx.log.alogrithm.tree.interf.TreeInfoExtractor;
import com.hx.log.collection.CollectionUtils;
import com.hx.log.util.Tools;
import com.hx.mongo.criteria.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
    private JdbcTemplate jdbcTemplate;

    @Override
    public Result add(ResourceSaveForm params) {
        ResourcePO po = new ResourcePO(params.getName(), params.getIconClass(), params.getUrl(),
                params.getParentId(), params.getSort(), params.getEnable());

        try {
            resourceDao.save(po, BlogConstants.ADD_BEAN_CONFIG);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.failed(Tools.errorMsg(e));
        }
        return ResultUtils.success(po.getId());
    }

    @Override
    public Result adminList(boolean spread) {
        String resourceSql = " select * from resource where deleted = 0 order by sort ";
        List<ResourceVO> resources =jdbcTemplate.query(resourceSql, new ResourceVOMapper());
        if(CollectionUtils.isEmpty(resources) ) {
            return ResultUtils.success(new JSONObject());
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
        }, "children", "-1");
        TreeUtils.childArrayify(root, "children");
        return ResultUtils.success(root);
    }

    @Override
    public Result update(ResourceSaveForm params) {
        ResourcePO po = new ResourcePO(params.getName(), params.getIconClass(), params.getUrl(),
                params.getParentId(), params.getSort(), params.getEnable());

        po.setId(params.getId());
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
    public Result remove(BeanIdForm params) {
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
}
