package com.hx.blog_v2.util;

import com.hx.log.collection.CollectionUtils;
import com.hx.log.util.Tools;
import org.jsoup.nodes.Element;

import java.util.List;

/**
 * ElementHandler
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 7/6/2017 8:38 PM
 */
public class CompositeElementHandler implements ElementHandler {

    /**
     * handler 列表
     */
    private List<ElementHandler> handlers;

    public CompositeElementHandler(List<ElementHandler> handlers) {
        Tools.assert0(handlers != null, "'handlers' can't be null ! ");
        Tools.assert0(! CollectionUtils.isAnyNull(handlers), "'handlers' can't be null ! ");
        this.handlers = handlers;
    }

    @Override
    public void handle(Element element) {
        for (ElementHandler handler : handlers) {
            handler.handle(element);
        }
    }
}
