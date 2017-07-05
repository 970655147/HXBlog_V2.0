/**
 * messageView.js
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

/**
 * 记录 msgId -> isConsumed
 * @type {{}}
 */
var id2Consumed = {}

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

    //页数据初始化
    initilData(sPageNow);

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
                    var html = new StringBuilder();
                    for (var i in resp.data.list) {
                        var item = resp.data.list[i];
                        id2Consumed[item.id] = item.consumed
                        html.append('<tr>')
                        html.append('<td> <img src="' + mapMessageUrl(item.consumed) + '" name="imgOf' + item.id + '" width="50px" height="40px" /> </td>')
                        html.append('<td>' + item.senderName + '</td>')
                        html.append('<td>' + item.createdAt + '</td>')
                        html.append('<td>' + item.subject + '</td>')
                        html.append('<td>' + item.content + '</td>')
                        html.append('<td><button class="layui-btn layui-btn-small layui-btn-normal" onclick="layui.funcs.showData(' + item.id + ', \'' + item.senderName + '\', \'' + item.createdAt + '\', \'' + item.subject + '\', \'' + transferQuote(encodeURI(item.content)) + '\')"><i class="layui-icon">&#x1005;</i></button></td>')
                        html.append('<td><button class="layui-btn layui-btn-small layui-btn-normal" onclick="layui.funcs.addReply(' + item.id + ', \'' + item.senderId + '\', \'' + item.senderName + '\', \'' + item.createdAt + '\', \'' + item.subject + '\', \'' + transferQuote(encodeURI(item.content)) + '\')"><i class="layui-icon">&#xe63a;</i></button></td>')
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
    form.on('submit(addReplySubmit)', function (data) {
        ajax({
            url: reqMap.message.add,
            type: "POST",
            data: $("#addReplyForm").serialize(),
            success: function (resp) {
                if (resp.success) {
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

    form.on('submit(showMsgFormSubmit)', function (data) {
        layer.close(showMsgDialog)
        return false
    })

    //输出接口，主要是两个函数，一个删除一个编辑
    var funcs = {
        showData: function (id, senderName, createdAt, subject, content) {
            var html = new StringBuilder();
            html.append('<div id="showMsgForm" class="layui-form layui-form-pane" action="#" method="post">')
            html.append('<fieldset  class="layui-elem-field layui-field-title sys-list-field" >')
            html.append('<legend style="text-align:center;"><span> 发送者 : ' + senderName + ' 时间 : ' + createdAt + '</span></legend>')
            html.append('<div class="layui-field-box layui-form">')
            html.append('<input style="width:87%;margin: auto;color: #000!important;" value="' + subject + '" class="layui-input" readonly >')
            html.append('<hr>')
            html.append('<div style="width:87%;margin-left:20px;color: #000!important; height:200px" name="content" class="layui-area" readonly >' + decodeURI(detransferQuote(content)) + '</div>')
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
            consumeMessage(id)
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
            })
            consumeMessage(id)
        }
    };
    exports('funcs', funcs);
});

/**
 * 标记当前用户的所有消息已读
 */
function markAllConsumed() {
    ajax({
        url: reqMap.message.markAllConsumed,
        type: "POST",
        data: {},
        success: function (resp) {
            if (resp.success) {
                for(var id in id2Consumed) {
                    id2Consumed[id] = true
                    $("[name=\"imgOf" + id + "\"]").attr("src", mapMessageUrl(1))
                }

                layer.alert("标记消息成功 !");
            } else {
                layer.alert("标记消息失败[" + resp.data + "] !", {icon: 5});
            }
        }
    });
}

/**
 * 加载用户 角色列表
 */
function initUserAndRoles() {
    ajax({
        url: reqMap.composite.userAndRoles,
        type: "GET",
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
                layer.alert("拉取用户/角色列表失败[" + resp.data + "] !", {icon: 5});
            }
        }
    });
}

/**
 * 消费给定的消息
 *
 * @param id
 */
function consumeMessage(id) {
    if (!id2Consumed[id]) {
        ajax({
            url: reqMap.message.markConsumed,
            type: "POST",
            data: {id: id},
            success: function (resp) {
                id2Consumed[id] = true
                $("[name=\"imgOf" + id + "\"]").attr("src", mapMessageUrl(1))
            }
        });
    }
}

/**
 * 映射 是否已读的邮件的图标
 * @param read
 * @returns {string}
 */
function mapMessageUrl(read) {
    return (read === 1) ? "/static/admin/images/message_readed.png" :
        "/static/admin/images/message_unread.png"
}
