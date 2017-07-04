package com.hx.blog_v2.test;

import org.junit.Test;

import static com.hx.log.util.Log.err;

/**
 * Test06Connection
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/21/2017 7:40 PM
 */
public class Test07IpCompare {

    @Test
    public void test01ipCompare() {

        for (int i = 0; i < 300; i++) {
            String ipPart = String.valueOf(i);
            boolean lt0 = "000".compareTo(ipPart) > 0;
            boolean gt255 = "255".compareTo(ipPart) < 0;
            if (lt0 || gt255) {
                err(ipPart);
            }
        }

    }


}
