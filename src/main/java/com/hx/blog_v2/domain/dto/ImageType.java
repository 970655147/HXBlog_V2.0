package com.hx.blog_v2.domain.dto;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 图片类型
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/11/2017 2:30 PM
 */
public enum ImageType {
    /**
     * 头像图片
     */
    HEAD_IMG("headImg"),
    /**
     * 相册图片
     */
    IMAGE_SHOW("imgShow");

    /**
     * type -> imageType
     */
    public static final Map<String, ImageType> TYPE_2_IMAGE_TYPE = new LinkedHashMap<>();

    static {
        for (ImageType type : values()) {
            TYPE_2_IMAGE_TYPE.put(type.type, type);
        }
    }


    /**
     * 图片的类型的标志
     */
    private String type;

    ImageType(String type) {
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
    public static ImageType of(String type) {
        return TYPE_2_IMAGE_TYPE.get(type);
    }

}
