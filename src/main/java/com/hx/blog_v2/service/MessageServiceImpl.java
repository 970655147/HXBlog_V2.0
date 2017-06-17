package com.hx.blog_v2.service;

import com.hx.blog_v2.dao.interf.MessageDao;
import com.hx.blog_v2.dao.interf.UserDao;
import com.hx.blog_v2.domain.dto.MessageType;
import com.hx.blog_v2.domain.dto.SessionUser;
import com.hx.blog_v2.domain.form.BeanIdForm;
import com.hx.blog_v2.domain.form.MessageSaveForm;
import com.hx.blog_v2.domain.form.MessageSearchForm;
import com.hx.blog_v2.domain.mapper.MessageVOMapper;
import com.hx.blog_v2.domain.mapper.OneIntMapper;
import com.hx.blog_v2.domain.mapper.OneStringMapper;
import com.hx.blog_v2.domain.mapper.ToMapMapper;
import com.hx.blog_v2.domain.po.MessagePO;
import com.hx.blog_v2.domain.vo.MessageVO;
import com.hx.blog_v2.service.interf.BaseServiceImpl;
import com.hx.blog_v2.service.interf.MessageService;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.DateUtils;
import com.hx.blog_v2.util.SqlUtils;
import com.hx.blog_v2.context.WebContext;
import com.hx.common.interf.common.Page;
import com.hx.common.interf.common.Result;
import com.hx.common.util.ResultUtils;
import com.hx.json.JSONArray;
import com.hx.log.util.Tools;
import com.hx.mongo.criteria.Criteria;
import com.hx.mongo.criteria.interf.IQueryCriteria;
import com.hx.mongo.criteria.interf.IUpdateCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * BlogServiceImpl
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 11:47 AM
 */
@Service
public class MessageServiceImpl extends BaseServiceImpl<MessagePO> implements MessageService {

    @Autowired
    private MessageDao messageDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Result add(MessageSaveForm params) {
        Set<String> userIds = new HashSet<>();
        if (!Tools.isEmpty(params.getUserIds())) {
            Collections.addAll(userIds, Tools.trimAllSpaces(params.getUserIds().split(",")));
        }
        if (!Tools.isEmpty(params.getRoleIds())) {
            String sql = " select user_id from rlt_user_role where role_id in ( %s ) ";
            String[] roleIds = params.getRoleIds().split(",");
            String inSnippet = SqlUtils.wrapInSnippet(Tools.asSet(roleIds));
            List<String> userIdsByRole = jdbcTemplate.query(String.format(sql, inSnippet), new OneStringMapper("user_id"));
            userIds.addAll(userIdsByRole);
        }

        SessionUser user = (SessionUser) WebContext.getAttributeFromSession(BlogConstants.SESSION_USER);
        List<MessagePO> allMsg = new ArrayList<>(userIds.size());
        for (String userId : userIds) {
            allMsg.add(new MessagePO(user.getId(), userId, MessageType.SITE_INTERNAL.getType(), params.getSubject(), params.getContent()));
        }
        Result result = messageDao.add(allMsg);
        if (!result.isSuccess()) {
            return result;
        }

        return ResultUtils.success(collectMessageIds(allMsg));
    }

    @Override
    public Result list(MessageSearchForm params, Page<MessageVO> page) {
        String selectSql = " select * from message where deleted = 0 ";
        String selectSqlSuffix = " order by created_at desc limit ?, ?";
        String countSql = " select count(*) as totalRecord from message where deleted = 0 ";
        String currentUserId = WebContext.getStrAttrFromSession(BlogConstants.SESSION_USER_ID);
        params.setReceiverId(currentUserId);

        StringBuilder condSqlSb = new StringBuilder();
        List<Object> selectParams = new ArrayList<>(3);
        encapQueryForAdminList(params, condSqlSb, selectParams);
        String condSql = condSqlSb.toString();
        Object[] countParams = selectParams.toArray();
        selectParams.add(page.recordOffset());
        selectParams.add(page.getPageSize());

        List<MessageVO> list = jdbcTemplate.query(selectSql + condSql + selectSqlSuffix, selectParams.toArray(), new MessageVOMapper());
        Integer totalRecord = jdbcTemplate.queryForObject(countSql + condSql, countParams, new OneIntMapper("totalRecord"));
        encapNames(list);
        page.setList(list);
        page.setTotalRecord(totalRecord);
        return ResultUtils.success(page);
    }

    @Override
    public Result adminList(MessageSearchForm params, Page<MessageVO> page) {
        String selectSql = " select * from message where deleted = 0 ";
        String selectSqlSuffix = " order by created_at desc limit ?, ?";
        String countSql = " select count(*) as totalRecord from message where deleted = 0 ";

        StringBuilder condSqlSb = new StringBuilder();
        List<Object> selectParams = new ArrayList<>(3);
        encapQueryForAdminList(params, condSqlSb, selectParams);
        String condSql = condSqlSb.toString();
        Object[] countParams = selectParams.toArray();
        selectParams.add(page.recordOffset());
        selectParams.add(page.getPageSize());

        List<MessageVO> list = jdbcTemplate.query(selectSql + condSql + selectSqlSuffix, selectParams.toArray(), new MessageVOMapper());
        Integer totalRecord = jdbcTemplate.queryForObject(countSql + condSql, countParams, new OneIntMapper("totalRecord"));
        encapNames(list);
        page.setList(list);
        page.setTotalRecord(totalRecord);
        return ResultUtils.success(page);
    }

