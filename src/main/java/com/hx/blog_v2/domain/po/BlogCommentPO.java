package com.hx.blog_v2.domain.po;

import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.DateUtils;
import com.hx.json.JSONObject;
import com.hx.log.json.interf.JSONTransferable;
import com.hx.log.util.Constants;
import com.hx.log.util.Tools;

import java.util.*;

/**
 * 博客的回复
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 10:09 AM
 */
public class BlogCommentPO implements JSONTransferable<BlogCommentPO, Integer> {

    private String id;
    private String blogId;
    private String floorId;
    private String commentId;
    private String parentCommentId;

    private String name;
    private String email;
    private String headImgUrl;
    private String toUser;
    private String role;
    private String content;

    private String createdAt;
    private String updatedAt;
    private int deleted;

    public BlogCommentPO(String name, String email, String headImgUrl, String toUser, String role, String content) {
        this();
        this.name = name;
        this.email = email;
        this.headImgUrl = headImgUrl;
        this.toUser = toUser;
        this.role = role;
        this.content = content;
    }

    public BlogCommentPO() {
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

    public String getBlogId() {
        return blogId;
    }

    public void setBlogId(String blogId) {
        this.blogId = blogId;
    }

    public String getFloorId() {
        return floorId;
    }

    public void setFloorId(String floorId) {
        this.floorId = floorId;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(String parentCommentId) {
        this.parentCommentId = parentCommentId;
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

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
    public static final String[] blogIdIdxes = {"blogId", "blog_id" };
    public static final String[] floorIdIdxes = {"floorId", "floor_id" };
    public static final String[] commentIdIdxes = {"commentId", "comment_id" };
    public static final String[] parentCommentIdIdxes = {"parentCommentId", "parent_comment_id" };
    public static final String[] nameIdxes = {"name", "name" };
    public static final String[] emailIdxes = {"email", "email" };
    public static final String[] headImgUrlIdxes = {"headImgUrl", "head_img_url" };
    public static final String[] toUserIdxes = {"toUser", "to_user" };
    public static final String[] roleIdxes = {"role", "role" };
    public static final String[] contentIdxes = {"content", "content" };
    public static final String[] createdAtIdxes = {"createdAt", "created_at" };
    public static final String[] updatedAtIdxes = {"updatedAt", "updated_at" };
    public static final String[] deletedIdxes = {"deleted", "deleted" };

    // encapJSON相关filter
    public static final int ALL = 0;
    public static final int FILTER_ID = ALL + 1;
    public static final List<Set<String>> filters = Tools.asList(Tools.asSet(""), Tools.asSet(idIdxes));

    public static final String BEAN_KEY = "blogCommentPO_key";
    public static final BlogCommentPO PROTO_BEAN = new BlogCommentPO();

    @Override
    public BlogCommentPO loadFromJSON(Map<String, Object> obj, Map<String, Integer> idxMap) {
        return loadFromJSON(obj, idxMap, Constants.EMPTY_INIT_OBJ_FILTER );
    }
    @Override
    public BlogCommentPO loadFromJSON(Map<String, Object> obj, Map<String, Integer> idxMap, Set<String> initObjFilter) {
        if(Tools.isEmpty(obj) || Tools.isEmpty(idxMap) || (idxMap.get(BEAN_KEY) == null) ) {
            return this;
        }
        int idx = idxMap.get(BEAN_KEY).intValue();

        this.id = Tools.getString(obj, idx, idIdxes);
        this.blogId = Tools.optString(obj, idx, blogIdIdxes);
        this.floorId = Tools.optString(obj, idx, floorIdIdxes);
        this.commentId = Tools.optString(obj, idx, commentIdIdxes);
        this.parentCommentId = Tools.optString(obj, idx, parentCommentIdIdxes);
        this.name = Tools.optString(obj, idx, nameIdxes);
        this.email = Tools.optString(obj, idx, emailIdxes);
        this.headImgUrl = Tools.optString(obj, idx, headImgUrlIdxes);
        this.toUser = Tools.optString(obj, idx, toUserIdxes);
        this.role = Tools.optString(obj, idx, roleIdxes);
        this.content = Tools.optString(obj, idx, contentIdxes);
        this.createdAt = Tools.optString(obj, idx, createdAtIdxes);
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
            return JSONObject.fromObject(Constants.OBJECT_ALREADY_EXISTS).element("id", String.valueOf(id()) );
        }
        cycleDectector.push(this);

        if(Tools.isEmpty(idxMap) || (idxMap.get(BEAN_KEY) == null) ) {
            cycleDectector.pop();
            return null;
        }
        int idx = idxMap.get(BEAN_KEY).intValue();

        JSONObject res = new JSONObject()
                .element(idIdxes[Tools.getIdx(idx, idIdxes)], id).element(blogIdIdxes[Tools.getIdx(idx, blogIdIdxes)], blogId).element(floorIdIdxes[Tools.getIdx(idx, floorIdIdxes)], floorId)
                .element(commentIdIdxes[Tools.getIdx(idx, commentIdIdxes)], commentId).element(nameIdxes[Tools.getIdx(idx, nameIdxes)], name).element(emailIdxes[Tools.getIdx(idx, emailIdxes)], email)
                .element(headImgUrlIdxes[Tools.getIdx(idx, headImgUrlIdxes)], headImgUrl).element(toUserIdxes[Tools.getIdx(idx, toUserIdxes)], toUser).element(roleIdxes[Tools.getIdx(idx, roleIdxes)], role)
                .element(contentIdxes[Tools.getIdx(idx, contentIdxes)], content).element(createdAtIdxes[Tools.getIdx(idx, createdAtIdxes)], createdAt).element(updatedAtIdxes[Tools.getIdx(idx, updatedAtIdxes)], updatedAt)
                .element(deletedIdxes[Tools.getIdx(idx, deletedIdxes)], deleted).element(parentCommentIdIdxes[Tools.getIdx(idx, parentCommentIdIdxes)], parentCommentId);

        if(Tools.isEmpty(filterIdxMap) || (filterIdxMap.get(BEAN_KEY) == null) ) {
            cycleDectector.pop();
            return res;
        }

        cycleDectector.pop();
        int filterIdx = filterIdxMap.get(BEAN_KEY).intValue();
        return Tools.filter(res, filters.get(Tools.getIdx(filterIdx, filters.size())) );
    }

    @Override
    public BlogCommentPO newInstance(Object... args) {
        return new BlogCommentPO();
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
    public BlogCommentPO protoBean() {
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
