/**
 * blogManagement.js
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/24/2017 9:56 PM
 */
var sPageNow = sessionStorageGet(location.href)
if(isEmpty(sPageNow) ) {
    sPageNow = 1
}

// 加载类型, 标签, 同步加载, 否则 可能 layui 绑定不了事件
initTypeAndTags()

layui.define(['element', 'laypage', 'layer', 'form', 'pagesize'], function (exports) {
    var $ = layui.jquery;
    var layer = layui.layer;
    var form = layui.form();
    var laypage = layui.laypage;
    var element = layui.element();
    var laypageId = 'pageNav';

    form.on('submit(formSearch)', function (data) {
        var index = layer.load(1);
        doSearch(layer, index, 1)
        return false;
    });

    initilData(sPageNow);
    //页数据初始化
    function initilData(pageNow) {
        var index = layer.load(1);

        //模拟数据加载
        setTimeout(doSearch(layer, index, pageNow), 1000);
    }

    function doSearch(layer, index, pageNow) {
        layer.close(index);
        var params = $("#blogSearchForm").serialize()
        params += "&pageNow=" + pageNow
        params += "&pageSize=" + pageSize

        ajax({
            url: reqMap.blog.adminList,
            type: "GET",
            data: params,
            success: function (resp) {
                if (resp.success) {
                    var html = new StringBuilder();
                    for (var i in resp.data.list) {
                        var item = resp.data.list[i];
                        html.append('<tr>')
                        html.append('<td>' + item.id + '</td>')
                        html.append('<td><img src="' + item.blogCreateTypeImgUrl + '" />' +
                            '<img src="' + stateIconUrl(item.state) + '" width="20px" height="20px" style="margin-left: 20px" /></td>');
                        html.append('<td> <a href="' + previewUrl(item.id, item.state) + '" target="_blank" >' + item.title + '</a></td>')
                        html.append('<td>' + item.author + '</td>')
                        html.append('<td>' + item.summary + '</td>')
                        html.append('<td>' + item.createdAt + '</td>')
                        html.append('<td>' + item.blogTypeName + '</td>')
                        html.append('<td>' + item.blogTagNames + '</td>')
                        if (("10" === item.state) || ("40" === item.state)) {
                            html.append('<td><button class="layui-btn layui-btn-small layui-btn-normal" onclick="layui.funcs.transferState(' + item.id + ', \'20\')"><i class="layui-icon">&#xe628;</i></button></td>')
                        }
                        if("20" !== item.state) {
                            html.append('<td><button class="layui-btn layui-btn-small layui-btn-normal" onclick="layui.funcs.editData(' + item.id + ')"><i class="layui-icon">&#xe642;</i></button></td>')
                            html.append('<td><button class="layui-btn layui-btn-small layui-btn-danger" onclick="layui.funcs.deleteData(' + item.id + ')"><i class="layui-icon">&#xe640;</i></button></td>')
                        }
                        html.append('</tr>')
                    }
                    $('#dataContent').html(html.toString());
                    element.init();

                    form.render('checkbox');  //重新渲染CheckBox，编辑和添加的时候
                    laypage({
                        cont: laypageId,
                        pages: resp.data.totalPage,
                        groups: 5,
                        skip: true,
                        curr: pageNow,
                        jump: function (obj, first) {
                            sPageNow = obj.curr;
                            sessionStorageSet(location.href, sPageNow)
                            if (!first) {
                                initilData(sPageNow);
                            }
                        }
                    });
                } else {
                    layer.alert("拉取博客列表失败[" + resp.data + "] !", {icon: 5});
                }
            }
        });

    }

    //输出接口，主要是两个函数，一个删除一个编辑
    var funcs = {
        editData: function (id) {
            var saveBlogUrl = formatContextUrl('/static/admin/addBlog.html?id=' + id)
            parent.switchTab(parent.$, parent.element, '修改博客', saveBlogUrl, 'blog-' + id);
        },
        deleteData: function (id) {
            layer.confirm('同时会删除对应评论，确定删除？', {
                btn: ['确定', '取消'] //按钮
            }, function () {
                ajax({
                    url: reqMap.blog.remove,
                    data: {"id": id},
                    type: 'POST',
                    success: function (resp) {
                        if (resp.success) {
                            layer.alert('删除成功!', {
                                closeBtn: 0,
                                icon: 1
                            }, function () {
                                refresh()
                            });
                        } else {
                            layer.alert("删除博客失败[" + resp.data + "] !", {icon: 5});
                        }
                    }
                });
            }, function () {

            });
        },
        transferState: function (id, newState) {
            ajax({
                url: reqMap.blog.transfer,
                data: {
                    id: id,
                    state: newState
                },
                type: 'POST',
                success: function (resp) {
                    if (resp.success) {
                        layer.alert('发表博客成功, 管理员正在审核 !', function () {
                            refresh()
                        });
                    } else {
                        layer.alert("发表博客失败[" + resp.data + "] !", {icon: 5});
                    }
                }
            });
        }
    };
    exports('funcs', funcs);
});

/**
 * 加载类型 和标签列表
 */
function initTypeAndTags() {
    ajax({
        url: reqMap.composite.typeAndTags,
        type: "GET",
        success: function (resp) {
            if (resp.success) {
                var types = resp.data.types
                var typeIdEle = $("#typeId")
                for (idx in types) {
                    typeIdEle.append("<option value='" + types[idx].id + "'> " + types[idx].name + " </option>")
                }

                var tags = resp.data.tags
                var tagIdEle = $("#tagId")
                for (idx in tags) {
                    tagIdEle.append("<option value='" + tags[idx].id + "'> " + tags[idx].name + " </option>")
                }
            } else {
                layer.alert("拉取类型/标签列表失败[" + resp.data + "] !", {icon: 5});
            }
        }
    });
}

/**
 * 获取给定的博客的 预览的url
 * @param id
 * @param state
 */
function previewUrl(id, state) {
    var stateMap = {
        "30" : "/static/main/blogDetail.html?id=" + id
    }
    stateMap[defaultKey] = "/static/admin/blogDetail.html?id=" + id

    var result = getByIdx(stateMap, state, defaultKey)
    return formatContextUrl(result)
}

/**
 * 获取状态对应的图标
 * @param state
 * @returns {*}
 */
function stateIconUrl(state) {
    var stateMap = {
        "10" : "/static/admin/images/blog_draft.png",
        "20" : "/static/admin/images/blog_audit.png",
        "30" : "/static/admin/images/blog_success.png",
        "40" : "/static/admin/images/blog_failed.png"
    }
    stateMap[defaultKey] = "/static/admin/images/blog_failed.png"

    var result = getByIdx(stateMap, state, defaultKey)
    return formatContextUrl(result)
}

