package com.hx.blog_v2.biz_handler.handler;

import com.hx.blog_v2.biz_handler.handler.common.BizHandlerAdapter;
import com.hx.blog_v2.biz_handler.interf.BizContext;
import com.hx.blog_v2.domain.form.BlogSaveForm;
import com.hx.blog_v2.context.CacheContext;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.DateUtils;
import com.hx.common.interf.common.Result;
import com.hx.log.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * BlogSaveHandler
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/11/2017 12:30 AM
 */
@Component
public class BlogSaveHandler extends BizHandlerAdapter {

    @Autowired
    private CacheContext cacheContext;

    @Override
    public void afterHandle(BizContext context) {
        Result result = (Result) context.result();
        if (result.isSuccess()) {
            if ((context.args().length > 0) && (context.args()[0] instanceof BlogSaveForm)) {
                BlogSaveForm params = (BlogSaveForm) context.args()[0];
                // add
                if (Tools.isEmpty(params.getId())) {
                    cacheContext.todaysStatistics().incBlogCnt(1);
                    cacheContext.now5SecStatistics().incBlogCnt(1);

                    Date now = new Date();
                    String createdAtMonth = DateUtils.formate(now, BlogConstants.FORMAT_YYYY_MM);
                    cacheContext.updateBlogInMonthFacet(createdAtMonth, true);
                }
            }
        }
    }

}
