var element;
var $;

/**
 * 刷新统计的时间间隔
 */
var statsInterval = 5;
var statsIntervalInMs = statsInterval * 1000;

// 初始化菜单列表, 同步加载, 否则 可能 layui 绑定不了事件
initMenu()
refreshStatistics()
initTips();
$("#logout").click(function () {
    logout();
})

layui.define(['element', 'layer', 'util', 'pagesize', 'form'], function (exports) {
    $ = layui.jquery;
    element = layui.element();
    var layer = layui.layer;
    var util = layui.util;
    var form = layui.form();
    //form.render();

    //快捷菜单开关
    $('span.sys-title').click(function (e) {
        e.stopPropagation();    //阻止事件冒泡
        $('div.short-menu').slideToggle('fast');
    });
    $('div.short-menu').click(function (e) {
        e.stopPropagation();    //阻止事件冒泡
    });
    $(document).click(function () {
        $('div.short-menu').slideUp('fast');
        $('.individuation').removeClass('bounceInRight').addClass('flipOutY');
    });

    //个性化设置开关
    $('#individuation').click(function (e) {
        e.stopPropagation();    //阻止事件冒泡
        $('.individuation').removeClass('layui-hide').toggleClass('bounceInRight').toggleClass('flipOutY');
    });
    $('.individuation').click(function (e) {
        e.stopPropagation();    //阻止事件冒泡
    })
    $('.layui-body').click(function () {
        $('.individuation').removeClass('bounceInRight').addClass('flipOutY');
    });

    //监听左侧导航点击
    element.on('nav(leftNav)', function (elem) {
        var url = $(elem).children('a').attr('data-url');   //页面url
        var dataId = $(elem).children('a').attr('data-id');     //tab唯一Id
        var title = $(elem).children('a').text();           //菜单名称
        if (title === "首页") {
            element.tabChange('tab', 0);
            return;
        }
        if (url === undefined) {
            return;
        }

        switchTab($, element, title, url, dataId);
    });


    //监听侧边导航开关
    form.on('switch(sidenav)', function (data) {
        if (data.elem.checked) {
            showSideNav();
            layer.msg('这个开关是layui的开关改编的');
        } else {
            hideSideNav();
        }
    });

    //收起侧边导航点击事件
    $('.layui-side-hide').click(function () {
        hideSideNav();
        $('input[lay-filter=sidenav]').siblings('.layui-form-switch').removeClass('layui-form-onswitch');
        $('input[lay-filter=sidenav]').prop("checked", false);
    });

    //鼠标靠左展开侧边导航
    $(document).mousemove(function (e) {
        if (e.pageX === 0) {
            showSideNav();

            var sideNavEle = $('input[lay-filter=sidenav]')
            sideNavEle.siblings('.layui-form-switch').addClass('layui-form-onswitch');
            sideNavEle.prop("checked", true);
        }
    });

    //皮肤切换
    $('.skin').click(function () {
        var skin = $(this).attr("data-skin");

        var bodyEle = $('body')
        bodyEle.removeClass();
        bodyEle.addClass(skin);
    });

    var ishide = false;
    // 隐藏侧边导航
    function hideSideNav() {
        if (!ishide) {
            $('.layui-side').animate({left: '-200px'});
            $('.layui-side-hide').animate({left: '-200px'});
            $('.layui-body').animate({left: '0px'});
            $('.layui-footer').animate({left: '0px'});
            var tishi = layer.msg('鼠标靠左自动显示菜单', {time: 1500});
            layer.style(tishi, {
                top: 'auto',
                bottom: '50px'
            });
            ishide = true;
        }
    }

    //显示侧边导航
    function showSideNav() {
        if (ishide) {
            $('.layui-side').animate({left: '0px'});
            $('.layui-side-hide').animate({left: '0px'});
            $('.layui-body').animate({left: '200px'});
            $('.layui-footer').animate({left: '200px'});
            ishide = false;
        }
    }

    /**
     * 是否停止管家
     */
    var isStop = true

    runSteward();
    //管家功能
    function runSteward() {
        var layerSteward;

        getNotReplyLeaveMessage();
        var interval = setTimeout(function () {
            getNotReplyLeaveMessage();
        }, 60000);

        function getNotReplyLeaveMessage() {
            var respInfo = []
            ajax({
                url: reqMap.message.unread,
                async: false,
                type: "GET",
                success: function (resp) {
                    if (resp.success) {
                        respInfo = resp.data
                    }
                }
            })

            if ((isStop) && (respInfo.cnt > 0)) {
                var content = '<p>目前有<span>' + respInfo.cnt + '</span>条消息<a href="javascript:void(0)" onclick="layui.funcs.toViewMessage()" >点击查看</a></p>';
                for (var idx in respInfo.list) {
                    var toRead = respInfo.list[idx]
                    content += '<span>' + toRead.left + ' : <span style="display:inline;" >' + toRead.right + '</span></span>';
                }
                content += '<div class="notnotice" >不再提醒</div>';
                layerSteward = layer.open({
                    type: 1,
                    title: '管家提醒',
                    shade: 0,
                    resize: false,
                    area: ['340px', '215px'],
                    // time: 10000, //10秒后自动关闭
                    skin: 'steward',
                    closeBtn: 1,
                    anim: 2,
                    content: content,
                    end: function () {

                    }
                });
                $('.steward').click(function (e) {
                    event.stopPropagation();    //阻止事件冒泡
                });
                $('.notnotice').click(function () {
                    isStop = true;
                    layer.close(layerSteward);

                    var stewardEle = $('input[lay-filter=steward]')
                    stewardEle.siblings('.layui-form-switch').removeClass('layui-form-onswitch');
                    stewardEle.prop("checked", false);
                });
                form.on('switch(steward)', function (data) {
                    if (data.elem.checked) {
                        isStop = false;
                    } else {
                        isStop = true;
                        layer.close(layerSteward);
                    }
                })
            }
        }
    }

    /**
     * 切换选项卡
     * @param $
     * @param element
     * @param title
     * @param url
     * @param id
     */
    function switchTab($, element, title, url, id) {
        var tabTitleDiv = $('.layui-tab[lay-filter=\'tab\']').children('.layui-tab-title')
        var exist = tabTitleDiv.find('li[lay-id=' + id + ']')
        if (exist.length > 0) {
            element.tabChange('tab', id)
        } else {
            var index = layer.load(1)
            //由于Ajax调用本地静态页面存在跨域问题，这里用iframe
            setTimeout(function () {
                layer.close(index);
                element.tabAdd('tab', {
                    title: title,
                    content: '<iframe src="' + url + '" style="width:100%;height:100%;border:none;outline:none;"></iframe>',
                    id: id
                })
                //切换到指定索引的卡片
                element.tabChange('tab', id)
            }, 500)
        }
    }

    var funcs = {
        /**
         * 查看消息的函数
         */
        toViewMessage: function () {
            var viewMessageUrl = "/static/admin/messageView.html"
            var viewMessageEle = $("[data-url='" + viewMessageUrl + "']")

            var url = viewMessageUrl
            var dataId = viewMessageEle.attr('data-id')
            var title = viewMessageEle.text()
            switchTab($, element, title, url, dataId);
        }
    }
    exports('funcs', funcs);

})


