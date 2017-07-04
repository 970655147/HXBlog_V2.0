/**
 * accountManagement.js
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/24/2017 9:33 PM
 */

var sPageNow = sessionStorageGet(location.href)
if(isEmpty(sPageNow) ) {
    sPageNow = 1
}

layui.define(['element', 'laypage', 'layer', 'form'], function (exports) {
    var $ = layui.jquery;
    var layer = layui.layer;
    var form = layui.form();
    var laypage = layui.laypage;
    var element = layui.element();
    var laypageId = 'pageNav';

    initilData(sPageNow);
    //页数据初始化
    function initilData(pageNow) {
        var index = layer.load(1);
        //模拟数据加载
        setTimeout(function () {
            layer.close(index);
            ajax({
                url: reqMap.user.list,
                type: "GET",
                data: {
                    pageNow: pageNow,
                    pageSize: pageSize
                },
                success: function (resp) {
                    if (resp.success) {
                        var users = resp.data.list
                        var html = new StringBuilder()
                        for (var i in users) {
                            var item = users[i];
                            html.append('<tr>')
                            html.append('<td>' + item.id + '</td>')
                            html.append('<td>' + item.userName + '</td>')
                            html.append('<td>' + item.nickName + '</td>')
                            html.append('<td>' + item.title + '</td>')
                            html.append('<td>' + item.email + '</td>')
                            html.append('<td><img src="' + item.headImgUrl + '" width="60px" height="60px" /></td>')
                            html.append('<td>' + item.motto + '</td>')
                            html.append('<td>' + item.lastLoginIp + '</td>')
                            html.append('<td>' + item.lastLoginAt + '</td>')
                            html.append('<td>' + item.createdAt + '</td>')
                            html.append('<td><button class="layui-btn layui-btn-small layui-btn-normal" onclick="layui.funcs.editData(' + item.id + ',\'' + item.userName + '\',\'' + item.nickName + '\',\'' + item.title + '\',\'' + item.email + '\',\'' + item.headImgUrl + '\',\'' + transferQuote(encodeURI(item.motto)) + '\')" ><i class="layui-icon">&#xe642;</i></button></td>')
                            html.append('<td><button class="layui-btn layui-btn-small layui-btn-danger" onclick="layui.funcs.deleteData(' + item.id + ')"><i class="layui-icon">&#xe640;</i></button></td>')
                            html.append('</tr>')
                        }
                        $('#dataContent').html(html.toString());
                        element.init();

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
                        layer.alert("拉取用户列表失败[" + resp.data + "] !", {icon: 5});
                    }
                }
            });
        }, 500);
    }

    form.on('submit(addAccountSubmit)', function (data) {
        ajax({
            url: reqMap.user.add,
            type: "POST",
            data: $("#addAccountForm").serialize(),
            success: function (resp) {
                if (resp.success) {
                    layer.alert('添加用户成功!', {
                        closeBtn: 0,
                        icon: 1
                    }, function () {
                        refresh()
                    });
                } else {
                    layer.alert("添加用户失败[" + resp.data + "] !", {icon: 5});
                }
            }
        });
        return false
    })

    form.on('submit(updateAccountSubmit)', function (data) {
        ajax({
            url: reqMap.user.update,
            type: "POST",
            data: $("#updateAccountForm").serialize(),
            success: function (resp) {
                if (resp.success) {
                    layer.alert('更新用户成功!', {
                        closeBtn: 0,
                        icon: 1
                    }, function () {
                        refresh()
                    });
                } else {
                    layer.alert("更新用户失败[" + resp.data + "] !", {icon: 5});
                }
            }
        });
        return false
    })

    //输出接口，主要是两个函数，一个删除一个编辑
    var funcs = {
        addData: function () {
            var html = new StringBuilder()
            html.append('<form id="addAccountForm" class="layui-form layui-form-pane" action="/admin/user/add" method="post">')
            html.append('<label class="layui-form-label" style="border: none;width: 180px;" >用户名:</label>')
            html.append('<input  style="width:87%;margin: auto;color: #000!important;" name="userName" lay-verify="required"  class="layui-input" >')
            html.append('<label class="layui-form-label" style="border: none;width: 180px;" >密码:</label>')
            html.append('<input  style="width:87%;margin: auto;color: #000!important;" name="password" type="password"  class="layui-input" >')
            html.append('<label class="layui-form-label" style="border: none;width: 180px;" >昵称:</label>')
            html.append('<input  style="width:87%;margin: auto;color: #000!important;" name="nickName" lay-verify="required"  class="layui-input" >')
            html.append('<label class="layui-form-label" style="border: none;width: 180px;" >称号:</label>')
            html.append('<input  style="width:87%;margin: auto;color: #000!important;" name="title" lay-verify="required"  class="layui-input" >')
            html.append('<label class="layui-form-label" style="border: none;width: 180px;" >邮箱:</label>')
            html.append('<input  style="width:87%;margin: auto;color: #000!important;" name="email" lay-verify="required"  class="layui-input" >')
            html.append('<label class="layui-form-label" style="border: none;width: 120px;" >头像地址:</label>')
            html.append('<img id="headImgShow" src="" width="40px" height="40px" />')
            html.append('<input  style="width:87%;margin: auto;color: #000!important;" name="headImgUrl" lay-verify="url"  class="layui-input" onblur="layui.datalist.headImgShow()" >')
            html.append('<label class="layui-form-label" style="border: none;width: 180px;" >个性签名:</label>')
            html.append('<input  style="width:87%;margin: auto;color: #000!important;" name="motto" lay-verify="required"  class="layui-input" >')
            html.append('<div class="layui-form-item">')
            html.append('<div class="layui-input-inline" style="margin:10px auto 0 auto;display: block;float: none;">')
            html.append('<button class="layui-btn" id="submit"  lay-submit="" lay-filter="addAccountSubmit">添加</button>')
            html.append('<button type="reset" class="layui-btn layui-btn-primary">重置</button>')
            html.append('</div>')
            html.append('</div>')
            html.append('</form>')

            layer.open({
                type: 1,
                skin: 'layui-layer-rim', //加上边框
                area: '620px', //宽高
                title: '添加用户',
                content: html.toString()
            })

            $("[name='headImgUrl']").val("http://tb.himg.baidu.com/sys/portrait/item/48c0c0b6b7e7393730363535313437f02d")
            layui.funcs.headImgShow()
        },
        editData: function (id, userName, nickName, title, email, headImgUrl, motto) {
            var html = new StringBuilder()
            html.append('<form id="updateAccountForm" class="layui-form layui-form-pane" action="/admin/user/update" method="post">')
            html.append('<input type="hidden" name="id" value="' + id + '"/>')
            html.append('<label class="layui-form-label" style="border: none;width: 180px;" >用户名:</label>')
            html.append('<input  style="width:87%;margin: auto;color: #000!important;" name="userName" value="' + userName + '" lay-verify="required"  class="layui-input" readonly >')
            html.append('<label class="layui-form-label" style="border: none;width: 180px;" >昵称:</label>')
            html.append('<input  style="width:87%;margin: auto;color: #000!important;" name="nickName" value="' + nickName + '" lay-verify="required"  class="layui-input" >')
            html.append('<label class="layui-form-label" style="border: none;width: 180px;" >称号:</label>')
            html.append('<input  style="width:87%;margin: auto;color: #000!important;" name="title" value="' + title + '" lay-verify="required"  class="layui-input" >')
            html.append('<label class="layui-form-label" style="border: none;width: 180px;" >邮箱:</label>')
            html.append('<input  style="width:87%;margin: auto;color: #000!important;" name="email" value="' + email + '" lay-verify="required"  class="layui-input" >')
            html.append('<label class="layui-form-label" style="border: none;width: 120px;" >头像地址:</label>')
            html.append('<img id="headImgShow" src="" width="40px" height="40px" />')
            html.append('<input  style="width:87%;margin: auto;color: #000!important;" name="headImgUrl" value="' + headImgUrl + '" lay-verify="url"  class="layui-input" onblur="layui.datalist.headImgShow()" >')
            html.append('<label class="layui-form-label" style="border: none;width: 180px;" >个性签名:</label>')
            html.append('<input  style="width:87%;margin: auto;color: #000!important;" name="motto" value="' + decodeURI(detransferQuote(motto)) + '" lay-verify="required"  class="layui-input" >')
            html.append('<div class="layui-form-item">')
            html.append('<div class="layui-input-inline" style="margin:10px auto 0 auto;display: block;float: none;">')
            html.append('<button class="layui-btn" id="submit"  lay-submit="" lay-filter="updateAccountSubmit">立即修改</button>')
            html.append('<button type="reset" class="layui-btn layui-btn-primary">重置</button>')
            html.append('</div>')
            html.append('</div>')
            html.append('</form>')

            layer.open({
                type: 1,
                skin: 'layui-layer-rim', //加上边框
                area: '620px', //宽高
                title: '修改用户',
                content: html.toString()
            });
            layui.funcs.headImgShow()
        },
        deleteData: function (id) {
            layer.confirm('您确定要删除吗？', {
                btn: ['确定', '取消'] //按钮
            }, function () {
                ajax({
                    url: reqMap.user.remove,
                    data: {"id" : id },
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
                            layer.alert("删除用户失败[" + resp.data + "] !", {icon: 5});
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

