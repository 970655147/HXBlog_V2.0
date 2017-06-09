package com.hx.blog_v2.dao.interf;

import com.hx.blog_v2.domain.form.BlogSenseForm;
import com.hx.blog_v2.domain.po.BlogSensePO;
import com.hx.mongo.dao.interf.MysqlIBaseDao;

/**
 * BlogTagDao
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 11:20 AM
 */
public interface BlogSenseDao extends MysqlIBaseDao<BlogSensePO> {

    /**
     * 根据给定的条件, 获取一个 po
     *
     * @return
     * @author Jerry.X.He
     * @date 6/9/2017 9:07 PM
     * @since 1.0
     */
    BlogSensePO get(BlogSenseForm params);

    /**
     * 向数据库中加入给定的 po
     *
     * @param po po
     * @return
     * @author Jerry.X.He
     * @date 6/9/2017 9:21 PM
     * @since 1.0
     */
    void add(BlogSensePO po);

}