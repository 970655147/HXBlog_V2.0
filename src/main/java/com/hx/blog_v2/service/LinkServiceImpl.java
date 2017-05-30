package com.hx.blog_v2.service;

import com.hx.blog_v2.dao.interf.LinkDao;
import com.hx.blog_v2.domain.POVOTransferUtils;
import com.hx.blog_v2.domain.form.BeanIdForm;
import com.hx.blog_v2.domain.form.LinkSaveForm;
import com.hx.blog_v2.domain.mapper.AdminLinkVOMapper;
import com.hx.blog_v2.domain.po.LinkPO;
import com.hx.blog_v2.domain.vo.AdminLinkVO;
import com.hx.blog_v2.service.interf.BaseServiceImpl;
import com.hx.blog_v2.service.interf.LinkService;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.CacheContext;
import com.hx.blog_v2.util.DateUtils;
import com.hx.common.interf.common.Page;
import com.hx.common.interf.common.Result;
import com.hx.common.util.ResultUtils;
import com.hx.log.util.Tools;
import com.hx.mongo.criteria.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private CacheContext cacheContext;

    @Override
    public Result add(LinkSaveForm params) {
        LinkPO po = new LinkPO(params.getName(), params.getDesc(), params.getUrl(), params.getSort(), params.getEnable());

        try {
            linkDao.save(po, BlogConstants.ADD_BEAN_CONFIG);
            cacheContext.allLinks().put(po.getId(), po);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.failed(Tools.errorMsg(e));
        }
        return ResultUtils.success(po.getId());
    }

    @Override
    public Result adminList() {
        Map<String, LinkPO> allLinks = cacheContext.allLinks();

        List<AdminLinkVO> list = new ArrayList<>(allLinks.size());
        for(Map.Entry<String, LinkPO> entry : allLinks.entrySet()) {
            LinkPO link = entry.getValue();
            if(link.getEnable() != 0) {
                list.add(POVOTransferUtils.linkPO2AdminLinkVO(link));
            }
        }
        return ResultUtils.success(list);
    }

    @Override
    public Result update(LinkSaveForm params) {
        LinkPO po = new LinkPO(params.getName(), params.getDesc(), params.getUrl(), params.getSort(), params.getEnable());

        po.setId(params.getId());
        po.setUpdatedAt(DateUtils.formate(new Date(), BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS));
        try {
            long modified = linkDao.updateById(po, BlogConstants.UPDATE_BEAN_CONFIG)
                    .getModifiedCount();
            if (modified == 0) {
                return ResultUtils.failed("没有找到对应的心情 !");
            }
            cacheContext.allLinks().put(po.getId(), po);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.failed(Tools.errorMsg(e));
        }
        return ResultUtils.success(po.getId());
    }

    @Override
    public Result remove(BeanIdForm params) {
        String updatedAt = DateUtils.formate(new Date(), BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS);
        try {
            long deleted = linkDao.updateOne(Criteria.eq("id", params.getId()),
                    Criteria.set("deleted", "1").add("updated_at", updatedAt)
            ).getModifiedCount();
            if (deleted == 0) {
                return ResultUtils.failed("连接[" + params.getId() + "]不存在 !");
            }
            cacheContext.allLinks().remove(params.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.failed(Tools.errorMsg(e));
        }
        return ResultUtils.success(params.getId());
    }

}
