package com.hx.blog_v2.domain.po;

import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.DateUtils;
import com.hx.json.JSONObject;
import com.hx.log.json.interf.JSONTransferable;
import com.hx.log.util.Constants;
import com.hx.log.util.Tools;

import java.util.*;

/**
 * 用户
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/23/2017 8:12 PM
 */
public class UserPO implements JSONTransferable<UserPO, Integer> {

    private String id;
    private String userName;
    private String password;
    private String nickName;
    private String email;
    private String headImgUrl;
    private String motto;
    private String lastLoginIp;
    private String lastLoginAt;
    private String createdAt;
    private String updatedAt;
    private int deleted;

    public UserPO(String userName, String password, String nickName, String email, String headImgUrl, String motto) {
        this();
        this.userName = userName;
        this.password = password;
        this.nickName = nickName;
        this.email = email;
        this.headImgUrl = headImgUrl;
        this.motto = motto;
    }

    public UserPO() {
        Date now = new Date();
        createdAt = DateUtils.formate(now, BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS);
        updatedAt = createdAt;
        lastLoginAt = DateUtils.formate(new Date(0), BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS);
        deleted = 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public String getLastLoginAt() {
        return lastLoginAt;
    }

    public void setLastLoginAt(String lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
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
    public static final String[] userNameIdxes = {"userName", "user_name" };
    public static final String[] passwordIdxes = {"password", "password" };
    public static final String[] nickNameIdxes = {"nickName", "nick_name" };
    public static final String[] emailIdxes = {"email", "email" };
    public static final String[] headImgUrlIdxes = {"headImgUrl", "head_img_url" };
    public static final String[] mottoIdxes = {"motto", "motto" };
    public static final String[] lastLoginIpIdxes = {"lastLoginIp", "last_login_ip" };
    public static final String[] lastLoginAtIdxes = {"lastLoginAt", "last_login_at" };
    public static final String[] createdAtIdxes = {"createdAt", "created_at" };
    public static final String[] updatedAtIdxes = {"updatedAt", "updated_at" };
    public static final String[] deletedIdxes = {"deleted", "deleted" };

    // encapJSON相关filter
    public static final int ALL = 0;
    public static final int FILTER_ID = ALL + 1;
    public static final int FILTER_WHILE_UPDATE = FILTER_ID + 1;
    public static final List<Set<String>> filters = Tools.asList(Tools.asSet(""), Tools.asSet(idIdxes),
            Tools.asSet(idIdxes, userNameIdxes, passwordIdxes, lastLoginIpIdxes, lastLoginAtIdxes, createdAtIdxes)
    );

    public static final String BEAN_KEY = "userPO_key";
    public static final UserPO PROTO_BEAN = new UserPO();

    @Override
    public UserPO loadFromJSON(Map<String, Object> obj, Map<String, Integer> idxMap) {
        return loadFromJSON(obj, idxMap, Constants.EMPTY_INIT_OBJ_FILTER );
    }
    @Override
    public UserPO loadFromJSON(Map<String, Object> obj, Map<String, Integer> idxMap, Set<String> initObjFilter) {
        if(Tools.isEmpty(obj) || Tools.isEmpty(idxMap) || (idxMap.get(BEAN_KEY) == null) ) {
            return this;
        }
        int idx = idxMap.get(BEAN_KEY).intValue();

        this.id = Tools.getString(obj, idx, idIdxes);
        this.userName = Tools.getString(obj, idx, userNameIdxes);
        this.password = Tools.getString(obj, idx, passwordIdxes);
        this.nickName = Tools.getString(obj, idx, nickNameIdxes);
        this.email = Tools.getString(obj, idx, emailIdxes);
        this.headImgUrl = Tools.getString(obj, idx, headImgUrlIdxes);
        this.motto = Tools.getString(obj, idx, mottoIdxes);
        this.lastLoginIp = Tools.getString(obj, idx, lastLoginIpIdxes);
        this.lastLoginAt = Tools.getString(obj, idx, lastLoginAtIdxes);
        this.createdAt = Tools.getString(obj, idx, createdAtIdxes);
        this.updatedAt = Tools.getString(obj, idx, updatedAtIdxes);
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
                .element(idIdxes[Tools.getIdx(idx, idIdxes)], id).element(userNameIdxes[Tools.getIdx(idx, userNameIdxes)], userName).element(passwordIdxes[Tools.getIdx(idx, passwordIdxes)], password)
                .element(nickNameIdxes[Tools.getIdx(idx, nickNameIdxes)], nickName).element(emailIdxes[Tools.getIdx(idx, emailIdxes)], email).element(headImgUrlIdxes[Tools.getIdx(idx, headImgUrlIdxes)], headImgUrl)
                .element(mottoIdxes[Tools.getIdx(idx, mottoIdxes)], motto).element(lastLoginIpIdxes[Tools.getIdx(idx, lastLoginIpIdxes)], lastLoginIp).element(lastLoginAtIdxes[Tools.getIdx(idx, lastLoginAtIdxes)], lastLoginAt)
                .element(createdAtIdxes[Tools.getIdx(idx, createdAtIdxes)], createdAt).element(updatedAtIdxes[Tools.getIdx(idx, updatedAtIdxes)], updatedAt).element(deletedIdxes[Tools.getIdx(idx, deletedIdxes)], deleted);

        if(Tools.isEmpty(filterIdxMap) || (filterIdxMap.get(BEAN_KEY) == null) ) {
            cycleDectector.pop();
            return res;
        }

        cycleDectector.pop();
        int filterIdx = filterIdxMap.get(BEAN_KEY).intValue();
        return Tools.filter(res, filters.get(Tools.getIdx(filterIdx, filters.size())) );
    }

    @Override
    public UserPO newInstance(Object... args) {
        return new UserPO();
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
    public UserPO protoBean() {
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
