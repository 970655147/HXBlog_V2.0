package com.baidu.ueditor;

import com.baidu.ueditor.define.ActionMap;
import com.baidu.ueditor.utils.Constants;
import com.hx.json.JSONArray;
import com.hx.json.JSONObject;
import com.hx.log.util.Tools;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 配置管理器
 *
 * @author hancong03@baidu.com
 */
public final class ConfigManager {

    private final String rootPath;
    public static final String CONFIG_FILE_PATH = "WEB-INF/classes/ueditor.config.json";
    public static JSONObject JSON_CONFIG = null;
    // 涂鸦上传filename定义
    private final static String SCRAWL_FILE_NAME = "scrawl";
    // 远程图片抓取filename定义
    private final static String REMOTE_FILE_NAME = "remote";

    /*
     * 通过一个给定的路径构建一个配置管理器， 该管理器要求地址路径所在目录下必须存在config.properties文件
     */
    public ConfigManager(String rootPath) throws IOException {
        rootPath = rootPath.replace("\\", "/");

        this.rootPath = rootPath;
        this.initEnv();

    }

    /**
     * 配置管理器构造工厂
     *
     * @param rootPath    服务器根路径
     * @param contextPath 服务器所在项目路径
     * @param uri         当前访问的uri
     * @return 配置管理器实例或者null
     */
    public static ConfigManager getInstance(String rootPath, String contextPath, String uri) {
        try {
            return new ConfigManager(rootPath);
        } catch (Exception e) {
            return null;
        }

    }

    // 验证配置文件加载是否正确
    public boolean valid() {
        return this.JSON_CONFIG != null;
    }

    public JSONObject getAllConfig() {
        JSONObject result = JSONObject.fromObject(JSON_CONFIG);
        result.remove("imageRootPath");
        result.remove("imageDomainPrefix");
        result.remove("dpies");
        result.remove("waptermarkDpies");
        return result;
    }

