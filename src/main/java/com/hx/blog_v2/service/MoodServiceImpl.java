package com.hx.blog_v2.service;

import com.hx.blog_v2.dao.interf.MoodDao;
import com.hx.blog_v2.domain.form.BeanIdForm;
import com.hx.blog_v2.domain.form.MoodSaveForm;
import com.hx.blog_v2.domain.mapper.AdminMoodVOMapper;
import com.hx.blog_v2.domain.mapper.MoodVOMapper;
import com.hx.blog_v2.domain.po.MoodPO;
import com.hx.blog_v2.domain.vo.AdminMoodVO;
import com.hx.blog_v2.domain.vo.MoodVO;
import com.hx.blog_v2.service.interf.BaseServiceImpl;
import com.hx.blog_v2.service.interf.MoodService;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.DateUtils;
import com.hx.common.interf.common.Result;
import com.hx.common.result.SimplePage;
import com.hx.common.util.ResultUtils;
import com.hx.log.util.Tools;
import com.hx.mongo.criteria.Criteria;
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
public class MoodServiceImpl extends BaseServiceImpl<MoodPO> implements MoodService {

    @Autowired
    private MoodDao moodDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Result add(MoodSaveForm params) {
        MoodPO po = new MoodPO(params.getTitle(), params.getContent(), params.getEnable());

        try {
            moodDao.save(po, BlogConstants.ADD_BEAN_CONFIG);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.failed(Tools.errorMsg(e));
        }
        return ResultUtils.success(po.getId());
    }

    @Override
    public Result list() {
        String sql = " select * from mood where deleted = 0 and enable = 1 order by created_at ";

        List<MoodVO> list = jdbcTemplate.query(sql, new MoodVOMapper());
        return ResultUtils.success(list);
    }

    @Override
    public Result adminList(SimplePage<AdminMoodVO> page) {
        String sql = " select * from mood where deleted = 0 order by created_at desc limit ?, ? ";
        Object[] params = new Object[]{page.recordOffset(), page.getPageSize()};

        List<AdminMoodVO> list = jdbcTemplate.query(sql, params, new AdminMoodVOMapper());
        page.setList(list);
        return ResultUtils.success(page);
    }

    @Override
    public Result update(MoodSaveForm params) {
        MoodPO po = new MoodPO(params.getTitle(), params.getContent(), params.getEnable());

        po.setId(params.getId());
        po.setUpdatedAt(DateUtils.formate(new Date(), BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS));
        try {
            long modified = moodDao.updateById(po, BlogConstants.UPDATE_BEAN_CONFIG)
                    .getModifiedCount();
            if (modified == 0) {
                return ResultUtils.failed("没有找到对应的心情 !");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.failed(Tools.errorMsg(e));
        }
        return ResultUtils.success(po.getId());
    }

    @Override
    public Result remove(BeanIdForm params) {
        String updatedAt = DateUtils.formate(new Date(), BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS);
        try {
            long deleted = moodDao.updateOne(Criteria.eq("id", params.getId()),
                    Criteria.set("deleted", "1").add("updated_at", updatedAt)
            ).getModifiedCount();
            if (deleted == 0) {
                return ResultUtils.failed("心情[" + params.getId() + "]不存在 !");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.failed(Tools.errorMsg(e));
        }
        return ResultUtils.success(params.getId());
    }
}
