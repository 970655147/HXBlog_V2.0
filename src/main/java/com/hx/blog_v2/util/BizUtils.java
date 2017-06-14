package com.hx.blog_v2.util;

import com.hx.attr_handler.util.AttrHandlerConstants;
import com.hx.attr_handler.util.AttrHandlerUtils;
import com.hx.blog_v2.domain.dto.SessionUser;
import com.hx.blog_v2.domain.dto.StatisticsInfo;
import com.hx.blog_v2.domain.form.interf.UserInfoExtractor;
import com.hx.blog_v2.domain.mapper.ToMapMapper;
import com.hx.json.JSONArray;
import com.hx.json.JSONObject;
import com.hx.log.json.JSONUtils;
import com.hx.log.util.Tools;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.servlet.http.HttpServletRequest;
import java.net.URL;
import java.util.*;

/**
 * 处理业务的相关通用的方法
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/5/2017 8:44 PM
 */
public final class BizUtils {

    // disable constructor
    private BizUtils() {
        Tools.assert0("can't instantiate !");
    }

    /**
     * 如果可以更新用户信息的话, 更新用户信息
     *
     * @param user   user
     * @param params params
     * @return com.hx.blog_v2.domain.dto.SessionUser
     * @author Jerry.X.He
     * @date 6/5/2017 8:50 PM
     * @since 1.0
     */
    public static SessionUser updateUserIfBe(SessionUser user, UserInfoExtractor params) {
        if (user == null) {
            user = new SessionUser(params.getName(), params.getEmail(), params.getHeadImgUrl(),
                    "guest", "guest", false);
        } else {
            if (!user.isSystemUser()) {
                user.setName(params.getName());
                user.setEmail(params.getEmail());
            }
            user.setHeadImgUrl(params.getHeadImgUrl());
        }

        return user;
    }

    /**
     * 获取当前请求的ip
     *
     * @return java.lang.String
     * @author Jerry.X.He
     * @date 6/9/2017 9:24 PM
     * @since 1.0
     */
    public static String getIp() {
        HttpServletRequest req = WebContext.getRequest();

        String ip = req.getHeader("X-Forwarded-For");
        if (Tools.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = req.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = req.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = req.getHeader("HTTP_CLIENT_IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = req.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = req.getRemoteAddr();
            }
        } else if (ip.length() > 15) {
            String[] ips = ip.split(",");
            for (int index = 0; index < ips.length; index++) {
                String strIp = ips[index];
                if (!("unknown".equalsIgnoreCase(strIp))) {
                    ip = strIp;
                    break;
                }
            }
        }

        return ip;
    }

    /**
     * http://ip.taobao.com//service/getIpInfo.php?ip=118.113.4.103
     * 查询ip
     *
     * @return java.lang.String
     * @author Jerry.X.He
     * @date 6/10/2017 7:46 PM
     * @since 1.0
     */
    public static String getIpAddr(String ip) {
        if ("0:0:0:0:0:0:0:1".equals(ip) || ip.startsWith("127")) {
            return " 环路 ip ";
        }
        if (ip.startsWith("192") || ip.startsWith("172")) {
            return " 局域网 ip ";
        }

        try {
            String urlStr = AttrHandlerUtils.handlerParse(BlogConstants.getInstance().ipAddrReqLogPattern,
                    AttrHandlerConstants.HANDLER).handle(ip);
            URL url = new URL(urlStr);
            String resp = Tools.getContent(url.openConnection().getInputStream());
            JSONObject ipInfo = JSONObject.fromObject(resp);

            if ("0".equalsIgnoreCase(ipInfo.optString("code"))) {
                JSONObject formatedIpInfo = JSONUtils.filter(ipInfo.getJSONObject("data"),
                        Tools.asSet("country_id", "area_id", "region_id", "city_id", "county_id"));
                return formatedIpInfo.toString();
            } else {
                return ipInfo.optString("data", "unknown !");
            }
        } catch (Exception e) {
            return "unknown";
        }
    }

    /**
     * 判断给定的 ip 是否是本地ip
     *
     * @param ip ip
     * @return boolean
     * @author Jerry.X.He
     * @date 6/10/2017 8:42 PM
     * @since 1.0
     */
    public static boolean isLocalIp(String ip) {
        if ("0:0:0:0:0:0:0:1".equals(ip) || ip.startsWith("127")) {
            return true;
        }
        if (ip.startsWith("192") || ip.startsWith("172")) {
            return true;
        }

        return false;
    }

