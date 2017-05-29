package com.hx.blog_v2.controller.admin;

import com.baidu.ueditor.ActionEnter;
import com.baidu.ueditor.ActionRetriver;
import com.baidu.ueditor.define.ActionMap;
import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.utils.Constants;
import com.hx.blog_v2.domain.form.UploadedImageSaveForm;
import com.hx.blog_v2.service.interf.UploadFileService;
import com.hx.blog_v2.util.WebContext;
import com.hx.common.interf.common.Result;
import com.hx.json.JSONObj;
import com.hx.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * SystemController
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 4:57 PM
 */
@RestController
@RequestMapping("/admin/upload")
public class UploadController {

    @Autowired
    private UploadFileService uploadFileService;

    @RequestMapping(value = "/image", method = RequestMethod.POST)
    public Result upload(UploadedImageSaveForm form) {

        return uploadFileService.add(form);
    }

    @RequestMapping(value = "/file", method = RequestMethod.POST)
    public Result file(UploadedImageSaveForm form) {

        return uploadFileService.add(form);
    }

    @RequestMapping(value="/ueditor/config")
    public JSONObject config(HttpServletRequest request, HttpServletResponse response) {
        // dispath 获取数据, 上传数据, ..
        request.setAttribute("action", request.getParameter( "action" ) );
        String actionCode = (String) request.getAttribute( "action" );
        Integer actionType = ActionMap.getType(actionCode);
        if ( actionType == null ) {
            return new JSONObject().element(Constants.STATE, "无效的 action !");
        }

        if(! ActionMap.isRetriveAction(actionType) ) {
            return uploadFileService.ueditorUpload();
        }
        return new JSONObject().element(Constants.STATE, "无效的 action !");
    }

}
