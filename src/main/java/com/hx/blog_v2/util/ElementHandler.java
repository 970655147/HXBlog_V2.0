package com.hx.blog_v2.util;

import org.jsoup.nodes.Element;

/**
 * ElementHandler
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 7/6/2017 8:38 PM
 */
public interface ElementHandler {

    /**
     * 处理给定的元素节点
     *
     * @param element element
     * @return
     * @author Jerry.X.He
     * @date 7/6/2017 8:38 PM
     * @since 1.0
     */
    void handle(Element element);

}
