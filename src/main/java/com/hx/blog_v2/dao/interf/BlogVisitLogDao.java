package com.hx.blog_v2.dao.interf;

import com.hx.blog_v2.domain.form.BlogVisitLogForm;
import com.hx.blog_v2.domain.po.BlogVisitLogPO;
import com.hx.mongo.dao.interf.MysqlIBaseDao;

/**
 * BlogPODao
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 10:37 AM
 */
public interface BlogVisitLogDao extends MysqlIBaseDao<BlogVisitLogPO> {

    /**
     * 根据给定的条件, 获取一个 BlogVisitPO
     *
     * @return
     * @author Jerry.X.He
     * @date 6/9/2017 9:07 PM
     * @since 1.0
     */
    BlogVisitLogPO get(BlogVisitLogForm params);

    /**
     * 向数据库中加入给定的 BlogVisitLog
     *
     * @param po po
     * @return
     * @author Jerry.X.He
     * @date 6/9/2017 9:21 PM
     * @since 1.0
     */
    void add(BlogVisitLogPO po);

}
