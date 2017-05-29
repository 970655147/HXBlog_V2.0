/**
 * blogTypeManagement.js
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/24/2017 9:46 PM
 */

layui.define(['layer', 'form'], function (exports) {
    var $ = layui.jquery;
    var layer = layui.layer;
    var form = layui.form();

    initilData();

    function initilData() {
        var index = layer.load(1);
        //模拟数据加载
        setTimeout(function () {
            layer.close(index);
            $.ajax({
                url: "/type/list",
                type: "GET",
                data: {},
                success: function (resp) {
                    var html = '';
                    if(resp.success) {
                        for (var i in resp.data) {
                            var item = resp.data[i];
                            html += '<tr>';
                            html += '<td>' + item.id + '</td>';
                            html += '<td>' + item.name + '</td>';
                            html += '<td><button class="layui-btn layui-btn-small layui-btn-normal" onclick="layui.funcs.editData(' + item.id + ',\'' + item.name + '\')"><i class="layui-icon">&#xe642;</i></button></td>';
                            html += '<td><button class="layui-btn layui-btn-small layui-btn-danger" onclick="layui.funcs.deleteData(' + item.id + ')"><i class="layui-icon">&#xe640;</i></button></td>';
                            html += '</tr>';
                        }
                    } else {
                        layer.alert("拉取类型列表失败[" + resp.msg + "] !", {icon: 5});
                    }
                    $('#dataContent').html(html);
                }
            });

            $('#dataConsole,#dataList').attr('style', 'display:block'); //显示FiledBox
        }, 500);
    }

    form.on('submit(addTypeSubmit)', function(data){
        $.ajax({
            url : "/admin/type/add",
            type : "POST",
            data :  $(".layui-form").serialize(),
            success : function (resp) {
                if (resp.success) {
                    layer.alert('添加类型成功!', {
                        closeBtn: 0,
                        icon: 1
                    }, function () {
                        location.reload()
                    });
                } else {
                    layer.alert("添加类型失败[" + resp.msg + "] !", {icon: 5});
                }
            }
        });

        return false;
    })

    form.on('submit(updateTypeSubmit)', function(data){
        $.ajax({
            url : "/admin/type/update",
            type : "POST",
            data : $(".layui-form").serialize(),
            success : function (resp) {
                if (resp.success) {
                    layer.alert('更新类型成功!', {
                        closeBtn: 0,
                        icon: 1
                    }, function () {
                        location.reload()
                    });
                } else {
                    layer.alert("更新类型失败[" + resp.msg + "] !", {icon: 5});
                }
            }
        });

        return false;
    })

    //输出接口，主要是两个函数，一个删除一个编辑
    var funcs = {
        addData: function () {
            var html = '';
            html += '<form class="layui-form layui-form-pane" >';
            html += '<label class="layui-form-label" style="border: none" name="content" >类别名称:</label>';
            html += '<input id="add_name"  style="width:87%;margin: auto;color: #000!important;" name="name" lay-verify="required" class="layui-input" >';
            html += '<div class="layui-form-item">';
            html += '<div class="layui-input-inline" style="margin:10px auto 0 auto;display: block;float: none;">';
            html += '<button class="layui-btn" id="submit"  lay-submit="" lay-filter="addTypeSubmit">添加</button>';
            html += '<button type="reset" class="layui-btn layui-btn-primary">重置</button>';
            html += '</div>';
            html += '</div>';
            html += '</form>';

            layer.open({
                type: 1,
                skin: 'layui-layer-rim', //加上边框
                area: '420px', //宽高
                title: '添加类别',
                content: html
            });
        },
        editData: function (id, typeName) {
            var html = '';
            html += '<form class="layui-form layui-form-pane" >';
            html += '<label class="layui-form-label" style="border: none" >类别名称:</label>';
            html += '<textarea  id="edit_name" style="width:87%;margin: auto;color: #000!important;" name="name" class="layui-textarea" lay-verify="required" >' + typeName + '</textarea>';
            html += '<input id="edit_id" type="hidden" name="id" value="' + id + '"/>';
            html += '<div class="layui-form-item">';
            html += '<div class="layui-input-inline" style="margin:10px auto 0 auto;display: block;float: none;">';
            html += '<button class="layui-btn" id="editSubmit" lay-submit="" lay-filter="updateTypeSubmit">立即提交</button>';
            html += '<button type="reset" class="layui-btn layui-btn-primary">重置</button>';
            html += '</div>';
            html += '</div>';
            html += '</form>';

            layer.open({
                type: 1,
                skin: 'layui-layer-rim', //加上边框
                area: '420px', //宽高
                title: '修改类别',
                content: html
            });
        },
        deleteData: function (id) {
            layer.confirm('您确定要删除吗？', {
                btn: ['确定', '取消'] //按钮
            }, function () {
                $.ajax({
                    url: '/admin/type/remove',
                    type: 'POST',
                    data: {
                        "id" : id
                    },
                    success: function (resp) {
                        if (resp.success) {
                            layer.alert('删除成功!', {
                                closeBtn: 0,
                                icon: 1
                            }, function () {
                                location.reload()
                            });
                        } else {
                            layer.alert("删除类型失败[" + resp.msg + "] !", {icon: 5});
                        }
                    }
                });
            }, function () {

            });
        }

    };
    exports('funcs', funcs);
});
