package com.hx.blog_v2.test;

import com.hx.blog_v2.domain.po.*;
import com.hx.log.json.JSONTransferableUtils;
import org.junit.Test;

import static com.hx.log.util.Log.info;

/**
 * Test02JSONTransferable
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 10:35 AM
 */
public class Test02JSONTransferable {

    @Test
    public void blog() throws Exception {
        Class clazz = BlogPO.class;
        infoIdxesAndDao(clazz);
    }

    @Test
    public void blogEx() throws Exception {
        Class clazz = BlogExPO.class;
        infoIdxesAndDao(clazz);
    }

    @Test
    public void blogTag() throws Exception {
        Class clazz = BlogTagPO.class;
        infoIdxesAndDao(clazz);
    }

    @Test
    public void blogType() throws Exception {
        Class clazz = BlogTypePO.class;
        infoIdxesAndDao(clazz);
    }

    @Test
    public void blogComment() throws Exception {
        Class clazz = BlogCommentPO.class;
        infoIdxesAndDao(clazz);
    }

    @Test
    public void rltBlogTag() throws Exception {
        Class clazz = RltBlogTagPO.class;
        infoIdxesAndDao(clazz);
    }

    @Test
    public void blogSense() throws Exception {
        Class clazz = BlogSensePO.class;
        infoIdxesAndDao(clazz);
    }

    @Test
    public void visitor() throws Exception {
        Class clazz = VisitorPO.class;
        infoIdxesAndDao(clazz);
    }

    @Test
    public void requestLog() throws Exception {
        Class clazz = RequestLogPO.class;
        infoIdxesAndDao(clazz);
    }

    @Test
    public void exceptionLog() throws Exception {
        Class clazz = ExceptionLogPO.class;
        infoIdxesAndDao(clazz);
    }

    @Test
    public void moodLog() throws Exception {
        Class clazz = MoodPO.class;
        infoIdxesAndDao(clazz);
    }

    @Test
    public void user() throws Exception {
        Class clazz = UserPO.class;
        infoIdxesAndDao(clazz);
    }

    @Test
    public void link() throws Exception {
        Class clazz = LinkPO.class;
        infoIdxesAndDao(clazz);
    }

    @Test
    public void image() throws Exception {
        Class clazz = ImagePO.class;
        infoIdxesAndDao(clazz);
    }

    /**
     * 生成 JSONTransferable 需要的数据, 以及 dao
     *
     * @param clazz clazz
     * @return void
     * @author Jerry.X.He
     * @date 5/20/2017 10:49 AM
     * @since 1.0
     */
    private void infoIdxesAndDao(Class clazz) throws Exception {
        String idxes = JSONTransferableUtils.generateIdxes(clazz, 3);
        info(idxes);
        String daoes = JSONTransferableUtils.generateDaoDaoImpl(clazz, JSONTransferableUtils.TYPE_MYSQL);
        info(daoes);
    }

}
