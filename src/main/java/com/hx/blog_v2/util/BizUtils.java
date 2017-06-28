package com.hx.blog_v2.util;

import com.hx.attr_handler.util.AttrHandlerConstants;
import com.hx.attr_handler.util.AttrHandlerUtils;
import com.hx.blog_v2.context.WebContext;
import com.hx.blog_v2.domain.dto.SessionUser;
import com.hx.blog_v2.domain.dto.StatisticsInfo;
import com.hx.blog_v2.domain.dto.StringIntPair;
import com.hx.blog_v2.domain.form.interf.UserInfoExtractor;
import com.hx.blog_v2.domain.mapper.StringIntPairMapper;
import com.hx.blog_v2.domain.mapper.ToMapMapper;
import com.hx.blog_v2.domain.po.interf.LogisticalId;
import com.hx.common.str.AntPathMatcher;
import com.hx.common.str.interf.PathMatcher;
import com.hx.json.JSONArray;
import com.hx.json.JSONObject;
import com.hx.log.json.JSONUtils;
import com.hx.log.util.Log;
import com.hx.log.util.Tools;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 处理业务的相关通用的方法
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/5/2017 8:44 PM
 */
public final class BizUtils {

    /**
     * pathMatcher
     */
    private static final PathMatcher ANT_PATH_MATCHER = new AntPathMatcher();

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
        if (!user.isSystemUser()) {
            user.setName(params.getName());
            user.setEmail(params.getEmail());
        }
        user.setHeadImgUrl(params.getHeadImgUrl());

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
        String statsSenseCntSql = " select date_format(created_at, '%Y-%m-%d') as date, count(if (score > 3, true, null)) as goodCnt, count(if (score <= 3, true, null)) as notGoodCnt from blog_sense where sense = '1' and created_at >= ? and created_at <= ? group by DATE_FORMAT(created_at, '%Y-%m-%d') ";
        String statsBlogCntSql = " select date_format(created_at, '%Y-%m-%d') as date, count(*) as blogCnt from blog where deleted = 0 and created_at >= ? and created_at <= ? group by date_format(created_at, '%Y-%m-%d') ";
        String statsCommentCntSql = " select date_format(created_at, '%Y-%m-%d') as date, count(*) as commentCnt from blog_comment where deleted = 0 and created_at >= ? and created_at <= ? group by date_format(created_at, '%Y-%m-%d') ";
        String requestLogCntSql = " select date_format(created_at, '%Y-%m-%d') as date, count(*) as requestCnt from request_log where created_at >= ? and created_at <= ? group by date_format(created_at, '%Y-%m-%d') ";
        String exceptionLogCntSql = " select date_format(created_at, '%Y-%m-%d') as date, count(*) as exceptionCnt from exception_log where created_at >= ? and created_at <= ? group by date_format(created_at, '%Y-%m-%d') ";
        String dateKey = "date";

        Object[] params = new Object[]{begin, end};
        RowMapper<Map<String, Object>> rowMapper = new ToMapMapper();
        List<Map<String, Object>> viewCnts = jdbcTemplate.query(statsViewCntSql, params, rowMapper);
        List<Map<String, Object>> senseCnts = jdbcTemplate.query(statsSenseCntSql, params, rowMapper);
        List<Map<String, Object>> blogCnts = jdbcTemplate.query(statsBlogCntSql, params, rowMapper);
        List<Map<String, Object>> commentCnts = jdbcTemplate.query(statsCommentCntSql, params, rowMapper);
        List<Map<String, Object>> requestCnts = jdbcTemplate.query(requestLogCntSql, params, rowMapper);
        List<Map<String, Object>> exceptionCnts = jdbcTemplate.query(exceptionLogCntSql, params, rowMapper);

