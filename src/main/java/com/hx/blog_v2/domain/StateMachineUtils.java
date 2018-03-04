package com.hx.blog_v2.domain;

import com.hx.blog_v2.domain.common.blog.BlogState;
import com.hx.blog_v2.domain.common.blog.BlogStateAction;
import com.hx.flow.flow.GenericStateMachine;
import com.hx.flow.flow.StandardStateMachine;
import com.hx.flow.flow.interf.StateMachine;
import com.hx.flow.flow.interf.TransferContext;
import com.hx.flow.flow.interf.TransferHandler;
import com.hx.log.util.Tools;

/**
 * StateMachineUtils
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/29/2017 7:36 PM
 */
public final class StateMachineUtils {

    // disable constructor
    private StateMachineUtils() {
        Tools.assert0("can't instantiate !");
    }

    /**
     * 处理博客状态扭转 的 stateMachine
     */
    public static StateMachine<BlogState, BlogStateAction> BLOG_STATE_MACHINE;

    static {
        StandardStateMachine.TransferMapBuilder<BlogState, BlogStateAction> builder =
                StandardStateMachine.TransferMapBuilder.<BlogState, BlogStateAction>start()
                        .add(BlogState.INIT, BlogStateAction.ADD_DRAFT, BlogState.DRAFT, new TransferBlogStateHandler())
                        .add(BlogState.INIT, BlogStateAction.PUBLISH, BlogState.AUDIT, new TransferBlogStateHandler())
                        .add(BlogState.DRAFT, BlogStateAction.SAVE_DRAFT, BlogState.DRAFT, new TransferBlogStateHandler())
                        .add(BlogState.DRAFT, BlogStateAction.PUBLISH, BlogState.AUDIT, new TransferBlogStateHandler())
                        .add(BlogState.AUDIT, BlogStateAction.ACCEPT, BlogState.SUCCESS, new TransferBlogStateHandler())
                        .add(BlogState.AUDIT, BlogStateAction.REJECT, BlogState.FAILED, new TransferBlogStateHandler())
                        .add(BlogState.SUCCESS, BlogStateAction.UPDATE, BlogState.AUDIT, new TransferBlogStateHandler())
                        .add(BlogState.FAILED, BlogStateAction.RE_PUBLISH, BlogState.AUDIT, new TransferBlogStateHandler())
                        .add(BlogState.FAILED, BlogStateAction.SAVE_DRAFT, BlogState.DRAFT, new TransferBlogStateHandler())
                        .add(BlogState.FAILED, BlogStateAction.UPDATE, BlogState.FAILED, new TransferBlogStateHandler());
        BLOG_STATE_MACHINE = new GenericStateMachine<>(BlogState.INIT, builder.build());
    }

    /**
     * TransferBlogStateHandler
     *
     * @author Jerry.X.He <970655147@qq.com>
     * @version 1.0
     * @date 6/29/2017 7:45 PM
     */
    private static class TransferBlogStateHandler implements TransferHandler<BlogState, BlogStateAction> {
        @Override
        public boolean handle(TransferContext<BlogState, BlogStateAction> context) throws Exception {
            return true;
        }
    }


}