    public Map<String, Object> getConfig(int type) {
        Map<String, Object> conf = new HashMap<String, Object>();
        Long maxSize = (100L << 20); // default 100M
        String savePath = null, rootPath = null, domainPrefix = null;

        switch (type) {
            case ActionMap.UPLOAD_FILE:
                conf.put(Constants.IS_BASE64, "false");
                conf.put(Constants.ALLOW_FILES, JSON_CONFIG.optJSONArray("fileAllowFiles", new JSONArray()));
                conf.put(Constants.FIELD_NAME, this.JSON_CONFIG.getString("fileFieldName"));

                maxSize = this.JSON_CONFIG.getLong("fileMaxSize");
                rootPath = this.JSON_CONFIG.getString("fileRootPath");
                domainPrefix = this.JSON_CONFIG.getString("fileDomainPrefix");
                savePath = this.JSON_CONFIG.getString("filePathFormat");
                break;

            case ActionMap.UPLOAD_IMAGE:
            case ActionMap.UPLOAD_IMAGE_MULTI_DPI:
            case ActionMap.GET_IMAGE:
                conf.put(Constants.IS_BASE64, "false");
                conf.put(Constants.ALLOW_FILES, JSON_CONFIG.optJSONArray("imageAllowFiles", new JSONArray()));
                conf.put(Constants.FIELD_NAME, this.JSON_CONFIG.getString("imageFieldName"));
                conf.put(Constants.DPIES, this.JSON_CONFIG.get("dpies"));
                conf.put(Constants.WATERMARK_DPIES, this.JSON_CONFIG.get("waptermarkDpies"));
                conf.put(Constants.OUTPUT_QUALITY, this.JSON_CONFIG.getFloat("imageOutputQuality"));
                conf.put(Constants.MAX_RESOLUTION, this.JSON_CONFIG.getLong("imageMaxResolution"));

                maxSize = this.JSON_CONFIG.getLong("imageMaxSize");
                rootPath = this.JSON_CONFIG.getString("imageRootPath");
                domainPrefix = this.JSON_CONFIG.getString("imageDomainPrefix");
                savePath = this.JSON_CONFIG.getString("imagePathFormat");
                break;

            case ActionMap.UPLOAD_VIDEO:
                conf.put(Constants.ALLOW_FILES, JSON_CONFIG.optJSONArray("videoAllowFiles", new JSONArray()));
                conf.put(Constants.FIELD_NAME, this.JSON_CONFIG.getString("videoFieldName"));

                maxSize = this.JSON_CONFIG.getLong("videoMaxSize");
                rootPath = this.JSON_CONFIG.getString("videoRootPath");
                domainPrefix = this.JSON_CONFIG.getString("videoDomainPrefix");
                savePath = this.JSON_CONFIG.getString("videoPathFormat");
                break;

            case ActionMap.UPLOAD_SCRAWL:
                conf.put(Constants.IS_BASE64, "true");
                conf.put(Constants.FILE_NAME, ConfigManager.SCRAWL_FILE_NAME);
                conf.put(Constants.FIELD_NAME, this.JSON_CONFIG.getString("scrawlFieldName"));
                conf.put(Constants.OUTPUT_QUALITY, this.JSON_CONFIG.getFloat("scrawlOutputQuality"));
                conf.put(Constants.MAX_RESOLUTION, this.JSON_CONFIG.getInt("scrawlMaxResolution"));

                maxSize = this.JSON_CONFIG.getLong("scrawlMaxSize");
                rootPath = this.JSON_CONFIG.getString("imageRootPath");
                domainPrefix = this.JSON_CONFIG.getString("imageDomainPrefix");
                savePath = this.JSON_CONFIG.getString("scrawlPathFormat");
                break;

            case ActionMap.CATCH_IMAGE:
                conf.put(Constants.FILE_NAME, ConfigManager.REMOTE_FILE_NAME);
                conf.put(Constants.FILTER, JSON_CONFIG.optJSONArray("catcherLocalDomain", new JSONArray()));
                conf.put(Constants.ALLOW_FILES, JSON_CONFIG.optJSONArray("catcherAllowFiles", new JSONArray()));
                conf.put(Constants.FIELD_NAME, this.JSON_CONFIG.getString("catcherFieldName") + "[]");

                maxSize = this.JSON_CONFIG.getLong("catcherMaxSize");
                rootPath = this.JSON_CONFIG.getString("imageRootPath");
                domainPrefix = this.JSON_CONFIG.getString("imageDomainPrefix");
                savePath = this.JSON_CONFIG.getString("catcherPathFormat");
                break;

            case ActionMap.LIST_IMAGE:
                conf.put(Constants.ALLOW_FILES, JSON_CONFIG.optJSONArray("imageManagerAllowFiles", new JSONArray()));
                conf.put(Constants.DIR, this.JSON_CONFIG.getString("imageManagerListPath"));
                conf.put(Constants.COUNT, this.JSON_CONFIG.getInt("imageManagerListSize"));
                break;

            case ActionMap.LIST_FILE:
                conf.put(Constants.ALLOW_FILES, JSON_CONFIG.optJSONArray("fileManagerAllowFiles", new JSONArray()));
                conf.put(Constants.DIR, this.JSON_CONFIG.getString("fileManagerListPath"));
                conf.put(Constants.COUNT, this.JSON_CONFIG.getInt("fileManagerListSize"));
                break;
        }

        conf.put(Constants.SAVE_PATH, savePath);
        conf.put(Constants.MAX_SIZE, maxSize);
        conf.put(Constants.ROOT_PATH, rootPath);
        conf.put(Constants.DOMAIN_PREFIX, domainPrefix);

        if (conf.get(Constants.ROOT_PATH) == null) {
            conf.put(Constants.ROOT_PATH, this.rootPath);
        }
        if (conf.get(Constants.DOMAIN_PREFIX) == null) {
            conf.put(Constants.DOMAIN_PREFIX, "");
        }

        return conf;

    }

    private void initEnv() throws FileNotFoundException, IOException {
        if(JSON_CONFIG != null) {
            return ;
        }

        String configContent = this.readFile(this.getConfigPath());
        try {
            JSONObject jsonConfig = JSONObject.fromObject(configContent);
            this.JSON_CONFIG = jsonConfig;
        } catch (Exception e) {
            this.JSON_CONFIG = null;
        }

    }

    private String getConfigPath() {
        return this.rootPath + File.separator + ConfigManager.CONFIG_FILE_PATH;
    }

    private String readFile(String path) throws IOException {
        String configContent = Tools.getContent(path);
        return this.filter(configContent);

    }

    // 过滤输入字符串, 剔除多行注释以及替换掉反斜杠
    private String filter(String input) {

        return input.replaceAll("/\\*[\\s\\S]*?\\*/", "");

    }

}
