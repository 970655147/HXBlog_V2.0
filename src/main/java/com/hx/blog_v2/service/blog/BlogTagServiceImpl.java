package com.hx.blog_v2.service.blog;

import com.hx.blog_v2.context.CacheContext;
import com.hx.blog_v2.context.ConstantsContext;
import com.hx.blog_v2.dao.interf.BlogTagDao;
import com.hx.blog_v2.domain.POVOTransferUtils;
import com.hx.blog_v2.domain.form.common.BeanIdForm;
import com.hx.blog_v2.domain.form.blog.BlogTagSaveForm;
import com.hx.blog_v2.domain.mapper.common.OneIntMapper;
import com.hx.blog_v2.domain.po.blog.BlogTagPO;
import com.hx.blog_v2.domain.vo.blog.BlogTagVO;
import com.hx.blog_v2.service.interf.BaseServiceImpl;
import com.hx.blog_v2.service.interf.blog.BlogTagService;
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
 * BlogTagServiceImpl
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
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ConstantsContext constantsContext;

    @Override
    public Result add(BlogTagSaveForm params) {
        BlogTagPO poByName = BizUtils.findByLogisticId(cacheContext.allBlogTags(), params.getName());
        if (poByName != null) {
            return ResultUtils.failed("该标签已经存在 !");
        }

        BlogTagPO po = new BlogTagPO(params.getName(), params.getSort());
        Result result = blogTagDao.add(po);
        if (!result.isSuccess()) {
            return result;
        }

        cacheContext.putBlogTag(po);
        return ResultUtils.success(po.getId());
    }

    @Override
    public Result list() {
        Map<String, BlogTagPO> tags = cacheContext.allBlogTags();
        List<BlogTagVO> all = new ArrayList<>(tags.size());
        for (Map.Entry<String, BlogTagPO> entry : tags.entrySet()) {
            all.add(POVOTransferUtils.blogTagPO2BlogTagVO(entry.getValue()));
        }

        return ResultUtils.success(all);
    }

    @Override
    public Result update(BlogTagSaveForm params) {
        BlogTagPO po = cacheContext.allBlogTags().get(params.getId());
        if (po == null) {
            return ResultUtils.failed("该标签不存在 !");
        }
        BlogTagPO poByName = BizUtils.findByLogisticId(cacheContext.allBlogTags(), params.getName());
        if ((poByName != null) && (!po.getId().equals(poByName.getId()))) {
            return ResultUtils.failed("该标签已经存在 !");
        }

        po.setName(params.getName());
        po.setSort(params.getSort());
        po.setUpdatedAt(DateUtils.format(new Date(), BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS));
        Result result = blogTagDao.update(po);
        if (!result.isSuccess()) {
            return result;
        }

        cacheContext.putBlogTag(po);
        return ResultUtils.success(params.getId());
    }

    @Override
    public Result remove(BeanIdForm params) {
        BlogTagPO po = cacheContext.allBlogTags().get(params.getId());
        if (po == null) {
            return ResultUtils.failed("该标签不存在 !");
        }
        String countSql = " select count(*) as totalRecord from blog where deleted = 0 and id in ( select blog_id from rlt_blog_tag where tag_id = ? ) ";
        Integer totalRecord = jdbcTemplate.queryForObject(countSql, new Object[]{params.getId()}, new OneIntMapper("totalRecord"));
        if (totalRecord > 0) {
            return ResultUtils.failed("该标签下面还有 " + totalRecord + "篇博客, 请先迁移这部分博客 !");
        }

        po.setUpdatedAt(DateUtils.format(new Date(), BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS));
        po.setDeleted(1);
        Result result = blogTagDao.update(po);
        if (!result.isSuccess()) {
            return result;
        }

        cacheContext.removeBlogTag(params.getId());
        return ResultUtils.success(params.getId());
    }

    @Override
    public Result reSort() {
        Map<String, BlogTagPO> tags = cacheContext.allBlogTags();
        List<BlogTagPO> sortedTags = BizUtils.resort(tags);
        int sort = constantsContext.reSortStart;
        for (BlogTagPO tag : sortedTags) {
            boolean isSortChanged = sort != tag.getSort();
            if (isSortChanged) {
                tag.setSort(sort);
                blogTagDao.update(tag);
            }
            sort += constantsContext.reSortOffset;
        }

        return ResultUtils.success("success");
    }


}
