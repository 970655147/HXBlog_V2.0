package com.baidu.ueditor.define;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 定义请求action类型
 * @author hancong03@baidu.com
 *
 */
@SuppressWarnings("serial")
public final class ActionMap {

	/**
	 * actionCode -> actionType
	 */
	public static final Map<String, Integer> mapping;
	/**
	 * 从服务端获取数据的actionType
	 */
	public static final List<Integer> retriveMapping = new ArrayList<>();
	
	// 获取配置请求
	public static int IDX = 0;
	public static final int CONFIG = 0;
	public static final int UPLOAD_IMAGE = CONFIG + 1;
	public static final int UPLOAD_IMAGE_MULTI_DPI = UPLOAD_IMAGE + 1;
	public static final int UPLOAD_SCRAWL = UPLOAD_IMAGE_MULTI_DPI + 1;
	public static final int UPLOAD_VIDEO = UPLOAD_SCRAWL + 1;
	public static final int UPLOAD_FILE = UPLOAD_VIDEO + 1;
	public static final int CATCH_IMAGE = UPLOAD_FILE + 1;
	public static final int LIST_FILE = CATCH_IMAGE + 1;
	public static final int LIST_IMAGE = LIST_FILE + 1;
	public static final int GET_IMAGE = LIST_IMAGE + 1;
	
	static {
		mapping = new HashMap<String, Integer>(){{
			put( "config", ActionMap.CONFIG );
			put( "uploadImage", ActionMap.UPLOAD_IMAGE );
			put( "uploadImageMultiDpi", ActionMap.UPLOAD_IMAGE_MULTI_DPI );
			put( "uploadScrawl", ActionMap.UPLOAD_SCRAWL );
			put( "uploadVideo", ActionMap.UPLOAD_VIDEO );
			put( "uploadFile", ActionMap.UPLOAD_FILE );
			put( "catchImage", ActionMap.CATCH_IMAGE );
			put( "listFile", ActionMap.LIST_FILE );
			put( "listImage", ActionMap.LIST_IMAGE );
//			put( "getImage", ActionMap.GET_IMAGE );
		}};
		
//		retriveMapping.add(ActionMap.GET_IMAGE);
	}
	
	public static Integer getType ( String key ) {
		return ActionMap.mapping.get( key );
	}
	
	public static boolean isRetriveAction(Integer type) {
		return retriveMapping.contains(type);
	}
	
}
