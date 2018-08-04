package com.hx.blog_v2.domain;

import com.hx.json.JSONObject;

import java.io.Serializable;

/**
 * BaseVO
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 8/4/2018 12:02 PM
 */
public class BaseVO implements Serializable {

    @Override
    public String toString() {
        return JSONObject.fromObject(this).toString();
    }

}
