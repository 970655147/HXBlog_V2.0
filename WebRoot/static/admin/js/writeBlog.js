/**
 * writeBlog.js
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/21/2017 10:01 PM
 */

    //实例化编辑器
var ue = UE.getEditor('editor');

// 加载类型, 标签
$.ajax({
    url : "/composite/typeAndTags",
    type : "GET",
    success : function (result) {
        if(result.success) {
            var types = result.data.types
            for(idx in types) {
                $("#blogTypeId").append("<option value='" + types[idx].id + "'> " + types[idx].name + " </option>")
            }
            var tags = result.data.tags
            for(idx in tags) {
                $("#tagIds").append("<option value='" + tags[idx].id + "'> " + tags[idx].name + " </option>")
            }
        }
    }
});

// 如果是更新博客的话, 更新加载博客原有的内容
queryParams = getParamsFromUrl(window.location.href)
var currentBlogId = queryParams.id
if(!isEmpty(currentBlogId)) {
    $.ajax({
        url : "/admin/blog/get",
        type : "GET",
        data : {"id" : currentBlogId },
        success : function (result) {
            if(result.success) {
                var blog = result.data
                $("[name='id']").attr("value", currentBlogId)
                $("[name='title']").attr("value", blog.title)
                $("[name='author']").attr("value", blog.author)
                $("[name='coverUrl']").attr("value", blog.coverUrl)
                $("[id='coverShow']").attr("src", blog.coverUrl)
                $("#blogTypeId option[value='" + blog.blogTypeId + "']").attr("selected", "")
                for(idx in blog.blogTagIds) {
                    var value = blog.blogTagIds[idx]
                    var text = blog.blogTagNames[idx]
                    $("#tagSelected").append(
                        '<div value=' + value + ' class="layui-unselect layui-form-checkbox layui-form-checked" lay-skin="" onclick="layui.funcs.toggleCheckted(this)" >' +
                        '<span>' + text + '</span><i class="layui-icon"></i>' +
                        '</div>')
                    $("#tagIds option[value='" + value + "']").remove()
                }
                $("#tagIds option:eq(1)").attr("selected", "")
                $("[name='summary']").val(blog.summary)
                // Cannot read property 'getRange' of undefined
                ue.ready(function() {
                    UE.getEditor('editor').execCommand('insertHtml', blog.content)
                });
            }
        }
    });
}

