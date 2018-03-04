/**
 * blogDetail.js
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/28/2017 11:05 AM
 */

$(document).ready(function () {
    /**
     * 从查询字符串中提取数据作为查询条件
     * @type {Object}
     */
    var params = getParamsFromUrl(window.location.href)
    if (params.id === '') {
        location.href = formatContextUrl('/')
    }

    ajax({
        url: reqMap.blog.adminGet,
        data: params,
        success: function (resp) {
            if (resp.success) {
                var blog = resp.data
                $("[name='title']").text(blog.title)
                $("[name='createdAt']").text(blog.createdAt)
                $("[name='author']").text(blog.author)
                $("[name='blogType']").html(
                    '<button type="button" name="typeBtn" typeId="' + blog.blogTypeId + '" typeName="' + blog.blogTypeName + '"  ' +
                    'class="btn btn-xs btn-info">' + blog.blogTypeName + '</button>')
                var tagsEle = $("[name='blogTags']")
                for (var idx in blog.blogTagIds) {
                    tagsEle.append(
                        '<button type="button" name="tagBtn" tagId="' + blog.blogTagIds[idx] + '" tagName="' + blog.blogTagNames[idx] + '"  ' +
                        'class="btn btn-xs btn-success">' + blog.blogTagNames[idx] + '</button>'
                    )
                }

                $("[name='typeBtn']").click(function () {
                    var btn = $(this)
                    location.href = formatContextUrl("/static/main/blogList.html?typeId=" + btn.attr("typeId") + "&typeName=" + btn.attr("typeName"))
                })
                $("[name='tagBtn']").click(function () {
                    var btn = $(this)
                    location.href = formatContextUrl("/static/main/blogList.html?tagId=" + btn.attr("tagId") + "&tagName=" + btn.attr("tagName"))
                })

                $("#blogContent").html(blog.content)
            } else {
                layer.alert("拉取博客列表失败 !")
            }
        }
    });
})

