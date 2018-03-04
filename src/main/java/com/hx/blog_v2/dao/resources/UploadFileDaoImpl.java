package com.hx.blog_v2.dao.resources;

import com.hx.blog_v2.context.CacheContext;
import com.hx.blog_v2.dao.interf.BaseDaoImpl;
import com.hx.blog_v2.dao.interf.UploadFileDao;
import com.hx.blog_v2.domain.po.system.UploadFilePO;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.MyMysqlConnectionProvider;
import com.hx.blog_v2.util.ResultUtils;
import com.hx.common.interf.common.Result;
import com.hx.mongo.config.MysqlDbConfig;
import com.hx.mongo.criteria.Criteria;
import com.hx.mongo.criteria.interf.IQueryCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

/**
 * UploadFileDaoImpl
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 10:38 AM
 */
@Repository
public class UploadFileDaoImpl extends BaseDaoImpl<UploadFilePO> implements UploadFileDao {

    @Autowired
    private CacheContext cacheContext;

    public UploadFileDaoImpl() {
        super(UploadFilePO.PROTO_BEAN,
                new MysqlDbConfig(BlogConstants.MYSQL_DB_CONFIG).table(tableName()).id(id()),
                MyMysqlConnectionProvider.getInstance());
    }


    public static String tableName() {
        return BlogConstants.getInstance().tableUploadedFiles;
    }

    public static String id() {
        return BlogConstants.getInstance().tableId;
    }

    @Override
    public Result get(MultipartFile file, String digest) {
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
        Result getFileResult = get(query);
        if (!getFileResult.isSuccess()) {
            return getFileResult;
        }
        po = (UploadFilePO) getFileResult.getData();
        if (po != null) {
            cacheContext.putUploadedFile(digest, po);
            return ResultUtils.success(po);
        }
        return ResultUtils.failed(" 没有符合条件的记录 ");
    }
}
