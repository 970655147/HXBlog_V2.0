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
public enum ConfigType {
    /**
     * 系统配置
     */
    SYSTEM("systemConfig"),
    /**
     * 首页配置
     */
    FRONT_INDEX_CONFIG("fIdxConfig");

    /**
     * type -> imageType
     */
    public static final Map<String, ConfigType> TYPE_2_CONFIG_TYPE = new LinkedHashMap<>();

    static {
        for (ConfigType type : values()) {
            TYPE_2_CONFIG_TYPE.put(type.type, type);
        }
    }


    /**
     * 类型的标志
     */
    private String type;

    ConfigType(String type) {
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
    public static ConfigType of(String type) {
        return TYPE_2_CONFIG_TYPE.get(type);
    }

}
