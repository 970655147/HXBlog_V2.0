/**
 * interfManagement.js
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/24/2017 9:56 PM
 */

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
                url: reqMap.interf.list,
                type: "GET",
                data: {},
                success: function (resp) {
                    if (resp.success) {
                        var html = new StringBuilder();
                        var roles = resp.data
                        for (var i in roles) {
                            var item = roles[i];
                            html.append('<tr>')
                            html.append('<td>' + item.id + '</td>')
                            html.append('<td>' + item.name + '</td>')
                            html.append('<td>' + item.desc + '</td>')
                            html.append('<td>' + item.sort + '</td>')
                            html.append('<td>' + item.createdAt + '</td>')
                            if (item.enable) {
                                html.append('<td><i class="layui-icon" style="font-size: 30px; color: #009688;vertical-align: middle;">&#xe609;</i> </td>')
                            } else {
                                html.append('<td><i class="layui-icon" style="font-size: 30px; color:#d2d2d2; vertical-align: middle;">&#xe60f;</i> </td>')
                            }
                            html.append('<td><button class="layui-btn layui-btn-small layui-btn-normal" onclick=\'layui.funcs.editData(' + JSON.stringify(item) + ')\'><i class="layui-icon">&#xe642;</i></button></td>')
                            html.append('<td><button class="layui-btn layui-btn-small layui-btn-danger" onclick="layui.funcs.deleteData(' + item.id + ')"><i class="layui-icon">&#xe640;</i></button></td>')
                            html.append('</tr>')
                        }
                        $('#dataContent').html(html.toString());
                    } else {
                        layer.alert("拉取接口列表失败[" + resp.data + "] !", {icon: 5});
                    }
                }
            });
        }, 500);
    }

    form.on('submit(addMoodSubmit)', function (data) {
        ajax({
            url: reqMap.interf.add,
            type: "POST",
            data: $("#addInterfForm").serialize(),
            success: function (resp) {
                if (resp.success) {
                    layer.alert('添加接口成功!', {
                        closeBtn: 0,
                        icon: 1
                    }, function () {
                        refresh()
                    });
                } else {
                    layer.alert("添加接口失败[" + resp.data + "] !", {icon: 5});
                }
            }
        });

        return false
    })

    form.on('submit(updateMoodSubmit)', function (data) {
        ajax({
            url: reqMap.interf.update,
            type: "POST",
            data: $("#updateInterfForm").serialize(),
            success: function (resp) {
                if (resp.success) {
                    var addTopId = layer.alert('修改接口成功 !', {
                        closeBtn: 0,
                        icon: 1
                    }, function () {
                        refresh()
                    });
                } else {
                    layer.alert("更新接口失败[" + resp.data + "] !", {icon: 5});
                }
            }
        });

        return false
    });

    //输出接口，主要是两个函数，一个删除一个编辑
    var funcs = {
        addData: function () {
            var html = new StringBuilder();
            html.append('<form id="addInterfForm" class="layui-form layui-form-pane" action="' + reqMap.interf.add + '" method="post">')
            html.append('<label class="layui-form-label" style="border: none" >名称:</label>')
            html.append('<input  style="width:87%;margin: auto;color: #000!important;" lay-verify="required" name="name"  class="layui-input" >')
            html.append('<label class="layui-form-label" style="border: none" >描述:</label>')
            html.append('<textarea  style="width:87%;margin: auto;color: #000!important;" lay-verify="required" name="desc" class="layui-textarea " ></textarea>')
            html.append('<label class="layui-form-label" style="border: none" >排序:</label>')
            html.append('<input  style="width:87%;margin: auto;color: #000!important;" lay-verify="required" name="sort"  class="layui-input" >')
            html.append('<label class="layui-form-label" style="border: none" >是否显示:</label>')
            html.append('<input type="radio" name="enable" value="1" title="是" checked />')
            html.append('<input type="radio" name="enable" value="0" title="否" />')
            html.append('<div class="layui-form-item">')
            html.append('<div class="layui-input-inline" style="margin:10px auto 0 auto;display: block;float: none;">')
            html.append('<button class="layui-btn" id="submit"  lay-submit="" lay-filter="addMoodSubmit" >添加</button>')
            html.append('<button type="reset" class="layui-btn layui-btn-primary">重置</button>')
            html.append('</div>')
            html.append('</div>')
            html.append('</form>')

            layer.open({
                type: 1,
                skin: 'layui-layer-rim', //加上边框
                area: '620px', //宽高
                title: '添加接口',
                content: html.toString()
            });
            form.render('radio');  //radio，编辑和添加的时候
        },
        editData: function (item) {
            var html = new StringBuilder();
            html.append('<form id="updateInterfForm" class="layui-form layui-form-pane" action="' + reqMap.interf.update + '" method="post" >')
            html.append('<label class="layui-form-label" style="border: none" >名称:</label>')
            html.append('<input type="hidden" id="id" name="id" value="' + item.id + '">')
            html.append('<input  style="width:87%;margin: auto;color: #000!important;" name="name" lay-verify="required"  class="layui-input" value="' + item.name + '" >')
            html.append('<label class="layui-form-label" style="border: none" >描述:</label>')
            html.append('<textarea  style="width:87%;margin: auto;color: #000!important;" name="desc" class="layui-textarea " lay-verify="required" >' + item.desc + '</textarea>')
            html.append('<label class="layui-form-label" style="border: none" >排序:</label>')
            html.append('<input  style="width:87%;margin: auto;color: #000!important;" lay-verify="required" name="sort" value="' + item.sort + '" class="layui-input" >')
            html.append('<label class="layui-form-label" style="border: none" >是否显示:</label>')
            if (item.enable) {
                html.append('<input type="radio" name="enable" value="1" title="是" checked />')
                html.append('<input type="radio" name="enable" value="0" title="否" />')
            } else {
                html.append('<input type="radio" name="enable" value="1" title="是" />')
                html.append('<input type="radio" name="enable" value="0" title="否" checked />')
            }
            html.append('<div class="layui-form-item">')
            html.append('<div class="layui-input-inline" style="margin:10px auto 0 auto;display: block;float: none;">')
            html.append('<button class="layui-btn" id="submit"  lay-submit="" lay-filter="updateMoodSubmit" >立即修改</button>')
            html.append('<button type="reset" class="layui-btn layui-btn-primary">重置</button>')
            html.append('</div>')
            html.append('</div>')
            html.append('</form>')

            layer.open({
                type: 1,
                skin: 'layui-layer-rim', //加上边框
                area: '620px', //宽高
                title: '修改接口',
                content: html.toString()
            });
            form.render('radio');  //radio，编辑和添加的时候
        },
        deleteData: function (id) {
            layer.confirm('确定删除这个接口吗？', {
                btn: ['确定', '取消'] //按钮
            }, function () {
                ajax({
                    url: reqMap.interf.remove,
                    data: {"id": id},
                    type: 'POST',
                    success: function (resp) {
                        if (resp.success) {
                            layer.alert('删除接口成功 !', {
                                closeBtn: 0,
                                icon: 1
                            }, function () {
                                refresh()
                            });
                        } else {
                            layer.alert("删除接口失败[" + resp.data + "] !", {icon: 5});
                        }
                    }
                });
            }, function () {

            });
        },
        reSort : function() {
            ajax({
                url: reqMap.interf.reSort,
                type: "POST",
                data: { },
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
