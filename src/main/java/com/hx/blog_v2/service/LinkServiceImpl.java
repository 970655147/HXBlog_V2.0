package com.hx.blog_v2.service;

import com.hx.blog_v2.dao.interf.LinkDao;
import com.hx.blog_v2.dao.interf.MoodDao;
import com.hx.blog_v2.domain.form.BeanIdForm;
import com.hx.blog_v2.domain.form.LinkAddForm;
import com.hx.blog_v2.domain.form.MoodAddForm;
import com.hx.blog_v2.domain.mapper.AdminLinkVOMapper;
import com.hx.blog_v2.domain.mapper.AdminMoodVOMapper;
import com.hx.blog_v2.domain.po.LinkPO;
import com.hx.blog_v2.domain.po.MoodPO;
import com.hx.blog_v2.domain.vo.AdminLinkVO;
import com.hx.blog_v2.domain.vo.AdminMoodVO;
import com.hx.blog_v2.service.interf.BaseServiceImpl;
import com.hx.blog_v2.service.interf.LinkService;
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
public class LinkServiceImpl extends BaseServiceImpl<LinkPO> implements LinkService {

    @Autowired
    private LinkDao linkDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Result add(LinkAddForm params) {
        LinkPO po = new LinkPO(params.getName(), params.getDesc(), params.getUrl(), params.getSort(), params.getEnable());

        try {
            linkDao.save(po, BlogConstants.IDX_MANAGER_FILTER_ID.getDoLoad(), BlogConstants.IDX_MANAGER_FILTER_ID.getDoFilter());
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.failed(Tools.errorMsg(e));
        }
        return ResultUtils.success(po.getId());
    }

    @Override
    public Result adminList(SimplePage<AdminLinkVO> page) {
        String sql = " select * from link where deleted = 0 order by created_at desc limit ?, ? ";
        Object[] params = new Object[]{page.recordOffset(), page.getPageSize() };

        List<AdminLinkVO> list = jdbcTemplate.query(sql, params, new AdminLinkVOMapper());
        page.setList(list);
        return ResultUtils.success(page);
    }

    @Override
    public Result update(LinkAddForm params) {
        LinkPO po = new LinkPO(params.getName(), params.getDesc(), params.getUrl(), params.getSort(), params.getEnable());

        po.setId(params.getId());
        po.setUpdatedAt(DateUtils.formate(new Date(), BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS));
        try {
            long modified = linkDao.updateById(po, BlogConstants.IDX_MANAGER_FILTER_ID.getDoLoad(), BlogConstants.IDX_MANAGER_FILTER_ID.getDoFilter())
                    .getModifiedCount();
            if(modified == 0) {
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
        try {
            long deleted = linkDao.updateOne(Criteria.eq("id", params.getId()), Criteria.set("deleted", "1")).getModifiedCount();
            if (deleted == 0) {
                return ResultUtils.failed("连接[" + params.getId() + "]不存在 !");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.failed(Tools.errorMsg(e));
        }
        return ResultUtils.success(params.getId());
    }
}
