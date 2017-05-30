package com.hx.blog_v2.domain;

import com.hx.blog_v2.domain.po.*;
import com.hx.blog_v2.domain.vo.*;
import com.hx.log.util.BeanTransferUtils;
import com.hx.log.util.Tools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
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

        Class src = ResourcePO.class;
        Class dst = ResourceVO.class;

        String transfer = BeanTransferUtils.transferTo(src, dst);
        String transferList = BeanTransferUtils.transferListTo(src, dst);
        info("    // -------------------- " + src.getSimpleName() + " <-> " + dst.getSimpleName() + " --------------------------");
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
        for (BlogPO ele : src) {
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
        for (AdminBlogVO ele : src) {
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
        for (MoodPO ele : src) {
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
        for (AdminMoodVO ele : src) {
            result.add(adminMoodVO2MoodPO(ele));
        }
        return result;
    }

    // -------------------- MoodPO <-> MoodVO --------------------------
    public static MoodVO moodPO2MoodVO(MoodPO src) {
        MoodVO result = new MoodVO();
        result.setId(src.getId());
        result.setContent(src.getContent());
        result.setCreatedAt(src.getCreatedAt());
        result.setTitle(src.getTitle());
        return result;
    }

    public static Collection<MoodVO> moodPO2MoodVOList(Collection<MoodPO> src) {
        List<MoodVO> result = new ArrayList<>(src.size());
        for (MoodPO ele : src) {
            result.add(moodPO2MoodVO(ele));
        }
        return result;
    }

    public static MoodPO moodVO2MoodPO(MoodVO src) {
        MoodPO result = new MoodPO();
        result.setId(src.getId());
        result.setContent(src.getContent());
        result.setCreatedAt(src.getCreatedAt());
        result.setTitle(src.getTitle());
        return result;
    }

    public static Collection<MoodPO> moodVO2MoodPOList(Collection<MoodVO> src) {
        List<MoodPO> result = new ArrayList<>(src.size());
        for (MoodVO ele : src) {
            result.add(moodVO2MoodPO(ele));
        }
        return result;
    }


    // -------------------- UserPO <-> AdminUserVO --------------------------
    public static AdminUserVO userPO2AdminUserVO(UserPO src) {
        AdminUserVO result = new AdminUserVO();
        result.setId(src.getId());
        result.setCreatedAt(src.getCreatedAt());
        result.setLastLoginIp(src.getLastLoginIp());
        result.setLastLoginAt(src.getLastLoginAt());
        result.setUserName(src.getUserName());
        result.setNickName(src.getNickName());
        result.setHeadImgUrl(src.getHeadImgUrl());
        result.setEmail(src.getEmail());
        result.setMotto(src.getMotto());
        return result;
    }

    public static Collection<AdminUserVO> userPO2AdminUserVOList(Collection<UserPO> src) {
        List<AdminUserVO> result = new ArrayList<>(src.size());
        for (UserPO ele : src) {
            result.add(userPO2AdminUserVO(ele));
        }
        return result;
    }

    public static UserPO adminUserVO2UserPO(AdminUserVO src) {
        UserPO result = new UserPO();
        result.setId(src.getId());
        result.setCreatedAt(src.getCreatedAt());
        result.setLastLoginIp(src.getLastLoginIp());
        result.setUserName(src.getUserName());
        result.setNickName(src.getNickName());
        result.setHeadImgUrl(src.getHeadImgUrl());
        result.setEmail(src.getEmail());
        result.setMotto(src.getMotto());
        return result;
    }

    public static Collection<UserPO> adminUserVO2UserPOList(Collection<AdminUserVO> src) {
        List<UserPO> result = new ArrayList<>(src.size());
        for (AdminUserVO ele : src) {
            result.add(adminUserVO2UserPO(ele));
        }
        return result;
    }

    // -------------------- UserPO <-> AdminUserVO --------------------------
    public static AdminLinkVO linkPO2AdminLinkVO(LinkPO src) {
        AdminLinkVO result = new AdminLinkVO();
        result.setName(src.getName());
        result.setId(src.getId());
        result.setUpdatedAt(src.getUpdatedAt());
        result.setCreatedAt(src.getCreatedAt());
        result.setEnable(src.getEnable());
        result.setUrl(src.getUrl());
        result.setDesc(src.getDesc());
        result.setSort(src.getSort());
        return result;
    }

    public static Collection<AdminLinkVO> linkPO2AdminLinkVOList(Collection<LinkPO> src) {
        List<AdminLinkVO> result = new ArrayList<>(src.size());
        for (LinkPO ele : src) {
            result.add(linkPO2AdminLinkVO(ele));
        }
        return result;
    }

    public static LinkPO adminLinkVO2LinkPO(AdminLinkVO src) {
        LinkPO result = new LinkPO();
        result.setName(src.getName());
        result.setId(src.getId());
        result.setUpdatedAt(src.getUpdatedAt());
        result.setCreatedAt(src.getCreatedAt());
        result.setEnable(src.getEnable());
        result.setUrl(src.getUrl());
        result.setDesc(src.getDesc());
        result.setSort(src.getSort());
        return result;
    }

    public static Collection<LinkPO> adminLinkVO2LinkPOList(Collection<AdminLinkVO> src) {
        List<LinkPO> result = new ArrayList<>(src.size());
        for (AdminLinkVO ele : src) {
            result.add(adminLinkVO2LinkPO(ele));
        }
        return result;
    }

    // -------------------- BlogCommentPO <-> AdminCommentVO --------------------------
    public static AdminCommentVO blogCommentPO2AdminCommentVO(BlogCommentPO src) {
        AdminCommentVO result = new AdminCommentVO();
        result.setName(src.getName());
        result.setId(src.getId());
        result.setContent(src.getContent());
        result.setCreatedAt(src.getCreatedAt());
        result.setHeadImgUrl(src.getHeadImgUrl());
        result.setEmail(src.getEmail());
        result.setRole(src.getRole());
        result.setToUser(src.getToUser());
        result.setCommentId(src.getCommentId());
        result.setBlogId(src.getBlogId());
        result.setFloorId(src.getFloorId());
        return result;
    }

    public static Collection<AdminCommentVO> blogCommentPO2AdminCommentVOList(Collection<BlogCommentPO> src) {
        List<AdminCommentVO> result = new ArrayList<>(src.size());
        for (BlogCommentPO ele : src) {
            result.add(blogCommentPO2AdminCommentVO(ele));
        }
        return result;
    }

    public static BlogCommentPO adminCommentVO2BlogCommentPO(AdminCommentVO src) {
        BlogCommentPO result = new BlogCommentPO();
        result.setName(src.getName());
        result.setId(src.getId());
        result.setContent(src.getContent());
        result.setCreatedAt(src.getCreatedAt());
        result.setHeadImgUrl(src.getHeadImgUrl());
        result.setEmail(src.getEmail());
        result.setRole(src.getRole());
        result.setToUser(src.getToUser());
        result.setCommentId(src.getCommentId());
        result.setBlogId(src.getBlogId());
        result.setFloorId(src.getFloorId());
        return result;
    }

    public static Collection<BlogCommentPO> adminCommentVO2BlogCommentPOList(Collection<AdminCommentVO> src) {
        List<BlogCommentPO> result = new ArrayList<>(src.size());
        for (AdminCommentVO ele : src) {
            result.add(adminCommentVO2BlogCommentPO(ele));
        }
        return result;
    }

    // -------------------- BlogCommentPO <-> CommentVO --------------------------
    public static CommentVO blogCommentPO2CommentVO(BlogCommentPO src) {
        CommentVO result = new CommentVO();
        result.setName(src.getName());
        result.setId(src.getId());
        result.setContent(src.getContent());
        result.setCommentId(src.getCommentId());
        result.setEmail(src.getEmail());
        result.setToUser(src.getToUser());
        result.setBlogId(src.getBlogId());
        result.setHeadImgUrl(src.getHeadImgUrl());
        result.setFloorId(src.getFloorId());
        result.setCreatedAt(src.getCreatedAt());
        result.setRole(src.getRole());
        return result;
    }

    public static Collection<CommentVO> blogCommentPO2CommentVOList(Collection<BlogCommentPO> src) {
        List<CommentVO> result = new ArrayList<>(src.size());
        for (BlogCommentPO ele : src) {
            result.add(blogCommentPO2CommentVO(ele));
        }
        return result;
    }

    public static BlogCommentPO commentVO2BlogCommentPO(CommentVO src) {
        BlogCommentPO result = new BlogCommentPO();
        result.setName(src.getName());
        result.setId(src.getId());
        result.setContent(src.getContent());
        result.setCommentId(src.getCommentId());
        result.setEmail(src.getEmail());
        result.setToUser(src.getToUser());
        result.setBlogId(src.getBlogId());
        result.setHeadImgUrl(src.getHeadImgUrl());
        result.setFloorId(src.getFloorId());
        result.setCreatedAt(src.getCreatedAt());
        result.setRole(src.getRole());
        return result;
    }

    public static Collection<BlogCommentPO> commentVO2BlogCommentPOList(Collection<CommentVO> src) {
        List<BlogCommentPO> result = new ArrayList<>(src.size());
        for (CommentVO ele : src) {
            result.add(commentVO2BlogCommentPO(ele));
        }
        return result;
    }

    // -------------------- BlogPO <-> BlogVO --------------------------
    public static BlogVO blogPO2BlogVO(BlogPO src) {
        BlogVO result = new BlogVO();
        result.setId(src.getId());
        result.setCreatedAtMonth(src.getCreatedAtMonth());
        result.setAuthor(src.getAuthor());
        result.setUpdatedAt(src.getUpdatedAt());
        result.setBlogTypeId(src.getBlogTypeId());
        result.setCreatedAt(src.getCreatedAt());
        result.setCoverUrl(src.getCoverUrl());
        result.setTitle(src.getTitle());
        result.setSummary(src.getSummary());
        result.setContentUrl(src.getContentUrl());
        return result;
    }

    public static Collection<BlogVO> blogPO2BlogVOList(Collection<BlogPO> src) {
        List<BlogVO> result = new ArrayList<>(src.size());
        for (BlogPO ele : src) {
            result.add(blogPO2BlogVO(ele));
        }
        return result;
    }

    public static BlogPO blogVO2BlogPO(BlogVO src) {
        BlogPO result = new BlogPO();
        result.setId(src.getId());
        result.setCreatedAtMonth(src.getCreatedAtMonth());
        result.setAuthor(src.getAuthor());
        result.setUpdatedAt(src.getUpdatedAt());
        result.setBlogTypeId(src.getBlogTypeId());
        result.setCreatedAt(src.getCreatedAt());
        result.setCoverUrl(src.getCoverUrl());
        result.setTitle(src.getTitle());
        result.setSummary(src.getSummary());
        result.setContentUrl(src.getContentUrl());
        return result;
    }

    public static Collection<BlogPO> blogVO2BlogPOList(Collection<BlogVO> src) {
        List<BlogPO> result = new ArrayList<>(src.size());
        for (BlogVO ele : src) {
            result.add(blogVO2BlogPO(ele));
        }
        return result;
    }

    public static BlogVO blogExPO2BlogVO(BlogExPO src, BlogVO vo) {
        vo.setViewCnt(src.getViewCnt());
        vo.setCommentCnt(src.getCommentCnt());
        vo.setGoodCnt(src.getGoodCnt());
        vo.setNotGoodCnt(src.getNotGoodCnt());
        return vo;
    }

    public static Collection<BlogVO> blogExPO2BlogVOList(Collection<BlogExPO> src, Collection<BlogVO> voes) {
        Iterator<BlogVO> ite = voes.iterator();
        for (BlogExPO ele : src) {
            blogExPO2BlogVO(ele, ite.next());
        }
        return voes;
    }

    // -------------------- ImagePO <-> ImageVO --------------------------
    public static ImageVO imagePO2ImageVO(ImagePO src) {
        ImageVO result = new ImageVO();
        result.setId(src.getId());
        result.setUrl(src.getUrl());
        result.setTitle(src.getTitle());
        result.setCreatedAt(src.getCreatedAt());
        return result;
    }

    public static Collection<ImageVO> imagePO2ImageVOList(Collection<ImagePO> src) {
        List<ImageVO> result = new ArrayList<>(src.size());
        for (ImagePO ele : src) {
            result.add(imagePO2ImageVO(ele));
        }
        return result;
    }

    public static ImagePO imageVO2ImagePO(ImageVO src) {
        ImagePO result = new ImagePO();
        result.setId(src.getId());
        result.setUrl(src.getUrl());
        result.setTitle(src.getTitle());
        result.setCreatedAt(src.getCreatedAt());
        return result;
    }

    public static Collection<ImagePO> imageVO2ImagePOList(Collection<ImageVO> src) {
        List<ImagePO> result = new ArrayList<>(src.size());
        for (ImageVO ele : src) {
            result.add(imageVO2ImagePO(ele));
        }
        return result;
    }

    // -------------------- ImagePO <-> AdminImageVO --------------------------
    public static AdminImageVO imagePO2AdminImageVO(ImagePO src) {
        AdminImageVO result = new AdminImageVO();
        result.setId(src.getId());
        result.setUpdatedAt(src.getUpdatedAt());
        result.setTitle(src.getTitle());
        result.setCreatedAt(src.getCreatedAt());
        result.setEnable(src.getEnable());
        result.setUrl(src.getUrl());
        return result;
    }

    public static Collection<AdminImageVO> imagePO2AdminImageVOList(Collection<ImagePO> src) {
        List<AdminImageVO> result = new ArrayList<>(src.size());
        for (ImagePO ele : src) {
            result.add(imagePO2AdminImageVO(ele));
        }
        return result;
    }

    public static ImagePO adminImageVO2ImagePO(AdminImageVO src) {
        ImagePO result = new ImagePO();
        result.setId(src.getId());
        result.setUpdatedAt(src.getUpdatedAt());
        result.setTitle(src.getTitle());
        result.setCreatedAt(src.getCreatedAt());
        result.setEnable(src.getEnable());
        result.setUrl(src.getUrl());
        return result;
    }

    public static Collection<ImagePO> adminImageVO2ImagePOList(Collection<AdminImageVO> src) {
        List<ImagePO> result = new ArrayList<>(src.size());
        for (AdminImageVO ele : src) {
            result.add(adminImageVO2ImagePO(ele));
        }
        return result;
    }

    // -------------------- ResourcePO <-> ResourceVO --------------------------
    public static ResourceVO resourcePO2ResourceVO(ResourcePO src) {
        ResourceVO result = new ResourceVO();
        result.setName(src.getName());
        result.setId(src.getId());
        result.setUrl(src.getUrl());
        result.setSort(src.getSort());
        result.setUpdatedAt(src.getUpdatedAt());
        result.setCreatedAt(src.getCreatedAt());
        result.setEnable(src.getEnable());
        result.setIconClass(src.getIconClass());
        result.setParentId(src.getParentId());
        return result;
    }

    public static Collection<ResourceVO> resourcePO2ResourceVOList(Collection<ResourcePO> src) {
        List<ResourceVO> result = new ArrayList<>(src.size());
        for(ResourcePO ele : src) {
            result.add(resourcePO2ResourceVO(ele));
        }
        return result;
    }

    public static ResourcePO resourceVO2ResourcePO(ResourceVO src) {
        ResourcePO result = new ResourcePO();
        result.setName(src.getName());
        result.setId(src.getId());
        result.setUrl(src.getUrl());
        result.setSort(src.getSort());
        result.setUpdatedAt(src.getUpdatedAt());
        result.setCreatedAt(src.getCreatedAt());
        result.setEnable(src.getEnable());
        result.setIconClass(src.getIconClass());
        result.setParentId(src.getParentId());
        return result;
    }

    public static Collection<ResourcePO> resourceVO2ResourcePOList(Collection<ResourceVO> src) {
        List<ResourcePO> result = new ArrayList<>(src.size());
        for(ResourceVO ele : src) {
            result.add(resourceVO2ResourcePO(ele));
        }
        return result;
    }

    // -------------------- 待续 --------------------------

}
