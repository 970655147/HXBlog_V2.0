package com.hx.blog_v2.domain.mapper;

import com.hx.blog_v2.util.BlogConstants;
import com.hx.log.json.interf.JSONTransferable;
import com.hx.mongo.util.ResultSet2MapAdapter;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * CommonPOMapper
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/4/2017 9:22 AM
 */
public class CommonPOMapper<T extends JSONTransferable<T>> implements RowMapper<T> {

    /**
     * protoBean 用于创建对象
     */
    private T protoBean;

    public CommonPOMapper(T protoBean) {
        this.protoBean = protoBean;
    }

    @Override
    public T mapRow(ResultSet resultSet, int i) throws SQLException {
        T po = protoBean.newInstance();
        po.loadFromJSON(new ResultSet2MapAdapter(resultSet), BlogConstants.LOAD_ALL_CONFIG);
        return po;
    }

}
