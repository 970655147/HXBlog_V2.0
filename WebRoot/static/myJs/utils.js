/**
 * 常用的工具集合
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/21/2017 10:43 AM
 */

function isEmpty(obj) {
    return obj === null || obj === undefined || '' === obj.trim();
}

/**
 * 获取给定的dom中 标签为tag, 并且有给定的属性对的元素集合
 *
 * @param scope 给定的dom
 * @param tag 需要查询的标签
 * @param attr 需要查询的标签的属性
 * @param value 需要查询的标签的值
 * @returns {Array}
 */
function getElementsByAttr(scope, tag, attr, value) {
    var elesByTag = scope.getElementsByTagName(tag);
    var result = [];
    for (var i = 0; i < elesByTag.length; i++) {
        if (elesByTag[i].getAttribute(attr) === value) {
            result.push(elesByTag[i])
        }
    }
    return result
}

/**
 * 获取给定的dom中 标签为tag, 并且有给定的属性的元素集合
 *
 * @param scope 给定的dom
 * @param tag 需要查询的标签
 * @param attr 需要查询的标签的属性
 * @returns {Array}
 */
function getElementsByWithAttr(scope, tag, attr) {
    var elesByTag = scope.getElementsByTagName(tag);
    var result = [];
    for (var i = 0; i < elesByTag.length; i++) {
        if (elesByTag[i].hasAttribute(attr)) {
            result.push(elesByTag[i]);
        }
    }
    return result;
}

/**
 * 收集给定的dom节点的属性, 以字符串返回
 *
 * @param arr 给定的dom节点数组
 * @param attr 给定的属性
 * @param sep 多个属性之间的分隔符
 * @param gotEmpty 是否获取空属性
 */
function collectAttrValues(arr, attr, sep, gotEmpty) {
    var result = new StringBuilder();
    for (idx in arr) {
        var val = arr.eq(idx).attr(attr)
        if ((gotEmpty) || (!isEmpty(val))) {
            result.append(arr.eq(idx).attr(attr))
        }
    }
    return result.join(sep)
}


/**
 * 从给定的url中 获取查询字符串代表的参数
 * @returns {Object}
 */
function getParamsFromUrl(url) {
    var params = {}
    var idxOfQ = url.indexOf("?")
    if (idxOfQ >= 0) {
        var str = url.substr(idxOfQ + 1)
        var splits = str.split("&")
        for (idx in splits) {
            var kvPair = splits[idx].split("=")
            if (kvPair.length >= 2) {
                params[kvPair[0]] = decodeURI(kvPair[1]);
            }
        }
    }
    return params;
}

/**
 * 获取给定的对象的长度
 *
 * @param o 给定的对象
 * @returns {number}
 */
function getLength(o) {
    var n, count = 0
    for (n in o) {
        if (o.hasOwnProperty(n)) {
            count++
        }
    }

    return count;
}

/**
 * refer : http://www.cnblogs.com/zhangpengshou/archive/2012/07/19/2599053.html
 * 对Date的扩展，将 Date 转化为指定格式的String
 * 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
 * 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
 * 例子：
 * (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
 * (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
 *
 * @param fmt
 * @returns {*}
 */
Date.prototype.format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

/**
 * 刷新当前页面
 */
refresh = function() {
    location.reload()
}

