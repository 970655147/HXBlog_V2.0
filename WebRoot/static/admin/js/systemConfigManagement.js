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

    /**
     * 提取type
     * @type {Object}
     */
    var params = getParamsFromUrl(location.href)
    var configType = isEmpty(params.type) ? "dummy" : params.type

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
                url: "/admin/config/list",
                type: "GET",
                data: {
                    "type": configType,
                    "pageNow": pageNow,
                    "pageSize": pageSize
                },
                success: function (resp) {
                    if (resp.success) {
                        var html = '';
                        var images = resp.data.list
                        for (var i in images) {
                            var item = images[i];
                            html += '<tr>';
                            html += '<td>' + item.id + '</td>';
                            html += '<td>' + item.name + '</td>';
                            html += '<td>' + item.value + '</td>';
                            html += '<td>' + item.desc + '</td>';
                            html += '<td>' + item.sort + '</td>';
                            html += '<td>' + item.createdAt + '</td>';
                            if (item.enable) {
                                html += '<td><i class="layui-icon" style="font-size: 30px; color: #009688;vertical-align: middle;">&#xe609;</i> </td>';
                            } else {
                                html += '<td><i class="layui-icon" style="font-size: 30px; color:#d2d2d2; vertical-align: middle;">&#xe60f;</i> </td>';
                            }
                            html += '<td><button class="layui-btn layui-btn-small layui-btn-normal" onclick=\'layui.funcs.editData(' + JSON.stringify(item) + ')\'><i class="layui-icon">&#xe642;</i></button></td>';
                            html += '<td><button class="layui-btn layui-btn-small layui-btn-danger" onclick="layui.funcs.deleteData(' + item.id + ')"><i class="layui-icon">&#xe640;</i></button></td>';
                            html += '</tr>';
                        }
                        $('#dataContent').html(html);
                        $('#dataConsole,#dataList').attr('style', 'display:block'); //显示FiledBox

                        laypage({
                            cont: laypageId,
                            pages: resp.data.totalPage,
                            groups: 5,
                            skip: true,
                            curr: pageNow,
                            jump: function (obj, first) {
                                var pageNow = obj.curr;
                                if (!first) {
                                    initilData(pageNow);
                                }
                            }
                        });
                    } else {
                        layer.alert("拉取系统配置墙列表失败[" + resp.msg + "] !", {icon: 5});
                    }
                }
            });
        }, 500);
    }

    form.on('submit(addConfigSubmit)', function (data) {
        var params = $("#addConfigForm").serialize()
        params += ("&type=" + configType)
        $.ajax({
            url: "/admin/config/add",
            type: "POST",
            data: params,
            success: function (resp) {
                if (resp.success) {
                    layer.alert('添加系统配置成功!', {
                        closeBtn: 0,
                        icon: 1
                    }, function () {
                        refresh()
                    });
                } else {
                    layer.alert("添加系统配置失败[" + resp.msg + "] !", {icon: 5});
                }
            }
        });
        return false
    })

    form.on('submit(updateConfigSubmit)', function (data) {
        var params = $("#updateConfigForm").serialize()
        params += ("&type=" + configType)
        $.ajax({
            url: "/admin/config/update",
            type: "POST",
            data: params,
            success: function (resp) {
                if (resp.success) {
                    var addTopId = layer.alert('更新系统配置成功 !', {
                        closeBtn: 0,
                        icon: 1
                    }, function () {
                        refresh()
                    });
                } else {
                    layer.alert("更新系统配置失败[" + resp.msg + "] !", {icon: 5});
                }
            }
        });
        return false
    });

    //输出接口，主要是两个函数，一个删除一个编辑
    var funcs = {
        addData: function () {
            var html = '';
            html += '<form id="addConfigForm" class="layui-form layui-form-pane" action="/admin/config/add" method="post">';
            html += '<label class="layui-form-label" style="border: none" >name:</label>';
            html += '<input  style="width:87%;margin: auto;color: #000!important;" lay-verify="required" name="name"  class="layui-input" >';
            html += '<label class="layui-form-label" style="border: none" >value:</label>';
            html += '<input  style="width:87%;margin: auto;color: #000!important;" lay-verify="required" name="value"  class="layui-input" >';
            html += '<label class="layui-form-label" style="border: none" >描述信息:</label>';
            html += '<input  style="width:87%;margin: auto;color: #000!important;" lay-verify="required" name="desc"  class="layui-input" >';
            html += '<label class="layui-form-label" style="border: none" >排序:</label>';
            html += '<input  style="width:87%;margin: auto;color: #000!important;" lay-verify="required" name="sort"  class="layui-input" >';
            html += '<label class="layui-form-label" style="border: none" >是否显示:</label>';
            html += '<input type="radio" name="enable" value="1" title="是" checked />';
            html += '<input type="radio" name="enable" value="0" title="否" />';
            html += '<div class="layui-form-item">';
            html += '<div class="layui-input-inline" style="margin:10px auto 0 auto;display: block;float: none;">';
            html += '<button class="layui-btn" id="submit"  lay-submit="" lay-filter="addConfigSubmit" >添加</button>';
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
            html += '<form id="updateConfigForm" class="layui-form layui-form-pane" action="/admin/config/update" method="post" >';
            html += '<input type="hidden" id="id" name="id" value="' + item.id + '">';
            html += '<label class="layui-form-label" style="border: none" >name:</label>';
            html += '<input  style="width:87%;margin: auto;color: #000!important;" lay-verify="required" name="name" value="' + item.name + '" class="layui-input" >';
            html += '<label class="layui-form-label" style="border: none" >value:</label>';
            html += '<input  style="width:87%;margin: auto;color: #000!important;" lay-verify="required" name="value" value="' + item.value + '" class="layui-input" >';
            html += '<label class="layui-form-label" style="border: none" >描述信息:</label>';
            html += '<input  style="width:87%;margin: auto;color: #000!important;" lay-verify="required" name="desc" value="' + item.desc + '" class="layui-input" >';
            html += '<label class="layui-form-label" style="border: none" >排序:</label>';
            html += '<input  style="width:87%;margin: auto;color: #000!important;" lay-verify="required" name="sort" value="' + item.sort + '" class="layui-input" >';
            html += '<label class="layui-form-label" style="border: none" >是否显示:</label>';
            if (item.enable) {
                html += '<input type="radio" name="enable" value="1" title="是" checked />';
                html += '<input type="radio" name="enable" value="0" title="否" />';
            } else {
                html += '<input type="radio" name="enable" value="1" title="是" />';
                html += '<input type="radio" name="enable" value="0" title="否" checked />';
            }
            html += '<div class="layui-form-item">';
            html += '<div class="layui-input-inline" style="margin:10px auto 0 auto;display: block;float: none;">';
            html += '<button class="layui-btn" id="submit"  lay-submit="" lay-filter="updateConfigSubmit" >立即修改</button>';
            html += '<button type="reset" class="layui-btn layui-btn-primary">重置</button>';
            html += '</div>';
            html += '</div>';
            html += '</form>';

            layer.open({
                type: 1,
                skin: 'layui-layer-rim', //加上边框
                area: '620px', //宽高
                title: '修改系统配置',
                content: html
            });
            form.render('radio');  //radio，编辑和添加的时候
            layui.funcs.headImgShow()
        },
        deleteData: function (id) {
            layer.confirm('确定删除这个系统配置吗？', {
                btn: ['确定', '取消'] //按钮
            }, function () {
                $.ajax({
                    url: '/admin/config/remove',
                    data: {"id": id},
                    type: 'POST',
                    success: function (resp) {
                        if (resp.success) {
                            layer.alert('删除系统配置成功 !', {
                                closeBtn: 0,
                                icon: 1
                            }, function () {
                                refresh()
                            });
                        } else {
                            layer.alert("删除系统配置失败[" + resp.msg + "] !", {icon: 5});
                        }
                    }
                });
            }, function () {

            });
        },
        fileUpload: function () {
            var formData = new FormData()
            formData.append("file", $("#uploadImgInput").get(0).files[0]);
            $.ajax({
                url: '/admin/upload/image', //上传接口
                data: formData,
                type: "POST",
                processData: false,
                contentType: false,
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
        headImgShow: function () {
            $("#coverShow").attr("src", $("[name='url']").val());
        },
        reSort: function () {
            $.ajax({
                url: "/admin/config/reSort",
                type: "POST",
                data: params,
                success: function (resp) {
                    if (resp.success) {
                        layer.alert('刷新排序成功 !', function() {
                            refresh()
                        });
                    } else {
                        layer.alert('刷新排序失败[' + resp.data + '], 请联系管理人员!');
                    }
                }
            });
        }
    };
    exports('funcs', funcs);
});
