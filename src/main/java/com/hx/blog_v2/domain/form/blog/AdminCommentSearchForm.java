package com.hx.blog_v2.domain.form.blog;

import com.hx.blog_v2.domain.BasePageForm;
import com.hx.blog_v2.util.CacheConstants;
import com.hx.log.str.StringUtils;
import com.hx.log.util.Tools;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 博客管理的搜索
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 9:42 AM
 */
public class AdminCommentSearchForm extends BasePageForm {

    /**
     * 根据博客排序
     */
    public static final int SORT_BLOG = 1;
    /**
     * 根据评论时间
     */
    public static final int SORT_CREATE = SORT_BLOG + 1;
    /**
     * 所有的支持的 类型
     */
    public static final int[] SORT_TYPES = new int[]{SORT_BLOG, SORT_CREATE};

    /**
     * 排序的val -> sql排序的关键字
     */
    public static final Map<Integer, String> SORT_2_FIELD = Tools.asMap(
            new Integer[]{SORT_BLOG, SORT_CREATE},
            "b.created_at", "c.created_at"
    );

    /**
     * blogName, userName, toUserName, content 的模糊匹配
     * 其他的是 各自的单独匹配, 如果 后者配置了, 则keyWords关键字不生效
     */
    private String keywords;
    private String blogName;
    private String userName;
    private String toUserName;
    private String content;

    /**
     * 精确匹配
     */
    private String blogId;
    private String blogTypeId;
    private String blogTagId;

    /**
     * 排序的类型
     */
    private int sort;
    /**
     * 排序 正序, 还是倒序
     */
    private boolean asc;

    public AdminCommentSearchForm() {
        sort = SORT_BLOG;
        asc = true;
    }

    public static int getSortBlog() {
        return SORT_BLOG;
    }

    public static int getSortCreate() {
        return SORT_CREATE;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getBlogName() {
        return blogName;
    }

    public void setBlogName(String blogName) {
        this.blogName = blogName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBlogId() {
        return blogId;
    }

    public void setBlogId(String blogId) {
        this.blogId = blogId;
    }

    public String getBlogTypeId() {
        return blogTypeId;
    }

    public void setBlogTypeId(String blogTypeId) {
        this.blogTypeId = blogTypeId;
    }

    public String getBlogTagId() {
        return blogTagId;
    }

    public void setBlogTagId(String blogTagId) {
        this.blogTagId = blogTagId;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public boolean isAsc() {
        return asc;
    }

    public void setAsc(boolean asc) {
        this.asc = asc;
    }


    @Override
    public String generateCacheKey() {
        List<String> list = Arrays.asList(keywords, blogName, userName, toUserName, content, blogId, blogTypeId,
                blogTagId, String.valueOf(sort), String.valueOf(asc), super.generateCacheKey());
        return StringUtils.join(list, CacheConstants.CACHE_LOCAL_SEP);
    }

}
