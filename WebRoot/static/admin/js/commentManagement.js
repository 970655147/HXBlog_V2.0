/**
 * commentManagement.js
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/25/2017 7:41 PM
 */
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

    initilData(1);
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
                    var html = '';
                    var comments = resp.data.list
                    for (var idx in comments) {
                        var item = comments[idx];
                        html += '<tr>';
                        html += '<td>' + item.id + '</td>';
                        html += '<td><a href="/static/main/blogDetail.html?id=' + item.blogId + '" target="_blank" >' + item.blogName + '</a></td>';
                        html += '<td>' + item.floorId + '</td>';
                        html += '<td>' + item.commentId + '</td>';
                        html += '<td>' + item.name + '</td>';
                        html += '<td><img src="' + item.headImgUrl + '" width="40px" height="40px" /> </td>';
                        html += '<td>' + item.toUser + '</td>';
                        html += '<td>' + item.comment + '</td>';
                        html += '<td>' + item.createdAt + '</td>';
                        html += '<td><i class="layui-icon layui-btn-small" style="cursor:pointer;font-size: 30px; color: #FA4B2A;vertical-align: middle;" onclick="layui.funcs.addReply(' + item.id + ', ' + item.blogId + ', ' + item.floorId + ', ' + item.commentId + ',\'' + item.name + '\',\'' + transferQuote(encodeURI(item.comment)) + '\')" >&#x1005;</i> </td>';
                        html += '<td><button class="layui-btn layui-btn-small" onclick=\'layui.funcs.showData("' + item.blogName + '", ' + item.blogId + ', ' + item.floorId + ')\'><i class="layui-icon">&#xe63a;</i></button></td>';
                        html += '<td><button class="layui-btn layui-btn-small layui-btn-normal" onclick="layui.funcs.editData(' + item.id + ',\'' + item.toUser + '\',\'' + transferQuote(encodeURI(item.comment)) + '\')"><i class="layui-icon">&#xe642;</i></button></td>';
                        html += '<td><button class="layui-btn layui-btn-small layui-btn-danger" onclick="layui.funcs.deleteData(' + item.id + ')"><i class="layui-icon">&#xe640;</i></button></td>';
                        html += '</tr>';
                    }
                    $('#dataContent').html(html);
                    element.init();

                    laypage({
                        cont: laypageId,
                        pages: resp.data.totalPage,
                        groups: 5,
                        skip: true,
                        curr: pageNow,
                        jump: function (obj, first) {
                            var pageNow = obj.curr;
                            if (!first) {
                                initilData(pageNow, pageSize);
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
                        var html = '';
                        html += '<fieldset  class="layui-elem-field layui-field-title sys-list-field" >';
                        html += '<legend style="text-align:center;"><span> 博客 : ' + blogName + ' 层数 : ' + floorId + '</span></legend>';
                        html += '<div class="layui-field-box layui-form">';

                        var comments = resp.data.list
                        var incr = 20, marginLeft = incr
                        for (var idx in comments) {
                            var reply = comments[idx]
                            html += '<blockquote class="layui-elem-quote" style="margin-left: ' + marginLeft + 'px" >';
                            html += '<span style="cursor:pointer" alt="reply" onclick="layui.funcs.addReply(' + reply.id + ', ' + reply.blogId + ', ' + reply.floorId + ', ' + reply.commentId + ',\'' + reply.name + '\',\'' + encodeURI(reply.comment) + '\')" >[' + reply.name + '] @ [' + reply.toUser + '] : ' + reply.comment + '</span>';
                            html += '</blockquote>';
                            marginLeft += incr
                        }
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
                    } else {
                        layer.alert("拉取给定的博客的评论列表失败[" + resp.data + "] !", {icon: 5});
                    }
                }
            })
        },
        addReply: function (id, blogId, floorId, commentId, toUser, comment) {
            var html = '';
            html += '<form id="addReplyForm" class="layui-form layui-form-pane" action="/admin/comment/reply" method="post">';
            html += '<input type="hidden" name="id" value="' + id + '"/>';
            html += '<input type="hidden" name="blogId" value="' + blogId + '"/>';
            html += '<input type="hidden" name="floorId" value="' + floorId + '"/>';
            html += '<input type="hidden" name="commentId" value="' + commentId + '"/>';
            html += '<input type="hidden" name="toUser" value="' + toUser + '"/>';
            html += '<label class="layui-form-label" style="border: none" >评论内容:</label>';
            html += '<div  style="width:87%;margin: auto;color: #000!important;"  readonly="true" class="layui-textarea layui-disabled" >' + decodeURI(detransferQuote(comment)) + '</div>';
            html += '<label class="layui-form-label" style="border: none">回复内容:</label>';
            html += '<textarea placeholder="请输入回复内容" name="comment" lay-verify="required" style="width:87%;margin: auto" class="layui-textarea " >[reply]' + toUser + '[/reply]</textarea>';
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
                title: '回复评论',
                content: html
            });
        },
        editData: function (id, toUser, comment) {
            var html = '';
            html += '<form id="updateReplyForm" class="layui-form layui-form-pane" action="/admin/comment/update" method="post">';
            html += '<input type="hidden" name="id" value="' + id + '"/>';
            html += '<label class="layui-form-label" style="border: none" >目标用户:</label>';
            html += '<input  style="width:87%;margin: auto;color: #000!important;" name="toUser" value="' + toUser + '" class="layui-input" lay-verify="required" >';
            html += '<label class="layui-form-label" style="border: none">回复内容:</label>';
            html += '<textarea placeholder="请输入回复内容" name="comment" lay-verify="required" style="width:87%;margin: auto" class="layui-textarea " > ' + decodeURI(detransferQuote(comment)) + ' </textarea>';
            html += '<div class="layui-form-item">';
            html += '<div class="layui-input-inline" style="margin:10px auto 0 auto;display: block;float: none;">';
            html += '<button class="layui-btn" id="submit"  lay-submit="" lay-filter="editCommentSubmit">立即提交</button>';
            html += '<button type="reset" class="layui-btn layui-btn-primary">重置</button>';
            html += '</div>';
            html += '</div>';
            html += '</form>';

            layer.open({
                type: 1,
                skin: 'layui-layer-rim', //加上边框
                area: ['800px', '600px'], //宽高
                title: '修改评论',
                content: html
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
