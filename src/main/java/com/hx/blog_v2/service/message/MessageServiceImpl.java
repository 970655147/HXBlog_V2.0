package com.hx.blog_v2.service.message;

import com.hx.blog_v2.cache_handler.CacheResultType;
import com.hx.blog_v2.cache_handler.CacheType;
import com.hx.blog_v2.cache_handler.anno.CacheEvictAll;
import com.hx.blog_v2.cache_handler.anno.CacheHandle;
import com.hx.blog_v2.context.ConstantsContext;
import com.hx.blog_v2.context.WebContext;
import com.hx.blog_v2.dao.interf.MessageDao;
import com.hx.blog_v2.domain.common.message.EmailContentType;
import com.hx.blog_v2.domain.common.system.SessionUser;
import com.hx.blog_v2.domain.common.common.StringStringPair;
import com.hx.blog_v2.domain.form.common.BeanIdForm;
import com.hx.blog_v2.domain.form.message.MessageSaveForm;
import com.hx.blog_v2.domain.form.message.MessageSearchForm;
import com.hx.blog_v2.domain.mapper.message.MessageVOMapper;
import com.hx.blog_v2.domain.mapper.common.OneIntMapper;
import com.hx.blog_v2.domain.mapper.common.OneStringMapper;
import com.hx.blog_v2.domain.mapper.common.StringStringPairMapper;
import com.hx.blog_v2.domain.mapper.common.ToMapMapper;
import com.hx.blog_v2.domain.po.message.EmailPO;
import com.hx.blog_v2.domain.po.message.MessagePO;
import com.hx.blog_v2.domain.validator.message.EmailValidator;
import com.hx.blog_v2.domain.vo.front_resources.MoodVO;
import com.hx.blog_v2.domain.vo.message.MessageVO;
import com.hx.blog_v2.service.interf.BaseServiceImpl;
import com.hx.blog_v2.service.interf.message.EmailService;
import com.hx.blog_v2.service.interf.message.MessageService;
import com.hx.blog_v2.util.*;
import com.hx.common.interf.common.Page;
import com.hx.common.interf.common.Result;
import com.hx.json.JSONArray;
import com.hx.json.JSONObject;
import com.hx.log.collection.CollectionUtils;
import com.hx.log.log.LogPatternUtils;
import com.hx.log.util.Tools;
import com.hx.mongo.criteria.Criteria;
import com.hx.mongo.criteria.interf.IQueryCriteria;
import com.hx.mongo.criteria.interf.IUpdateCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.hx.blog_v2.util.CacheConstants.*;

