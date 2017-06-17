/**
 * accountPwdManage.js
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/24/2017 9:35 PM
 */

layui.define(['form', 'upload', 'layer'], function (exports) {
    var $ = layui.jquery;
    var layer = layui.layer;
    var form = layui.form();

    form.on('submit(updatePwdSubmit)', function (data) {
        if(data.field.newPwd !== data.field.confirmNewPwd) {
            layer.alert(" 新密码 和确认的密码 不一致 !")
            return false;
        }

        var prams = {
            oldPwd : hex_md5(data.field.oldPwd),
            newPwd : hex_md5(data.field.newPwd),
            confirmNewPwd : hex_md5(data.field.confirmNewPwd)
        }
        ajax({
            url: reqMap.user.updatePwd,
            type: "POST",
            data: prams,
            success: function (resp) {
                if (resp.success) {
                    layer.alert('修改密码成功!', function () {
                        refresh()
                    })
                } else {
                    layer.alert("修改密码失败[" + resp.data + "] !", {icon: 5})
                }
            }
        });

        return false;
    });

});
