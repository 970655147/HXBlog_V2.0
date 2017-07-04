/**
 * messageManagement.js
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/24/2017 9:56 PM
 */
var sPageNow = sessionStorageGet(location.href)
if(isEmpty(sPageNow) ) {
    sPageNow = 1
}

// 加载类型, 标签, 同步加载, 否则 可能 layui 绑定不了事件
initUserAndRoles()

layui.define(['element', 'laypage', 'layer', 'form', 'pagesize'], function (exports) {
    var $ = layui.jquery;
    var layer = layui.layer;
    var form = layui.form();
    var laypage = layui.laypage;
    var element = layui.element();
    var laypageId = 'pageNav';

    form.on('submit(formSearch)', function (data) {
        var index = layer.load(1);
        doSearch(layer, index, 1)
        return false;
    });

    initilData(sPageNow);
    //页数据初始化
    function initilData(pageNow) {
        var index = layer.load(1);
        setTimeout(doSearch(layer, index, pageNow), 1000);
    }

    function doSearch(layer, index, pageNow) {
        layer.close(index);
        var params = $("#messageSearchForm").serialize()
        params += "&pageNow=" + pageNow
        params += "&pageSize=" + pageSize

        ajax({
            url: reqMap.message.adminList,
            type: "GET",
            data: params,
            success: function (resp) {
                if (resp.success) {
                    var html = new StringBuilder();
                    for (var i in resp.data.list) {
                        var item = resp.data.list[i];
                        html.append('<tr>')
                        html.append('<td>' + item.id + '</td>')
                        html.append('<td>' + item.senderName + '</td>')
                        html.append('<td>' + item.receiverName + '</td>')
                        html.append('<td>' + item.createdAt + '</td>')
                        html.append('<td>' + item.subject + '</td>')
                        html.append('<td>' + item.content + '</td>')
                        html.append('<td><button class="layui-btn layui-btn-small layui-btn-normal" onclick="layui.funcs.showData(' + item.id + ', \'' + item.senderName + '\', \'' + item.createdAt + '\', \'' + item.subject + '\', \'' + transferQuote(encodeURI(item.content)) + '\')"><i class="layui-icon">&#x1005;</i></button></td>')
                        html.append('<td><button class="layui-btn layui-btn-small layui-btn-normal" onclick="layui.funcs.addReply(' + item.id + ', \'' + item.senderId + '\', \'' + item.senderName + '\', \'' + item.createdAt + '\', \'' + item.subject + '\', \'' + transferQuote(encodeURI(item.content)) + '\')"><i class="layui-icon">&#xe63a;</i></button></td>')
                        html.append('<td><button class="layui-btn layui-btn-small layui-btn-normal" onclick="layui.funcs.editData(' + item.id + ', \'' + item.senderName + '\', \'' + item.createdAt + '\', \'' + item.type + '\', \'' + item.subject + '\', \'' + transferQuote(encodeURI(item.content)) + '\')"><i class="layui-icon">&#xe642;</i></button></td>')
                        html.append('<td><button class="layui-btn layui-btn-small layui-btn-danger" onclick="layui.funcs.deleteData(' + item.id + ')"><i class="layui-icon">&#xe640;</i></button></td>')
                        html.append('</tr>')
                    }
                    $('#dataContent').html(html.toString());
                    element.init();

                    form.render('checkbox');  //重新渲染CheckBox，编辑和添加的时候
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
                    layer.alert("拉取消息列表失败[" + resp.data + "] !", {icon: 5});
                }
            }
        });
    }

    var addReplyDialog, addReplyConfirmDialog
    var showMsgDialog
    form.on('submit(addReplySubmit)', function(data){
        ajax({
            url : reqMap.message.add,
            type : "POST",
            data : $("#addReplyForm").serialize(),
            success : function (resp) {
                if(resp.success) {
                    addReplyConfirmDialog = layer.alert('回复成功!', {
                        closeBtn: 0,
                        icon: 1
                    }, function () {
                        layer.close(addReplyConfirmDialog)
                        layer.close(addReplyDialog)
                    });
                } else {
                    layer.alert("添加评论失败[" + resp.data + "] !", {icon: 5});
                }
            }
        });
        return false
    })

    form.on('submit(editMessageSubmit)', function(data){
        ajax({
            url : reqMap.message.update,
            type : "POST",
            data : $("#editMessageForm").serialize(),
            success : function (resp) {
                if(resp.success) {
                    layer.alert('更新消息成功!', {closeBtn: 0, icon: 1 }, function () {
                        refresh()
                    });
                } else {
                    layer.alert("更新消息失败[" + resp.data + "] !", {icon: 5});
                }
            }
        });
        return false
    })

    form.on('submit(showMsgFormSubmit)', function (data) {
        layer.close(showMsgDialog)
        return false
    })

    //输出接口，主要是两个函数，一个删除一个编辑
    var funcs = {
        showData: function (id, senderName, createdAt, subject, content) {
            var html = new StringBuilder();
            html += '<div id="showMsgForm" class="layui-form layui-form-pane" action="#" method="post">'
            html.append('<fieldset  class="layui-elem-field layui-field-title sys-list-field" >')
            html.append('<legend style="text-align:center;"><span> 发送者 : ' + senderName + ' 时间 : ' + createdAt + '</span></legend>')
            html.append('<div class="layui-field-box layui-form">')
            html.append('<input style="width:87%;margin: auto;color: #000!important;" value="' + subject + '" class="layui-input" readonly >')
            html += '<hr>'
            html.append('<div style="width:87%;margin: auto;color: #000!important; height:200px" name="content" class="layui-area" readonly >' + decodeURI(detransferQuote(content)) + '</div>')
            html.append('<hr>')
            html.append('</div>')
            html.append('</fieldset>')
            html.append('<div class="layui-form-item">')
            html.append('<div class="layui-input-inline" style="margin:10px auto 0 auto;display: block;float: none;">')
            html.append('<button class="layui-btn" id="submit"  lay-submit="" lay-filter="showMsgFormSubmit">确认</button>')
            html.append('</div>')
            html.append('</div>')
            html.append('</div>')

            showMsgDialog = layer.open({
                type: 1,
                skin: 'layui-layer-rim', //加上边框
                area: ['800px', '600px'], //宽高
                title: '查看回复',
                content: html.toString()
            });
        },
        addReply: function (id, senderId, senderName, createdAt, subject, content) {
            var html = new StringBuilder();
            html.append('<form id="addReplyForm" class="layui-form layui-form-pane" action="/admin/message/add" method="post">')
            html.append('<input type="hidden" name="userIds" value="' + senderId + '"/>')
            html.append('<input type="hidden" name="type" value="2"/>')
            html.append('<label class="layui-form-label" style="border: none" >消息内容:</label>')
            html.append('<div  style="width:87%;margin: auto;color: #000!important;"  readonly="true" class="layui-textarea layui-disabled" >' + decodeURI(detransferQuote(content)) + '</div>')
            html.append('<label class="layui-form-label" style="border: none">主题:</label>')
            html.append('<input placeholder="请输入回复主题" name="subject" lay-verify="required" style="width:87%;margin: auto" class="layui-input " />')
            html.append('<label class="layui-form-label" style="border: none">回复内容:</label>')
            html.append('<textarea placeholder="请输入回复内容" name="content" lay-verify="required" style="width:87%;margin: auto" class="layui-textarea " ></textarea>')
            html.append('<div class="layui-form-item">')
            html.append('<div class="layui-input-inline" style="margin:10px auto 0 auto;display: block;float: none;">')
            html.append('<button class="layui-btn" id="submit"  lay-submit="" lay-filter="addReplySubmit">立即提交</button>')
            html.append('<button type="reset" class="layui-btn layui-btn-primary">重置</button>')
            html.append('</div>')
            html.append('</div>')
            html.append('</form>')

            addReplyDialog = layer.open({
                type: 1,
                skin: 'layui-layer-rim', //加上边框
                area: ['800px', '600px'], //宽高
                title: '回复消息',
                content: html.toString()
            });
        },
        editData: function (id, senderName, createdAt, type, subject, content) {
            var html = new StringBuilder();
            html.append('<form id="editMessageForm" class="layui-form layui-form-pane" action="/admin/message/update" method="post">')
            html.append('<input type="hidden" name="id" value="' + id + '"/>')
            html.append('<input type="hidden" name="type" value="' + type + '"/>')
            html.append('<fieldset  class="layui-elem-field layui-field-title sys-list-field" >')
            html.append('<legend style="text-align:center;"><span> 发送者 : ' + senderName + ' 时间 : ' + createdAt + '</span></legend>')
            html.append('<div class="layui-field-box layui-form">')
            html.append('<input style="width:87%;margin: auto;color: #000!important;" value="' + subject + '" lay-verify="required"  class="layui-input" >')
            html += '<hr>'
            html.append('<textarea style="width:87%;margin: auto;color: #000!important; height:200px" lay-verify="required" name="content" class="layui-area" >' + decodeURI(detransferQuote(content)) + '</textarea>')
            html.append('<hr>')
            html.append('</div>')
            html.append('<div class="layui-form-item">')
            html.append('<div class="layui-input-inline" style="margin:10px auto 0 auto;display: block;float: none;">')
            html.append('<button class="layui-btn" id="submit"  lay-submit="" lay-filter="editMessageSubmit">立即提交</button>')
            html.append('<button type="reset" class="layui-btn layui-btn-primary">重置</button>')
            html.append('</div>')
            html.append('</div>')
            html.append('</fieldset>')
            html.append('</form>')
            layer.open({
                type: 1,
                skin: 'layui-layer-rim', //加上边框
                area: ['800px', '600px'], //宽高
                title: '编辑消息',
                content: html.toString()
            });
        },
        deleteData: function (id) {
            layer.confirm('确定删除该消息？', {
                btn: ['确定', '取消'] //按钮
            }, function () {
                ajax({
                    url: reqMap.message.remove,
                    data: {"id" : id },
                    type: 'POST',
                    success: function (resp) {
                        if (resp.success) {
                            layer.alert('删除成功!', {closeBtn: 0, icon: 1}, function () {
                                refresh()
                            });
                        } else {
                            layer.alert("删除消息失败[" + resp.data + "] !", {icon: 5});
                        }
                    }
                });
            }, function () {

            });
        }
    };
    exports('funcs', funcs);
});

/**
 * 加载用户 角色列表
 */
function initUserAndRoles() {
    ajax({
        url: reqMap.composite.userAndRoles,
        type: "GET",
        success: function (resp) {
            if (resp.success) {
                var senderIdsEle = $("#senderIds")
                var receiverIdsEle = $("#receiverIds")
                var users = resp.data.users
                for (idx in users) {
                    senderIdsEle.append("<option value='" + users[idx].id + "'> " + users[idx].name + " </option>")
                    receiverIdsEle.append("<option value='" + users[idx].id + "'> " + users[idx].name + " </option>")
                }

                var rolesEle = $("#roleIds")
                var roles = resp.data.roles
                for (idx in roles) {
                    rolesEle.append("<option value='" + roles[idx].id + "'> " + roles[idx].name + " </option>")
                }
            } else {
                layer.alert("拉取用户/角色列表失败[" + resp.data + "] !", {icon: 5});
            }
        }
    });
}

