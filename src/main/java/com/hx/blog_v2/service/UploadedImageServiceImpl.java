package com.hx.blog_v2.service;

import com.hx.blog_v2.dao.interf.UploadedImageDao;
import com.hx.blog_v2.domain.form.UploadedImageSaveForm;
import com.hx.blog_v2.domain.po.UploadedImagePO;
import com.hx.blog_v2.service.interf.BaseServiceImpl;
import com.hx.blog_v2.service.interf.UploadedImageService;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.CacheContext;
import com.hx.blog_v2.util.DateUtils;
import com.hx.blog_v2.util.WebContext;
import com.hx.common.interf.common.Result;
import com.hx.common.util.ResultUtils;
import com.hx.json.JSONObject;
import com.hx.log.alogrithm.code.Codec;
import com.hx.log.file.FileUtils;
import com.hx.log.util.Tools;
import com.hx.mongo.criteria.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.util.Date;

/**
 * BlogServiceImpl
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 11:47 AM
 */
@Service
public class UploadedImageServiceImpl extends BaseServiceImpl<UploadedImagePO> implements UploadedImageService {

    @Autowired
    private UploadedImageDao uploadedImageDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private CacheContext cacheContext;

    @Override
    public Result add(UploadedImageSaveForm params) {
        CommonsMultipartFile file = params.getFile();
        String digest = generateDigest(file);
        UploadedImagePO po = getUploadedImageFromExists(file, digest);
        if (po != null) {
            JSONObject data = new JSONObject()
                    .element("originFileName", po.getOriginalFileName()).element("length", po.getSize())
                    .element("contentType", po.getContentType()).element("url", getImageVisitUrl(po.getUrl()));
            return ResultUtils.success(data);
        }

        String relativePath = generateImgPath(file);
        String filePath = Tools.getFilePath(WebContext.getImgRootPath(), relativePath);
        try {
            FileUtils.createIfNotExists(filePath, true);
            file.transferTo(new File(filePath));

            po = new UploadedImagePO(file.getOriginalFilename(), file.getContentType(), relativePath, digest, String.valueOf(file.getSize()));
            uploadedImageDao.save(po, BlogConstants.ADD_BEAN_CONFIG);
            cacheContext.putUploadedImage(digest, po);

            JSONObject data = new JSONObject()
                    .element("originFileName", po.getOriginalFileName()).element("length", po.getSize())
                    .element("contentType", po.getContentType()).element("url", getImageVisitUrl(relativePath));
            return ResultUtils.success(data);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.failed("failed !");
        }
    }

    // -------------------- 辅助方法 --------------------------

    /**
     * 根据给定的id生成该图片需要保存的路径 [相对]
     *
     * @param file 给定的文件信息
     * @return java.lang.String
     * @author Jerry.X.He
     * @date 5/21/2017 3:09 PM
     * @since 1.0
     */
    private String generateImgPath(CommonsMultipartFile file) {
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
    private String generateDigest(CommonsMultipartFile file) {
        byte[] bytes = file.getBytes();
        return Codec.byte2Hex(Codec.md5(bytes));
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
        return BlogConstants.IMAGE_URL_RREFIX + relativePath;
    }

    /**
     * 从缓存中获取 file 对应的 image
     *
     * @param file   file
     * @param digest digest
     * @return com.hx.blog_v2.domain.po.UploadedImagePO
     * @author Jerry.X.He
     * @date 5/29/2017 4:31 PM
     * @since 1.0
     */
    private UploadedImagePO getUploadedImageFromExists(CommonsMultipartFile file, String digest) {
        UploadedImagePO po = cacheContext.getUploadedImage(digest);
        if (po != null) {
            if (po.getSize().equals(String.valueOf(file.getSize()))
                    && po.getOriginalFileName().equals(file.getOriginalFilename())
                    && po.getContentType().equals(file.getContentType())
                    ) {
                return po;
            }
        }

        try {
            po = uploadedImageDao.findOne(
                    Criteria.and(Criteria.eq("digest", digest), Criteria.eq("original_file_name", file.getOriginalFilename()),
                            Criteria.eq("size", file.getSize()), Criteria.eq("content_type", file.getContentType())),
                    BlogConstants.LOAD_ALL_CONFIG);
            cacheContext.putUploadedImage(digest, po);
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
