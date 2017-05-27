/**
 * commonImport
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/24/2017 8:52 PM
 */

// 需要导入的css, js
var styleShetts = [
    "./css/bootstrap.css",
    "./css/dropdown.css",
    "./css/animate.min.css",
    "./css/jquery.mCustomScrollbar.min.css",
    "./css/jquery.emoji.css"
]
var jses = [
    "./js/bootstrap.min.js",
    "./js/wow.min.js",
    "./js/modernizr.custom.js",
    "./js/jquery.dropdown.js",
    "./js/time.js",
    "./js/jquery.mousewheel-3.0.6.min.js",
    "./js/jquery.mCustomScrollbar.min.js",
    "./js/xiaolongbao.js",
    "./js/myjs.js"
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


