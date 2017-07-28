/**
 * 常用的工具集合
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/21/2017 10:43 AM
 */

// -------------------------------------------------- prototype extends ------------------------------------------------
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
 * 从当前数组对象中获取 target 的索引
 * @param arr
 * @param target
 * @returns {*}
 */
function indexOf(arr, target) {
    for (var idx in arr) {
        if (arr[idx] === target) {
            return idx
        }
    }

    return -1
}

/**
 * 从当前数组对象中获取 target 是否存在
 * @param arr
 * @param target
 * @returns {boolean}
 */
function contains(arr, target) {
    return indexOf(arr, target) >= 0
}

// -------------------------------------------------- utils ------------------------------------------------

/**
 * 判断给定的对象是否为空
 * @param obj
 * @returns {boolean}
 */
function isEmpty(obj) {
    return obj === null || obj === undefined || '' === obj.toString().trim();
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
    // ;JSESSIONID, #_anchorId, not deal
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
 * 刷新当前页面
 */
refresh = function () {
    location.reload()
}

/**
 * 根据给定的url, 以及参数, 封装查询字符串
 *
 * @param baseUrl
 * @param params
 * @returns {*}
 */
encapGetUrl = function (baseUrl, params) {
    var sb = new StringBuilder()
    for (key in params) {
        sb.append(key + "=" + params[key])
    }
    var sep = baseUrl.indexOf("?") > 0 ? "&" : "?";

    return baseUrl + sep + sb.join("&")
}

/**
 * 根据给定的分页信息, 收集需要展示的分页信息
 *
 * @param pagination
 * @param pageInfo
 * @param pageNow
 * @param getPageUrlFunc
 */
function collectPagination(pagination, pageInfo, pageNow, getPageUrlFunc) {
    pageNow = parseInt(pageNow)
    pagination.push({active: false, pageUrl: getPageUrlFunc(1), pageNo: "首页"})
    if (pageNow !== 1) {
        pagination.push({active: false, pageUrl: getPageUrlFunc(pageNow - 1), pageNo: "上一页"})
    }
    var start = (pageNow - 3) >= 1 ? pageNow - 3 : 1
    var end = pageNow + 3 <= pageInfo.totalPage ? pageNow + 3 : pageInfo.totalPage
    for (var i = start; i <= end; i++) {
        pagination.push({active: (i === pageNow) ? "active" : "false", pageUrl: getPageUrlFunc(i), pageNo: i})
    }
    if (pageNow !== pageInfo.totalPage) {
        pagination.push({active: false, pageUrl: getPageUrlFunc(pageNow + 1), pageNo: "下一页"})
    }
    pagination.push({active: false, pageUrl: getPageUrlFunc(pageInfo.totalPage), pageNo: "尾页"})
}

/**
 * 深拷贝给定的对象
 *
 * @param data
 */
function copyOf(data) {
    if (typeof data !== 'object') {
        return data
    }

    return JSON.parse(JSON.stringify(data));
}

/**
 * 如果给定的字符串 长于len, 则将长的部分 使用 placeholder 替换
 * @param str
 * @param len
 * @param placeholder
 */
function trimIfExceed(str, len, placeholder) {
    if (isEmpty(str)) {
        return "";
    }
    if (str.length <= len) {
        return str
    }

    return str.substr(0, len - placeholder.length) + placeholder
}

/**
 * 防止一个页面上多个 ajax, 然后 同时弹出多个弹框的场景
 * @type {boolean}
 */
var interceptorDialog = null
/**
 * 放在请求头中的 token 的key
 * @type {string}
 */
var tokenHeader = "hx_blog_token"

/**
 * 自己封装的一层 发送 ajax, 主要用于 处理一些 发送请求之前之后, 都要处理的业务
 * @param config
 */
function ajax(config) {
    if (contains(ajaxNeedFilter, config.url)) {
        $.ajax(config)
        return
    }

    var beforeSend = function (xhr) {
        xhr.setRequestHeader(tokenHeader, localStorageGet(tokenHeader));
        return true
    }
    var beforeSucc = function (resp, status, xhr) {
        var tokenFromServer = xhr.getResponseHeader(tokenHeader)
        if (!isEmpty(tokenFromServer)) {
            // hide this !!
            var tokenClientHandled = hex_md5(tokenFromServer)
            localStorageSet(tokenHeader, tokenClientHandled)
        }
        if (resp.code === 200) {
            return true
        }

        if ((201 === resp.code) || (202 === resp.code)) {
            alertIfException(resp.data, function () {
                layer.close(interceptorDialog)
                interceptorDialog = null
                locateRoot(window).location.href = "/static/admin/index.html"
            })
            return false
        } else if ((203 === resp.code) || (204 === resp.code) || (205 === resp.code) || (206 === resp.code)) {
            alertIfException(resp.data, function () {
                layer.close(interceptorDialog)
                interceptorDialog = null
            })
            return false
        } else if (500 === resp.code) {
            // alertIfException(resp.data, function () {
            //     layer.close(interceptorDialog)
            //     interceptorDialog = null
            // })
            return true
        } else {
            return true;
        }
    }

    ajax0(config, beforeSend, beforeSucc)
}

/**
 * 服务端返回异常数据之后的异常处理
 * @param msg
 * @param callback
 */
function alertIfException(msg, callback) {
    if (interceptorDialog === null) {
        interceptorDialog = layer.alert(msg, callback)
    }
}

/**
 * 定位顶级window节点
 * 不知道 为什么递归 会stackoverflow, 顶级window.parent 不是null吗??
 * @param window
 * @returns {*}
 */
function locateRoot(window) {
    if (window.parent) {
        return window.parent
    }
    return window
}

/**
 * 在ajax的基础上面包装一层
 * 统一的处理 beforeSend, beforeSuccess, afterSuccess, beforeException, afterException
 * @param config 原有的参数, 按照 jquery 原生的格式传递
 * @param beforeSend
 * @param beforeSucc
 * @param afterSucc
 * @param beforeEx
 * @param afterEx
 */
function ajax0(config, beforeSend, beforeSucc, afterSucc, beforeEx, afterEx) {
    var oldBeforeFunc = config.beforeSend
    var oldSuccFunc = config.success
    var oldErrorFunc = config.error
    config.beforeSend = function (xhr) {
        beforeSend && beforeSend(xhr) && oldBeforeFunc && oldBeforeFunc(xhr)
    }
    config.success = function (resp, status, xhr) {
        beforeSucc && beforeSucc(resp, status, xhr) && oldSuccFunc && oldSuccFunc(resp, status, xhr)
        && afterSucc && afterSucc(resp, status, xhr)
    }
    config.error = function (xhr, errMsg, ex) {
        beforeEx && beforeEx(xhr, errMsg, ex) && oldErrorFunc && oldErrorFunc(xhr, errMsg, ex)
        && afterEx && afterEx(xhr, errMsg, ex)
    }

    $.ajax(config)
}

/**
 * 将给定的kv 存放到 localStorage
 * 增加一层封装, 为了一些统一的处理
 *
 * @param key
 * @param value
 */
function localStorageSet(key, value) {
    localStorage.setItem(key, value)
}

function localStorageGet(key) {
    return localStorage.getItem(key)
}

/**
 * 将给定的kv 存放到 sessionStorage
 * 增加一层封装, 为了一些统一的处理
 *
 * @param key
 * @param value
 */
function sessionStorageSet(key, value) {
    sessionStorage.setItem(key, value)
}

function sessionStorageGet(key) {
    return sessionStorage.getItem(key)
}

/**
 * 格式化 json
 * refer : http://www.cnblogs.com/Jeremy2001/p/6530726.html
 * @param json
 * @param options
 * @returns {string}
 */
var prettyJson = function (json, options) {
    var reg = null,
        formatted = '',
        pad = 0,
        PADDING = '    ';
    options = options || {};
    options.newlineAfterColonIfBeforeBraceOrBracket = (options.newlineAfterColonIfBeforeBraceOrBracket === true) ? true : false;
    options.spaceAfterColon = (options.spaceAfterColon === false) ? false : true;
    if (typeof json !== 'string') {
        json = JSON.stringify(json);
    } else {
        json = JSON.parse(json);
        json = JSON.stringify(json);
    }
    reg = /([\{\}])/g;
    json = json.replace(reg, '\r\n$1\r\n');
    reg = /([\[\]])/g;
    json = json.replace(reg, '\r\n$1\r\n');
    reg = /(\,)/g;
    json = json.replace(reg, '$1\r\n');
    reg = /(\r\n\r\n)/g;
    json = json.replace(reg, '\r\n');
    reg = /\r\n\,/g;
    json = json.replace(reg, ',');
    if (!options.newlineAfterColonIfBeforeBraceOrBracket) {
        reg = /\:\r\n\{/g;
        json = json.replace(reg, ':{');
        reg = /\:\r\n\[/g;
        json = json.replace(reg, ':[');
    }
    if (options.spaceAfterColon) {
        reg = /\:/g;
        json = json.replace(reg, ':');
    }
    (json.split('\r\n')).forEach(function (node, index) {
            var i = 0,
                indent = 0,
                padding = '';

            if (node.match(/\{$/) || node.match(/\[$/)) {
                indent = 1;
            } else if (node.match(/\}/) || node.match(/\]/)) {
                if (pad !== 0) {
                    pad -= 1;
                }
            } else {
                indent = 0;
            }

            for (i = 0; i < pad; i++) {
                padding += PADDING;
            }

            formatted += padding + node + '\r\n';
            pad += indent;
        }
    );
    return formatted;
}

/**
 * 转义给定的字符串
 * @param str
 * @param needToTransfer
 * @param transferChar
 */
function transfer(str, needToTransfer, transferChar) {
    var sb = new StringBuilder()
    for (var i = 0, len = str.length; i < len; i++) {
        var ch = str.charAt(i)
        if (contains(needToTransfer, ch)) {
            sb.append(transferChar)
        }
        sb.append(ch)
    }

    return sb.toString()
}

/**
 * 反转义给定的字符
 * @param str
 * @param needToTransfer
 * @param transferChar
 */
function detransfer(str, needToTransfer, transferChar) {
    var sb = new StringBuilder()
    for (var i = 0, len = str.length; i < len; i++) {
        var ch = str.charAt(i)
        if ((ch === transferChar) && (contains(needToTransfer, str.charAt(i + 1)))) {
            sb.append(str.charAt(i + 1))
            i++;
        } else {
            sb.append(ch)
        }
    }

    return sb.toString()
}

/**
 * 转义', "
 * @param str
 * @returns {*}
 */
function transferQuote(str) {
    return transfer(str, ['\'', '"'], "\\")
}

/**
 * 转义', "
 * @param str
 * @returns {*}
 */
function detransferQuote(str) {
    return detransfer(str, ['\'', '"'], "\\")
}

/**
 * 将arr中的所有元素旋转一次
 * @param arr
 */
function reverse(arr) {
    if (arr &&
        (typeof arr === 'object' && Array === arr.constructor)) {
        var halfOf = (arr.length >>> 1);
        for (var i = 0; i < halfOf; i++) {
            var tmp = arr[i]
            arr[i] = arr[arr.length - i - 1]
            arr[arr.length - i - 1] = tmp
        }
    }
}

// -------------------------------------------------- to be continued ------------------------------------------------