/**
 * MessageServiceImpl
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
    private EmailService emailService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ConstantsContext constantsContext;
    @Autowired
    private EmailValidator emailValidator;

    @Override
    @CacheEvictAll(ns = {CACHE_AOP_ADMIN_PAGE_MESSAGE, CACHE_AOP_PAGE_MESSAGE})
    public Result add(MessageSaveForm params) {
        Set<String> userIds = new HashSet<>();
        if (!Tools.isEmpty(params.getUserIds())) {
            Collections.addAll(userIds, Tools.trimAllSpaces(params.getUserIds().split(",")));
        }
        if (!Tools.isEmpty(params.getUserNames())) {
            String sql = " select id from user where user_name in ( %s ) ";
            String[] roleIds = params.getUserNames().split(",");
            String inSnippet = SqlUtils.wrapInSnippetForIds(Tools.asSet(roleIds));
            List<String> userIdsByName = jdbcTemplate.query(String.format(sql, inSnippet), new OneStringMapper("id"));
            userIds.addAll(userIdsByName);
        }
        if (!Tools.isEmpty(params.getRoleIds())) {
            String sql = " select user_id from rlt_user_role where role_id in ( %s ) ";
            String[] roleIds = params.getRoleIds().split(",");
            String inSnippet = SqlUtils.wrapInSnippetForIds(Tools.asSet(roleIds));
            List<String> userIdsByRole = jdbcTemplate.query(String.format(sql, inSnippet), new OneStringMapper("user_id"));
            userIds.addAll(userIdsByRole);
        }

        SessionUser user = (SessionUser) WebContext.getAttributeFromSession(BlogConstants.SESSION_USER);
        List<MessagePO> allMsg = new ArrayList<>(userIds.size());
        for (String userId : userIds) {
            allMsg.add(new MessagePO(user.getId(), userId, params.getType(),
                    params.getSubject(), params.getContent()));
        }
        Result result = messageDao.add(allMsg);
        if (!result.isSuccess()) {
            return result;
        }

        // TODO: 7/1/2017 上线注释部分代码
//        if (constantsContext.sendEmailIfWithNotify && (!Tools.isEmpty(allMsg))) {
//            Result sendEmailResult = sendEmailNotify(userIds, allMsg);
//            if (!sendEmailResult.isSuccess()) {
//                return sendEmailResult;
//            }
//        }
        return ResultUtils.success(collectMessageIds(allMsg));
    }

    @Override
    @CacheHandle(type = CacheType.BASE_REQ, ns = CACHE_AOP_PAGE_MESSAGE, timeout = CACHE_DEFAULT_TIMEOUT,
            cacheResultType = CacheResultType.RESULT_PAGE, cacheResultClass = MessageVO.class)
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

        Integer totalRecord = jdbcTemplate.queryForObject(countSql + condSql, countParams, new OneIntMapper("totalRecord"));
        if (totalRecord <= 0) {
            page.setList(Collections.<MessageVO>emptyList());
        } else {
            List<MessageVO> list = jdbcTemplate.query(selectSql + condSql + selectSqlSuffix, selectParams.toArray(), new MessageVOMapper());
            encapNames(list);
            page.setList(list);
        }
        page.setTotalRecord(totalRecord);
        return ResultUtils.success(page);
    }

    @Override
    @CacheHandle(type = CacheType.BASE_REQ, ns = CACHE_AOP_ADMIN_PAGE_MESSAGE, timeout = CACHE_DEFAULT_TIMEOUT,
            cacheResultType = CacheResultType.RESULT_PAGE, cacheResultClass = MessageVO.class)
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

        Integer totalRecord = jdbcTemplate.queryForObject(countSql + condSql, countParams, new OneIntMapper("totalRecord"));
        if (totalRecord <= 0) {
            page.setList(Collections.<MessageVO>emptyList());
        } else {
            List<MessageVO> list = jdbcTemplate.query(selectSql + condSql + selectSqlSuffix, selectParams.toArray(), new MessageVOMapper());
            encapNames(list);
            page.setList(list);
        }
        page.setTotalRecord(totalRecord);
        return ResultUtils.success(page);
    }

    @Override
    @CacheEvictAll(ns = {CACHE_AOP_ADMIN_PAGE_MESSAGE, CACHE_AOP_PAGE_MESSAGE})
    public Result update(MessageSaveForm params) {
        String updateAt = DateUtils.format(new Date(), BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS);
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
    public Result unread() {
        String userId = WebContext.getStrAttrFromSession(BlogConstants.SESSION_USER_ID);
        String selectSql = " select type, sender_id, date_format(created_at, '%Y-%m-%d') as created_at, subject, content from message " +
                " where deleted = 0 and receiver_id = '" + userId + "' and consumed = 0 order by created_at limit 0, 5 ";
        String countSql = " select count(*) as totalRecord from message where deleted = 0 and receiver_id = '" + userId + "' and consumed = 0 ";
        List<MessageVO> msgToRead = jdbcTemplate.query(selectSql, new MessageVOMapper());
        String cnt = jdbcTemplate.queryForObject(countSql, new OneStringMapper("totalRecord"));
        if (Tools.isEmpty(msgToRead)) {
            return ResultUtils.success(new JSONObject().element("cnt", cnt)
                    .element("list", Collections.emptyList()));
        }

        String selectUserSql = " select id, user_name from user where id in ( %s ) ";
        List<StringStringPair> id2UserName = jdbcTemplate.query(String.format(selectUserSql, SqlUtils.wrapInSnippet(msgToRead)),
                new StringStringPairMapper("id", "user_name"));
        List<StringStringPair> list = new ArrayList<>(msgToRead.size());
        for (MessageVO vo : msgToRead) {
            StringStringPair pair = BizUtils.findByLogisticId(id2UserName, vo.getSenderId());
            String userName = (pair == null) ? "unknown" : pair.getRight();
            String left = LogPatternUtils.formatLogInfo(" [{}] [{}] at [{}] say ", vo.getType(), userName, vo.getCreatedAt());
            list.add(new StringStringPair(left, vo.getContent()));
        }

        JSONObject data = new JSONObject().element("cnt", cnt)
                .element("list", list);
        return ResultUtils.success(data);
    }

    @Override
    @CacheEvictAll(ns = {CACHE_AOP_ADMIN_PAGE_MESSAGE, CACHE_AOP_PAGE_MESSAGE})
    public Result markConsumed(BeanIdForm params) {
        String updateAt = DateUtils.format(new Date(), BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS);
        IQueryCriteria query = Criteria.eq("id", params.getId());
        IUpdateCriteria update = Criteria.set("consumed", "1").add("updated_at", updateAt);

        Result result = messageDao.update(query, update);
        if (!result.isSuccess()) {
            return result;
        }
        return ResultUtils.success(params.getId());
    }

    @Override
    @CacheEvictAll(ns = {CACHE_AOP_ADMIN_PAGE_MESSAGE, CACHE_AOP_PAGE_MESSAGE})
    public Result markAllConsumed() {
        String userId = WebContext.getStrAttrFromSession(BlogConstants.SESSION_USER_ID);
        String updateAt = DateUtils.format(new Date(), BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS);
        IQueryCriteria query = Criteria.eq("receiver_id", userId);
        IUpdateCriteria update = Criteria.set("consumed", "1").add("updated_at", updateAt);

        Result result = messageDao.update(query, update, true);
        if (!result.isSuccess()) {
            return result;
        }
        return ResultUtils.success();
    }

    @Override
    @CacheEvictAll(ns = {CACHE_AOP_ADMIN_PAGE_MESSAGE, CACHE_AOP_PAGE_MESSAGE})
    public Result remove(BeanIdForm params) {
        String updatedAt = DateUtils.format(new Date(), BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS);
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
        List<Map<String, Object>> id2Names = jdbcTemplate.query(String.format(selectUserNameSql, SqlUtils.wrapInSnippetForIds(userIds)), new ToMapMapper());
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

    /**
     * 给给定的用户列表 发送邮件
     *
     * @param userIds userIds
     * @param allMsg  allMsg
     * @return void
     * @author Jerry.X.He
     * @date 6/27/2017 7:28 PM
     * @since 1.0
     */
    private Result sendEmailNotify(Collection<String> userIds, List<MessagePO> allMsg) {
        String getEmailSqlTemplate = " select user_name, email from user where id in ( %s ) ";
        String getEmailSql = String.format(getEmailSqlTemplate, SqlUtils.wrapInSnippetForIds(userIds));
        List<StringStringPair> userName2Email = jdbcTemplate.query(getEmailSql,
                new StringStringPairMapper("user_name", "email"));
        List<String> to = new ArrayList<>(userName2Email.size());
        for (StringStringPair pair : userName2Email) {
            Result emailValidateResult = emailValidator.validate(pair.getRight(), null);
            if (emailValidateResult.isSuccess()) {
                to.add(pair.getRight());
            }
        }
        if (CollectionUtils.isAnyNull(to)) {
            return ResultUtils.success(" no receliver ! ");
        }

        MessagePO msg = allMsg.get(0);
        EmailPO email = new EmailPO(constantsContext.emailAuthUserName,
                to, (String) null, msg.getSubject(), msg.getContent(),
                EmailContentType.TEXT_HTML.msg());
        Result sendEmailResult = emailService.sendEmail(email);
        if (!sendEmailResult.isSuccess()) {
            return sendEmailResult;
        }

        return ResultUtils.success();
    }


}
