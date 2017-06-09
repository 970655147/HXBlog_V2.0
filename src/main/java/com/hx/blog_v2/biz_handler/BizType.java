package com.hx.blog_v2.biz_handler;

/**
 * 相关的 BizType
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/8/2017 8:54 PM
 */
public enum BizType {

    /**
     * 可以异步处理的业务
     */
    ADD_VIEW_CNT(Type.ASYNC, "addViewCnt"),
    /**
     * 减小代码耦合的业务
     */
    DECOUPLE(Type.DECOUPLE, ""),
    /**
     * 其他业务
     */
    OTHER(Type.OTHER, "other"),
    ;

    /**
     * 当前业务的类型
     */
    private Type type;
    /**
     * 当前业务的id
     */
    private String key;

    BizType(Type type, String key) {
        this.type = type;
        this.key = key;
    }


    /**
     * 业务的类型
     *
     * @author Jerry.X.He <970655147@qq.com>
     * @version 1.0
     * @date 6/8/2017 8:58 PM
     */
    public enum Type {
        ASYNC, DECOUPLE, OTHER
    }
}
