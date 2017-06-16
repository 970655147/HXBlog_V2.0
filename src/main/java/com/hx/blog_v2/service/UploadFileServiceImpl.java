package com.hx.blog_v2.service;

import com.baidu.ueditor.ConfigManager;
import com.baidu.ueditor.define.ActionMap;
import com.baidu.ueditor.utils.Constants;
import com.hx.blog_v2.dao.interf.UploadFileDao;
import com.hx.blog_v2.domain.form.UploadedFileSaveForm;
import com.hx.blog_v2.domain.form.UploadedImageSaveForm;
import com.hx.blog_v2.domain.po.UploadFilePO;
import com.hx.blog_v2.service.interf.BaseServiceImpl;
import com.hx.blog_v2.service.interf.UploadFileService;
import com.hx.blog_v2.util.*;
import com.hx.common.interf.common.Result;
import com.hx.common.util.ResultUtils;
import com.hx.json.JSONObject;
import com.hx.log.alogrithm.code.Codec;
import com.hx.log.file.FileUtils;
import com.hx.log.util.Log;
import com.hx.log.util.Tools;
import com.hx.mongo.criteria.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * BlogServiceImpl
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 11:47 AM
 */
@Service
public class UploadFileServiceImpl extends BaseServiceImpl<UploadFilePO> implements UploadFileService {

    @Autowired
    private UploadFileDao uploadFileDao;
    @Autowired
    private CacheContext cacheContext;
    @Autowired
    private ConstantsContext constantsContext;
    @Autowired
    private BlogConstants constants;

    @Override
    public Result add(UploadedImageSaveForm params) {
        MultipartFile file = params.getFile();
        return add0(file);
    }

    @Override
    public Result add(UploadedFileSaveForm params) {
        MultipartFile file = params.getFile();
        return add0(file);
    }

    @Override
    public JSONObject ueditorUpload() {
        ConfigManager configManager = null;
        try {
            configManager = new ConfigManager(WebContext.getProjPath());
        } catch (IOException e) {
            // ignore
        }
        if (configManager == null || !configManager.valid()) {
            return new JSONObject().element(Constants.STATE, "配置文件读取错误 !");
        }

        String actionType = WebContext.getParameterFromRequest("action");
        int actionCode = ActionMap.getType(actionType);
        switch (actionCode) {
            case ActionMap.CONFIG: {
                return configManager.getAllConfig();
            }
            case ActionMap.UPLOAD_IMAGE:
            case ActionMap.UPLOAD_SCRAWL:
//			case ActionMap.UPLOAD_VIDEO:
//			case ActionMap.UPLOAD_FILE:
            case ActionMap.UPLOAD_IMAGE_MULTI_DPI: {
                HttpServletRequest req = WebContext.getRequest();
                if (!(req instanceof MultipartHttpServletRequest)) {
                    return new JSONObject().element(Constants.STATE, "没有需要上传的数据 !");
                }

                MultipartHttpServletRequest multipartReq = (MultipartHttpServletRequest) req;
                Map<String, MultipartFile> multipartFileMap = multipartReq.getFileMap();
                if (multipartFileMap.isEmpty()) {
                    return new JSONObject().element(Constants.STATE, "没有需要上传的数据 !");
                }
                if (multipartFileMap.size() > 1) {
                    Log.err("上传文件大于1个, 多余的文件 会被忽略 !");
                }

                MultipartFile file = multipartFileMap.values().iterator().next();
                UploadedImageSaveForm form = new UploadedImageSaveForm(file);
                Result result = add(form);
                JSONObject data = JSONObject.fromObject(result.getData());
                data.element(Constants.STATE, result.isSuccess() ? "SUCCESS" : result.getMsg());
                return data;
            }

        }

        return new JSONObject().element(Constants.STATE, "没有找到对应的 action !");
    }

    // -------------------- 辅助方法 --------------------------

