package com.hx.blog_v2.domain.po;

import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.DateUtils;
import com.hx.json.JSONObject;
import com.hx.log.json.interf.JSONTransferable;
import com.hx.log.util.Tools;

import java.util.*;

/**
 * 博客的一条记录
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 9:42 AM
 */
public class BlogPO implements JSONTransferable<BlogPO, Integer> {

    private String id;
    private String title;
    private String author;
    /**
     * 封面的url
     */
    private String coverUrl;
    /**
     * 所属的分类
     */
    private String blogTypeId;
    /**
     * 摘要
     */
    private String summary;
    /**
     * 内容的映射
     */
    private String contentUrl;

    private String createdAt;
    private String createdAtMonth;
    private String updatedAt;
    private int deleted;

    public BlogPO(String title, String author, String coverUrl, String blogTypeId, String summary, String contentUrl) {
        this();
        this.title = title;
        this.author = author;
        this.coverUrl = coverUrl;
        this.blogTypeId = blogTypeId;
        this.summary = summary;
        this.contentUrl = contentUrl;
    }

    public BlogPO() {
        Date now = new Date();
        createdAtMonth = DateUtils.formate(now, BlogConstants.FORMAT_YYYY_MM);
        createdAt = DateUtils.formate(now, BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS);
        updatedAt = createdAt;
        deleted = 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getBlogTypeId() {
        return blogTypeId;
    }

    public void setBlogTypeId(String blogTypeId) {
        this.blogTypeId = blogTypeId;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedAtMonth() {
        return createdAtMonth;
    }

    public void setCreatedAtMonth(String createdAtMonth) {
        this.createdAtMonth = createdAtMonth;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int isDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    // loadFromObject相关索引
    public static final int CAMEL = 0;
    public static final int UNDER_LINE = CAMEL + 1;
    public static final String[] idIdxes = {"id", "id" };
    public static final String[] titleIdxes = {"title", "title" };
    public static final String[] authorIdxes = {"author", "author" };
    public static final String[] coverUrlIdxes = {"coverUrl", "cover_url" };
    public static final String[] blogTypeIdIdxes = {"blogTypeId", "blog_type_id" };
    public static final String[] summaryIdxes = {"summary", "summary" };
    public static final String[] contentUrlIdxes = {"contentUrl", "content_url" };
    public static final String[] createdAtIdxes = {"createdAt", "created_at" };
    public static final String[] createdAtMonthIdxes = {"createdAtMonth", "created_at_month" };
    public static final String[] updatedAtIdxes = {"updatedAt", "updated_at" };
    public static final String[] deletedIdxes = {"deleted", "deleted" };

    // encapJSON相关filter
    public static final int ALL = 0;
    public static final int FILTER_ID = ALL + 1;
    public static final List<Set<String>> filters = Tools.asList(Tools.asSet(""), Tools.asSet(idIdxes));

    public static final String BEAN_KEY = "blogPO_key";
    public static final BlogPO PROTO_BEAN = new BlogPO();

    @Override
    public BlogPO loadFromJSON(Map<String, Object> obj, Map<String, Integer> idxMap) {
        return loadFromJSON(obj, idxMap, com.hx.log.util.Constants.EMPTY_INIT_OBJ_FILTER );
    }
    @Override
    public BlogPO loadFromJSON(Map<String, Object> obj, Map<String, Integer> idxMap, Set<String> initObjFilter) {
        if(Tools.isEmpty(obj) || Tools.isEmpty(idxMap) || (idxMap.get(BEAN_KEY) == null) ) {
            return this;
        }
        int idx = idxMap.get(BEAN_KEY).intValue();

        this.id = Tools.getString(obj, idx, idIdxes);
        this.title = Tools.optString(obj, idx, titleIdxes);
        this.author = Tools.optString(obj, idx, authorIdxes);
        this.coverUrl = Tools.optString(obj, idx, coverUrlIdxes);
        this.blogTypeId = Tools.optString(obj, idx, blogTypeIdIdxes);
        this.summary = Tools.optString(obj, idx, summaryIdxes);
        this.contentUrl = Tools.optString(obj, idx, contentUrlIdxes);
        this.createdAt = Tools.optString(obj, idx, createdAtIdxes);
        this.createdAtMonth = Tools.optString(obj, idx, createdAtMonthIdxes);
        this.updatedAt = Tools.optString(obj, idx, updatedAtIdxes);
        this.deleted = Tools.optBoolean(obj, idx, deletedIdxes) ? 1 : 0;

        return this;
    }

    @Override
    public JSONObject encapJSON(Map<String, Integer> idxMap, Map<String, Integer> filterIdxMap) {
        return encapJSON(idxMap, filterIdxMap, new LinkedList<Object>() );
    }
    @Override
    public JSONObject encapJSON(Map<String, Integer> idxMap, Map<String, Integer> filterIdxMap, Deque<Object> cycleDectector) {
        if(cycleDectector.contains(this) ) {
            return JSONObject.fromObject(com.hx.log.util.Constants.OBJECT_ALREADY_EXISTS).element("id", String.valueOf(id()) );
        }
        cycleDectector.push(this);

        if(Tools.isEmpty(idxMap) || (idxMap.get(BEAN_KEY) == null) ) {
            cycleDectector.pop();
            return null;
        }
        int idx = idxMap.get(BEAN_KEY).intValue();

        JSONObject res = new JSONObject()
                .element(idIdxes[Tools.getIdx(idx, idIdxes)], id).element(titleIdxes[Tools.getIdx(idx, titleIdxes)], title).element(authorIdxes[Tools.getIdx(idx, authorIdxes)], author)
                .element(coverUrlIdxes[Tools.getIdx(idx, coverUrlIdxes)], coverUrl).element(blogTypeIdIdxes[Tools.getIdx(idx, blogTypeIdIdxes)], blogTypeId).element(summaryIdxes[Tools.getIdx(idx, summaryIdxes)], summary)
                .element(contentUrlIdxes[Tools.getIdx(idx, contentUrlIdxes)], contentUrl).element(createdAtIdxes[Tools.getIdx(idx, createdAtIdxes)], createdAt).element(createdAtMonthIdxes[Tools.getIdx(idx, createdAtMonthIdxes)], createdAtMonth)
                .element(updatedAtIdxes[Tools.getIdx(idx, updatedAtIdxes)], updatedAt).element(deletedIdxes[Tools.getIdx(idx, deletedIdxes)], deleted);

        if(Tools.isEmpty(filterIdxMap) || (filterIdxMap.get(BEAN_KEY) == null) ) {
            cycleDectector.pop();
            return res;
        }

        cycleDectector.pop();
        int filterIdx = filterIdxMap.get(BEAN_KEY).intValue();
        return Tools.filter(res, filters.get(Tools.getIdx(filterIdx, filters.size())) );
    }

    @Override
    public BlogPO newInstance(Object... args) {
        return new BlogPO();
    }
    @Override
    public String id() {
        return id;
    }
    @Override
    public void id(String id) {
        this.id = id;
    }
    @Override
    public String beanKey() {
        return BEAN_KEY;
    }
    @Override
    public BlogPO protoBean() {
        return PROTO_BEAN;
    }
    @Override
    public Integer defaultLoadIdx() {
        return CAMEL;
    }
    @Override
    public Integer defaultFilterIdx() {
        return ALL;
    }

}
