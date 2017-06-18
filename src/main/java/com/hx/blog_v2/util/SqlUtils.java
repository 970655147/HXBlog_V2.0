package com.hx.blog_v2.util;

import com.hx.blog_v2.domain.po.interf.LogisticalId;
import com.hx.json.JSONArray;
import com.hx.log.util.Tools;

import java.util.Collection;

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

    /**
     * 封装 in 查询的 in代码片
     *
     * @param col col
     * @return java.lang.String
     * @author Jerry.X.He
     * @date 6/5/2017 7:32 PM
     * @since 1.0
     */
    public static <T> String wrapInSnippetForIds(Collection<T> col) {
        JSONArray arr = JSONArray.fromObject(col);
        String result = arr.toString();
        return result.substring(result.indexOf("[")+1, result.lastIndexOf("]"));
    }

    public static <T extends LogisticalId<IDType>, IDType> String wrapInSnippet(Collection<T> col) {
        JSONArray arr = new JSONArray();
        for(T ele : col) {
            arr.add(ele.logisticalId());
        }
        String result = arr.toString();
        return result.substring(result.indexOf("[")+1, result.lastIndexOf("]"));
    }


}
