/**
 * 保存常量信息
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/21/2017 7:02 PM
 */

/**
 * 分页的时候, 页面的记录条数
 */
var pageSize = 10

/**
 * 读取文件的编码
 * @type {string}
 */
var encoding = "gbk"

/**
 * 所有的请求的路由
 *
 * @type {{}}
 */
var reqMap = {

    /**
     * 博客相关
     */
    blog: {
        add: "/admin/blog/add",
        get: "/blog/get",
        adminGet: "/admin/blog/get",
        list: "/blog/list",
        adminList: "/admin/blog/list",
        sense: "/blog/sense/sense",
        update: "/admin/blog/update",
        remove: "/admin/blog/remove"
    },

    /**
     * 博客类型相关
     */
    type: {
        add: "/admin/type/add",
        list: "/admin/type/list",
        update: "/admin/type/update",
        reSort: "/admin/type/reSort",
        remove: "/admin/type/remove"
    },

    /**
     * 博客标签相关
     */
    tag: {
        add: "/admin/tag/add",
        list: "/admin/type/list",
        update: "/admin/tag/update",
        reSort: "/admin/tag/reSort",
        remove: "/admin/tag/remove"
    },

    /**
     * 博客创建类型相关
     */
    createType: {
        add: "/admin/blog/createType/add",
        list: "/admin/blog/createType/list",
        update: "/admin/blog/createType/update",
        reSort: "/admin/blog/createType/reSort",
        remove: "/admin/blog/createType/remove"
    }
    ,

    /**
     * 用户相关
     */
    user: {
        add: "/admin/user/add",
        list: "/admin/user/list",
        update: "/admin/user/update",
        updatePwd: "/admin/user/updatePwd",
        remove: "/admin/user/remove",

        login: "/admin/user/login",
        logout: "/admin/user/logout"
    }
    ,

    /**
     * 消息相关
     */
    message: {
        add: "/admin/message/add",
        list: "/admin/message/adminList",
        update: "/admin/message/update",
        markConsumed: "/admin/message/markConsumed",
        remove: "/admin/message/remove"
    }
    ,

    /**
     * 用户角色关联相关
     */
    userRole: {
        list: "/admin/role/userRole/list",
        update: "/admin/role/userRole/update"
    }
    ,

    /**
     * 角色相关
     */
    role: {
        add: "/admin/role/add",
        list: "/admin/role/list",
        update: "/admin/role/update",
        reSort: "/admin/role/reSort",
        remove: "/admin/role/remove"
    }
    ,

    /**
     * 角色资源相关
     */
    roleResource: {
        list: "/admin/resource/roleResource/list",
        update: "/admin/resource/roleResource/update"
    },

    /**
     * 资源相关
     */
    resource: {
        add: "/admin/resource/add",
        list: "/admin/resource/list",
        adminTreeList: "/admin/resource/adminTreeList",
        update: "/admin/resource/update",
        reSort: "/admin/resource/reSort",
        remove: "/admin/resource/remove"
    },

    /**
     * 资源接口关联相关
     */
    resourceInterf: {
        list: "/admin/interf/resourceInterf/list",
        update: "/admin/interf/resourceInterf/update"
    },

    /**
     * 接口相关
     */
    interf: {
        add: "/admin/interf/add",
        list: "/admin/interf/list",
        update: "/admin/interf/update",
        reSort: "/admin/interf/reSort",
        remove: "/admin/interf/remove"
    },

    /**
     * 友情链接相关
     */
    link: {
        add: "/admin/link/add",
        list: "/admin/link/list",
        update: "/admin/link/update",
        remove: "/admin/link/remove"
    },

    /**
     * 图片相关
     */
    image: {
        add: "/admin/image/add",
        upload: "/admin/upload/image",
        headImgList: "/image/headImgList",
        list: "/admin/image/list",
        update: "/admin/image/update",
        reSort: "/admin/image/reSort",
        remove: "/admin/image/remove"
    }
    ,

    /**
     * 心情相关
     */
    mood: {
        add: "/admin/mood/add",
        list: "/admin/mood/list",
        update: "/admin/mood/update",
        remove: "/admin/mood/remove"
    },

    /**
     * 评论相关
     */
    comment: {
        add: "/comment/add",
        adminAdd: "/admin/comment/add",
        list: "/comment/list",
        adminList: "/admin/comment/list",
        commentsForFloor: "/admin/comment/comment/list",
        update: "/admin/comment/update",
        remove: "/admin/comment/remove"
    }
    ,

    /**
     * 配置相关
     */
    config: {
        add: "/admin/config/add",
        list: "/admin/config/list",
        update: "/admin/config/update",
        remove: "/admin/config/remove",
        reSort: "/admin/config/reSort"
    },

    /**
     * 首页相关
     */
    index: {
        index: "/index/index",
        adminMenu: "/admin/index/menus",
        adminStatistics: "/admin/index/statistics"
    }
    ,

    /**
     * 返回复合数据的请求
     */
    composite: {
        moodAndImages: "/composite/moodAndImages",
        typeAndTags: "/composite/typeAndTags",
        userAndRoles: "/composite/userAndRoles"
    }
    ,

    /**
     * 缓存相关
     */
    cache: {
        refreshAll: "/admin/cache/refreshAll",
        refreshAllCached: "/admin/cache/refreshAllCached",
        refreshTableCached: "/admin/cache/refreshTableCached",
        refreshLocalCached: "/admin/cache/refreshLocalCached",
        refreshStatisticsInfo: "/admin/cache/refreshStatisticsInfo",
        refreshOtherCached: "/admin/cache/refreshOtherCached",
        refreshAllConfigured: "/admin/cache/refreshAllConfigured",
        refreshSystemConfig: "/admin/cache/refreshSystemConfig",
        refreshRuleConfig: "/admin/cache/refreshRuleConfig",
        refreshFrontIdxConfig: "/admin/cache/refreshFrontIdxConfig"
    },

    /**
     * 系统管理相关
     */
    system: {
        cacheSummary: "/admin/system/cacheSummary",
        localCacheSummary: "/admin/system/localCacheSummary",
        statsSummary: "/admin/system/statsSummary"
    },

    /**
     * 其他配置
     */
    other: {
        templateUrl: "/static/main/templates.html"

    }


}



