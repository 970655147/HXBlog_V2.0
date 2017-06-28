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
public enum CorrectionType implements Code2Msg<String, String> {
    /**
     * 评论数量校正
     */
    COMMENT_CNT("1", "commentCnt"),
    /**
     * 评分数量校正
     */
    SCORE_CNT("2", "scoreCnt");

    /**
     * type -> imageType
     */
    public static final Map<String, CorrectionType> TYPE_2_CORRECTION_TYPE = new LinkedHashMap<>();

    static {
        for (CorrectionType type : values()) {
            TYPE_2_CORRECTION_TYPE.put(type.code, type);
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

    CorrectionType(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 获取 code 对应的 CorrectionType
     *
     * @param code code
     * @return com.hx.blog_v2.domain.dto.ImageType
     * @author Jerry.X.He
     * @date 6/11/2017 2:36 PM
     * @since 1.0
     */
    public static CorrectionType of(String code) {
        return TYPE_2_CORRECTION_TYPE.get(code);
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
