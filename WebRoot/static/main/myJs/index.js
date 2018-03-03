/**
 * index.js
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 9:18 AM
 */

/**
 * 初始化主页的内容
 */
function contentInit() {
    var app = new Vue({
        el: '#bodyContent',
        data: {
            recommend : {},
            latestBlogs : {},

            latestBlogL1 : [],
            latestBlogL2 : []
        },
        mounted: function () {
            var that = this
            ajax({
                url: reqMap.index.latest,
                success: function(resp) {
                    if(resp.success) {
                        var data = resp.data
                        that.recommend = data.recommend
                        that.latestBlogs = data.latestBlogs

                        that.initLatestBlogs(that)
                    }
                }
            })

            fetchAdv(location.href, $("#advArea"), {})
        },
        methods: {
            initLatestBlogs : function(that) {
                reverse(that.latestBlogs)
                if(that.latestBlogs.length <= 2) {
                    that.latestBlogL1 = that.latestBlogs
                } else {
                    that.latestBlogL1.push(that.latestBlogs[0])
                    that.latestBlogL1.push(that.latestBlogs[1])
                    for(i=2, len=that.latestBlogs.length; i<len; i++) {
                        that.latestBlogL2.push(that.latestBlogs[i])
                    }
                }
            }
        }
    })

}


