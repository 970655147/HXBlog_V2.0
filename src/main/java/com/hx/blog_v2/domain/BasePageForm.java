package com.hx.blog_v2.domain;

import com.hx.blog_v2.context.WebContext;
import com.hx.common.interf.common.Page;
import com.hx.common.result.SimplePage;
import com.hx.log.str.StringUtils;

/**
 * BaseForm
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 8/4/2018 10:26 AM
 */
public class BasePageForm extends BaseForm {

    @Override
    public String generateCacheKey() {
        String pageNow = WebContext.getRequest().getParameter("pageNow");
        String pageSize = WebContext.getRequest().getParameter("pageSize");
        return "page[" + pageNow + ", " + pageSize + "]";
    }

    @Override
    public boolean isPageRequest() {
        return true;
    }

    @Override
    public Page getPageInfo() {
        Page page = new SimplePage();
        String pageNow = WebContext.getRequest().getParameter("pageNow");
        String pageSize = WebContext.getRequest().getParameter("pageSize");
        if (StringUtils.isNumeric(pageNow)) {
            page.setPageNow(Integer.valueOf(pageNow));
        }
        if (StringUtils.isNumeric(pageSize)) {
            page.setPageNow(Integer.valueOf(pageSize));
        }
        return null;
    }

}
