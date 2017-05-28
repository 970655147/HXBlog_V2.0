/**
 * js 实现的StringBuilder
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/7/2017 11:14 AM
 */

/**
 * js实现的StringBuilder
 *
 * @constructor
 */
function StringBuilder() {
    this._stringArray = new Array();
}

/**
 * 添加字符串的方法
 *
 * @param str 给定的需要添加的字符串
 */
StringBuilder.prototype.append = function (str) {
    this._stringArray.push(str);
}

/**
 * 将当前StrinbgBuilder转换为字符串
 *
 * @param sep 连接各个子元素的分隔符
 */
StringBuilder.prototype.join = function (sep) {
    return this._stringArray.join(sep);
}

/**
 * 将当前StrinbgBuilder转换为字符串
 */
StringBuilder.prototype.toString = function () {
    return this.join("");
}








