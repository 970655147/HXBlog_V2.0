package com.hx.blog_v2.test;

import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.DateUtils;

import java.util.Date;

import static com.hx.log.util.Log.info;

/**
 * dateFormat
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/3/2017 11:19 AM
 */
public class Test04DateFormat {

    public static void main(String[] args) {

        Date now = new Date();

        info(DateUtils.formate(now, BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS));

    }

}
