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

    /**
     * 从查询字符串中提取数据作为查询条件
     * @type {Object}
     */
    var params = getParamsFromUrl(window.location.href)

    var app = new Vue({
        el: '#bodyContent',
        data: {
            pageInfo: {},
            blogs: []
        },
        mounted: function () {
            var that = this
            $.ajax({
                url: "/blog/list",
                data: params,
                success: function (resp) {
                    if (resp.success) {
                        that.pageInfo = resp.data
                        that.blogs = resp.data.list
                    }
                }
            });
        },
        methods: {}
    })

}
