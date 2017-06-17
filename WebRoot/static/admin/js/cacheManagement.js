/**
 * moodsManage.js
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/24/2017 9:56 PM
 */

layui.define(['element', 'laypage', 'layer', 'form', 'upload'], function (exports) {
    var $ = layui.jquery;
    var layer = layui.layer;

    /**
     * 增加 tips
     */
    $("[name='refreshAll']").hover(function () {
        layer.tips("全部缓存 [⑴ + ⑵]", "[name='refreshAll']", {
            tips: [1, '#ff0000'],
            time: 1000
        })
    }, function () {
    })

    $("[name='allCached']").hover(function () {
        layer.tips("CacheContext [① + ② + ③ + ④]", "[name='allCached']", {
            tips: [1, '#ff0000'],
            time: 1000
        })
    }, function () {
    })
    $("[name='tableCached']").hover(function () {
        layer.tips("[type, tag, link, role, resource, interf, blogCreateType]", "[name='tableCached']", {
            tips: [1, '#ff0000'],
            time: 1000
        })
    }, function () {
    })
    $("[name='localCached']").hover(function () {
        layer.tips("[uploadFile, roleIds2ResIds, res2Interfs, info2sense, id2blogEx, info2VisitLog]", "[name='localCached']", {
            tips: [1, '#ff0000'],
            time: 1000
        })
    }, function () {
    })
    $("[name='statisticsInfo']").hover(function () {
        layer.tips("[allStatistics, all5SecStatistics]", "[name='statisticsInfo']", {
            tips: [1, '#ff0000'],
            time: 1000
        })
    }, function () {
    })
    $("[name='otherCached']").hover(function () {
        layer.tips("[floorIdx, commentIdx, forceOffLine]", "[name='otherCached']", {
            tips: [1, '#ff0000'],
            time: 1000
        })
    }, function () {
    })

    $("[name='allConfigured']").hover(function () {
        layer.tips("ConstantsContext [① + ② + ③]", "[name='allConfigured']", {
            tips: [1, '#ff0000'],
            time: 1000
        })
    }, function () {
    })
    $("[name='systemConfig']").hover(function () {
        layer.tips("[systemConfig]", "[name='systemConfig']", {
            tips: [1, '#ff0000'],
            time: 1000
        })
    }, function () {
    })
    $("[name='ruleConfig']").hover(function () {
        layer.tips("[ruleConfig]", "[name='ruleConfig']", {
            tips: [1, '#ff0000'],
            time: 1000
        })
    }, function () {
    })
    $("[name='frontIdxConfig']").hover(function () {
        layer.tips("[frontIdxConfig]", "[name='frontIdxConfig']", {
            tips: [1, '#ff0000'],
            time: 1000
        })
    }, function () {
    })

    var funcs = {
        refreshAll: function () {
            $.ajax({
                url: "/admin/cache/refreshAll",
                data: {},
                success: function (resp) {
                    if (resp.success) {
                        layer.alert("刷新缓存配置成功 !")
                    } else {
                        layer.alert("刷新缓存配置出现了点问题[" + resp.data + "], 请联系管理员 !")
                    }
                }
            })
        },
        refreshAllCached: function () {
            $.ajax({
                url: "/admin/cache/refreshAllCached",
                data: {},
                success: function (resp) {
                    if (resp.success) {
                        layer.alert("刷新CacheContext的缓存成功 !")
                    } else {
                        layer.alert("刷新CacheContext的缓存出现了点问题[" + resp.data + "], 请联系管理员 !")
                    }
                }
            })
        },
        refreshTableCached: function () {
            $.ajax({
                url: "/admin/cache/refreshTableCached",
                data: {},
                success: function (resp) {
                    if (resp.success) {
                        layer.alert("刷新全表缓存配置成功 !")
                    } else {
                        layer.alert("刷新全表缓存配置出现了点问题[" + resp.data + "], 请联系管理员 !")
                    }
                }
            })
        },
        refreshLocalCached: function () {
            $.ajax({
                url: "/admin/cache/refreshLocalCached",
                data: {},
                success: function (resp) {
                    if (resp.success) {
                        layer.alert("刷新局部缓存配置成功 !")
                    } else {
                        layer.alert("刷新局部缓存配置出现了点问题[" + resp.data + "], 请联系管理员 !")
                    }
                }
            })
        },
        refreshStatisticsInfo: function () {
            $.ajax({
                url: "/admin/cache/refreshStatisticsInfo",
                data: {},
                success: function (resp) {
                    if (resp.success) {
                        layer.alert("刷新统计缓存配置成功 !")
                    } else {
                        layer.alert("刷新统计缓存配置出现了点问题[" + resp.data + "], 请联系管理员 !")
                    }
                }
            })
        },
        refreshOtherCached: function () {
            $.ajax({
                url: "/admin/cache/refreshOtherCached",
                data: {},
                success: function (resp) {
                    if (resp.success) {
                        layer.alert("刷新其他配置缓存成功 !")
                    } else {
                        layer.alert("刷新其他配置缓存出现了点问题[" + resp.data + "], 请联系管理员 !")
                    }
                }
            })
        },

        refreshAllConfigured: function () {
            $.ajax({
                url: "/admin/cache/refreshAllConfigured",
                data: {},
                success: function (resp) {
                    if (resp.success) {
                        layer.alert("刷新ConstantsContext的缓存成功 !")
                    } else {
                        layer.alert("刷新ConstantsContext的缓存出现了点问题[" + resp.data + "], 请联系管理员 !")
                    }
                }
            })
        },
        refreshSystemConfig: function () {
            $.ajax({
                url: "/admin/cache/refreshSystemConfig",
                data: {},
                success: function (resp) {
                    if (resp.success) {
                        layer.alert("刷新系统缓存配置成功 !")
                    } else {
                        layer.alert("刷新系统缓存配置出现了点问题[" + resp.data + "], 请联系管理员 !")
                    }
                }
            })
        },
        refreshRuleConfig: function () {
            $.ajax({
                url: "/admin/cache/refreshRuleConfig",
                data: {},
                success: function (resp) {
                    if (resp.success) {
                        layer.alert("刷新规则配置缓存配置成功 !")
                    } else {
                        layer.alert("刷新规则配置缓存配置出现了点问题[" + resp.data + "], 请联系管理员 !")
                    }
                }
            })
        },
        refreshFrontIdxConfig: function () {
            $.ajax({
                url: "/admin/cache/refreshFrontIdxConfig",
                data: {},
                success: function (resp) {
                    if (resp.success) {
                        layer.alert("刷新前台配置缓存配置成功 !")
                    } else {
                        layer.alert("刷新前台配置缓存配置出现了点问题[" + resp.data + "], 请联系管理员 !")
                    }
                }
            })
        }

    }
    exports('funcs', funcs);

});
