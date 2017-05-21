package com.hx.blog_v2.controller;

import com.hx.blog_v2.service.interf.BlogTagService;
import com.hx.blog_v2.service.interf.BlogTypeService;
import com.hx.common.interf.common.Result;
import com.hx.common.util.ResultUtils;
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
    private BlogTypeService blogTypeService;
    @Autowired
    private BlogTagService blogTagService;


    @RequestMapping(value = "/typeAndTags", method = RequestMethod.GET)
    public Result list() {

        Result typeResult = blogTypeService.list();
        if(! typeResult.isSuccess()) {
            return typeResult;
        }
        Result tagResult = blogTagService.list();
        if(! tagResult.isSuccess()) {
            return tagResult;
        }

        JSONObject data = new JSONObject().element("types", typeResult.getData())
                .element("tags", tagResult.getData());
        return ResultUtils.success(data);
    }

}
