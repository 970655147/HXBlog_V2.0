/**
 * accountManagement.js
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/24/2017 9:33 PM
 */

var linkNum = 3;

layui.define(['element', 'laypage', 'layer', 'form'], function (exports) {
    var $ = layui.jquery;
    var layer = layui.layer;
    var form = layui.form();
    var laypage = layui.laypage;
    var element = layui.element();
    var laypageId = 'pageNav';

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
                url: "/admin/user/list",
                type: "GET",
                data: {
                    pageNow: pageNow,
                    pageSize: pageSize
                },
                success: function (resp) {
                    if (resp.success) {
                        var users = resp.data.list
                        var html = '';
                        for (var i in users) {
                            var item = users[i];
                            html += '<tr>';
                            html += '<td>' + item.id + '</td>';
                            html += '<td>' + item.userName + '</td>';
                            html += '<td>' + item.nickName + '</td>';
                            html += '<td>' + item.email + '</td>';
                            html += '<td><img src="' + item.headImgUrl + '" width="60px" height="60px" /></td>';
                            html += '<td>' + item.motto + '</td>';
                            html += '<td>' + item.lastLoginIp + '</td>';
                            html += '<td>' + item.lastLoginAt + '</td>';
                            html += '<td>' + item.createdAt + '</td>';
                            html += '<td><button class="layui-btn layui-btn-small layui-btn-normal" onclick="layui.funcs.editData(' + item.id + ',\'' + item.userName + '\',\'' + item.nickName + '\',\'' + item.email + '\',\'' + item.headImgUrl + '\',\'' + item.motto + '\')" ><i class="layui-icon">&#xe642;</i></button></td>';
                            html += '<td><button class="layui-btn layui-btn-small layui-btn-danger" onclick="layui.funcs.deleteData(' + item.id + ')"><i class="layui-icon">&#xe640;</i></button></td>';
                            html += '</tr>';
                        }
                        $('#dataContent').html(html);
                        element.init();

                        $('#dataConsole,#dataList').attr('style', 'display:block'); //显示FiledBox
                        laypage({
                            cont: laypageId,
                            pages: resp.data.totalPage,
                            groups: 5,
                            skip: true,
                            curr: pageNow,
                            jump: function (obj, first) {
                                var pageNow = obj.curr;
                                if (!first) {
                                    initilData(pageNow);
                                }
                            }
                        });
                    } else {
                        layer.alert("拉取用户列表失败[" + resp.msg + "] !", {icon: 5});
                    }
                }
            });
        }, 500);
    }

    form.on('submit(addAccountSubmit)', function (data) {
        $.ajax({
            url: "/admin/user/add",
            type: "POST",
            data: $(".layui-form").serialize(),
            success: function (result) {
                if (result.success) {
                    layer.alert('添加用户成功!', {
                        closeBtn: 0,
                        icon: 1
                    }, function () {
                        window.location.href = "/static/admin/accountManagement.html"
                    });
                } else {
                    layer.alert("添加用户失败[" + resp.msg + "] !", {icon: 5});
                }
            }
        });
        return false
    })

    form.on('submit(updateAccountSubmit)', function (data) {
        $.ajax({
            url: "/admin/user/update",
            type: "POST",
            data: $(".layui-form").serialize(),
            success: function (result) {
                if (result.success) {
                    layer.alert('更新用户成功!', {
                        closeBtn: 0,
                        icon: 1
                    }, function () {
                        window.location.href = "/static/admin/accountManagement.html"
                    });
                } else {
                    layer.alert("更新用户失败[" + resp.msg + "] !", {icon: 5});
                }
            }
        });
        return false
    })

    //输出接口，主要是两个函数，一个删除一个编辑
    var funcs = {
        addData: function () {
            var html = '';
            html += '<form class="layui-form layui-form-pane" action="/admin/user/add" method="post">';
            html += '<label class="layui-form-label" style="border: none;width: 180px;" >用户名:</label>';
            html += '<input  style="width:87%;margin: auto;color: #000!important;" name="userName" lay-verify="required"  class="layui-input" >';
            html += '<label class="layui-form-label" style="border: none;width: 180px;" >密码:</label>';
            html += '<input  style="width:87%;margin: auto;color: #000!important;" name="password" type="password"  class="layui-input" >';
            html += '<label class="layui-form-label" style="border: none;width: 180px;" >昵称:</label>';
            html += '<input  style="width:87%;margin: auto;color: #000!important;" name="nickName" lay-verify="required"  class="layui-input" >';
            html += '<label class="layui-form-label" style="border: none;width: 180px;" >邮箱:</label>';
            html += '<input  style="width:87%;margin: auto;color: #000!important;" name="email" lay-verify="required"  class="layui-input" >';
            html += '<label class="layui-form-label" style="border: none;width: 120px;" >头像地址:</label>';
            html += '<img id="headImgShow" src="" width="40px" height="40px" />';
            html += '<input  style="width:87%;margin: auto;color: #000!important;" name="headImgUrl" lay-verify="url"  class="layui-input" onblur="layui.datalist.headImgShow()" >';
            html += '<label class="layui-form-label" style="border: none;width: 180px;" >个性签名:</label>';
            html += '<input  style="width:87%;margin: auto;color: #000!important;" name="motto" lay-verify="required"  class="layui-input" >';
            html += '<div class="layui-form-item">';
            html += '<div class="layui-input-inline" style="margin:10px auto 0 auto;display: block;float: none;">';
            html += '<button class="layui-btn" id="submit"  lay-submit="" lay-filter="addAccountSubmit">添加</button>';
            html += '<button type="reset" class="layui-btn layui-btn-primary">重置</button>';
            html += '</div>';
            html += '</div>';
            html += '</form>';

            layer.open({
                type: 1,
                skin: 'layui-layer-rim', //加上边框
                area: '620px', //宽高
                title: '添加用户',
                content: html
            })

            $("[name='headImgUrl']").val("http://tb.himg.baidu.com/sys/portrait/item/48c0c0b6b7e7393730363535313437f02d")
            layui.funcs.headImgShow()
        },
        editData: function (id, userName, nickName, email, headImgUrl, motto) {
            console.log(headImgUrl)
            var html = '';
            html += '<form class="layui-form layui-form-pane" action="/admin/user/update" method="post">';
            html += '<input type="hidden" name="id" value="' + id + '"/>';
            html += '<label class="layui-form-label" style="border: none;width: 180px;" >用户名:</label>';
            html += '<input  style="width:87%;margin: auto;color: #000!important;" name="userName" value="' + userName + '" lay-verify="required"  class="layui-input" >';
            html += '<label class="layui-form-label" style="border: none;width: 180px;" >昵称:</label>';
            html += '<input  style="width:87%;margin: auto;color: #000!important;" name="nickName" value="' + nickName + '" lay-verify="required"  class="layui-input" >';
            html += '<label class="layui-form-label" style="border: none;width: 180px;" >邮箱:</label>';
            html += '<input  style="width:87%;margin: auto;color: #000!important;" name="email" value="' + email + '" lay-verify="required"  class="layui-input" >';
            html += '<label class="layui-form-label" style="border: none;width: 120px;" >头像地址:</label>';
            html += '<img id="headImgShow" src="" width="40px" height="40px" />';
            html += '<input  style="width:87%;margin: auto;color: #000!important;" name="headImgUrl" value="' + headImgUrl + '" lay-verify="url"  class="layui-input" onblur="layui.datalist.headImgShow()" >';
            html += '<label class="layui-form-label" style="border: none;width: 180px;" >个性签名:</label>';
            html += '<input  style="width:87%;margin: auto;color: #000!important;" name="motto" value="' + motto + '" lay-verify="required"  class="layui-input" >';
            html += '<div class="layui-form-item">';
            html += '<div class="layui-input-inline" style="margin:10px auto 0 auto;display: block;float: none;">';
            html += '<button class="layui-btn" id="submit"  lay-submit="" lay-filter="updateAccountSubmit">立即修改</button>';
            html += '<button type="reset" class="layui-btn layui-btn-primary">重置</button>';
            html += '</div>';
            html += '</div>';
            html += '</form>';

            layer.open({
                type: 1,
                skin: 'layui-layer-rim', //加上边框
                area: '620px', //宽高
                title: '修改用户',
                content: html
            });
            layui.funcs.headImgShow()
        },
        deleteData: function (id) {
            layer.confirm('您确定要删除吗？', {
                btn: ['确定', '取消'] //按钮
            }, function () {
                $.ajax({
                    url: '/admin/user/remove',
                    data: {"id" : id },
                    type: 'POST',
                    success: function (result) {
                        if (result.success) {
                            layer.alert('删除成功!', {
                                closeBtn: 0,
                                icon: 1
                            }, function () {
                                window.location.href = "/static/admin/accountManagement.html"
                            });
                        } else {
                            layer.alert("删除用户失败[" + resp.msg + "] !", {icon: 5});
                        }
                    }
                });
            }, function () {

            });
        },
        headImgShow : function() {
            $("#headImgShow").attr("src", $("[name='headImgUrl']").val());
        }
    };
    exports('funcs', funcs);
});

