package com.hx.blog_v2.service.front_resources;

import com.hx.blog_v2.context.CacheContext;
import com.hx.blog_v2.context.ConstantsContext;
import com.hx.blog_v2.dao.interf.LinkDao;
import com.hx.blog_v2.domain.POVOTransferUtils;
import com.hx.blog_v2.domain.form.common.BeanIdForm;
import com.hx.blog_v2.domain.form.front_resources.LinkSaveForm;
import com.hx.blog_v2.domain.po.front_resources.LinkPO;
import com.hx.blog_v2.domain.vo.front_resources.AdminLinkVO;
import com.hx.blog_v2.service.interf.BaseServiceImpl;
import com.hx.blog_v2.service.interf.front_resources.LinkService;
import com.hx.blog_v2.util.BizUtils;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.domain.BaseVO;
import com.hx.blog_v2.util.DateUtils;
import com.hx.blog_v2.util.ResultUtils;
import com.hx.common.interf.common.Result;
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
 * LinkServiceImpl
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
    @Autowired
    private ConstantsContext constantsContext;

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
    public Result list() {
        Map<String, LinkPO> allLinks = cacheContext.allLinks();
        List<AdminLinkVO> list = new ArrayList<>(allLinks.size());
        for (Map.Entry<String, LinkPO> entry : allLinks.entrySet()) {
            LinkPO link = entry.getValue();
            if (link.getEnable() == 1) {
                list.add(POVOTransferUtils.linkPO2AdminLinkVO(link));
            }
        }
        return ResultUtils.success(list);
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
        po.setUpdatedAt(DateUtils.format(new Date(), BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS));

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
        String updatedAt = DateUtils.format(new Date(), BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS);
        IQueryCriteria query = Criteria.eq("id", params.getId());
        IUpdateCriteria update = Criteria.set("deleted", "1").add("updated_at", updatedAt);
        Result result = linkDao.update(query, update);
        if (!result.isSuccess()) {
            return result;
        }

        return ResultUtils.success(params.getId());
    }

    @Override
    public Result reSort() {
        Map<String, LinkPO> links = cacheContext.allLinks();
        List<LinkPO> sortedLinks = BizUtils.resort(links);
        int sort = constantsContext.reSortStart;
        for (LinkPO link : sortedLinks) {
            boolean isSortChanged = sort != link.getSort();
            if (isSortChanged) {
                link.setSort(sort);
                linkDao.update(link);
            }
            sort += constantsContext.reSortOffset;
        }

        return ResultUtils.success("success");
    }


}
