package com.hx.blog_v2.biz_handler.handler.common;

import com.hx.blog_v2.biz_handler.interf.BizContext;
import com.hx.blog_v2.biz_handler.interf.BizHandler;
import com.hx.log.util.Tools;

import java.util.List;

/**
 * BizHandlerAdapter
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/9/2017 7:18 PM
 */
public class BizHandlerChain implements BizHandler {

    private List<BizHandler> chain;

    public BizHandlerChain(List<BizHandler> chain) {
        Tools.assert0(chain != null, "'chain' can't be null !");
        this.chain = chain;
    }

    public void addBizHandler(BizHandler handler) {
        chain.add(handler);
    }

    @Override
    public void afterInvalid(BizContext context) {
        for(BizHandler handler : chain) {
            handler.afterInvalid(context);
        }
    }

    @Override
    public void beforeHandle(BizContext context) {
        for(BizHandler handler : chain) {
            handler.beforeHandle(context);
        }
    }

    @Override
    public void afterHandle(BizContext context) {
        for(BizHandler handler : chain) {
            handler.afterHandle(context);
        }
    }

    @Override
    public void afterException(BizContext context) {
        for(BizHandler handler : chain) {
            handler.afterException(context);
        }
    }
}
