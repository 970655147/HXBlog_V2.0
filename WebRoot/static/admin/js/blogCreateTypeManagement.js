/**
 * moodsManage.js
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/24/2017 9:56 PM
 */

layui.define(['element', 'laypage', 'layer', 'form', 'upload'], function (exports) {
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
            $.ajax({
                url: "/admin/blog/createType/list",
                type: "GET",
                data: {},
                success: function (resp) {
                    if (resp.success) {
                        var html = '';
                        var createTypes = resp.data
                        for (var idx in createTypes) {
                            var item = createTypes[idx];
                            html += '<tr>';
                            html += '<td>' + item.id + '</td>';
                            html += '<td>' + item.name + '</td>';
                            html += '<td>' + item.desc + '</td>';
                            html += '<td><img src="' + item.imgUrl + '" width="60px" height="60px" /> </td>';
                            html += '<td>' + item.sort + '</td>';
                            html += '<td>' + item.createdAt + '</td>';
                            html += '<td><button class="layui-btn layui-btn-small layui-btn-normal" onclick=\'layui.funcs.editData(' + JSON.stringify(item) + ')\'><i class="layui-icon">&#xe642;</i></button></td>';
                            html += '<td><button class="layui-btn layui-btn-small layui-btn-danger" onclick="layui.funcs.deleteData(' + item.id + ')"><i class="layui-icon">&#xe640;</i></button></td>';
                            html += '</tr>';
                        }
                        $('#dataContent').html(html);
                        $('#dataConsole,#dataList').attr('style', 'display:block'); //显示FiledBox
                    } else {
                        layer.alert("拉取创建类型列表失败[" + resp.data + "] !", {icon: 5});
                    }
                }
            });
        }, 500);
    }

    form.on('submit(addCreateTypeSubmit)', function (data) {
        var params = $("#addCreateTypeForm").serialize()
        $.ajax({
            url: "/admin/blog/createType/add",
            type: "POST",
            data: params,
            success: function (resp) {
                if (resp.success) {
                    layer.alert('添加创建类型成功!', {
                        closeBtn: 0,
                        icon: 1
                    }, function () {
                        refresh()
                    });
                } else {
                    layer.alert("添加创建类型失败[" + resp.data + "] !", {icon: 5});
                }
            }
        });
        return false
    })

    form.on('submit(updateCreateTypeSubmit)', function (data) {
        var params = $("#updateCreateTypeForm").serialize()
        $.ajax({
            url: "/admin/blog/createType/update",
            type: "POST",
            data: params,
            success: function (resp) {
                if (resp.success) {
                    var addTopId = layer.alert('更新创建类型成功 !', {
                        closeBtn: 0,
                        icon: 1
                    }, function () {
                        refresh()
                    });
                } else {
                    layer.alert("更新创建类型失败[" + resp.data + "] !", {icon: 5});
                }
            }
        });
        return false
    });

    //输出接口，主要是两个函数，一个删除一个编辑
    var funcs = {
        addData: function () {
            var html = '';
            html += '<form id="addCreateTypeForm" class="layui-form layui-form-pane" action="/admin/blog/createType/add" method="post">';
            html += '<label class="layui-form-label" style="border: none" >名称:</label>';
            html += '<input  style="width:87%;margin: auto;color: #000!important;" name="name" lay-verify="required"  class="layui-input" >';
            html += '<label class="layui-form-label" style="border: none" >描述:</label>';
            html += '<input  style="width:87%;margin: auto;color: #000!important;" name="desc" lay-verify="required"  class="layui-input" >';
            html += '<div>';
            html += '<label class="layui-form-label" style="border: none" >上传图片:</label>';
            html += '<input  style="width:87%;margin: auto;color: #000!important;" lay-verify="required" name="imgUrl"  class="layui-input" onblur="layui.funcs.headImgShow()" >';
            html += '<input id="uploadImgInput" type="file" name="file" onchange="layui.funcs.fileUpload()" style="margin-left: 50px" />';
            html += '<img id="coverShow" width="60px" height="60px" />'
            html += '</div>';
            html += '<label class="layui-form-label" style="border: none" >排序:</label>';
            html += '<input  style="width:87%;margin: auto;color: #000!important;" lay-verify="required" name="sort"  class="layui-input" >';
            html += '<div class="layui-form-item">';
            html += '<div class="layui-input-inline" style="margin:10px auto 0 auto;display: block;float: none;">';
            html += '<button class="layui-btn" id="submit"  lay-submit="" lay-filter="addCreateTypeSubmit" >添加</button>';
            html += '<button type="reset" class="layui-btn layui-btn-primary">重置</button>';
            html += '</div>';
            html += '</div>';
            html += '</form>';

            layer.open({
                type: 1,
                skin: 'layui-layer-rim', //加上边框
                area: '620px', //宽高
                title: '添加心情',
                content: html
            });
            form.render('radio');  //radio，编辑和添加的时候
        },
        editData: function (item) {
            var html = '';
            html += '<form id="updateCreateTypeForm" class="layui-form layui-form-pane" action="/admin/blog/createType/update" method="post" >';
            html += '<input type="hidden" id="id" name="id" value="' + item.id + '">';
            html += '<label class="layui-form-label" style="border: none" >名称:</label>';
            html += '<input  style="width:87%;margin: auto;color: #000!important;" name="name" lay-verify="required"  class="layui-input" value="' + item.name + '" >';
            html += '<label class="layui-form-label" style="border: none" >描述:</label>';
            html += '<input  style="width:87%;margin: auto;color: #000!important;" name="desc" lay-verify="required"  class="layui-input" value="' + item.desc + '" >';
            html += '<div>';
            html += '<label class="layui-form-label" style="border: none" >上传图片:</label>';
            html += '<input  style="width:87%;margin: auto;color: #000!important;" lay-verify="required" name="imgUrl"  class="layui-input" value="' + item.imgUrl + '" onblur="layui.funcs.headImgShow()" >';
            html += '<input id="uploadImgInput" type="file" name="file" onchange="layui.funcs.fileUpload()" style="margin-left: 50px" />';
            html += '<img id="coverShow" width="60px" height="60px" />'
            html += '</div>';
            html += '<label class="layui-form-label" style="border: none" >排序:</label>';
            html += '<input  style="width:87%;margin: auto;color: #000!important;" lay-verify="required" name="sort" value="' + item.sort + '" class="layui-input" >';
            html += '<div class="layui-form-item">';
            html += '<div class="layui-input-inline" style="margin:10px auto 0 auto;display: block;float: none;">';
            html += '<button class="layui-btn" id="submit"  lay-submit="" lay-filter="updateCreateTypeSubmit" >立即修改</button>';
            html += '<button type="reset" class="layui-btn layui-btn-primary">重置</button>';
            html += '</div>';
            html += '</div>';
            html += '</form>';

            layer.open({
                type: 1,
                skin: 'layui-layer-rim', //加上边框
                area: '620px', //宽高
                title: '修改创建类型',
                content: html
            });
            form.render('radio');  //radio，编辑和添加的时候
            layui.funcs.headImgShow()
        },
        deleteData: function (id) {
            layer.confirm('确定删除这个创建类型吗？', {
                btn: ['确定', '取消'] //按钮
            }, function () {
                $.ajax({
                    url: '/admin/blog/createType/remove',
                    data: {"id": id},
                    type: 'POST',
                    success: function (resp) {
                        if (resp.success) {
                            layer.alert('删除创建类型成功 !', {
                                closeBtn: 0,
                                icon: 1
                            }, function () {
                                refresh()
                            });
                        } else {
                            layer.alert("删除创建类型失败[" + resp.data + "] !", {icon: 5});
                        }
                    }
                });
            }, function () {

            });
        },
        fileUpload : function() {
            var formData = new FormData()
            formData.append("file", $("#uploadImgInput").get(0).files[0]);
            $.ajax({
                url: '/admin/upload/image', //上传接口
                data : formData,
                type : "POST",
                processData : false,
                contentType : false,
                success: function (resp) { //上传成功后的回调
                    if (resp.success) {
                        var visitUrl = resp.data.url
                        $("[name='url']").attr("value", visitUrl)
                        $("#coverShow").attr("src", visitUrl)
                    } else {
                        alert("上传文件失败");
                    }
                }
            });
        },
        headImgShow : function() {
            $("#coverShow").attr("src", $("[name='imgUrl']").val());
        },
        reSort : function() {
            $.ajax({
                url: "/admin/blog/createType/reSort",
                type: "POST",
                data: { },
                success: function (resp) {
                    if (resp.success) {
                        layer.alert('刷新排序成功 !');
                        refresh()
                    } else {
                        layer.alert('刷新排序失败[' + resp.data + '], 请联系管理人员!');
                    }
                }
            });
        }
    };
    exports('funcs', funcs);
});
