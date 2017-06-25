package com.hx.blog_v2.biz_handler.handler;

import com.hx.blog_v2.biz_handler.handler.common.BizHandlerAdapter;
import com.hx.blog_v2.biz_handler.interf.BizContext;
import com.hx.blog_v2.context.CacheContext;
import com.hx.blog_v2.context.ConstantsContext;
import com.hx.blog_v2.dao.interf.BlogExDao;
import com.hx.blog_v2.domain.form.BeanIdForm;
import com.hx.blog_v2.domain.form.CommentSaveForm;
import com.hx.blog_v2.domain.po.BlogExPO;
import com.hx.blog_v2.util.BizUtils;
import com.hx.common.interf.common.Result;
import com.hx.log.util.Tools;
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
public class CommentAddHandler extends BizHandlerAdapter {

    @Autowired
    private BlogExDao blogExDao;
    @Autowired
    private CacheContext cacheContext;
    @Autowired
    private ConstantsContext constantsContext;

    @Override
    public void afterHandle(BizContext context) {
        Result result = (Result) context.result();
        if (result.isSuccess()) {
            CommentSaveForm params = (CommentSaveForm) context.args()[0];
            int endOfReply = BizUtils.idxOfEndRe(params.getComment(),
                    constantsContext.replyCommentPrefix, constantsContext.replyCommentSuffix);
            boolean isReply = ((!Tools.isEmpty(params.getFloorId())) && (endOfReply >= 0));
            if (!isReply) {
                Result getExResult = blogExDao.get(new BeanIdForm(params.getBlogId()));
                if (!getExResult.isSuccess()) {
                    result.setExtra(getExResult);
                    return;
                }
                BlogExPO exPo = ((BlogExPO) getExResult.getData());
                exPo.incCommentCnt(1);
                cacheContext.putBlogEx(exPo);
            }

            cacheContext.todaysStatistics().incCommentCnt(1);
            cacheContext.now5SecStatistics().incCommentCnt(1);
        }
    }

}
