/**
 * linksManagement.js
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/24/2017 9:49 PM
 */

var linkNum = 3;

layui.define(['element', 'laypage', 'layer', 'form'], function (exports) {
    var $ = layui.jquery;
    var layer = layui.layer;
    var form = layui.form();
    var laypage = layui.laypage;
    var element = layui.element();
    var laypageId = 'pageNav';

    initilData();
    //页数据初始化
    //currentIndex：当前页面
    //pageSize：页容量（每页显示的条数）
    function initilData() {
        var index = layer.load(1);
        //模拟数据加载
        setTimeout(function () {
            layer.close(index);
            ajax({
                url: reqMap.link.list,
                type: "GET",
                data: { },
                success: function (resp) {
                    if (resp.success) {
                        var links = resp.data
                        var html = '';
                        for (var i in links) {
                            var item = links[i];
                            html += '<tr>';
                            html += '<td>' + item.id + '</td>';
                            html += '<td>' + item.name + '</td>';
                            html += '<td>' + item.desc + '</td>';
                            html += '<td>' + item.url + '</td>';
                            html += '<td>' + item.sort + '</td>';
                            if (item.enable) {
                                html += '<td><i class="layui-icon" style="font-size: 30px; color: #009688;vertical-align: middle;">&#xe609;</i> </td>';
                            } else {
                                html += '<td><i class="layui-icon" style="font-size: 30px; color:#d2d2d2; vertical-align: middle;">&#xe60f;</i> </td>';
                            }
                            html += '<td>' + item.createdAt + '</td>';
                            html += '<td>' + item.updatedAt + '</td>';
                            html += '<td><button class="layui-btn layui-btn-small layui-btn-normal" onclick="layui.funcs.editData(' + item.id + ',\'' + item.name + '\',\'' + item.desc + '\',\'' + item.url + '\',\'' + item.sort + '\',' + item.enable + ')"><i class="layui-icon">&#xe642;</i></button></td>';
                            html += '<td><button class="layui-btn layui-btn-small layui-btn-danger" onclick="layui.funcs.deleteData(' + item.id + ')"><i class="layui-icon">&#xe640;</i></button></td>';
                            html += '</tr>';
                        }
                        $('#dataContent').html(html);
                        element.init();
                    } else {
                        layer.alert("拉取友情链接列表失败[" + resp.data + "] !", {icon: 5});
                    }
                }
            });
        }, 500);
    }

    form.on('submit(addLinkSubmit)', function(data){
        ajax({
            url : reqMap.link.add,
            type : "POST",
            data : $("#addLinkForm").serialize(),
            success : function (resp) {
                if(resp.success) {
                    layer.alert('添加友情链接成功!', {
                        closeBtn: 0,
                        icon: 1
                    }, function () {
                        refresh()
                    });
                } else {
                    layer.alert("添加友情链接失败[" + resp.data + "] !", {icon: 5});
                }
            }
        });
        return false
    })

    form.on('submit(updateLinkSubmit)', function(data){
        ajax({
            url : reqMap.link.update,
            type : "POST",
            data : $("#updateLinkForm").serialize(),
            success : function (resp) {
                if(resp.success) {
                    var addTopId = layer.alert('修改友情链接成功 !', {
                        closeBtn: 0,
                        icon: 1
                    }, function () {
                        refresh()
                    });
                } else {
                    layer.alert("更新友情链接失败[" + resp.data + "] !", {icon: 5});
                }
            }
        });
        return false
    });

    //输出接口，主要是两个函数，一个删除一个编辑
    var funcs = {
        addData: function () {
            var html = '';
            html += '<form id="addLinkForm" class="layui-form layui-form-pane" action="/admin/link/add" method="post">';
            html += '<label class="layui-form-label" style="border: none;width: 180px;" >友情链接名称:</label>';
            html += '<input  style="width:87%;margin: auto;color: #000!important;" name="name" lay-verify="required"  class="layui-input" >';
            html += '<label class="layui-form-label" style="border: none;width: 180px;" >友情链接描述:</label>';
            html += '<input  style="width:87%;margin: auto;color: #000!important;" name="desc" lay-verify="required"  class="layui-input" >';
            html += '<label class="layui-form-label" style="border: none;width: 180px;" >友情链接地址:</label>';
            html += '<input  style="width:87%;margin: auto;color: #000!important;" name="url" lay-verify="url"  class="layui-input" >';
            html += '<label class="layui-form-label" style="border: none;" >顺序:</label>';
            html += '<input  style="width:87%;margin: auto;color: #000!important;" name="sort" lay-verify="number" class="layui-input" >';
            html += '<label class="layui-form-label" style="border: none" >是否可用:</label>';
            html += '<input type="radio" name="enable" value="1" title="是" checked />';
            html += '<input type="radio" name="enable" value="0" title="否" />';
            html += '<div class="layui-form-item">';
            html += '<div class="layui-input-inline" style="margin:10px auto 0 auto;display: block;float: none;">';
            html += '<button class="layui-btn" id="submit"  lay-submit="" lay-filter="addLinkSubmit">添加</button>';
            html += '<button type="reset" class="layui-btn layui-btn-primary">重置</button>';
            html += '</div>';
            html += '</div>';
            html += '</form>';

            layer.open({
                type: 1,
                skin: 'layui-layer-rim', //加上边框
                area: '620px', //宽高
                title: '添加友情链接',
                content: html
            });
            form.render()
        },
        editData: function (id, name, desc, url, sort, enable) {
            var html = '';
            html += '<form id="updateLinkForm" class="layui-form layui-form-pane" action="/admin/link/update" method="post">';
            html += '<input type="hidden" name="id" value="' + id + '"/>';
            html += '<label class="layui-form-label" style="border: none;width: 180px;"  >友情链接名称:</label>';
            html += '<input  style="width:87%;margin: auto;color: #000!important;" name="name" lay-verify="required" class="layui-input" value="' + name + '">';
            html += '<label class="layui-form-label" style="border: none;width: 180px;"  >友情链接描述:</label>';
            html += '<input  style="width:87%;margin: auto;color: #000!important;" name="desc" lay-verify="required" class="layui-input" value="' + desc + '">';
            html += '<label class="layui-form-label" style="border: none;width: 180px;" >友情链接地址:</label>';
            html += '<input  style="width:87%;margin: auto;color: #000!important;" name="url"  class="layui-input" lay-verify="url" value="' + url + '">';
            html += '<label class="layui-form-label" style="border: none;" >顺序:</label>';
            html += '<input  style="width:87%;margin: auto;color: #000!important;" name="sort"  class="layui-input" lay-verify="number" value="' + sort + '">';
            html += '<label class="layui-form-label" style="border: none" >是否可用:</label>';
            if(enable) {
                html += '<input type="radio" name="enable" value="1" title="是" checked />';
                html += '<input type="radio" name="enable" value="0" title="否" />';
            } else {
                html += '<input type="radio" name="enable" value="1" title="是" />';
                html += '<input type="radio" name="enable" value="0" title="否" checked />';
            }
            html += '<div class="layui-form-item">';
            html += '<div class="layui-input-inline" style="margin:10px auto 0 auto;display: block;float: none;">';
            html += '<button class="layui-btn" id="submit"  lay-submit="" lay-filter="updateLinkSubmit">立即修改</button>';
            html += '<button type="reset" class="layui-btn layui-btn-primary">重置</button>';
            html += '</div>';
            html += '</div>';
            html += '</form>';

            layer.open({
                type: 1,
                skin: 'layui-layer-rim', //加上边框
                area: '620px', //宽高
                title: '修改友情链接',
                content: html
            });
            form.render()
        },
        deleteData: function (id) {
            layer.confirm('您确定要删除吗？', {
                btn: ['确定', '取消'] //按钮
            }, function () {
                ajax({
                    url: reqMap.link.remove,
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
                            layer.alert("删除友情链接失败[" + resp.data + "] !", {icon: 5});
                        }
                    }
                });
            }, function () {

            });
        },
        reSort: function () {
            ajax({
                url: reqMap.link.reSort,
                type: "POST",
                data: {},
                success: function (resp) {
                    if (resp.success) {
                        layer.alert('刷新排序成功 !', function () {
                            refresh()
                        });
                    } else {
                        layer.alert('刷新排序失败[' + resp.data + '], 请联系管理人员!');
                    }
                }
            });
        }
    }
    exports('funcs', funcs);
});
