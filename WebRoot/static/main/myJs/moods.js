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

    $('.VivaTimeline').vivaTimeline({
        carousel: false,
        carouselTime: 3000
    });
    $('.gallery-grid a').Chocolat();


    var app = new Vue({
        el: '#bodyContent',
        data: {
            moods : [],
            images : []
        },
        mounted: function () {
            var that = this
            $.ajax({
                url: "/composite/moodAndImages",
                data: {},
                success: function (resp) {
                    if (resp.success) {
                        that.moods = resp.data.moods
                        that.images = resp.data.images
                    }
                }
            });
        },
        methods: {}
    })

}
