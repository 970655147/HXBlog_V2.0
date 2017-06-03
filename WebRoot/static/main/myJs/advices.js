/**
 * blogList.js
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/28/2017 11:05 AM
 */

/**
 * 初始化博客列表
 */
function contentInit() {

    var app = new Vue({
        el: '#bodyContent',
        data: {
            blog: {},
            comments: [],
            userName : "",
            email : "",
            selectedHeadImgUrl : "",
            headImages : []
        },
        mounted: function () {
            var that = this
            $.ajax({
                url: "/blog/advices",
                data: { },
                success: function (resp) {
                    if (resp.success) {
                        that.blog = resp.data.blog
                        var comments = resp.data.comments
                        $("#blogContent").html(that.blog.content)

                        for (idx in comments) {
                            that.comments.push({
                                floorComment: comments[idx][0],
                                replies: comments[idx].slice(1)
                            })
                        }

                        var userInfo = resp.extra
                        if(userInfo !== null) {
                            that.userName = userInfo.userName
                            that.email = userInfo.email
                            that.selectedHeadImgUrl = userInfo.headImgUrl
                        }
                    }
                }
            });
            $.ajax({
                url: "/image/headImgList",
                data: {},
                type : "GET",
                success: function (resp) {
                    if (resp.success) {
                        that.headImages = resp.data
                        that.selectedHeadImgUrl = that.headImages[0].url
                    } else {
                        console.log("拉取头像列表失败")
                    }
                }
            });

            SyntaxHighlighter.all()
            that.initEmoji()

            heartClick("#blogHeart", "#blogLikeCount", function(isPrise) {

            })
        },
        methods: {
            replyFunc : function(event) {
                var floorInfo = $(event.currentTarget).parents(".replyDiv").find(".floorInfo")

                $("#comment").text("[reply]" + floorInfo.attr("name") + "[/reply]\r\n")
                $("html,body").animate({scrollTop: $("#comment").offset().top}, 1000);
            },
            initEmoji: function () {
                $("#comment").emoji({
                    button: "#looks",
                    animation: 'fade',
                    icons: [{
                        name: "qq",
                        path: "/static/main/images/qq/",
                        maxNum: 91,
                        file: ".gif",
                        placeholder: "#qq_{alias}#"
                    }]
                });
                this.parse();
            },
            parse: function () {
                $(".sourceText").emojiParse({
                    icons: [{
                        path: "/static/main/images/qq/",
                        file: ".gif",
                        placeholder: "#qq_{alias}#"
                    }]
                });
            },
            updateHeadImg : function(event) {
                this.selectedHeadImgUrl = $(event.target).find("option:selected").attr("imgUrl")
            }
        }
    })

}
