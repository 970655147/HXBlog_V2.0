package com.hx.blog_v2.biz_handler.handler;

import com.hx.blog_v2.biz_handler.handler.common.BizHandlerAdapter;
import com.hx.blog_v2.biz_handler.interf.BizContext;
import com.hx.blog_v2.domain.form.BlogSenseForm;
import com.hx.blog_v2.context.CacheContext;
import com.hx.common.interf.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * BlogSaveHandler
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/11/2017 12:30 AM
 */
@Component
public class BlogSenseHandler extends BizHandlerAdapter {

    @Autowired
    private CacheContext cacheContext;

    @Override
    public void afterHandle(BizContext context) {
        Result result = (Result) context.result();
        if(result.isSuccess()) {
            if ((context.args().length > 0) && (context.args()[0] instanceof BlogSenseForm)) {
                BlogSenseForm params = (BlogSenseForm) context.args()[0];
                if (params.getScore() > 3) {
                    cacheContext.todaysStatistics().incGoodCnt(1);
                    cacheContext.now5SecStatistics().incGoodCnt(1);
//                    cacheContext.todaysStatistics().incNotGoodCnt(-1);
                } else {
//                    cacheContext.todaysStatistics().incGoodCnt(-1);
                    cacheContext.todaysStatistics().incNotGoodCnt(1);
                    cacheContext.now5SecStatistics().incNotGoodCnt(1);
                }
            }
        }
    }

}
