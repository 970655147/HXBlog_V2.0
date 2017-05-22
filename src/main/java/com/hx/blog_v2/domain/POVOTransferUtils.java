package com.hx.blog_v2.domain;

import com.hx.blog_v2.domain.po.BlogPO;
import com.hx.blog_v2.domain.po.BlogTagPO;
import com.hx.blog_v2.domain.po.BlogTypePO;
import com.hx.blog_v2.domain.po.MoodPO;
import com.hx.blog_v2.domain.vo.AdminBlogVO;
import com.hx.blog_v2.domain.vo.AdminMoodVO;
import com.hx.blog_v2.domain.vo.BlogTagVO;
import com.hx.blog_v2.domain.vo.BlogTypeVO;
import com.hx.log.util.BeanTransferUtils;
import com.hx.log.util.Tools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.hx.log.util.Log.info;

/**
 * POVOTransferUtils
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/21/2017 2:18 PM
 */
public final class POVOTransferUtils {

    // disable constructor
    private POVOTransferUtils() {
        Tools.assert0("can't instantiate !");
    }

    /**
     * 生成各个转换的方法
     */
    public static void main(String[] args) {

        Class src = MoodPO.class;
        Class dst = AdminMoodVO.class;

        String transfer = BeanTransferUtils.transferTo(src, dst);
        String transferList = BeanTransferUtils.transferListTo(src, dst);
        info(transfer);
        info(transferList);

        transfer = BeanTransferUtils.transferTo(dst, src);
        transferList = BeanTransferUtils.transferListTo(dst, src);
        info(transfer);
        info(transferList);

    }


    // -------------------- 转换方法 --------------------------

    // -------------------- BlogTypePO <-> BlogTypeVO --------------------------
    public static BlogTypeVO blogTypePO2BlogTypeVO(BlogTypePO src) {
        BlogTypeVO result = new BlogTypeVO();
        result.setName(src.getName());
        result.setId(src.getId());
        result.setUpdatedAt(src.getUpdatedAt());
        result.setCreatedAt(src.getCreatedAt());
        return result;
    }

    public static Collection<BlogTypeVO> blogTypePO2BlogTypeVOList(Collection<BlogTypePO> src) {
        List<BlogTypeVO> result = new ArrayList<>(src.size());
        for (BlogTypePO ele : src) {
            result.add(blogTypePO2BlogTypeVO(ele));
        }
        return result;
    }

    public static BlogTypePO blogTypeVO2BlogTypePO(BlogTypeVO src) {
        BlogTypePO result = new BlogTypePO();
        result.setName(src.getName());
        result.setId(src.getId());
        result.setUpdatedAt(src.getUpdatedAt());
        result.setCreatedAt(src.getCreatedAt());
        return result;
    }

    public static Collection<BlogTypePO> blogTypeVO2BlogTypePOList(Collection<BlogTypeVO> src) {
        List<BlogTypePO> result = new ArrayList<>(src.size());
        for (BlogTypeVO ele : src) {
            result.add(blogTypeVO2BlogTypePO(ele));
        }
        return result;
    }

    // -------------------- BlogTagPO <-> BlogTagVO --------------------------
    public static BlogTagVO blogTagPO2BlogTagVO(BlogTagPO src) {
        BlogTagVO result = new BlogTagVO();
        result.setName(src.getName());
        result.setId(src.getId());
        result.setUpdatedAt(src.getUpdatedAt());
        result.setCreatedAt(src.getCreatedAt());
        return result;
    }

    public static Collection<BlogTagVO> blogTagPO2BlogTagVOList(Collection<BlogTagPO> src) {
        List<BlogTagVO> result = new ArrayList<>(src.size());
        for (BlogTagPO ele : src) {
            result.add(blogTagPO2BlogTagVO(ele));
        }
        return result;
    }

    public static BlogTagPO blogTagVO2BlogTagPO(BlogTagVO src) {
        BlogTagPO result = new BlogTagPO();
        result.setName(src.getName());
        result.setId(src.getId());
        result.setUpdatedAt(src.getUpdatedAt());
        result.setCreatedAt(src.getCreatedAt());
        return result;
    }

