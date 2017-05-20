package com.hx.blog_v2.domain.po;

import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.DateUtils;
import com.hx.json.JSONObject;
import com.hx.log.json.interf.JSONTransferable;
import com.hx.log.util.Constants;
import com.hx.log.util.Tools;

import java.util.*;

/**
 * 博客顶踩的记录
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 10:12 AM
 */
public class BlogSensePO implements JSONTransferable<BlogSensePO, Integer> {

    private String id;
    private String blogId;
    private String name;
    private String email;
    private String isGood;
    private String createdAt;

    public BlogSensePO(String blogId, String name, String email, String isGood) {
        this();
        this.blogId = blogId;
        this.name = name;
        this.email = email;
        this.isGood = isGood;
    }

    public BlogSensePO() {
        this.createdAt = DateUtils.formate(new Date(), BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS);
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIsGood() {
        return isGood;
    }

    public void setIsGood(String isGood) {
        this.isGood = isGood;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    // loadFromObject相关索引
    public static final int CAMEL = 0;
    public static final int UNDER_LINE = CAMEL + 1;
    public static final String[] idIdxes = {"id", "id" };
    public static final String[] blogIdIdxes = {"blogId", "blog_id" };
    public static final String[] nameIdxes = {"name", "name" };
    public static final String[] emailIdxes = {"email", "email" };
    public static final String[] isGoodIdxes = {"isGood", "is_good" };
    public static final String[] createdAtIdxes = {"createdAt", "created_at" };

    // encapJSON相关filter
    public static final int ALL = 0;
    public static final int FILTER_ID = ALL + 1;
    public static final List<Set<String>> filters = Tools.asList(Tools.asSet(""), Tools.asSet(idIdxes));

    public static final String BEAN_KEY = "blogSensePO_key";
    public static final BlogSensePO PROTO_BEAN = new BlogSensePO();

    @Override
    public BlogSensePO loadFromJSON(Map<String, Object> obj, Map<String, Integer> idxMap) {
        return loadFromJSON(obj, idxMap, Constants.EMPTY_INIT_OBJ_FILTER );
    }
    @Override
    public BlogSensePO loadFromJSON(Map<String, Object> obj, Map<String, Integer> idxMap, Set<String> initObjFilter) {
        if(Tools.isEmpty(obj) || Tools.isEmpty(idxMap) || (idxMap.get(BEAN_KEY) == null) ) {
            return this;
        }
        int idx = idxMap.get(BEAN_KEY).intValue();

        this.id = Tools.getString(obj, idx, idIdxes);
        this.blogId = Tools.getString(obj, idx, blogIdIdxes);
        this.name = Tools.getString(obj, idx, nameIdxes);
        this.email = Tools.getString(obj, idx, emailIdxes);
        this.isGood = Tools.getString(obj, idx, isGoodIdxes);
        this.createdAt = Tools.getString(obj, idx, createdAtIdxes);

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
                .element(idIdxes[Tools.getIdx(idx, idIdxes)], id).element(blogIdIdxes[Tools.getIdx(idx, blogIdIdxes)], blogId).element(nameIdxes[Tools.getIdx(idx, nameIdxes)], name)
                .element(emailIdxes[Tools.getIdx(idx, emailIdxes)], email).element(isGoodIdxes[Tools.getIdx(idx, isGoodIdxes)], isGood).element(createdAtIdxes[Tools.getIdx(idx, createdAtIdxes)], createdAt);

        if(Tools.isEmpty(filterIdxMap) || (filterIdxMap.get(BEAN_KEY) == null) ) {
            cycleDectector.pop();
            return res;
        }

        cycleDectector.pop();
        int filterIdx = filterIdxMap.get(BEAN_KEY).intValue();
        return Tools.filter(res, filters.get(Tools.getIdx(filterIdx, filters.size())) );
    }

    @Override
    public BlogSensePO newInstance(Object... args) {
        return new BlogSensePO();
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
    public BlogSensePO protoBean() {
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
