/**
 * accountManagement.js
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/24/2017 9:33 PM
 */

var linkNum = 3;

layui.define(['element', 'laypage', 'layer', 'form', 'tree'], function (exports) {
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
            var prams = getParamsFromUrl(location.href)

            $.ajax({
                url: "/admin/resource/treeList",
                type: "GET",
                async : false,
                data: prams,
                success: function (resp) {
                    if (resp.success) {
                        var root = resp.data
                        if ("#root" === root.name) {
                            $("#updateResourceForm").find("[name='rootId']").text(root.id)
                            layui.tree({
                                elem: '#resourceTree',
                                nodes: root.children,
                                click: function(node){
                                    var resourceInfoForm = $("#updateResourceForm")
                                    resourceInfoForm.find("[name='id']").val(node.id)
                                    resourceInfoForm.find("[name='name']").val(node.name)
                                    resourceInfoForm.find("[name='iconClass']").val(node.iconClass)
                                    resourceInfoForm.find("[name='url']").val(node.url)
                                    resourceInfoForm.find("[name='sort']").val(node.sort)
                                    resourceInfoForm.find("[name='parentId']").val(node.parentId)
                                    if(node.enable) {
                                        var html = ''
                                        html += '<input type="radio" name="enable" value="1" title="是" checked />'
                                        html += '<input type="radio" name="enable" value="0" title="否" />'
                                        $("[name='enableRatioGroup']").html(html)
                                    } else {
                                        var html = ''
                                        html += '<input type="radio" name="enable" value="1" title="是" />'
                                        html += '<input type="radio" name="enable" value="0" title="否" checked />'
                                        $("[name='enableRatioGroup']").html(html)
                                    }
                                    form.render('radio');  //radio，编辑和添加的时候
                                }
                            });

                            $('#dataConsole,#dataList').attr('style', 'display:block'); //显示FiledBox
                        } else {
                            layer.alert("服务器返回数据异常 !", {icon: 5});
                        }
                    } else {
                        layer.alert("拉取资源列表失败[" + resp.data + "] !", {icon: 5});
                    }
                }
            });
        }, 500);
    }

    form.on('submit(addResourceSubmit)', function (data) {
        $.ajax({
            url: "/admin/resource/add",
            type: "POST",
            data: $("#addResourceForm").serialize(),
            success: function (resp) {
                if (resp.success) {
                    layer.alert('添加资源成功!', {
                        closeBtn: 0,
                        icon: 1
                    });
                } else {
                    layer.alert("添加资源失败[" + resp.data + "] !", {icon: 5});
                }
            }
        });

        return false
    })

    form.on('submit(updateResourceSubmit)', function (data) {
        $.ajax({
            url: "/admin/resource/update",
            type: "POST",
            data: $("#updateResourceForm").serialize(),
            success: function (resp) {
                if (resp.success) {
                    layer.alert('更新资源成功!', {
                        closeBtn: 0,
                        icon: 1
                    });
                } else {
                    layer.alert('更新资源失败[' + resp.data + '], 请联系管理人员!');
                }
            }
        });

        return false
    })

    form.on('submit(deleteResourceSubmit)', function (data) {
        var id = data.field.id
        layer.confirm('您确定要删除吗？', {
            btn: ['确定', '取消'] //按钮
        }, function () {
            $.ajax({
                url: '/admin/resource/remove',
                data: {"id" : id },
                type: 'POST',
                success: function (resp) {
                    if (resp.success) {
                        layer.alert('删除资源成功!', {
                            closeBtn: 0,
                            icon: 1
                        });
                    } else {
                        layer.alert("删除资源失败[" + resp.data + "] !", {icon: 5});
                    }
                }
            });
        }, function () {

        });

        return false
    })

    //输出接口，主要是两个函数，一个删除一个编辑
    var funcs = {
        addData: function () {
            var html = '';
            html += '<form id="addResourceForm" class="layui-form layui-form-pane" action="/admin/resource/add" method="post">';
            html += '<label class="layui-form-label" style="border: none;width: 180px;" >名称:</label>';
            html += '<input  style="width:87%;margin: auto;color: #000!important;" name="name" lay-verify="required"  class="layui-input" >';
            html += '<label class="layui-form-label" style="border: none;width: 180px;" >样式:</label>';
            html += '<input  style="width:87%;margin: auto;color: #000!important;" name="iconClass" lay-verify="required"  class="layui-input" >';
            html += '<label class="layui-form-label" style="border: none;width: 180px;" >url:</label>';
            html += '<input  style="width:87%;margin: auto;color: #000!important;" name="url" lay-verify="required"  class="layui-input" >';
            html += '<label class="layui-form-label" style="border: none;width: 180px;" >排序:</label>';
            html += '<input  style="width:87%;margin: auto;color: #000!important;" name="sort" lay-verify="required"  class="layui-input" >';
            html += '<label class="layui-form-label" style="border: none;width: 120px;" >父节点:</label>';
            html += '<input  style="width:87%;margin: auto;color: #000!important;" name="parentId" lay-verify="required"  class="layui-input" >';
            html += '<label class="layui-form-label" style="border: none" >是否显示:</label>';
            html += '<input type="radio" name="enable" value="1" title="是" checked />';
            html += '<input type="radio" name="enable" value="0" title="否" />';
            html += '<div class="layui-form-item">';
            html += '<div class="layui-input-inline" style="margin:10px auto 0 auto;display: block;float: none;">';
            html += '<button class="layui-btn" id="submit"  lay-submit="" lay-filter="addResourceSubmit">添加</button>';
            html += '<button type="reset" class="layui-btn layui-btn-primary">重置</button>';
            html += '</div>';
            html += '</div>';
            html += '</form>';

            layer.open({
                type: 1,
                skin: 'layui-layer-rim', //加上边框
                area: '740px', //宽高
                title: '添加资源',
                content: html
            })
            form.render('radio');  // ratio，编辑和添加的时候
        },
        spreadAll : function() {
            var currentHref = location.href
            var newHref = new StringBuilder()
            newHref.append(currentHref)
            if(currentHref.indexOf("?") < 0) {
                newHref.append("?")
            } else {
                newHref.append("&")
            }
            newHref.append("spread=true")
            location.href = newHref.toString()
        },
        reSort : function() {
            $.ajax({
                url: "/admin/resource/reSort",
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