        List<StatisticsInfo> result = new ArrayList<>(dayOff);
        for (int off = 0; off < dayOff; off++) {
            String dayStr = DateUtils.formate(DateUtils.addDay(oldestDay, off), BlogConstants.FORMAT_YYYY_MM_DD);
            Map<String, Object> viewCntMap = locateByDate(viewCnts, "created_at_day", dayStr);
            Map<String, Object> senseCntMap = locateByDate(senseCnts, dateKey, dayStr);
            Map<String, Object> blogCntMap = locateByDate(blogCnts, dateKey, dayStr);
            Map<String, Object> commentCntMap = locateByDate(commentCnts, dateKey, dayStr);
            Map<String, Object> requestCntMap = locateByDate(requestCnts, dateKey, dayStr);
            Map<String, Object> exceptionCntMap = locateByDate(exceptionCnts, dateKey, dayStr);

            StatisticsInfo dayInfo = new StatisticsInfo();
            encapStatisticsInfo(viewCntMap, senseCntMap, blogCntMap, commentCntMap,
                    requestCntMap, exceptionCntMap, dayInfo);
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
        String statsSenseCntSql = " select count(if (score > 3, true, null)) as goodCnt, count(if (score <= 3, true, null)) as notGoodCnt from blog_sense where sense = '1' and date_format(created_at, '%y-%m-%d') <= ? ";
        String statsBlogCntSql = " select count(*) as blogCnt from blog where deleted = 0 and date_format(created_at, '%y-%m-%d') <= ? ";
        String statsCommentCntSql = " select count(*) as commentCnt from blog_comment where deleted = 0 and date_format(created_at, '%y-%m-%d') <= ? ";
        String requestLogCntSql = " select count(*) as requestCnt from request_log where date_format(created_at, '%y-%m-%d') <= ? ";
        String exceptionLogCntSql = " select count(*) as exceptionCnt from exception_log where date_format(created_at, '%y-%m-%d') <= ? ";

        Object[] params = new Object[]{yesterday};
        Map<String, Object> viewCntMap = jdbcTemplate.queryForMap(statsViewCntSql, params);
        Map<String, Object> senseCntMap = jdbcTemplate.queryForMap(statsSenseCntSql, params);
        Map<String, Object> blogCntMap = jdbcTemplate.queryForMap(statsBlogCntSql, params);
        Map<String, Object> commentCntMap = jdbcTemplate.queryForMap(statsCommentCntSql, params);
        Map<String, Object> requestCntMap = jdbcTemplate.queryForMap(requestLogCntSql, params);
        Map<String, Object> exceptionCntMap = jdbcTemplate.queryForMap(exceptionLogCntSql, params);

        StatisticsInfo result = new StatisticsInfo();
        encapStatisticsInfo(viewCntMap, senseCntMap, blogCntMap, commentCntMap, requestCntMap, exceptionCntMap, result);
        return result;
    }

