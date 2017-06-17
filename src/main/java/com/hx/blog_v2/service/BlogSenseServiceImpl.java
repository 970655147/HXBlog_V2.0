package com.hx.blog_v2.service;

import com.hx.blog_v2.dao.interf.BlogExDao;
import com.hx.blog_v2.dao.interf.BlogSenseDao;
import com.hx.blog_v2.domain.form.BeanIdForm;
import com.hx.blog_v2.domain.form.BlogSenseForm;
import com.hx.blog_v2.domain.po.BlogExPO;
import com.hx.blog_v2.domain.po.BlogSensePO;
import com.hx.blog_v2.service.interf.BaseServiceImpl;
import com.hx.blog_v2.service.interf.BlogSenseService;
import com.hx.blog_v2.util.BizUtils;
import com.hx.blog_v2.context.CacheContext;
import com.hx.common.interf.common.Result;
import com.hx.common.util.ResultUtils;
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
        Result getSenseResult = senseDao.get(params);
        BlogSensePO po = null;
        boolean exists = (getSenseResult.isSuccess());
        if (exists) {
            po = (BlogSensePO) getSenseResult.getData();
        } else {
            po = new BlogSensePO(params.getBlogId(), params.getName(), params.getEmail(), BizUtils.getIp(), params.getSense());
        }
        if (params.getClicked().equals(po.getClicked())) {
            return ResultUtils.failed("wtf");
        }

        Result updateExResult = updateBlogEx(params);
        if (!updateExResult.isSuccess()) {
            return updateExResult;
        }
        po.setClicked(params.getClicked());

        if (!exists) {
            Result saveResult = senseDao.add(po);
            if (!saveResult.isSuccess()) {
                return saveResult;
            }
        }
        cacheContext.putBlogSense(params, po);
        return ResultUtils.success("success");
    }


    /**
     * 更新博客维护的额外的点赞信息
     *
     * @param params params
     * @return void
     * @author Jerry.X.He
     * @date 6/6/2017 8:53 PM
     * @since 1.0
     */
    private Result updateBlogEx(BlogSenseForm params) {
        Result getResult = blogExDao.get(new BeanIdForm(params.getBlogId()));
        if (!getResult.isSuccess()) {
            return getResult;
        }

        BlogExPO po = (BlogExPO) getResult.getData();
        po.incGoodCnt((params.getClicked() == 1) ? 1 : -1);
        cacheContext.putBlogEx(po);
        return ResultUtils.success("success");
    }


}
