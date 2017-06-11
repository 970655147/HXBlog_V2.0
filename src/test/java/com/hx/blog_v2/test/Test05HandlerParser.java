package com.hx.blog_v2.test;

import com.hx.attr_handler.attr_handler.interf.AttrHandler;
import com.hx.attr_handler.util.AttrHandlerConstants;
import com.hx.attr_handler.util.AttrHandlerUtils;

import static com.hx.log.util.Log.info;

/**
 * Test05HandlerParser
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/10/2017 8:04 PM
 */
public class Test05HandlerParser {

    public static void main(String[] args) {

        String handlerStr = "map('http://ip.taobao.com/service/getIpInfo.php?ip=' + $this)";
//        String handlerStr = "map(length>15?(subString(0,12)+'...'):$this ) ";

        AttrHandler handler = AttrHandlerUtils.handlerParse(handlerStr, AttrHandlerConstants.HANDLER);
        info(handler.handle("xxx"));

    }

}