/**
 * 初始化菜单
 */
function initMenu() {
    ajax({
        url: reqMap.index.adminMenu,
        data: {},
        async: false,
        type: "GET",
        success: function (resp) {
            if (resp.success) {
                var root = resp.data
                if ("#root" === root.name) {
                    var topLevel = root.childs
                    var html = ''
                    for (idx in topLevel) {
                        var menu = topLevel[idx]
                        var subMenus = menu.childs
                        html += '<li class="layui-nav-item ">'
                        html += '<a href="javascript:void(0);">'
                        html += '<i class="' + menu.iconClass + '"></i>&nbsp;&nbsp;' + menu.name + ''
                        html += '<span class="layui-nav-more"></span>'
                        html += '</a>'
                        html += '<dl class="layui-nav-child">'
                        for (idxOfSub in subMenus) {
                            var subMenu = subMenus[idxOfSub]
                            html += '<dd><a href="javascript:void(0);" class="' + subMenu.iconClass + '" data-url="' + subMenu.url + '" data-id="' + subMenu.id + '">&nbsp;&nbsp;' + subMenu.name + '</a></dd>'
                        }
                        html += '</dl>'
                        html += '</li>'
                    }
                    $("#leftNav").append(html)
                }
            }
        }
    });
}

/**
 * 初始化首页统计数据
 */
