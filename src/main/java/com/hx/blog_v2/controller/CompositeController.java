package com.hx.blog_v2.controller;

import com.hx.blog_v2.context.ConstantsContext;
import com.hx.blog_v2.domain.form.ImageSearchForm;
import com.hx.blog_v2.service.interf.*;
import com.hx.blog_v2.util.ResultUtils;
import com.hx.common.interf.common.Result;
import com.hx.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * SystemController
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 4:57 PM
 */
@RestController
@RequestMapping("/composite")
public class CompositeController {

    @Autowired
    private BlogCreateTypeService blogCreateTypeService;
    @Autowired
    private BlogTypeService blogTypeService;
    @Autowired
    private BlogTagService blogTagService;
    @Autowired
    private MoodService moodService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private ConstantsContext constantsContext;


    @RequestMapping(value = "/typeAndTags", method = RequestMethod.GET)
    public Result typeAndTags() {
        Result createTypeResult = blogCreateTypeService.adminList();
        if (!createTypeResult.isSuccess()) {
            return createTypeResult;
        }
        Result typeResult = blogTypeService.list();
        if (!typeResult.isSuccess()) {
            return typeResult;
        }
        Result tagResult = blogTagService.list();
        if (!tagResult.isSuccess()) {
            return tagResult;
        }

        JSONObject data = new JSONObject().element("createTypes", createTypeResult.getData())
                .element("types", typeResult.getData()).element("tags", tagResult.getData());
        return ResultUtils.success(data);
    }

    @RequestMapping(value = "/moodAndImages", method = RequestMethod.GET)
    public Result moodAndImages() {
        Result moodsResult = moodService.list();
        if (!moodsResult.isSuccess()) {
            return moodsResult;
        }
        ImageSearchForm imgShowSearch = new ImageSearchForm(constantsContext.imgTypeImgShow);
        Result imagesResult = imageService.imageList(imgShowSearch);
        if (!imagesResult.isSuccess()) {
            return imagesResult;
        }

        JSONObject data = new JSONObject()
                .element("moods", moodsResult.getData()).element("images", imagesResult.getData());
        return ResultUtils.success(data);
    }

    @RequestMapping(value = "/userAndRoles", method = RequestMethod.GET)
    public Result userAndRoles() {
        Result userResult = userService.allId2Name();
        if (!userResult.isSuccess()) {
            return userResult;
        }
        Result roleResult = roleService.adminList();
        if (!roleResult.isSuccess()) {
            return roleResult;
        }

        JSONObject data = new JSONObject()
                .element("users", userResult.getData()).element("roles", roleResult.getData());
        return ResultUtils.success(data);
    }

}
