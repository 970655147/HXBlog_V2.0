package com.hx.blog_v2.service;

import com.hx.blog_v2.dao.interf.BlogTagDao;
import com.hx.blog_v2.domain.POVOTransferUtils;
import com.hx.blog_v2.domain.form.BlogTagAddForm;
import com.hx.blog_v2.domain.form.BlogTagUpdateForm;
import com.hx.blog_v2.domain.po.BlogTagPO;
import com.hx.blog_v2.service.interf.BaseServiceImpl;
import com.hx.blog_v2.service.interf.BlogTagService;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.CacheContext;
import com.hx.blog_v2.util.DateUtils;
import com.hx.common.interf.common.Result;
import com.hx.common.util.ResultUtils;
import com.hx.log.util.Tools;
import com.hx.mongo.criteria.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
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
public class BlogTagServiceImpl extends BaseServiceImpl<BlogTagPO> implements BlogTagService {

    @Autowired
    private BlogTagDao blogTagDao;
    @Autowired
    private CacheContext cacheContext;

    @Override
    public Result add(BlogTagAddForm params) {
        Map<String, BlogTagPO> blogTypes = cacheContext.allBlogTags();
        if (contains(blogTypes, params.getName())) {
            return ResultUtils.failed("该标签已经存在 !");
        }

        BlogTagPO po = new BlogTagPO(params.getName());
        try {
            blogTagDao.save(po, BlogConstants.IDX_MANAGER_FILTER_ID.getDoLoad(), BlogConstants.IDX_MANAGER_FILTER_ID.getDoFilter());
            blogTypes.put(po.getId(), po);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.failed(Tools.errorMsg(e));
        }
        return ResultUtils.success(po.getId());
    }

    @Override
    public Result list() {
        Map<String, BlogTagPO> blogTypes = cacheContext.allBlogTags();
        List<BlogTagPO> all = new ArrayList<>(blogTypes.size());
        for (Map.Entry<String, BlogTagPO> entry : blogTypes.entrySet()) {
            all.add(entry.getValue());
        }
        return ResultUtils.success(POVOTransferUtils.blogTagPO2BlogTagVOList(all));
    }

    @Override
    public Result update(BlogTagUpdateForm params) {
        BlogTagPO po = cacheContext.allBlogTags().get(params.getId());
        if (po == null) {
            return ResultUtils.failed("该标签不存在 !");
        }

        po.setName(params.getName());
        po.setUpdatedAt(DateUtils.formate(new Date(), BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS));
        try {
            long matched = blogTagDao.updateOne(Criteria.eq("id", params.getId()),
                    Criteria.set("name", po.getName()).add("updated_at", po.getUpdatedAt()))
                    .getMatchedCount();
            if(matched == 0) {
                return ResultUtils.failed("该标签不存在 !");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.failed(Tools.errorMsg(e));
        }
        return ResultUtils.success(params.getId());
    }

    @Override
    public Result remove(BlogTagUpdateForm params) {
        BlogTagPO po = cacheContext.allBlogTags().remove(params.getId());
        if (po == null) {
            return ResultUtils.failed("该标签不存在 !");
        }

        try {
            long deleted = blogTagDao.updateOne(Criteria.eq("id", params.getId()), Criteria.set("deleted", 1))
                    .getModifiedCount();
            if(deleted == 0) {
                return ResultUtils.failed("该标签不存在 !");
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
    private boolean contains(Map<String, BlogTagPO> blogTypes, String name) {
        for (Map.Entry<String, BlogTagPO> entry : blogTypes.entrySet()) {
            if (Tools.equalsIgnoreCase(entry.getValue().getName(), name)) {
                return true;
            }
        }
        return false;
    }

}
