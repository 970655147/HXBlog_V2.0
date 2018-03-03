/**
 * 处理广告相关业务
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 3/3/2018 4:51 PM
 */

/**
 * 向服务器拉取广告, 并做展示
 *
 * @param url
 * @param args
 */
function fetchAdv(url, parentEle, args) {
    // 延迟一秒加载
    setTimeout(function() {
        ajax({
            url: reqMap.adv.list,
            data : {
                url : url
            },
            success: function(resp) {
                if(resp.success) {
                    var advList = resp.data
                    for(var idx in advList) {
                        renderAdv(url, parentEle, args, advList[idx], idx)
                    }
                }
            }
        })
    }, fetchAdvDelayInMs)

}

/**
 * 根据上下文, 渲染给定的广告
 * img_fixed : 固定一个图片的广告
 * text_fixed : 固定文字的广告
 * img_redirect : 点击之后重定向的图片广告
 * text_redirect : 点击之后重定向的文字广告
 *
 * @param url
 * @param outerArgs
 * @param adv
 * @param idx
 */
function renderAdv(url, parentEle, outerArgs, adv, idx) {
    var type2Handler = {}
    type2Handler[advTypeKeyLiteral.imgFixed] = renderImgFixed
    type2Handler[advTypeKeyLiteral.textFixed] = renderTextFixed
    type2Handler[advTypeKeyLiteral.imgRedirect] = renderImgRedirect
    type2Handler[advTypeKeyLiteral.textRedirect] = renderTextRedirect
    type2Handler[defaultKey] = renderDefault

    var renderFunc = getByIdx(type2Handler, adv.type, defaultKey)
    renderFunc(url, parentEle, outerArgs, adv, idx)
}

// -------------------------------------------------- render methods ------------------------------------------------

/**
 * 根据上下文, 默认的渲染方式
 *
 * @param url
 * @param outerArgs
 * @param adv
 * @param idx
 */
function renderDefault(url, parentEle, outerArgs, adv, idx) {
    // do nothing
}

/**
 * 根据上下文, 渲染固定位置的图片的广告
 * params 参数说明 : src. 图片的地址, fadeInMs : 淡进的时间长度, style. 各个样式
 *
 * @param url
 * @param parentEle
 * @param outerArgs
 * @param adv
 * @param idx
 */
function renderImgFixed(url, parentEle, outerArgs, adv, idx) {
    var provider = adv.provider
    var params = JSON.parse(adv.params)

    var advEle = $("<img />")
    advEle.attr("src", params.src)
    advEle.attr("alt", "广告提供商 : " + provider)
    if(params.style !== undefined && params.style !== null) {
        for(var styleKey in params.style) {
            advEle.css(styleKey, params.style[styleKey])
        }
    }
    advEle.css("display", "none")
    $(parentEle).append(advEle)

    // 淡进淡出效果
    var fadeInMs = params.fadeInMs || 1000
    var eleInDoc = $(advEle)
    eleInDoc.fadeIn(fadeInMs)
    return eleInDoc
}

/**
 * 根据上下文, 渲染固定位置的文字的广告
 *  params 参数说明 : text. 文字的内容, fadeInMs : 淡进的时间长度, style. 各个样式
 *
 * @param url
 * @param parentEle
 * @param outerArgs
 * @param adv
 * @param idx
 */
function renderTextFixed(url, parentEle, outerArgs, adv, idx) {
    var provider = adv.provider
    var params = JSON.parse(adv.params)

    var advEle = $("<span />")
    advEle.text(params.text)
    if(params.style !== undefined && params.style !== null) {
        for (var styleKey in params.style) {
            advEle.css(styleKey, params.style[styleKey])
        }
    }
    advEle.css("display", "none")
    $(parentEle).append(advEle)

    // 淡进淡出效果
    var fadeInMs = params.fadeInMs || 1000
    var eleInDoc = $(advEle)
    eleInDoc.fadeIn(fadeInMs)
    return eleInDoc
}

/**
 * 根据上下文, 渲染固定位置的图片的广告, 点击跳转 url
 * params 参数说明 : 继承 renderImgFixed, redirectUrl. 点击需要跳转的url
 *
 * @param url
 * @param parentEle
 * @param outerArgs
 * @param adv
 * @param idx
 */
function renderImgRedirect(url, parentEle, outerArgs, adv, idx) {
    var advEle = renderImgFixed(url, parentEle, outerArgs, adv, idx)
    var params = JSON.parse(adv.params)

    advEle.click(function() {
        location.href = params.redirectUrl
    })
}

/**
 * 根据上下文, 渲染固定位置的文字的广告, 点击跳转 url
 *  params 参数说明 : 继承 renderTextFixed, redirectUrl. 点击需要跳转的url
 *
 * @param url
 * @param parentEle
 * @param outerArgs
 * @param adv
 * @param idx
 */
function renderTextRedirect(url, parentEle, outerArgs, adv, idx) {
    var advEle = renderTextFixed(url, parentEle, outerArgs, adv, idx)
    var params = JSON.parse(adv.params)

    advEle.click(function() {
        location.href = params.redirectUrl
    })
}


