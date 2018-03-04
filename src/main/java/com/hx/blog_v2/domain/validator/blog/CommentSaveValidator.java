package com.hx.blog_v2.domain.validator.blog;

import com.hx.blog_v2.context.ConstantsContext;
import com.hx.blog_v2.context.WebContext;
import com.hx.blog_v2.domain.ErrorCode;
import com.hx.blog_v2.domain.form.blog.CommentSaveForm;
import com.hx.blog_v2.domain.validator.message.EmailValidator;
import com.hx.blog_v2.domain.validator.common.IpValidator;
import com.hx.blog_v2.domain.validator.common.UrlValidator;
import com.hx.blog_v2.domain.validator.others.UserNameValidator;
import com.hx.blog_v2.domain.validator.interf.ConfigRefreshableValidator;
import com.hx.blog_v2.domain.validator.common.BeanIdStrValidator;
import com.hx.blog_v2.util.BizUtils;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.ElementHandler;
import com.hx.blog_v2.util.ResultUtils;
import com.hx.common.interf.common.Result;
import com.hx.common.interf.validator.Validator;
import com.hx.log.util.Tools;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * CommentSaveValidator
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/15/2017 7:18 PM
 */
@Component
public class CommentSaveValidator extends ConfigRefreshableValidator<CommentSaveForm> implements Validator<CommentSaveForm> {

    @Autowired
    private UserNameValidator userNameValidator;
    @Autowired
    private EmailValidator emailValidator;
    @Autowired
    private UrlValidator urlValidator;
    @Autowired
    private IpValidator ipValidator;
    @Autowired
    private CommentContentValidator commentContentValidator;
    @Autowired
    private BeanIdStrValidator beanIdStrValidator;
    @Autowired
    private ConstantsContext constantsContext;

    /**
     * 相关配置
     */
    private String commentImgWidth = null;
    private String commentImgHeight = null;
    private int commentDivMinLen = -1;

    @Override
    public Result doValidate(CommentSaveForm form, Object extra) {
        if (!beanIdStrValidator.validate(form.getBlogId(), extra).isSuccess()) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " blogId 非数字 ! ");
        }
        if (!Tools.isEmpty(form.getFloorId())) {
            if (!beanIdStrValidator.validate(form.getFloorId(), extra).isSuccess()) {
                return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " floorId 非数字 ! ");
            }
        }
        if (!Tools.isEmpty(form.getCommentId())) {
            if (!beanIdStrValidator.validate(form.getCommentId(), extra).isSuccess()) {
                return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " commentId 非数字 ! ");
            }
        }

        boolean isLogin = WebContext.getAttributeFromSession(BlogConstants.SESSION_USER_ID) != null;
        if ((!isLogin) || (!Tools.isEmpty(form.getName()))) {
            Result result = userNameValidator.validate(form.getName(), extra);
            if (!result.isSuccess()) {
                return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " 用户名格式不合法 ! ");
            }
        }
        Result result = userNameValidator.validate(form.getToUser(), extra);
        if (!result.isSuccess()) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " 用户名格式不合法 ! ");
        }
        if (!Tools.isEmpty(form.getEmail())) {
            result = emailValidator.validate(form.getEmail(), extra);
            if (!result.isSuccess()) {
                return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " email 格式不合法 ! ");
            }
        }
        if (!Tools.isEmpty(form.getRequestIp())) {
            result = ipValidator.validate(form.getRequestIp(), extra);
            if (!result.isSuccess()) {
                return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " requestIp 格式不合法 ! ");
            }
        }
        if ((!isLogin) || (!Tools.isEmpty(form.getHeadImgUrl()))) {
            result = urlValidator.validate(form.getHeadImgUrl(), extra);
            if (!result.isSuccess()) {
                return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " 头像url 格式不合法 ! ");
            }
        }
        result = commentContentValidator.validate(form.getComment(), extra);
        if (!result.isSuccess()) {
            return result;
        }

        // comment 的处理
        Map<String, ElementHandler> tag2Handler = Tools.<String, ElementHandler>asMap(new String[]{"img", "div"},
                commentSaveElementHandler, commentSaveElementHandler);
        String contentFormatted = BizUtils.prepareContent("[comment] " + form.getBlogId() + "-" + form.getFloorId(),
                form.getComment(), constantsContext.allowTagCommentSensetiveTags, constantsContext.allowTagSensetiveTag2Attr,
                constantsContext.allowTagSensetiveAttrs, tag2Handler);
        form.setComment(contentFormatted);

        return ResultUtils.success();
    }

    @Override
    public boolean needRefresh() {
        return commentDivMinLen < 0;
    }

    @Override
    public void refreshConfig() {
        commentImgWidth = constantsContext.ruleConfig("comment.img.width", "40px");
        commentImgHeight = constantsContext.ruleConfig("comment.img.height", "40px");
        commentDivMinLen = Integer.parseInt(constantsContext.ruleConfig("comment.div.minLen", "20"));
    }

    /**
     * 处理评论的 ElementHandler
     *
     * @author Jerry.X.He <970655147@qq.com>
     * @version 1.0
     * @date 7/6/2017 8:44 PM
     */
    private ElementHandler commentSaveElementHandler = new ElementHandler() {
        @Override
        public void handle(Element ele) {
            String lowerTagName = ele.tagName().toLowerCase();
            if ("img".equals(lowerTagName)) {
                ele.attr("width", commentImgWidth);
                ele.attr("height", commentImgHeight);
            } else if ("div".equals(lowerTagName)) {
                int lenOfDiv = ele.text().length();
                if (lenOfDiv < commentDivMinLen) {
                    ele.attr("style", "display:inline;");
                }
            }
        }
    };

}
