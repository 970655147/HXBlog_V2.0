package com.hx.blog_v2.service;

import com.hx.blog_v2.dao.interf.BlogExDao;
import com.hx.blog_v2.domain.POVOTransferUtils;
import com.hx.blog_v2.domain.dto.SessionUser;
import com.hx.blog_v2.domain.extractor.ResourceTreeInfoExtractor;
import com.hx.blog_v2.domain.mapper.*;
import com.hx.blog_v2.domain.po.BlogTagPO;
import com.hx.blog_v2.domain.po.BlogTypePO;
import com.hx.blog_v2.domain.po.ResourcePO;
import com.hx.blog_v2.domain.vo.BlogVO;
import com.hx.blog_v2.domain.vo.CommentVO;
import com.hx.blog_v2.domain.vo.FacetByMonthVO;
import com.hx.blog_v2.domain.vo.ResourceVO;
import com.hx.blog_v2.service.interf.BaseServiceImpl;
import com.hx.blog_v2.service.interf.LinkService;
import com.hx.blog_v2.service.interf.SystemService;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.CacheContext;
import com.hx.blog_v2.util.WebContext;
import com.hx.common.interf.common.Result;
import com.hx.common.util.ResultUtils;
import com.hx.json.JSONObject;
import com.hx.log.alogrithm.tree.TreeUtils;
import com.hx.log.util.Log;
import com.hx.log.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * BlogServiceImpl
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 11:47 AM
 */
@Service
public class SystemServiceImpl extends BaseServiceImpl<Object> implements SystemService {

            @Autowired
            private CacheContext cacheContext;

            @Override
            public Result refreshConfig() {
                cacheContext.refresh();
        return ResultUtils.success("success");
    }

    @Override
    public Result statsSummary() {
        JSONObject data = new JSONObject().element("lastWeekInfo", cacheContext.allStatistics())
                .element("realTimeInfo", cacheContext.all5SecStatistics());
        return ResultUtils.success(data);
    }
}
