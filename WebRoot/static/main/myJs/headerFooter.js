/**
 * header - footer
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/27/2017 7:58 PM
 */

var templateUrl = "/static/main/templates.html"
var jq = $

/**
 * 加载header, footer
 */
function importHeaderFooter() {
    $.ajax({
        url: templateUrl,
        type: "GET",
        async: false,
        data: {},
        success: function (result) {
            var template = $(result)
            // resp 不能为 <html> 开头
            jq("#header").html(template.find("#header").html())
            jq("#banner").html(template.find("#banner").html())
            jq("#rightNav").html(template.find("#rightNav").html())
            jq("#footerNav").html(template.find("#footerNav").html())
            jq("#copyrightNav").html(template.find("#copyrightNav").html())
        }
    })
}




