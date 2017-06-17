package com.hx.blog_v2.context;

import com.hx.blog_v2.util.BlogConstants;
import com.hx.json.JSONParseUtils;
import com.hx.json.interf.JSON;
import com.hx.log.util.Log;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.hx.log.util.Tools.assert0;

/**
 * WebContext
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/6/2017 11:16 AM
 */
public final class WebContext {

    // disable constructor
    private WebContext() {
        assert0("can't instantiate !");
    }

    /**
     * 输出的 字符集
     */
    public static String RESP_CHARSET = "utf-8";

    /**
     * 保存线程特有的相关对象
     */
    private static Map<String, ThreadLocal<Object>> threadLocalMap = new HashMap<>();

    static {
        threadLocalMap.put(BlogConstants.SESSION_REQUEST, new ThreadLocal<>());
        threadLocalMap.put(BlogConstants.SESSION_RESPONSE, new ThreadLocal<>());
        threadLocalMap.put(BlogConstants.SESSION_SESSION, new ThreadLocal<>());
    }

    /**
     * 项目的绝对路径
     */
    private static String PROJ_PATH = null;

    // -------------------- 文件系统相关 --------------------------

    public static void init(ServletContext servletContext) {
        PROJ_PATH = servletContext.getRealPath("/");
    }

    /**
     * 获取当前项目的路径
     *
     * @return java.lang.String
     * @author Jerry.X.He
     * @date 5/21/2017 3:17 PM
     * @since 1.0
     */
    public static String getProjPath() {
        return PROJ_PATH;
    }

    // -------------------- req/resp --------------------------

    /**
     * 配置当前请求处理线程对应的request
     *
     * @param req 当前线程对应的请求
     * @return void
     * @author Jerry.X.He
     * @date 5/6/2017 3:04 PM
     * @since 1.0
     */
    public static void setRequest(HttpServletRequest req) {
        setThreadLocal(BlogConstants.SESSION_REQUEST, req);
    }

    /**
     * 获取当前请求处理线程对应的request
     *
     * @return void
     * @author Jerry.X.He
     * @date 5/6/2017 3:04 PM
     * @since 1.0
     */
    public static HttpServletRequest getRequest() {
        return (HttpServletRequest) getThreadLocal(BlogConstants.SESSION_REQUEST);
    }

    /**
     * 清理当前请求处理线程对应的request
     *
     * @return void
     * @author Jerry.X.He
     * @date 5/6/2017 3:04 PM
     * @since 1.0
     */
    public static void removeRequest() {
        cleanThreadLocal(BlogConstants.SESSION_REQUEST);
    }

    /**
     * 配置当前请求处理线程对应的request
     *
     * @param resp 当前线程对应的response
     * @return void
     * @author Jerry.X.He
     * @date 5/6/2017 3:04 PM
     * @since 1.0
     */
    public static void setResponse(HttpServletResponse resp) {
        setThreadLocal(BlogConstants.SESSION_RESPONSE, resp);
    }

    /**
     * 获取当前请求处理线程对应的request
     *
     * @return void
     * @author Jerry.X.He
     * @date 5/6/2017 3:04 PM
     * @since 1.0
     */
    public static HttpServletResponse getResponse() {
        return (HttpServletResponse) getThreadLocal(BlogConstants.SESSION_RESPONSE);
    }

    /**
     * 清理当前请求处理线程对应的response
     *
     * @return void
     * @author Jerry.X.He
     * @date 5/6/2017 3:04 PM
     * @since 1.0
     */
    public static void removeResponse() {
        cleanThreadLocal(BlogConstants.SESSION_RESPONSE);
    }

    /**
     * 配置当前请求处理线程对应的session
     *
     * @param session 当前请求对应的session
     * @return void
     * @author Jerry.X.He
     * @date 5/6/2017 3:04 PM
     * @since 1.0
     */
    public static void setSession(HttpSession session) {
        setThreadLocal(BlogConstants.SESSION_SESSION, session);
    }

