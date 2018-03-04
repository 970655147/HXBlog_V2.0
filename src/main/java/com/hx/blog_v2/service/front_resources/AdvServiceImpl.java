package com.hx.blog_v2.service.front_resources;

import com.baidu.ueditor.utils.Constants;
import com.hx.blog_v2.context.CacheContext;
import com.hx.blog_v2.context.ConstantsContext;
import com.hx.blog_v2.dao.interf.AdvDao;
import com.hx.blog_v2.domain.POVOTransferUtils;
import com.hx.blog_v2.domain.form.front_resources.AdvListForm;
import com.hx.blog_v2.domain.form.front_resources.AdvSaveForm;
import com.hx.blog_v2.domain.form.common.BeanIdForm;
import com.hx.blog_v2.domain.po.front_resources.AdvPO;
import com.hx.blog_v2.domain.vo.front_resources.AdminAdvVO;
import com.hx.blog_v2.domain.vo.front_resources.AdvVO;
import com.hx.blog_v2.service.interf.front_resources.AdvService;
import com.hx.blog_v2.service.interf.BaseServiceImpl;
import com.hx.blog_v2.util.BizUtils;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.DateUtils;
import com.hx.blog_v2.util.ResultUtils;
import com.hx.common.interf.common.Result;
import com.hx.common.str.AntPathMatcher;
import com.hx.common.str.interf.PathMatcher;
import com.hx.log.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * AdvServiceImpl
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 3/3/2018 5:46 PM
 */
@Service
public class AdvServiceImpl extends BaseServiceImpl<AdvPO> implements AdvService {

    @Autowired
    private AdvDao advDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private CacheContext cacheContext;
    @Autowired
    private BlogConstants constants;
    @Autowired
    private ConstantsContext constantsContext;

    /**
     * pathMatcher
     */
    private PathMatcher antPathMatcher = new AntPathMatcher();
    /**
     * 多个 pattern 之间的分隔符
     */
    public static String MULTI_PATTERN_SEP = Constants.MULTI_PATH_SEPRATOR;


    @Override
    public Result add(AdvSaveForm params) {
        AdvPO poByName = BizUtils.findByLogisticId(cacheContext.allAdvById(), params.getName());
        if (poByName != null) {
            return ResultUtils.failed("该广告已经存在 !");
        }

        AdvPO po = new AdvPO(params.getName(), params.getProvider(), params.getPathMatch(), params.getType(),
                params.getParams(), params.getSort());
        Result result = advDao.add(po);
        if (!result.isSuccess()) {
            return result;
        }

        cacheContext.putAdvById(po);
        return ResultUtils.success(po.getId());
    }

    @Override
    public Result list(AdvListForm params) {
        Map<String, AdvPO> allAdvInfo = cacheContext.allAdvById();
        String requestUri = BizUtils.getRequestUri(params.getUrl());

        List<AdvVO> result = new ArrayList<>(allAdvInfo.size());
        for(Map.Entry<String, AdvPO> entry : allAdvInfo.entrySet()) {
            AdvPO advInfo = entry.getValue();
            if(advMatch(advInfo, requestUri)) {
                result.add(POVOTransferUtils.advPO2AdvVO(advInfo));
            }
        }
        return ResultUtils.success(result);
    }

    @Override
    public Result adminList() {
        Map<String, AdvPO> advs = cacheContext.allAdvById();
        List<AdminAdvVO> all = new ArrayList<>(advs.size());
        for (Map.Entry<String, AdvPO> entry : advs.entrySet()) {
            all.add(POVOTransferUtils.advPO2AdminAdvVO(entry.getValue()));
        }

        return ResultUtils.success(all);
    }

    @Override
    public Result update(AdvSaveForm params) {
        AdvPO po = cacheContext.allAdvById().get(params.getId());
        if (po == null) {
            return ResultUtils.failed("该广告不存在 !");
        }
        AdvPO poByName = BizUtils.findByLogisticId(cacheContext.allAdvById(), params.getName());
        if ((poByName != null) && (!po.getId().equals(poByName.getId()))) {
            return ResultUtils.failed("该广告已经存在 !");
        }

        po.setName(params.getName());
        po.setProvider(params.getProvider());
        po.setPathMatch(params.getPathMatch());
        po.setType(params.getType());
        po.setParams(params.getParams());
        po.setSort(params.getSort());
        po.setUpdatedAt(DateUtils.format(new Date(), BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS));
        Result result = advDao.update(po);
        if (!result.isSuccess()) {
            return result;
        }

        cacheContext.putAdvById(po);
        return ResultUtils.success(params.getId());
    }

    @Override
    public Result remove(BeanIdForm params) {
        AdvPO po = cacheContext.allAdvById().get(params.getId());
        if (po == null) {
            return ResultUtils.failed("该广告不存在 !");
        }

        po.setUpdatedAt(DateUtils.format(new Date(), BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS));
        po.setDeleted(1);
        Result result = advDao.update(po);
        if (!result.isSuccess()) {
            return result;
        }

        cacheContext.removeAdvById(params.getId());
        return ResultUtils.success(params.getId());
    }

    @Override
    public Result reSort() {
        Map<String, AdvPO> advs = cacheContext.allAdvById();
        List<AdvPO> advTags = BizUtils.resort(advs);
        int sort = constantsContext.reSortStart;
        for (AdvPO adv : advTags) {
            boolean isSortChanged = sort != adv.getSort();
            if (isSortChanged) {
                adv.setSort(sort);
                advDao.update(adv);
            }
            sort += constantsContext.reSortOffset;
        }

        return ResultUtils.success("success");
    }

    // -------------------- 辅助方法 --------------------------

    /**
     * 判断当前请求是否可以获取当前广告
     *
     * @param advInfo advInfo
     * @param requestUri requestUri
     * @return boolean
     * @author Jerry.X.He
     * @date 3/3/2018 6:15 PM
     * @since 1.0
     */
    private boolean advMatch(AdvPO advInfo, String requestUri) {
        String pathMatch = advInfo.getPathMatch();
        if(Tools.isEmpty(pathMatch)) {
            return false;
        }

        String[] allPattern = pathMatch.split(MULTI_PATTERN_SEP);
        for(String pattern : allPattern) {
            if(antPathMatcher.match(pattern, requestUri)) {
                return true;
            }
        }
        return false;
    }

}
