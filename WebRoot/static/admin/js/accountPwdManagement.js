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

    var oldPassword = [[${session.currentAdmin.password}]];
    var oldUserName = [[${session.currentAdmin.userName}]];

    form.on('submit(submit)', function (data) {
        if (oldUserName !== $("#oldUserName").val() || oldPassword !== $("#oldPassword").val()) {
            layer.alert('原账号名或密码错误!', {icon: 5});
            return false;
        }
        return true;
    });
});
