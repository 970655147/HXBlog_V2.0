/**
 * file name : ImageRetriver.java
 * created at : 22:30:27 2016-10-31
 * created by 970655147
 */

package com.baidu.ueditor;

import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baidu.ueditor.define.ActionMap;
import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.hunter.FileManager;
import com.baidu.ueditor.hunter.ImageHunter;
import com.baidu.ueditor.retrive.ImageRetriver;
import com.baidu.ueditor.upload.ImageMultiDpiUploader;
import com.baidu.ueditor.upload.Uploader;

/**
 * 处理抓取数据的action
 * 
 * @author Jerry.X.He
 */
public class ActionRetriver {

	private HttpServletRequest request = null;
	private HttpServletResponse response = null;
	
	private String rootPath = null;
	private String contextPath = null;
	
	private String actionType = null;
	
	private ConfigManager configManager = null;
	
	public ActionRetriver ( HttpServletRequest request, HttpServletResponse response, String rootPath ) {
		
		this.request = request;
		this.response = response;
		this.rootPath = rootPath;
		this.actionType = (String) request.getAttribute( "action" );
		this.contextPath = request.getContextPath();
		this.configManager = ConfigManager.getInstance(rootPath, this.contextPath, request.getRequestURI() );
		
	}
	
	/**
	 * 处理抓取数据的业务
	 *
	 * @author Jerry.X.He
	 */
	public void doRetrive() {
		if ( this.configManager == null || !this.configManager.valid() ) {
			return ;
		}
		
		int actionCode = ActionMap.getType( this.actionType );
		Map<String, Object> conf = null;
		
		switch ( actionCode ) {
		
			case ActionMap.GET_IMAGE:
				conf = this.configManager.getConfig( actionCode );
				new ImageRetriver(request, response, conf).doExec();
				return ;
				
				
		}
	}
	
	
}
