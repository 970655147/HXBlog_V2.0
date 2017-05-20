package com.hx.blog_v2.domain.po;

import com.hx.json.JSONObject;
import com.hx.log.json.interf.JSONTransferable;
import com.hx.log.util.Constants;
import com.hx.log.util.Tools;

import java.util.*;

import static com.hx.blog_v2.domain.po.BlogPO.idIdxes;

/**
 * 博客的额外信息
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 9:46 AM
 */
public class BlogExPO implements JSONTransferable<BlogExPO, Integer> {

    private String id;
    private String blogId;
    /**
     * 评论的数量
     */
    private int commentCnt;
    /**
     * 查看的数量
     */
    private int viewCnt;
    /**
     * 点赞的数量
     */
    private int goodCnt;
    /**
     * 踩的数量
     */
    private int notGoodCnt;

    public BlogExPO(String blogId) {
        this.blogId = blogId;
    }

    public BlogExPO() {
    }

    /**
     * setter & getter
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBlogId() {
        return blogId;
    }

    public void setBlogId(String blogId) {
        this.blogId = blogId;
    }

    public int getCommentCnt() {
        return commentCnt;
    }

    public void setCommentCnt(int commentCnt) {
        this.commentCnt = commentCnt;
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

    // loadFromObject相关索引
    public static final int CAMEL = 0;
    public static final int UNDER_LINE = CAMEL + 1;
    public static final String[] idIdxes = {"id", "id" };
    public static final String[] blogIdIdxes = {"blogId", "blog_id" };
    public static final String[] commentCntIdxes = {"commentCnt", "comment_cnt" };
    public static final String[] viewCntIdxes = {"viewCnt", "view_cnt" };
    public static final String[] goodCntIdxes = {"goodCnt", "good_cnt" };
    public static final String[] notGoodCntIdxes = {"notGoodCnt", "not_good_cnt" };

    // encapJSON相关filter
    public static final int ALL = 0;
    public static final int FILTER_ID = ALL + 1;
    public static final List<Set<String>> filters = Tools.asList(Tools.asSet(""), Tools.asSet(idIdxes));

    public static final String BEAN_KEY = "blogExPO_key";
    public static final BlogExPO PROTO_BEAN = new BlogExPO();

    @Override
    public BlogExPO loadFromJSON(Map<String, Object> obj, Map<String, Integer> idxMap) {
        return loadFromJSON(obj, idxMap, Constants.EMPTY_INIT_OBJ_FILTER );
    }
    @Override
    public BlogExPO loadFromJSON(Map<String, Object> obj, Map<String, Integer> idxMap, Set<String> initObjFilter) {
        if(Tools.isEmpty(obj) || Tools.isEmpty(idxMap) || (idxMap.get(BEAN_KEY) == null) ) {
            return this;
        }
        int idx = idxMap.get(BEAN_KEY).intValue();

        this.id = Tools.getString(obj, idx, idIdxes);
        this.blogId = Tools.getString(obj, idx, blogIdIdxes);
        this.commentCnt = Tools.getInt(obj, idx, commentCntIdxes);
        this.viewCnt = Tools.getInt(obj, idx, viewCntIdxes);
        this.goodCnt = Tools.getInt(obj, idx, goodCntIdxes);
        this.notGoodCnt = Tools.getInt(obj, idx, notGoodCntIdxes);

        return this;
    }

    @Override
    public JSONObject encapJSON(Map<String, Integer> idxMap, Map<String, Integer> filterIdxMap) {
        return encapJSON(idxMap, filterIdxMap, new LinkedList<Object>() );
    }
    @Override
    public JSONObject encapJSON(Map<String, Integer> idxMap, Map<String, Integer> filterIdxMap, Deque<Object> cycleDectector) {
        if(cycleDectector.contains(this) ) {
            return JSONObject.fromObject(Constants.OBJECT_ALREADY_EXISTS).element("id", String.valueOf(id()) );
        }
        cycleDectector.push(this);

        if(Tools.isEmpty(idxMap) || (idxMap.get(BEAN_KEY) == null) ) {
            cycleDectector.pop();
            return null;
        }
        int idx = idxMap.get(BEAN_KEY).intValue();

        JSONObject res = new JSONObject()
                .element(idIdxes[Tools.getIdx(idx, idIdxes)], id).element(blogIdIdxes[Tools.getIdx(idx, blogIdIdxes)], blogId).element(commentCntIdxes[Tools.getIdx(idx, commentCntIdxes)], commentCnt)
                .element(viewCntIdxes[Tools.getIdx(idx, viewCntIdxes)], viewCnt).element(goodCntIdxes[Tools.getIdx(idx, goodCntIdxes)], goodCnt).element(notGoodCntIdxes[Tools.getIdx(idx, notGoodCntIdxes)], notGoodCnt);

        if(Tools.isEmpty(filterIdxMap) || (filterIdxMap.get(BEAN_KEY) == null) ) {
            cycleDectector.pop();
            return res;
        }

        cycleDectector.pop();
        int filterIdx = filterIdxMap.get(BEAN_KEY).intValue();
        return Tools.filter(res, filters.get(Tools.getIdx(filterIdx, filters.size())) );
    }

    @Override
    public BlogExPO newInstance(Object... args) {
        return new BlogExPO();
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
    public BlogExPO protoBean() {
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
