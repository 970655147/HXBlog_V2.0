/**
 * 处理 moniter.html 的相关业务
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/11/2017 9:26 AM
 */

/**
 * 刷新统计的时间间隔
 */
var statsInterval = 5;
var statsIntervalInMs = statsInterval * 1000;

/**
 * echart API 委托
 * @type {null}
 */
var eChart = null
/**
 * 上周的图表
 * @type {null}
 */
var lastWeekChart = null
/**
 * 实时的图表
 * @type {null}
 */
var realTimeChart = null

/**
 * 初始化图表
 */
initCharts()
/**
 * 定时轮询
 */
setInterval(refreshCharts, statsIntervalInMs)

/**
 * 初始化 echarts 图标
 */
function initCharts() {
    // 路径配置
    require.config({
        paths: {
            echarts: 'http://echarts.baidu.com/build/dist'
        }
    });

    // 使用
    require(
        [
            'echarts',
            'echarts/chart/line',
            'echarts/chart/bar'
        ],
        function (ec) {
            eChart = ec
            lastWeekChart = eChart.init(document.getElementsByName('lastWeekChart').item(0))
            realTimeChart = eChart.init(document.getElementsByName('realTimeChart').item(0))
            refreshCharts();
        }
    );
}

/**
 * 向服务器拉数据 刷新图表
 */
function refreshCharts() {
    var options = encapStats()
    lastWeekChart.setOption(options.lastWeekOption);
    realTimeChart.setOption(options.realTimeOption)
}

/**
 * 发送请求封装统计数据
 */
function encapStats() {
    var dayOfWeek = new Date().getDay()
    var options = {};
    ajax({
        url: reqMap.system.statsSummary,
        data: {},
        async: false,
        success: function (resp) {
            if (resp.success) {
                var lastWeekInfo = resp.data.lastWeekInfo
                var realTimeInfo = resp.data.realTimeInfo
                var lastWeekXAxisArr = []
                for (var i = 0; i < lastWeekInfo.length; i++) {
                    lastWeekXAxisArr.push(weeksStr((dayOfWeek + 7 - i) % 7))
                }
                options.lastWeekOption = encapOptions(lastWeekInfo, lastWeekXAxisArr)

                var realTimeXAxisArr = []
                var minuteAndSeconds = new Date().format("m-s").split("-")
                var minute = parseInt(minuteAndSeconds[0]), second = parseInt(minuteAndSeconds[1])
                for (var i = 0; i < realTimeInfo.length; i++) {
                    realTimeXAxisArr.push(realTimeStr(minute, second, i * (-statsInterval)))
                }
                options.realTimeOption = encapOptions(realTimeInfo, realTimeXAxisArr)
            } else {
                layer.msg("拉取统计数据失败[" + resp.data + "] !")
            }
        }
    });

    return options;
}

/**
 * 封装 echarts 的 option
 */
function encapOptions(statsArr, xAxisArr) {
    var statsDayFlushViewCnt = [], statsViewCnt = [], statsGoodCnt = []
    var statsNotGoodCnt = [], statsBlogCnt = [], statsCommentCnt = []

    var length = statsArr.length
    for (idx in statsArr) {
        var stats = statsArr[length - idx - 1]
        statsDayFlushViewCnt.push(stats.dayFlushViewCnt)
        statsViewCnt.push(stats.viewCnt)
        statsGoodCnt.push(stats.goodCnt)
        statsNotGoodCnt.push(stats.notGoodCnt)
        statsBlogCnt.push(stats.blogCnt)
        statsCommentCnt.push(stats.commentCnt)
    }

    var option = {
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['访问数量[uv]', '访问数量[pv]', '点赞数量', '取消点赞', '博客数量', '评论数量']
        },
        toolbox: {
            show: true,
            feature: {
                mark: {show: true},
                dataView: {show: true, readOnly: false},
                magicType: {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        calculable: true,
        xAxis: [
            {
                type: 'category',
                boundaryGap: false,
                data: xAxisArr
            }
        ],
        yAxis: [
            {
                type: 'value'
            }
        ],
        series: [
            {
                name: '访问数量[uv]',
                type: 'line',
                stack: '总量',
                data: statsDayFlushViewCnt
            },
            {
                name: '访问数量[pv]',
                type: 'line',
                stack: '总量',
                data: statsViewCnt
            },
            {
                name: '点赞数量',
                type: 'line',
                stack: '总量',
                data: statsGoodCnt
            },
            {
                name: '取消点赞',
                type: 'line',
                stack: '总量',
                data: statsNotGoodCnt
            },
            {
                name: '博客数量',
                type: 'line',
                stack: '总量',
                data: statsBlogCnt
            },
            {
                name: '评论数量',
                type: 'line',
                stack: '总量',
                data: statsCommentCnt
            }
        ]
    }
    return option
}

/**
 * 获取给定的 dayOfWeek 的字符串表示
 */
function weeksStr(dayOfWeek) {
    return "星期" + "日一二三四五六".charAt(dayOfWeek);
}

/**
 * 获取 给定的时间 偏移为off的时间的字符串表示
 *
 * @param minute
 * @param second
 * @param off
 */
function realTimeStr(minute, second, off) {
    var offMinute = off / 60
    var offSencond = off % 60
    return mod60(minute + floorFloat2Int(offMinute)) + " : " + mod60(second + roundFloat2Int(offSencond))
}

/**
 * 将给定的 floatVal 转换为 int, 四舍五入
 * @param floatVal
 */
function roundFloat2Int(floatVal) {
    if (Math.abs(floatVal) < 0.000001) {
        return 0
    }

    return Math.round(floatVal)
}

/**
 * 将给定的 floatVal 转换为 int, 取小
 * @param floatVal
 */
function floorFloat2Int(floatVal) {
    if (Math.abs(floatVal) < 0.000001) {
        return 0
    }

    return Math.floor(floatVal)
}

/**
 * 格式化 给定的 分钟/秒数
 * @param minuteOrSeond
 * @returns {number}
 */
function mod60(minuteOrSeond) {
    return (minuteOrSeond + 60) % 60;
}