    /**
     * 获取给定的请求的请求头信息
     *
     * @param req req
     * @return java.lang.String
     * @author Jerry.X.He
     * @date 6/11/2017 8:19 PM
     * @since 1.0
     */
    public static JSONObject getHeaderInfo(HttpServletRequest req) {
        Enumeration<String> names = req.getHeaderNames();
        JSONObject headers = new JSONObject();
        while (names.hasMoreElements()) {
            String key = names.nextElement();
            headers.put(key, req.getHeader(key));
        }

        return headers;
    }

    /**
     * 获取异常的详细信息
     *
     * @param e e
     * @return com.hx.json.JSONArray
     * @author Jerry.X.He
     * @date 6/11/2017 8:25 PM
     * @since 1.0
     */
    public static JSONArray getExceptionInfo(Throwable e) {
        if (e == null) {
            return JSONArray.NULL_JSON_ARRAY;
        }

        JSONArray result = new JSONArray();
        result.add(e.getMessage());
        result.add(e.getLocalizedMessage());

        StackTraceElement[] stackTraces = e.getStackTrace();
        int exceptionLogMaxStackTrace = BlogConstants.getInstance().exceptionLogMaxStackTrace;
        int min = (stackTraces.length - exceptionLogMaxStackTrace) > 0 ? stackTraces.length - exceptionLogMaxStackTrace : 0;
        for (int i = stackTraces.length - 1; i >= min; i--) {
            result.add(stackTraces[i].toString());
        }
        return result;
    }

    /**
     * 收集 最近 dayOff 天的统计信息
     *
     * @param dayOff dayOff
     * @return void
     * @author Jerry.X.He
     * @date 6/10/2017 11:07 PM
     * @since 1.0
     */
    public static List<StatisticsInfo> collectRecentlyStatisticsInfo(JdbcTemplate jdbcTemplate, int dayOff) {
        Date now = new Date();
        Date oldestDay = DateUtils.addDay(now, -dayOff + 1);
        String begin = DateUtils.formate(DateUtils.beginOfDay(oldestDay), BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS);
        String end = DateUtils.formate(now, BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS);

        String statsViewCntSql = " select created_at_day as date, count(request_ip) as viewCount, count(distinct(request_ip)) as uniqueViewCount from blog_visit_log where created_at >= ? and created_at <= ? group by created_at_day ";
        String statsSenseCntSql = " select date_format(created_at, '%Y-%m-%d') as date, count(if (clicked = 1, true, null)) as goodCnt, count(if (clicked = 0, true, null)) as notGoodCnt from blog_sense where sense = 'good' and created_at >= ? and created_at <= ? group by DATE_FORMAT(created_at, '%Y-%m-%d') ";
        String statsBlogCntSql = " select date_format(created_at, '%Y-%m-%d') as date, count(*) as blogCnt from blog where deleted = 0 and created_at >= ? and created_at <= ? group by date_format(created_at, '%Y-%m-%d') ";
        String statsCommentCntSql = " select date_format(created_at, '%Y-%m-%d') as date, count(*) as commentCnt from blog_comment where deleted = 0 and created_at >= ? and created_at <= ? group by date_format(created_at, '%Y-%m-%d') ";
        String dateKey = "date";

        Object[] params = new Object[]{begin, end};
        RowMapper<Map<String, Object>> rowMapper = new ToMapMapper();
        List<Map<String, Object>> viewCnts = jdbcTemplate.query(statsViewCntSql, params, rowMapper);
        List<Map<String, Object>> senseCnts = jdbcTemplate.query(statsSenseCntSql, params, rowMapper);
        List<Map<String, Object>> blogCnts = jdbcTemplate.query(statsBlogCntSql, params, rowMapper);
        List<Map<String, Object>> commentCnts = jdbcTemplate.query(statsCommentCntSql, params, rowMapper);

        List<StatisticsInfo> result = new ArrayList<>(dayOff);
        for (int off = 0; off < dayOff; off++) {
            String dayStr = DateUtils.formate(DateUtils.addDay(oldestDay, off), BlogConstants.FORMAT_YYYY_MM_DD);
            Map<String, Object> viewCntMap = locateByDate(viewCnts, "created_at_day", dayStr);
            Map<String, Object> senseCntMap = locateByDate(senseCnts, dateKey, dayStr);
            Map<String, Object> blogCntMap = locateByDate(blogCnts, dateKey, dayStr);
            Map<String, Object> commentCntMap = locateByDate(commentCnts, dateKey, dayStr);

            StatisticsInfo dayInfo = new StatisticsInfo();
            encapStatisticsInfo(viewCntMap, senseCntMap, blogCntMap, commentCntMap, dayInfo);
            result.add(dayInfo);
        }

        return result;
    }

