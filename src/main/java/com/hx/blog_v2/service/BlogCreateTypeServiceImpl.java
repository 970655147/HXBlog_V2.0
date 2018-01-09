package com.hx.blog_v2.service;

import com.hx.blog_v2.context.CacheContext;
import com.hx.blog_v2.context.ConstantsContext;
import com.hx.blog_v2.dao.interf.BlogCreateTypeDao;
import com.hx.blog_v2.domain.POVOTransferUtils;
import com.hx.blog_v2.domain.form.BeanIdForm;
import com.hx.blog_v2.domain.form.BlogCreateTypeSaveForm;
import com.hx.blog_v2.domain.mapper.OneIntMapper;
import com.hx.blog_v2.domain.po.BlogCreateTypePO;
import com.hx.blog_v2.domain.vo.BlogCreateTypeVO;
import com.hx.blog_v2.service.interf.BaseServiceImpl;
import com.hx.blog_v2.service.interf.BlogCreateTypeService;
import com.hx.blog_v2.util.BizUtils;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.DateUtils;
import com.hx.blog_v2.util.ResultUtils;
import com.hx.common.interf.common.Result;
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
public class BlogCreateTypeServiceImpl extends BaseServiceImpl<BlogCreateTypePO> implements BlogCreateTypeService {

    @Autowired
    private BlogCreateTypeDao blogCreateTypeDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private CacheContext cacheContext;
    @Autowired
    private ConstantsContext constantsContext;

    @Override
    public Result add(BlogCreateTypeSaveForm params) {
        BlogCreateTypePO poByName = BizUtils.findByLogisticId(cacheContext.allBlogCreateTypes(), params.getName());
        if (poByName != null) {
            return ResultUtils.failed("该创建类型已经存在 !");
        }

        BlogCreateTypePO po = new BlogCreateTypePO(params.getName(), params.getDesc(), params.getImgUrl(), params.getSort());
        Result result = blogCreateTypeDao.add(po);
        if (!result.isSuccess()) {
            return result;
        }

        cacheContext.putBlogCreateType(po);
        return ResultUtils.success(po.getId());
    }

    @Override
    public Result adminList() {
        Map<String, BlogCreateTypePO> blogTypes = cacheContext.allBlogCreateTypes();
        List<BlogCreateTypeVO> all = new ArrayList<>(blogTypes.size());
        for (Map.Entry<String, BlogCreateTypePO> entry : blogTypes.entrySet()) {
            all.add(POVOTransferUtils.blogCreateTypePO2BlogCreateTypeVO(entry.getValue()));
        }
        return ResultUtils.success(all);
    }

    @Override
    public Result update(BlogCreateTypeSaveForm params) {
        BlogCreateTypePO po = cacheContext.allBlogCreateTypes().get(params.getId());
        if (po == null) {
            return ResultUtils.failed("该创建类型不存在 !");
        }
        BlogCreateTypePO poByName = BizUtils.findByLogisticId(cacheContext.allBlogCreateTypes(), params.getName());
        if ((poByName != null) && (!po.getId().equals(poByName.getId()))) {
            return ResultUtils.failed("该创建类型已经存在 !");
        }

        po.setName(params.getName());
        po.setSort(params.getSort());
        po.setUpdatedAt(DateUtils.format(new Date(), BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS));
        Result result = blogCreateTypeDao.update(po);
        if (!result.isSuccess()) {
            return result;
        }

        return ResultUtils.success(params.getId());
    }

    @Override
    public Result remove(BeanIdForm params) {
        BlogCreateTypePO po = cacheContext.allBlogCreateTypes().get(params.getId());
        if (po == null) {
            return ResultUtils.failed("该类型不存在 !");
        }
        String countSql = " select count(*) as totalRecord from blog where deleted = 0 and blog_create_type = ? ";
        Integer totalRecord = jdbcTemplate.queryForObject(countSql, new Object[]{params.getId()}, new OneIntMapper("totalRecord"));
        if (totalRecord > 0) {
            return ResultUtils.failed("该创建类型下面还有 " + totalRecord + "篇博客, 请先迁移这部分博客 !");
        }

        po.setUpdatedAt(DateUtils.format(new Date(), BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS));
        po.setDeleted(1);
        Result result = blogCreateTypeDao.update(po);
        if (!result.isSuccess()) {
            return result;
        }

        cacheContext.removeBlogCreateType(params.getId());
        return ResultUtils.success(params.getId());
    }

    @Override
    public Result reSort() {
        Map<String, BlogCreateTypePO> types = cacheContext.allBlogCreateTypes();
        List<BlogCreateTypePO> sortedTypes = BizUtils.resort(types);
        int sort = constantsContext.reSortStart;
        for (BlogCreateTypePO po : sortedTypes) {
            boolean isSortChanged = sort != po.getSort();
            if (isSortChanged) {
                po.setSort(sort);
                blogCreateTypeDao.update(po);
            }
            sort += constantsContext.reSortOffset;
        }

        return ResultUtils.success("success");
    }

}
