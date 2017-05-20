package com.hx.blog_v2.domain.po;

import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.DateUtils;
import com.hx.json.JSONObject;
import com.hx.log.json.interf.JSONTransferable;
import com.hx.log.util.Constants;
import com.hx.log.util.Tools;

import java.util.*;

/**
 * 请求日志的历史记录
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 10:16 AM
 */
public class RequestLogPO implements JSONTransferable<RequestLogPO, Integer> {

    private String id;
    /**
     * 请求的接口
     */
    private String url;
    /**
     * 请求的参数列表
     */
    private String params;
    /**
     * 开销的时间
     */
    private String cost;
    /**
     * 请求的用户
     */
    private String name;
    /**
     * 请求的用户的邮箱
     */
    private String email;
    private String createdAt;

    public RequestLogPO(String id, String url, String params, String cost, String name, String email) {
        this();
        this.id = id;
        this.url = url;
        this.params = params;
        this.cost = cost;
        this.name = name;
        this.email = email;
    }

    public RequestLogPO() {
        createdAt = DateUtils.formate(new Date(), BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
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
    public static final String[] urlIdxes = {"url", "url" };
    public static final String[] paramsIdxes = {"params", "params" };
    public static final String[] costIdxes = {"cost", "cost" };
    public static final String[] nameIdxes = {"name", "name" };
    public static final String[] emailIdxes = {"email", "email" };
    public static final String[] createdAtIdxes = {"createdAt", "created_at" };

    // encapJSON相关filter
    public static final int ALL = 0;
    public static final int FILTER_ID = ALL + 1;
    public static final List<Set<String>> filters = Tools.asList(Tools.asSet(""), Tools.asSet(idIdxes));

    public static final String BEAN_KEY = "requestLogPO_key";
    public static final RequestLogPO PROTO_BEAN = new RequestLogPO();

    @Override
    public RequestLogPO loadFromJSON(Map<String, Object> obj, Map<String, Integer> idxMap) {
        return loadFromJSON(obj, idxMap, Constants.EMPTY_INIT_OBJ_FILTER );
    }
    @Override
    public RequestLogPO loadFromJSON(Map<String, Object> obj, Map<String, Integer> idxMap, Set<String> initObjFilter) {
        if(Tools.isEmpty(obj) || Tools.isEmpty(idxMap) || (idxMap.get(BEAN_KEY) == null) ) {
            return this;
        }
        int idx = idxMap.get(BEAN_KEY).intValue();

        this.id = Tools.getString(obj, idx, idIdxes);
        this.url = Tools.getString(obj, idx, urlIdxes);
        this.params = Tools.getString(obj, idx, paramsIdxes);
        this.cost = Tools.getString(obj, idx, costIdxes);
        this.name = Tools.getString(obj, idx, nameIdxes);
        this.email = Tools.getString(obj, idx, emailIdxes);
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
                .element(idIdxes[Tools.getIdx(idx, idIdxes)], id).element(urlIdxes[Tools.getIdx(idx, urlIdxes)], url).element(paramsIdxes[Tools.getIdx(idx, paramsIdxes)], params)
                .element(costIdxes[Tools.getIdx(idx, costIdxes)], cost).element(nameIdxes[Tools.getIdx(idx, nameIdxes)], name).element(emailIdxes[Tools.getIdx(idx, emailIdxes)], email)
                .element(createdAtIdxes[Tools.getIdx(idx, createdAtIdxes)], createdAt);

        if(Tools.isEmpty(filterIdxMap) || (filterIdxMap.get(BEAN_KEY) == null) ) {
            cycleDectector.pop();
            return res;
        }

        cycleDectector.pop();
        int filterIdx = filterIdxMap.get(BEAN_KEY).intValue();
        return Tools.filter(res, filters.get(Tools.getIdx(filterIdx, filters.size())) );
    }

    @Override
    public RequestLogPO newInstance(Object... args) {
        return new RequestLogPO();
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
    public RequestLogPO protoBean() {
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
