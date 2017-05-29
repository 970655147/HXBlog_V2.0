package com.hx.blog_v2.domain.po;

import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.DateUtils;
import com.hx.json.JSONObject;
import com.hx.json.config.interf.JSONConfig;
import com.hx.json.interf.JSONField;
import com.hx.log.json.interf.JSONTransferable;
import com.hx.log.util.Constants;
import com.hx.log.util.Tools;

import java.util.Date;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;

/**
 * 上传到服务器的图片的信息[不提供给用户 以及管理者]
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/29/2017 4:11 PM
 */
public class UploadedImagePO implements JSONTransferable<UploadedImagePO> {

    @JSONField({"id", "id"})
    private String id;
    @JSONField({"originalFileName", "original_file_name"})
    private String originalFileName;
    @JSONField({"contentType", "content_type"})
    private String contentType;
    @JSONField({"url", "url"})
    private String url;
    @JSONField({"digest", "digest"})
    private String digest;
    @JSONField({"size", "size"})
    private String size;
    @JSONField({"createdAt", "created_at"})
    private String createdAt;

    public UploadedImagePO(String originalFileName, String contentType, String url, String digest, String size) {
        this();
        this.originalFileName = originalFileName;
        this.contentType = contentType;
        this.url = url;
        this.digest = digest;
        this.size = size;
    }

    public UploadedImagePO() {
        Date now = new Date();
        createdAt = DateUtils.formate(now, BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    // for debug
    @Override
    public String toString() {
        return JSONObject.fromObject(this).toString();
    }

    public static final UploadedImagePO PROTO_BEAN = new UploadedImagePO();

    @Override
    public UploadedImagePO loadFromJSON(Map<String, Object> obj, JSONConfig config) {
        if (Tools.isEmpty(obj)) {
            return this;
        }

        JSONObject.fromObject(obj).toBean(UploadedImagePO.class, this, config);
        return this;
    }

    @Override
    public JSONObject encapJSON(JSONConfig config) {
        return encapJSON(config, new LinkedList<Object>() );
    }

    @Override
    public JSONObject encapJSON(JSONConfig config, Deque<Object> cycleDectector) {
        if(cycleDectector.contains(this) ) {
            return JSONObject.fromObject(Constants.OBJECT_ALREADY_EXISTS).element("id", String.valueOf(id()) );
        }
        cycleDectector.push(this);

        return JSONObject.fromObject(this, config);
    }

    @Override
    public UploadedImagePO newInstance(Object... args) {
        return new UploadedImagePO();
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
