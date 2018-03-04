/**
 * blogManagement.js
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/24/2017 9:56 PM
 */
var sTypeNow = sessionStorageGet(location.href)
if (isEmpty(sTypeNow)) {
    sTypeNow = -1
}
$("[name='localCacheType']").val(sTypeNow)

layui.define(['element', 'laypage', 'layer', 'form', 'pagesize'], function (exports) {
    var $ = layui.jquery;
    var layer = layui.layer;
    var form = layui.form();
    var laypage = layui.laypage;
    var element = layui.element();
    var laypageId = 'pageNav';

    // /**
    //  * 提取type
    //  * @type {Object}
    //  */
    // var params = getParamsFromUrl(location.href)
    // sTypeNow = isEmpty(params.type) ? sTypeNow : params.type

    initilData(sTypeNow);
    //页数据初始化
    function initilData(type) {
        ajax({
            url: reqMap.cache.cacheDetail,
            type: "GET",
            data: {type: type},
            success: function (resp) {
                if (resp.success) {
                    var html = new StringBuilder();
                    for (var key in resp.data) {
                        var value = JSON.stringify(resp.data[key])
                        html.append('<tr>')
                        html.append('<td>' + key + '</td>')
                        html.append('<td>' + value + '</td>')
                        html.append('<td><button class="layui-btn layui-btn-small layui-btn-normal" onclick="layui.funcs.showData(\'' + transferQuote(encodeURI(key)) + '\', \'' + transferQuote(encodeURI(value)) + '\')"><i class="layui-icon">&#x1005;</i></button></td>')
                        html.append('<td><button class="layui-btn layui-btn-small layui-btn-normal" onclick="layui.funcs.visitInfo(\'' + key + '\')"><i class="layui-icon">&#xe60b;</i></button></td>')
                        html.append('<td><button class="layui-btn layui-btn-small layui-btn-danger" onclick="layui.funcs.deleteData(\'' + transferQuote(encodeURI(key)) + '\')"><i class="layui-icon">&#xe640;</i></button></td>')
                        html.append('</tr>')
                    }
                    $('#dataContent').html(html.toString());
                }
            }
        });
    }

    var showMsgDialog, visitInfoDialog
    form.on('submit(refreshBtn)', function (data) {
        refresh()
        return false
    });

    form.on('submit(formSearch)', function (data) {
        sTypeNow = $("[name='localCacheType']").val()
        if (isEmpty(sTypeNow)) {
            return false;
        }

        sessionStorageSet(location.href, sTypeNow)
        initilData(sTypeNow)
        return false
    });

    form.on('submit(showMsgFormSubmit)', function (data) {
        layer.close(showMsgDialog)
        return false
    })
    form.on('submit(visitInfoFormSubmit)', function (data) {
        layer.close(visitInfoDialog)
        return false
    })

    var funcs = {
        showData: function (key, value) {
            key = decodeURI(detransferQuote(key))
            value = decodeURI(detransferQuote(value))
            var html = new StringBuilder();
            html.append('<div id="showMsgForm" class="layui-form layui-form-pane" action="#" method="post">')
            html.append('<label class="layui-form-label" style="border: none" > key </label>')
            html.append('<textarea style="width:87%;margin-left:60px; color: #000!important; height:40px" name="params" class="layui-area" readonly > ' + key + '</textarea>')
            html.append('<hr>')
            html.append('<label class="layui-form-label" style="border: none" > 值 </label>')
            html.append('<textarea style="width:87%;margin-left:60px; color: #000!important; height:200px" name="params" class="layui-area" readonly > ' + prettyJson(value) + '</textarea>')
            html.append('<hr>')
            html.append('<div class="layui-form-item">')
            html.append('<div class="layui-input-inline" style="margin:10px auto 0 auto;display: block;float: none;">')
            html.append('<button class="layui-btn" id="submit"  lay-submit="" lay-filter="showMsgFormSubmit">确认</button>')
            html.append('</div>')
            html.append('</div>')
            html.append('</div>')

            showMsgDialog = layer.open({
                type: 1,
                skin: 'layui-layer-rim', //加上边框
                area: ['800px', '600px'], //宽高
                title: '查看回复',
                content: html.toString()
            });
        },
        visitInfo: function (key) {
            key = decodeURI(detransferQuote(key))

            ajax({
                url: reqMap.cache.cacheVisitInfo,
                data: {
                    type: sTypeNow,
                    id: key
                },
                type: 'GET',
                success: function (resp) {
                    if (resp.success) {
                        var visitInfo = resp.data
                        var html = new StringBuilder();
                        html.append('<div id="showMsgForm" class="layui-form layui-form-pane" action="#" method="post">')
                        html.append('<label class="layui-form-label" style="border: none" > key </label>')
                        html.append('<textarea style="width:87%;margin-left:60px; color: #000!important; height:40px" name="params" class="layui-area" readonly > ' + key + '</textarea>')
                        html.append('<hr>')
                        html.append('<label class="layui-form-label" style="border: none" > 值 </label>')
                        html.append('<textarea style="width:87%;margin-left:60px; color: #000!important; height:200px" name="params" class="layui-area" readonly > ' + prettyJson(visitInfo.value) + '</textarea>')
                        html.append('<hr>')
                        html.append('<label class="layui-form-label" style="border: none" > accessCnt </label>')
                        html.append('<input style="width:87%;margin: auto;color: #000!important;" class="layui-input" value="' + visitInfo.accessCount + '" readonly >')
                        html.append('<hr>')
                        html.append('<label class="layui-form-label" style="border: none" > createdAt </label>')
                        html.append('<input style="width:87%;margin: auto;color: #000!important;" class="layui-input" value="' + new Date(visitInfo.createdAt).format("yyyy-MM-dd hh:mm:ss") + '" readonly >')
                        html.append('<hr>')
                        html.append('<label class="layui-form-label" style="border: none" > lastAccess </label>')
                        html.append('<input style="width:87%;margin: auto;color: #000!important;" class="layui-input" value="' + new Date(visitInfo.lastAccessed).format("yyyy-MM-dd hh:mm:ss") + '" readonly >')
                        html.append('<hr>')
                        html.append('<label class="layui-form-label" style="border: none" > lastUpdated </label>')
                        html.append('<input style="width:87%;margin: auto;color: #000!important;" class="layui-input" value="' + new Date(visitInfo.lastUpdated).format("yyyy-MM-dd hh:mm:ss") + '" readonly >')
                        html.append('<hr>')
                        html.append('<label class="layui-form-label" style="border: none" > ttl </label>')
                        html.append('<input style="width:87%;margin: auto;color: #000!important;" class="layui-input" value="' + visitInfo.ttl + '" readonly >')
                        html.append('<hr>')
                        html.append('<div class="layui-form-item">')
                        html.append('<div class="layui-input-inline" style="margin:10px auto 0 auto;display: block;float: none;">')
                        html.append('<button class="layui-btn" id="submit"  lay-submit="" lay-filter="visitInfoFormSubmit">确认</button>')
                        html.append('</div>')
                        html.append('</div>')
                        html.append('</div>')

                        visitInfoDialog = layer.open({
                            type: 1,
                            skin: 'layui-layer-rim', //加上边框
                            area: ['800px', '600px'], //宽高
                            title: '查看回复',
                            content: html.toString()
                        });
                    } else {
                        layer.alert("拉取访问信息失败[" + resp.data + "] !", {icon: 5});
                    }
                }
            });
        },
        deleteData: function (key) {
            layer.confirm('删除对应缓存，确定删除？', {
                btn: ['确定', '取消'] //按钮
            }, function () {
                key = decodeURI(detransferQuote(key))
                ajax({
                    url: reqMap.cache.cacheRemove,
                    data: {
                        type: sTypeNow,
                        id: key
                    },
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
                            layer.alert("删除缓存失败[" + resp.data + "] !", {icon: 5});
                        }
                    }
                });
            }, function () {

            });
        }
    };
    exports('funcs', funcs);
});
