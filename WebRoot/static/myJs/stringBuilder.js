/**
 * js ʵ�ֵ�StringBuilder
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/7/2017 11:14 AM
 */

/**
 * jsʵ�ֵ�StringBuilder
 *
 * @constructor
 */
function StringBuilder() {
    this._stringArray = new Array();
}

/**
 * ����ַ����ķ���
 *
 * @param str ��������Ҫ��ӵ��ַ���
 */
StringBuilder.prototype.append = function (str) {
    this._stringArray.push(str);
}

/**
 * ����ǰStrinbgBuilderת��Ϊ�ַ���
 *
 * @param sep ���Ӹ�����Ԫ�صķָ���
 */
StringBuilder.prototype.join = function (sep) {
    return this._stringArray.join(sep);
}

/**
 * ����ǰStrinbgBuilderת��Ϊ�ַ���
 */
StringBuilder.prototype.toString = function () {
    return this.join("");
}








