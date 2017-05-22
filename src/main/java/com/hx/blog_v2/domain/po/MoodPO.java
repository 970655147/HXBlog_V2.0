package com.hx.blog_v2.domain.po;

import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.DateUtils;
import com.hx.json.JSONObject;
import com.hx.log.json.interf.JSONTransferable;
import com.hx.log.util.Constants;
import com.hx.log.util.Tools;

import java.util.*;

/**
 * 心情
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/22/2017 8:03 PM
 */
public class MoodPO implements JSONTransferable<MoodPO, Integer> {

    private String id;
    private String title;
    private String content;
    private String createdAt;
    private String updatedAt;
    /**
     * 是否可用
     */
    private int enable;
    private int deleted;

    public MoodPO(String title, String content, int enable) {
        this();
        this.title = title;
        this.content = content;
        this.enable = enable;
    }

    public MoodPO() {
        Date now = new Date();
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }

    public int getDeleted() {
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
    public static final String[] contentIdxes = {"content", "content" };
    public static final String[] createdAtIdxes = {"createdAt", "created_at" };
    public static final String[] updatedAtIdxes = {"updatedAt", "updated_at" };
    public static final String[] enableIdxes = {"enable", "enable" };
    public static final String[] deletedIdxes = {"deleted", "deleted" };

    // encapJSON相关filter
    public static final int ALL = 0;
    public static final int FILTER_ID = ALL + 1;
    public static final List<Set<String>> filters = Tools.asList(Tools.asSet(""), Tools.asSet(idIdxes));

    public static final String BEAN_KEY = "mood_key";
    public static final MoodPO PROTO_BEAN = new MoodPO();

    @Override
    public MoodPO loadFromJSON(Map<String, Object> obj, Map<String, Integer> idxMap) {
        return loadFromJSON(obj, idxMap, Constants.EMPTY_INIT_OBJ_FILTER );
    }
    @Override
    public MoodPO loadFromJSON(Map<String, Object> obj, Map<String, Integer> idxMap, Set<String> initObjFilter) {
        if(Tools.isEmpty(obj) || Tools.isEmpty(idxMap) || (idxMap.get(BEAN_KEY) == null) ) {
            return this;
        }
        int idx = idxMap.get(BEAN_KEY).intValue();

        this.id = Tools.getString(obj, idx, idIdxes);
        this.title = Tools.getString(obj, idx, titleIdxes);
        this.content = Tools.getString(obj, idx, contentIdxes);
        this.createdAt = Tools.getString(obj, idx, createdAtIdxes);
        this.updatedAt = Tools.getString(obj, idx, updatedAtIdxes);
        this.enable = Tools.optBoolean(obj, idx, enableIdxes) ? 1 : 0;
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
            return JSONObject.fromObject(Constants.OBJECT_ALREADY_EXISTS).element("id", String.valueOf(id()) );
        }
        cycleDectector.push(this);

        if(Tools.isEmpty(idxMap) || (idxMap.get(BEAN_KEY) == null) ) {
            cycleDectector.pop();
            return null;
        }
        int idx = idxMap.get(BEAN_KEY).intValue();

        JSONObject res = new JSONObject()
                .element(idIdxes[Tools.getIdx(idx, idIdxes)], id).element(titleIdxes[Tools.getIdx(idx, titleIdxes)], title).element(contentIdxes[Tools.getIdx(idx, contentIdxes)], content)
                .element(createdAtIdxes[Tools.getIdx(idx, createdAtIdxes)], createdAt).element(updatedAtIdxes[Tools.getIdx(idx, updatedAtIdxes)], updatedAt).element(enableIdxes[Tools.getIdx(idx, enableIdxes)], enable)
                .element(deletedIdxes[Tools.getIdx(idx, deletedIdxes)], deleted);

        if(Tools.isEmpty(filterIdxMap) || (filterIdxMap.get(BEAN_KEY) == null) ) {
            cycleDectector.pop();
            return res;
        }

        cycleDectector.pop();
        int filterIdx = filterIdxMap.get(BEAN_KEY).intValue();
        return Tools.filter(res, filters.get(Tools.getIdx(filterIdx, filters.size())) );
    }

    @Override
    public MoodPO newInstance(Object... args) {
        return new MoodPO();
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
    public MoodPO protoBean() {
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
