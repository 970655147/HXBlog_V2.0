
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

    initilData(1);
    //页数据初始化

    function initilData(pageNow) {
        var index = layer.load(1);

        //模拟数据加载
        setTimeout(doSearch(layer, index, pageNow), 1000);
    }

    function doSearch(layer, index, pageNow) {
        layer.close(index);
        var params = $("#messageSearchForm").serialize()
        params += "&pageNow=" + pageNow
        params += "&pageSize=" + pageSize

        ajax({
            url: reqMap.message.list,
            type: "GET",
            data: params,
            success: function (resp) {
                if (resp.success) {
                    var html = '';
                    for (var i in resp.data.list) {
                        var item = resp.data.list[i];
                        html += '<tr>';
                        html += '<td>' + item.id + '</td>';
                        html += '<td>' + item.senderName + '</td>';
                        html += '<td>' + item.receiverName + '</td>';
                        html += '<td>' + item.createdAt + '</td>';
                        html += '<td>' + item.subject + '</td>';
                        html += '<td>' + item.content + '</td>';
                        html += '<td><button class="layui-btn layui-btn-small layui-btn-normal" onclick="layui.funcs.showData(' + item.id + ', \'' + item.senderName + '\', \'' + item.createdAt + '\', \'' + item.subject + '\', \'' + encodeURI(item.content) + '\')"><i class="layui-icon">&#x1005;</i></button></td>';
                        html += '<td><button class="layui-btn layui-btn-small layui-btn-normal" onclick="layui.funcs.addReply(' + item.id + ', \'' + item.senderId + '\', \'' + item.senderName + '\', \'' + item.createdAt + '\', \'' + item.subject + '\', \'' + encodeURI(item.content) + '\')"><i class="layui-icon">&#xe63a;</i></button></td>';
                        html += '<td><button class="layui-btn layui-btn-small layui-btn-normal" onclick="layui.funcs.editData(' + item.id + ', \'' + item.senderName + '\', \'' + item.createdAt + '\', \'' + item.subject + '\', \'' + encodeURI(item.content) + '\')"><i class="layui-icon">&#xe642;</i></button></td>';
                        html += '<td><button class="layui-btn layui-btn-small layui-btn-danger" onclick="layui.funcs.deleteData(' + item.id + ')"><i class="layui-icon">&#xe640;</i></button></td>';
                        html += '</tr>';
                    }
                    $('#dataContent').html(html);
                    element.init();

                    form.render('checkbox');  //重新渲染CheckBox，编辑和添加的时候
                    $('#dataConsole, #dataList').attr('style', 'display:block'); //显示FiledBox
                    laypage({
                        cont: laypageId,
                        pages: resp.data.totalPage,
                        groups: 5,
                        skip: true,
                        curr: pageNow,
                        jump: function (obj, first) {
                            var pageNow = obj.curr;
                            if (! first) {
                                initilData(pageNow);
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

    //输出接口，主要是两个函数，一个删除一个编辑
    var funcs = {
        showData: function (id, senderName, createdAt, subject, content) {
            var html = '';
            html += '<fieldset  class="layui-elem-field layui-field-title sys-list-field" >';
            html += '<legend style="text-align:center;"><span> 发送者 : ' + senderName + ' 时间 : ' + createdAt + '</span></legend>';
            html += '<div class="layui-field-box layui-form">';
            html += '<input style="width:87%;margin: auto;color: #000!important;" value="' + subject + '" lay-verify="required"  class="layui-input" readonly >';
            html += '<hr>'
            html += '<div style="width:87%;margin: auto;color: #000!important; height:200px" lay-verify="required" name="content" class="layui-area" readonly >' + decodeURI(content) + '</div>';
            html += '<hr>';
            html += '</div>';
            html += '</fieldset>';
            layer.open({
                type: 1,
                skin: 'layui-layer-rim', //加上边框
                area: ['800px', '600px'], //宽高
                title: '查看回复',
                content: html
            });
        },
        addReply: function (id, senderId, senderName, createdAt, subject, content) {
            var html = '';
            html += '<form id="addReplyForm" class="layui-form layui-form-pane" action="/admin/message/add" method="post">';
            html += '<input type="hidden" name="userIds" value="' + senderId + '"/>';
            html += '<label class="layui-form-label" style="border: none" >消息内容:</label>';
            html += '<div  style="width:87%;margin: auto;color: #000!important;"  readonly="true" class="layui-textarea layui-disabled" >' + decodeURI(content) + '</div>';
            html += '<label class="layui-form-label" style="border: none">主题:</label>';
            html += '<input placeholder="请输入回复主题" name="subject" lay-verify="required" style="width:87%;margin: auto" class="layui-input " />';
            html += '<label class="layui-form-label" style="border: none">回复内容:</label>';
            html += '<textarea placeholder="请输入回复内容" name="content" lay-verify="required" style="width:87%;margin: auto" class="layui-textarea " ></textarea>';
            html += '<div class="layui-form-item">';
            html += '<div class="layui-input-inline" style="margin:10px auto 0 auto;display: block;float: none;">';
            html += '<button class="layui-btn" id="submit"  lay-submit="" lay-filter="addReplySubmit">立即提交</button>';
            html += '<button type="reset" class="layui-btn layui-btn-primary">重置</button>';
            html += '</div>';
            html += '</div>';
            html += '</form>';

            addReplyDialog = layer.open({
                type: 1,
                skin: 'layui-layer-rim', //加上边框
                area: ['800px', '600px'], //宽高
                title: '回复消息',
                content: html
            });
        },
        editData: function (id, senderName, createdAt, subject, content) {
            var html = '';
            html += '<form id="editMessageForm" class="layui-form layui-form-pane" action="/admin/message/update" method="post">';
            html += '<input type="hidden" name="id" value="' + id + '"/>';
            html += '<fieldset  class="layui-elem-field layui-field-title sys-list-field" >';
            html += '<legend style="text-align:center;"><span> 发送者 : ' + senderName + ' 时间 : ' + createdAt + '</span></legend>';
            html += '<div class="layui-field-box layui-form">';
            html += '<input style="width:87%;margin: auto;color: #000!important;" value="' + subject + '" lay-verify="required"  class="layui-input" >';
            html += '<hr>'
            html += '<textarea style="width:87%;margin: auto;color: #000!important; height:200px" lay-verify="required" name="content" class="layui-area" >' + decodeURI(content) + '</textarea>';
            html += '<hr>';
            html += '</div>';
            html += '<div class="layui-form-item">';
            html += '<div class="layui-input-inline" style="margin:10px auto 0 auto;display: block;float: none;">';
            html += '<button class="layui-btn" id="submit"  lay-submit="" lay-filter="editMessageSubmit">立即提交</button>';
            html += '<button type="reset" class="layui-btn layui-btn-primary">重置</button>';
            html += '</div>';
            html += '</div>';
            html += '</fieldset>';
            html += '</form>';
            layer.open({
                type: 1,
                skin: 'layui-layer-rim', //加上边框
                area: ['800px', '600px'], //宽高
                title: '编辑消息',
                content: html
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

