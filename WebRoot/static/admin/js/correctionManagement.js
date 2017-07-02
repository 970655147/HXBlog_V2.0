/**
 * blogTagManage.js
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/24/2017 9:40 PM
 */

var params = getParamsFromUrl(location.href)
var codeNow = params.code
/**
 * 所有的需要校正的记录的id
 * @type {StringBuilder}
 */
var correctionItemIds = new StringBuilder()

/**
 * 各个校验类型按钮的事件
 */
$("[name='correctionItem']").click(function () {
    var baseUrl = "/static/admin/correctionManagement.html"
    params.code = $(this).attr("correctionCode")
    location.href = encapGetUrl(baseUrl, params)
})

$("[name='correctionItem'][correctionCode='" + codeNow + "']").addClass("layui-btn-normal")

layui.define(['element', 'laypage', 'layer', 'form'], function (exports) {
    var $ = layui.jquery;
    var layer = layui.layer;
    var form = layui.form();
    var laypage = layui.laypage;
    var element = layui.element();
    var laypageId = 'pageNav';

    initTable();

    /**
     * 初始化校验列表
     */
    function initTable() {
        if (isEmpty(codeNow)) {
            return
        }

        var index = layer.load(1);
        setTimeout(function () {
            layer.close(index);
            ajax({
                url: reqMap.correction.list,
                type: "GET",
                data: {code: codeNow},
                success: function (resp) {
                    if (resp.success) {
                        var html = '';
                        var tags = resp.data
                        for (var i in tags) {
                            var item = tags[i];
                            correctionItemIds.append(item.id)

                            html += '<tr>';
                            html += '<td>' + item.id + '</td>';
                            html += '<td>' + item.contextInfo + '</td>';
                            html += '<td>' + item.expect + '</td>';
                            html += '<td>' + item.value + '</td>';
                            html += '<td>' + item.desc + '</td>';
                            html += '<td><button class="layui-btn layui-btn-small layui-btn" onclick="layui.funcs.doCorrect(' + item.id + ')"><i class="layui-icon">&#xe604;</i></button></td>';
                            html += '</tr>';
                        }
                        $('#dataContent').html(html);
                        element.init();
                    } else {
                        layer.alert("拉取校正列表失败[" + resp.data + "] !", {icon: 5});
                    }
                }
            });
        }, 500);
    }

    //输出接口，主要是两个函数，一个删除一个编辑
    var funcs = {
        doCorrect: function (id) {
            ajax({
                url: reqMap.correction.doCorrection,
                data: {id: id, code: codeNow},
                type: "POST",
                success: function (resp) {
                    if (resp.success) {
                        layer.alert("校正记录成功 !", function () {
                            refresh()
                        })
                    } else {
                        layer.alert("校正记录出现问题[" + resp.data + "], 请联系管理员 !")
                    }
                }
            })
        },
        doCorrectAll: function () {
            var correctionAllParams = {code : codeNow}
            // 文件校验特殊处理 !
            if(codeNow === "3") {
                correctionAllParams.ids = correctionItemIds.join(",")
            }

            ajax({
                url: reqMap.correction.doCorrection,
                data: correctionAllParams,
                type: "POST",
                success: function (resp) {
                    if (resp.success) {
                        layer.alert("校正记录成功[" + resp.data + "] !", function () {
                            refresh()
                        })
                    } else {
                        layer.alert("校正记录出现问题[" + resp.data + "], 请联系管理员 !")
                    }
                }
            })
        }

    };
    exports('funcs', funcs);
});
