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
public enum CorrectionType {
    /**
     * 评论数量校正
     */
    COMMENT_CNT("commentCnt");

    /**
     * type -> imageType
     */
    public static final Map<String, CorrectionType> TYPE_2_CORRECTION_TYPE = new LinkedHashMap<>();

    static {
        for (CorrectionType type : values()) {
            TYPE_2_CORRECTION_TYPE.put(type.type, type);
        }
    }


    /**
     * 类型的标志
     */
    private String type;

    CorrectionType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    /**
     * 获取 type 对应的 CorrectionType
     *
     * @param type type
     * @return com.hx.blog_v2.domain.dto.ImageType
     * @author Jerry.X.He
     * @date 6/11/2017 2:36 PM
     * @since 1.0
     */
    public static CorrectionType of(String type) {
        return TYPE_2_CORRECTION_TYPE.get(type);
    }

}
