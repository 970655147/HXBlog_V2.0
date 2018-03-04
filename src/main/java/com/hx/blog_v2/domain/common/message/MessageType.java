package com.hx.blog_v2.domain.common.message;

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
public enum MessageType implements Code2Msg<String, String> {
    /**
     * 系统消息
     */
    SYSTEM("1", "system"),
    /**
     * 站内消息
     */
    SITE_INTERNAL("2", "siteInternal");

    /**
     * type -> imageType
     */
    public static final Map<String, MessageType> TYPE_2_MSG_TYPE = new LinkedHashMap<>();

    static {
        for (MessageType type : values()) {
            TYPE_2_MSG_TYPE.put(type.code, type);
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

    MessageType(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 获取 type 对应的 ImageType
     *
     * @param type type
     * @return com.hx.blog_v2.domain.dto.front_resources.ImageType
     * @author Jerry.X.He
     * @date 6/11/2017 2:36 PM
     * @since 1.0
     */
    public static MessageType of(String type) {
        return TYPE_2_MSG_TYPE.get(type);
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
