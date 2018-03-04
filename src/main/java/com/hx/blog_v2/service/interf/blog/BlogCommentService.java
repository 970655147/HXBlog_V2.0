package com.hx.blog_v2.service.interf.blog;

import com.hx.blog_v2.domain.form.blog.AdminCommentSearchForm;
import com.hx.blog_v2.domain.form.common.BeanIdForm;
import com.hx.blog_v2.domain.form.blog.CommentSaveForm;
import com.hx.blog_v2.domain.form.blog.FloorCommentListSearchForm;
import com.hx.blog_v2.domain.po.blog.BlogCommentPO;
import com.hx.blog_v2.domain.vo.blog.AdminCommentVO;
import com.hx.blog_v2.domain.vo.blog.CommentVO;
import com.hx.blog_v2.service.interf.BaseService;
import com.hx.common.interf.common.Page;
import com.hx.common.interf.common.Result;

import java.util.List;

/**
 * BlogCommentService
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 11:48 AM
 */
public interface BlogCommentService extends BaseService<BlogCommentPO> {

    /**
     * 增加一个 评论
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result add(CommentSaveForm params);

    /**
     * 搜索评论列表
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result list(BeanIdForm params, Page<List<CommentVO>> page);

    /**
     * 搜索符合的条件的评论列表
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result adminList(AdminCommentSearchForm params, Page<AdminCommentVO> page);

    /**
     * 搜索符合的条件的评论列表
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result floorCommentList(FloorCommentListSearchForm params, Page<CommentVO> page);

    /**
     * 更新给定的评论
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result update(CommentSaveForm params);

    /**
     * 移除给定的评论
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result remove(BeanIdForm params);

}
