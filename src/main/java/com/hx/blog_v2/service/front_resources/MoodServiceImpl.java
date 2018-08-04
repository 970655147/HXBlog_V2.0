package com.hx.blog_v2.service.front_resources;

import com.hx.blog_v2.dao.interf.MoodDao;
import com.hx.blog_v2.domain.form.common.BeanIdForm;
import com.hx.blog_v2.domain.form.front_resources.MoodSaveForm;
import com.hx.blog_v2.domain.mapper.front_resources.AdminMoodVOMapper;
import com.hx.blog_v2.domain.mapper.front_resources.MoodVOMapper;
import com.hx.blog_v2.domain.mapper.common.OneIntMapper;
import com.hx.blog_v2.domain.po.front_resources.MoodPO;
import com.hx.blog_v2.domain.vo.front_resources.AdminMoodVO;
import com.hx.blog_v2.domain.vo.front_resources.MoodVO;
import com.hx.blog_v2.service.interf.BaseServiceImpl;
import com.hx.blog_v2.service.interf.front_resources.MoodService;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.domain.BaseVO;
import com.hx.blog_v2.util.DateUtils;
import com.hx.blog_v2.util.ResultUtils;
import com.hx.common.interf.common.Page;
import com.hx.common.interf.common.Result;
import com.hx.mongo.criteria.Criteria;
import com.hx.mongo.criteria.interf.IQueryCriteria;
import com.hx.mongo.criteria.interf.IUpdateCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * MoodServiceImpl
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 11:47 AM
 */
@Service
public class MoodServiceImpl extends BaseServiceImpl<MoodPO> implements MoodService {

    @Autowired
    private MoodDao moodDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Result add(MoodSaveForm params) {
        MoodPO po = new MoodPO(params.getTitle(), params.getContent(), params.getEnable());

        Result result = moodDao.add(po);
        if (!result.isSuccess()) {
            return result;
        }
        return ResultUtils.success(po.getId());
    }

    @Override
    public Result list() {
        String sql = " select * from mood where deleted = 0 and enable = 1 order by created_at desc ";
        List<MoodVO> list = jdbcTemplate.query(sql, new MoodVOMapper());
        return ResultUtils.success(list);
    }

    @Override
    public Result adminList(Page<AdminMoodVO> page) {
        String selectSql = " select * from mood where deleted = 0 order by created_at desc limit ?, ? ";
        String countSql = " select count(*) as totalRecord from mood where deleted = 0 ";
        Object[] params = new Object[]{page.recordOffset(), page.getPageSize()};

        List<AdminMoodVO> list = jdbcTemplate.query(selectSql, params, new AdminMoodVOMapper());
        Integer totalRecord = jdbcTemplate.queryForObject(countSql, new OneIntMapper("totalRecord"));
        page.setList(list);
        page.setTotalRecord(totalRecord);
        return ResultUtils.success(page);
    }

    @Override
    public Result update(MoodSaveForm params) {
        MoodPO po = new MoodPO(params.getTitle(), params.getContent(), params.getEnable());
        po.setId(params.getId());
        po.setUpdatedAt(DateUtils.format(new Date(), BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS));

        Result result = moodDao.update(po);
        if (!result.isSuccess()) {
            return result;
        }
        return ResultUtils.success(po.getId());
    }

    @Override
    public Result remove(BeanIdForm params) {
        String updatedAt = DateUtils.format(new Date(), BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS);
        IQueryCriteria query = Criteria.eq("id", params.getId());
        IUpdateCriteria update = Criteria.set("deleted", "1").add("updated_at", updatedAt);
        Result result = moodDao.update(query, update);
        if (!result.isSuccess()) {
            return result;
        }
        return ResultUtils.success(params.getId());
    }
}
