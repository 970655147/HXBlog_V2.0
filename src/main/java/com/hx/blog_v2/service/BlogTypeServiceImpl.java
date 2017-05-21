package com.hx.blog_v2.service;

import com.hx.blog_v2.dao.interf.BlogTypeDao;
import com.hx.blog_v2.domain.POVOTransferUtils;
import com.hx.blog_v2.domain.form.BlogTypeAddForm;
import com.hx.blog_v2.domain.form.BlogTypeUpdateForm;
import com.hx.blog_v2.domain.po.BlogTypePO;
import com.hx.blog_v2.service.interf.BaseServiceImpl;
import com.hx.blog_v2.service.interf.BlogTypeService;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.CacheContext;
import com.hx.common.interf.common.Result;
import com.hx.common.util.ResultUtils;
import com.hx.log.util.Tools;
import com.hx.mongo.criteria.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
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
public class BlogTypeServiceImpl extends BaseServiceImpl<BlogTypePO> implements BlogTypeService {

    @Autowired
    private BlogTypeDao blogTypeDao;
    @Autowired
    private CacheContext cacheContext;

    @Override
    public Result add(BlogTypeAddForm params) {
        Map<String, BlogTypePO> blogTypes = cacheContext.allBlogTypes();
        if(contains(blogTypes, params.getName()) ) {
            return ResultUtils.failed("该类型已经存在 !");
        }

        BlogTypePO po = new BlogTypePO(params.getName());
        blogTypes.put(po.getId(), po);
        try {
            blogTypeDao.save(po, BlogConstants.IDX_MANAGER_FILTER_ID.getDoLoad(), BlogConstants.IDX_MANAGER_FILTER_ID.getDoFilter());
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.failed(Tools.errorMsg(e));
        }
        return ResultUtils.success(po.getId());
    }

    @Override
    public Result list() {
        Map<String, BlogTypePO> blogTypes = cacheContext.allBlogTypes();
        List<BlogTypePO> all = new ArrayList<>(blogTypes.size());
        for (Map.Entry<String, BlogTypePO> entry : blogTypes.entrySet()) {
            all.add(entry.getValue());
        }
        return ResultUtils.success(POVOTransferUtils.blogTypePO2BlogTypeVOList(all));
    }


    @Override
    public Result update(BlogTypeUpdateForm params) {
        BlogTypePO po = cacheContext.allBlogTypes().remove(params.getId());
        if (po == null) {
            return ResultUtils.failed("该类型不存在 !");
        }

        po.setName(params.getName());
        try {
            long matched = blogTypeDao.updateOne(Criteria.eq("id", params.getId()), Criteria.set("name", params.getName())).getMatchedCount();
            if(matched == 0) {
                return ResultUtils.failed("该类型不存在 !");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.failed(Tools.errorMsg(e));
        }
        return ResultUtils.success(params.getId());
    }

    @Override
    public Result remove(BlogTypeUpdateForm params) {
        BlogTypePO po = cacheContext.allBlogTypes().remove(params.getId());
        if(po == null) {
            return ResultUtils.failed("该类型不存在 !");
        }

        try {
            long deleted = blogTypeDao.deleteOne(Criteria.eq("id", params.getId())).getDeletedCount();
            if(deleted == 0) {
                return ResultUtils.failed("该类型不存在 !");
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
    private boolean contains(Map<String, BlogTypePO> blogTypes, String name) {
        for (Map.Entry<String, BlogTypePO> entry : blogTypes.entrySet()) {
            if (Tools.equalsIgnoreCase(entry.getValue().getName(), name)) {
                return true;
            }
        }
        return false;
    }

}