    /**
     * 收集当前所有的 month -> blogCnt
     *
     * @param jdbcTemplate jdbcTemplate
     * @return java.util.Map<java.lang.String,java.lang.Integer>
     * @author Jerry.X.He
     * @date 6/24/2017 3:29 PM
     * @since 1.0
     */
    public static Map<String, Integer> collectMonthFacet(JdbcTemplate jdbcTemplate) {
        String statsBlogCntSql = " select created_at_month, count(*) as cnt from blog where id >= 0 and deleted = 0 group by created_at_month ";
        List<StringIntPair> monthFacet = jdbcTemplate.query(statsBlogCntSql, new StringIntPairMapper("created_at_month", "cnt"));
        Map<String, Integer> result = new LinkedHashMap<>(Tools.estimateMapSize(monthFacet.size()));
        for (StringIntPair pair : monthFacet) {
            result.put(pair.getLeft(), pair.getRight());
        }

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
     * 判断给定的 flag 对应于 mask 的标志位是否存在
     *
     * @param flag flag
     * @param mask mask
     * @return boolean
     * @author Jerry.X.He
     * @date 6/17/2017 12:11 PM
     * @since 1.0
     */
    public static boolean flagExists(int flag, int mask) {
        return ((flag & mask) != 0);
    }

    /**
     * 遍历 map, 查询 id 为给定的id的元素
     *
     * @param map map
     * @return java.util.List<T>
     * @author Jerry.X.He
     * @date 6/10/2017 4:33 PM
     * @since 1.0
     */
    public static <T extends LogisticalId<ID>, ID> T findByLogisticId(Map<String, T> map, ID id) {
        for (Map.Entry<String, T> entry : map.entrySet()) {
            if (Objects.equals(id, entry.getValue().logisticalId())) {
                return entry.getValue();
            }
        }

        return null;
    }

    public static <T extends LogisticalId<ID>, ID> T findByLogisticId(List<T> list, ID id) {
        for (T entry : list) {
            if (Objects.equals(id, entry.logisticalId())) {
                return entry;
            }
        }

        return null;
    }

    /**
     * 从 request 中获取 cookieName 对应的 cookie的值
     * 本来是打算将 hx_blog_token 存放于 cookie的, 结果 放进去之后, 除了首页之外的其他页面 没有带token过来
     * 因此 后来换了一种思路, 使用的 localSession 来解决问题 !
     *
     * @param req        req
     * @param cookieName cookieName
     * @return java.lang.String
     * @author Jerry.X.He
     * @date 6/18/2017 11:55 AM
     * @since 1.0
     */
    public static String getCookieValByName(HttpServletRequest req, String cookieName) {
        Cookie[] cookies = req.getCookies();
        if ((cookies == null) || (cookieName == null)) {
            return null;
        }

        String res = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieName)) {
                res = cookie.getValue();
                break;
            }
        }
        return res;
    }

    /**
     * 向给定的 response 中放入一个 cookie
     *
     * @param resp        resp
     * @param cookieName  cookieName
     * @param cookieValue cookieValue
     * @return java.lang.String
     * @author Jerry.X.He
     * @date 6/18/2017 11:55 AM
     * @since 1.0
     */
    public static void addCookie(HttpServletResponse resp, String cookieName, String cookieValue) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
