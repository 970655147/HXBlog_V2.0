package com.hx.blog_v2.domain.common.adv;

import com.hx.common.interf.common.Code2Msg;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 广告类型
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 3/3/2018 7:31 PM
 */
public enum AdvType implements Code2Msg<String, String> {
    /**
     * 固定一个图片的广告
     */
    IMG_FIXED("img_fixed", "固定一个图片的广告"),
    /**
     * 固定文字的广告
     */
    TEXT_FIXED("text_fixed", "固定文字的广告"),
    /**
     * 点击之后重定向的图片广告
     */
    IMG_REDIRECT("img_redirect", "点击之后重定向的图片广告"),
    /**
     * 点击之后重定向的文字广告
     */
    TEXT_REDIRECT("text_redirect", "点击之后重定向的文字广告");

    /**
     * type -> imageType
     */
    public static final Map<String, AdvType> TYPE_2_CONTENT_TYPE = new LinkedHashMap<>();

    static {
        for (AdvType type : values()) {
            TYPE_2_CONTENT_TYPE.put(type.code, type);
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

    AdvType(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 获取 type 对应的 AdvType
     *
     * @param type type
     * @return com.hx.blog_v2.domain.dto.front_resources.ImageType
     * @author Jerry.X.He
     * @date 6/11/2017 2:36 PM
     * @since 1.0
     */
    public static AdvType of(String type) {
        return TYPE_2_CONTENT_TYPE.get(type);
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
