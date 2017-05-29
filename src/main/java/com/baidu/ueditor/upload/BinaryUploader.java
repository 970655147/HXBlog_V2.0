package com.baidu.ueditor.upload;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hx.json.JSONArray;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.baidu.ueditor.PathFormat;
import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.State;
import com.baidu.ueditor.utils.Constants;
import com.baidu.ueditor.utils.FileUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class BinaryUploader {

	public static final State save(HttpServletRequest request, Map<String, Object> conf) {
		boolean isAjaxUpload = request.getHeader( "X_Requested_With" ) != null;

		if (!ServletFileUpload.isMultipartContent(request)) {
			return new BaseState(false, AppInfo.NOT_MULTIPART_CONTENT);
		}
		if(! (request instanceof MultipartHttpServletRequest)) {
			return new BaseState(false, AppInfo.NOT_MULTIPART_CONTENT);
		}

		try {
			MultipartHttpServletRequest multipartReq = (MultipartHttpServletRequest) request;
			MultipartFile file = multipartReq.getMultiFileMap().getFirst("file");
			String savePath = (String) conf.get(Constants.SAVE_PATH);
			String originFileName = file.getOriginalFilename();
			String suffix = FileUtils.getSuffixByFilename(originFileName, ".");

			originFileName = originFileName.substring(0,
					originFileName.length() - suffix.length());
			savePath = savePath + suffix;

			long maxSize = ((Long) conf.get(Constants.MAX_SIZE));
			if (! ((JSONArray) conf.get(Constants.ALLOW_FILES)).contains(suffix)) {
				return new BaseState(false, AppInfo.NOT_ALLOW_FILE_TYPE);
			}

			savePath = PathFormat.parse(savePath, originFileName);
			String physicalPath = FileUtils.getPhysicalPath(conf, savePath);

			InputStream is = file.getInputStream();
			State storageState = StorageManager.saveFileByInputStream(is, physicalPath, maxSize);
			is.close();

			if (storageState.isSuccess()) {
				String formatedPath = PathFormat.format(savePath);
				String domainPrefix = String.valueOf(conf.get(Constants.DOMAIN_PREFIX) );
				String fullPath = (domainPrefix.trim().length() != 0) ? 
						FileUtils.getPhysicalPath(domainPrefix, formatedPath) : formatedPath;

				storageState.putInfo(Constants.URL, fullPath);
//				storageState.putInfo(Constants.SAVE_PATH, formatedPath);
				storageState.putInfo(Constants.TYPE, suffix);
				storageState.putInfo(Constants.NAME, FileUtils.getFilenameByPath(formatedPath, "/") );
				storageState.putInfo(Constants.ORIGINAL, originFileName + suffix);
			}

			return storageState;
		} catch (IOException e) {
			return new BaseState(false, AppInfo.IO_ERROR);
		}
	}

}
