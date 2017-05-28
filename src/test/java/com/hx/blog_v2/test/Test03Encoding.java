package com.hx.blog_v2.test;

import com.hx.log.alogrithm.code.Codec;
import com.hx.log.util.Tools;
import org.junit.Test;

import static com.hx.log.util.Log.info;
import static com.hx.log.util.Log.infoHorizon;

/**
 * Test03Encoding
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/28/2017 9:59 AM
 */
public class Test03Encoding {

    @Test
    public void test01Encoding() throws Exception {

        String str = "生活有度";

        info(Codec.newString(str.getBytes(Tools.UTF_16), Tools.UTF_8));
        info(Codec.newString(str.getBytes(Tools.UTF_16), Tools.GBK));
        infoHorizon();
        info(Codec.newString(str.getBytes(), Tools.UTF_8));
        info(Codec.newString(str.getBytes(), Tools.GBK));

    }

}
