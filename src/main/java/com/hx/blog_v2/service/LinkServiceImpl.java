package com.hx.blog_v2.service;

import com.hx.blog_v2.context.CacheContext;
import com.hx.blog_v2.dao.interf.LinkDao;
import com.hx.blog_v2.domain.POVOTransferUtils;
import com.hx.blog_v2.domain.form.BeanIdForm;
import com.hx.blog_v2.domain.form.LinkSaveForm;
import com.hx.blog_v2.domain.po.LinkPO;
import com.hx.blog_v2.domain.vo.AdminLinkVO;
import com.hx.blog_v2.service.interf.BaseServiceImpl;
import com.hx.blog_v2.service.interf.LinkService;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.DateUtils;
import com.hx.common.interf.common.Result;
import com.hx.common.util.ResultUtils;
import com.hx.mongo.criteria.Criteria;
import com.hx.mongo.criteria.interf.IQueryCriteria;
import com.hx.mongo.criteria.interf.IUpdateCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * BlogServiceImpl
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 11:47 AM
 */
@Service
public class LinkServiceImpl extends BaseServiceImpl<LinkPO> implements LinkService {

    @Autowired
    private LinkDao linkDao;
    @Autowired
    private CacheContext cacheContext;

    @Override
    public Result add(LinkSaveForm params) {
        LinkPO po = new LinkPO(params.getName(), params.getDesc(), params.getUrl(), params.getSort(), params.getEnable());

        Result result = linkDao.add(po);
        if (!result.isSuccess()) {
            return result;
        }
        cacheContext.putLink(po);
        return ResultUtils.success(po.getId());
    }

    @Override
    public Result adminList() {
        Map<String, LinkPO> allLinks = cacheContext.allLinks();

        List<AdminLinkVO> list = new ArrayList<>(allLinks.size());
        for (Map.Entry<String, LinkPO> entry : allLinks.entrySet()) {
            LinkPO link = entry.getValue();
            list.add(POVOTransferUtils.linkPO2AdminLinkVO(link));
        }
        return ResultUtils.success(list);
    }

    @Override
    public Result update(LinkSaveForm params) {
        LinkPO po = new LinkPO(params.getName(), params.getDesc(), params.getUrl(), params.getSort(), params.getEnable());

        po.setId(params.getId());
        po.setUpdatedAt(DateUtils.formate(new Date(), BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS));
        Result result = linkDao.update(po);
        if (!result.isSuccess()) {
            return result;
        }
        cacheContext.putLink(po);
        return ResultUtils.success(po.getId());
    }

    @Override
    public Result remove(BeanIdForm params) {
        LinkPO po = cacheContext.link(params.getId());
        if (po == null) {
            return ResultUtils.failed("没有对应的链接 !");
        }

        cacheContext.allLinks().remove(params.getId());
        String updatedAt = DateUtils.formate(new Date(), BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS);
        IQueryCriteria query = Criteria.eq("id", params.getId());
        IUpdateCriteria update = Criteria.set("deleted", "1").add("updated_at", updatedAt);
        Result result = linkDao.update(query, update);
        if (!result.isSuccess()) {
            return result;
        }

        return ResultUtils.success(params.getId());
    }

}
