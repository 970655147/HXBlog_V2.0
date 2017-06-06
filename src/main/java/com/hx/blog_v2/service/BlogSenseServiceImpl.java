package com.hx.blog_v2.service;

import com.hx.blog_v2.dao.interf.BlogExDao;
import com.hx.blog_v2.dao.interf.BlogSenseDao;
import com.hx.blog_v2.domain.form.BlogSenseForm;
import com.hx.blog_v2.domain.po.BlogExPO;
import com.hx.blog_v2.domain.po.BlogSensePO;
import com.hx.blog_v2.service.interf.BaseServiceImpl;
import com.hx.blog_v2.service.interf.BlogSenseService;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.CacheContext;
import com.hx.common.interf.common.Result;
import com.hx.common.util.ResultUtils;
import com.hx.log.util.Log;
import com.hx.log.util.Tools;
import com.hx.mongo.criteria.Criteria;
import com.hx.mongo.criteria.interf.MultiCriteriaQueryCriteria;
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
        Boolean sense = cacheContext.getBlogSense(params);
        if (sense != null) {
            if (sense.equals(params.isClicked())) {
                Log.err("有小伙子在注入了 ??");
            } else {
                Result updateExResult = updateBlogEx(params);
                if(! updateExResult.isSuccess()) {
                    return updateExResult;
                }
                cacheContext.putBlogSense(params, params.isClicked());
            }
            return ResultUtils.success("success");
        }

        MultiCriteriaQueryCriteria query = Criteria.and(Criteria.eq("blog_id", params.getBlogId()))
                .add(Criteria.eq("name", params.getName())).add(Criteria.eq("sense", params.getSense()));
        if (!Tools.isEmpty(params.getEmail())) {
            query.add(Criteria.eq("email", params.getEmail()));
        }
        try {
            BlogSensePO po = senseDao.findOne(query, BlogConstants.LOAD_ALL_CONFIG);
            if (po == null) {
                po = new BlogSensePO(params.getBlogId(), params.getName(), params.getEmail(), params.getSense());
            }
            if (params.isClicked().equals(po.getClicked() == 1)) {
                Log.err("有小伙子在注入了 ??");
            } else {
                Result updateExResult = updateBlogEx(params);
                if(! updateExResult.isSuccess()) {
                    return updateExResult;
                }

                po.setClicked(params.isClicked() ? 1 : 0);
                senseDao.insertOne(po, BlogConstants.ADD_BEAN_CONFIG);
                cacheContext.putBlogSense(params, params.isClicked());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.failed(Tools.errorMsg(e));
        }

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
        BlogExPO po = cacheContext.getBlogEx(params.getBlogId());
        if (po != null) {
            po.incGoodCnt(params.isClicked() ? 1 : -1);
            return ResultUtils.success("success");
        }

        try {
            po = blogExDao.findOne(Criteria.and(Criteria.eq("blog_id", params.getBlogId())),
                    BlogConstants.LOAD_ALL_CONFIG);
            if (po == null) {
                return ResultUtils.failed("没有对应的博客");
            }
            po.incGoodCnt(params.isClicked() ? 1 : -1);
            cacheContext.putBlogEx(po);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.failed(Tools.errorMsg(e));
        }

        return ResultUtils.success("success");
    }


}
