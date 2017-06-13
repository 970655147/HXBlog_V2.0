package com.hx.blog_v2.service;

import com.hx.blog_v2.dao.interf.SystemConfigDao;
import com.hx.blog_v2.domain.form.BeanIdForm;
import com.hx.blog_v2.domain.form.SystemConfigSaveForm;
import com.hx.blog_v2.domain.form.SystemConfigSearchForm;
import com.hx.blog_v2.domain.mapper.OneIntMapper;
import com.hx.blog_v2.domain.mapper.SystemConfigVOMapper;
import com.hx.blog_v2.domain.po.SystemConfigPO;
import com.hx.blog_v2.domain.vo.SystemConfigVO;
import com.hx.blog_v2.service.interf.BaseServiceImpl;
import com.hx.blog_v2.service.interf.SystemConfigService;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.CacheContext;
import com.hx.blog_v2.util.DateUtils;
import com.hx.common.interf.common.Page;
import com.hx.common.interf.common.Result;
import com.hx.common.util.ResultUtils;
import com.hx.mongo.criteria.Criteria;
import com.hx.mongo.criteria.SortByCriteria;
import com.hx.mongo.criteria.interf.IQueryCriteria;
import com.hx.mongo.criteria.interf.IUpdateCriteria;
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
public class SystemConfigServiceImpl extends BaseServiceImpl<SystemConfigPO> implements SystemConfigService {

    @Autowired
    private SystemConfigDao systemConfigDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private CacheContext cacheContext;

    @Override
    public Result add(SystemConfigSaveForm params) {
        SystemConfigPO po = new SystemConfigPO(params.getName(), params.getValue(), params.getDesc(),
                params.getType(), params.getSort(), params.getEnable());

        Result result = systemConfigDao.add(po);
        if (!result.isSuccess()) {
            return result;
        }
        return ResultUtils.success(po.getId());
    }

    @Override
    public Result adminList(SystemConfigSearchForm params, Page<SystemConfigVO> page) {
        String selectSql = " select * from system_config where deleted = 0 and type = '" + params.getType() + "' order by sort limit ?, ? ";
        String countSql = " select count(*) as totalRecord from system_config where deleted = 0 and type = ' " + params.getType() + " ' ";
        Object[] sqlParams = new Object[]{page.recordOffset(), page.getPageSize()};

        List<SystemConfigVO> list = jdbcTemplate.query(selectSql, sqlParams, new SystemConfigVOMapper());
        Integer totalRecord = jdbcTemplate.queryForObject(countSql, new OneIntMapper("totalRecord"));
        page.setList(list);
        page.setTotalRecord(totalRecord);
        return ResultUtils.success(page);
    }

    @Override
    public Result update(SystemConfigSaveForm params) {
        SystemConfigPO po = new SystemConfigPO(params.getName(), params.getValue(), params.getDesc(),
                params.getType(), params.getSort(), params.getEnable());
        po.setId(params.getId());
        po.setUpdatedAt(DateUtils.formate(new Date(), BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS));

        Result result = systemConfigDao.update(po);
        if (!result.isSuccess()) {
            return result;
        }
        return ResultUtils.success(po.getId());
    }

    @Override
    public Result remove(BeanIdForm params) {
        String updatedAt = DateUtils.formate(new Date(), BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS);
        IQueryCriteria query = Criteria.eq("id", params.getId());
        IUpdateCriteria update = Criteria.set("deleted", "1").add("updated_at", updatedAt);

        Result result = systemConfigDao.update(query, update);
        if (!result.isSuccess()) {
            return result;
        }
        return ResultUtils.success(params.getId());
    }

    @Override
    public Result reSort(SystemConfigSearchForm params) {
        IQueryCriteria query = Criteria.eq("type", params.getType());
        SortByCriteria sortBy = Criteria.sortBy("sort", SortByCriteria.ASC);
        Result getAllResult = systemConfigDao.list(query, sortBy, Criteria.limitNothing());
        if (!getAllResult.isSuccess()) {
            return getAllResult;
        }

        List<SystemConfigPO> sortedImages = (List<SystemConfigPO>) getAllResult.getData();
        int sort = BlogConstants.RE_SORT_START;
        for (SystemConfigPO po : sortedImages) {
            boolean isSortChanged = sort != po.getSort();
            if (isSortChanged) {
                po.setSort(sort);
                systemConfigDao.update(po);
            }
            sort += BlogConstants.RE_SORT_OFFSET;
        }

        return ResultUtils.success("success");
    }

    // -------------------- 辅助方法 --------------------------


}
