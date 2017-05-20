/**
 * index.js
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 9:18 AM
 */

var app = new Vue({

    el: '#content',
    data: {
        title: 'Hello Vue!'

    },
    mounted: function () {
        var that = this
        $.ajax({
            url: "/tests/helloWorld",
            async: false,
            success: function(resp) {
                that.title = resp.data.toString()
            }
        });
    },
    methods: {
        reverseMessage: function () {

        }
    }


})