    public static Collection<BlogTagPO> blogTagVO2BlogTagPOList(Collection<BlogTagVO> src) {
        List<BlogTagPO> result = new ArrayList<>(src.size());
        for (BlogTagVO ele : src) {
            result.add(blogTagVO2BlogTagPO(ele));
        }
        return result;
    }

    // -------------------- BlogPO <-> AdminBlogVO --------------------------
    public static AdminBlogVO blogPO2AdminBlogVO(BlogPO src) {
        AdminBlogVO result = new AdminBlogVO();
        result.setId(src.getId());
        result.setCreatedAt(src.getCreatedAt());
        result.setCreatedAtMonth(src.getCreatedAtMonth());
        result.setUpdatedAt(src.getUpdatedAt());
        result.setCoverUrl(src.getCoverUrl());
        result.setBlogTypeId(src.getBlogTypeId());
        result.setTitle(src.getTitle());
        result.setAuthor(src.getAuthor());
        result.setSummary(src.getSummary());
        result.setContentUrl(src.getContentUrl());
        return result;
    }

    public static Collection<AdminBlogVO> blogPO2AdminBlogVOList(Collection<BlogPO> src) {
        List<AdminBlogVO> result = new ArrayList<>(src.size());
        for(BlogPO ele : src) {
            result.add(blogPO2AdminBlogVO(ele));
        }
        return result;
    }

    public static BlogPO adminBlogVO2BlogPO(AdminBlogVO src) {
        BlogPO result = new BlogPO();
        result.setId(src.getId());
        result.setCreatedAt(src.getCreatedAt());
        result.setUpdatedAt(src.getUpdatedAt());
        result.setCoverUrl(src.getCoverUrl());
        result.setBlogTypeId(src.getBlogTypeId());
        result.setTitle(src.getTitle());
        result.setAuthor(src.getAuthor());
        result.setSummary(src.getSummary());
        result.setContentUrl(src.getContentUrl());
        return result;
    }

    public static Collection<BlogPO> adminBlogVO2BlogPOList(Collection<AdminBlogVO> src) {
        List<BlogPO> result = new ArrayList<>(src.size());
        for(AdminBlogVO ele : src) {
            result.add(adminBlogVO2BlogPO(ele));
        }
        return result;
    }

    // -------------------- MoodPO <-> AdminMoodVO --------------------------
    public static AdminMoodVO moodPO2AdminMoodVO(MoodPO src) {
        AdminMoodVO result = new AdminMoodVO();
        result.setId(src.getId());
        result.setContent(src.getContent());
        result.setUpdatedAt(src.getUpdatedAt());
        result.setTitle(src.getTitle());
        result.setCreatedAt(src.getCreatedAt());
        result.setEnable(src.getEnable());
        return result;
    }

    public static Collection<AdminMoodVO> moodPO2AdminMoodVOList(Collection<MoodPO> src) {
        List<AdminMoodVO> result = new ArrayList<>(src.size());
        for(MoodPO ele : src) {
            result.add(moodPO2AdminMoodVO(ele));
        }
        return result;
    }

    public static MoodPO adminMoodVO2MoodPO(AdminMoodVO src) {
        MoodPO result = new MoodPO();
        result.setId(src.getId());
        result.setContent(src.getContent());
        result.setUpdatedAt(src.getUpdatedAt());
        result.setTitle(src.getTitle());
        result.setCreatedAt(src.getCreatedAt());
        result.setEnable(src.getEnable());
        return result;
    }

    public static Collection<MoodPO> adminMoodVO2MoodPOList(Collection<AdminMoodVO> src) {
        List<MoodPO> result = new ArrayList<>(src.size());
        for(AdminMoodVO ele : src) {
            result.add(adminMoodVO2MoodPO(ele));
        }
        return result;
    }


    // -------------------- 待续 --------------------------

}
