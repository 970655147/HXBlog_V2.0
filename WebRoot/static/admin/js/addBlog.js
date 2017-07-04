/**
 * addBlog.js
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
/**
 * 周期保存博客信息的任务
 */
var saveBlogInfoTask = null

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
                loadBlogInfo(blog)
            } else {
                alert("拉取博客信息失败[" + resp.data + "] !", {icon: 5});
            }
        }
    });
} else {
    // 如果是新增文章, 首先从暂存区加载之前存储的数据
    loadBlogInfoIfWith()
}

/**
 * 周期保存博客信息到 localStorage
 */
saveBlogInfoTask = setInterval(saveBlogInfoFunc, saveBlogInfoInterval)

layui.define(['form', 'upload', 'layer'], function (exports) {
    var $ = layui.jquery;
    var layer = layui.layer;
    var form = layui.form();

    var addTypeLayer, addTypeConfirm, addBlogLayer;

    form.on('submit(submitBlog)', function (data) {
        $("[name='blogTagIds']").attr("value", collectAttrValues($("#tagSelected .layui-form-checked"), "value", ", ", false))
        $("[name='state']").attr("value", "20")
        if ((blog !== null) && (blog !== undefined) && ("40" === blog.state)) {
            $("[name='state']").attr("value", "40")
        }
        $("[name='content']").attr("value", ue.getContent())
        var saveUrl = (isEmpty(currentBlogId)) ? reqMap.blog.add : reqMap.blog.update
        if ((saveUrl === reqMap.blog.update) && ("admin" === editType)) {
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
        if ((saveUrl === reqMap.blog.update) && ("admin" === editType)) {
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
            '<div value=' + value + ' text=' + text + ' class="layui-unselect layui-form-checkbox layui-form-checked" lay-skin="" onclick="layui.funcs.toggleCheckted(this)" >' +
            '<span>' + text + '</span><i class="layui-icon"></i>' +
            '</div>')
        selectAfter.find("dd[lay-value=" + value + "]").remove()
        // 算了, 不更新标题了, 更新不来 ..
        //            selectAfter.find("[class=layui-select-title] input").attr("value", selectAfter.find("dd[lay-value]:eq(0)").text())
    });

    //输出接口，主要是两个函数，一个删除一个编辑
    var funcs = {
        addTypeData: function () {
            var html = new StringBuilder();
            html.append('<form id="addTypeForm" class="layui-form layui-form-pane" action="/admin/type/add" method="post" >')
            html.append('<label class="layui-form-label" style="border: none" name="content" >类别名称:</label>')
            html.append('<input style="width:87%;margin: auto;color: #000!important;" name="name"  class="layui-input" >')
            html.append('<label class="layui-form-label" style="border: none" name="content" >排序:</label>')
            html.append('<input style="width:87%;margin: auto;color: #000!important;" name="sort"  class="layui-input" >')
            html.append('<div class="layui-form-item">')
            html.append('<div class="layui-input-inline" style="margin:10px auto 0 auto;display: block;float: none;">')
            html.append('<button class="layui-btn" id="submit" lay-submit="" lay-filter="addTypeSubmit">添加</button>')
            html.append('<button type="reset" class="layui-btn layui-btn-primary">重置</button>')
            html.append('</div>')
            html.append('</div>')
            html.append('</form>')
            addTypeLayer = layer.open({
                type: 1,
                skin: 'layui-layer-rim', //加上边框
                area: '420px', //宽高
                title: '添加类别',
                content: html.toString()
            });
        },
        addTagData: function () {
            var html = new StringBuilder();
            html.append('<form id="addTagForm" class="layui-form layui-form-pane" action="/admin/tag/add" method="post >')
            html.append('<label class="layui-form-label" style="border: none" name="content" >标签名称:</label>')
            html.append('<input style="width:87%;margin: auto;color: #000!important;" name="name"  class="layui-input" >')
            html.append('<label class="layui-form-label" style="border: none" name="content" >排序:</label>')
            html.append('<input style="width:87%;margin: auto;color: #000!important;" name="sort"  class="layui-input" >')
            html.append('<div class="layui-form-item">')
            html.append('<div class="layui-input-inline" style="margin:10px auto 0 auto;display: block;float: none;">')
            html.append('<button class="layui-btn" id="submit" lay-submit="" lay-filter="addTagSubmit">添加</button>')
            html.append('<button type="reset" class="layui-btn layui-btn-primary">重置</button>')
            html.append('</div>')
            html.append('</div>')
            html.append('</form>')

            addTypeLayer = layer.open({
                type: 1,
                skin: 'layui-layer-rim', //加上边框
                area: '420px', //宽高
                title: '添加类别',
                content: html.toString()
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

/**
 * 加载给定的博客的信息 到dom
 */
function loadBlogInfo(blog) {
    $("[name='title']").attr("value", blog.title)
    $("[name='author']").attr("value", blog.author)
    $("[name='coverUrl']").attr("value", blog.coverUrl)
    $("[id='coverShow']").attr("src", blog.coverUrl)
    $("[name='blogTypeId'] option[value='" + blog.blogTypeId + "']").attr("selected", "")
    $("[name='blogCreateTypeId'] option[value='" + blog.blogCreateTypeId + "']").attr("selected", "")

    if(blog.blogTagIds !== null) {
        for (idx in blog.blogTagIds) {
            var value = blog.blogTagIds[idx].trim()
            var text = blog.blogTagNames[idx].trim()

            if(isEmpty(value)) {
                continue ;
            }
            $("#tagSelected").append(
                '<div value=' + value + ' text=' + text + ' class="layui-unselect layui-form-checkbox layui-form-checked" lay-skin="" onclick="layui.funcs.toggleCheckted(this)" >' +
                '<span>' + text + '</span><i class="layui-icon"></i>' +
                '</div>')
            $("[name='tagIds'] option[value='" + value + "']").remove()
        }
    }
    $("[name='tagIds'] option:eq(1)").attr("selected", "")
    $("[name='summary']").val(blog.summary)
    // Cannot read property 'getRange' of undefined
    ue.ready(function () {
        UE.getEditor('editor').execCommand('insertHtml', blog.content)
    });

    if ("10" !== blog.state) {
        $("#draftBlog").css("display", "none")
    }
}

/**
 * 清理暂存的博客信息, 并刷新
 */
function clearSaveBlogInfo() {
    layer.confirm('确认清理暂存? 不可恢复哦 !', {icon: 3, title:'确认'}, function() {
        localStorageSet(saveBlogInfoBakKey, localStorage.getItem(saveBlogInfoKey))
        localStorageSet(saveBlogContentBakKey, localStorage.getItem(saveBlogContentKey))
        localStorageSet(saveBlogInfoKey, "")
        refresh()
    });
}

/**
 * 将内容保存到 localStorage 中
 */
function saveBlogInfoFunc() {
    var formSerialized = "dummyPrefix?" + $("#addBlogForm").serialize()
    var params = getParamsFromUrl(formSerialized)

    params.coverUrl = $("[name='coverUrl']").val()
    params.blogTagIds = collectAttrValues($("#tagSelected .layui-form-checked"), "value", ", ", false).split(",")
    params.blogTagNames = collectAttrValues($("#tagSelected .layui-form-checked"), "text", ", ", false).split(",")
    var state = 20
    if ((blog !== null) && (blog !== undefined) && ("40" === state)) {
        state = 40
    }
    params.state = state

    var content = ue.getContent()
    params.editorValue = null
    var saveBlogInfoStr = transferQuote(encodeURI(JSON.stringify(params)))
    localStorageSet(saveBlogInfoKey, saveBlogInfoStr)
    localStorageSet(saveBlogContentKey, encodeURI(content))
}

/**
 * 如果暂存了博客信息的话, 加载博客信息
 */
function loadBlogInfoIfWith() {
    var blogInfoStr = localStorageGet(saveBlogInfoKey)
    if (!isEmpty(blogInfoStr)) {
        var blogInfo = JSON.parse(decodeURI(transferQuote(blogInfoStr)))
        blogInfo.content = decodeURI(localStorageGet(saveBlogContentKey))
        loadBlogInfo(blogInfo)
    }
}
