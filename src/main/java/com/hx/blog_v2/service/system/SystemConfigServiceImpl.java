package com.hx.blog_v2.service.system;

import com.hx.blog_v2.context.ConstantsContext;
import com.hx.blog_v2.dao.interf.SystemConfigDao;
import com.hx.blog_v2.domain.form.common.BeanIdForm;
import com.hx.blog_v2.domain.form.system.SystemConfigSaveForm;
import com.hx.blog_v2.domain.form.system.SystemConfigSearchForm;
import com.hx.blog_v2.domain.mapper.common.OneIntMapper;
import com.hx.blog_v2.domain.mapper.system.SystemConfigVOMapper;
import com.hx.blog_v2.domain.po.system.SystemConfigPO;
import com.hx.blog_v2.domain.vo.system.SystemConfigVO;
import com.hx.blog_v2.service.interf.BaseServiceImpl;
import com.hx.blog_v2.service.interf.system.SystemConfigService;
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

/**
 * SystemConfigServiceImpl
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
    private ConstantsContext constantsContext;

    @Override
    public Result add(SystemConfigSaveForm params) {
        String val = constantsContext.configByTypeAndKey(params.getType(), params.getName());
        if (val != null) {
            return ResultUtils.failed(" 该配置已经存在 !");
        }

        SystemConfigPO po = new SystemConfigPO(params.getName(), params.getValue(), params.getDesc(),
                params.getType(), params.getSort(), params.getEnable());
        Result result = systemConfigDao.add(po);
        if (!result.isSuccess()) {
            return result;
        }

        constantsContext.putConfigByTypeAndKey(params.getType(), params.getName(), po.getValue());
        return ResultUtils.success(po.getId());
    }

    @Override
    public Result adminList(SystemConfigSearchForm params, Page<SystemConfigVO> page) {
        String selectSql = " select * from system_config where deleted = 0 and type = '" + params.getType() + "' order by sort limit ?, ? ";
        String countSql = " select count(*) as totalRecord from system_config where deleted = 0 and type = '" + params.getType() + "' ";
        Object[] sqlParams = new Object[]{page.recordOffset(), page.getPageSize()};

        Integer totalRecord = jdbcTemplate.queryForObject(countSql, new OneIntMapper("totalRecord"));
        if (totalRecord <= 0) {
            page.setList(Collections.<SystemConfigVO>emptyList());
        } else {
            List<SystemConfigVO> list = jdbcTemplate.query(selectSql, sqlParams, new SystemConfigVOMapper());
            page.setList(list);
        }
        page.setTotalRecord(totalRecord);
        return ResultUtils.success(page);
    }

    @Override
    public Result update(SystemConfigSaveForm params) {
        String val = constantsContext.configByTypeAndKey(params.getType(), params.getName());
        if (val == null) {
            return ResultUtils.failed(" 该配置不存在 !");
        }

        SystemConfigPO po = new SystemConfigPO(params.getName(), params.getValue(), params.getDesc(),
                params.getType(), params.getSort(), params.getEnable());
        po.setId(params.getId());
        po.setUpdatedAt(DateUtils.format(new Date(), BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS));
        Result result = systemConfigDao.update(po);
        if (!result.isSuccess()) {
            return result;
        }

        constantsContext.putConfigByTypeAndKey(params.getType(), params.getName(), po.getValue());
        return ResultUtils.success(po.getId());
    }

    @Override
    public Result remove(BeanIdForm params) {
        Result getResult = systemConfigDao.get(params);
        if (!getResult.isSuccess()) {
            return getResult;
        }
        SystemConfigPO po = (SystemConfigPO) getResult.getData();
        if (!constantsContext.configByType(po.getType()).containsKey(po.getName())) {
            return ResultUtils.failed(" 缓存和数据库数据不一致 !");
        }

        String updatedAt = DateUtils.format(new Date(), BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS);
        IQueryCriteria query = Criteria.eq("id", params.getId());
        IUpdateCriteria update = Criteria.set("deleted", "1").add("updated_at", updatedAt);
        Result result = systemConfigDao.update(query, update);
        if (!result.isSuccess()) {
            return result;
        }

        constantsContext.configByType(po.getType()).remove(po.getName());
        return ResultUtils.success(params.getId());
    }

    @Override
    public Result reSort(SystemConfigSearchForm params) {
        IQueryCriteria query = Criteria.and(Criteria.eq("type", params.getType()))
                .add(Criteria.eq("deleted", "0"));
        SortByCriteria sortBy = Criteria.sortBy("sort", SortByCriteria.ASC);
        Result getAllResult = systemConfigDao.list(query, sortBy, Criteria.limitNothing());
        if (!getAllResult.isSuccess()) {
            return getAllResult;
        }

        List<SystemConfigPO> sortedImages = (List<SystemConfigPO>) getAllResult.getData();
        int sort = constantsContext.reSortStart;
        for (SystemConfigPO po : sortedImages) {
            boolean isSortChanged = sort != po.getSort();
            if (isSortChanged) {
                po.setSort(sort);
                systemConfigDao.update(po);
            }
            sort += constantsContext.reSortOffset;
        }

        return ResultUtils.success("success");
    }

    // -------------------- 辅助方法 --------------------------


}
