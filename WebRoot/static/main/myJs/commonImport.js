/**
 * commonImport
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/24/2017 8:52 PM
 */

// 需要导入的css, js
var styleShetts = []
var jses = [
    "./js/wow.min.js",
    "./js/modernizr.custom.js",
    "./js/time.js",
    "./js/xiaolongbao.js",
    "./js/myjs.js",
    "../myJs/constants.js",
    "../myJs/stringBuilder.js"
]

// 处理导入 css, js 的逻辑
var importEle = document.head
function importCss() {
    for(idx in styleShetts) {
        var newNode = document.createElement("link");
        newNode.setAttribute("rel", "stylesheet")
        newNode.setAttribute("href", styleShetts[idx])
        importEle.appendChild(newNode)
    }
}

function importJs() {
    for(idx in jses) {
        var newNode = document.createElement("script");
        newNode.setAttribute("type", "text/javascript")
        newNode.setAttribute("src", jses[idx])
        importEle.appendChild(newNode)
    }
}


