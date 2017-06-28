package com.hx.blog_v2.domain.dto;

import com.hx.common.interf.common.Code2Msg;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 图片类型
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/11/2017 2:30 PM
 */
public enum ImageType implements Code2Msg<String, String> {
    /**
     * 头像图片
     */
    HEAD_IMG("1", "headImg"),
    /**
     * 相册图片
     */
    IMAGE_SHOW("2", "imgShow");

    /**
     * type -> imageType
     */
    public static final Map<String, ImageType> TYPE_2_IMAGE_TYPE = new LinkedHashMap<>();

    static {
        for (ImageType type : values()) {
            TYPE_2_IMAGE_TYPE.put(type.code, type);
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

    ImageType(String code, String msg) {
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
    public static ImageType of(String type) {
        return TYPE_2_IMAGE_TYPE.get(type);
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
