package com.hx.blog_v2.domain.dto;

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

}
