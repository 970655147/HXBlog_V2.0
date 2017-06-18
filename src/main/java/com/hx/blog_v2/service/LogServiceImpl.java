package com.hx.blog_v2.service;

import com.hx.blog_v2.context.ConstantsContext;
import com.hx.blog_v2.domain.form.LogSearchForm;
import com.hx.blog_v2.domain.mapper.ExceptionLogVOMapper;
import com.hx.blog_v2.domain.mapper.OneIntMapper;
import com.hx.blog_v2.domain.mapper.RequestLogVOMapper;
import com.hx.blog_v2.domain.vo.ExceptionLogVO;
import com.hx.blog_v2.domain.vo.RequestLogVO;
import com.hx.blog_v2.service.interf.BaseServiceImpl;
import com.hx.blog_v2.service.interf.LogService;
import com.hx.blog_v2.util.ResultUtils;
import com.hx.blog_v2.util.SqlUtils;
import com.hx.common.interf.common.Page;
import com.hx.common.interf.common.Result;
import com.hx.log.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * BlogServiceImpl
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 11:47 AM
 */
@Service
public class LogServiceImpl extends BaseServiceImpl<Object> implements LogService {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ConstantsContext constantsContext;

    @Override
    public Result requestLogList(LogSearchForm params, Page<RequestLogVO> page) {
        String selectSql = " select * from request_log where 1 = 1 ";
        String selectSqlSuffix = " order by created_at desc limit ?, ?";
        String countSql = " select count(*) as totalRecord from request_log where 1 = 1 ";

        StringBuilder condSqlSb = new StringBuilder();
        List<Object> selectParams = new ArrayList<>(3);
        encapQueryForAdminList(params, condSqlSb, selectParams);
        String condSql = condSqlSb.toString();
        Object[] countParams = selectParams.toArray();
        selectParams.add(page.recordOffset());
        selectParams.add(page.getPageSize());

        List<RequestLogVO> list = jdbcTemplate.query(selectSql + condSql + selectSqlSuffix, selectParams.toArray(), new RequestLogVOMapper());
        Integer totalRecord = jdbcTemplate.queryForObject(countSql + condSql, countParams, new OneIntMapper("totalRecord"));
        page.setList(list);
        page.setTotalRecord(totalRecord);
        return ResultUtils.success(page);
    }

    @Override
    public Result exceptionLogList(LogSearchForm params, Page<ExceptionLogVO> page) {
        String selectSql = " select * from exception_log where 1 = 1 ";
        String selectSqlSuffix = " order by created_at desc limit ?, ?";
        String countSql = " select count(*) as totalRecord from exception_log where 1 = 1 ";

        StringBuilder condSqlSb = new StringBuilder();
        List<Object> selectParams = new ArrayList<>(3);
        encapQueryForAdminList(params, condSqlSb, selectParams);
        if (params.getMsg() != null) {
            condSqlSb.append(" and msg like ? ");
            selectParams.add(SqlUtils.wrapWildcard(params.getMsg()));
        }
        String condSql = condSqlSb.toString();
        Object[] countParams = selectParams.toArray();
        selectParams.add(page.recordOffset());
        selectParams.add(page.getPageSize());

        List<ExceptionLogVO> list = jdbcTemplate.query(selectSql + condSql + selectSqlSuffix, selectParams.toArray(), new ExceptionLogVOMapper());
        Integer totalRecord = jdbcTemplate.queryForObject(countSql + condSql, countParams, new OneIntMapper("totalRecord"));
        page.setList(list);
        page.setTotalRecord(totalRecord);
        return ResultUtils.success(page);
    }


    // -------------------- 辅助方法 --------------------------

    /**
     * 封装查询条件, 以及查询参数 [adminList]
     *
     * @param params        params
     * @param condSqlSb     condSqlSb
     * @param sqlParamsList sqlParamsList
     * @return void
     * @author Jerry.X.He
     * @date 6/4/2017 5:14 PM
     * @since 1.0
     */
    private void encapQueryForAdminList(LogSearchForm params, StringBuilder condSqlSb, List<Object> sqlParamsList) {
        if (!Tools.isEmpty(params.getUrl())) {
            condSqlSb.append(" and url like ? ");
            sqlParamsList.add(SqlUtils.wrapWildcard(params.getUrl()));
        }
        if (!Tools.isEmpty(params.getHandler())) {
            condSqlSb.append(" and handler like ? ");
            sqlParamsList.add(SqlUtils.wrapWildcard(params.getHandler()));
        }
        if (!Tools.isEmpty(params.getParams())) {
            condSqlSb.append(" and params like ? ");
            sqlParamsList.add(SqlUtils.wrapWildcard(params.getParams()));
        }
        if (!Tools.isEmpty(params.getName())) {
            condSqlSb.append(" and name like ? ");
            sqlParamsList.add(SqlUtils.wrapWildcard(params.getName()));
        }
        if (!Tools.isEmpty(params.getEmail())) {
            condSqlSb.append(" and email like ? ");
            sqlParamsList.add(SqlUtils.wrapWildcard(params.getEmail()));
        }
        if (params.getIsSystemUser() != null) {
            condSqlSb.append(" and is_system_user like ? ");
            sqlParamsList.add(params.getIsSystemUser());
        }
        if (params.getRequestIp() != null) {
            condSqlSb.append(" and request_ip like ? ");
            sqlParamsList.add(SqlUtils.wrapWildcard(params.getRequestIp()));
        }
    }

}
