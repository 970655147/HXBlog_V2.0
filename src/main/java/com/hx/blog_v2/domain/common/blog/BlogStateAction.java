package com.hx.blog_v2.domain.common.blog;

import com.hx.flow.flow.interf.Action;

import java.util.HashMap;
import java.util.Map;

/**
 * 博客状态切换的 action
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/29/2017 7:32 PM
 */
public enum BlogStateAction implements Action<BlogStateAction> {

    /**
     * 增加草稿
     */
    ADD_DRAFT("addDraft"),
    /**
     * 保存草稿
     */
    SAVE_DRAFT("saveDraft"),
    /**
     * 更新博客
     */
    UPDATE("update"),
    /**
     * 发布博客
     */
    PUBLISH("publish"),
    /**
     * 重新发布博客
     */
    RE_PUBLISH("rePublish"),
    /**
     * 同意文章
     */
    ACCEPT("accept"),
    /**
     * 拒绝草稿
     */
    REJECT("reject");

    private static Map<String, BlogStateAction> ID_2_ACTION = new HashMap<>();

    static {
        for (BlogStateAction state : values()) {
            ID_2_ACTION.put(state.id, state);
        }
    }

    private String id;

    BlogStateAction(String id) {
        this.id = id;
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public BlogStateAction create(String s, Object o) {
        return idOf(s);
    }

    @Override
    public BlogStateAction idOf(String s) {
        return ID_2_ACTION.get(s);
    }

    @Override
    public BlogStateAction action() {
        return this;
    }

    @Override
    public Object extra() {
        return null;
    }
}
