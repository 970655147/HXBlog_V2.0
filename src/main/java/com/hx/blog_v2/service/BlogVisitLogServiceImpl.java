package com.hx.blog_v2.service;

import com.hx.blog_v2.dao.interf.BlogVisitLogDao;
import com.hx.blog_v2.dao.interf.LinkDao;
import com.hx.blog_v2.domain.POVOTransferUtils;
import com.hx.blog_v2.domain.form.BeanIdForm;
import com.hx.blog_v2.domain.form.LinkSaveForm;
import com.hx.blog_v2.domain.po.BlogVisitLogPO;
import com.hx.blog_v2.domain.po.LinkPO;
import com.hx.blog_v2.domain.vo.AdminLinkVO;
import com.hx.blog_v2.service.interf.BaseServiceImpl;
import com.hx.blog_v2.service.interf.BlogVisitLogService;
import com.hx.blog_v2.service.interf.LinkService;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.CacheContext;
import com.hx.blog_v2.util.DateUtils;
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
public class BlogVisitLogServiceImpl extends BaseServiceImpl<BlogVisitLogPO> implements BlogVisitLogService {

    @Autowired
    private BlogVisitLogDao visitLogDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private CacheContext cacheContext;



}
