/**
 * blogTagManage.js
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/24/2017 9:40 PM
 */

var blogTagNum = 3;

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
            ajax({
                url: reqMap.tag.list,
                type: "GET",
                data: {},
                success: function (resp) {
                    if (resp.success) {
                        var html = '';
                        var tags = resp.data
                        for (var i in tags) {
                            var item = tags[i];
                            html += '<tr>';
                            html += '<td>' + item.id + '</td>';
                            html += '<td>' + item.name + '</td>';
                            html += '<td>' + item.sort + '</td>';
                            html += '<td><button class="layui-btn layui-btn-small layui-btn-normal" onclick="layui.funcs.editData(' + item.id + ',\'' + item.name + '\', ' + item.sort + ')"><i class="layui-icon">&#xe642;</i></button></td>';
                            html += '<td><button class="layui-btn layui-btn-small layui-btn-danger" onclick="layui.funcs.deleteData(' + item.id + ')"><i class="layui-icon">&#xe640;</i></button></td>';
                            html += '</tr>';
                        }
                        $('#dataContent').html(html);
                        element.init();
                    } else {
                        layer.alert("拉取标签列表失败[" + resp.data + "] !", {icon: 5});
                    }
                }
            });

            $('#dataConsole,#dataList').attr('style', 'display:block'); //显示FiledBox
        }, 500);
    }

    form.on('submit(addTagSubmit)', function (data) {
        ajax({
            url: reqMap.tag.add,
            type: "POST",
            data: $("#addTagForm").serialize(),
            success: function (resp) {
                if (resp.success) {
                    layer.alert('添加标签成功!', {
                        closeBtn: 0,
                        icon: 1
                    }, function () {
                        refresh()
                    });
                } else {
                    layer.alert("添加标签失败[" + resp.data + "] !", {icon: 5});
                }
            }
        });
        return false
    })

    form.on('submit(updateTagSubmit)', function (data) {
        ajax({
            url: reqMap.tag.update,
            type: "POST",
            data: $("#updateTagForm").serialize(),
            success: function (resp) {
                if (resp.success) {
                    layer.alert('更新标签成功!', {
                        closeBtn: 0,
                        icon: 1
                    }, function () {
                        refresh()
                    });
                } else {
                    layer.alert("更新标签失败[" + resp.data + "] !", {icon: 5});
                }
            }
        });
        return false
    })

    //输出接口，主要是两个函数，一个删除一个编辑
    var funcs = {
        addData: function () {
            var html = '';
            html += '<form id="addTagForm" class="layui-form layui-form-pane" action="/admin/tag/add" method="post">';
            html += '<label class="layui-form-label" style="border: none" name="content"  >名称:</label>';
            html += '<input  style="width:87%;margin: auto;color: #000!important;" name="name"  class="layui-input" lay-verify="required" >';
            html += '<label class="layui-form-label" style="border: none" name="content"  >排序:</label>';
            html += '<input  style="width:87%;margin: auto;color: #000!important;" name="sort"  class="layui-input" lay-verify="required" >';
            html += '<div class="layui-form-item">';
            html += '<div class="layui-input-inline" style="margin:10px auto 0 auto;display: block;float: none;">';
            html += '<button class="layui-btn" id="submit"  lay-submit="" lay-filter="addTagSubmit">添加</button>';
            html += '<button type="reset" class="layui-btn layui-btn-primary">重置</button>';
            html += '</div>';
            html += '</div>';
            html += '</form>';

            layer.open({
                type: 1,
                skin: 'layui-layer-rim', //加上边框
                area: '420px', //宽高
                title: '添加标签',
                content: html
            });
        },
        editData: function (id, tagName, sort) {
            var html = '';
            html += '<form id="updateTagForm" class="layui-form layui-form-pane" action="/admin/tag/update" method="post">';
            html += '<input type="hidden" name="id" value="' + id + '"/>';
            html += '<label class="layui-form-label" style="border: none" >名称:</label>';
            html += '<textarea  style="width:87%;margin: auto;color: #000!important;" name="name" class="layui-textarea" lay-verify="required" >' + tagName + '</textarea>';
            html += '<label class="layui-form-label" style="border: none" name="content"  >排序:</label>';
            html += '<input  style="width:87%;margin: auto;color: #000!important;" name="sort" value="' + sort + '"  class="layui-input" lay-verify="required" >';
            html += '<div class="layui-form-item">';
            html += '<div class="layui-input-inline" style="margin:10px auto 0 auto;display: block;float: none;">';
            html += '<button class="layui-btn" id="submit"  lay-submit="" lay-filter="updateTagSubmit">立即提交</button>';
            html += '<button type="reset" class="layui-btn layui-btn-primary">重置</button>';
            html += '</div>';
            html += '</div>';
            html += '</form>';

            layer.open({
                type: 1,
                skin: 'layui-layer-rim', //加上边框
                area: '420px', //宽高
                title: '修改标签',
                content: html
            });
        },
        deleteData: function (id) {
            layer.confirm('您确定要删除吗？', {
                btn: ['确定', '取消'] //按钮
            }, function () {
                ajax({
                    url: reqMap.tag.remove,
                    data: {"id": id},
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
                            layer.alert("删除标签失败[" + resp.data + "] !", {icon: 5});
                        }
                    }
                });
            }, function () {

            });
        },
        reSort: function () {
            ajax({
                url: reqMap.tag.reSort,
                type: "POST",
                data: {},
                success: function (resp) {
                    if (resp.success) {
                        layer.alert('刷新排序成功 !');
                    } else {
                        layer.alert('刷新排序失败[' + resp.data + '], 请联系管理人员!');
                    }
                }
            });
        }
    };
    exports('funcs', funcs);
});
