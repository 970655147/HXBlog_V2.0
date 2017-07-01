/**
 * writeBlog.js
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/21/2017 10:01 PM
 */

    //实例化编辑器
var ue = UE.getEditor('editor');

// 加载类型, 标签, 同步加载, 否则 可能 layui 绑定不了事件
initTypeAndTags()

// 如果是更新博客的话, 更新加载博客原有的内容
queryParams = getParamsFromUrl(window.location.href)
var currentBlogId = queryParams.id
var editType = queryParams.editType
var blog = null

if (!isEmpty(currentBlogId)) {
    ajax({
        url: reqMap.blog.adminGet,
        type: "GET",
        data: {"id": currentBlogId},
        async: false,
        success: function (resp) {
            if (resp.success) {
                blog = resp.data
                $("[name='id']").attr("value", currentBlogId)
                $("[name='title']").attr("value", blog.title)
                $("[name='author']").attr("value", blog.author)
                $("[name='coverUrl']").attr("value", blog.coverUrl)
                $("[id='coverShow']").attr("src", blog.coverUrl)
                $("[name='blogTypeId'] option[value='" + blog.blogTypeId + "']").attr("selected", "")
                $("[name='blogCreateTypeId'] option[value='" + blog.blogCreateTypeId + "']").attr("selected", "")

                for (idx in blog.blogTagIds) {
                    var value = blog.blogTagIds[idx].trim()
                    var text = blog.blogTagNames[idx].trim()
                    $("#tagSelected").append(
                        '<div value=' + value + ' class="layui-unselect layui-form-checkbox layui-form-checked" lay-skin="" onclick="layui.funcs.toggleCheckted(this)" >' +
                        '<span>' + text + '</span><i class="layui-icon"></i>' +
                        '</div>')
                    $("[name='tagIds'] option[value='" + value + "']").remove()
                }
                $("[name='tagIds'] option:eq(1)").attr("selected", "")
                $("[name='summary']").val(blog.summary)
                // Cannot read property 'getRange' of undefined
                ue.ready(function () {
                    UE.getEditor('editor').execCommand('insertHtml', blog.content)
                });

                if("10" !== blog.state) {
                    $("#draftBlog").css("display", "none")
                }
            } else {
                alert("拉取博客信息失败[" + resp.data + "] !", {icon: 5});
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
        $("[name='blogTagIds']").attr("value", collectAttrValues($("#tagSelected .layui-form-checked"), "value", ", ", false))
        $("[name='state']").attr("value", "20")
        if((blog !== null) && (blog !== undefined) && ("40" === blog.state)) {
            $("[name='state']").attr("value", "40")
        }
        $("[name='content']").attr("value", ue.getContent())
        var saveUrl = (isEmpty(currentBlogId)) ? reqMap.blog.add : reqMap.blog.update
        if((saveUrl === reqMap.blog.update) && ("admin"  === editType) ) {
            saveUrl = reqMap.blog.adminUpdate
        }

        ajax({
            url: saveUrl,
            type: "POST",
            data: $("#addBlogForm").serialize(),
            success: function (resp) {
                if (resp.success) {
                    addBlogLayer = layer.alert('保存博客成功!', {
                        closeBtn: 0,
                        icon: 1
                    }, function () {
                        layer.close(addBlogLayer)
                    });
                } else {
                    layer.alert("保存博客失败[" + resp.data + "] !", {icon: 5});
                }
            }
        });

        return false;
    });

    form.on('submit(draftBlog)', function (data) {
        $("[name='blogTagIds']").attr("value", collectAttrValues($("#tagSelected .layui-form-checked"), "value", ", ", false))
        $("[name='state']").attr("value", "10")
        $("[name='content']").attr("value", ue.getContent())
        var saveUrl = (isEmpty(currentBlogId)) ? reqMap.blog.add : reqMap.blog.update
        if((saveUrl === reqMap.blog.update) && ("admin"  === editType) ) {
            saveUrl = reqMap.blog.adminUpdate
        }

        ajax({
            url: saveUrl,
            type: "POST",
            data: $("#addBlogForm").serialize(),
            success: function (resp) {
                if (resp.success) {
                    addBlogLayer = layer.alert('保存草稿成功!', {
                        closeBtn: 0,
                        icon: 1
                    }, function () {
                        layer.close(addBlogLayer)
                    });
                } else {
                    layer.alert("保存草稿失败[" + resp.data + "] !", {icon: 5});
                }
            }
        });

        return false;
    });

    form.on('submit(addTypeSubmit)', function (data) {
        ajax({
            url: reqMap.type.add,
            type: "POST",
            data: $("#addTypeForm").serialize(),
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
                    layer.alert("添加类型失败[" + resp.data + "] !", {icon: 5});
                }
            }
        });
        return false;
    })

    form.on('submit(addTagSubmit)', function (data) {
        ajax({
            url: reqMap.tag.add,
            type: "POST",
            data: $("#addTagForm").serialize(),
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
                    layer.alert("保存标签失败[" + resp.data + "] !", {icon: 5});
                }
            }
        });
        return false;
    })

    layui.upload({
        elem: "#uploadCoverImage",
        url: '/admin/upload/image', //上传接口
        ext: "jpg|jpeg|png|bmp|gif",
        success: function (resp) { //上传成功后的回调
            if (resp.success) {
                var visitUrl = resp.data.url
                $("#coverUrl").attr("value", visitUrl)
                $("#coverShow").attr("src", visitUrl);
            } else {
                layer.alert("保存图片失败[" + resp.data + "] !", {icon: 5});
            }
        }
    });

    layui.upload({
        elem: "#uploadEditorHtml",
        url: '/admin/upload/file', //上传接口
        ext: "html|txt",
        before: function (input) {
            var reader = new FileReader();
            reader.readAsText(input.files[0], encoding);
            reader.onload = function (evt) {
                var content = evt.target.result;
                UE.getEditor('editor').execCommand('insertHtml', content)
            }
        },
        success: function (resp) { //上传成功后的回调
            if (resp.success) {
            } else {
                layer.alert("保存文件失败[" + resp.data + "] !", {icon: 5});
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
            html += '<form id="addTypeForm" class="layui-form layui-form-pane" action="/admin/type/add" method="post" >';
            html += '<label class="layui-form-label" style="border: none" name="content" >类别名称:</label>';
            html += '<input style="width:87%;margin: auto;color: #000!important;" name="name"  class="layui-input" >';
            html += '<label class="layui-form-label" style="border: none" name="content" >排序:</label>';
            html += '<input style="width:87%;margin: auto;color: #000!important;" name="sort"  class="layui-input" >';
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
            html += '<form id="addTagForm" class="layui-form layui-form-pane" action="/admin/tag/add" method="post >';
            html += '<label class="layui-form-label" style="border: none" name="content" >标签名称:</label>';
            html += '<input style="width:87%;margin: auto;color: #000!important;" name="name"  class="layui-input" >';
            html += '<label class="layui-form-label" style="border: none" name="content" >排序:</label>';
            html += '<input style="width:87%;margin: auto;color: #000!important;" name="sort"  class="layui-input" >';
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
        coverImgShow: function () {
            $("#coverShow").attr("src", $("[name='coverUrl']").val());
        }
    };


    exports('funcs', funcs);
});

/**
 * 初始化类型, 标签列表
 */
function initTypeAndTags() {
    ajax({
        url: reqMap.composite.typeAndTags,
        type: "GET",
        async: false,
        success: function (resp) {
            if (resp.success) {
                var typeIdEle = $("[name='blogTypeId']")
                var types = resp.data.types
                for (idx in types) {
                    typeIdEle.append("<option value='" + types[idx].id + "'> " + types[idx].name + " </option>")
                }

                var createTypeIdEle = $("[name='blogCreateTypeId']")
                var createTypes = resp.data.createTypes
                for (idx in createTypes) {
                    createTypeIdEle.append("<option value='" + createTypes[idx].id + "'> " + createTypes[idx].name + " </option>")
                }

                var tagIdEle = $("[name='tagIds']")
                var tags = resp.data.tags
                for (idx in tags) {
                    tagIdEle.append("<option value='" + tags[idx].id + "'> " + tags[idx].name + " </option>")
                }
            } else {
                layer.alert("拉取类型/标签列表失败[" + resp.data + "] !", {icon: 5});
            }
        }
    });
}
