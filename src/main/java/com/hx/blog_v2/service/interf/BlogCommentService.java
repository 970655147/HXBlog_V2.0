package com.hx.blog_v2.service.interf;

import com.hx.blog_v2.domain.form.AdminCommentSearchForm;
import com.hx.blog_v2.domain.form.BeanIdForm;
import com.hx.blog_v2.domain.form.CommentSaveForm;
import com.hx.blog_v2.domain.form.FloorCommentListSearchForm;
import com.hx.blog_v2.domain.po.BlogCommentPO;
import com.hx.blog_v2.domain.vo.AdminCommentVO;
import com.hx.blog_v2.domain.vo.CommentVO;
import com.hx.common.interf.common.Page;
import com.hx.common.interf.common.Result;

/**
 * BlogService
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 11:48 AM
 */
public interface BlogCommentService extends BaseService<BlogCommentPO> {

    /**
     * 增加一个 心情
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result add(CommentSaveForm params);

    /**
     * 搜索符合的条件的心情列表
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result adminList(AdminCommentSearchForm params, Page<AdminCommentVO> page);

    /**
     * 搜索符合的条件的心情列表
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result floorCommentList(FloorCommentListSearchForm params, Page<CommentVO> page);

    /**
     * 更新给定的心情
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result update(CommentSaveForm params);

    /**
     * 移除给定的心情
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/20/2017 6:21 PM
     * @since 1.0
     */
    Result remove(BeanIdForm params);

}
