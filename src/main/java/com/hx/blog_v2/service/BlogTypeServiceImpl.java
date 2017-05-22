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
import com.hx.blog_v2.util.DateUtils;
import com.hx.common.interf.common.Result;
import com.hx.common.util.ResultUtils;
import com.hx.log.util.Tools;
import com.hx.mongo.criteria.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
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
public class BlogTypeServiceImpl extends BaseServiceImpl<BlogTypePO> implements BlogTypeService {

    @Autowired
    private BlogTypeDao blogTypeDao;
    @Autowired
    private CacheContext cacheContext;

    @Override
    public Result add(BlogTypeAddForm params) {
        Map<String, BlogTypePO> blogTypes = cacheContext.allBlogTypes();
        if(contains(blogTypes, params.getName()) ) {
            return ResultUtils.failed("�������Ѿ����� !");
        }

        BlogTypePO po = new BlogTypePO(params.getName());
        try {
            blogTypeDao.save(po, BlogConstants.IDX_MANAGER_FILTER_ID.getDoLoad(), BlogConstants.IDX_MANAGER_FILTER_ID.getDoFilter());
            blogTypes.put(po.getId(), po);
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
        BlogTypePO po = cacheContext.allBlogTypes().get(params.getId());
        if (po == null) {
            return ResultUtils.failed("�����Ͳ����� !");
        }

        po.setName(params.getName());
        po.setUpdatedAt(DateUtils.formate(new Date(), BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS));
        try {
            long modified = blogTypeDao.updateOne(Criteria.eq("id", params.getId()),
                    Criteria.set("name", po.getName()).add("updated_at", po.getUpdatedAt()))
                    .getModifiedCount();
            if(modified == 0) {
                return ResultUtils.failed("�����Ͳ����� !");
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
            return ResultUtils.failed("�����Ͳ����� !");
        }

        try {
            long modified = blogTypeDao.updateOne(Criteria.eq("id", params.getId()), Criteria.set("deleted", 1))
                    .getModifiedCount();
            if(modified == 0) {
                return ResultUtils.failed("�����Ͳ����� !");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.failed(Tools.errorMsg(e));
        }
        return ResultUtils.success(params.getId());
    }


    // -------------------- �������� --------------------------

    /**
     * �жϵ�ǰ���е� BlogType �� �Ƿ�������Ϊ name�� BlogType
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
