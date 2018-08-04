package com.hx.blog_v2.test;

import com.hx.blog_v2.dao.interf.BlogDao;
import com.hx.blog_v2.domain.mapper.others.CommonPOMapper;
import com.hx.blog_v2.domain.po.blog.BlogPO;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.domain.BaseVO;
import com.hx.mongo.criteria.Criteria;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static com.hx.log.util.Log.info;

/**
 * Test06Connection
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/21/2017 7:40 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextHierarchy({
        @ContextConfiguration(locations = {
                "classpath:spring-core.xml", "classpath:spring-mvc.xml"
        })
})
public class Test06Connection {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private BlogDao blogDao;
    @Autowired
    private DataSource dataSource;

    public static final int CNT = 200;

    @Test
    public void test01JdbcTemplate() {

        String sql = " select * from blog ";

        for (int i = 0; i < CNT; i++) {
            List<BlogPO> result = jdbcTemplate.query(sql, new CommonPOMapper<>(BlogPO.PROTO_BEAN));
            info(result.size());
        }


    }

    @Test
    public void test02BaseDao() throws Exception {

        for (int i = 0; i < CNT; i++) {
            List<BlogPO> result = blogDao.findMany(Criteria.allMatch(), BlogConstants.LOAD_ALL_CONFIG);
            info(result.size());
        }


    }

    @Test
    public void test03DataSource() throws Exception {

        String sql = " select * from blog ";
        List<ResultSet> resultSets = new ArrayList<>(CNT);

        for (int i = 0; i < CNT; i++) {
            Connection con = dataSource.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // 卧槽 这是怎么回事, 为什么 HXMongo 就不行 !
//            resultSets.add(rs);
            // 关闭 ps.close 也可以访问 200 个请求
            ps.close();
            con.close();
            info(i);
        }


    }

}
