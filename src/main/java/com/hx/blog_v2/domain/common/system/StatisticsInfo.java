package com.hx.blog_v2.domain.common.system;

/**
 * StatisticsInfo
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/10/2017 8:49 PM
 */
public class StatisticsInfo {

    private int dayFlushViewCnt;
    private int viewCnt;
    private int goodCnt;
    private int notGoodCnt;
    private int blogCnt;
    private int commentCnt;
    private int requestLogCnt;
    private int exceptionLogCnt;

    public StatisticsInfo() {
    }

    public int getDayFlushViewCnt() {
        return dayFlushViewCnt;
    }

    public void setDayFlushViewCnt(int dayFlushViewCnt) {
        this.dayFlushViewCnt = dayFlushViewCnt;
    }

    public int getViewCnt() {
        return viewCnt;
    }

    public void setViewCnt(int viewCnt) {
        this.viewCnt = viewCnt;
    }

    public int getGoodCnt() {
        return goodCnt;
    }

    public void setGoodCnt(int goodCnt) {
        this.goodCnt = goodCnt;
    }

    public int getNotGoodCnt() {
        return notGoodCnt;
    }

    public void setNotGoodCnt(int notGoodCnt) {
        this.notGoodCnt = notGoodCnt;
    }

    public int getBlogCnt() {
        return blogCnt;
    }

    public void setBlogCnt(int blogCnt) {
        this.blogCnt = blogCnt;
    }

    public int getCommentCnt() {
        return commentCnt;
    }

    public void setCommentCnt(int commentCnt) {
        this.commentCnt = commentCnt;
    }

    public int getRequestLogCnt() {
        return requestLogCnt;
    }

    public void setRequestLogCnt(int requestLogCnt) {
        this.requestLogCnt = requestLogCnt;
    }

    public int getExceptionLogCnt() {
        return exceptionLogCnt;
    }

    public void setExceptionLogCnt(int exceptionLogCnt) {
        this.exceptionLogCnt = exceptionLogCnt;
    }

    public void incDayFlushViewCnt(int delta) {
        this.dayFlushViewCnt += delta;
    }

    public void incViewCnt(int delta) {
        this.viewCnt += delta;
    }

    public void incGoodCnt(int delta) {
        this.goodCnt += delta;
    }

    public void incNotGoodCnt(int delta) {
        this.notGoodCnt += delta;
    }

    public void incBlogCnt(int delta) {
        this.blogCnt += delta;
    }

    public void incCommentCnt(int delta) {
        this.commentCnt += delta;
    }

    public void incRequestLogCnt(int delta) {
        this.requestLogCnt += delta;
    }

    public void incExceptionLogCnt(int delta) {
        this.exceptionLogCnt += delta;
    }

    /**
     * 将其他的另外一个 stats 归并到当前 status 中
     *
     * @param other other
     * @return void
     * @author Jerry.X.He
     * @date 6/18/2017 6:04 PM
     * @since 1.0
     */
    public void merge(StatisticsInfo other) {
        this.incDayFlushViewCnt(other.dayFlushViewCnt);
        this.incViewCnt(other.viewCnt);
        this.incGoodCnt(other.goodCnt);
        this.incNotGoodCnt(other.notGoodCnt);
        this.incBlogCnt(other.blogCnt);
        this.incCommentCnt(other.commentCnt);
        this.incRequestLogCnt(other.requestLogCnt);
        this.incExceptionLogCnt(other.exceptionLogCnt);
    }

}
