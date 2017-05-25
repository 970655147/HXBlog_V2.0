package com.hx.blog_v2.util;

import com.hx.log.util.Tools;

/**
 * sql 处理的相关工具
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/25/2017 8:37 PM
 */
public final class SqlUtils {

    // disable constructor
    private SqlUtils() {
        Tools.assert0("can't instantiate !");
    }


    /**
     * 封装模糊匹配的语句
     *
     * @param keywords keywords
     * @return java.lang.String
     * @author Jerry.X.He
     * @date 5/21/2017 7:54 PM
     * @since 1.0
     */
    public static String wrapWildcard(String keywords) {
        return "%" + keywords + "%";
    }


}