    /**
     * 获取当前请求处理线程对应的request
     *
     * @return void
     * @author Jerry.X.He
     * @date 5/6/2017 3:04 PM
     * @since 1.0
     */
    public static HttpSession getSession() {
        return (HttpSession) getThreadLocal(BlogConstants.SESSION_SESSION);
    }

    /**
     * 清理当前请求处理线程对应的response
     *
     * @return void
     * @author Jerry.X.He
     * @date 5/6/2017 3:04 PM
     * @since 1.0
     */
    public static void removeSession() {
        cleanThreadLocal(BlogConstants.SESSION_SESSION);
    }

    /**
     * 从request中获取参数
     *
     * @param key 给定的key
     * @return java.lang.Object
     * @author Jerry.X.He
     * @date 5/6/2017 11:59 PM
     * @since 1.0
     */
    public static String getParameterFromRequest(String key) {
        return getRequest().getParameter(key);
    }

    /**
     * 从request中获取属性
     *
     * @param key 给定的key
     * @return java.lang.Object
     * @author Jerry.X.He
     * @date 5/6/2017 11:59 PM
     * @since 1.0
     */
    public static Object getAttributeFromRequest(String key) {
        return getRequest().getAttribute(key);
    }

    /**
     * 从request中获取属性
     *
     * @param key 给定的key
     * @return java.lang.Object
     * @author Jerry.X.He
     * @date 5/6/2017 11:59 PM
     * @since 1.0
     */
    public static String getStrAttrFromRequest(String key) {
        return String.valueOf(getRequest().getAttribute(key));
    }

    /**
     * 配置request中的属性
     *
     * @param key 给定的key
     * @param val 给定的val
     * @return java.lang.Object
     * @author Jerry.X.He
     * @date 5/6/2017 11:59 PM
     * @since 1.0
     */
    public static void setAttributeForRequest(String key, Object val) {
        getRequest().setAttribute(key, val);
    }

    /**
     * 移除requset中的属性
     *
     * @param key 给定的key
     * @return java.lang.Object
     * @author Jerry.X.He
     * @date 5/6/2017 11:59 PM
     * @since 1.0
     */
    public static void removeAttributeFromRequest(String key) {
        getRequest().removeAttribute(key);
    }

    /**
     * 从session中获取属性
     *
     * @param key 给定的key
     * @return java.lang.Object
     * @author Jerry.X.He
     * @date 5/6/2017 11:59 PM
     * @since 1.0
     */
    public static Object getAttributeFromSession(String key) {
        return getSession().getAttribute(key);
    }

    /**
     * 从session中获取属性
     *
     * @param key 给定的key
     * @return java.lang.Object
     * @author Jerry.X.He
     * @date 5/6/2017 11:59 PM
     * @since 1.0
     */
    public static String getStrAttrFromSession(String key) {
        return String.valueOf(getSession().getAttribute(key));
    }

    /**
     * 配置session中的属性
     *
     * @param key 给定的key
     * @param val 给定的val
     * @return java.lang.Object
     * @author Jerry.X.He
     * @date 5/6/2017 11:59 PM
     * @since 1.0
     */
    public static void setAttributeForSession(String key, Object val) {
        getSession().setAttribute(key, val);
    }

    /**
     * 移除session中的属性
     *
     * @param key 给定的key
     * @return java.lang.Object
     * @author Jerry.X.He
     * @date 5/6/2017 11:59 PM
     * @since 1.0
     */
    public static void removeAttributeFromSession(String key) {
        getSession().removeAttribute(key);
    }

    // -------------------- write response --------------------------

    /**
     * 向response中写出text
     *
     * @param text 需要写出的数据
     * @return void
     * @author Jerry.X.He
     * @date 5/6/2017 3:24 PM
     * @since 1.0
     */
    public static void responseText(String text) {
        HttpServletResponse resp = getResponse();
        resp.setHeader("Content-Type", "text/plain;charset=" + RESP_CHARSET);
        responseWrite(resp, text);
    }

