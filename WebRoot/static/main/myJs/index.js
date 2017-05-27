/**
 * index.js
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 9:18 AM
 */

/**
 * 初始化头部, 右边栏, 底部
 */
function init() {
    var app = new Vue({

        el: '#content',
        data: {
            title: '生活有度，人生添寿',
            subTitle: '如果你浪费了自己的年龄，那是挺可悲的。因为你的青春只能持续一点儿时间——很短的一点儿时间。',
            types : ["全部"],
            tags : ["全部"],
            links : [],

            recommend : {},
            hotBlogs : {},
            latestBlogs : {},
            latestComments : {},
            facetByMonth : {},

            goodSensed : {},
            todayVisited : {},

            latestBlogL1 : [],
            latestBlogL2 : []
        },
        mounted: function () {
            var that = this
            $.ajax({
                url: "/index/index",
                async: false,
                success: function(resp) {
                    if(resp.success) {
                        var data = resp.data
                        that.title = data.title
                        that.subTitle = data.subTitle
                        that.types = data.types
                        that.tags = data.tags
                        that.links = data.links

                        that.recommend = data.recommend
                        that.hotBlogs = data.hotBlogs
                        that.latestBlogs = data.latestBlogs
                        that.latestComments = data.latestComments
                        that.facetByMonth = data.facetByMonth

                        that.goodSensed = data.goodSensed
                        that.todayVisited = data.todayVisited

                        that.initTags(that.tags)
                        that.initLatestBlogs(that)
                    }
                }
            });
        },
        methods: {
            initTags : function(tags) {
                var tagEntries = [];
                for (var idx in tags) {
                    var entry = {
                        label: tags[idx].name,
                        url: '/blog/tag/' + tags[idx].id
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
                this.parse()
            },
            initLatestBlogs : function(that) {
                if(that.latestBlogs.length <= 2) {
                    that.latestBlogL1 = that.latestBlogs
                } else {
                    that.latestBlogL1.push(that.latestBlogs[0])
                    that.latestBlogL1.push(that.latestBlogs[1])
                    for(i=2, len=that.latestBlogs.length; i<len; i++) {
                        that.latestBlogL2.push(that.latestBlogs[i])
                    }
                }
            } ,
            parse : function() {
                $(".sourceText").emojiParse({
                    icons: [{
                        path: "/foreground/images/qq/",
                        file: ".gif",
                        placeholder: "#qq_{alias}#"
                    }]
                });
            }
        }

    })


}

