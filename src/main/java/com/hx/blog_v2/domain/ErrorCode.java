package com.hx.blog_v2.domain;

import com.hx.common.interf.common.Code2Msg;
import com.hx.common.interf.idx.IdxIterator;

/**
 * ErrorCode
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/15/2017 7:21 PM
 */
public enum ErrorCode implements Code2Msg<Integer, String> {

    /**
     * SUCCESS
     */
    SUCCESS(200, "success"),
    /**
     * NOT_LOGIN
     */
    NOT_LOGIN(201, " please login first ! "),
    /**
     * BE_OFFLINE
     */
    BE_OFFLINE(202, " you have been offline ! "),
    /**
     * BE_OFFLINE
     */
    NOT_AUTHORIZED(203, " have no privilege to visit this ! "),
    /**
     * TOKEN_NOT_MATCH
     */
    TOKEN_NOT_MATCH(203, " your token not match ! "),
    /**
     * NOT_FOUND
     */
    NOT_FOUND(404, "resource not found"),
    /**
     * SYSTEM_ERROR
     */
    SYSTEM_ERROR(500, "system internal error !"),

    /**
     * INPUT_NOT_FORMAT
     */
    INPUT_NOT_FORMAT(IdxGenerator.nextId(), "input not format !"),;


    /**
     * code -> msg
     */
    private Integer code;
    private String msg;

    ErrorCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public Integer code() {
        return code;
    }

    @Override
    public String msg() {
        return msg;
    }

    /**
     * 生成索引的工具
     *
     * @author Jerry.X.He <970655147@qq.com>
     * @version 1.0
     * @date 6/15/2017 7:25 PM
     */
    private static class IdxGenerator {

        /**
         * 生成索引的工具
         */
        static IdxIterator IDX_ITERATOR = new com.hx.log.idx.IdxGenerator(10000);

        /**
         * 获取下一个 id
         *
         * @return int
         * @author Jerry.X.He
         * @date 6/15/2017 7:29 PM
         * @since 1.0
         */
        static int nextId() {
            return IDX_ITERATOR.next();
        }

    }

}
