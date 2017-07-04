/**
 * roleResourceManagement.js
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/24/2017 9:33 PM
 */

layui.define(['element', 'laypage', 'layer', 'form'], function (exports) {
    var $ = layui.jquery;
    var layer = layui.layer;
    var form = layui.form();
    var laypage = layui.laypage;
    var element = layui.element();
    var laypageId = 'pageNav';

    initResources()
    initilData();

    //页数据初始化
    function initilData() {
        var index = layer.load(1);
        //模拟数据加载
        setTimeout(function () {
            layer.close(index);
            ajax({
                url: reqMap.roleResource.list,
                type: "GET",
                data: {},
                success: function (resp) {
                    if (resp.success) {
                        var users = resp.data
                        var html = new StringBuilder();
                        for (var i in users) {
                            var item = users[i];
                            var resourceIds = collectResourceIdList(item)
                            var resourceNames = collectResourceList(item)
                            html.append('<tr>')
                            html.append('<td>' + item.id + '</td>')
                            html.append('<td>' + item.name + '</td>')
                            html.append('<td>' + item.desc + '</td>')
                            html.append('<td>' + resourceNames + '</td>')
                            html.append('<td>' + item.createdAt + '</td>')
                            html.append('<td><button class="layui-btn layui-btn-small layui-btn-normal" onclick="layui.funcs.editData(' + item.id + ',\'' + item.name + '\',\'' + resourceIds + '\')" ><i class="layui-icon">&#xe642;</i></button></td>')
                            html.append('</tr>')
                        }
                        $('#dataContent').html(html.toString());
                        element.init();
                    } else {
                        layer.alert("拉取角色资源列表失败[" + resp.data + "] !", {icon: 5});
                    }
                }
            });
        }, 500);
    }

    form.on('submit(updateRoleResourceSubmit)', function (data) {
        $("[name='resourceIds']").attr("value", collectAttrValues($("#resourceSelected .layui-form-checked"), "value", ", ", false))
        ajax({
            url: reqMap.roleResource.update,
            type: "POST",
            data: $("#updateRoleResourceForm").serialize(),
            success: function (resp) {
                if (resp.success) {
                    layer.alert('更新角色资源信息成功!', {
                        closeBtn: 0,
                        icon: 1
                    }, function () {
                        refresh()
                    });
                } else {
                    layer.alert("更新角色资源信息失败[" + resp.data + "] !", {icon: 5});
                }
            }
        });
        return false
    })

    //输出接口，主要是两个函数，一个删除一个编辑
    var funcs = {
        editData: function (roleId, roleName, resourceIds) {
            var html = new StringBuilder();
            html.append('<div>')
            html.append('<form id="updateRoleResourceForm" class="layui-form layui-form-pane" action="/admin/role/userRole/update" method="post">')
            html.append('<input type="hidden" name="roleId" value="' + roleId + '" />')
            html.append('<input type="hidden" name="resourceIds" />')
            html.append('<label class="layui-form-label" style="border: none;width: 180px;" >角色名 :</label>')
            html.append('<input style="width:50%;margin: auto;color: #000!important;" name="roleName"  class="layui-input" value="' + roleName + '" readonly />')
            html.append('<label class="layui-form-label" style="border: none;width: 180px;" >资源列表:</label>')
            html.append('<div class="layui-input-block" id="resourceSelected" >')
            html.append('</div>')
            html.append('<div class="layui-form-item">')
            html.append('<div class="layui-input-inline" style="margin:10px auto 0 auto;display: block;float: none;">')
            html.append('<button class="layui-btn" id="submit"  lay-submit="" lay-filter="updateRoleResourceSubmit">提交</button>')
            html.append('<button type="reset" class="layui-btn layui-btn-primary">重置</button>')
            html.append('</div>')
            html.append('</div>')
            html.append('</form>')
            html.append('</div>')

            /**
             * 增加角色, 并选中需要选中的角色
             * @type {*}
             */
            $htmlDoc = $(html.toString())
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
    ajax({
        url: reqMap.resource.list,
        type: "GET",
        data: {},
        success: function (resp) {
            if (resp.success) {
                resources = resp.data;
            } else {
                layer.alert("拉取资源列表失败[" + resp.data + "] !", {icon: 5});
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