    /**
     * 收集 最近 dayOff 天的统计信息
     *
     * @param jdbcTemplate jdbcTemplate
     * @return void
     * @author Jerry.X.He
     * @date 6/10/2017 11:07 PM
     * @since 1.0
     */
    public static StatisticsInfo collectSumStatisticsInfo(JdbcTemplate jdbcTemplate) {
        String yesterday = DateUtils.formate(DateUtils.addDay(new Date(), -1), BlogConstants.FORMAT_YYYY_MM_DD);
        String statsViewCntSql = " select created_at_day as date, count(request_ip) as viewCount, count(distinct(request_ip)) as uniqueViewCount from blog_visit_log where created_at_day <= ? ";
        String statsSenseCntSql = " select count(if (clicked = 1, true, null)) as goodCnt, count(if (clicked = 0, true, null)) as notGoodCnt from blog_sense where sense = 'good' and date_format(created_at, '%y-%m-%d') <= ? ";
        String statsBlogCntSql = " select count(*) as blogCnt from blog where deleted = 0 and date_format(created_at, '%y-%m-%d') <= ? ";
        String statsCommentCntSql = " select count(*) as commentCnt from blog_comment where deleted = 0 and date_format(created_at, '%y-%m-%d') <= ? ";

        Object[] params = new Object[]{yesterday};
        RowMapper<Map<String, Object>> rowMapper = new ToMapMapper();
        Map<String, Object> viewCntMap = jdbcTemplate.queryForMap(statsViewCntSql, params);
        Map<String, Object> senseCntMap = jdbcTemplate.queryForMap(statsSenseCntSql, params);
        Map<String, Object> blogCntMap = jdbcTemplate.queryForMap(statsBlogCntSql, params);
        Map<String, Object> commentCntMap = jdbcTemplate.queryForMap(statsCommentCntSql, params);

        StatisticsInfo result = new StatisticsInfo();
        encapStatisticsInfo(viewCntMap, senseCntMap, blogCntMap, commentCntMap, result);
        return result;
    }

    /**
     * 获取所有的 value, 并排序
     *
     * @param map map
     * @return java.util.List<T>
     * @author Jerry.X.He
     * @date 6/10/2017 4:33 PM
     * @since 1.0
     */
    public static <T extends Comparable<T>> List<T> resort(Map<String, T> map) {
        List<T> all = new ArrayList<>(map.size());
        for (Map.Entry<String, T> entry : map.entrySet()) {
            all.add(entry.getValue());
        }
        Collections.sort(all);
        return all;
    }

    /**
     * 根据给定的日期定位 到日期对应的记录
     *
     * @param rsByDay rsByDay
     * @param dateKey dateKey
     * @param date    date
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @author Jerry.X.He
     * @date 6/10/2017 11:34 PM
     * @since 1.0
     */
    private static Map<String, Object> locateByDate(List<Map<String, Object>> rsByDay, String dateKey, String date) {
        for (Map<String, Object> resultSet : rsByDay) {
            if (date.equals(Tools.optString(resultSet, dateKey, ""))) {
                return resultSet;
            }
        }
        return null;
    }

    /**
     * 封装给定的数据 到StatisticsInfo
     *
     * @param viewCntMap    viewCntMap
     * @param senseCntMap   senseCntMap
     * @param blogCntMap    blogCntMap
     * @param commentCntMap commentCntMap
     * @param dayInfo       dayInfo
     * @return void
     * @author Jerry.X.He
     * @date 6/10/2017 11:44 PM
     * @since 1.0
     */
    private static void encapStatisticsInfo(Map<String, Object> viewCntMap, Map<String, Object> senseCntMap,
                                            Map<String, Object> blogCntMap, Map<String, Object> commentCntMap,
                                            StatisticsInfo dayInfo) {
        if (!Tools.isEmpty(viewCntMap)) {
            dayInfo.setViewCnt(Tools.optInt(viewCntMap, "viewCount", 0));
            dayInfo.setDayFlushViewCnt(Tools.optInt(viewCntMap, "uniqueViewCount", 0));
        }
        if (!Tools.isEmpty(senseCntMap)) {
            dayInfo.setGoodCnt(Tools.optInt(senseCntMap, "goodCnt", 0));
            dayInfo.setNotGoodCnt(Tools.optInt(senseCntMap, "notGoodCnt", 0));
        }
        if (!Tools.isEmpty(blogCntMap)) {
            dayInfo.setBlogCnt(Tools.optInt(blogCntMap, "blogCnt", 0));
        }
        if (!Tools.isEmpty(commentCntMap)) {
            dayInfo.setCommentCnt(Tools.optInt(commentCntMap, "commentCnt", 0));
        }
    }


}
