package com.hx.blog_v2.domain.dto;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 消息类型
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/11/2017 2:30 PM
 */
public enum MessageType {
    /**
     * 系统消息
     */
    SYSTEM("system"),
    /**
     * 站内消息
     */
    SITE_INTERNAL("siteInternal");

    /**
     * type -> imageType
     */
    public static final Map<String, MessageType> TYPE_2_MSG_TYPE = new LinkedHashMap<>();

    static {
        for (MessageType type : values()) {
            TYPE_2_MSG_TYPE.put(type.type, type);
        }
    }


    /**
     * 类型的标志
     */
    private String type;

    MessageType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
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
    public static MessageType of(String type) {
        return TYPE_2_MSG_TYPE.get(type);
    }

}
