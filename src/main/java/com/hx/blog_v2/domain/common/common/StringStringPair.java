package com.hx.blog_v2.domain.common.common;

import com.hx.blog_v2.domain.po.interf.LogisticalId;

/**
 * StringStringPair
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/18/2017 7:38 PM
 */
public class StringStringPair extends Pair<String, String> implements LogisticalId<String> {

    public StringStringPair(String left, String right) {
        super(left, right);
    }

    public StringStringPair() {
    }

    @Override
    public String logisticalId() {
        return getLeft();
    }

}
