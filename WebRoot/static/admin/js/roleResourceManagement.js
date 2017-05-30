/**
 * accountManagement.js
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/24/2017 9:33 PM
 */

var linkNum = 3;
var resources = null

layui.define(['element', 'laypage', 'layer', 'form'], function (exports) {
    var $ = layui.jquery;
    var layer = layui.layer;
    var form = layui.form();
    var laypage = layui.laypage;
    var element = layui.element();
    var laypageId = 'pageNav';

    initResources()
    initilData(1);

    //页数据初始化
    //currentIndex：当前页面
    //pageSize：页容量（每页显示的条数）
    function initilData(pageNow) {
        var index = layer.load(1);
        //模拟数据加载
        setTimeout(function () {
            layer.close(index);
            $.ajax({
                url: "/admin/resource/roleResource/list",
                type: "GET",
                data: {},
                success: function (resp) {
                    if (resp.success) {
                        var users = resp.data
                        var html = '';
                        for (var i in users) {
                            var item = users[i];
                            var resourceIds = collectResourceIdList(item)
                            var resourceNames = collectResourceList(item)
                            html += '<tr>';
                            html += '<td>' + item.id + '</td>';
                            html += '<td>' + item.name + '</td>';
                            html += '<td>' + item.desc + '</td>';
                            html += '<td>' + resourceNames + '</td>';
                            html += '<td>' + item.createdAt + '</td>';
                            html += '<td><button class="layui-btn layui-btn-small layui-btn-normal" onclick="layui.funcs.editData(' + item.id + ',\'' + item.name + '\',\'' + resourceIds + '\')" ><i class="layui-icon">&#xe642;</i></button></td>';
                            html += '</tr>';
                        }
                        $('#dataContent').html(html);
                        element.init();

                        $('#dataConsole,#dataList').attr('style', 'display:block'); //显示FiledBox
                    } else {
                        layer.alert("拉取角色资源列表失败[" + resp.msg + "] !", {icon: 5});
                    }
                }
            });
        }, 500);
    }

    form.on('submit(updateRoleResourceSubmit)', function (data) {
        $("[name='resourceIds']").attr("value", collectAttrValues($("#resourceSelected .layui-form-checked"), "value", ", ", false))
        $.ajax({
            url: "/admin/resource/roleResource/update",
            type: "POST",
            data: $("#updateRoleResourceForm").serialize(),
            success: function (resp) {
                if (resp.success) {
                    layer.alert('更新角色资源信息成功!', {
                        closeBtn: 0,
                        icon: 1
                    }, function () {
                        location.reload()
                    });
                } else {
                    layer.alert("更新角色资源信息失败[" + resp.msg + "] !", {icon: 5});
                }
            }
        });
        return false
    })

    //输出接口，主要是两个函数，一个删除一个编辑
    var funcs = {
        editData: function (roleId, roleName, resourceIds) {
            var html = '';
            html += '<div>'
            html += '<form id="updateRoleResourceForm" class="layui-form layui-form-pane" action="/admin/role/userRole/update" method="post">';
            html += '<input type="hidden" name="roleId" value="' + roleId + '" />'
            html += '<input type="hidden" name="resourceIds" />'
            html += '<label class="layui-form-label" style="border: none;width: 180px;" >用户名 :</label>';
            html += '<input style="width:50%;margin: auto;color: #000!important;" name="userName"  class="layui-input" value="' + roleName + '" readonly />';
            html += '<label class="layui-form-label" style="border: none;width: 180px;" >角色列表:</label>';
            html += '<div class="layui-input-block" id="resourceSelected" >'
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
            for(idx in resources) {
                var value = resources[idx].id
                var text = resources[idx].name
                $htmlDoc.find("#resourceSelected").append(
                    '<div value=' + value + ' class="layui-unselect layui-form-checkbox" lay-skin="" onclick="toggleCheckted(this)" >' +
                    '<span>' + text + '</span><i class="layui-icon"></i>' +
                    '</div>')
            }
            resourceIds = resourceIds.split(",")
            for (idx in resourceIds) {
                toggleCheckted($htmlDoc.find("div[value='" + resourceIds[idx].trim() + "']"))
            }

            layer.open({
                type: 1,
                skin: 'layui-layer-rim', //加上边框
                area: '800px', //宽高
                title: '编辑角色资源',
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
 * 初始化 role 列表
 */
function initResources() {
    $.ajax({
        url: "/admin/resource/list",
        type: "GET",
        data: {},
        success: function (resp) {
            if (resp.success) {
                resources = resp.data;
            } else {
                layer.alert("拉取资源列表失败[" + resp.msg + "] !", {icon: 5});
            }
        }
    });
}

/**
 * 收集给定的用户的角色列表
 * @param userRole
 * @returns {StringBuilder}
 */
function collectResourceList(userRole) {
    var result = new StringBuilder()
    for (idx in userRole.resourceNames) {
        result.append(userRole.resourceNames[idx])
    }
    return result.join(", ");
}
function collectResourceIdList(userRole) {
    var result = new StringBuilder()
    for (idx in userRole.resourceIds) {
        result.append(userRole.resourceIds[idx])
    }
    return result.join(", ");
}
