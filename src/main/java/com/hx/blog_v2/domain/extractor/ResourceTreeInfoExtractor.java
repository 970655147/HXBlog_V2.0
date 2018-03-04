package com.hx.blog_v2.domain.extractor;

import com.hx.blog_v2.domain.vo.resources.ResourceVO;
import com.hx.json.JSONObject;
import com.hx.log.alogrithm.tree.interf.TreeInfoExtractor;

/**
 * ResourceTreeInfoExtractor
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/3/2017 3:21 PM
 */
public class ResourceTreeInfoExtractor implements TreeInfoExtractor<ResourceVO> {

    @Override
    public void extract(ResourceVO bean, JSONObject obj) {
        obj.element("id", bean.getId());
        obj.element("name", bean.getName());
        obj.element("url", bean.getUrl());
        obj.element("iconClass", bean.getIconClass());
        obj.element("parentId", bean.getParentId());
    }

}
