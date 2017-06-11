/**
 * writeBlog.js
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/21/2017 10:01 PM
 */

    //实例化编辑器
var ue = UE.getEditor('editor');

// 加载用户列表, 角色
initUserAndRoles()

layui.define(['form', 'upload', 'layer'], function (exports) {
    var $ = layui.jquery;
    var layer = layui.layer;
    var form = layui.form();

    var addTypeLayer, addTypeConfirm, addBlogLayer;

    form.on('submit(submitBlog)', function (data) {
        $("[name='content']").attr("value", ue.getContent())
        $("[name='userIds']").attr("value", collectAttrValues($("#userSelected .layui-form-checked"), "value", ", ", false))
        $("[name='roleIds']").attr("value", collectAttrValues($("#roleSelected .layui-form-checked"), "value", ", ", false))
        if((isEmpty($("[name='userIds']").attr("value"))) && (isEmpty($("[name='roleIds']").attr("value"))) ) {
            layer.tips("请至少选择一个目标用户 !", "#userSelected", {
                tips: [1, '#3595CC'],
                time: 4000
            })
            return false
        }
        if((isEmpty($("[name='content']")).attr("value")) ) {
            layer.tips("请输入内容 !", "#editor", {
                tips: [1, '#3595CC'],
                time: 4000
            })
            return false
        }

        $.ajax({
            url: "/admin/message/add",
            type: "POST",
            data: $("#addMessageForm").serialize(),
            success: function (resp) {
                if (resp.success) {
                    addBlogLayer = layer.alert('发送消息成功!', {
                        closeBtn: 0,
                        icon: 1
                    }, function () {
                        layer.close(addBlogLayer)
                    });
                } else {
                    layer.alert("保存消息失败[" + resp.data + "] !", {icon: 5});
                }
            }
        });

        return false;
    });

    layui.upload({
        elem: "#uploadEditorHtml",
        url: '/admin/upload/file', //上传接口
        ext: "html|txt",
        before: function (input) {
            var reader = new FileReader();
            reader.readAsText(input.files[0], encoding);
            reader.onload = function (evt) {
                var content = evt.target.result;
                UE.getEditor('editor').execCommand('insertHtml', content)
            }
        },
        success: function (resp) { //上传成功后的回调
            if (resp.success) {
            } else {
                layer.alert("保存文件失败[" + resp.data + "] !", {icon: 5});
            }
        }
    });

    form.on('select(userIds)', function (context) {
        var select = $(context.elem)
        var value = context.value
        var selectAfter = $(context.othis)

        var text = select.find("option[value=" + value + "]").text()
        $("#userSelected").append(
            '<div value=' + value + ' class="layui-unselect layui-form-checkbox layui-form-checked" lay-skin="" onclick="layui.funcs.toggleCheckted(this)" >' +
            '<span>' + text + '</span><i class="layui-icon"></i>' +
            '</div>')
        selectAfter.find("dd[lay-value=" + value + "]").remove()
    });
    form.on('select(roleIds)', function (context) {
        var select = $(context.elem)
        var value = context.value
        var selectAfter = $(context.othis)

        var text = select.find("option[value=" + value + "]").text()
        $("#roleSelected").append(
            '<div value=' + value + ' class="layui-unselect layui-form-checkbox layui-form-checked" lay-skin="" onclick="layui.funcs.toggleCheckted(this)" >' +
            '<span>' + text + '</span><i class="layui-icon"></i>' +
            '</div>')
        selectAfter.find("dd[lay-value=" + value + "]").remove()
    });

    //输出接口
    var funcs = {
        toggleCheckted: function (input) {
            input = $(input)
            if (input.hasClass("layui-form-checked")) {
                input.removeClass("layui-form-checked")
            } else {
                input.addClass("layui-form-checked")
            }
        }
    };


    exports('funcs', funcs);
});

/**
 * 初始化类型, 标签列表
 */
function initUserAndRoles() {
    $.ajax({
        url: "/composite/userAndRoles",
        type: "GET",
        async: false,
        success: function (resp) {
            if (resp.success) {
                var usersEle = $("#userIds")
                var users = resp.data.users
                for (idx in users) {
                    usersEle.append("<option value='" + users[idx].id + "'> " + users[idx].name + " </option>")
                }

                var rolesEle = $("#roleIds")
                var roles = resp.data.roles
                for (idx in roles) {
                    rolesEle.append("<option value='" + roles[idx].id + "'> " + roles[idx].name + " </option>")
                }
            } else {
                layer.alert("拉取用户角色列表失败[" + resp.data + "] !", {icon: 5});
            }
        }
    });
}
