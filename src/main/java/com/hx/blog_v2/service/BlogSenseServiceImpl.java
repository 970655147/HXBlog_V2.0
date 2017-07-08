package com.hx.blog_v2.service;

import com.hx.blog_v2.context.CacheContext;
import com.hx.blog_v2.context.WebContext;
import com.hx.blog_v2.dao.interf.BlogExDao;
import com.hx.blog_v2.dao.interf.BlogSenseDao;
import com.hx.blog_v2.domain.dto.SessionUser;
import com.hx.blog_v2.domain.form.BlogSenseForm;
import com.hx.blog_v2.domain.po.BlogSensePO;
import com.hx.blog_v2.service.interf.BaseServiceImpl;
import com.hx.blog_v2.service.interf.BlogSenseService;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.ResultUtils;
import com.hx.common.interf.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * BlogServiceImpl
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 11:47 AM
 */
@Service
public class BlogSenseServiceImpl extends BaseServiceImpl<BlogSensePO> implements BlogSenseService {

    @Autowired
    private BlogSenseDao senseDao;
    @Autowired
    private BlogExDao blogExDao;
    @Autowired
    private CacheContext cacheContext;


    @Override
    public Result sense(BlogSenseForm params) {
        SessionUser user = (SessionUser) WebContext.getAttributeFromSession(BlogConstants.SESSION_USER);
        Result getSenseResult = senseDao.get(params);
        boolean exists = (getSenseResult.isSuccess());
        if (exists && (!user.isSystemUser())) {
            return ResultUtils.failed(" do not sense again ! ");
        }

        BlogSensePO oldPo = exists ? (BlogSensePO) getSenseResult.getData() : null;
        BlogSensePO po = new BlogSensePO(params.getBlogId(), params.getName(), params.getEmail(), params.getRequestIp(),
                params.getSense(), params.getScore());
        WebContext.setAttributeForRequest(BlogConstants.REQUEST_DATA, po);
        WebContext.setAttributeForRequest(BlogConstants.REQUEST_EXTRA, oldPo);

        if (!exists) {
            Result saveResult = senseDao.add(po);
            if (!saveResult.isSuccess()) {
                return saveResult;
            }
        }
        return ResultUtils.success("success");
    }


}
