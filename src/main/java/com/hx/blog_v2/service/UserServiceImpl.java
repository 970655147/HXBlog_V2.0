package com.hx.blog_v2.service;

import com.hx.blog_v2.dao.interf.UserDao;
import com.hx.blog_v2.domain.POVOTransferUtils;
import com.hx.blog_v2.domain.dto.SessionUser;
import com.hx.blog_v2.domain.form.BeanIdForm;
import com.hx.blog_v2.domain.form.LoginForm;
import com.hx.blog_v2.domain.form.UpdatePwdForm;
import com.hx.blog_v2.domain.form.UserSaveForm;
import com.hx.blog_v2.domain.mapper.AdminUserVOMapper;
import com.hx.blog_v2.domain.mapper.OneIntMapper;
import com.hx.blog_v2.domain.po.UserPO;
import com.hx.blog_v2.domain.vo.AdminUserVO;
import com.hx.blog_v2.service.interf.UserService;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.DateUtils;
import com.hx.blog_v2.util.WebContext;
import com.hx.common.interf.common.Page;
import com.hx.common.interf.common.Result;
import com.hx.common.util.ResultUtils;
import com.hx.json.JSONArray;
import com.hx.log.alogrithm.code.Codec;
import com.hx.log.util.Tools;
import com.hx.mongo.criteria.Criteria;
import com.hx.mongo.criteria.interf.IQueryCriteria;
import com.hx.mongo.criteria.interf.IUpdateCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
    @Autowired
    private BlogConstants constants;

    @Override
    public Result add(UserSaveForm params) {
        String countSql = "select count(*) as totalRecord from `user` where user_name = ? ";
        Integer totalRecord = jdbcTemplate.queryForObject(countSql, new String[]{params.getUserName()}, new OneIntMapper("totalRecord"));
        if (totalRecord > 0) {
            return ResultUtils.failed("用户[" + params.getUserName() + "]已经存在 !");
        }

        UserPO po = new UserPO(params.getUserName(), params.getPassword(), params.getTitle(), params.getNickName(),
                params.getEmail(), params.getHeadImgUrl(), params.getMotto());

        String pwdSalt = newSalt();
        String pwd = encodePwd(params.getPassword(), pwdSalt);
        po.setPwdSalt(pwdSalt);
        po.setPassword(pwd);
        try {
            userDao.save(po, BlogConstants.ADD_BEAN_CONFIG);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.failed(Tools.errorMsg(e));
        }
        return ResultUtils.success(po.getId());
    }

    @Override
    public Result adminList(Page<AdminUserVO> page) {
        String selectSql = " select * from `user` where deleted = 0 order by created_at desc limit ?, ? ";
        String countSql = " select count(*) as totalRecord from `user` where deleted = 0 ";
        Object[] params = new Object[]{page.recordOffset(), page.getPageSize()};

        List<AdminUserVO> list = jdbcTemplate.query(selectSql, params, new AdminUserVOMapper());
        Integer totalRecord = jdbcTemplate.queryForObject(countSql, new OneIntMapper("totalRecord"));
        page.setList(list);
        page.setTotalRecord(totalRecord);
        return ResultUtils.success(page);
    }

    @Override
    public Result update(UserSaveForm params) {
        UserPO po = new UserPO(null, null, params.getNickName(), params.getTitle(), params.getEmail(),
                params.getHeadImgUrl(), params.getMotto());
        po.setId(params.getId());
        po.setUpdatedAt(DateUtils.formate(new Date(), BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS));

        try {
            long modified = userDao.updateById(po, BlogConstants.USER_UPDATE_BEAN_CONFIG)
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
    public Result updatePwd(UpdatePwdForm params) {
        SessionUser user = (SessionUser) WebContext.getAttributeFromSession(BlogConstants.SESSION_USER);
        try {
            UserPO po = userDao.findById(user.getId(), BlogConstants.LOAD_ALL_CONFIG);
            if (po == null) {
                return ResultUtils.failed("用户[" + user.getId() + "]不存在 !");
            }
            if (!po.getPassword().equalsIgnoreCase(encodePwd(params.getOldPwd(), po.getPwdSalt()))) {
                return ResultUtils.failed("用户密码不正确 !");
            }

            String updatedAt = DateUtils.formate(new Date(), BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS);
            String newSalt = newSalt();
            String newPwd = encodePwd(params.getNewPwd(), newSalt);
            long deleted = userDao.updateOne(Criteria.eq("id", user.getId()),
                    Criteria.set("updated_at", updatedAt).add("pwd_salt", newSalt).add("password", newPwd)
            ).getModifiedCount();
            if (deleted == 0) {
                return ResultUtils.failed("用户[" + user.getId() + "]不存在 !");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.failed(Tools.errorMsg(e));
        }
        return ResultUtils.success(user.getId());
    }

    @Override
    public Result remove(BeanIdForm params) {
        String updatedAt = DateUtils.formate(new Date(), BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS);
        try {
            long deleted = userDao.updateOne(Criteria.eq("id", params.getId()),
                    Criteria.set("deleted", "1").add("updated_at", updatedAt)
            ).getModifiedCount();
            if (deleted == 0) {
                return ResultUtils.failed("用户[" + params.getId() + "]不存在 !");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.failed(Tools.errorMsg(e));
        }
        return ResultUtils.success(params.getId());
    }

    @Override
    public Result login(LoginForm params) {
        String checkCodeInServer = (String) WebContext.getAttributeFromSession(BlogConstants.SESSION_CHECK_CODE);
        if (Tools.isEmpty(checkCodeInServer)) {
            return ResultUtils.failed("您还没有验证码 !");
        }
        // TODO: 6/3/2017 完成之后 增加验证码的校验
//        if(! checkCodeInServer.equalsIgnoreCase(params.getCheckCode())) {
//            return ResultUtils.failed("验证码不正确 !");
//        }

        UserPO user = null;
        IQueryCriteria query = Criteria.and(Criteria.eq("user_name", params.getUserName()))
                .add(Criteria.eq("deleted", "0"));
        IUpdateCriteria update = Criteria.set("last_login_ip", params.getIp())
                .add("last_login_at", DateUtils.formate(new Date(), BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS));
        try {
            user = userDao.findOne(query, BlogConstants.LOAD_ALL_CONFIG);
        } catch (Exception e) {
            return ResultUtils.failed("bad userName !");
        }
        if (user == null) {
            return ResultUtils.failed("没有 这个用户");
        }
        if (!encodePwd(params.getPassword(), user.getPwdSalt()).equals(user.getPassword())) {
            return ResultUtils.failed("用户名 或者密码不正确");
        }

        SessionUser sessionUser = POVOTransferUtils.userPO2SessionUser(user);
        String sql = " select role_id from rlt_user_role where user_id = ? order by role_id ";
        List<Integer> roleIds = jdbcTemplate.query(sql, new Object[]{user.getId()}, new OneIntMapper("role_id"));
        String roleIdsStr = collectRoleIds(roleIds);
        sessionUser.setRoleIds(roleIdsStr);
        sessionUser.setSystemUser(true);
        WebContext.setAttributeForSession(BlogConstants.SESSION_USER, sessionUser);

        try {
            userDao.updateOne(Criteria.eq("id", user.getId()), update);
        } catch (Exception e) {
            return ResultUtils.failed("bad userName !");
        }
        return ResultUtils.success("success");
    }

    @Override
    public Result logout() {
        WebContext.removeAttributeFromSession(BlogConstants.SESSION_USER);
        WebContext.removeAttributeFromSession(BlogConstants.SESSION_CHECK_CODE);

        return ResultUtils.success("success");
    }

    // -------------------- 辅助方法 --------------------------

    /**
     * 对给定的密码进行加密
     *
     * @param pwd  pwd
     * @param salt salt
     * @return java.lang.String
     * @author Jerry.X.He
     * @date 6/3/2017 10:31 AM
     * @since 1.0
     */
    private String encodePwd(String pwd, String salt) {
        String desEncoded = Codec.byte2Hex(Codec.desE(pwd.getBytes(), salt.getBytes()));
        String md5Encoded = Codec.byte2Hex(Codec.md5(desEncoded.getBytes()));
        return md5Encoded;
    }

    /**
     * 新建一个 salt
     *
     * @return java.lang.String
     * @author Jerry.X.He
     * @date 6/3/2017 10:34 AM
     * @since 1.0
     */
    private String newSalt() {
        StringBuilder sb = new StringBuilder(constants.pwdSaltNums);
        for (int i = 0; i < constants.pwdSaltNums; i++) {
            sb.append(Tools.ran.nextInt(10));
        }
        return sb.toString();
    }

    /**
     * 获取 roleIds 的字符串表示[作为key]
     *
     * @param roleIds roleIds
     * @return java.lang.String
     * @author Jerry.X.He
     * @date 6/3/2017 3:14 PM
     * @since 1.0
     */
    private String collectRoleIds(List<Integer> roleIds) {
        JSONArray arr = JSONArray.fromObject(roleIds);
        return arr.toString();
    }

}
