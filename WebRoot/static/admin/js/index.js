/**
 * index.js
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/24/2017 9:56 PM
 */

layui.define([ 'layer', 'form'], function (exports) {
    var form = layui.form();
    var $ = layui.jquery;

    //自定义验证
    form.verify({
        password: [/^[\S]{3,32}$/, '密码必须3到32位'],
        userName: function (value) {
            if (value.length <= 2 || value.length > 32) {
                return "账号必须3到32位"
            }
            var reg = /^[a-zA-Z0-9]*$/;
            if (!reg.test(value)) {
                return "账号只能为英文或数字";
            }
        },
        result_response: function () {
            var value=$("#lc-captcha-response").val();
            if (value.length < 1) {
                return '请点击人机识别验证';
            }
        },
    });

    //监听登陆提交
    form.on('submit(login)', function (data) {
        var index = layer.load(1);
        layer.close(index);

        var userName = data.field.userName
        var password = hex_md5(data.field.password)
        var checkCode = data.field.checkCode
        var ip = returnCitySN["cip"];
        var ipAddr = returnCitySN["cname"];
        ajax({
            url : reqMap.user.login ,
            type : "POST",
            data:{
                "userName" : userName,
                "password" : password,
                "checkCode" : checkCode,
                "ip" : ip,
                "ipAddr" : ipAddr
            },
            success : function (resp) {
                if(resp.success){
                    layer.msg('登陆成功，正在跳转......', { icon: 6 });
                    layer.closeAll('page');
                    setTimeout(function () {
                        location.href = "/static/admin/main.html";
                    }, 1000);
                }else{
                    refreshCheckCode()
                    layer.msg(resp.data, { icon: 5, time : 200 })
                }
            }
        });
        return false;
    });

    //检测键盘按下
    $('body').keydown(function (e) {
        if (e.keyCode == 13) {  //Enter键
            if ($('#layer-login').length <= 0) {
                login();
            }
        }
    });

    $('.enter').on('click', login);

    function login() {
        var loginHtml = new StringBuilder();
        loginHtml.append('<form class="layui-form" action="">')
        loginHtml.append('<div class="layui-form-item">')
        loginHtml.append('<label class="layui-form-label">账号</label>')
        loginHtml.append('<div class="layui-input-inline pm-login-input">')
        loginHtml.append('<input type="text" name="userName" lay-verify="userName" placeholder="请输入账号"  autocomplete="off" class="layui-input">')
        loginHtml.append('</div>')
        loginHtml.append('</div>')
        loginHtml.append('<div class="layui-form-item">')
        loginHtml.append('<label class="layui-form-label">密码</label>')
        loginHtml.append('<div class="layui-input-inline pm-login-input">')
        loginHtml.append('<input type="password" name="password" lay-verify="password" placeholder="请输入密码"  autocomplete="off" class="layui-input">')
        loginHtml.append('</div>')
        loginHtml.append('</div>')
        loginHtml.append('<div class="layui-form-item">')
        loginHtml.append('<label class="layui-form-label">验证码</label>')
        loginHtml.append('<div class="layui-input-inline pm-login-input">')
        loginHtml.append('<input type="text" name="checkCode" placeholder="请输入验证码"  autocomplete="off" class="layui-input">')
        loginHtml.append('<br />')
        loginHtml.append('<img name="checkCodeImg" width="160px" height="80px" onclick="refreshCheckCode()" />')
        loginHtml.append('</div>')
        loginHtml.append('</div>')
        loginHtml.append('<div class="layui-form-item">')
        // TODO: 7/1/2017 上线注释部分代码
        // loginHtml.append('<label class="layui-form-label">人机验证</label>')
        // loginHtml.append('<div class="layui-input-inline pm-login-input">')
        // loginHtml.append('<div class="l-captcha" lay-verify="result_response" data-site-key="0c5f2ddcf3eb0f58a678e0c50e0d736e"></div>')
        // loginHtml.append('</div>')
        // loginHtml.append('</div>')
        // loginHtml.append('<div class="layui-form-item" style="margin-top:25px;margin-bottom:0;">')
        loginHtml.append('<div class="layui-input-block">')
        loginHtml.append(' <button class="layui-btn" style="width:230px;" lay-submit="" lay-filter="login">立即登录</button>')
        loginHtml.append('</div>')
        loginHtml.append('</div>')
        loginHtml.append('</form>')
        loginHtml +='<script src="//captcha.luosimao.com/static/dist/api.js"></script>';

        layer.open({
            id: 'layer-login',
            type: 1,
            title: false,
            shade: 0.4,
            shadeClose: true,
            area: ['540px', '420px'],
            closeBtn: 0,
            anim: 1,
            skin: 'pm-layer-login',
            content: loginHtml.toString()
        });
        layui.form().render('checkbox');
        refreshCheckCode()
    }

    exports('index', {});
});

/**
 * 刷新验证码
 */
function refreshCheckCode() {
    $("[name='checkCodeImg']").attr("src", "/image/checkCode?ts=" + Date.parse(new Date()))
}

