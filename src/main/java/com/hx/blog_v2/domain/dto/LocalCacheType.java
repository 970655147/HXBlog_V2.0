package com.hx.blog_v2.domain.dto;

import com.hx.common.interf.common.Code2Msg;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 局部缓存类型
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 7/5/2017 8:22 PM
 */
public enum LocalCacheType implements Code2Msg<String, String> {

    /**
     * 博客额外信息的缓存
     */
    BLOG_EX("1", "blogEx"),
    /**
     * 博客访问记录的缓存
     */
    VISIT_LOG("2", "visitLog"),
    /**
     * 博客打分缓存
     */
    BLOG_SENSE("3", "blogSense"),
    /**
     * role -> resIds
     */
    ROLE_2_RES("4", "role2Res"),
    /**
     * res -> interf
     */
    RES_2_INTERF("5", "res2Interf"),
    /**
     * 上传文件缓存
     */
    UPLOAD_FILE("6", "uploadFile"),
    /**
     * 强制下线缓存
     */
    FORCE_OFF_LINE("7", "forceOffline"),
    /**
     * 黑名单缓存
     */
    BLACK_LIST("8", "blackList"),
    /**
     * 博客缓存
     */
    BLOG("9", "blog"),
    /**
     * blogId -> tagIds
     */
    BLOG_2_TAG_IDS("10", "blogId2TagIds"),
    /**
     * (blogId, pageNo) -> [comment ]
     */
    BLOG_ID_PAGE_NO_2_COMMENT("11", "blogIdPageNo2Comment");

    /**
     * type -> imageType
     */
    public static final Map<String, LocalCacheType> TYPE_2_CONFIG_TYPE = new LinkedHashMap<>();

    static {
        for (LocalCacheType type : values()) {
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

    LocalCacheType(String code, String msg) {
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
    public static LocalCacheType of(String type) {
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
