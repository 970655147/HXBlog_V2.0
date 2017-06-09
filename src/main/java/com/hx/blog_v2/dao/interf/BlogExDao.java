package com.hx.blog_v2.dao.interf;

import com.hx.blog_v2.domain.form.BeanIdForm;
import com.hx.blog_v2.domain.po.BlogExPO;
import com.hx.mongo.dao.interf.MysqlIBaseDao;

/**
 * BlogExDao
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 11:20 AM
 */
public interface BlogExDao extends MysqlIBaseDao<BlogExPO> {

    /**
     * 根据给定的条件, 获取一个 po
     *
     * @return
     * @author Jerry.X.He
     * @date 6/9/2017 9:07 PM
     * @since 1.0
     */
    BlogExPO get(BeanIdForm params);

    /**
     * 向数据库中加入给定的 po
     *
     * @param po po
     * @return
     * @author Jerry.X.He
     * @date 6/9/2017 9:21 PM
     * @since 1.0
     */
    void add(BlogExPO po);

}