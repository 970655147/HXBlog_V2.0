package com.hx.blog_v2.service;

import com.baidu.ueditor.ConfigManager;
import com.baidu.ueditor.define.ActionMap;
import com.baidu.ueditor.utils.Constants;
import com.hx.blog_v2.context.CacheContext;
import com.hx.blog_v2.context.ConstantsContext;
import com.hx.blog_v2.context.WebContext;
import com.hx.blog_v2.dao.interf.UploadFileDao;
import com.hx.blog_v2.domain.ErrorCode;
import com.hx.blog_v2.domain.dto.SimpleFileItem;
import com.hx.blog_v2.domain.form.UploadedFileSaveForm;
import com.hx.blog_v2.domain.form.UploadedImageSaveForm;
import com.hx.blog_v2.domain.po.UploadFilePO;
import com.hx.blog_v2.service.interf.BaseServiceImpl;
import com.hx.blog_v2.service.interf.UploadFileService;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.DateUtils;
import com.hx.blog_v2.util.ResultUtils;
import com.hx.common.interf.common.Result;
import com.hx.json.JSONObject;
import com.hx.log.alogrithm.code.Codec;
import com.hx.log.file.FileUtils;
import com.hx.log.util.Log;
import com.hx.log.util.Tools;
import com.hx.mongo.criteria.Criteria;
import com.hx.mongo.criteria.interf.IQueryCriteria;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
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
    /**
     * 构造 CommonMultiPartFile 所需, 小于此阈值, 可以将文件存放于mem
     */
    private int szThreshold = -1;
    /**
     * 上传图片处理之后的最大的分辨率
     */
    private int maxResolution = 1920;
    /**
     * 上传图片处理之后的图片质量控制
     */
    private float outputQuality = 0.8F;

    @Override
    public Result add(UploadedImageSaveForm params) {
        initIfNeed();
        MultipartFile file = params.getFile();
        File tmpFile = getTmpFile();

        String suffix = getSuffixByFilename(file.getOriginalFilename(), ".");
        try {
            BufferedImage uploadedImage = ImageIO.read(file.getInputStream());
            long longger = Math.max(uploadedImage.getWidth(), uploadedImage.getHeight());
            if (longger > maxResolution) {
                uploadedImage = Scalr.resize(uploadedImage, maxResolution);
            }
            Thumbnails.Builder<BufferedImage> builder = Thumbnails.of(uploadedImage).scale(1.0f).outputFormat(suffix.substring(1));
            builder.outputQuality(outputQuality);
            builder.toFile(tmpFile);
        } catch (IOException e) {
            e.printStackTrace();
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, Tools.errorMsg(e));
        }

        tmpFile = new File(tmpFile.getAbsolutePath() + suffix);
        FileItem tmpFileItem = new SimpleFileItem("uploadFile", true,
                file.getContentType(), file.getOriginalFilename(), tmpFile, null);
        Result result = add0(new CommonsMultipartFile(tmpFileItem));
        if(result.isSuccess()) {
            tmpFile.delete();
        }
        return result;
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
        Result getCachedResult = getUploadedImageFromExists(file, digest);
        if (getCachedResult.isSuccess()) {
            UploadFilePO po = (UploadFilePO) getCachedResult.getData();
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

        UploadFilePO po = new UploadFilePO(file.getOriginalFilename(), file.getContentType(),
                relativePath, digest, String.valueOf(file.getSize()));
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
    private Result getUploadedImageFromExists(MultipartFile file, String digest) {
        UploadFilePO po = cacheContext.getUploadedFile(digest);
        if (po != null) {
            if (po.getSize().equals(String.valueOf(file.getSize()))
                    && po.getOriginalFileName().equals(file.getOriginalFilename())
                    && po.getContentType().equals(file.getContentType())
                    ) {
                return ResultUtils.success(po);
            }
        }

        IQueryCriteria query = Criteria.and(Criteria.eq("digest", digest),
                Criteria.eq("original_file_name", file.getOriginalFilename()),
                Criteria.eq("size", file.getSize()), Criteria.eq("content_type", file.getContentType()));
        Result getFileResult = uploadFileDao.get(query);
        if (!getFileResult.isSuccess()) {
            return getFileResult;
        }

        cacheContext.putUploadedFile(digest, po);
        if (po != null) {
            return ResultUtils.success(po);
        }
        return ResultUtils.failed(" 上传放行 ");
    }

    /**
     * 根据给定的文件名,获取其后缀信息
     *
     * @param filename filename
     * @param sep      sep
     * @return java.lang.String
     * @author Jerry.X.He
     * @date 7/2/2017 10:28 AM
     * @since 1.0
     */
    private String getSuffixByFilename(String filename, String sep) {
        int lastIdxOf = filename.lastIndexOf(sep);
        if (lastIdxOf < 0) {
            return filename;
        }
        return filename.substring(lastIdxOf).toLowerCase();
    }

    /**
     * 获取一个临时文件
     *
     * @return java.io.File
     * @author Jerry.X.He
     * @since 2017/1/24 11:11
     */
    private static File getTmpFile() {
        File tmpDir = org.apache.commons.io.FileUtils.getTempDirectory();
        String tmpFileName = (Math.random() * 10000 + "").replace(".", "");
        return new File(tmpDir, tmpFileName);
    }

    /**
     * 初始化配置
     *
     * @return void
     * @author Jerry.X.He
     * @date 7/2/2017 10:38 AM
     * @since 1.0
     */
    private void initIfNeed() {
        if (szThreshold < 0) {
            szThreshold = Integer.parseInt(constantsContext.ruleConfig("image.size.threshold.mem", "1048576"));
            maxResolution = Integer.parseInt(constantsContext.ruleConfig("image.max.resolution", "1920"));
            outputQuality = Float.parseFloat(constantsContext.ruleConfig("image.quality.max", "0.8"));
        }
    }

}