function refreshStatistics() {
    ajax({
        url: reqMap.index.adminStatistics,
        data: {},
        type: "GET",
        success: function (resp) {
            if (resp.success) {
                var loginInfo = resp.data.loginInfo
                $("[name='loginIp']").text(loginInfo.loginIp)
                $("[name='loginAddr']").text(loginInfo.loginAddr)
                $("[name='loginDate']").text(loginInfo.loginDate)

                var todayStats = resp.data.todayStats
                $("[name='todayInfo'] [name='requestLogCnt']").text(todayStats.requestLogCnt)
                $("[name='todayInfo'] [name='exceptionLogCnt']").text(todayStats.exceptionLogCnt)
                $("[name='todayInfo'] [name='dayFlushViewCnt']").text(todayStats.dayFlushViewCnt)
                $("[name='todayInfo'] [name='viewCnt']").text(todayStats.viewCnt)
                $("[name='todayInfo'] [name='blogCnt']").text(todayStats.blogCnt)
                $("[name='todayInfo'] [name='commentCnt']").text(todayStats.commentCnt)
                $("[name='todayInfo'] [name='goodCnt']").text(todayStats.goodCnt)
                $("[name='todayInfo'] [name='notGoodCnt']").text(todayStats.notGoodCnt)
                var recentlyStats = resp.data.recentlyStats
                $("[name='recentlyInfo'] [name='requestLogCnt']").text(recentlyStats.requestLogCnt + todayStats.requestLogCnt)
                $("[name='recentlyInfo'] [name='exceptionLogCnt']").text(recentlyStats.exceptionLogCnt + todayStats.exceptionLogCnt)
                $("[name='recentlyInfo'] [name='dayFlushViewCnt']").text(recentlyStats.dayFlushViewCnt + todayStats.dayFlushViewCnt)
                $("[name='recentlyInfo'] [name='viewCnt']").text(recentlyStats.viewCnt + todayStats.viewCnt)
                $("[name='recentlyInfo'] [name='blogCnt']").text(recentlyStats.blogCnt + todayStats.blogCnt)
                $("[name='recentlyInfo'] [name='commentCnt']").text(recentlyStats.commentCnt + todayStats.commentCnt)
                $("[name='recentlyInfo'] [name='goodCnt']").text(recentlyStats.goodCnt + todayStats.goodCnt)
                $("[name='recentlyInfo'] [name='notGoodCnt']").text(recentlyStats.notGoodCnt + todayStats.notGoodCnt)
                var sumStats = resp.data.sumStats
                $("[name='sumInfo'] [name='requestLogCnt']").text(sumStats.requestLogCnt + todayStats.requestLogCnt)
                $("[name='sumInfo'] [name='exceptionLogCnt']").text(sumStats.exceptionLogCnt + todayStats.exceptionLogCnt)
                $("[name='sumInfo'] [name='dayFlushViewCnt']").text(sumStats.dayFlushViewCnt + todayStats.dayFlushViewCnt)
                $("[name='sumInfo'] [name='viewCnt']").text(sumStats.viewCnt + todayStats.viewCnt)
                $("[name='sumInfo'] [name='blogCnt']").text(sumStats.blogCnt + todayStats.blogCnt)
                $("[name='sumInfo'] [name='commentCnt']").text(sumStats.commentCnt + todayStats.commentCnt)
                $("[name='sumInfo'] [name='goodCnt']").text(sumStats.goodCnt + todayStats.goodCnt)
                $("[name='sumInfo'] [name='notGoodCnt']").text(sumStats.notGoodCnt + todayStats.notGoodCnt)
            } else {
                // layer.alert("拉取菜单列表失败[" + resp.data + "] !", {icon: 5});
            }
        }
    });
}

/**
 * 登出系统
 */
function logout() {
    ajax({
        url: reqMap.user.logout,
        data: {},
        type: "POST",
        success: function (resp) {
            if (resp.success) {
                layer.alert("登出成功 !", function () {
                    location.href = "/static/admin/index.html"
                })
            } else {
                layer.alert("登出失败 ??")
            }
        }
    })
}

/**
 * 轮询任务的句柄
 */
var pollingTaskId = null

/**
 * 轮询拉取数据
 */
function pollingStatistics() {
    if (pollingTaskId) {
        clearInterval(pollingTaskId)
        pollingTaskId = null
    } else {
        pollingTaskId = setInterval(refreshStatistics, statsIntervalInMs)
    }
    layer.msg("切换成功, 轮询状态 : " + (pollingTaskId !== null) + " !")
}

/**
 * 初始化 绑定 tips
 */
function initTips() {
    $("[name='refreshBtn']").hover(function () {
        layer.tips("刷新统计数据", "[name='refreshBtn']", {
            tips: [1, '#ff0000'],
            time: 1000
        })
    }, function () {
    })

    $("[name='pollingBtn']").hover(function () {
        layer.tips("轮询状态 : " + (pollingTaskId !== null), "[name='pollingBtn']", {
            tips: [1, '#ff0000'],
            time: 1000
        })
    }, function () {
    })
}