//        cookie.setPath("/");
        resp.addCookie(cookie);
    }

    /**
     * 如果给定的评论内容是回复的话, 获取回复的开始索引
     *
     * @param comment            comment
     * @param replyCommentPrefix replyCommentPrefix
     * @param replyCommentSuffix replyCommentSuffix
     * @return int
     * @author Jerry.X.He
     * @date 6/4/2017 1:49 PM
     * @since 1.0
     */
    public static int idxOfEndRe(String comment, String replyCommentPrefix, String replyCommentSuffix) {
        if (!comment.startsWith(replyCommentPrefix)) {
            return -1;
        }

        return comment.indexOf(replyCommentSuffix) + replyCommentSuffix.length();
    }

    /**
     * 预处理content
     * 1. 去掉script
     * 2. 去掉a.href, iframe.src
     * 3. 去掉各个事件
     * 因为html大小写不敏感, 因此这里统一以小写作为标准进行比较[toLowerCase]
     *
     * @param content content
     * @return java.lang.String
     * @author Jerry.X.He
     * @date 6/25/2017 8:58 AM
     * @since 1.0
     */
    public static String prepareContent(String id, String content, Collection<String> sensetiveTags,
                                        Map<String, Map<String, List<String>>> sensetiveTag2Attr,
                                        Collection<String> sensetiveAttrs) {
        Document doc = Jsoup.parse(content);
        AtomicLong updated = new AtomicLong(0);
        prepareContent(doc, sensetiveTags, sensetiveTag2Attr, sensetiveAttrs, updated);
        Log.logWithIdx(" deal [{0}], updated : {1} ", id, updated.get());
        return doc.toString();
    }

    public static void prepareContent(Element ele, Collection<String> sensetiveTags,
                                      Map<String, Map<String, List<String>>> sensetiveTag2Attr,
                                      Collection<String> sensetiveAttrs, AtomicLong updated) {
        String lowerTagName = ele.tagName().toLowerCase();
        // remove if tag is forbidden
        for (String sensetiveTag : sensetiveTags) {
            if (ANT_PATH_MATCHER.match(sensetiveTag, lowerTagName)) {
                ele.remove();
                updated.addAndGet(1 + ele.attributes().size());
                return;
            }
        }

        // remove specified tag's specified attribute if that contains sensetiveWords
        Map<String, List<String>> attr2SensetiveWords = null;
        for (Map.Entry<String, Map<String, List<String>>> entry : sensetiveTag2Attr.entrySet()) {
            if (ANT_PATH_MATCHER.match(entry.getKey(), lowerTagName)) {
                attr2SensetiveWords = entry.getValue();
                break;
            }
        }
        if (!Tools.isEmpty(attr2SensetiveWords)) {
            // remove all sensetiveAttrs
            Iterator<Attribute> attrIts = ele.attributes().iterator();
            while (attrIts.hasNext()) {
                Attribute attr = attrIts.next();
                String lowerAttrKey = attr.getKey().toLowerCase();
                // href = "javascript:alert(1)"
                if (attr2SensetiveWords.containsKey(lowerAttrKey)) {
                    String lowerAttrValue = attr.getValue().toLowerCase();
                    for (String sensetiveWord : attr2SensetiveWords.get(lowerAttrKey)) {
                        if (lowerAttrValue.contains(sensetiveWord)) {
                            ele.removeAttr(attr.getKey());
                            updated.addAndGet(1);
                            break;
                        }
                    }
                }
            }
        }

        // remove all sensetiveAttrs
        Iterator<Attribute> attrIts = ele.attributes().iterator();
        while (attrIts.hasNext()) {
            Attribute attr = attrIts.next();
            String lowerAttrKey = attr.getKey().toLowerCase();
            for (String sensetiveAttr : sensetiveAttrs) {
                if (ANT_PATH_MATCHER.match(sensetiveAttr, lowerAttrKey)) {
                    ele.removeAttr(attr.getKey());
                    updated.addAndGet(1);
                    break;
                }
            }
        }

        for (Element child : ele.children()) {
            prepareContent(child, sensetiveTags, sensetiveTag2Attr, sensetiveAttrs, updated);
        }
    }

    /**
     * 在不允许标签的场景下面, 转义所有的标签
     *
     * @param body           body
     * @param needToBeFormat needToBeFormat
     * @return java.lang.String
     * @author Jerry.X.He
     * @date 6/25/2017 9:18 AM
     * @since 1.0
     */
    public static String transferTags(String id, String body, Map<String, String> needToBeFormat) {
        if (Tools.isEmpty(body)) {
            return Tools.EMPTY_STR;
        }

        long updated = 0;
        StringBuilder sb = new StringBuilder(body.length());
        for (int i = 0; i < body.length(); i++) {
            // 为了效率, 这里就仅仅写"替换key"长度为1的数据了, 以后再来更新吧
            String nextCh = String.valueOf(body.charAt(i));
            if (!needToBeFormat.containsKey(nextCh)) {
                sb.append(nextCh);
            } else {
                sb.append(needToBeFormat.get(nextCh));
                updated++;
            }
        }

        Log.logWithIdx(" deal [{0}], updated : {1} ", id, updated);
        return sb.toString();
    }

    // ----------------- 辅助方法 -----------------------

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
                                            Map<String, Object> requestCntMap, Map<String, Object> exceptionCntMap,
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
        if (!Tools.isEmpty(requestCntMap)) {
            dayInfo.setRequestLogCnt(Tools.optInt(requestCntMap, "requestCnt", 0));
        }
        if (!Tools.isEmpty(exceptionCntMap)) {
            dayInfo.setExceptionLogCnt(Tools.optInt(exceptionCntMap, "exceptionCnt", 0));
        }
    }


}
