package com.hx.blog_v2.util;

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
     * �����߳����е���ض���
     */
    private static Map<String, ThreadLocal<Object>> threadLocalMap = new HashMap<>();

    static {
        threadLocalMap.put(BlogConstants.SESSION_REQUEST, new ThreadLocal<>());
        threadLocalMap.put(BlogConstants.SESSION_RESPONSE, new ThreadLocal<>());
        threadLocalMap.put(BlogConstants.SESSION_SESSION, new ThreadLocal<>());
    }

    /**
     * ��Ŀ�ľ���·��
     */
    private static String PROJ_PATH = null;
    /**
     * ��Ų��͵��ļ���
     */
    private static String BLOG_ROOT_PATH = null;
    /**
     * ���ͼƬ���ļ���
     */
    private static String IMG_ROOT_PATH = null;

    static {
        try {
            Class.forName(BlogConstants.DB_DRIVER);
        } catch (Exception e) {
            Log.err("error while loading driver !");
        }
    }

    // -------------------- �ļ�ϵͳ��� --------------------------

    public static void init(ServletContext servletContext) {
        PROJ_PATH = servletContext.getRealPath("/");
        BLOG_ROOT_PATH = BlogConstants.BLOG_ROOT_DIR;
        IMG_ROOT_PATH = BlogConstants.IMG_ROOT_DIR;
    }

    /**
     * ��ȡ��ǰ��Ŀ��·��
     *
     * @return java.lang.String
     * @author Jerry.X.He
     * @date 5/21/2017 3:17 PM
     * @since 1.0
     */
    public static String getProjPath() {
        return PROJ_PATH;
    }

    /**
     * ��ȡ���͵ĸ�·��
     *
     * @return java.lang.String
     * @author Jerry.X.He
     * @date 5/21/2017 3:17 PM
     * @since 1.0
     */
    public static String getBlogRootPath() {
        return BLOG_ROOT_PATH;
    }

    /**
     * ��ȡͼƬ�ĸ�·��
     *
     * @return java.lang.String
     * @author Jerry.X.He
     * @date 5/21/2017 3:17 PM
     * @since 1.0
     */
    public static String getImgRootPath() {
        return IMG_ROOT_PATH;
    }

    // -------------------- req/resp --------------------------

    /**
     * ���õ�ǰ�������̶߳�Ӧ��request
     *
     * @param req ��ǰ�̶߳�Ӧ������
     * @return void
     * @author Jerry.X.He
     * @date 5/6/2017 3:04 PM
     * @since 1.0
     */
    public static void setRequest(HttpServletRequest req) {
        setThreadLocal(BlogConstants.SESSION_REQUEST, req);
    }

    /**
     * ��ȡ��ǰ�������̶߳�Ӧ��request
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
     * ����ǰ�������̶߳�Ӧ��request
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
     * ���õ�ǰ�������̶߳�Ӧ��request
     *
     * @param resp ��ǰ�̶߳�Ӧ��response
     * @return void
     * @author Jerry.X.He
     * @date 5/6/2017 3:04 PM
     * @since 1.0
     */
    public static void setResponse(HttpServletResponse resp) {
        setThreadLocal(BlogConstants.SESSION_RESPONSE, resp);
    }

    /**
     * ��ȡ��ǰ�������̶߳�Ӧ��request
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
     * ����ǰ�������̶߳�Ӧ��response
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
     * ���õ�ǰ�������̶߳�Ӧ��session
     *
     * @param session ��ǰ�����Ӧ��session
     * @return void
     * @author Jerry.X.He
     * @date 5/6/2017 3:04 PM
     * @since 1.0
     */
    public static void setSession(HttpSession session) {
        setThreadLocal(BlogConstants.SESSION_SESSION, session);
    }

    /**
     * ��ȡ��ǰ�������̶߳�Ӧ��request
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
     * ����ǰ�������̶߳�Ӧ��response
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
     * ��request�л�ȡ����
     *
     * @param key ������key
     * @return java.lang.Object
     * @author Jerry.X.He
     * @date 5/6/2017 11:59 PM
     * @since 1.0
     */
    public static Object getParameterFromRequest(String key) {
        return getRequest().getParameter(key);
    }

    /**
     * ��request�л�ȡ����
     *
     * @param key ������key
     * @return java.lang.Object
     * @author Jerry.X.He
     * @date 5/6/2017 11:59 PM
     * @since 1.0
     */
    public static Object getAttributeFromRequest(String key) {
        return getRequest().getAttribute(key);
    }

    /**
     * ��request�л�ȡ����
     *
     * @param key ������key
     * @return java.lang.Object
     * @author Jerry.X.He
     * @date 5/6/2017 11:59 PM
     * @since 1.0
     */
    public static String getStrAttrFromRequest(String key) {
        return String.valueOf(getRequest().getAttribute(key));
    }

    /**
     * ����request�е�����
     *
     * @param key ������key
     * @param val ������val
     * @return java.lang.Object
     * @author Jerry.X.He
     * @date 5/6/2017 11:59 PM
     * @since 1.0
     */
    public static void setAttributeForRequest(String key, Object val) {
        getRequest().setAttribute(key, val);
    }

    /**
     * �Ƴ�requset�е�����
     *
     * @param key ������key
     * @return java.lang.Object
     * @author Jerry.X.He
     * @date 5/6/2017 11:59 PM
     * @since 1.0
     */
    public static void removeAttributeFromRequest(String key) {
        getRequest().removeAttribute(key);
    }

    /**
     * ��session�л�ȡ����
     *
     * @param key ������key
     * @return java.lang.Object
     * @author Jerry.X.He
     * @date 5/6/2017 11:59 PM
     * @since 1.0
     */
    public static Object getAttributeFromSession(String key) {
        return getSession().getAttribute(key);
    }

    /**
     * ��session�л�ȡ����
     *
     * @param key ������key
     * @return java.lang.Object
     * @author Jerry.X.He
     * @date 5/6/2017 11:59 PM
     * @since 1.0
     */
    public static String getStrAttrFromSession(String key) {
        return String.valueOf(getSession().getAttribute(key));
    }

    /**
     * ����session�е�����
     *
     * @param key ������key
     * @param val ������val
     * @return java.lang.Object
     * @author Jerry.X.He
     * @date 5/6/2017 11:59 PM
     * @since 1.0
     */
    public static void setAttributeForSession(String key, Object val) {
        getSession().setAttribute(key, val);
    }

    /**
     * �Ƴ�session�е�����
     *
     * @param key ������key
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
     * ��response��д��text
     *
     * @param text ��Ҫд��������
     * @return void
     * @author Jerry.X.He
     * @date 5/6/2017 3:24 PM
     * @since 1.0
     */
    public static void responseText(String text) {
        HttpServletResponse resp = getResponse();
        resp.setHeader("Content-Type", "text/plain;charset=" + BlogConstants.DEFAULT_CHARSET);
        responseWrite(resp, text);
    }

    /**
     * ��response��д��html
     *
     * @param text ��Ҫд��������
     * @return void
     * @author Jerry.X.He
     * @date 5/6/2017 3:24 PM
     * @since 1.0
     */
    public static void responseHtml(String text) {
        HttpServletResponse resp = getResponse();
        resp.setHeader("Content-Type", "text/html;charset=" + BlogConstants.DEFAULT_CHARSET);
        responseWrite(resp, text);
    }

    /**
     * ��response��д��xml
     *
     * @param text ��Ҫд��������
     * @return void
     * @author Jerry.X.He
     * @date 5/6/2017 3:24 PM
     * @since 1.0
     */
    public static void responseXml(String text) {
        HttpServletResponse resp = getResponse();
        resp.setHeader("Content-Type", "text/xml;charset=" + BlogConstants.DEFAULT_CHARSET);
        responseWrite(resp, text);
    }

    /**
     * ��response��д��json
     *
     * @param text ��Ҫд��������
     * @return void
     * @author Jerry.X.He
     * @date 5/6/2017 3:24 PM
     * @since 1.0
     */
    public static void responseJson(String text) {
        HttpServletResponse resp = getResponse();
        resp.setHeader("Content-Type", "application/json;charset=" + BlogConstants.DEFAULT_CHARSET);
        responseWrite(resp, text);
    }

    public static void responseJson(JSON json) {
        responseJson(String.valueOf(json));
    }

    public static void responseJson(Object bean) {
        responseJson(JSONParseUtils.fromBean(bean));
    }

    /**
     * ��response��д��ͼƬ
     *
     * @param img    ��Ҫд��������
     * @param format ��Ҫд����ͼƬ�ĸ�ʽ
     * @return void
     * @author Jerry.X.He
     * @date 5/6/2017 3:24 PM
     * @since 1.0
     */
    public static void responseImage(RenderedImage img, String format) {
        HttpServletResponse resp = getResponse();
        resp.setHeader("Content-Type", "image;charset=" + BlogConstants.DEFAULT_CHARSET);

        try {
            ImageIO.write(img, format, resp.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * ���õ�ǰresponse��������
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


    // -------------------- �������� --------------------------


    /**
     * ���õ�ǰ�������̶߳�Ӧ��kv
     *
     * @param key ��Ҫ���õĵ�key
     * @param val ��Ҫ���õ�key��Ӧ��value
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
     * ��ȡ��ǰ�������̶߳�Ӧ��key��Ӧ�Ķ���
     *
     * @param key ��Ҫ��ȡ�ĵ�key
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
     * ����ǰ�������̶߳�Ӧ��key��Ӧ�Ķ���
     *
     * @param key ��Ҫ��ȡ�ĵ�key
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
     * ��response��д��text
     *
     * @param resp response
     * @param text ��Ҫд������Ϣ
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