    @Override
    public Result update(MessageSaveForm params) {
        String updateAt = DateUtils.formate(new Date(), BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS);
        IQueryCriteria query = Criteria.eq("id", params.getId());
        IUpdateCriteria update = Criteria.set("subject", params.getSubject()).add("content", params.getContent())
                .add("updated_at", updateAt);

        Result result = messageDao.update(query, update);
        if (!result.isSuccess()) {
            return result;
        }
        return ResultUtils.success(params.getId());
    }

    @Override
    public Result markConsumed(BeanIdForm params) {
        String updateAt = DateUtils.formate(new Date(), BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS);
        IQueryCriteria query = Criteria.eq("id", params.getId());
        IUpdateCriteria update = Criteria.set("consumed", "1").add("updated_at", updateAt);

        Result result = messageDao.update(query, update);
        if (!result.isSuccess()) {
            return result;
        }
        return ResultUtils.success(params.getId());
    }

    @Override
    public Result remove(BeanIdForm params) {
        String updatedAt = DateUtils.formate(new Date(), BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS);
        IQueryCriteria query = Criteria.eq("id", params.getId());
        IUpdateCriteria update = Criteria.set("deleted", "1").add("updated_at", updatedAt);

        Result result = messageDao.update(query, update);
        if (!result.isSuccess()) {
            return result;
        }
        return ResultUtils.success(params.getId());
    }


    // -------------------- 辅助方法 --------------------------

    /**
     * 收集给定的 消息列表的所有的 message 的id
     *
     * @param msgs msgs
     * @return java.lang.String
     * @author Jerry.X.He
     * @date 6/11/2017 3:29 PM
     * @since 1.0
     */
    private String collectMessageIds(List<MessagePO> msgs) {
        JSONArray result = new JSONArray();
        for (MessagePO po : msgs) {
            result.add(po.getId());
        }
        return result.toString();
    }

    /**
     * 封装查询条件, 参数
     *
     * @param params        params
     * @param condSqlSb     condSqlSb
     * @param sqlParamsList sqlParamsList
     * @return void
     * @author Jerry.X.He
     * @date 6/11/2017 3:31 PM
     * @since 1.0
     */
    private void encapQueryForAdminList(MessageSearchForm params, StringBuilder condSqlSb, List<Object> sqlParamsList) {
        if (!Tools.isEmpty(params.getId())) {
            condSqlSb.append(" and id = ? ");
            sqlParamsList.add(params.getId());
        } else {
            if (!Tools.isEmpty(params.getSenderId())) {
                condSqlSb.append(" and sender_id = ? ");
                sqlParamsList.add(params.getSenderId());
            }
            if (!Tools.isEmpty(params.getReceiverId())) {
                condSqlSb.append(" and receiver_id = ? ");
                sqlParamsList.add(params.getReceiverId());
            }
            if (!Tools.isEmpty(params.getRoleId())) {
                condSqlSb.append(" and sender_id in ( select user_id from rlt_user_role where role_id = ? ) ");
                sqlParamsList.add(params.getRoleId());
            }
            if (!Tools.isEmpty(params.getSubject())) {
                condSqlSb.append(" and subject like ? ");
                sqlParamsList.add(SqlUtils.wrapWildcard(params.getSubject()));
            }
            if (!Tools.isEmpty(params.getContent())) {
                condSqlSb.append(" and content like ? ");
                sqlParamsList.add(SqlUtils.wrapWildcard(params.getContent()));
            }
        }
    }

    /**
     * 查询用户的名字, 封装到 messageVO 中
     *
     * @param msgs msgs
     * @return void
     * @author Jerry.X.He
     * @date 6/11/2017 4:32 PM
     * @since 1.0
     */
    private void encapNames(List<MessageVO> msgs) {
        if (Tools.isEmpty(msgs)) {
            return;
        }
        Set<String> userIds = new HashSet<>(Tools.estimateMapSize(msgs.size() << 1));
        for (MessageVO msg : msgs) {
            userIds.add(msg.getSenderId());
            userIds.add(msg.getReceiverId());
        }

        String selectUserNameSql = " select id, user_name from user where id in ( %s ) ";
        List<Map<String, Object>> id2Names = jdbcTemplate.query(String.format(selectUserNameSql, SqlUtils.wrapInSnippet(userIds)), new ToMapMapper());
        for (MessageVO msg : msgs) {
            msg.setSenderName(getNameFor(id2Names, "id", "user_name", msg.getSenderId()));
            msg.setReceiverName(getNameFor(id2Names, "id", "user_name", msg.getReceiverId()));
        }
    }

    /**
     * 从 list 中获取id为给定的id的元素的name
     *
     * @param list    list
     * @param idKey   idKey
     * @param nameKey nameKey
     * @param id      id
     * @return java.lang.String
     * @author Jerry.X.He
     * @date 6/11/2017 4:31 PM
     * @since 1.0
     */
    private String getNameFor(List<Map<String, Object>> list, String idKey, String nameKey, String id) {
        for (Map<String, Object> ele : list) {
            if (id.equals(Tools.optString(ele, idKey, ""))) {
                return Tools.optString(ele, nameKey, "unknown");
            }
        }

        return "unknown";
    }

}
