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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

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
            List<String> blogIdCandidate = new ArrayList<>();
            if((context.args().length > 0) && (context.args()[0] instanceof BeanIdForm)) {
                blogIdCandidate.add(((BeanIdForm) context.args()[0]).getId() );
            }
            Collections.addAll(blogIdCandidate, context.bizHandle().others());

            for(String blogId : blogIdCandidate) {
                BlogVisitLogForm params = new BlogVisitLogForm(blogId, BizUtils.getIp());
                BlogExPO blogEx = (BlogExPO) blogExDao.get(new BeanIdForm(blogId)).getData();

                blogEx.incViewCnt(1);
                params.setCreatedAtDay(DateUtils.formate(new Date(), BlogConstants.FORMAT_YYYY_MM_DD));
                BlogVisitLogPO po = visitLogDao.get(params);
                // 今天 没访问过?
                if (po == null) {
                    blogEx.incDayFlushViewCnt(1);
                    po = encapBlogVisitLog(params);

                    BlogVisitLogForm ipVisitLogParam = new BlogVisitLogForm(params.getBlogId(), params.getRequestIp());
                    ipVisitLogParam.setCreatedAtDay(null);
                    BlogVisitLogPO ipVisitLogPo = visitLogDao.get(ipVisitLogParam);
                    if (ipVisitLogPo == null) {
                        blogEx.incUniqueViewCnt(1);
                    }

                    visitLogDao.add(po);
                    cacheContext.putBlogVisitLog(params, po);
                    if (ipVisitLogPo == null) {
                        cacheContext.putBlogVisitLog(ipVisitLogParam, po);
                    }
                }

                visitLogDao.add(po);
                cacheContext.putBlogEx(blogEx);
            }
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
    private BlogVisitLogPO encapBlogVisitLog(BlogVisitLogForm params) {
        SessionUser user = (SessionUser) WebContext.getAttributeFromSession(BlogConstants.SESSION_USER);
        String userName = "unknown", email = "unknown";
        int isSystemUser = 0;
        if (user != null) {
            userName = user.getName();
            email = user.getEmail();
            isSystemUser = user.isSystemUser() ? 1 : 0;
        }

        BlogVisitLogPO po = new BlogVisitLogPO(params.getBlogId(), userName, email, isSystemUser, params.getRequestIp());
        return po;
    }

}
