package com.hx.blog_v2.controller;

import com.hx.blog_v2.context.WebContext;
import com.hx.blog_v2.service.interf.BlogService;
import com.hx.common.interf.common.Result;
import com.hx.blog_v2.util.ResultUtils;
import com.hx.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * HelloWorldController
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/19/2017 9:06 PM
 */
@Controller
@RequestMapping("/tests")
public class HelloWorldController {

    @Autowired
    private BlogService blogService;

    @ResponseBody
    @RequestMapping("/helloWorld")
    public Result helloWorld() {

        // 因为 不是 标准的 getter 方法 不是标准的命名, 因此 得到的是 No MessageConverter
        return ResultUtils.success(new JSONObject().element("Hello", "World !")
                .element("service is null", blogService == null)
        );
//        return new Result(new JSONObject().element("Hello, ", "World !"));
    }

    @RequestMapping("/responseType")
    public void responseType() {
        // 不行,
        WebContext.responseText("Hello World !");
        // 还是不行,
//        return "Hello World !";
    }


//    /**
//     * Result
//     *
//     * @author Jerry.X.He <970655147@qq.com>
//     * @version 1.0
//     * @date 5/19/2017 9:19 PM
//     */
//    static class Result {
//        public Object data;
//
//        public Result(Object data) {
//            this.data = data;
//        }
//
//        public Object getData() {
//            return data;
//        }
//
//        public void setData(Object data) {
//            this.data = data;
//        }
//    }

}
