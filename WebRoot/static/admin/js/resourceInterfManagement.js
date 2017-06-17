/**
 * accountManagement.js
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/24/2017 9:33 PM
 */

var linkNum = 3;
var interfs = null

layui.define(['element', 'laypage', 'layer', 'form'], function (exports) {
    var $ = layui.jquery;
    var layer = layui.layer;
    var form = layui.form();
    var laypage = layui.laypage;
    var element = layui.element();
    var laypageId = 'pageNav';

    initInterfs()
    initilData(1);

    //页数据初始化
    //currentIndex：当前页面
    //pageSize：页容量（每页显示的条数）
    function initilData(pageNow) {
        var index = layer.load(1);
        //模拟数据加载
        setTimeout(function () {
            layer.close(index);
            ajax({
                url: reqMap.resourceInterf.list,
                type: "GET",
                data: {},
                success: function (resp) {
                    if (resp.success) {
                        var users = resp.data
                        var html = '';
                        for (var i in users) {
                            var item = users[i];
                            var interfIds = collectInterfIdList(item)
                            var interfNames = collectInterfList(item)
                            html += '<tr>';
                            html += '<td>' + item.id + '</td>';
                            html += '<td>' + item.name + '</td>';
                            html += '<td>' + item.url + '</td>';
                            html += '<td>' + item.level + '</td>';
                            html += '<td>' + item.parentId + '</td>';
                            html += '<td>' + interfNames + '</td>';
                            html += '<td>' + item.createdAt + '</td>';
                            html += '<td><button class="layui-btn layui-btn-small layui-btn-normal" onclick="layui.funcs.editData(' + item.id + ',\'' + item.name + '\',\'' + interfIds + '\')" ><i class="layui-icon">&#xe642;</i></button></td>';
                            html += '</tr>';
                        }
                        $('#dataContent').html(html);
                        element.init();

                        $('#dataConsole,#dataList').attr('style', 'display:block'); //显示FiledBox
                    } else {
                        layer.alert("拉取资源接口列表失败[" + resp.data + "] !", {icon: 5});
                    }
                }
            });
        }, 500);
    }

    form.on('submit(updateRoleResourceSubmit)', function (data) {
        $("[name='interfIds']").attr("value", collectAttrValues($("#interfSelected .layui-form-checked"), "value", ", ", false))
        ajax({
            url: reqMap.resourceInterf.update,
            type: "POST",
            data: $("#updateResourceInterfForm").serialize(),
            success: function (resp) {
                if (resp.success) {
                    layer.alert('更新资源接口信息成功!', {
                        closeBtn: 0,
                        icon: 1
                    }, function () {
                        refresh()
                    });
                } else {
                    layer.alert("更新资源接口信息失败[" + resp.data + "] !", {icon: 5});
                }
            }
        });
        return false
    })

    //输出接口，主要是两个函数，一个删除一个编辑
    var funcs = {
        editData: function (resourceId, resourceName, interfIds) {
            var html = '';
            html += '<div>'
            html += '<form id="updateResourceInterfForm" class="layui-form layui-form-pane" action="/admin/role/userRole/update" method="post">';
            html += '<input type="hidden" name="resourceId" value="' + resourceId + '" />'
            html += '<input type="hidden" name="interfIds" />'
            html += '<label class="layui-form-label" style="border: none;width: 180px;" >资源名 :</label>';
            html += '<input style="width:50%;margin: auto;color: #000!important;" name="resourceName"  class="layui-input" value="' + resourceName + '" readonly />';
            html += '<label class="layui-form-label" style="border: none;width: 180px;" >接口列表:</label>';
            html += '<div class="layui-input-block" id="interfSelected" >'
            html += '</div>'
            html += '<div class="layui-form-item">';
            html += '<div class="layui-input-inline" style="margin:10px auto 0 auto;display: block;float: none;">';
            html += '<button class="layui-btn" id="submit"  lay-submit="" lay-filter="updateRoleResourceSubmit">提交</button>';
            html += '<button type="reset" class="layui-btn layui-btn-primary">重置</button>';
            html += '</div>';
            html += '</div>';
            html += '</form>';
            html += '</div>'

            /**
             * 增加角色, 并选中需要选中的角色
             * @type {*}
             */
            $htmlDoc = $(html)
            for (idx in interfs) {
                var value = interfs[idx].id
                var text = interfs[idx].name
                $htmlDoc.find("#interfSelected").append(
                    '<div value=' + value + ' class="layui-unselect layui-form-checkbox" lay-skin="" onclick="toggleCheckted(this)" >' +
                    '<span>' + text + '</span><i class="layui-icon"></i>' +
                    '</div>')
            }
            interfIds = interfIds.split(",")
            for (idx in interfIds) {
                toggleCheckted($htmlDoc.find("div[value='" + interfIds[idx].trim() + "']"))
            }

            layer.open({
                type: 1,
                skin: 'layui-layer-rim', //加上边框
                area: '800px', //宽高
                title: '编辑资源接口',
                content: $htmlDoc.html()
            })
            form.render('select')
        }
    };
    exports('funcs', funcs);
});

/**
 * 切换输入框的样式
 */
function toggleCheckted(input) {
    input = $(input)
    if (input.hasClass("layui-form-checked")) {
        input.removeClass("layui-form-checked")
    } else {
        input.addClass("layui-form-checked")
    }
}

/**
 * 初始化 interf 列表
 */
function initInterfs() {
    ajax({
        url: reqMap.interf.list,
        type: "GET",
        data: {},
        success: function (resp) {
            if (resp.success) {
                interfs = resp.data;
            } else {
                layer.alert("拉取接口列表失败[" + resp.data + "] !", {icon: 5});
            }
        }
    });
}

/**
 * 收集给定的资源的的接口列表
 * @param userRole
 * @returns {StringBuilder}
 */
function collectInterfList(userRole) {
    var result = new StringBuilder()
    for (idx in userRole.interfNames) {
        result.append(userRole.interfNames[idx])
    }
    return result.join(", ");
}
function collectInterfIdList(userRole) {
    var result = new StringBuilder()
    for (idx in userRole.interfIds) {
        result.append(userRole.interfIds[idx])
    }
    return result.join(", ");
}
