/**
 * commentManagement.js
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/25/2017 7:41 PM
 */
var sPageNow = sessionStorageGet(location.href)
if(isEmpty(sPageNow) ) {
    sPageNow = 1
}

// 加载类型, 标签, 同步加载, 否则 可能 layui 绑定不了事件
initTypeAndTags()

layui.define(['element', 'laypage', 'layer', 'form'], function (exports) {
    var $ = layui.jquery;
    var layer = layui.layer;
    var form = layui.form();
    var laypage = layui.laypage;
    var element = layui.element();
    var laypageId = 'pageNav';

    /**
     * 弹出层相关id
     * @type {null}
     */
    var addReplyConfirmDialog = null, addReplyDialog = null

    form.on('submit(formSearch)', function (data) {
        var index = layer.load(1);
        doSearch(layer, index, 1)
        return false;
    });

    initilData(sPageNow);
    function initilData(pageNow) {
        var index = layer.load(1);

        //模拟数据加载
        setTimeout(doSearch(layer, index, pageNow), 1000);
    }
    function doSearch(layer, index, pageNow) {
        var params = $("#commentSearchForm").serialize()
        params += "&pageNow=" + pageNow
        params += "&pageSize=" + pageSize

        layer.close(index);
        ajax({
            url: reqMap.comment.adminList,
            type: "GET",
            data: params,
            success: function (resp) {
                if (resp.success) {
                    var html = new StringBuilder();
                    var comments = resp.data.list
                    for (var idx in comments) {
                        var item = comments[idx]
                        var blogDetailUrl = formatContextUrl("/static/main/blogDetail.html?id=" + item.blogId)
                        html.append('<tr>')
                        html.append('<td>' + item.id + '</td>')
                        html.append('<td><a href="' + blogDetailUrl + '" target="_blank" >' + item.blogName + '</a></td>')
                        html.append('<td>' + item.floorId + '</td>')
                        html.append('<td>' + item.commentId + '</td>')
                        html.append('<td>' + item.name + '</td>')
                        html.append('<td><img src="' + item.headImgUrl + '" width="40px" height="40px" /> </td>')
                        html.append('<td>' + item.toUser + '</td>')
                        html.append('<td>' + item.comment + '</td>')
                        html.append('<td>' + item.createdAt + '</td>')
                        html.append('<td><i class="layui-icon layui-btn-small" style="cursor:pointer;font-size: 30px; color: #FA4B2A;vertical-align: middle;" onclick="layui.funcs.addReply(' + item.id + ', ' + item.blogId + ', ' + item.floorId + ', ' + item.commentId + ',\'' + item.name + '\',\'' + transferQuote(encodeURI(item.comment)) + '\')" >&#x1005;</i> </td>')
                        html.append('<td><button class="layui-btn layui-btn-small" onclick=\'layui.funcs.showData("' + item.blogName + '", ' + item.blogId + ', ' + item.floorId + ')\'><i class="layui-icon">&#xe63a;</i></button></td>')
                        html.append('<td><button class="layui-btn layui-btn-small layui-btn-normal" onclick="layui.funcs.editData(' + item.id + ', ' + item.blogId + ', \'' + item.toUser + '\',\'' + transferQuote(encodeURI(item.comment)) + '\')"><i class="layui-icon">&#xe642;</i></button></td>')
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
                    layer.alert("拉取评论列表失败[" + resp.data + "] !", {icon: 5});
                }
            }
        }, 500);
    }

    form.on('submit(addReplySubmit)', function(data){
        ajax({
            url : reqMap.comment.adminAdd,
            type : "POST",
            data : $("#addReplyForm").serialize(),
            success : function (resp) {
                if(resp.success) {
                    addReplyConfirmDialog = layer.alert('添加评论成功!', {
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

    form.on('submit(editCommentSubmit)', function(data){
        ajax({
            url : reqMap.comment.update,
            type : "POST",
            data : $("#updateReplyForm").serialize(),
            success : function (resp) {
                if(resp.success) {
                    layer.alert('更新评论成功!', {closeBtn: 0, icon: 1 }, function () {
                        refresh()
                    });
                } else {
                    layer.alert("更新评论失败[" + resp.data + "] !", {icon: 5});
                }
            }
        });
        return false
    })

    //输出接口，主要是两个函数，一个删除一个编辑
    var funcs = {
        showData: function (blogName, blogId, floorId) {
            ajax({
                url: reqMap.comment.commentsForFloor,
                type: "GET",
                data: {
                    "blogId" : blogId,
                    "floorId" : floorId
                },
                success: function (resp) {
                    if(resp.success) {
                        var html = new StringBuilder();
                        html.append('<fieldset  class="layui-elem-field layui-field-title sys-list-field" >')
                        html.append('<legend style="text-align:center;"><span> 博客 : ' + blogName + ' 层数 : ' + floorId + '</span></legend>')
                        html.append('<div class="layui-field-box layui-form">')

                        var comments = resp.data.list
                        var incr = 20, marginLeft = incr
                        for (var idx in comments) {
                            var reply = comments[idx]
                            html.append('<blockquote class="layui-elem-quote" style="margin-left: ' + marginLeft + 'px" >')
                            html.append('<span style="cursor:pointer" alt="reply" onclick="layui.funcs.addReply(' + reply.id + ', ' + reply.blogId + ', ' + reply.floorId + ', ' + reply.commentId + ',\'' + reply.name + '\',\'' + encodeURI(reply.comment) + '\')" >[' + reply.name + '] @ [' + reply.toUser + '] : ' + reply.comment + '</span>')
                            html.append('</blockquote>')
                            marginLeft += incr
                        }
                        html.append('<hr>')
                        html.append('</div>')
                        html.append('</fieldset>')
                        layer.open({
                            type: 1,
                            skin: 'layui-layer-rim', //加上边框
                            area: ['800px', '600px'], //宽高
                            title: '查看回复',
                            content: html.toString()
                        });
                    } else {
                        layer.alert("拉取给定的博客的评论列表失败[" + resp.data + "] !", {icon: 5});
                    }
                }
            })
        },
        addReply: function (id, blogId, floorId, commentId, toUser, comment) {
            var html = new StringBuilder();
            html.append('<form id="addReplyForm" class="layui-form layui-form-pane" action="' + reqMap.comment.reply + '" method="post">')
            html.append('<input type="hidden" name="blogId" value="' + blogId + '"/>')
            html.append('<input type="hidden" name="floorId" value="' + floorId + '"/>')
            html.append('<input type="hidden" name="commentId" value="' + commentId + '"/>')
            html.append('<input type="hidden" name="toUser" value="' + toUser + '"/>')
            html.append('<label class="layui-form-label" style="border: none" >评论内容:</label>')
            html.append('<div  style="width:87%;margin: auto;color: #000!important;"  readonly="true" class="layui-textarea layui-disabled" >' + decodeURI(detransferQuote(comment)) + '</div>')
            html.append('<label class="layui-form-label" style="border: none">回复内容:</label>')
            html.append('<textarea placeholder="请输入回复内容" name="comment" lay-verify="required" style="width:87%;margin: auto" class="layui-textarea " >[reply]' + toUser + '[/reply]</textarea>')
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
                title: '回复评论',
                content: html.toString()
            });
        },
        editData: function (id, blogId, toUser, comment) {
            var html = new StringBuilder();
            html.append('<form id="updateReplyForm" class="layui-form layui-form-pane" action="' + reqMap.comment.update + '" method="post">')
            html.append('<input type="hidden" name="id" value="' + id + '"/>')
            html.append('<input type="hidden" name="blogId" value="' + blogId + '"/>')
            html.append('<label class="layui-form-label" style="border: none" >目标用户:</label>')
            html.append('<input  style="width:87%;margin: auto;color: #000!important;" name="toUser" value="' + toUser + '" class="layui-input" lay-verify="required" >')
            html.append('<label class="layui-form-label" style="border: none">回复内容:</label>')
            html.append('<textarea placeholder="请输入回复内容" name="comment" lay-verify="required" style="width:87%;margin: auto" class="layui-textarea " > ' + decodeURI(detransferQuote(comment)) + ' </textarea>')
            html.append('<div class="layui-form-item">')
            html.append('<div class="layui-input-inline" style="margin:10px auto 0 auto;display: block;float: none;">')
            html.append('<button class="layui-btn" id="submit"  lay-submit="" lay-filter="editCommentSubmit">立即提交</button>')
            html.append('<button type="reset" class="layui-btn layui-btn-primary">重置</button>')
            html.append('</div>')
            html.append('</div>')
            html.append('</form>')

            layer.open({
                type: 1,
                skin: 'layui-layer-rim', //加上边框
                area: ['800px', '600px'], //宽高
                title: '修改评论',
                content: html.toString()
            });
        },
        deleteData: function (id) {
            layer.confirm('同时会删除对应回复，确定删除？', {
                btn: ['确定', '取消'] //按钮
            }, function () {
                ajax({
                    url: reqMap.comment.remove,
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
                            layer.alert("删除评论失败[" + resp.data + "] !", {icon: 5});
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
 * 加载类型 和标签列表
 */
function initTypeAndTags() {
    ajax({
        url: reqMap.composite.typeAndTags,
        type: "GET",
        success: function (resp) {
            if (resp.success) {
                var types = resp.data.types
                var typeIdEle = $("#typeId")
                typeIdEle.append("<option value=''> 全部 </option>")
                for (idx in types) {
                    typeIdEle.append("<option value='" + types[idx].id + "'> " + types[idx].name + " </option>")
                }

                var tags = resp.data.tags
                var tagIdEle = $("#tagId")
                tagIdEle.append("<option value=''> 全部 </option>")
                for (idx in tags) {
                    tagIdEle.append("<option value='" + tags[idx].id + "'> " + tags[idx].name + " </option>")
                }
            } else {
                layer.alert("拉取类型/标签列表失败[" + resp.data + "] !", {icon: 5});
            }
        }
    });
}
