package com.hx.blog_v2.domain.po.message;

import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.DateUtils;
import com.hx.json.JSONObject;
import com.hx.json.config.interf.JSONConfig;
import com.hx.json.interf.JSONField;
import com.hx.log.json.interf.JSONTransferable;
import com.hx.log.util.Constants;
import com.hx.log.util.Tools;

import java.util.*;

/**
 * EmailPO
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/26/2017 9:09 PM
 */
public class EmailPO implements JSONTransferable<EmailPO> {

    @JSONField({"id", "id"})
    private String id;
    @JSONField({"from", "from"})
    private String from;
    @JSONField({"to", "to"})
    private List<String> to;
    @JSONField({"cc", "cc"})
    private List<String> cc;
    @JSONField({"subject", "subject"})
    private String subject;
    @JSONField({"content", "content"})
    private String content;
    @JSONField({"contentType", "contentType"})
    private String contentType;
    @JSONField({"createdAt", "createdAt"})
    private String createdAt;

    public EmailPO(String from, List<String> to, List<String> cc, String subject, String content, String contentType) {
        this();
        Tools.assert0(to != null, "'to' can't be null !");
        Tools.assert0(!Tools.isEmpty(to), "'to' can't be null !");

        this.to = new ArrayList<>();
        this.cc = new ArrayList<>();

        this.from = from;
        this.to.addAll(to);
        if (cc != null) {
            for (String ccVal : cc) {
                if (!Tools.isEmpty(ccVal)) {
                    this.cc.add(ccVal);
                }
            }
        }
        this.subject = subject;
        this.content = content;
        this.contentType = contentType;
    }

    public EmailPO(String from, List<String> to, String cc, String subject, String content, String contentType) {
        this(from, to, Collections.singletonList(cc), subject, content, contentType);
    }

    public EmailPO(String from, String to, String cc, String subject, String content, String contentType) {
        this(from, Collections.singletonList(to), (cc == null) ? null : Collections.singletonList(cc),
                subject, content, contentType);
    }

    public EmailPO(String from, String to, String subject, String content, String contentType) {
        this(from, to, null, subject, content, contentType);
    }

    public EmailPO() {
        Date now = new Date();
        createdAt = DateUtils.format(now, BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS);
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public List<String> getTo() {
        return to;
    }

    public void setTo(List<String> to) {
        this.to = to;
    }

    public List<String> getCc() {
        return cc;
    }

    public void setCc(List<String> cc) {
        this.cc = cc;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void addTo(String to) {
        if (!this.to.contains(to)) {
            this.to.add(to);
        }
    }

    public void addCc(String cc) {
        if (!this.cc.contains(cc)) {
            this.cc.add(cc);
        }
    }

    public static final EmailPO PROTO_BEAN = new EmailPO();

    @Override
    public EmailPO loadFromJSON(Map<String, Object> obj, JSONConfig config) {
        if (Tools.isEmpty(obj)) {
            return this;
        }

        JSONObject.fromObject(obj).toBean(EmailPO.class, this, config);
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
    public EmailPO newInstance(Object... args) {
        return new EmailPO();
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
