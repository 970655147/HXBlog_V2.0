package com.hx.blog_v2.domain.common.common;

import com.hx.blog_v2.domain.po.interf.LogisticalId;

/**
 * StringIntPair
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/18/2017 7:38 PM
 */
public class StringIntPair extends Pair<String, Integer> implements LogisticalId<String> {

    public StringIntPair(String left, Integer right) {
        super(left, right);
    }

    public StringIntPair() {
    }

    @Override
    public String logisticalId() {
        return getLeft();
    }

}
