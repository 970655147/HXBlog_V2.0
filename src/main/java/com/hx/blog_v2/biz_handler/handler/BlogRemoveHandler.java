package com.hx.blog_v2.biz_handler.handler;

import com.hx.blog_v2.biz_handler.handler.common.BizHandlerAdapter;
import com.hx.blog_v2.biz_handler.interf.BizContext;
import com.hx.blog_v2.context.CacheContext;
import com.hx.blog_v2.context.ConstantsContext;
import com.hx.blog_v2.context.WebContext;
import com.hx.blog_v2.dao.interf.BlogDao;
import com.hx.blog_v2.domain.dto.MessageType;
import com.hx.blog_v2.domain.dto.SessionUser;
import com.hx.blog_v2.domain.form.BeanIdForm;
import com.hx.blog_v2.domain.form.BlogSaveForm;
import com.hx.blog_v2.domain.form.MessageSaveForm;
import com.hx.blog_v2.domain.po.BlogPO;
import com.hx.blog_v2.domain.po.RolePO;
import com.hx.blog_v2.service.interf.MessageService;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.DateUtils;
import com.hx.common.interf.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

/**
 * BlogSaveHandler
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/11/2017 12:30 AM
 */
@Component
public class BlogRemoveHandler extends BizHandlerAdapter {

    @Autowired
    private BlogDao blogDao;
    @Autowired
    private MessageService messageService;
    @Autowired
    private CacheContext cacheContext;
    @Autowired
    private BlogConstants constants;
    @Autowired
    private ConstantsContext constantsContext;
    /**
     * 影响到统计5s的数据的最大统计偏移
     */
    private long now5SecStatsMaxTsOff = -1;
    /**
     * 一天的毫秒数
     */
    private long oneDayTsOff = ((24 * 60 * 60) * 1000);
    /**
     * 影响到统计最近7天的数据的最大统计偏移
     */
    private long recentlyStatsMaxTsOff = -1;
    /**
     * 配置上一次刷新的时间
     */
    private long lastRefreshTs = -1;

    @Override
    public void afterHandle(BizContext context) {
        initIfNeed();

        Result result = (Result) context.result();
        if (result.isSuccess()) {
            BeanIdForm params = (BeanIdForm) context.args()[0];
            Result getBlogResult = blogDao.get(params);
            if (!getBlogResult.isSuccess()) {
                result.setExtra(getBlogResult);
                return;
            }

            BlogPO po = (BlogPO) getBlogResult.getData();
            Date now = new Date();
            Date createdAt = DateUtils.parse(po.getCreatedAt(), BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS);
            long offsetFromNow = now.getTime() - createdAt.getTime();
            long offsetByDay = DateUtils.beginOfDay(now).getTime() - DateUtils.beginOfDay(createdAt).getTime();

            if (offsetFromNow < now5SecStatsMaxTsOff) {
                cacheContext.now5SecStatistics().incBlogCnt(-1);
                cacheContext.todaysStatistics().incBlogCnt(-1);
            } else if (Objects.equals(DateUtils.formate(now, BlogConstants.FORMAT_YYYY_MM_DD),
                    DateUtils.formate(createdAt, BlogConstants.FORMAT_YYYY_MM_DD))) {
                cacheContext.todaysStatistics().incBlogCnt(-1);
            } else if (offsetByDay < recentlyStatsMaxTsOff) {
                int offFromNow = (int) (offsetByDay / oneDayTsOff);
                cacheContext.recentlyStatistics().get(cacheContext.recentlyStatistics().size() - 1 - offFromNow).incBlogCnt(-1);
                cacheContext.sumStatistics().incBlogCnt(-1);
            } else {
                cacheContext.sumStatistics().incBlogCnt(-1);
            }
            cacheContext.updateBlogInMonthFacet(po.getCreatedAtMonth(), false);

            RolePO role = cacheContext.roleByName(constants.roleAdmin);
            if (role != null) {
                Result sendEmailResult = sendMessage(role, result, po);
                if (!sendEmailResult.isSuccess()) {
                    // ignore
                }
            }
        }
    }

    /**
     * 如果有必要的话, 初始化
     *
     * @return void
     * @author Jerry.X.He
     * @date 6/24/2017 4:15 PM
     * @since 1.0
     */
    private void initIfNeed() {
        if ((now5SecStatsMaxTsOff < 0) || (lastRefreshTs < constantsContext.systemConfigLastRefreshTs())) {
            lastRefreshTs = constantsContext.systemConfigLastRefreshTs();
            now5SecStatsMaxTsOff = ((constantsContext.maxRealTimeCacheStasticsTimes
                    * constantsContext.realTimeChartTimeInterval) * 1000);
            recentlyStatsMaxTsOff = ((constantsContext.maxCacheStatisticsDays - 1) * oneDayTsOff);
        }
    }

    /**
     * 向给定的 角色的用户发送邮件
     *
     * @param role role
     * @return void
     * @author Jerry.X.He
     * @date 6/27/2017 8:16 PM
     * @since 1.0
     */
    private Result sendMessage(RolePO role, Result result, BlogPO po) {
        SessionUser user = (SessionUser) WebContext.getAttributeFromSession(BlogConstants.SESSION_USER);
        MessageSaveForm msgForm = new MessageSaveForm();

        msgForm.setSenderId(constantsContext.contextSystemUserId);
        msgForm.setRoleIds(role.getId());
        msgForm.setType(MessageType.SYSTEM.code());
        msgForm.setSubject("[HXBlog]博客提醒");
        msgForm.setContent(" 用户 [" + user.getName() + "] 移除了一篇博客 : " +
                " 原url : <a href='" + constantsContext.contextUrlPrefix + "static/main/blogDetail.html?id=" + result.getData() + "'" +
                " color='red' > " +
                po.getTitle() + "</a>, 请知晓 ! ");
        return messageService.add(msgForm);
    }

}
