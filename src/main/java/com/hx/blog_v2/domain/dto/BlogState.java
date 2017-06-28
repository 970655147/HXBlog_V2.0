package com.hx.blog_v2.domain.dto;

import com.hx.common.interf.common.Code2Msg;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 消息类型
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/11/2017 2:30 PM
 */
public enum BlogState implements Code2Msg<String, String> {
    /**
     * 草稿箱
     */
    DRAFT("10", "draft"),
    /**
     * 审核中
     */
    AUDIT("20", "audit"),
    /**
     * 发表成功
     */
    SUCCESS("30", "success"),
    /**
     * 发表失败
     */
    FAILED("40", "failed");

    /**
     * type -> imageType
     */
    public static final Map<String, BlogState> TYPE_2_STATE = new LinkedHashMap<>();

    static {
        for (BlogState type : values()) {
            TYPE_2_STATE.put(type.code, type);
        }
    }


    /**
     * 类型的标志
     */
    private String code;
    /**
     * 类型的名称
     */
    private String msg;

    BlogState(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 获取 type 对应的 ImageType
     *
     * @param type type
     * @return com.hx.blog_v2.domain.dto.ImageType
     * @author Jerry.X.He
     * @date 6/11/2017 2:36 PM
     * @since 1.0
     */
    public static BlogState of(String type) {
        return TYPE_2_STATE.get(type);
    }


    @Override
    public String code() {
        return code;
    }

    @Override
    public String msg() {
        return msg;
    }
}
