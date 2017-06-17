package com.hx.blog_v2.service;

import com.hx.blog_v2.context.CacheContext;
import com.hx.blog_v2.context.ConstantsContext;
import com.hx.blog_v2.dao.interf.BlogTypeDao;
import com.hx.blog_v2.domain.POVOTransferUtils;
import com.hx.blog_v2.domain.form.BeanIdForm;
import com.hx.blog_v2.domain.form.BlogTypeSaveForm;
import com.hx.blog_v2.domain.mapper.OneIntMapper;
import com.hx.blog_v2.domain.po.BlogTypePO;
import com.hx.blog_v2.domain.vo.BlogTypeVO;
import com.hx.blog_v2.service.interf.BaseServiceImpl;
import com.hx.blog_v2.service.interf.BlogTypeService;
import com.hx.blog_v2.util.BizUtils;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.DateUtils;
import com.hx.common.interf.common.Result;
import com.hx.common.util.ResultUtils;
import com.hx.log.util.Tools;
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
public class BlogTypeServiceImpl extends BaseServiceImpl<BlogTypePO> implements BlogTypeService {

    @Autowired
    private BlogTypeDao blogTypeDao;
    @Autowired
    private CacheContext cacheContext;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ConstantsContext constantsContext;

    @Override
    public Result add(BlogTypeSaveForm params) {
        if (contains(cacheContext.allBlogTypes(), params.getName())) {
            return ResultUtils.failed("该类型已经存在 !");
        }

        BlogTypePO po = new BlogTypePO(params.getName(), params.getSort());
        Result result = blogTypeDao.add(po);
        if (!result.isSuccess()) {
            return result;
        }

        cacheContext.putBlogType(po);
        return ResultUtils.success(po.getId());
    }

    @Override
    public Result list() {
        Map<String, BlogTypePO> blogTypes = cacheContext.allBlogTypes();
        List<BlogTypeVO> all = new ArrayList<>(blogTypes.size());
        for (Map.Entry<String, BlogTypePO> entry : blogTypes.entrySet()) {
            all.add(POVOTransferUtils.blogTypePO2BlogTypeVO(entry.getValue()));
        }
        return ResultUtils.success(all);
    }


    @Override
    public Result update(BlogTypeSaveForm params) {
        BlogTypePO po = cacheContext.allBlogTypes().get(params.getId());
        if (po == null) {
            return ResultUtils.failed("该类型不存在 !");
        }

        po.setName(params.getName());
        po.setSort(params.getSort());
        po.setUpdatedAt(DateUtils.formate(new Date(), BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS));
        Result result = blogTypeDao.update(po);
        if (!result.isSuccess()) {
            return result;
        }

        cacheContext.putBlogType(po);
        return ResultUtils.success(params.getId());
    }

    @Override
    public Result remove(BeanIdForm params) {
        BlogTypePO po = cacheContext.allBlogTypes().get(params.getId());
        if (po == null) {
            return ResultUtils.failed("该类型不存在 !");
        }
        String countSql = " select count(*) as totalRecord from blog where deleted = 0 and blog_type_id = ? ";
        Integer totalRecord = jdbcTemplate.queryForObject(countSql, new Object[]{params.getId()}, new OneIntMapper("totalRecord"));
        if (totalRecord > 0) {
            return ResultUtils.failed("该类型下面还有 " + totalRecord + "篇博客, 请先迁移这部分博客 !");
        }

        cacheContext.allBlogTypes().remove(params.getId());
        po.setUpdatedAt(DateUtils.formate(new Date(), BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS));
        po.setDeleted(1);
        Result result = blogTypeDao.update(po);
        if (!result.isSuccess()) {
            return result;
        }

        return ResultUtils.success(params.getId());
    }

    @Override
    public Result reSort() {
        Map<String, BlogTypePO> types = cacheContext.allBlogTypes();
        List<BlogTypePO> sortedTypes = BizUtils.resort(types);
        int sort = constantsContext.reSortStart;
        for (BlogTypePO tag : sortedTypes) {
            boolean isSortChanged = sort != tag.getSort();
            if (isSortChanged) {
                tag.setSort(sort);
                blogTypeDao.update(tag);
            }
            sort += constantsContext.reSortOffset;
        }

        return ResultUtils.success("success");
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
