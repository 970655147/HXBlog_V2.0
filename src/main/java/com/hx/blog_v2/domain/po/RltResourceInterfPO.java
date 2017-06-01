package com.hx.blog_v2.domain.po;

import com.hx.json.JSONObject;
import com.hx.json.config.interf.JSONConfig;
import com.hx.json.interf.JSONField;
import com.hx.log.json.interf.JSONTransferable;
import com.hx.log.util.Constants;
import com.hx.log.util.Tools;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;

/**
 * role -> resource
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 10:05 AM
 */
public class RltResourceInterfPO implements JSONTransferable<RltResourceInterfPO> {

    @JSONField({"id", "id"})
    private String id;
    @JSONField({"resourceId", "resource_id"})
    private String resourceId;
    @JSONField({"interfId", "interf_id"})
    private String interfId;

    public RltResourceInterfPO(String resourceId, String interfId) {
        this.resourceId = resourceId;
        this.interfId = interfId;
    }

    public RltResourceInterfPO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInterfId() {
        return interfId;
    }

    public void setInterfId(String interfId) {
        this.interfId = interfId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public static final RltResourceInterfPO PROTO_BEAN = new RltResourceInterfPO();

    @Override
    public RltResourceInterfPO loadFromJSON(Map<String, Object> obj, JSONConfig config) {
        if (Tools.isEmpty(obj)) {
            return this;
        }

        JSONObject.fromObject(obj).toBean(RltResourceInterfPO.class, this, config);
        return this;
    }

    @Override
    public JSONObject encapJSON(JSONConfig config) {
        return encapJSON(config, new LinkedList<>());
    }

    @Override
    public JSONObject encapJSON(JSONConfig config, Deque<Object> cycleDectector) {
        if (cycleDectector.contains(this)) {
            return JSONObject.fromObject(Constants.OBJECT_ALREADY_EXISTS).element("id", String.valueOf(id()));
        }
        cycleDectector.push(this);

        JSONObject result = JSONObject.fromObject(this, config);
        return result;
    }

    @Override
    public RltResourceInterfPO newInstance(Object... args) {
        return new RltResourceInterfPO();
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public void id(String id) {
        this.id = id;
    }


}
