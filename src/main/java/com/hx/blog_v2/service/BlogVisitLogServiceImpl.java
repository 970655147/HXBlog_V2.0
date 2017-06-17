package com.hx.blog_v2.service;

import com.hx.blog_v2.dao.interf.BlogVisitLogDao;
import com.hx.blog_v2.domain.po.BlogVisitLogPO;
import com.hx.blog_v2.service.interf.BaseServiceImpl;
import com.hx.blog_v2.service.interf.BlogVisitLogService;
import com.hx.blog_v2.context.CacheContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

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
