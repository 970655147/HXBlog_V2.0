package com.hx.blog_v2.domain;

import com.hx.blog_v2.domain.common.system.SessionUser;
import com.hx.blog_v2.domain.po.front_resources.AdvPO;
import com.hx.blog_v2.domain.po.blog.*;
import com.hx.blog_v2.domain.po.front_resources.ImagePO;
import com.hx.blog_v2.domain.po.front_resources.LinkPO;
import com.hx.blog_v2.domain.po.front_resources.MoodPO;
import com.hx.blog_v2.domain.po.message.MessagePO;
import com.hx.blog_v2.domain.po.resources.InterfPO;
import com.hx.blog_v2.domain.po.resources.ResourcePO;
import com.hx.blog_v2.domain.po.resources.RolePO;
import com.hx.blog_v2.domain.po.resources.UserPO;
import com.hx.blog_v2.domain.po.rlt.RltRoleResourcePO;
import com.hx.blog_v2.domain.po.system.ExceptionLogPO;
import com.hx.blog_v2.domain.po.system.RequestLogPO;
import com.hx.blog_v2.domain.po.system.SystemConfigPO;
import com.hx.blog_v2.domain.vo.front_resources.AdminAdvVO;
import com.hx.blog_v2.domain.vo.front_resources.AdvVO;
import com.hx.blog_v2.domain.vo.blog.*;
import com.hx.blog_v2.domain.vo.front_resources.*;
import com.hx.blog_v2.domain.vo.message.MessageVO;
import com.hx.blog_v2.domain.vo.resources.AdminInterfVO;
import com.hx.blog_v2.domain.vo.resources.AdminRoleVO;
import com.hx.blog_v2.domain.vo.resources.AdminUserVO;
import com.hx.blog_v2.domain.vo.resources.ResourceVO;
import com.hx.blog_v2.domain.vo.rlt.ResourceInterfVO;
import com.hx.blog_v2.domain.vo.rlt.RltRoleResourceVO;
import com.hx.blog_v2.domain.vo.rlt.RoleResourceVO;
import com.hx.blog_v2.domain.vo.rlt.UserRoleVO;
import com.hx.blog_v2.domain.vo.system.ExceptionLogVO;
import com.hx.blog_v2.domain.vo.system.RequestLogVO;
import com.hx.blog_v2.domain.vo.system.SystemConfigVO;
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

        Class src = AdvPO.class;
        Class dst = AdminAdvVO.class;

        String transfer = BeanTransferUtils.transferTo(src, dst);
        String transferList = BeanTransferUtils.transferListTo(src, dst);
        info("// -------------------- " + src.getSimpleName() + " <-> " + dst.getSimpleName() + " --------------------------");
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
        result.setId(src.getId());
        result.setName(src.getName());
        result.setSort(src.getSort());
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
        result.setId(src.getId());
        result.setName(src.getName());
        result.setSort(src.getSort());
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
        result.setId(src.getId());
        result.setName(src.getName());
        result.setSort(src.getSort());
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
        result.setId(src.getId());
        result.setName(src.getName());
        result.setSort(src.getSort());
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
        result.setBlogCreateTypeId(src.getBlogCreateTypeId());
        result.setBlogTypeId(src.getBlogTypeId());
        result.setState(src.getState());
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
        result.setBlogCreateTypeId(src.getBlogCreateTypeId());
        result.setBlogTypeId(src.getBlogTypeId());
        result.setState(src.getState());
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
        result.setTitle(src.getTitle());
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
        result.setTitle(src.getTitle());
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
        result.setComment(src.getComment());
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
        result.setComment(src.getComment());
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
        result.setComment(src.getComment());
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
        result.setComment(src.getComment());
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
        result.setBlogCreateTypeId(src.getBlogCreateTypeId());
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
        result.setBlogCreateTypeId(src.getBlogCreateTypeId());
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
        vo.setGoodTotalCnt(src.getGoodTotalCnt());
        vo.setGoodAvgScore((src.getGoodTotalCnt() == 0) ? "0.0" :
                String.format("%.1f", (((src.getGoodTotalScore()*1.0F) / (src.getGoodTotalCnt()*5)) * 5)) );
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
        result.setTitle(src.getTitle());
        result.setUrl(src.getUrl());
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
        result.setSort(src.getSort());
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
        result.setSort(src.getSort());
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
        for (ResourcePO ele : src) {
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
        for (ResourceVO ele : src) {
            result.add(resourceVO2ResourcePO(ele));
        }
        return result;
    }

    // -------------------- RolePO <-> AdminRoleVO --------------------------
    public static AdminRoleVO rolePO2AdminRoleVO(RolePO src) {
        AdminRoleVO result = new AdminRoleVO();
        result.setId(src.getId());
        result.setName(src.getName());
        result.setSort(src.getSort());
        result.setCreatedAt(src.getCreatedAt());
        result.setEnable(src.getEnable());
        result.setUpdatedAt(src.getUpdatedAt());
        result.setDesc(src.getDesc());
        return result;
    }

    public static Collection<AdminRoleVO> rolePO2AdminRoleVOList(Collection<RolePO> src) {
        List<AdminRoleVO> result = new ArrayList<>(src.size());
        for (RolePO ele : src) {
            result.add(rolePO2AdminRoleVO(ele));
        }
        return result;
    }

    public static RolePO adminRoleVO2RolePO(AdminRoleVO src) {
        RolePO result = new RolePO();
        result.setId(src.getId());
        result.setName(src.getName());
        result.setSort(src.getSort());
        result.setCreatedAt(src.getCreatedAt());
        result.setEnable(src.getEnable());
        result.setUpdatedAt(src.getUpdatedAt());
        result.setDesc(src.getDesc());
        return result;
    }

    public static Collection<RolePO> adminRoleVO2RolePOList(Collection<AdminRoleVO> src) {
        List<RolePO> result = new ArrayList<>(src.size());
        for (AdminRoleVO ele : src) {
            result.add(adminRoleVO2RolePO(ele));
        }
        return result;
    }

    // -------------------- UserPO <-> UserRoleVO --------------------------
    public static UserRoleVO userPO2UserRoleVO(UserPO src) {
        UserRoleVO result = new UserRoleVO();
        result.setId(src.getId());
        result.setCreatedAt(src.getCreatedAt());
        result.setUserName(src.getUserName());
        result.setNickName(src.getNickName());
        result.setEmail(src.getEmail());
        result.setHeadImgUrl(src.getHeadImgUrl());
        return result;
    }

    public static Collection<UserRoleVO> userPO2UserRoleVOList(Collection<UserPO> src) {
        List<UserRoleVO> result = new ArrayList<>(src.size());
        for (UserPO ele : src) {
            result.add(userPO2UserRoleVO(ele));
        }
        return result;
    }

    public static UserPO userRoleVO2UserPO(UserRoleVO src) {
        UserPO result = new UserPO();
        result.setId(src.getId());
        result.setCreatedAt(src.getCreatedAt());
        result.setUserName(src.getUserName());
        result.setNickName(src.getNickName());
        result.setEmail(src.getEmail());
        result.setHeadImgUrl(src.getHeadImgUrl());
        return result;
    }

    public static Collection<UserPO> userRoleVO2UserPOList(Collection<UserRoleVO> src) {
        List<UserPO> result = new ArrayList<>(src.size());
        for (UserRoleVO ele : src) {
            result.add(userRoleVO2UserPO(ele));
        }
        return result;
    }

    // -------------------- RolePO <-> RoleResourceVO --------------------------
    public static RoleResourceVO rolePO2RoleResourceVO(RolePO src) {
        RoleResourceVO result = new RoleResourceVO();
        result.setName(src.getName());
        result.setId(src.getId());
        result.setCreatedAt(src.getCreatedAt());
        result.setDesc(src.getDesc());
        return result;
    }

    public static Collection<RoleResourceVO> rolePO2RoleResourceVOList(Collection<RolePO> src) {
        List<RoleResourceVO> result = new ArrayList<>(src.size());
        for (RolePO ele : src) {
            result.add(rolePO2RoleResourceVO(ele));
        }
        return result;
    }

    public static RolePO roleResourceVO2RolePO(RoleResourceVO src) {
        RolePO result = new RolePO();
        result.setName(src.getName());
        result.setId(src.getId());
        result.setCreatedAt(src.getCreatedAt());
        result.setDesc(src.getDesc());
        return result;
    }

    public static Collection<RolePO> roleResourceVO2RolePOList(Collection<RoleResourceVO> src) {
        List<RolePO> result = new ArrayList<>(src.size());
        for (RoleResourceVO ele : src) {
            result.add(roleResourceVO2RolePO(ele));
        }
        return result;
    }

    // -------------------- RltRoleResourcePO <-> RltRoleResourceVO --------------------------
    public static RltRoleResourceVO rltRoleResourcePO2RltRoleResourceVO(RltRoleResourcePO src) {
        RltRoleResourceVO result = new RltRoleResourceVO();
        result.setId(src.getId());
        result.setResourceId(src.getResourceId());
        result.setRoleId(src.getRoleId());
        return result;
    }

    public static Collection<RltRoleResourceVO> rltRoleResourcePO2RltRoleResourceVOList(Collection<RltRoleResourcePO> src) {
        List<RltRoleResourceVO> result = new ArrayList<>(src.size());
        for (RltRoleResourcePO ele : src) {
            result.add(rltRoleResourcePO2RltRoleResourceVO(ele));
        }
        return result;
    }

    public static RltRoleResourcePO rltRoleResourceVO2RltRoleResourcePO(RltRoleResourceVO src) {
        RltRoleResourcePO result = new RltRoleResourcePO();
        result.setId(src.getId());
        result.setResourceId(src.getResourceId());
        result.setRoleId(src.getRoleId());
        return result;
    }

    public static Collection<RltRoleResourcePO> rltRoleResourceVO2RltRoleResourcePOList(Collection<RltRoleResourceVO> src) {
        List<RltRoleResourcePO> result = new ArrayList<>(src.size());
        for (RltRoleResourceVO ele : src) {
            result.add(rltRoleResourceVO2RltRoleResourcePO(ele));
        }
        return result;
    }

    // -------------------- ResourcePO <-> ResourceInterfVO --------------------------
    public static ResourceInterfVO resourcePO2ResourceInterfVO(ResourcePO src) {
        ResourceInterfVO result = new ResourceInterfVO();
        result.setName(src.getName());
        result.setId(src.getId());
        result.setParentId(src.getParentId());
        result.setUrl(src.getUrl());
        result.setLevel(src.getLevel());
        result.setCreatedAt(src.getCreatedAt());
        return result;
    }

    public static Collection<ResourceInterfVO> resourcePO2ResourceInterfVOList(Collection<ResourcePO> src) {
        List<ResourceInterfVO> result = new ArrayList<>(src.size());
        for (ResourcePO ele : src) {
            result.add(resourcePO2ResourceInterfVO(ele));
        }
        return result;
    }

    public static ResourcePO resourceInterfVO2ResourcePO(ResourceInterfVO src) {
        ResourcePO result = new ResourcePO();
        result.setName(src.getName());
        result.setId(src.getId());
        result.setParentId(src.getParentId());
        result.setUrl(src.getUrl());
        result.setLevel(src.getLevel());
        result.setCreatedAt(src.getCreatedAt());
        return result;
    }

    public static Collection<ResourcePO> resourceInterfVO2ResourcePOList(Collection<ResourceInterfVO> src) {
        List<ResourcePO> result = new ArrayList<>(src.size());
        for (ResourceInterfVO ele : src) {
            result.add(resourceInterfVO2ResourcePO(ele));
        }
        return result;
    }

    // -------------------- InterfPO <-> AdminInterfVO --------------------------
    public static AdminInterfVO interfPO2AdminInterfVO(InterfPO src) {
        AdminInterfVO result = new AdminInterfVO();
        result.setName(src.getName());
        result.setId(src.getId());
        result.setEnable(src.getEnable());
        result.setUpdatedAt(src.getUpdatedAt());
        result.setCreatedAt(src.getCreatedAt());
        result.setDesc(src.getDesc());
        result.setSort(src.getSort());
        return result;
    }

    public static Collection<AdminInterfVO> interfPO2AdminInterfVOList(Collection<InterfPO> src) {
        List<AdminInterfVO> result = new ArrayList<>(src.size());
        for (InterfPO ele : src) {
            result.add(interfPO2AdminInterfVO(ele));
        }
        return result;
    }

    public static InterfPO adminInterfVO2InterfPO(AdminInterfVO src) {
        InterfPO result = new InterfPO();
        result.setName(src.getName());
        result.setId(src.getId());
        result.setEnable(src.getEnable());
        result.setUpdatedAt(src.getUpdatedAt());
        result.setCreatedAt(src.getCreatedAt());
        result.setDesc(src.getDesc());
        result.setSort(src.getSort());
        return result;
    }

    public static Collection<InterfPO> adminInterfVO2InterfPOList(Collection<AdminInterfVO> src) {
        List<InterfPO> result = new ArrayList<>(src.size());
        for (AdminInterfVO ele : src) {
            result.add(adminInterfVO2InterfPO(ele));
        }
        return result;
    }

    // -------------------- UserPO <-> SessionUser --------------------------
    public static SessionUser userPO2SessionUser(UserPO src) {
        SessionUser result = new SessionUser();
        result.setId(src.getId());
        result.setTitle(src.getTitle());
        result.setName(src.getUserName());
        result.setHeadImgUrl(src.getHeadImgUrl());
        result.setEmail(src.getEmail());
        return result;
    }

    public static Collection<SessionUser> userPO2SessionUserList(Collection<UserPO> src) {
        List<SessionUser> result = new ArrayList<>(src.size());
        for (UserPO ele : src) {
            result.add(userPO2SessionUser(ele));
        }
        return result;
    }

    public static UserPO sessionUser2UserPO(SessionUser src) {
        UserPO result = new UserPO();
        result.setId(src.getId());
        result.setTitle(src.getTitle());
        result.setUserName(src.getName());
        result.setHeadImgUrl(src.getHeadImgUrl());
        result.setEmail(src.getEmail());
        return result;
    }

    public static Collection<UserPO> sessionUser2UserPOList(Collection<SessionUser> src) {
        List<UserPO> result = new ArrayList<>(src.size());
        for (SessionUser ele : src) {
            result.add(sessionUser2UserPO(ele));
        }
        return result;
    }

    // -------------------- MessagePO <-> MessageVO --------------------------
    public static MessageVO messagePO2MessageVO(MessagePO src) {
        MessageVO result = new MessageVO();
        result.setId(src.getId());
        result.setType(src.getType());
        result.setContent(src.getContent());
        result.setCreatedAt(src.getCreatedAt());
        result.setUpdatedAt(src.getUpdatedAt());
        result.setReceiverId(src.getReceiverId());
        result.setConsumed(src.getConsumed());
        result.setSenderId(src.getSenderId());
        result.setSubject(src.getSubject());
        return result;
    }

    public static Collection<MessageVO> messagePO2MessageVOList(Collection<MessagePO> src) {
        List<MessageVO> result = new ArrayList<>(src.size());
        for (MessagePO ele : src) {
            result.add(messagePO2MessageVO(ele));
        }
        return result;
    }

    public static MessagePO messageVO2MessagePO(MessageVO src) {
        MessagePO result = new MessagePO();
        result.setId(src.getId());
        result.setType(src.getType());
        result.setContent(src.getContent());
        result.setCreatedAt(src.getCreatedAt());
        result.setUpdatedAt(src.getUpdatedAt());
        result.setReceiverId(src.getReceiverId());
        result.setConsumed(src.getConsumed());
        result.setSenderId(src.getSenderId());
        result.setSubject(src.getSubject());
        return result;
    }

    public static Collection<MessagePO> messageVO2MessagePOList(Collection<MessageVO> src) {
        List<MessagePO> result = new ArrayList<>(src.size());
        for (MessageVO ele : src) {
            result.add(messageVO2MessagePO(ele));
        }
        return result;
    }

    // -------------------- BlogCreateTypePO <-> BlogCreateTypeVO --------------------------
    public static BlogCreateTypeVO blogCreateTypePO2BlogCreateTypeVO(BlogCreateTypePO src) {
        BlogCreateTypeVO result = new BlogCreateTypeVO();
        result.setName(src.getName());
        result.setId(src.getId());
        result.setSort(src.getSort());
        result.setCreatedAt(src.getCreatedAt());
        result.setDesc(src.getDesc());
        result.setImgUrl(src.getImgUrl());
        return result;
    }

    public static Collection<BlogCreateTypeVO> blogCreateTypePO2BlogCreateTypeVOList(Collection<BlogCreateTypePO> src) {
        List<BlogCreateTypeVO> result = new ArrayList<>(src.size());
        for (BlogCreateTypePO ele : src) {
            result.add(blogCreateTypePO2BlogCreateTypeVO(ele));
        }
        return result;
    }

    public static BlogCreateTypePO blogCreateTypeVO2BlogCreateTypePO(BlogCreateTypeVO src) {
        BlogCreateTypePO result = new BlogCreateTypePO();
        result.setName(src.getName());
        result.setId(src.getId());
        result.setSort(src.getSort());
        result.setCreatedAt(src.getCreatedAt());
        result.setDesc(src.getDesc());
        result.setImgUrl(src.getImgUrl());
        return result;
    }

    public static Collection<BlogCreateTypePO> blogCreateTypeVO2BlogCreateTypePOList(Collection<BlogCreateTypeVO> src) {
        List<BlogCreateTypePO> result = new ArrayList<>(src.size());
        for (BlogCreateTypeVO ele : src) {
            result.add(blogCreateTypeVO2BlogCreateTypePO(ele));
        }
        return result;
    }

    // -------------------- SystemConfigPO <-> SystemConfigVO --------------------------
    public static SystemConfigVO systemConfigPO2SystemConfigVO(SystemConfigPO src) {
        SystemConfigVO result = new SystemConfigVO();
        result.setValue(src.getValue());
        result.setId(src.getId());
        result.setName(src.getName());
        result.setCreatedAt(src.getCreatedAt());
        result.setSort(src.getSort());
        result.setEnable(src.getEnable());
        result.setDesc(src.getDesc());
        return result;
    }

    public static Collection<SystemConfigVO> systemConfigPO2SystemConfigVOList(Collection<SystemConfigPO> src) {
        List<SystemConfigVO> result = new ArrayList<>(src.size());
        for (SystemConfigPO ele : src) {
            result.add(systemConfigPO2SystemConfigVO(ele));
        }
        return result;
    }

    public static SystemConfigPO systemConfigVO2SystemConfigPO(SystemConfigVO src) {
        SystemConfigPO result = new SystemConfigPO();
        result.setValue(src.getValue());
        result.setId(src.getId());
        result.setName(src.getName());
        result.setCreatedAt(src.getCreatedAt());
        result.setSort(src.getSort());
        result.setEnable(src.getEnable());
        result.setDesc(src.getDesc());
        return result;
    }

    public static Collection<SystemConfigPO> systemConfigVO2SystemConfigPOList(Collection<SystemConfigVO> src) {
        List<SystemConfigPO> result = new ArrayList<>(src.size());
        for (SystemConfigVO ele : src) {
            result.add(systemConfigVO2SystemConfigPO(ele));
        }
        return result;
    }

    // -------------------- RequestLogPO <-> RequestLogVO --------------------------
    public static RequestLogVO requestLogPO2RequestLogVO(RequestLogPO src) {
        RequestLogVO result = new RequestLogVO();
        result.setName(src.getName());
        result.setId(src.getId());
        result.setHandler(src.getHandler());
        result.setEmail(src.getEmail());
        result.setUrl(src.getUrl());
        result.setCreatedAt(src.getCreatedAt());
        result.setIsSystemUser(src.getIsSystemUser());
        result.setParams(src.getParams());
        result.setRequestIp(src.getRequestIp());
        result.setCost(src.getCost());
        return result;
    }

    public static Collection<RequestLogVO> requestLogPO2RequestLogVOList(Collection<RequestLogPO> src) {
        List<RequestLogVO> result = new ArrayList<>(src.size());
        for (RequestLogPO ele : src) {
            result.add(requestLogPO2RequestLogVO(ele));
        }
        return result;
    }

    public static RequestLogPO requestLogVO2RequestLogPO(RequestLogVO src) {
        RequestLogPO result = new RequestLogPO();
        result.setName(src.getName());
        result.setId(src.getId());
        result.setHandler(src.getHandler());
        result.setEmail(src.getEmail());
        result.setUrl(src.getUrl());
        result.setCreatedAt(src.getCreatedAt());
        result.setIsSystemUser(src.getIsSystemUser());
        result.setParams(src.getParams());
        result.setRequestIp(src.getRequestIp());
        result.setCost(src.getCost());
        return result;
    }

    public static Collection<RequestLogPO> requestLogVO2RequestLogPOList(Collection<RequestLogVO> src) {
        List<RequestLogPO> result = new ArrayList<>(src.size());
        for (RequestLogVO ele : src) {
            result.add(requestLogVO2RequestLogPO(ele));
        }
        return result;
    }

    // -------------------- ExceptionLogPO <-> ExceptionLogVO --------------------------
    public static ExceptionLogVO exceptionLogPO2ExceptionLogVO(ExceptionLogPO src) {
        ExceptionLogVO result = new ExceptionLogVO();
        result.setName(src.getName());
        result.setId(src.getId());
        result.setHandler(src.getHandler());
        result.setHeaders(src.getHeaders());
        result.setParams(src.getParams());
        result.setRequestIp(src.getRequestIp());
        result.setIsSystemUser(src.getIsSystemUser());
        result.setUrl(src.getUrl());
        result.setCreatedAt(src.getCreatedAt());
        result.setEmail(src.getEmail());
        result.setMsg(src.getMsg());
        return result;
    }

    public static Collection<ExceptionLogVO> exceptionLogPO2ExceptionLogVOList(Collection<ExceptionLogPO> src) {
        List<ExceptionLogVO> result = new ArrayList<>(src.size());
        for (ExceptionLogPO ele : src) {
            result.add(exceptionLogPO2ExceptionLogVO(ele));
        }
        return result;
    }

    public static ExceptionLogPO exceptionLogVO2ExceptionLogPO(ExceptionLogVO src) {
        ExceptionLogPO result = new ExceptionLogPO();
        result.setName(src.getName());
        result.setId(src.getId());
        result.setHandler(src.getHandler());
        result.setHeaders(src.getHeaders());
        result.setParams(src.getParams());
        result.setRequestIp(src.getRequestIp());
        result.setIsSystemUser(src.getIsSystemUser());
        result.setUrl(src.getUrl());
        result.setCreatedAt(src.getCreatedAt());
        result.setEmail(src.getEmail());
        result.setMsg(src.getMsg());
        return result;
    }

    public static Collection<ExceptionLogPO> exceptionLogVO2ExceptionLogPOList(Collection<ExceptionLogVO> src) {
        List<ExceptionLogPO> result = new ArrayList<>(src.size());
        for (ExceptionLogVO ele : src) {
            result.add(exceptionLogVO2ExceptionLogPO(ele));
        }
        return result;
    }

    // -------------------- AdvPO <-> AdvVO --------------------------
    public static AdvVO advPO2AdvVO(AdvPO src) {
        AdvVO result = new AdvVO();
        result.setName(src.getName());
        result.setType(src.getType());
        result.setParams(src.getParams());
        result.setProvider(src.getProvider());
        return result;
    }

    public static Collection<AdvVO> advPO2AdvVOList(Collection<AdvPO> src) {
        List<AdvVO> result = new ArrayList<>(src.size());
        for(AdvPO ele : src) {
            result.add(advPO2AdvVO(ele));
        }
        return result;
    }

    public static AdvPO advVO2AdvPO(AdvVO src) {
        AdvPO result = new AdvPO();
        result.setName(src.getName());
        result.setType(src.getType());
        result.setParams(src.getParams());
        result.setProvider(src.getProvider());
        return result;
    }

    public static Collection<AdvPO> advVO2AdvPOList(Collection<AdvVO> src) {
        List<AdvPO> result = new ArrayList<>(src.size());
        for(AdvVO ele : src) {
            result.add(advVO2AdvPO(ele));
        }
        return result;
    }

    // -------------------- AdvPO <-> AdminAdvVO --------------------------
    public static AdminAdvVO advPO2AdminAdvVO(AdvPO src) {
        AdminAdvVO result = new AdminAdvVO();
        result.setName(src.getName());
        result.setId(src.getId());
        result.setType(src.getType());
        result.setUpdatedAt(src.getUpdatedAt());
        result.setSort(src.getSort());
        result.setCreatedAt(src.getCreatedAt());
        result.setPathMatch(src.getPathMatch());
        result.setProvider(src.getProvider());
        result.setParams(src.getParams());
        return result;
    }

    public static Collection<AdminAdvVO> advPO2AdminAdvVOList(Collection<AdvPO> src) {
        List<AdminAdvVO> result = new ArrayList<>(src.size());
        for(AdvPO ele : src) {
            result.add(advPO2AdminAdvVO(ele));
        }
        return result;
    }

    public static AdvPO adminAdvVO2AdvPO(AdminAdvVO src) {
        AdvPO result = new AdvPO();
        result.setName(src.getName());
        result.setId(src.getId());
        result.setType(src.getType());
        result.setUpdatedAt(src.getUpdatedAt());
        result.setSort(src.getSort());
        result.setCreatedAt(src.getCreatedAt());
        result.setPathMatch(src.getPathMatch());
        result.setProvider(src.getProvider());
        result.setParams(src.getParams());
        return result;
    }

    public static Collection<AdvPO> adminAdvVO2AdvPOList(Collection<AdminAdvVO> src) {
        List<AdvPO> result = new ArrayList<>(src.size());
        for(AdminAdvVO ele : src) {
            result.add(adminAdvVO2AdvPO(ele));
        }
        return result;
    }

    // -------------------- 待续 --------------------------

}
