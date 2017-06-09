package com.hx.blog_v2.biz_handler.handler;

import com.hx.blog_v2.biz_handler.interf.BizContext;
import com.hx.blog_v2.dao.interf.BlogExDao;
import com.hx.blog_v2.dao.interf.BlogVisitLogDao;
import com.hx.blog_v2.domain.dto.SessionUser;
import com.hx.blog_v2.domain.form.BeanIdForm;
import com.hx.blog_v2.domain.form.BlogVisitLogForm;
import com.hx.blog_v2.domain.po.BlogExPO;
import com.hx.blog_v2.domain.po.BlogVisitLogPO;
import com.hx.blog_v2.util.*;
import com.hx.common.interf.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 记录博客访问日志的 handler
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/9/2017 9:15 PM
 */
@Component
public class BlogVisitLogHandler extends BizHandlerAdapter {

    @Autowired
    private BlogVisitLogDao visitLogDao;
    @Autowired
    private BlogExDao blogExDao;
    @Autowired
    private CacheContext cacheContext;


    @Override
    public void afterHandle(BizContext context) {
        Result result = (Result) context.result();
        if (result.isSuccess()) {
            String blogId = ((BeanIdForm) context.args()[0]).getId();
            BlogVisitLogForm params = new BlogVisitLogForm(blogId, BizUtils.getIp());
            BlogExPO blogEx = blogExDao.get(new BeanIdForm(params.getBlogId()));

            blogEx.incViewCnt(1);
            params.setCreatedAtDay(null);
            BlogVisitLogPO po = visitLogDao.get(params);
            if (po == null) {
                blogEx.incUniqueViewCnt(1);
                blogEx.incDayFlushViewCnt(1);
            } else {
                params.setCreatedAtDay(DateUtils.formate(new Date(), BlogConstants.FORMAT_YYYY_MM_DD));
                po = visitLogDao.get(params);
                if (po == null) {
                    blogEx.incDayFlushViewCnt(1);
                }
            }

            po = addBlogVisitLog(params);
            cacheContext.putBlogEx(blogEx);
        }
    }


    // -------------------- 辅助方法 --------------------------

    /**
     * 创建一个 BlogVisitLogPO 并持久化
     *
     * @param params params
     * @return com.hx.blog_v2.domain.po.BlogVisitLogPO
     * @author Jerry.X.He
     * @date 6/9/2017 9:34 PM
     * @since 1.0
     */
    private BlogVisitLogPO addBlogVisitLog(BlogVisitLogForm params) {
        SessionUser user = (SessionUser) WebContext.getAttributeFromSession(BlogConstants.SESSION_USER);
        String userName = "unknown", email = "unknown";
        int isSystemUser = 0;
        if (user != null) {
            userName = user.getName();
            email = user.getEmail();
            isSystemUser = user.isSystemUser() ? 1 : 0;
        }

        BlogVisitLogPO po = new BlogVisitLogPO(params.getBlogId(), userName, email, isSystemUser, params.getRequestIp());
        visitLogDao.add(po);
        return po;
    }

}
