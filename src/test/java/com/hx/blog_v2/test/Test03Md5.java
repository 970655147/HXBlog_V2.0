package com.hx.blog_v2.test;


import com.hx.blog_v2.service.UserServiceImpl;
import com.hx.blog_v2.service.interf.UserService;
import com.hx.log.alogrithm.code.Codec;

import static com.hx.log.util.Log.info;

/**
 * Test03Md5
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/3/2017 12:15 AM
 */
public class Test03Md5 {

    public static void main(String[] args) {

        String pwd = "123456";
        String encoded = Codec.byte2Hex(Codec.md5(Codec.md5(pwd.getBytes())));

        info(encoded);

        String newPwd = encodePwd("e10adc3949ba59abbe56e057f20f883e", "11111111");
        info(newPwd);

        String str = "D1A7B2963654A31104D7E41BC2A61C27";
        info(Codec.byte2Hex(Codec.md5(str.getBytes())));

    }

    private static String encodePwd(String pwd, String salt) {
        String desEncoded = Codec.byte2Hex(Codec.desE(pwd.getBytes(), salt.getBytes()));
        String md5Encoded = Codec.byte2Hex(Codec.md5(desEncoded.getBytes()));
        return md5Encoded;
    }

}