    /**
     * 上传给定的文件, 持久化, 并在数据库中记录
     *
     * @param file file
     * @return com.hx.common.interf.common.Result
     * @author Jerry.X.He
     * @date 6/16/2017 11:56 PM
     * @since 1.0
     */
    private Result add0(MultipartFile file) {
        Result digestResult = generateDigest(file);
        if (!digestResult.isSuccess()) {
            return digestResult;
        }

        String digest = (String) digestResult.getData();
        UploadFilePO po = getUploadedImageFromExists(file, digest);
        if (po != null) {
            JSONObject data = new JSONObject()
                    .element("originFileName", po.getOriginalFileName()).element("length", po.getSize())
                    .element("contentType", po.getContentType()).element("url", getImageVisitUrl(po.getUrl()));
            return ResultUtils.success(data);
        }

        String relativePath = generateImgPath(file);
        String filePath = Tools.getFilePath(constants.fileRootDir, relativePath);
        try {
            FileUtils.createIfNotExists(filePath, true);
            file.transferTo(new File(filePath));
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.failed("failed !");
        }

        po = new UploadFilePO(file.getOriginalFilename(), file.getContentType(), relativePath, digest, String.valueOf(file.getSize()));
        Result addResult = uploadFileDao.add(po);
        if (!addResult.isSuccess()) {
            return addResult;
        }

        cacheContext.putUploadedFile(digest, po);
        JSONObject data = new JSONObject()
                .element("originFileName", po.getOriginalFileName()).element("length", po.getSize())
                .element("contentType", po.getContentType()).element("url", getImageVisitUrl(relativePath));
        return ResultUtils.success(data);
    }

    /**
     * 根据给定的id生成该图片需要保存的路径 [相对]
     *
     * @param file 给定的文件信息
     * @return java.lang.String
     * @author Jerry.X.He
     * @date 5/21/2017 3:09 PM
     * @since 1.0
     */
    private String generateImgPath(MultipartFile file) {
        String fileName = file.getOriginalFilename(), suffix = fileName.substring(fileName.indexOf("."));
        String dateStr = DateUtils.formate(new Date(), BlogConstants.FORMAT_YYYY_MM_DD);
        String folder = dateStr.replace("-", "/");
        return Tools.getFilePath(folder, Codec.byte2Hex(Codec.md5((fileName + com.hx.log.date.DateUtils.nowStr()).getBytes())) + suffix);
    }

    /**
     * 生成给定的文件的摘要
     *
     * @param file file
     * @return java.lang.String
     * @author Jerry.X.He
     * @date 5/29/2017 4:32 PM
     * @since 1.0
     */
    private Result generateDigest(MultipartFile file) {
        try {
            byte[] bytes = file.getBytes();
            return ResultUtils.success(Codec.byte2Hex(Codec.md5(bytes)));
        } catch (IOException e) {
            return ResultUtils.failed(Tools.errorMsg(e));
        }
    }

    /**
     * 根据相对路径, 获取给定的图片的访问的路径
     *
     * @param relativePath relativePath
     * @return java.lang.String
     * @author Jerry.X.He
     * @date 5/29/2017 4:45 PM
     * @since 1.0
     */
    private String getImageVisitUrl(String relativePath) {
        return constantsContext.imageUrlPrefix + relativePath;
    }

    /**
     * 从缓存中获取 file 对应的 image
     *
     * @param file   file
     * @param digest digest
     * @return com.hx.blog_v2.domain.po.UploadFilePO
     * @author Jerry.X.He
     * @date 5/29/2017 4:31 PM
     * @since 1.0
     */
    private UploadFilePO getUploadedImageFromExists(MultipartFile file, String digest) {
        UploadFilePO po = cacheContext.getUploadedFile(digest);
        if (po != null) {
            if (po.getSize().equals(String.valueOf(file.getSize()))
                    && po.getOriginalFileName().equals(file.getOriginalFilename())
                    && po.getContentType().equals(file.getContentType())
                    ) {
                return po;
            }
        }

        try {
            po = uploadFileDao.findOne(
                    Criteria.and(Criteria.eq("digest", digest), Criteria.eq("original_file_name", file.getOriginalFilename()),
                            Criteria.eq("size", file.getSize()), Criteria.eq("content_type", file.getContentType())),
                    BlogConstants.LOAD_ALL_CONFIG);
            cacheContext.putUploadedFile(digest, po);
            if (po != null) {
                return po;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return null;
    }

}
