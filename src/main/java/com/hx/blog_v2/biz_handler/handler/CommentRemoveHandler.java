package com.hx.blog_v2.biz_handler.handler;

import com.hx.blog_v2.biz_handler.handler.common.BizHandlerAdapter;
import com.hx.blog_v2.biz_handler.interf.BizContext;
import com.hx.blog_v2.context.CacheContext;
import com.hx.blog_v2.context.WebContext;
import com.hx.blog_v2.dao.interf.BlogExDao;
import com.hx.blog_v2.domain.form.BeanIdForm;
import com.hx.blog_v2.domain.po.BlogCommentPO;
import com.hx.blog_v2.domain.po.BlogExPO;
import com.hx.blog_v2.util.BlogConstants;
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
public class CommentRemoveHandler extends BizHandlerAdapter {

    @Autowired
    private BlogExDao blogExDao;
    @Autowired
    private CacheContext cacheContext;

    /**
     * 注 : 请在方法中 配置 BlogCommentPO 为 request:data
     *
     * @return
     * @author Jerry.X.He
     * @date 6/24/2017 6:03 PM
     * @since 1.0
     */
    @Override
    public void afterHandle(BizContext context) {
        Result result = (Result) context.result();
        if (result.isSuccess()) {
            BlogCommentPO po = (BlogCommentPO) WebContext.getAttributeFromRequest(BlogConstants.REQUEST_DATA);
            if ("1".equals(po.getCommentId())) {
                Result getExResult = blogExDao.get(new BeanIdForm(po.getBlogId()));
                if (!getExResult.isSuccess()) {
                    result.setExtra(getExResult);
                    return;
                }

                BlogExPO exPo = ((BlogExPO) getExResult.getData());
                exPo.incCommentCnt(-1);
                cacheContext.putBlogEx(exPo);
            }

            cacheContext.todaysStatistics().incCommentCnt(-1);
            cacheContext.now5SecStatistics().incCommentCnt(-1);
        }
    }

}
