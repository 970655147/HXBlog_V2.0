/**
 * blogTagManagement.js
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/24/2017 9:40 PM
 */

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
                url: reqMap.adv.adminList,
                type: "GET",
                data: {},
                success: function (resp) {
                    if (resp.success) {
                        var html = new StringBuilder();
                        var tags = resp.data
                        for (var i in tags) {
                            var item = tags[i];
                            html.append('<tr>')
                            html.append('<td>' + item.id + '</td>')
                            html.append('<td>' + item.name + '</td>')
                            html.append('<td>' + item.provider + '</td>')
                            html.append('<td>' + item.pathMatch + '</td>')
                            html.append('<td>' + (advTypeKey2Tips[item.type] || 'unknown') + '</td>')
                            html.append('<td>' + item.params + '</td>')
                            html.append('<td>' + item.sort + '</td>')
                            html.append('<td>' + item.createdAt + '</td>')
                            html.append('<td><button class="layui-btn layui-btn-small layui-btn-normal" onclick=\'layui.funcs.editData(' + JSON.stringify(item) + ')\')"><i class="layui-icon">&#xe642;</i></button></td>')
                            html.append('<td><button class="layui-btn layui-btn-small layui-btn-danger" onclick="layui.funcs.deleteData(' + item.id + ')"><i class="layui-icon">&#xe640;</i></button></td>')
                            html.append('</tr>')
                        }
                        $('#dataContent').html(html.toString());
                        element.init();
                    } else {
                        layer.alert("拉取广告列表失败[" + resp.data + "] !", {icon: 5});
                    }
                }
            });
        }, 500);
    }

    form.on('submit(addAdvSubmit)', function (data) {
        ajax({
            url: reqMap.adv.add,
            type: "POST",
            data: $("#addAdvForm").serialize(),
            success: function (resp) {
                if (resp.success) {
                    layer.alert('添加广告成功!', {
                        closeBtn: 0,
                        icon: 1
                    }, function () {
                        refresh()
                    });
                } else {
                    layer.alert("添加广告失败[" + resp.data + "] !", {icon: 5});
                }
            }
        });
        return false
    })

    form.on('submit(updateAdvSubmit)', function (data) {
        ajax({
            url: reqMap.adv.update,
            type: "POST",
            data: $("#updateAdvForm").serialize(),
            success: function (resp) {
                if (resp.success) {
                    layer.alert('更新广告成功!', {
                        closeBtn: 0,
                        icon: 1
                    }, function () {
                        refresh()
                    });
                } else {
                    layer.alert("更新广告失败[" + resp.data + "] !", {icon: 5});
                }
            }
        });
        return false
    })

    //输出接口，主要是两个函数，一个删除一个编辑
    var funcs = {
        addData: function () {
            var html = new StringBuilder();
            html.append('<form id="addAdvForm" class="layui-form layui-form-pane" action="' + reqMap.adv.add + '" method="post">')
            html.append('<label class="layui-form-label" style="border: none" name="name"  >名称:</label>')
            html.append('<input  style="width:87%;margin: auto;color: #000!important;" name="name"  class="layui-input" lay-verify="required" >')
            html.append('<label class="layui-form-label" style="border: none" name="provider"  >提供者:</label>')
            html.append('<input  style="width:87%;margin: auto;color: #000!important;" name="provider"  class="layui-input" lay-verify="required" >')
            html.append('<label class="layui-form-label" style="border: none" name="pathMatch"  >匹配路径:</label>')
            html.append('<input  style="width:87%;margin: auto;color: #000!important;" name="pathMatch"  class="layui-input" lay-verify="required" >')
            html.append('<label class="layui-form-label" style="border: none" >类型:</label>')
            funcs.appendAdvTypes(html, advTypeKeyLiteral.imgFixed)
            // html.append('<input  style="width:87%;margin: auto;color: #000!important;" name="type"  class="layui-input" lay-verify="required" >')
            html.append('<br/><br/>')
            html.append('<label class="layui-form-label" style="border: none" name="params" >参数:</label>')
            html.append('<textarea style="width:87%;margin-left:60px; color: #000!important; height:200px" name="params" class="layui-area" ></textarea>')
            html.append('<label class="layui-form-label" style="border: none" name="sort"  >排序:</label>')
            html.append('<input  style="width:87%;margin: auto;color: #000!important;" name="sort"  class="layui-input" lay-verify="required" >')
            html.append('<div class="layui-form-item">')
            html.append('<div class="layui-input-inline" style="margin:10px auto 0 auto;display: block;float: none;">')
            html.append('<button class="layui-btn" id="submit"  lay-submit="" lay-filter="addAdvSubmit">添加</button>')
            html.append('<button type="reset" class="layui-btn layui-btn-primary">重置</button>')
            html.append('</div>')
            html.append('</div>')
            html.append('</form>')

            layer.open({
                type: 1,
                skin: 'layui-layer-rim', //加上边框
                area: ['800px', '600px'], //宽高
                title: '添加广告',
                content: html.toString()
            })
            form.render('radio')  //radio，编辑和添加的时候
        },
        editData: function (item) {
            var html = new StringBuilder();
            html.append('<form id="updateAdvForm" class="layui-form layui-form-pane" action="' + reqMap.adv.update + '" method="post">')
            html.append('<input type="hidden" name="id" value="' + item.id + '"/>')
            html.append('<label class="layui-form-label" style="border: none" name="name" >名称:</label>')
            html.append('<input  style="width:87%;margin: auto;color: #000!important;" name="name" value="' + item.name + '" class="layui-input" lay-verify="required" >')
            html.append('<label class="layui-form-label" style="border: none" name="provider"  >提供者:</label>')
            html.append('<input  style="width:87%;margin: auto;color: #000!important;" name="provider" value="' + item.provider + '" class="layui-input" lay-verify="required" >')
            html.append('<label class="layui-form-label" style="border: none" name="pathMatch"  >匹配路径:</label>')
            html.append('<input  style="width:87%;margin: auto;color: #000!important;" name="pathMatch" value="' + item.pathMatch + '" class="layui-input" lay-verify="required" >')
            html.append('<label class="layui-form-label" style="border: none" name="type"  >类型:</label>')
            funcs.appendAdvTypes(html, item.type)
            // html.append('<input  style="width:87%;margin: auto;color: #000!important;" name="type" value="' + item.type + '" class="layui-input" lay-verify="required" >')
            html.append('<br/><br/>')
            html.append('<label class="layui-form-label" style="border: none" name="params" >参数:</label>')
            html.append('<textarea style="width:87%;margin-left:60px; color: #000!important; height:200px" name="params" class="layui-area" >' + prettyJson(item.params) + '</textarea>')
            html.append('<label class="layui-form-label" style="border: none" name="sort"  >排序:</label>')
            html.append('<input  style="width:87%;margin: auto;color: #000!important;" name="sort" value="' + item.sort + '" class="layui-input" lay-verify="required" >')
            html.append('<div class="layui-form-item">')
            html.append('<div class="layui-input-inline" style="margin:10px auto 0 auto;display: block;float: none;">')
            html.append('<button class="layui-btn" id="submit"  lay-submit="" lay-filter="updateAdvSubmit">立即提交</button>')
            html.append('<button type="reset" class="layui-btn layui-btn-primary">重置</button>')
            html.append('</div>')
            html.append('</div>')
            html.append('</form>')

            layer.open({
                type: 1,
                skin: 'layui-layer-rim', //加上边框
                area: ['800px', '600px'], //宽高
                title: '修改广告',
                content: html.toString()
            })
            form.render('radio')  //radio，编辑和添加的时候
        },
        deleteData: function (id) {
            layer.confirm('您确定要删除吗？', {
                btn: ['确定', '取消'] //按钮
            }, function () {
                ajax({
                    url: reqMap.adv.remove,
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
                            layer.alert("删除广告失败[" + resp.data + "] !", {icon: 5});
                        }
                    }
                });
            }, function () {

            });
        },
        reSort: function () {
            ajax({
                url: reqMap.adv.reSort,
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
        },
        /**
         * 添加广告类型的按钮
         *
         * @param html
         * @param checked
         */
        appendAdvTypes : function(html, checked) {
            if(isEmpty(advTypeKey2Tips[checked])) {
                checked = advTypeKeyLiteral.imgFixed
            }

            for(var type in advTypeKey2Tips) {
                var checkedOrNot = (type === checked) ? " checked " : " "
                html.append('<input type="radio" name="type" value="' + type + '" title="' + advTypeKey2Tips[type] + '" ' + checkedOrNot + ' />')
            }
        }
    };
    exports('funcs', funcs);
});
