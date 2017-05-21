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





