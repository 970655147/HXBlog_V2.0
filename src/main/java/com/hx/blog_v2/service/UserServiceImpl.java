package com.hx.blog_v2.service;

import com.hx.blog_v2.dao.interf.UserDao;
import com.hx.blog_v2.domain.form.BeanIdForm;
import com.hx.blog_v2.domain.form.UserAddForm;
import com.hx.blog_v2.domain.mapper.AdminUserVOMapper;
import com.hx.blog_v2.domain.po.UserPO;
import com.hx.blog_v2.domain.vo.AdminUserVO;
import com.hx.blog_v2.service.interf.UserService;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.DateUtils;
import com.hx.common.interf.common.Result;
import com.hx.common.result.SimplePage;
import com.hx.common.util.ResultUtils;
import com.hx.json.JSONObject;
import com.hx.log.util.Tools;
import com.hx.mongo.criteria.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * UserServiceImpl
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/23/2017 8:22 PM
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Result add(UserAddForm params) {
        UserPO po = new UserPO(params.getUserName(), params.getPassword(), params.getNickName(), params.getEmail(),
                params.getHeadImgUrl(), params.getMotto());

        try {
            userDao.save(po, BlogConstants.IDX_MANAGER_FILTER_ID.getDoLoad(), BlogConstants.IDX_MANAGER_FILTER_ID.getDoFilter());
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.failed(Tools.errorMsg(e));
        }
        return ResultUtils.success(po.getId());
    }

    @Override
    public Result adminList(SimplePage<AdminUserVO> page) {
        String sql = " select * from `user` where deleted = 0 order by created_at desc limit ?, ? ";
        Object[] params = new Object[]{page.recordOffset(), page.getPageSize()};

        List<AdminUserVO> list = jdbcTemplate.query(sql, params, new AdminUserVOMapper());
        page.setList(list);
        return ResultUtils.success(page);
    }

    @Override
    public Result update(UserAddForm params) {
        UserPO po = new UserPO(null, null, params.getNickName(), params.getEmail(), params.getHeadImgUrl(),
                params.getMotto());
        po.setId(params.getId());
        po.setUpdatedAt(DateUtils.formate(new Date(), BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS));

        Map<String, Integer> filterIdxMap = JSONObject.fromObject(BlogConstants.IDX_MANAGER_FILTER_ID.getDoFilter());
        filterIdxMap.put(UserPO.BEAN_KEY, UserPO.FILTER_WHILE_UPDATE);
        try {
            long modified = userDao.updateById(po, BlogConstants.IDX_MANAGER_FILTER_ID.getDoLoad(), filterIdxMap)
                    .getModifiedCount();
            if (modified == 0) {
                return ResultUtils.failed("没有找到对应的用户 !");
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
            long deleted = userDao.updateOne(Criteria.eq("id", params.getId()), Criteria.set("deleted", "1")).getModifiedCount();
            if (deleted == 0) {
                return ResultUtils.failed("用户[" + params.getId() + "]不存在 !");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.failed(Tools.errorMsg(e));
        }
        return ResultUtils.success(params.getId());
    }
}
