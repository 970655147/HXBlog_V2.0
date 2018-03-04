/**
 * accountRoleManagement.js.js
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/24/2017 9:35 PM
 */
var sPageNow = sessionStorageGet(location.href)
if(isEmpty(sPageNow) ) {
    sPageNow = 1
}

var roles = null

layui.define(['element', 'laypage', 'layer', 'form'], function (exports) {
    var $ = layui.jquery;
    var layer = layui.layer;
    var form = layui.form();
    var laypage = layui.laypage;
    var element = layui.element();
    var laypageId = 'pageNav';

    initRoles()
    initilData(sPageNow);

    //页数据初始化
    function initilData(pageNow) {
        var index = layer.load(1);
        setTimeout(function () {
            layer.close(index);
            ajax({
                url: reqMap.userRole.list,
                type: "GET",
                data: {
                    pageNow: pageNow,
                    pageSize: pageSize
                },
                success: function (resp) {
                    if (resp.success) {
                        var users = resp.data.list
                        var html = new StringBuilder();
                        for (var i in users) {
                            var item = users[i];
                            var roleIds = collectRoleIdList(item)
                            var roleNames = collectRoleList(item)
                            html.append('<tr>')
                            html.append('<td>' + item.id + '</td>')
                            html.append('<td>' + item.userName + '</td>')
                            html.append('<td>' + item.nickName + '</td>')
                            html.append('<td>' + item.email + '</td>')
                            html.append('<td><img src="' + item.headImgUrl + '" width="60px" height="60px" /></td>')
                            html.append('<td>' + roleNames + '</td>')
                            html.append('<td>' + item.createdAt + '</td>')
                            html.append('<td><button class="layui-btn layui-btn-small layui-btn-normal" onclick="layui.funcs.editData(' + item.id + ',\'' + item.userName + '\',\'' + roleIds + '\',\'' + roleNames + '\')" ><i class="layui-icon">&#xe642;</i></button></td>')
                            html.append('</tr>')
                        }
                        $('#dataContent').html(html.toString());
                        element.init();

                        $('#dataConsole,#dataList').attr('style', 'display:block'); //显示FiledBox
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
                        })
                    } else {
                        layer.alert("拉取用户列表失败[" + resp.data + "] !", {icon: 5});
                    }
                }
            })
        }, 500);
    }

    form.on('submit(updateUserRoleSubmit)', function (data) {
        $("[name='roleIds']").attr("value", collectAttrValues($("#roleSelected .layui-form-checked"), "value", ", ", false))
        ajax({
            url: reqMap.userRole.update,
            type: "POST",
            data: $("#updateUserRoleForm").serialize(),
            success: function (resp) {
                if (resp.success) {
                    layer.alert('更新用户角色信息成功!', {
                        closeBtn: 0,
                        icon: 1
                    }, function () {
                        refresh()
                    });
                } else {
                    layer.alert("更新用户角色信息失败[" + resp.data + "] !", {icon: 5});
                }
            }
        });
        return false
    })

    //输出接口，主要是两个函数，一个删除一个编辑
    var funcs = {
        editData: function (userId, userName, roleIds, roleNames) {
            var html = new StringBuilder();
            html.append('<div>')
            html.append('<form id="updateUserRoleForm" class="layui-form layui-form-pane" action="' + reqMap.userRole.update + '" method="post">')
            html.append('<input type="hidden" name="userId" value="' + userId + '" />')
            html.append('<input type="hidden" name="roleIds" />')
            html.append('<label class="layui-form-label" style="border: none;width: 180px;" >用户名 :</label>')
            html.append('<input style="width:50%;margin: auto;color: #000!important;" name="userName"  class="layui-input" value="' + userName + '" readonly />')
            html.append('<label class="layui-form-label" style="border: none;width: 180px;" >角色列表:</label>')
            html.append('<div class="layui-input-block" id="roleSelected" >')
            html.append('</div>')
            html.append('<div class="layui-form-item">')
            html.append('<div class="layui-input-inline" style="margin:10px auto 0 auto;display: block;float: none;">')
            html.append('<button class="layui-btn" id="submit"  lay-submit="" lay-filter="updateUserRoleSubmit">提交</button>')
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
            for(idx in roles) {
                var value = roles[idx].id
                var text = roles[idx].name
                $htmlDoc.find("#roleSelected").append(
                    '<div value=' + value + ' class="layui-unselect layui-form-checkbox" lay-skin="" onclick="toggleCheckted(this)" >' +
                    '<span>' + text + '</span><i class="layui-icon"></i>' +
                    '</div>')
            }
            roleIds = roleIds.split(",")
            for (idx in roleIds) {
                toggleCheckted($htmlDoc.find("div[value='" + roleIds[idx].trim() + "']"))
            }

            layer.open({
                type: 1,
                skin: 'layui-layer-rim', //加上边框
                area: '800px', //宽高
                title: '编辑用户角色',
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
function initRoles() {
    ajax({
        url: reqMap.role.list,
        type: "GET",
        data: {},
        success: function (resp) {
            if (resp.success) {
                roles = resp.data;
            } else {
                layer.alert("拉取角色列表失败[" + resp.data + "] !", {icon: 5});
            }
        }
    });
}

/**
 * 收集给定的用户的角色列表
 * @param userRole
 * @returns {StringBuilder}
 */
function collectRoleList(userRole) {
    var result = new StringBuilder()
    for (idx in userRole.roleNames) {
        result.append(userRole.roleNames[idx])
    }
    return result.join(", ");
}
function collectRoleIdList(userRole) {
    var result = new StringBuilder()
    for (idx in userRole.roleIds) {
        result.append(userRole.roleIds[idx])
    }
    return result.join(", ")
}