    /**
     * 向response中写出html
     *
     * @param text 需要写出的数据
     * @return void
     * @author Jerry.X.He
     * @date 5/6/2017 3:24 PM
     * @since 1.0
     */
    public static void responseHtml(String text) {
        HttpServletResponse resp = getResponse();
        resp.setHeader("Content-Type", "text/html;charset=" + RESP_CHARSET);
        responseWrite(resp, text);
    }

    /**
     * 向response中写出xml
     *
     * @param text 需要写出的数据
     * @return void
     * @author Jerry.X.He
     * @date 5/6/2017 3:24 PM
     * @since 1.0
     */
    public static void responseXml(String text) {
        HttpServletResponse resp = getResponse();
        resp.setHeader("Content-Type", "text/xml;charset=" + RESP_CHARSET);
        responseWrite(resp, text);
    }

    /**
     * 向response中写出json
     *
     * @param text 需要写出的数据
     * @return void
     * @author Jerry.X.He
     * @date 5/6/2017 3:24 PM
     * @since 1.0
     */
    public static void responseJson(String text) {
        HttpServletResponse resp = getResponse();
        resp.setHeader("Content-Type", "application/json;charset=" + RESP_CHARSET);
        responseWrite(resp, text);
    }

    public static void responseJson(JSON json) {
        responseJson(String.valueOf(json));
    }

    public static void responseJson(Object bean) {
        responseJson(JSONParseUtils.fromBean(bean));
    }

    /**
     * 向response中写出图片
     *
     * @param img    需要写出的数据
     * @param format 需要写出的图片的格式
     * @return void
     * @author Jerry.X.He
     * @date 5/6/2017 3:24 PM
     * @since 1.0
     */
    public static void responseImage(RenderedImage img, String format) {
        HttpServletResponse resp = getResponse();
        resp.setHeader("Content-Type", "image;charset=" + RESP_CHARSET);

        try {
            ImageIO.write(img, format, resp.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 配置当前response不允许缓存
     *
     * @return void
     * @author Jerry.X.He
     * @date 5/6/2017 3:28 PM
     * @since 1.0
     */
    public static void responseNoCache() {
        HttpServletResponse resp = getResponse();
        resp.setDateHeader("Expires", -1);
        resp.setHeader("Cache-Control", "no-cache");
        resp.setHeader("Pragma", "no-cache");
    }

    // -------------------- to be continued --------------------------


    // -------------------- 辅助方法 --------------------------


    /**
     * 配置当前请求处理线程对应的kv
     *
     * @param key 需要配置的的key
     * @param val 需要配置的key对应的value
     * @return void
     * @author Jerry.X.He
     * @date 5/6/2017 3:04 PM
     * @since 1.0
     */
    public static void setThreadLocal(String key, Object val) {
        ThreadLocal<Object> threadLocal = threadLocalMap.get(key);
        if (threadLocal == null) {
            threadLocal = new ThreadLocal<>();
            threadLocalMap.put(key, threadLocal);
        }

        threadLocal.set(val);
    }

    /**
     * 获取当前请求处理线程对应的key对应的对象
     *
     * @param key 需要获取的的key
     * @return void
     * @author Jerry.X.He
     * @date 5/6/2017 3:04 PM
     * @since 1.0
     */
    public static Object getThreadLocal(String key) {
        ThreadLocal<Object> threadLocal = threadLocalMap.get(key);

        if (threadLocal != null) {
            return threadLocal.get();
        }
        return null;
    }

    /**
     * 清理当前请求处理线程对应的key对应的对象
     *
     * @param key 需要获取的的key
     * @return void
     * @author Jerry.X.He
     * @date 5/6/2017 3:04 PM
     * @since 1.0
     */
    public static void cleanThreadLocal(String key) {
        ThreadLocal<Object> threadLocal = threadLocalMap.get(key);
        if (threadLocal != null) {
            threadLocal.remove();
        }
    }

    /**
     * 向response中写出text
     *
     * @param resp response
     * @param text 需要写出的信息
     * @return void
     * @author Jerry.X.He
     * @date 5/6/2017 3:23 PM
     * @since 1.0
     */
    private static void responseWrite(HttpServletResponse resp, String text) {
        try {
            resp.getWriter().write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
