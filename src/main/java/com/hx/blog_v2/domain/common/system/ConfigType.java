package com.hx.blog_v2.domain.common.system;

import com.hx.common.interf.common.Code2Msg;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 系统配置类型
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/11/2017 2:30 PM
 */
public enum ConfigType implements Code2Msg<String, String> {
    /**
     * 系统配置
     */
    SYSTEM("1", "systemConfig"),
    /**
     * 规则配置
     */
    RULE("2", "ruleConfig"),
    /**
     * 首页配置
     */
    FRONT_INDEX_CONFIG("3", "frontendIdxConfig");

    /**
     * type -> imageType
     */
    public static final Map<String, ConfigType> TYPE_2_CONFIG_TYPE = new LinkedHashMap<>();

    static {
        for (ConfigType type : values()) {
            TYPE_2_CONFIG_TYPE.put(type.code, type);
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

    ConfigType(String code, String msg) {
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
    public static ConfigType of(String type) {
        return TYPE_2_CONFIG_TYPE.get(type);
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
