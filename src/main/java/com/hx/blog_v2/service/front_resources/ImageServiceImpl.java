package com.hx.blog_v2.service.front_resources;

import com.hx.blog_v2.cache_handler.CacheResultType;
import com.hx.blog_v2.cache_handler.CacheType;
import com.hx.blog_v2.cache_handler.anno.CacheEvictAll;
import com.hx.blog_v2.cache_handler.anno.CacheHandle;
import com.hx.blog_v2.context.ConstantsContext;
import com.hx.blog_v2.dao.interf.ImageDao;
import com.hx.blog_v2.domain.form.common.BeanIdForm;
import com.hx.blog_v2.domain.form.front_resources.ImageSaveForm;
import com.hx.blog_v2.domain.form.front_resources.ImageSearchForm;
import com.hx.blog_v2.domain.mapper.common.OneIntMapper;
import com.hx.blog_v2.domain.mapper.front_resources.AdminImageVOMapper;
import com.hx.blog_v2.domain.mapper.front_resources.ImageVOMapper;
import com.hx.blog_v2.domain.po.front_resources.ImagePO;
import com.hx.blog_v2.domain.vo.front_resources.AdminImageVO;
import com.hx.blog_v2.domain.vo.front_resources.ImageVO;
import com.hx.blog_v2.service.interf.BaseServiceImpl;
import com.hx.blog_v2.service.interf.front_resources.ImageService;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.DateUtils;
import com.hx.blog_v2.util.ResultUtils;
import com.hx.common.interf.common.Page;
import com.hx.common.interf.common.Result;
import com.hx.mongo.criteria.Criteria;
import com.hx.mongo.criteria.SortByCriteria;
import com.hx.mongo.criteria.interf.IQueryCriteria;
import com.hx.mongo.criteria.interf.IUpdateCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.hx.blog_v2.util.CacheConstants.*;

/**
 * ImageServiceImpl
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 11:47 AM
 */
@Service
public class ImageServiceImpl extends BaseServiceImpl<ImagePO> implements ImageService {

    @Autowired
    private ImageDao imageDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ConstantsContext constantsContext;

    @Override
    @CacheEvictAll(ns = {CACHE_AOP_ADMIN_LIST_IMAGE, CACHE_AOP_LIST_IMAGE})
    public Result add(ImageSaveForm params) {
        ImagePO po = new ImagePO(params.getTitle(), params.getUrl(), params.getType(), params.getSort(), params.getEnable());

        Result result = imageDao.add(po);
        if (!result.isSuccess()) {
            return result;
        }
        return ResultUtils.success(po.getId());
    }

    @Override
    @CacheHandle(type = CacheType.BASE_REQ, ns = CACHE_AOP_LIST_IMAGE, timeout = CACHE_DEFAULT_TIMEOUT,
            cacheResultType = CacheResultType.RESULT_LIST, cacheResultClass = ImageVO.class)
    public Result imageList(ImageSearchForm params) {
        String sql = " select * from images where deleted = 0 and enable = 1 and type = '" + params.getType() + "' order by sort ";

        List<ImageVO> list = jdbcTemplate.query(sql, new ImageVOMapper());
        return ResultUtils.success(list);
    }

    @Override
    @CacheHandle(type = CacheType.BASE_REQ, ns = CACHE_AOP_ADMIN_LIST_IMAGE, timeout = CACHE_DEFAULT_TIMEOUT,
            cacheResultType = CacheResultType.RESULT_PAGE, cacheResultClass = AdminImageVO.class)
    public Result adminList(ImageSearchForm params, Page<AdminImageVO> page) {
        String selectSql = " select * from images where deleted = 0 and type = '" + params.getType() + "' order by sort limit ?, ? ";
        String countSql = " select count(*) as totalRecord from images where deleted = 0 and type = '" + params.getType() + "' ";
        Object[] sqlParams = new Object[]{page.recordOffset(), page.getPageSize()};

        Integer totalRecord = jdbcTemplate.queryForObject(countSql, new OneIntMapper("totalRecord"));
        if (totalRecord <= 0) {
            page.setList(Collections.<AdminImageVO>emptyList());
        } else {
            List<AdminImageVO> list = jdbcTemplate.query(selectSql, sqlParams, new AdminImageVOMapper());
            page.setList(list);
        }
        page.setTotalRecord(totalRecord);
        return ResultUtils.success(page);
    }

    @Override
    @CacheEvictAll(ns = {CACHE_AOP_ADMIN_LIST_IMAGE, CACHE_AOP_LIST_IMAGE})
    public Result update(ImageSaveForm params) {
        ImagePO po = new ImagePO(params.getTitle(), params.getUrl(), params.getType(), params.getSort(), params.getEnable());
        po.setId(params.getId());
        po.setUpdatedAt(DateUtils.format(new Date(), BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS));

        Result result = imageDao.update(po);
        if (!result.isSuccess()) {
            return result;
        }
        return ResultUtils.success(po.getId());
    }

    @Override
    @CacheEvictAll(ns = {CACHE_AOP_ADMIN_LIST_IMAGE, CACHE_AOP_LIST_IMAGE})
    public Result remove(BeanIdForm params) {
        String updatedAt = DateUtils.format(new Date(), BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS);
        IQueryCriteria query = Criteria.eq("id", params.getId());
        IUpdateCriteria update = Criteria.set("deleted", "1").add("updated_at", updatedAt);

        Result result = imageDao.update(query, update);
        if (!result.isSuccess()) {
            return result;
        }
        return ResultUtils.success(params.getId());
    }

    @Override
    @CacheEvictAll(ns = {CACHE_AOP_ADMIN_LIST_IMAGE, CACHE_AOP_LIST_IMAGE})
    public Result reSort(ImageSearchForm params) {
        IQueryCriteria query = Criteria.and(Criteria.eq("type", params.getType()))
                .add(Criteria.eq("deleted", "0"));
        SortByCriteria sortBy = Criteria.sortBy("sort", SortByCriteria.ASC);
        Result getAllResult = imageDao.list(query, sortBy, Criteria.limitNothing());
        if (!getAllResult.isSuccess()) {
            return getAllResult;
        }

        List<ImagePO> sortedImages = (List<ImagePO>) getAllResult.getData();
        int sort = constantsContext.reSortStart;
        for (ImagePO po : sortedImages) {
            boolean isSortChanged = sort != po.getSort();
            if (isSortChanged) {
                po.setSort(sort);
                imageDao.update(po);
            }
            sort += constantsContext.reSortOffset;
        }

        return ResultUtils.success("success");
    }

    // -------------------- 辅助方法 --------------------------


}