layui.define(['form', 'upload', 'layer'], function (exports) {
    var $ = layui.jquery;
    var layer = layui.layer;
    var form = layui.form();

    var addTypeLayer, addTypeConfirm, addBlogLayer;

    form.on('submit(submitBlog)', function (data) {
        $("#content").attr("value", ue.getContent())
        $("#blogTagIds").attr("value", collectAttrValues($("#tagSelected .layui-form-checked"), "value", ", ", false))

        $.ajax({
            url: "/admin/blog/save",
            type: "POST",
            data: $(".layui-form").serialize(),
            success: function (result) {
                if (result.success) {
                    addBlogLayer = layer.alert('保存博客成功!', {
                        closeBtn: 0,
                        icon: 1
                    }, function () {
                        layer.close(addBlogLayer)
                        console.log("清理日志 !")
                    });
                }
            }
        });

        return false;
    });

    form.on('submit(addTypeSubmit)', function (data) {
        $.ajax({
            url: "/admin/type/add",
            type: "POST",
            data: $(".layui-form").serialize(),
            success: function (resp) {
                if (resp.success) {
                    addTypeConfirm = layer.alert('添加类型成功!', {
                        closeBtn: 0,
                        icon: 1
                    }, function () {
                        layer.close(addTypeConfirm)
                        layer.close(addTypeLayer)
                    });
                } else {
                    layer.alert('添加类型失败!', {icon: 5});
                }
            }
        });
        return false;
    })

    form.on('submit(addTagSubmit)', function (data) {
        $.ajax({
            url: "/admin/tag/add",
            type: "POST",
            data: $(".layui-form").serialize(),
            success: function (resp) {
                if (resp.success) {
                    addTypeConfirm = layer.alert('添加标签成功!', {
                        closeBtn: 0,
                        icon: 1
                    }, function () {
                        layer.close(addTypeConfirm)
                        layer.close(addTypeLayer)
                    });
                } else {
                    layer.alert('添加标签失败!', {icon: 5});
                }
            }
        });
        return false;
    })

    layui.upload({
        url: '/admin/image/upload', //上传接口
        success: function (result) { //上传成功后的回调
            if (result.success) {
                var visitUrl = result.data.url
                console.log(visitUrl)
                $("#coverUrl").attr("value", visitUrl)
                $("#coverShow").attr("src", visitUrl)
            } else {
                alert("上传文件失败");
            }
        }
    });
    form.on('select(tagIds)', function (context) {
        var select = $(context.elem)
        var value = context.value
        var selectAfter = $(context.othis)

        var text = select.find("option[value=" + value + "]").text()
        $("#tagSelected").append(
            '<div value=' + value + ' class="layui-unselect layui-form-checkbox layui-form-checked" lay-skin="" onclick="layui.funcs.toggleCheckted(this)" >' +
            '<span>' + text + '</span><i class="layui-icon"></i>' +
            '</div>')
        selectAfter.find("dd[lay-value=" + value + "]").remove()
        // 算了, 不更新标题了, 更新不来 ..
        //            selectAfter.find("[class=layui-select-title] input").attr("value", selectAfter.find("dd[lay-value]:eq(0)").text())
    });

    //输出接口，主要是两个函数，一个删除一个编辑
    var funcs = {
        addTypeData: function () {
            var html = '';
            html += '<form class="layui-form layui-form-pane" action="/admin/type/add" method="post" >';
            html += '<label class="layui-form-label" style="border: none" name="content" >类别名称:</label>';
            html += '<input id="type_add_name" style="width:87%;margin: auto;color: #000!important;" name="name"  class="layui-input" >';
            html += '<div class="layui-form-item">';
            html += '<div class="layui-input-inline" style="margin:10px auto 0 auto;display: block;float: none;">';
            html += '<button class="layui-btn" id="submit" lay-submit="" lay-filter="addTypeSubmit">添加</button>';
            html += '<button type="reset" class="layui-btn layui-btn-primary">重置</button>';
            html += '</div>';
            html += '</div>';
            html += '</form>';
            addTypeLayer = layer.open({
                type: 1,
                skin: 'layui-layer-rim', //加上边框
                area: '420px', //宽高
                title: '添加类别',
                content: html
            });
        },
        addTagData: function () {
            var html = '';
            html += '<form class="layui-form layui-form-pane" action="/admin/tag/add" method="post >';
            html += '<label class="layui-form-label" style="border: none" name="content" >标签名称:</label>';
            html += '<input id="tag_add_name" style="width:87%;margin: auto;color: #000!important;" name="name"  class="layui-input" >';
            html += '<div class="layui-form-item">';
            html += '<div class="layui-input-inline" style="margin:10px auto 0 auto;display: block;float: none;">';
            html += '<button class="layui-btn" id="submit" lay-submit="" lay-filter="addTagSubmit">添加</button>';
            html += '<button type="reset" class="layui-btn layui-btn-primary">重置</button>';
            html += '</div>';
            html += '</div>';
            html += '</form>';

            addTypeLayer = layer.open({
                type: 1,
                skin: 'layui-layer-rim', //加上边框
                area: '420px', //宽高
                title: '添加类别',
                content: html
            });
        },
        toggleCheckted: function (input) {
            input = $(input)
            if (input.hasClass("layui-form-checked")) {
                input.removeClass("layui-form-checked")
            } else {
                input.addClass("layui-form-checked")
            }
        },
        refresh: function () {
            window.location.href = window.location.href;
        }
    };


    exports('funcs', funcs);
});
