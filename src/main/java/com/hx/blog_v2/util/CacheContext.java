package com.hx.blog_v2.util;

import com.hx.blog_v2.dao.interf.BlogTagDao;
import com.hx.blog_v2.dao.interf.BlogTypeDao;
import com.hx.blog_v2.domain.po.BlogTagPO;
import com.hx.blog_v2.domain.po.BlogTypePO;
import com.hx.log.util.Log;
import com.hx.mongo.criteria.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 缓存了一部分的数据
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/21/2017 6:02 PM
 */
@Component
public class CacheContext {

    @Autowired
    private BlogTagDao blogTagDao;
    @Autowired
    private BlogTypeDao blogTypeDao;

    /**
     * id -> blogTag
     */
    private Map<String, BlogTagPO> blogTagsById;
    /**
     * id -> blogType
     */
    private Map<String, BlogTypePO> blogTypesById;

    public BlogTagDao getBlogTagDao() {
        return blogTagDao;
    }

    public void setBlogTagDao(BlogTagDao blogTagDao) {
        this.blogTagDao = blogTagDao;
    }

    public BlogTypeDao getBlogTypeDao() {
        return blogTypeDao;
    }

    public void setBlogTypeDao(BlogTypeDao blogTypeDao) {
        this.blogTypeDao = blogTypeDao;
    }

    /**
     * 初始化 CacheContext
     *
     * @return void
     * @author Jerry.X.He
     * @date 5/21/2017 6:36 PM
     * @since 1.0
     */
    @PostConstruct
    public void init() {
        blogTagsById = new LinkedHashMap<>();
        blogTypesById = new LinkedHashMap<>();
        try {
            List<BlogTagPO> list = blogTagDao.findMany(Criteria.allMatch(), BlogConstants.IDX_MANAGER.getDoLoad());
            for(BlogTagPO po : list) {
                blogTagsById.put(po.getId(), po);
            }
        } catch (Exception e) {
            Log.err("error while load blog_tag's data !");
        }
        try {
            List<BlogTypePO> list = blogTypeDao.findMany(Criteria.allMatch(), BlogConstants.IDX_MANAGER.getDoLoad());
            for(BlogTypePO po : list) {
                blogTypesById.put(po.getId(), po);
            }
        } catch (Exception e) {
            Log.err("error while load blog_type's data !");
        }

    }

    /**
     * 获取所有的 BlogType
     *
     * @return java.util.Map<java.lang.String,com.hx.blog_v2.domain.po.BlogTypePO>
     * @author Jerry.X.He
     * @date 5/21/2017 6:13 PM
     * @since 1.0
     */
    public Map<String, BlogTypePO> allBlogTypes() {
        return blogTypesById;
    }

    public BlogTypePO blogType(String id) {
        return blogTypesById.get(id);
    }

    /**
     * 获取所有的 BlogType
     *
     * @return java.util.Map<java.lang.String,com.hx.blog_v2.domain.po.BlogTypePO>
     * @author Jerry.X.He
     * @date 5/21/2017 6:13 PM
     * @since 1.0
     */
    public Map<String, BlogTagPO> allBlogTags() {
        return blogTagsById;
    }

    public BlogTagPO blogTag(String id) {
        return blogTagsById.get(id);
    }






}
