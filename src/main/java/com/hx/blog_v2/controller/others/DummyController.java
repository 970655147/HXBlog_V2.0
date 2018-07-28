package com.hx.blog_v2.controller.others;

import com.hx.blog_v2.biz_log.anno.BizLogger;
import com.hx.blog_v2.context.CacheContext;
import com.hx.blog_v2.context.WebContext;
import com.hx.blog_v2.domain.common.system.SessionUser;
import com.hx.blog_v2.util.BizUtils;
import com.hx.blog_v2.util.BlogConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * DummyController, honey jar
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 8/12/2017 4:13 PM
 */
@RestController
@RequestMapping("/")
public class DummyController {

    @Autowired
    private CacheContext cacheContext;

    @RequestMapping("/*")
    @BizLogger(req = false, resp = false)
    public void dummy() {

        SessionUser user = (SessionUser) WebContext.getAttributeFromSession(BlogConstants.SESSION_USER);
        String ip = user.getRequestIp();
        BizUtils.incFreq(cacheContext.inputNotFormatPerPeroid(), ip, 1);

    }

}
