/**
 * file name : ImageRetriver.java
 * created at : 22:54:56 2016-10-31
 * created by 970655147
 */

package com.baidu.ueditor.retrive;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.StringUtils;
import com.baidu.ueditor.utils.FileUtils;

/**
 * 抓取图片数据的Retriver
 * 
 * @author Jerry.X.He
 *
 */
public class ImageRetriver {

	/**
	 * request, response, 以及当前上传任务的config
	 */
	private HttpServletRequest request = null;
	private HttpServletResponse response = null;
	private Map<String, Object> conf = null;
	
	public ImageRetriver(HttpServletRequest request, HttpServletResponse response, Map<String, Object> conf) {
		this.request = request;
		this.response = response;
		this.conf = conf;
	}

	/**
	 * 获取给定的path对应的文件, 对于如果不合法的请求返回任何东西
	 *
	 * @author Jerry.X.He
	 */
	public void doExec() {
		String path = request.getParameter("path");
		if(StringUtils.isNullOrEmpty(path) || path.contains("..") ) {
			return ;
		}
		String suffix = FileUtils.getSuffixByFilename(path, ".");
		if (! FileUtils.validType(suffix, (String[]) conf.get("allowFiles")) ) {
			return ;
		}
		
		response.setHeader("Content-Type", "image");
		// case 02 : resolve if return "http://image.jetmall.com/fileName.png" 
		// case 03. "ts.png"		
		path = FileUtils.resolveRleativePath(path);
		if(path == null) {
			return ;
		}
		
		String imgPath = FileUtils.getPhysicalPath(this.conf, path);
		try {
			BufferedImage img = ImageIO.read(new FileInputStream(imgPath) );
			// substring cutoff the '.'
			ImageIO.write(img, suffix.substring(1), response.getOutputStream() );
		} catch (FileNotFoundException e) {
			// ignore 
			// e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
