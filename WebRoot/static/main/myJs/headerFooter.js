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
        success: function (resp) {
            var template = $(resp)
            // resp 不能为 <html> 开头
            jq("#headerNav").html(template.find("#headerNav").html())
            jq("#bannerNav").html(template.find("#bannerNav").html())
            jq("#rightNav").html(template.find("#rightNav").html())
            jq("#footerNav").html(template.find("#footerNav").html())
            jq("#copyrightNav").html(template.find("#copyrightNav").html())
        }
    })
}

/**
 * 初始化头部, 右边栏, 底部
 */
function headerFooterInit() {
    $.ajax({
        url: "/index/index",
        success: function (resp) {
            if (resp.success) {
                var data = resp.data

                $("#title").text(data.title)
                $("#subTitle").text(data.subTitle)

                var typesEle = $("#cd-dropdown")
                for (idx in data.types) {
                    var type = data.types[idx]
                    typesEle.append("<option name='type' value='/static/main/blogList.html?typeId=" + type.id + "' >" + type.name + "</option>")
                }

                var linksEle = $("#links")
                for (idx in data.links) {
                    var link = data.links[idx]
                    linksEle.append(
                        "<div class='sim-button button5'>" +
                        "<a href='" + link.url + "'><span> " + link.name + " </span></a>" +
                        "</div>")
                }

                var tags = data.tags
                initTags(tags)

                var hotBlogsEle = $("#hotBlogs")
                for (idx in data.hotBlogs) {
                    var blog = data.hotBlogs[idx]
                    hotBlogsEle.append(
                        "<div class='blog-grids wow fadeInDown' data-wow-duration='.8s' data-wow-delay='.2s'> " +
                        "<div class='blog-grid-left'> " +
                        "<a href='/static/main/blogDetail.html?id=" + blog.id + "'> " +
                        "<img src='" + blog.coverUrl + "' class='img-responsive' alt='" + blog.author + "' width='40px' height='40px'>" +
                        "</a>" +
                        "</div>" +
                        "<div class='blog-grid-right'><h5><a href='/static/main/blogDetail.html?id=" + blog.id + "'> " + blog.title + " </a></h5></div>" +
                        "<div class='clearfix'></div>" +
                        "</div>")
                }

                var facetByMonthEle = $("#facetByMonth")
                for (idx in data.facetByMonth) {
                    var facetByMonth = data.facetByMonth[idx]
                    facetByMonthEle.append("<a>" + facetByMonth.month + " " + facetByMonth.cnt + "</a>")
                }

                var latestComments = $("#latestComments")
                for (idx in data.latestComments) {
                    var comment = data.latestComments[idx]
                    latestComments.append(
                        "<span>" +
                        "<a href='/static/main/blogDetail.html?id=" + comment.blogId + "'>" + comment.name + "@" + comment.toUser + " </a> - " + comment.createdAt + " " +
                        "</span>" +
                        "<p><span class='sourceText'>" + comment.content + "</span></p>"
                    )
                }

                $("#projLikeCnt").text(data.goodSensed)
                $("#todayVisited").text(data.todayVisited)

                heartClick("#projHeart", "#projLikeCnt", function (isPrise) {
                    console.log(isPrise)
                })
                selectHeader()
            }
        }
    });

    /**
     * 初始化标签云的数据
     * @param tags
     */
    function initTags(tags) {
        var tagEntries = [];
        for (var idx in tags) {
            var entry = {
                label: tags[idx].name,
                url: '/static/main/blogList.html?tagId=' + tags[idx].id
            }
            tagEntries.push(entry);
        }

        var settings = {
            entries: tagEntries,
            width: '100%',
            height: '100%',
            radius: '73%',
            radiusMin: '50%',
            bgDraw: true,
            bgColor: '#FFF',
            opacityOver: 1.00,
            opacityOut: 0.13,
            opacitySpeed: 6,
            fov: 800,
            speed: 0.5,
            fontFamily: 'Oswald, Arial, sans-serif',
            fontSize: '15',
            fontColor: '#000',
            fontWeight: 'normal',//bold
            fontStyle: 'normal',//italic
            fontStretch: 'normal',//wider, narrower, ultra-condensed, extra-condensed, condensed, semi-condensed, semi-expanded, expanded, extra-expanded, ultra-expanded
            fontToUpperCase: false

        };
        //var svg3DTagCloud = new SVG3DTagCloud( document.getElementById( 'holder'  ), settings );
        $('#tag-cloud').svg3DTagCloud(settings);
        parse()
    }

    function parse() {
        $(".sourceText").emojiParse({
            icons: [{
                path: "/foreground/images/qq/",
                file: ".gif",
                placeholder: "#qq_{alias}#"
            }]
        });
    }

}

/**
 * 点赞, 踩的逻辑处理
 * @param heart
 * @param likeCnt
 * @param callback
 */
function heartClick(heart, likeCnt, callback) {
    var heartEle = $(heart)
    heartEle.click(function () {
        var likeCntEle = $(likeCnt);
        var cntNow = parseInt(likeCntEle.html())

        var isPrise = (heartEle.attr("liked") !== "like")
        if (isPrise) {
            likeCntEle.html(cntNow + 1);
            heartEle.addClass("heartAnimation").attr("liked", "like");
        } else {
            likeCntEle.html(cntNow - 1);
            heartEle.removeClass("heartAnimation").attr("liked", "unlike");
            heartEle.css("background-position", "left");
        }
        if (callback) {
            callback(isPrise)
        }
    })
}

/**
 * 选择顶部导航
 */
function selectHeader() {
    var url = location.href

    var allA = $("#headerNav0 li a")
    for(idx in allA) {
        if(url.indexOf(allA[idx].href) >= 0) {
            $(allA[idx]).parent().addClass("active act")
        }
    }
}

