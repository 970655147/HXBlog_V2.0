

layui.define(['element', 'laypage', 'layer', 'form', 'pagesize'], function (exports) {
    var $ = layui.jquery;
    var layer = layui.layer;
    var form = layui.form();
    var laypage = layui.laypage;
    var element = layui.element();
    var laypageId = 'pageNav';

    form.on('submit(formSearch)', function (data) {
        var index = layer.load(1);
        doSearch(layer, index, 1)
        return false;
    });

    initilData(1);
    //页数据初始化
    function initilData(pageNow) {
        var index = layer.load(1);

        //模拟数据加载
        setTimeout(doSearch(layer, index, pageNow), 1000);
    }

    function doSearch(layer, index, pageNow) {
        layer.close(index);
        var params = $("#logSearchForm").serialize()
        params += "&pageNow=" + pageNow
        params += "&pageSize=" + pageSize

        ajax({
            url: reqMap.log.requestLogList,
            type: "GET",
            data: params,
            success: function (resp) {
                if (resp.success) {
                    var html = '';
                    for (var i in resp.data.list) {
                        var item = resp.data.list[i];
                        html += '<tr>';
                        html += '<td>' + item.id + '</td>';
                        html += '<td>' + item.url + '</td>';
                        html += '<td>' + item.handler + '</td>';
                        html += '<td>' + item.params + '</td>';
                        html += '<td>' + item.cost + '</td>';
                        html += '<td>' + item.name + '</td>';
                        html += '<td>' + item.email + '</td>';
                        html += '<td>' + item.requestIp + '</td>';
                        html += '<td>' + item.isSystemUser + '</td>';
                        html += '<td>' + item.createdAt + '</td>';
                        html += '<td><button class="layui-btn layui-btn-small layui-btn-normal" onclick=\'layui.funcs.viewData(' + JSON.stringify(item) + ')\'><i class="layui-icon">&#xe642;</i></button></td>';
                        html += '</tr>';
                    }
                    $('#dataContent').html(html);
                    element.init();

                    form.render('checkbox');  //重新渲染CheckBox，编辑和添加的时候
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
                    layer.alert("拉取请求日志列表失败[" + resp.data + "] !", {icon: 5});
                }
            }
        });
    }

    form.on('submit(viewDialogSubmit)', function (data) {
        layer.close(viewDialog)
        return false;
    })

    /**
     * 查看详情的句柄
     * @type {null}
     */
    var viewDialog = null

    var funcs = {
        viewData: function (item) {
            var html = '';
            html += '<form id="viewLogForm" action="#" class="layui-form layui-form-pane" >';
            html += '<label class="layui-form-label" style="border: none" name="id" >id:</label>';
            html += '<input style="width:87%;margin: auto;color: #000!important;" name="id" value="' + item.id + '" class="layui-input" readonly >';
            html += '<label class="layui-form-label" style="border: none" name="url" >url:</label>';
            html += '<input style="width:87%;margin: auto;color: #000!important;" name="url" value="' + item.url + '" class="layui-input" readonly >';
            html += '<label class="layui-form-label" style="border: none" name="handler" >handler:</label>';
            html += '<input style="width:87%;margin: auto;color: #000!important;" name="handler" value="' + item.handler + '" class="layui-input" readonly >';
            html += '<label class="layui-form-label" style="border: none" name="id" >参数列表:</label>';
            html += '<textarea style="width:87%;margin-left:60px; color: #000!important; height:200px" name="params" class="layui-area" readonly > ' + prettyJson(item.params) + '</textarea>';
            html += '<label class="layui-form-label" style="border: none" name="id" >开销时间:</label>';
            html += '<input style="width:87%;margin: auto;color: #000!important;" name="cost" value="' + item.cost + '" class="layui-input" readonly >';
            html += '<label class="layui-form-label" style="border: none" name="id" >用户:</label>';
            html += '<input style="width:87%;margin: auto;color: #000!important;" name="name" value="' + item.name + '" class="layui-input" readonly >';
            html += '<label class="layui-form-label" style="border: none" name="id" >邮箱:</label>';
            html += '<input style="width:87%;margin: auto;color: #000!important;" name="email" value="' + item.email + '" class="layui-input" readonly >';
            html += '<label class="layui-form-label" style="border: none" name="id" >ip:</label>';
            html += '<input style="width:87%;margin: auto;color: #000!important;" name="ip" value="' + item.ip + '" class="layui-input" readonly >';
            html += '<label class="layui-form-label" style="border: none" name="id" >是否是系统用户:</label>';
            html += '<input style="width:87%;margin: auto;color: #000!important;" name="isSystemUser" value="' + item.isSystemUser + '" class="layui-input" readonly >';
            html += '<label class="layui-form-label" style="border: none" name="id" >创建时间:</label>';
            html += '<input style="width:87%;margin: auto;color: #000!important;" name="createdAt" value="' + item.createdAt + '" class="layui-input" readonly >';
            html += '<div class="layui-form-item">';
            html += '<div class="layui-input-inline" style="margin:10px auto 0 auto;display: block;float: none;">';
            html += '<button class="layui-btn" id="viewSubmit" lay-submit="" lay-filter="viewDialogSubmit" >确定</button>';
            html += '</div>';
            html += '</div>';
            html += '</form>';

            viewDialog = layer.open({
                type: 1,
                skin: 'layui-layer-rim', //加上边框
                area: ['800px', '600px'], //宽高
                title: '查看详情',
                content: html
            });
        }
    };
    exports('funcs', funcs);
});

