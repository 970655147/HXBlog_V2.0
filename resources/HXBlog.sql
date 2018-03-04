/*
Navicat MySQL Data Transfer

Source Server         : SC_Server
Source Server Version : 50721
Source Host           : 127.0.0.1:3306
Source Database       : blog

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-03-04 21:06:20
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for adv
-- ----------------------------
DROP TABLE IF EXISTS `adv`;
CREATE TABLE `adv` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(256) DEFAULT NULL COMMENT '广告名称',
  `provider` varchar(256) DEFAULT NULL COMMENT '广告提供商',
  `path_match` varchar(256) DEFAULT NULL COMMENT '匹配的url [多个路径使用"|“隔开]',
  `type` varchar(256) DEFAULT NULL COMMENT '广告类型',
  `params` varchar(4096) DEFAULT NULL COMMENT '参数',
  `sort` int(11) DEFAULT '0' COMMENT '排序字段',
  `created_at` varchar(32) DEFAULT NULL COMMENT '创建时间',
  `updated_at` varchar(32) DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT NULL COMMENT '是否被删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COMMENT='广告配置';

-- ----------------------------
-- Records of adv
-- ----------------------------
INSERT INTO `adv` VALUES ('16', '首页图片1', 'admin', '/**', 'img_fixed', '{\"src\":\"https://gss0.bdstatic.com/94o3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike220%2C5%2C5%2C220%2C73/sign=0d0be4b5a551f3ded7bfb136f5879b7a/838ba61ea8d3fd1fd1298e453a4e251f95ca5fa8.jpg\",\"fadeInMs\":3000,\"style\":{\"width\":\"240px\",\"height\":\"140px\",\"top\":\"1200px\",\"left\":\"80px\",\"position\":\"absolute\"}}', '10', '2018-03-03 16:11:52', '2018-03-03 16:11:52', '0');
INSERT INTO `adv` VALUES ('17', '首页文字1', 'admin', '/**', 'text_fixed', '{\"text\":\"这是广告\",\"fadeInMs\":1000,\"style\":{\"width\":\"240px\",\"height\":\"140px\",\"top\":\"1600px\",\"left\":\"80px\",\"font-size\":\"20px\",\"color\":\"red\",\"position\":\"absolute\"}}', '30', '2018-03-03 18:53:52', '2018-03-03 18:53:52', '0');
INSERT INTO `adv` VALUES ('18', '首页图片 跳转 java贴吧', 'admin', '/**', 'img_redirect', '{\r\n    \"src\":\"https://gss0.bdstatic.com/94o3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike220%2C5%2C5%2C220%2C73/sign=0d0be4b5a551f3ded7bfb136f5879b7a/838ba61ea8d3fd1fd1298e453a4e251f95ca5fa8.jpg\",\r\n    \"redirectUrl\":\"http://tieba.baidu.com/f?fr=index&kw=java\",\r\n    \"fadeInMs\":10000,\r\n    \"style\":{\r\n        \"width\":\"240px\",\r\n        \"height\":\"140px\",\r\n        \"top\":\"2200px\",\r\n        \"left\":\"80px\",\r\n        \"position\":\"absolute\"\r\n    }\r\n}\r\n\r\n', '0', '2018-03-03 16:11:52', '2018-03-03 21:34:19', '0');
INSERT INTO `adv` VALUES ('19', '首页文字 跳转 百度', 'admin', '/**', 'text_redirect', '{\r\n    \"text\":\"跳转百度\",\r\n    \"fadeInMs\":1000,\r\n    \"redirectUrl\":\"http://www.baidu.com\",\r\n    \"style\":{\r\n        \"width\":\"240px\",\r\n        \"height\":\"140px\",\r\n        \"top\":\"2600px\",\r\n        \"left\":\"80px\",\r\n        \"font-size\":\"20px\",\r\n        \"color\":\"red\",\r\n        \"position\":\"absolute\"\r\n    }\r\n}\r\n\r\n', '20', '2018-03-03 18:53:52', '2018-03-03 21:35:52', '0');
INSERT INTO `adv` VALUES ('20', '假的 广告1', 'admin1', '/**/index.html', 'text_fixed', '{\r\n\"src\" : \"xxxx\"\r\n}\r\n\r\n', '70', '2018-03-03 18:53:52', '2018-03-03 20:59:25', '1');

-- ----------------------------
-- Table structure for blog
-- ----------------------------
DROP TABLE IF EXISTS `blog`;
CREATE TABLE `blog` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(256) DEFAULT NULL COMMENT '标题',
  `author` varchar(256) DEFAULT NULL COMMENT '作者',
  `cover_url` varchar(2048) DEFAULT NULL COMMENT '封面的url',
  `blog_create_type_id` int(11) DEFAULT NULL COMMENT '博客创建类型',
  `blog_type_id` int(11) DEFAULT NULL COMMENT '博客的类型',
  `state` varchar(12) DEFAULT NULL COMMENT '博客的状态[draft, audit, success]',
  `summary` varchar(2048) DEFAULT NULL COMMENT '摘要',
  `content_url` varchar(256) DEFAULT NULL COMMENT '博客的内容的索引',
  `created_at` varchar(32) DEFAULT NULL COMMENT '创建的时间',
  `created_at_month` varchar(12) DEFAULT NULL COMMENT '创建的月份',
  `updated_at` varchar(32) DEFAULT NULL COMMENT '修改的时间',
  `deleted` tinyint(1) DEFAULT NULL COMMENT '是否被删除',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_4` (`blog_type_id`),
  CONSTRAINT `FK_Reference_4` FOREIGN KEY (`blog_type_id`) REFERENCES `blog_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=104 DEFAULT CHARSET=utf8 COMMENT='博客记录表';

-- ----------------------------
-- Records of blog
-- ----------------------------
INSERT INTO `blog` VALUES ('-3', '自我介绍', 'Jerry.X.He', 'http://localhost/imgs/2017/05/28/1B6C4998EFBC1045452E47DC9E15DD99.jpg', '1', '42', '30', '关于博主的自我介绍', '59/2017-05-28_11-42-21-自我介绍.html', '2017-05-28 11:42:21', '2017-05', '2017-05-28 11:42:21', '0');
INSERT INTO `blog` VALUES ('-2', '项目建议信息', 'admin', 'http://localhost/imgs/2017/05/28/1B6C4998EFBC1045452E47DC9E15DD99.jpg', '1', '42', '30', '项目上下文信息', '4/2017-06-04_16-55-31-项目建议信息.html', '2017-06-02 23:38:06', '2017-06', '2017-06-04 16:55:31', '0');
INSERT INTO `blog` VALUES ('-1', '项目上下文信息', 'admin', 'http://localhost:8080/static/admin/images/logo.jpg', '1', '42', '30', 'digest', '19/2017-06-04_16-57-01-项目上下文信息.html', '2017-05-21 03:51:07', '2017-06', '2017-06-04 16:57:01', '0');
INSERT INTO `blog` VALUES ('27', '关于java中有几种类型的变量', 'admin', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1496576208623&di=d160a80ed1f560fe173bd5a029514d5c&imgtype=0&src=http%3A%2F%2Fimg9.3lian.com%2Fc1%2Fvector2%2F03%2F07%2F03.jpg', '1', '41', '30', '上周记得去面试吧, 面试官问到了java中有几种类型的变量, 我当时答得是 : 原始类型 和引用类型, 如果引用也算是一种类型的话, 那么 吧\"引用\"这种类型也算上\r\n\r\n然后 今天, 突然 想到了这个问题, 因此 查了一下jls, 找到了答案 ', '53/2017-06-04_16-49-00-关于java中有几种类型的变量.html', '2017-06-04 16:49:00', '2017-06', '2017-06-04 16:49:00', '0');
INSERT INTO `blog` VALUES ('38', '深入理解Java虚拟机[第一章实战] 编译Hotspot', 'admin', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497064610057&di=de420c8ffcbad17a287a914805eab45e&imgtype=0&src=http%3A%2F%2Fm2.app111.com%2FAppImg%2F160x160%2F2012%2F01%2F28%2F4862078961456197427622.jpg', '1', '41', '30', '想要一探JDK内部的实现机制,最便捷的路径之一就是自己编译一套JDK ,通过阅读和跟踪调试JDK源码去了解Java技术体系的原理,虽然门槛会高一点,但肯定会比阅读各种书籍、文章更加贴近本质。另外,JDK中的很多底层方法都是本地化( Native ) 的 ,需要跟踪这些方法的运作或对JDK进行Hack的时候 ,都需要自己编译一套JDK。\r\n\r\n现在网络上有不少开源的JDK实现可以供我们选择,如Apache Harmony、OpenJDK等。 考虑到Sun系列的JDK是现在使用得最广泛的JDK版 本 ,笔者选择了OpenJDK进行这次编译实战。', '37/2017-06-10_09-15-27-深入理解Java虚拟机[第一章实战] 编译Hotspot.html', '2017-06-10 08:33:44', '2017-06', '2017-06-10 09:15:27', '0');
INSERT INTO `blog` VALUES ('39', '深入理解Java虚拟机[第一章]  走进Java', 'admin', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497064610057&di=de420c8ffcbad17a287a914805eab45e&imgtype=0&src=http%3A%2F%2Fm2.app111.com%2FAppImg%2F160x160%2F2012%2F01%2F28%2F4862078961456197427622.jpg', '1', '41', '30', 'Java不仅仅是一门编程语言,还是一个由一系列计算机软件和规范形成的技术体系,这 个技术体系提供了完整的用于软件开发和跨平台部署的支持环境,并广泛应用于嵌入式系统、移动终端、企业服务器、大型机等各种场合,如图1-1所示。时至今日,Java技术体系已 经吸引了900多万软件开发者,这是全球最大的软件开发团队。使用Java的设备多达几十亿 台,其中包括11亿多台个人计算机、30亿部移动电话及其他手持设备、数量众多的智能卡, 以及大量机顶盒、导航系统和其他设备。', '29/2017-06-10_09-11-51-深入理解Java虚拟机[第一章]  走进Java.html', '2017-06-10 08:22:06', '2017-06', '2017-06-10 09:11:51', '0');
INSERT INTO `blog` VALUES ('40', '深入理解Java虚拟机[第二章] 内存区域', 'admin', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497064610057&di=de420c8ffcbad17a287a914805eab45e&imgtype=0&src=http%3A%2F%2Fm2.app111.com%2FAppImg%2F160x160%2F2012%2F01%2F28%2F4862078961456197427622.jpg', '1', '41', '30', '对于从事C、C++程序开发的开发人员来说,在内存管理领域,他们既是拥有最高权力的“皇帝”又是从事最基础工作的“劳动人民”—— 既拥有每一个对象的“所有权”,又担负着每一个对象生命开始到终结的维护责任。\r\n\r\n对于Java程序员来说,在虚拟机自动内存管理机制的帮助下,不再需要为每一个new操 作去写配对的delete/free代码 ,不容易出现内存泄漏和内存溢出问题,由虚拟机管理内存这—切看起来都很美好。不过,也正是因为Java程序员把内存控制的权力交给了Java虚拟机, 一旦出现内存泄漏和溢出方面的问题,如果不了解虚拟机是怎样使用内存的,那么排查错误将会成为一项异常艰难的工作。', '61/2017-06-10_09-14-00-深入理解Java虚拟机[第二章] 内存区域.html', '2017-06-10 09:14:00', '2017-06', '2017-06-10 09:14:00', '0');
INSERT INTO `blog` VALUES ('41', '深入理解Java虚拟机[第二章] HotSpot虚拟机对象', 'admin', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497064610057&di=de420c8ffcbad17a287a914805eab45e&imgtype=0&src=http%3A%2F%2Fm2.app111.com%2FAppImg%2F160x160%2F2012%2F01%2F28%2F4862078961456197427622.jpg', '1', '41', '30', '介绍完Java虚拟机的运行时数据区之后,我们大致知道了虚拟机内存的概况,读者了解了内存中放了些什么后,也许就会想更进一步了解这些虚拟机内存中的数据的其他细节,譬如它们是如何创建、如何布局以及如何访问的。对于这样涉及细节的问题,必须把讨论范围限定在具体的虚拟机和集中在某一个内存区域上才有意义。基于实用优先的原则,笔者以常 用的虚拟机HotSpot和常用的内存区域Java堆 为 例 ,深入探讨HotSpot虚拟机在Java堆中对象分配、布局和访问的全过程。', '12/2017-06-10_09-18-41-深入理解Java虚拟机[第二章] HotSpot虚拟机对象.html', '2017-06-10 09:17:33', '2017-06', '2017-06-10 09:18:41', '0');
INSERT INTO `blog` VALUES ('42', '深入理解Java虚拟机[第二章] 实战OOM', 'admin', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497064610057&di=de420c8ffcbad17a287a914805eab45e&imgtype=0&src=http%3A%2F%2Fm2.app111.com%2FAppImg%2F160x160%2F2012%2F01%2F28%2F4862078961456197427622.jpg', '1', '41', '30', '在Java虚拟机规范的描述中,除了程序计数器外,虚拟机内存的其他几个运行时区域都有发生OutOfMemoryError(下文称00M )异常的可能,本节将通过若干实例来验证异常发生的场景(代码清单2-3?代码清单2-9的几段简单代码),并且会初步介绍几个与内存相关的最基本的虚拟机参数。', '33/2017-06-10_09-50-16-深入理解Java虚拟机[第二章] 实战OOM.html', '2017-06-10 09:43:42', '2017-06', '2017-06-10 09:50:16', '0');
INSERT INTO `blog` VALUES ('43', 'eclipse C & CPP编译含有多个main函数的项目', 'admin', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497069626729&di=daa9e67bd4259d2684bb46d103d0d27a&imgtype=0&src=http%3A%2F%2Fb.hiphotos.baidu.com%2Fzhidao%2Fwh%253D450%252C600%2Fsign%3D8ed0d223242dd42a5f5c09af360b7783%2Fb21bb051f81986186f8068ce4bed2e738ad4e6b1.jpg', '1', '41', '30', '今天 闲的蛋疼的时候, 突然想起了使用eclipse构建C/ C++项目, 下载好mingw编译器之后, 创建了一个项目, 之后写了两个”类” [.. java用惯了], 但是 却发现了和visualStudio相同的问题, 一个项目中不能够存在多个”类” 同时存在main函数,,', '20/2017-06-10_09-58-02-eclipse C & CPP编译含有多个main函数的项目.html', '2017-06-10 09:52:50', '2017-06', '2017-06-10 09:58:02', '0');
INSERT INTO `blog` VALUES ('44', '关于Tomcat的pathInfo部分的编码', 'admin', 'http://tomcat.apache.org/images/tomcat.png', '1', '41', '30', '似乎是很久以前就看到这篇文章了吧,, \" http://www.xuebuyuan.com/1287083.html  \", 当时 也是直接了解了一下了一下自己的场景, 然后 解决了问题 就没有在管其中的一些细节了, 然后 今天似乎是突发奇想[哦 不对 似乎是在某群里看到了一个乱码问题], 就想走一走这个流程\r\n首先是走的第一个关于pathInfo的处理的流程,, 走完之后 发现了一些问题, 因此 分享一下, 这里没有什么其他意思, 仅仅是对于这个问题去看了一下相关的代码, 然后 写了一些关于自己的理解而已 \r\n因为个人水平有限, 因此 难免存在错误的地方, 因此 请大家多多指正', '8/2017-06-10_10-12-53-关于Tomcat的pathInfo部分的编码.html', '2017-06-10 10:12:53', '2017-06', '2017-06-10 10:12:53', '0');
INSERT INTO `blog` VALUES ('45', 'queryString 和postData 的编码', 'admin', 'http://tomcat.apache.org/images/tomcat.png', '1', '41', '30', 'tomcat 对于queryString 和postData 的的编码的处理', '16/2017-06-10_10-46-55-queryString 和postData 的编码.html', '2017-06-10 10:46:55', '2017-06', '2017-06-10 10:46:55', '0');
INSERT INTO `blog` VALUES ('46', 'header 部分的编码处理', 'admin', 'http://tomcat.apache.org/images/tomcat.png', '1', '41', '30', 'tomcat 对于header部分的编码的处理', '37/2017-06-10_10-52-37-header 部分的编码处理.html', '2017-06-10 10:52:37', '2017-06', '2017-06-10 10:52:37', '0');
INSERT INTO `blog` VALUES ('47', '关于HttpClient自动保存cookie', 'admin', 'http://www.apache.org/img/asf_logo.png', '1', '41', '30', '下面是我以前想做的一个专门为了HXBlog \"刷访问\" 的工具,, 当时 直接使用的我的HXCrawler进行发送请求, 但是 很遗憾失败了,, \r\n也就是 虽然我发送了\"requestTime\"个请求, 但是 该博客的\"访问次数\"依然仅仅增加了一次\r\n\r\n因为 不知道为何发送第一个请求之后, 后面的所有请求都带上了第一次请求所得到的sessionId, 但是 问题在于, 我这里是每一次 都新建了一个CrawlerConfig啊, 因此 每次发送的请求应该都没有什么关系啊, 为什么 会出现这种情况呢,,', '20/2017-06-10_11-25-16-关于HttpClient自动保存cookie.html', '2017-06-10 11:08:06', '2017-06', '2017-06-10 11:25:16', '0');
INSERT INTO `blog` VALUES ('48', '测试 bash 高亮', 'admin', 'http://pic.sdodo.com/icon/3/2.png', '1', '41', '30', '测试测试', '53/2017-06-10_11-33-57-测试 bash 高亮.html', '2017-06-10 11:33:57', '2017-06', '2017-06-10 11:36:32', '1');
INSERT INTO `blog` VALUES ('49', '关于快速排序', 'admin', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497078033615&di=59583540378fb0eacf9dcdd36a447a1f&imgtype=0&src=http%3A%2F%2Fwhathappen.today%2Fwp-content%2Fuploads%2F2014%2F10%2F20141017_54407c59d37c2.png', '1', '41', '30', '            昨天 和一位朋友交流的时候, 谈到了快速排序,, 因此 闲来无事, 回顾回顾快速排序,, 讲算法, 要说哪里讲得好,, 当然是算法导论咯, 不过 对于这本书 虽然是看过一遍, 但是 的的确确还是存在很多不懂的地方,, 呵呵 可能要看个7, 8遍才能有什么大的收获吧\r\n我认为 算法, 数据结构这种东西 估计是需要终生学习的吧, 毕竟相关的东西太多了', '50/2017-06-10_12-23-16-关于快速排序.html', '2017-06-10 12:17:22', '2017-06', '2017-06-10 12:23:16', '0');
INSERT INTO `blog` VALUES ('50', '记录一下我看过的设计模式', 'admin', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497078924003&di=e071f0b491f722171dcbd5025f9fc647&imgtype=0&src=http%3A%2F%2Fimages.csdn.net%2F20051129%2FGOF-OOPSLA-1994.jpg', '1', '41', '30', '闲的蛋疼 记录一下自己看过的设计模式', '37/2017-06-10_12-27-35-记录一下我看过的设计模式.html', '2017-06-10 12:27:35', '2017-06', '2017-06-10 12:27:35', '0');
INSERT INTO `blog` VALUES ('51', 'HXAttrHandler', 'admin', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497082133111&di=6de5abcbb3d1e4719c64e049057b622c&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F15%2F17%2F62%2F19Z58PICE5S_1024.jpg', '1', '41', '30', '主要是一个工具, 可以把一个符合\"规范\"[由作者约定]的表达式, 转换为一个AttrHandler对象,, 然后 就可以操作于不同的字符串, 从而得到对应的处理的结果\r\n举个例子 : 表达式为\"map(subString(1, sub(length, 1)) )\", 表示将传入的字符串进行map操作, 如何map是由\"map(\", \")\"之间部分进行定义的\r\n比如说 这里的是\"subString(1, sub(length, 1) )\", 意思 就是获取传入的字符串的1, length-1的子串, 如果存在IndexOutOfBounds, 会抛出该异常', '5/2017-06-10_13-23-17-HXAttrHandler.html', '2017-06-10 13:21:26', '2017-06', '2017-06-10 13:23:17', '0');
INSERT INTO `blog` VALUES ('52', '关于动态数据源的配置引发的问题', 'admin', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497087665896&di=5703c320843821bcd2cec7b8f2568a13&imgtype=0&src=http%3A%2F%2Fimage.lxway.com%2Fupload%2F3%2F4a%2F34af412918d7400d482088a1ee447c9f_thumb.jpg', '1', '41', '30', '今天在看erp项目的时候, 碰到了查询不了的问题, 报的错误是 \"找不到ds0.tableName\", 然而 我需要查询的表示\"ds1.tableName\", 那么 为什么会查询\"ds0.tableName\"呢, 这就是 本文需要探索的的东西了\r\n首先 这个问题之前也碰到过,, 本来想问朋友的, 但是 因为种种原因 现在问不了。。', '13/2017-06-10_14-54-09-关于动态数据源的配置引发的问题.html', '2017-06-10 13:28:11', '2017-06', '2017-06-10 14:54:09', '0');
INSERT INTO `blog` VALUES ('53', '取消卸载还要点确定', 'admin', 'https://ss3.baidu.com/9fo3dSag_xI4khGko9WTAnF6hhy/image/h%3D200/sign=43bce27e85025aafcc3279cbcbecab8d/562c11dfa9ec8a13f188f35ef003918fa1ecc0fa.jpg', '1', '43', '30', '百度云管家取消卸载还要点确定', '33/2017-06-10_13-42-03-取消卸载还要点确定.html', '2017-06-10 13:23:00', '2017-06', '2017-06-10 13:42:03', '0');
INSERT INTO `blog` VALUES ('54', '并行的bfs', 'admin', 'http://img05.tooopen.com/images/20150528/tooopen_sy_126737277876.jpg', '1', '43', '30', '新增一张gif', '41/2017-06-10_13-52-20-并行的bfs.html', '2017-06-10 13:52:20', '2017-06', '2017-06-10 13:52:20', '0');
INSERT INTO `blog` VALUES ('55', '关于xx-net的配置', 'admin', 'https://avatars2.githubusercontent.com/u/10395542?v=3&s=200', '1', '41', '30', '关于 这个官方已经有很详细的说明了,, \r\nxx-net : https://github.com/XX-net/XX-Net \r\n首先大概是有两种代理管理方式\r\n第一是通过全局PAC智能管理, 第二 是取消全局管理', '20/2017-06-10_13-55-25-关于xx-net的配置.html', '2017-06-10 13:55:25', '2017-06', '2017-06-10 13:55:25', '0');
INSERT INTO `blog` VALUES ('56', 'idea 配置, 部署eclipse web项目', 'admin', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497084784278&di=a11ec382e83ea288471a61acfd4256c0&imgtype=0&src=http%3A%2F%2Fimg.25pp.com%2Fuploadfile%2Fapp%2Ficon%2F20160322%2F1458609327533708.jpg', '1', '41', '30', '最近的时候, 有机会试用了一下ultimate版本的idea[之前一直使用的是免费的community版本], 也尝试了在上面配置服务器, 部署了一下web项目\r\n然后尝试将HelloServlet部署到idea的tomcat上面,, 我的HelloServlet项目是eclipse创建的web项目, 因此 少了一些idea web项目所需的配置, 因此 上次尝试了很久都没有部署HelloServlet成功,, ', '19/2017-06-10_14-05-13-idea 配置, 部署eclipse web项目.html', '2017-06-10 14:05:13', '2017-06', '2017-06-10 14:05:13', '0');
INSERT INTO `blog` VALUES ('57', 'ubuntu 关闭图形化界面, 配置ip网卡', 'admin', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497084983812&di=d13023d077fb2da7139713f89331692a&imgtype=0&src=http%3A%2F%2Fpic11.nipic.com%2F20101109%2F6032425_110031098924_2.jpg', '1', '41', '30', 'ubuntu 关闭图形化界面,  配置ip网卡', '48/2017-06-10_14-08-34-ubuntu 关闭图形化界面, 配置ip网卡.html', '2017-06-10 14:08:34', '2017-06', '2017-06-10 14:08:34', '0');
INSERT INTO `blog` VALUES ('58', '关于netbeans7.0.1, c,cpp的安装', 'admin', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497085195962&di=2b71980a6b4a810c70aa4ef8dc77eb1d&imgtype=0&src=http%3A%2F%2Flogonoid.com%2Fimages%2Fnetbeans-logo.png', '1', '41', '30', '今天 安装netbeans的时候, 出现了不能启动, 以及闪退等等一系列的问题, 因此记录一下\r\n首先介绍一下环境 : ubuntu12.04 + jdkXX[变化] + netbeans7.0.1', '51/2017-06-10_14-12-07-关于netbeans7.0.1, c,cpp的安装.html', '2017-06-10 14:12:07', '2017-06', '2017-06-10 14:12:07', '0');
INSERT INTO `blog` VALUES ('59', '关于百度的验证, 收不到短信怎么办', 'admin', 'https://ss3.baidu.com/9fo3dSag_xI4khGko9WTAnF6hhy/image/h%3D200/sign=43bce27e85025aafcc3279cbcbecab8d/562c11dfa9ec8a13f188f35ef003918fa1ecc0fa.jpg', '1', '43', '30', '验证方式', '20/2017-06-10_14-14-53-关于百度的验证, 收不到短信怎么办.html', '2017-06-10 14:14:53', '2017-06', '2017-06-10 14:14:53', '0');
INSERT INTO `blog` VALUES ('60', '关于Quartz的Blocked的状态', 'admin', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497085639933&di=e887979ff8e71fac666a187186043f09&imgtype=0&src=http%3A%2F%2Finfo.53dns.com%2FUploadFiles%2F2011-03%2Fadmin%2F20110330120344445.jpg', '1', '41', '30', '这周小白哥不是说项目中存在一个quartz的数据库的trigger表的两个任务为blocked的状态嘛, 然后 分配了调度的这个任务, 当然 这里与这个任务没有太大的关系, 主要是 说一说quartz的block的这个状态[这里主要是以RAMJobStore为例]', '39/2017-06-10_14-19-39-关于Quartz的Blocked的状态.html', '2017-06-10 14:19:39', '2017-06', '2017-06-10 14:19:39', '0');
INSERT INTO `blog` VALUES ('61', 'Quartz 调度任务的流程', 'admin', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497085639933&di=e887979ff8e71fac666a187186043f09&imgtype=0&src=http%3A%2F%2Finfo.53dns.com%2FUploadFiles%2F2011-03%2Fadmin%2F20110330120344445.jpg', '1', '41', '30', '这周小白哥不是说项目中存在一个quartz的数据库的trigger表的两个任务为blocked的状态嘛, 然后 分配了调度的这个任务, 当然 这里与这个任务没有太大的关系, 主要是 说一说quartz的block的这个状态[这里主要是以RAMJobStore为例]', '35/2017-06-10_14-24-29-Quartz 调度任务的流程.html', '2017-06-10 14:21:58', '2017-06', '2017-06-10 14:24:29', '0');
INSERT INTO `blog` VALUES ('62', '[转]mysql 常用的优化技巧', 'admin', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497086433943&di=ef29225db5cd10114aea3ceea985e202&imgtype=0&src=http%3A%2F%2Fimages.liqucn.com%2Fh018%2Fh88%2Fimg201411221428360219_info300X300.png', '3', '41', '30', 'MySQL数据库是常见的两个瓶颈是CPU和I/O的瓶颈，CPU在饱和的时候一般发生在数据装入内存或从磁盘上读取数据时候。磁盘I/O瓶颈发生在装入数据远大于内存容量的时候，如果应用分布在网络上，那么查询量相当大的时候那么平瓶颈就会出现在网络上，我们可以用mpstat, iostat, sar和vmstat来查看系统的性能状态。', '22/2017-06-30_19-16-10-[转]mysql 常用的优化技巧.html', '2017-06-10 14:28:28', '2017-06', '2017-06-30 19:20:58', '0');
INSERT INTO `blog` VALUES ('63', '[转]Mysql 锁机制', 'admin', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497086433943&di=ef29225db5cd10114aea3ceea985e202&imgtype=0&src=http%3A%2F%2Fimages.liqucn.com%2Fh018%2Fh88%2Fimg201411221428360219_info300X300.png', '3', '41', '30', '锁是计算机协调多个进程或线程并发访问某一资源的机制。在数据库中，除传统的计算资源（如CPU、RAM、I/O等）的争用以外，数据也是一种供许多用户共享的资源。如何保证数据并发访问的一致性、有效性是所有数据库必须解决的一个问题，锁冲突也是影响数据库并发访问性能的一个重要因素。从这个角度来说，锁对数据库而言显得尤其重要，也更加复杂。', '24/2017-06-30_19-15-55-[转]Mysql 锁机制.html', '2017-06-10 14:40:26', '2017-06', '2017-06-30 19:20:44', '0');
INSERT INTO `blog` VALUES ('64', '关于SpringSession', 'admin', 'https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1594940755,55583916&fm=26&gp=0.jpg', '1', '41', '30', '简单的介绍了一下 SpringSession 的流程', '42/2017-06-10_14-47-18-关于SpringSession.html', '2017-06-10 14:46:49', '2017-06', '2017-06-10 14:47:18', '0');
INSERT INTO `blog` VALUES ('65', 'SpringMVC 解析不到模板', 'admin', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497087665896&di=5703c320843821bcd2cec7b8f2568a13&imgtype=0&src=http%3A%2F%2Fimage.lxway.com%2Fupload%2F3%2F4a%2F34af412918d7400d482088a1ee447c9f_thumb.jpg', '1', '41', '30', 'Could not resolve view with name Test01Redirect2Ftl.ftl in servlet with name springMVC', '1/2017-06-10_14-53-39-SpringMVC 解析不到模板.html', '2017-06-10 14:53:39', '2017-06', '2017-06-10 14:53:39', '0');
INSERT INTO `blog` VALUES ('66', 'Nexus 的搭建使用记录', 'admin', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497682567&di=aa59536843b0e24db75fd56a844a9a03&imgtype=jpg&er=1&src=http%3A%2F%2Fbillcprice.com%2Ffutureimperfect%2Fwp-content%2Fuploads%2F2013%2F11%2Fgoogle-nexus-logo.jpg', '1', '41', '30', 'Nexus的使用笔记\r\n更加详细的文档可以参见官方文档[http://books.sonatype.com/nexus-book/reference3/install.html#config-data-directory]\r\n首先从官网下载linux对应的包[https://www.sonatype.com/download-oss-sonatype], 一个tar.gz包', '55/2017-06-10_14-56-24-Nexus 的搭建使用记录.html', '2017-06-10 14:56:24', '2017-06', '2017-06-10 14:56:24', '0');
INSERT INTO `blog` VALUES ('67', 'Jenkins 的搭建使用记录', 'admin', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497087950208&di=20f04abe46a9658560704a4c2c8f88fe&imgtype=0&src=http%3A%2F%2Fwww.bleum.com%2Fwp-content%2Fuploads%2F2016%2F06%2Fomnipresent_jenkins_logo_1-217x300.png', '1', '41', '30', 'jenkins的安装\r\n从官网下载最新的war包, 然后 部署到服务器上面就行了\r\nhttp://mirrors.jenkins-ci.org/war-stable/2.19.2/', '47/2017-06-10_14-57-59-Jenkins 的搭建使用记录.html', '2017-06-10 14:57:59', '2017-06', '2017-06-10 14:57:59', '0');
INSERT INTO `blog` VALUES ('68', '一个基于Redis SortedSet的MostUsed缓存', 'admin', 'https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1594940755,55583916&fm=26&gp=0.jpg', '1', '41', '30', '这个\"容器\"[我称之为]是我之前做东西的时候, 遇到了一个统计pv的问题, 数据记录如下 : id, productId, date, pageVisited\r\n每天记录下给定的产品页面被访问的次数, 对于这个问题, 我们首先将其简单化一点, 去掉date, 试想我们现在需要统计一个产品从被创建到现在的访问次数, 那么 我们应该怎么做呢?\r\n最简单的思路 是在获取产品详情的接口做一个拦截, 然后 进行处理, 每一次访问给定的产品, 从数据库中拉出给定的产品的记录, 然后pageVisited+1, 然后在持久化回去', '10/2017-06-10_15-05-09-一个基于Redis SortedSet的MostUsed缓存.html', '2017-06-10 15:05:09', '2017-06', '2017-06-10 15:05:09', '0');
INSERT INTO `blog` VALUES ('69', '基于Redis的SessionManager', 'admin', 'https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1594940755,55583916&fm=26&gp=0.jpg', '1', '41', '30', '这个需求这样的, 我们做一个android的app, 然后 登录使用的是android原生的东西, 然后 之后其他的东西, 是使用html, css, jss来处理的, 具体的细节我也不清楚, 然后 问题是前端拿到sessionId之后, 似乎是由于js的限定, 发送请求的时候, 不能把sessionId放到Cookie头中[具体我没有看, 但是前端大佬过来说, 是这样],, 然后 这样就不能使用服务器自己的session机制了,, 因此 需要自己\"实现\"一套\r\n然后 自己基于redis自己写了一下, 虽然 没有使用我这个..', '63/2017-06-10_15-08-26-基于Redis的SessionManager.html', '2017-06-10 15:08:26', '2017-06', '2017-06-10 15:08:26', '0');
INSERT INTO `blog` VALUES ('70', '使用Wrapper来解决一个头疼问题', 'admin', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497078924003&di=e071f0b491f722171dcbd5025f9fc647&imgtype=0&src=http%3A%2F%2Fimages.csdn.net%2F20051129%2FGOF-OOPSLA-1994.jpg', '1', '41', '30', '​问题是这样的, 现在有两个系统, 一个是与app交互的后台系统, session机制是自己写的, 使用sid的header作为认证的方式\r\n另外一个后台系统, 此系统使用的session是服务器的session, 在某些场景下面 需要调用前一个系统的一些接口', '11/2017-06-10_15-11-36-使用Wrapper来解决一个头疼问题.html', '2017-06-10 15:11:36', '2017-06', '2017-06-10 15:11:36', '0');
INSERT INTO `blog` VALUES ('71', 'HXFlow', 'admin', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497088948165&di=2d38cea286240f1de4d9085f88218335&imgtype=0&src=http%3A%2F%2Ff.fengguo.com.cn%2Fuf%2Fopus%2F09%2F04%2F14%2F40503_113449_0xp3_618.jpg', '1', '41', '30', '​因为 公司之前是准备做一个项目, 项目中涉及了很多业务流程, 然后 公司是准备使用开源的工作流引擎来处理其中的业务流程, 然后 这个HXFlow是我对于工作流的自己的理解, 然后 自己将其实现了一下.., 我们这里只说一下思路\r\n当然 这个也算是在扯淡吧, 有很多开源的相关的东西[activiti 等等], 在实际工作中, 我们差不多是只需要查查资料 使用就行了', '39/2017-06-10_15-14-45-HXFlow.html', '2017-06-10 15:14:45', '2017-06', '2017-06-10 15:14:45', '0');
INSERT INTO `blog` VALUES ('72', '一个基于HXFlow的Demo', 'admin', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497088948165&di=2d38cea286240f1de4d9085f88218335&imgtype=0&src=http%3A%2F%2Ff.fengguo.com.cn%2Fuf%2Fopus%2F09%2F04%2F14%2F40503_113449_0xp3_618.jpg', '1', '41', '30', '​这是一个基于HXFlow的小demo', '2/2017-06-10_15-17-59-一个基于HXFlow的Demo.html', '2017-06-10 15:17:59', '2017-06', '2017-06-10 15:17:59', '0');
INSERT INTO `blog` VALUES ('73', 'UserGroupInformation_doAs', 'admin', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497089359233&di=6fabc68561c0ee59ec660ad390d3de87&imgtype=0&src=http%3A%2F%2Fwww.thebigdata.cn%2Fupload%2F2016-10%2F161021100720395.jpg', '1', '41', '30', '前几天搭建Spark集群的时候, 然后跑WordCount的时候碰到了这样的一个问题\r\n集群启动成功之后, 向集群提交任务, 然后 driver程序跑到需要taskScheduler为taskSet分配资源的时候, 找不到可用的executor\r\n然后 导致driver程序这边任务执行不了, 直到超时爆出了异常, 然后只好 shutdown driver程序,', '5/2017-06-10_15-21-29-UserGroupInformation_doAs.html', '2017-06-10 15:21:29', '2017-06', '2017-06-10 15:21:29', '0');
INSERT INTO `blog` VALUES ('74', '如何光明正大的导入Unsafe', 'admin', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1496576208623&di=d160a80ed1f560fe173bd5a029514d5c&imgtype=0&src=http%3A%2F%2Fimg9.3lian.com%2Fc1%2Fvector2%2F03%2F07%2F03.jpg', '1', '41', '30', 'sun.misc.Unsafe 这个类 包含了很多\"底层\"的方法, 在并发包中使用非常频繁\r\n然后 曾经很久之前, 我也曾尝试使用这个工具, 然后 因为他是package权限的, 因此 当时import之后会报错, 然后 需要修改eclipse的编译配置, 才能使之\"正常\"', '20/2017-06-10_15-23-05-如何光明正大的导入Unsafe.html', '2017-06-10 15:23:05', '2017-06', '2017-06-10 15:23:05', '0');
INSERT INTO `blog` VALUES ('75', '1', 'admin', '2', '1', '43', '30', '            1', '1/2017-06-11_00-46-42-1.html', '2017-06-11 00:46:42', '2017-06', '2017-06-11 00:47:10', '1');
INSERT INTO `blog` VALUES ('76', '1', 'admin', '1', '1', '43', '30', '            1', '8/2017-06-11_00-53-12-1.html', '2017-06-11 00:53:12', '2017-06', '2017-06-11 00:53:24', '1');
INSERT INTO `blog` VALUES ('77', 'idea_maven 下载不了代码', 'admin', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497755479&di=0b5323aaac77fa18a0aacf87fff019a7&imgtype=jpg&er=1&src=http%3A%2F%2Fwww.oschina.net%2Fuploads%2Fimg%2F201004%2F28121027_46LP.png', '1', '41', '30', '今天的饿时候, 想看看某个包的代码, 然后 使用maven来下载代码 然后 结果显示是source not found, \r\n然后 我就很疑惑了, 为嘛 堂堂maven仓库还找不到代码了,, \r\n然后 之后的饿时候, 因为 有事情, 所以 就临时看了一下反编译之后的结果', '32/2017-06-11_11-12-07-idea_maven 下载不了代码.html', '2017-06-11 11:12:07', '2017-06', '2017-06-11 11:12:07', '0');
INSERT INTO `blog` VALUES ('78', '1', 'admin', '2', '1', '43', '30', '11', '5/2017-06-11_14-49-24-1.html', '2017-06-11 14:49:24', '2017-06', '2017-06-11 14:49:40', '1');
INSERT INTO `blog` VALUES ('79', 'Select2 多选框问题', 'admin', 'https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2423914498,3360693256&fm=26&gp=0.jpg', '1', '44', '30', '下面两个弹出框, 对应于一个实体的增加, 删除, 二者是通过弹出层框架弹出来的\r\n出现问题之后, 我就拿出了文本比较工具, 来比较两个form[包含上面的多选框代码片]的不同之处, 发现二者 没有什么不同的, 然后 做了一些其他的尝试啊[删除掉增加的弹出框中的这个select, 等等]什么的, 都没有见效\r\n然后 找了一下前端的朋友, 来瞅瞅这个问题, 因为 问题比较奇葩, 因此 建议我重新找一个框架, 然后 我也准备重新找一个了, \r\n', '9/2017-06-12_22-32-20-Select2 多选框问题.html', '2017-06-12 22:32:20', '2017-06', '2017-06-12 22:32:20', '0');
INSERT INTO `blog` VALUES ('80', '1', 'admin', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498316223388&di=70e8a99c95c182aa9b886fd22e40f76d&imgtype=0&src=http%3A%2F%2Fwww.qh.xinhuanet.com%2F1114644310_14264052762281n.jpg', '1', '43', '30', '111', '37/2017-06-24_20-35-39-1.html', '2017-06-24 20:09:36', '2017-06', '2017-06-24 21:56:41', '1');
INSERT INTO `blog` VALUES ('81', '关于快速排序', 'admin', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497078033615&di=59583540378fb0eacf9dcdd36a447a1f&imgtype=0&src=http%3A%2F%2Fwhathappen.today%2Fwp-content%2Fuploads%2F2014%2F10%2F20141017_54407c59d37c2.png', '1', '41', '30', '昨天 和一位朋友交流的时候[写于2016.09.10, 暂存于HXBlog], 谈到了快速排序,, 因此 闲来无事, 回顾回顾快速排序,, 讲算法, 要说哪里讲得好,, 当然是算法导论咯, 不过 对于这本书 虽然是看过一遍, 但是 的的确确还是存在很多不懂的地方,, 呵呵 可能要看个7, 8遍才能有什么大的收获吧\r\n\r\n我认为 算法, 数据结构这种东西 估计是需要终生学习的吧, 毕竟相关的东西太多了\r\n\r\n先来几张 算法导论第三版第七章[“汉化版”] 的关于快速排序的描述 以及图解分析 \r\n以下图片来自于算法导论[红线为我画的,, 请忽略][我一画上去是不是感觉数都被我毁了 …]', '57/2017-06-25_11-11-13-关于快速排序.html', '2017-06-25 11:11:13', '2017-06', '2017-06-25 11:44:09', '1');
INSERT INTO `blog` VALUES ('82', '关于快速排序', 'admin', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497078033615&di=59583540378fb0eacf9dcdd36a447a1f&imgtype=0&src=http%3A%2F%2Fwhathappen.today%2Fwp-content%2Fuploads%2F2014%2F10%2F20141017_54407c59d37c2.png', '1', '41', '30', '昨天 和一位朋友交流的时候[写于2016.09.10, 暂存于HXBlog], 谈到了快速排序,, 因此 闲来无事, 回顾回顾快速排序,, 讲算法, 要说哪里讲得好,, 当然是算法导论咯, 不过 对于这本书 虽然是看过一遍, 但是 的的确确还是存在很多不懂的地方,, 呵呵 可能要看个7, 8遍才能有什么大的收获吧\r\n\r\n我认为 算法, 数据结构这种东西 估计是需要终生学习的吧, 毕竟相关的东西太多了\r\n\r\n先来几张 算法导论第三版第七章[“汉化版”] 的关于快速排序的描述 以及图解分析 \r\n以下图片来自于算法导论[红线为我画的,, 请忽略][我一画上去是不是感觉数都被我毁了 …]', '0/2017-06-25_11-25-20-关于快速排序.html', '2017-06-25 11:25:20', '2017-06', '2017-06-25 11:44:02', '1');
INSERT INTO `blog` VALUES ('83', '关于快速排序', 'admin', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497078033615&di=59583540378fb0eacf9dcdd36a447a1f&imgtype=0&src=http%3A%2F%2Fwhathappen.today%2Fwp-content%2Fuploads%2F2014%2F10%2F20141017_54407c59d37c2.png', '1', '41', '30', '昨天 和一位朋友交流的时候[写于2016.09.10, 暂存于HXBlog], 谈到了快速排序,, 因此 闲来无事, 回顾回顾快速排序,, 讲算法, 要说哪里讲得好,, 当然是算法导论咯, 不过 对于这本书 虽然是看过一遍, 但是 的的确确还是存在很多不懂的地方,, 呵呵 可能要看个7, 8遍才能有什么大的收获吧\r\n\r\n我认为 算法, 数据结构这种东西 估计是需要终生学习的吧, 毕竟相关的东西太多了\r\n\r\n先来几张 算法导论第三版第七章[“汉化版”] 的关于快速排序的描述 以及图解分析 \r\n以下图片来自于算法导论[红线为我画的,, 请忽略][我一画上去是不是感觉数都被我毁了 …]', '26/2017-06-25_11-25-59-关于快速排序.html', '2017-06-25 11:25:59', '2017-06', '2017-06-25 11:43:06', '1');
INSERT INTO `blog` VALUES ('84', '关于快速排序', 'admin', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497078033615&di=59583540378fb0eacf9dcdd36a447a1f&imgtype=0&src=http%3A%2F%2Fwhathappen.today%2Fwp-content%2Fuploads%2F2014%2F10%2F20141017_54407c59d37c2.png', '1', '41', '30', '昨天 和一位朋友交流的时候[写于2016.09.10, 暂存于HXBlog], 谈到了快速排序,, 因此 闲来无事, 回顾回顾快速排序,, 讲算法, 要说哪里讲得好,, 当然是算法导论咯, 不过 对于这本书 虽然是看过一遍, 但是 的的确确还是存在很多不懂的地方,, 呵呵 可能要看个7, 8遍才能有什么大的收获吧\r\n\r\n我认为 算法, 数据结构这种东西 估计是需要终生学习的吧, 毕竟相关的东西太多了\r\n\r\n先来几张 算法导论第三版第七章[“汉化版”] 的关于快速排序的描述 以及图解分析 \r\n以下图片来自于算法导论[红线为我画的,, 请忽略][我一画上去是不是感觉数都被我毁了 …]', '59/2017-06-25_11-28-06-关于快速排序.html', '2017-06-25 11:28:06', '2017-06', '2017-06-25 11:42:49', '1');
INSERT INTO `blog` VALUES ('85', '1', 'admin', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497078033615&di=59583540378fb0eacf9dcdd36a447a1f&imgtype=0&src=http%3A%2F%2Fwhathappen.today%2Fwp-content%2Fuploads%2F2014%2F10%2F20141017_54407c59d37c2.png', '1', '43', '30', '111', '32/2017-06-25_11-38-47-1.html', '2017-06-25 11:38:47', '2017-06', '2017-06-25 11:39:16', '1');
INSERT INTO `blog` VALUES ('86', '11', 'admin', 'https://gss3.bdstatic.com/84oSdTum2Q5BphGlnYG/timg?wapp&quality=80&size=b150_150&subsize=20480&cut_x=0&cut_w=0&cut_y=0&cut_h=0&sec=1369815402&srctrace&di=be0d8d07379b7403d93afd8b05e957e2&wh_rate=null&src=http%3A%2F%2Fimgsrc.baidu.com%2Fforum%2Fpic%2Fitem%2Fa9d3fd1f4134970a9b762c7c94cad1c8a7865d5f.jpg', '1', '43', '30', '111', '51/2017-06-26_21-56-10-11.html', '2017-06-26 21:56:10', '2017-06', '2017-06-27 20:35:46', '1');
INSERT INTO `blog` VALUES ('87', '11', 'admin', 'https://gss3.bdstatic.com/84oSdTum2Q5BphGlnYG/timg?wapp&quality=80&size=b150_150&subsize=20480&cut_x=0&cut_w=0&cut_y=0&cut_h=0&sec=1369815402&srctrace&di=be0d8d07379b7403d93afd8b05e957e2&wh_rate=null&src=http%3A%2F%2Fimgsrc.baidu.com%2Fforum%2Fpic%2Fitem%2Fa9d3fd1f4134970a9b762c7c94cad1c8a7865d5f.jpg', '1', '43', '30', '111', '15/2017-06-26_22-01-13-11.html', '2017-06-26 22:01:13', '2017-06', '2017-06-27 20:35:43', '1');
INSERT INTO `blog` VALUES ('88', '11', 'admin', 'https://gss3.bdstatic.com/84oSdTum2Q5BphGlnYG/timg?wapp&quality=80&size=b150_150&subsize=20480&cut_x=0&cut_w=0&cut_y=0&cut_h=0&sec=1369815402&srctrace&di=be0d8d07379b7403d93afd8b05e957e2&wh_rate=null&src=http%3A%2F%2Fimgsrc.baidu.com%2Fforum%2Fpic%2Fitem%2Fa9d3fd1f4134970a9b762c7c94cad1c8a7865d5f.jpg', '1', '43', '30', '111', '44/2017-06-26_22-01-38-11.html', '2017-06-26 22:01:38', '2017-06', '2017-06-27 20:35:40', '1');
INSERT INTO `blog` VALUES ('89', '11', 'admin', 'https://gss3.bdstatic.com/84oSdTum2Q5BphGlnYG/timg?wapp&quality=80&size=b150_150&subsize=20480&cut_x=0&cut_w=0&cut_y=0&cut_h=0&sec=1369815402&srctrace&di=be0d8d07379b7403d93afd8b05e957e2&wh_rate=null&src=http%3A%2F%2Fimgsrc.baidu.com%2Fforum%2Fpic%2Fitem%2Fa9d3fd1f4134970a9b762c7c94cad1c8a7865d5f.jpg', '1', '43', '30', '111', '50/2017-06-26_22-04-24-11.html', '2017-06-26 22:04:24', '2017-06', '2017-06-27 20:35:37', '1');
INSERT INTO `blog` VALUES ('90', 'csdn图片服务器', 'admin', 'https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2190964529,967274460&fm=26&gp=0.jpg', '1', '43', '30', '哈哈哈哈哈哈', '54/2017-06-27_20-38-30-csdn图片服务器.html', '2017-06-27 20:38:30', '2017-06', '2017-06-27 20:39:38', '1');
INSERT INTO `blog` VALUES ('91', 'csdn图片服务器', 'admin', 'https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2190964529,967274460&fm=26&gp=0.jpg', '1', '43', '30', '哈哈哈哈哈哈', '62/2017-06-27_21-13-45-csdn图片服务器.html', '2017-06-27 20:38:54', '2017-06', '2017-06-27 21:13:45', '0');
INSERT INTO `blog` VALUES ('92', 'csdn图片服务器', 'admin', 'https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2190964529,967274460&fm=26&gp=0.jpg', '1', '43', '30', '哈哈哈哈哈哈', '58/2017-06-27_20-58-54-csdn图片服务器.html', '2017-06-27 20:58:54', '2017-06', '2017-06-27 21:08:48', '1');
INSERT INTO `blog` VALUES ('93', 'csdn图片服务器', 'admin', 'https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2190964529,967274460&fm=26&gp=0.jpg', '1', '43', '30', '哈哈哈哈哈哈', '36/2017-06-27_21-00-05-csdn图片服务器.html', '2017-06-27 21:00:05', '2017-06', '2017-06-27 21:08:45', '1');
INSERT INTO `blog` VALUES ('94', 'csdn图片服务器', 'admin', 'https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2190964529,967274460&fm=26&gp=0.jpg', '1', '43', '30', '哈哈哈哈哈哈', '2/2017-06-27_21-01-48-csdn图片服务器.html', '2017-06-27 21:01:48', '2017-06', '2017-06-27 21:08:42', '1');
INSERT INTO `blog` VALUES ('95', 'csdn图片服务器', 'admin', 'https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2190964529,967274460&fm=26&gp=0.jpg', '1', '43', '30', '哈哈哈哈哈哈', '28/2017-06-27_21-04-45-csdn图片服务器.html', '2017-06-27 21:04:45', '2017-06', '2017-06-27 21:08:37', '1');
INSERT INTO `blog` VALUES ('96', 'csdn图片服务器', 'admin', 'https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2190964529,967274460&fm=26&gp=0.jpg', '1', '43', '30', '哈哈哈哈哈哈', '55/2017-06-27_21-05-41-csdn图片服务器.html', '2017-06-27 21:05:41', '2017-06', '2017-06-27 21:08:34', '1');
INSERT INTO `blog` VALUES ('97', 'csdn图片服务器', 'admin', 'https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2190964529,967274460&fm=26&gp=0.jpg', '1', '43', '30', '哈哈哈哈哈哈', '21/2017-06-27_21-07-52-csdn图片服务器.html', '2017-06-27 21:07:52', '2017-06', '2017-06-27 21:08:31', '1');
INSERT INTO `blog` VALUES ('98', '关之琳大美女', 'admin', 'http://inews.gtimg.com/newsapp_match/0/1710329813/0', '1', '43', '30', '都说香港人只认两大美人，我猜不能没有关之琳', '50/2017-06-30_19-15-15-关之琳大美女.html', '2017-06-27 21:34:09', '2017-06', '2017-06-30 19:20:47', '0');
INSERT INTO `blog` VALUES ('99', '关之琳大美女', 'admin', 'http://imgsrc.baidu.com/baike/pic/item/8ad4b31c8701a18b59c116889b2f07082938fecb.jpg', '1', '43', '30', '都说香港人只认两大美人，我猜不能没有关之琳', '6/2017-06-27_21-34-20-关之琳大美女.html', '2017-06-27 21:34:20', '2017-06', '2017-06-27 21:36:19', '1');
INSERT INTO `blog` VALUES ('100', '11', 'admin', 'https://gss3.bdstatic.com/84oSdTum2Q5BphGlnYG/timg?wapp&quality=80&size=b150_150&subsize=20480&cut_x=0&cut_w=0&cut_y=0&cut_h=0&sec=1369815402&srctrace&di=be0d8d07379b7403d93afd8b05e957e2&wh_rate=null&src=http%3A%2F%2Fimgsrc.baidu.com%2Fforum%2Fpic%2Fitem%2Fa9d3fd1f4134970a9b762c7c94cad1c8a7865d5f.jpg', '1', '43', '30', '1111', '21/2017-06-28_21-25-12-11.html', '2017-06-28 21:25:12', '2017-06', '2017-06-29 21:27:26', '1');
INSERT INTO `blog` VALUES ('101', '13', 'admin', 'https://gss3.bdstatic.com/84oSdTum2Q5BphGlnYG/timg?wapp&quality=80&size=b150_150&subsize=20480&cut_x=0&cut_w=0&cut_y=0&cut_h=0&sec=1369815402&srctrace&di=be0d8d07379b7403d93afd8b05e957e2&wh_rate=null&src=http%3A%2F%2Fimgsrc.baidu.com%2Fforum%2Fpic%2Fitem%2Fa9d3fd1f4134970a9b762c7c94cad1c8a7865d5f.jpg', '1', '43', '30', '1312', '58/2017-06-29_21-06-35-13.html', '2017-06-29 20:41:58', '2017-06', '2017-06-29 21:26:55', '1');
INSERT INTO `blog` VALUES ('102', '111', 'admin', 'http://s6.sinaimg.cn/middle/49730edaha5b930d79345&690&690', '1', '43', '30', '1111', '35/2017-07-16_09-09-24-111.html', '2017-07-16 09:09:24', '2017-07', '2017-07-16 09:09:44', '1');
INSERT INTO `blog` VALUES ('103', '你好', 'admin', 'http://192.168.0.190:8888/HXBlog_V2.0/files/2017/12/02/E7BF216EFB5C761D71BAE2F848247F4D.png', '1', '43', '30', '你好你好', '44/2018-03-03_15-17-39-你好.html', '2018-03-03 14:52:52', '2018-03', '2018-03-03 15:19:16', '0');

-- ----------------------------
-- Table structure for blog_comment
-- ----------------------------
DROP TABLE IF EXISTS `blog_comment`;
CREATE TABLE `blog_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `blog_id` int(11) DEFAULT NULL COMMENT '关联的 blogId',
  `floor_id` int(11) DEFAULT NULL COMMENT '评论的层数',
  `comment_id` int(11) DEFAULT NULL COMMENT '给定的层的评论的评论id',
  `parent_comment_id` int(11) DEFAULT '-1' COMMENT '父级评论的id',
  `name` varchar(256) DEFAULT NULL COMMENT '评论的用户的姓名',
  `email` varchar(60) DEFAULT NULL COMMENT '评论的用户的邮箱',
  `head_img_url` varchar(2048) DEFAULT NULL COMMENT '评论的用户的头像',
  `to_user` varchar(256) DEFAULT NULL COMMENT '评论的对象',
  `role` varchar(60) DEFAULT NULL COMMENT '评论的用户的角色',
  `comment` varchar(2048) DEFAULT NULL COMMENT '评论的内容',
  `created_at` varchar(32) DEFAULT NULL COMMENT '创建时间',
  `updated_at` varchar(32) DEFAULT NULL COMMENT '修改时间',
  `deleted` tinyint(1) DEFAULT NULL COMMENT '是否被删除',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_2` (`blog_id`),
  CONSTRAINT `FK_Reference_2` FOREIGN KEY (`blog_id`) REFERENCES `blog` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=229 DEFAULT CHARSET=utf8 COMMENT='博客评论表';

-- ----------------------------
-- Records of blog_comment
-- ----------------------------
INSERT INTO `blog_comment` VALUES ('103', '27', '1', '1', '-1', 'admin', '970655147@qq.com', 'http://tb2.bdstatic.com/tb/editor/images/face/i_f25.png?t=20140803', 'admin', 'admin', '自己给自己赞一个&nbsp;<img class=\"emoji_icon\" src=\"/static/main/images/qq/45.gif\">', '2017-06-04 20:45:00', '2017-06-04 20:45:00', '0');
INSERT INTO `blog_comment` VALUES ('104', '27', '2', '1', '-1', 'admin', '970655147@qq.com', 'https://gss0.bdstatic.com/6LZ1dD3d1sgCo2Kml5_Y_D3/sys/portrait/item/48c0c0b6b7e7393730363535313437f02d', 'admin', 'admin', '<img class=\"emoji_icon\" src=\"/static/main/images/qq/32.gif\">&nbsp;换个头像 再来赞一个&nbsp;<img class=\"emoji_icon\" src=\"/static/main/images/qq/25.gif\">', '2017-06-04 20:45:32', '2017-06-04 20:45:32', '0');
INSERT INTO `blog_comment` VALUES ('105', '-2', '1', '1', '-1', 'admin', '970655147@qq.com', 'https://gss0.bdstatic.com/6LZ1dD3d1sgCo2Kml5_Y_D3/sys/portrait/item/48c0c0b6b7e7393730363535313437f02d', 'admin', 'admin', '多多给点建议哦&nbsp;<img class=\"emoji_icon\" src=\"/static/main/images/qq/6.gif\">', '2017-06-04 20:47:36', '2017-06-04 20:47:36', '0');
INSERT INTO `blog_comment` VALUES ('106', '27', '3', '1', '-1', '', '', 'http://avatar.csdn.net/C/D/C/1_u011039332.jpg', 'admin', 'guest', '11', '2017-06-05 20:13:27', '2017-06-05 20:13:27', '0');
INSERT INTO `blog_comment` VALUES ('107', '27', '4', '1', '-1', '', '', 'http://avatar.csdn.net/C/D/C/1_u011039332.jpg', 'admin', 'guest', '11', '2017-06-05 20:13:29', '2017-06-05 20:13:29', '0');
INSERT INTO `blog_comment` VALUES ('108', '27', '5', '1', '-1', '', '', 'http://avatar.csdn.net/C/D/C/1_u011039332.jpg', 'admin', 'guest', '11', '2017-06-05 20:13:30', '2017-06-05 20:13:30', '0');
INSERT INTO `blog_comment` VALUES ('109', '27', '6', '1', '-1', '', '', 'http://avatar.csdn.net/C/D/C/1_u011039332.jpg', 'admin', 'guest', '11', '2017-06-05 20:13:31', '2017-06-05 20:13:31', '0');
INSERT INTO `blog_comment` VALUES ('110', '27', '7', '1', '-1', '', '', 'http://avatar.csdn.net/C/D/C/1_u011039332.jpg', 'admin', 'guest', '11', '2017-06-05 20:13:32', '2017-06-05 20:13:32', '0');
INSERT INTO `blog_comment` VALUES ('111', '27', '8', '1', '-1', '', '', 'http://avatar.csdn.net/C/D/C/1_u011039332.jpg', 'admin', 'guest', '11', '2017-06-05 20:13:33', '2017-06-05 20:13:33', '0');
INSERT INTO `blog_comment` VALUES ('112', '27', '9', '1', '-1', '', '', 'http://avatar.csdn.net/C/D/C/1_u011039332.jpg', 'admin', 'guest', '2', '2017-06-05 20:13:33', '2017-06-05 20:13:33', '0');
INSERT INTO `blog_comment` VALUES ('113', '27', '10', '1', '-1', '', '', 'http://avatar.csdn.net/C/D/C/1_u011039332.jpg', 'admin', 'guest', '2', '2017-06-05 20:13:34', '2017-06-05 20:13:34', '0');
INSERT INTO `blog_comment` VALUES ('114', '27', '11', '1', '-1', '', '', 'http://avatar.csdn.net/C/D/C/1_u011039332.jpg', 'admin', 'guest', '1', '2017-06-05 20:13:35', '2017-06-05 20:13:35', '0');
INSERT INTO `blog_comment` VALUES ('115', '27', '12', '1', '-1', '', '', 'http://avatar.csdn.net/C/D/C/1_u011039332.jpg', 'admin', 'guest', '3', '2017-06-05 20:13:36', '2017-06-05 20:13:36', '0');
INSERT INTO `blog_comment` VALUES ('116', '27', '13', '1', '-1', '', '', 'http://avatar.csdn.net/C/D/C/1_u011039332.jpg', 'admin', 'guest', '', '2017-06-05 20:13:37', '2017-06-05 20:13:37', '0');
INSERT INTO `blog_comment` VALUES ('117', '27', '14', '1', '-1', '', '', 'http://avatar.csdn.net/C/D/C/1_u011039332.jpg', 'admin', 'guest', '13', '2017-06-05 20:13:38', '2017-06-05 20:13:38', '0');
INSERT INTO `blog_comment` VALUES ('118', '27', '15', '1', '-1', '', '', 'http://avatar.csdn.net/C/D/C/1_u011039332.jpg', 'admin', 'guest', '13', '2017-06-05 20:13:39', '2017-06-05 20:13:39', '0');
INSERT INTO `blog_comment` VALUES ('119', '27', '16', '1', '-1', '', '', 'http://avatar.csdn.net/C/D/C/1_u011039332.jpg', 'admin', 'guest', '13', '2017-06-05 20:13:41', '2017-06-05 20:13:41', '0');
INSERT INTO `blog_comment` VALUES ('120', '27', '17', '1', '-1', '', '', 'http://avatar.csdn.net/C/D/C/1_u011039332.jpg', 'admin', 'guest', '13', '2017-06-05 20:13:42', '2017-06-05 20:13:42', '0');
INSERT INTO `blog_comment` VALUES ('121', '27', '18', '1', '-1', '', '', 'http://avatar.csdn.net/C/D/C/1_u011039332.jpg', 'admin', 'guest', '13', '2017-06-05 20:13:43', '2017-06-05 20:13:43', '0');
INSERT INTO `blog_comment` VALUES ('122', '27', '19', '1', '-1', '', '', 'http://avatar.csdn.net/C/D/C/1_u011039332.jpg', 'admin', 'guest', '13', '2017-06-05 20:18:42', '2017-06-05 20:18:42', '0');
INSERT INTO `blog_comment` VALUES ('123', '27', '20', '1', '-1', '', '', 'http://avatar.csdn.net/C/D/C/1_u011039332.jpg', 'admin', 'guest', '222', '2017-06-05 20:18:50', '2017-06-05 20:18:50', '0');
INSERT INTO `blog_comment` VALUES ('124', '27', '21', '1', '-1', '', '', 'http://avatar.csdn.net/C/D/C/1_u011039332.jpg', 'admin', 'guest', '1313', '2017-06-05 20:19:34', '2017-06-10 18:01:25', '1');
INSERT INTO `blog_comment` VALUES ('125', '27', '22', '1', '-1', '', '', 'http://avatar.csdn.net/C/D/C/1_u011039332.jpg', 'admin', 'guest', '1313', '2017-06-05 20:19:44', '2017-06-05 20:19:44', '0');
INSERT INTO `blog_comment` VALUES ('126', '27', '23', '1', '-1', '', '', 'http://avatar.csdn.net/C/D/C/1_u011039332.jpg', 'admin', 'guest', '1313', '2017-06-05 20:20:10', '2017-06-05 20:20:10', '0');
INSERT INTO `blog_comment` VALUES ('127', '27', '24', '1', '-1', '', '', 'http://avatar.csdn.net/C/D/C/1_u011039332.jpg', 'admin', 'guest', '1313', '2017-06-05 20:20:41', '2017-06-05 20:20:41', '0');
INSERT INTO `blog_comment` VALUES ('128', '27', '25', '1', '-1', '13', '13131', 'http://avatar.csdn.net/C/D/C/1_u011039332.jpg', 'admin', 'guest', '131313', '2017-06-05 20:36:18', '2017-06-05 20:36:18', '0');
INSERT INTO `blog_comment` VALUES ('129', '-2', '2', '1', '-1', '13', '13131', 'http://avatar.csdn.net/C/D/C/1_u011039332.jpg', 'admin', 'guest', '是否还有一种矜持还留在心间', '2017-06-05 20:39:47', '2017-06-05 20:39:47', '0');
INSERT INTO `blog_comment` VALUES ('130', '27', '2', '2', '104', '13', '13131', 'http://avatar.csdn.net/C/D/C/1_u011039332.jpg', 'admin', 'guest', '<div>睡到了副科级</div>', '2017-06-05 21:13:28', '2017-06-05 21:13:28', '0');
INSERT INTO `blog_comment` VALUES ('131', '27', '2', '3', '104', 'admin', '970655147@qq.com', 'http://tb2.bdstatic.com/tb/editor/images/face/i_f25.png?t=20140803', 'admin', 'admin', '有什么事情吗', '2017-06-05 21:13:57', '2017-06-05 21:13:57', '0');
INSERT INTO `blog_comment` VALUES ('132', '27', '2', '4', '130', 'admin', '970655147@qq.com', 'http://tb2.bdstatic.com/tb/editor/images/face/i_f25.png?t=20140803', '13', 'admin', '你好,&nbsp;', '2017-06-05 21:15:28', '2017-06-05 21:15:28', '0');
INSERT INTO `blog_comment` VALUES ('133', '27', '2', '5', '130', 'admin', '970655147@qq.com', 'http://tb2.bdstatic.com/tb/editor/images/face/i_f25.png?t=20140803', '13', 'admin', '111', '2017-06-05 21:17:15', '2017-06-05 21:17:15', '0');
INSERT INTO `blog_comment` VALUES ('134', '27', '2', '6', '130', 'admin', '970655147@qq.com', 'http://tb2.bdstatic.com/tb/editor/images/face/i_f25.png?t=20140803', '13', 'admin', '1313', '2017-06-05 21:20:43', '2017-06-05 21:20:43', '0');
INSERT INTO `blog_comment` VALUES ('135', '27', '2', '7', '130', 'admin', '970655147@qq.com', 'http://tb2.bdstatic.com/tb/editor/images/face/i_f25.png?t=20140803', '13', 'admin', '131', '2017-06-05 21:21:40', '2017-06-05 21:21:40', '0');
INSERT INTO `blog_comment` VALUES ('136', '27', '26', '1', '-1', '13', '1313', 'http://avatar.csdn.net/C/D/C/1_u011039332.jpg', 'admin', 'guest', '345345', '2017-06-05 21:27:08', '2017-06-05 21:27:08', '0');
INSERT INTO `blog_comment` VALUES ('137', '27', '27', '1', '-1', '13', '1313', 'http://avatar.csdn.net/C/D/C/1_u011039332.jpg', 'admin', 'guest', '', '2017-06-05 21:28:08', '2017-06-05 21:28:08', '0');
INSERT INTO `blog_comment` VALUES ('138', '-2', '1', '2', '105', 'admin', '970655147@qq.com', 'http://tb2.bdstatic.com/tb/editor/images/face/i_f25.png?t=20140803', 'admin', 'admin', '不给建议', '2017-06-10 17:56:29', '2017-06-10 17:56:29', '0');
INSERT INTO `blog_comment` VALUES ('139', '-2', '2', '2', '129', 'admin', '970655147@qq.com', 'http://tb2.bdstatic.com/tb/editor/images/face/i_f25.png?t=20140803', '13', 'admin', '  矜持?? ', '2017-06-10 17:58:56', '2017-06-10 18:01:10', '0');
INSERT INTO `blog_comment` VALUES ('140', '-2', '1', '3', '138', 'admin', '970655147@qq.com', 'null', 'admin', 'admin', '求求你了', '2017-06-11 00:53:55', '2017-06-11 00:53:55', '0');
INSERT INTO `blog_comment` VALUES ('141', '-2', '2', '3', '139', 'admin', '970655147@qq.com', 'null', 'admin', 'admin', '想多了', '2017-06-11 00:54:57', '2017-06-11 00:54:57', '0');
INSERT INTO `blog_comment` VALUES ('142', '-2', '2', '4', '129', 'admin', '970655147@qq.com', 'null', '13', 'admin', '哦, 好', '2017-06-11 00:55:37', '2017-06-11 00:55:37', '0');
INSERT INTO `blog_comment` VALUES ('143', '77', '1', '1', '-1', '张三', '12345678@126.com', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497100627788&di=52eb6dc3050d2990517a095c528e0fe7&imgtype=0&src=http%3A%2F%2Ffile.popoho.com%2F2016-08-05%2Fd501f852d63dd41940f4a8b2d40748c8.jpg', 'admin', 'guest', '哟西,&nbsp;有点意思', '2017-06-11 11:14:12', '2017-06-11 11:14:12', '0');
INSERT INTO `blog_comment` VALUES ('144', '77', '2', '1', '-1', '张三', '12345678@126.com', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497100627788&di=52eb6dc3050d2990517a095c528e0fe7&imgtype=0&src=http%3A%2F%2Ffile.popoho.com%2F2016-08-05%2Fd501f852d63dd41940f4a8b2d40748c8.jpg', 'admin', 'guest', '哟西,&nbsp;有点意思', '2017-06-11 11:14:54', '2017-06-11 11:14:54', '0');
INSERT INTO `blog_comment` VALUES ('145', '77', '3', '1', '-1', 'admin', '970655147@qq.com', 'http://tb2.bdstatic.com/tb/editor/images/face/i_f25.png?t=20140803', 'admin', 'guest', '哦, Hao a&nbsp;', '2017-06-11 11:48:40', '2017-06-11 11:48:40', '0');
INSERT INTO `blog_comment` VALUES ('146', '80', '1', '1', '-1', 'zhangsan', '9139834@124.com', 'http://avatar.csdn.net/C/D/C/1_u011039332.jpg', 'admin', 'null', '哦, 你好 111', '2017-06-24 20:56:53', '2017-06-24 21:56:41', '1');
INSERT INTO `blog_comment` VALUES ('147', '80', '2', '1', '-1', 'zhangsan', '9139834@124.com', 'http://avatar.csdn.net/C/D/C/1_u011039332.jpg', 'admin', 'null', 'sdf', '2017-06-24 20:58:55', '2017-06-24 21:56:41', '1');
INSERT INTO `blog_comment` VALUES ('148', '80', '1', '2', '146', 'zhangsan', '9139834@124.com', 'http://avatar.csdn.net/C/D/C/1_u011039332.jpg', 'zhangsan', 'null', 'sdf&nbsp;', '2017-06-24 20:59:02', '2017-06-24 21:56:41', '1');
INSERT INTO `blog_comment` VALUES ('149', '80', '3', '1', '-1', 'admin', '970655147@qq.com', 'http://tb2.bdstatic.com/tb/editor/images/face/i_f25.png?t=20140803', 'admin', 'admin', '1312', '2017-06-24 21:05:12', '2017-06-24 21:56:41', '1');
INSERT INTO `blog_comment` VALUES ('150', '80', '4', '1', '-1', 'admin', '970655147@qq.com', 'http://tb2.bdstatic.com/tb/editor/images/face/i_f25.png?t=20140803', 'admin', 'admin', '1312', '2017-06-24 21:13:05', '2017-06-24 21:56:41', '1');
INSERT INTO `blog_comment` VALUES ('151', '27', '28', '1', '-1', 'admin', '970655147@qq.com', 'http://tb2.bdstatic.com/tb/editor/images/face/i_f25.png?t=20140803', 'admin', 'admin', '<html>\n <head></head>\n <body>\n  有 8 种\n </body>\n</html>', '2017-06-25 10:15:13', '2017-06-25 10:15:13', '0');
INSERT INTO `blog_comment` VALUES ('152', '27', '29', '1', '-1', 'admin', '970655147@qq.com', 'http://tb2.bdstatic.com/tb/editor/images/face/i_f25.png?t=20140803', 'admin', 'admin', '<html>\n <head></head>\n <body>\n  &lt;script&gt;alert(\"dfd\")&lt;script&gt;\n  <div>\n   注入测试\n  </div>\n </body>\n</html>', '2017-06-25 10:18:43', '2017-06-25 10:18:43', '0');
INSERT INTO `blog_comment` VALUES ('153', '27', '30', '1', '-1', 'admin', '970655147@qq.com', 'http://tb2.bdstatic.com/tb/editor/images/face/i_f25.png?t=20140803', 'admin', 'admin', '<html>\n <head></head>\n <body>\n  雷浩雷浩\n </body>\n</html>', '2017-06-25 10:22:15', '2017-06-25 10:22:15', '0');
INSERT INTO `blog_comment` VALUES ('154', '27', '31', '1', '-1', 'admin', '970655147@qq.com', 'http://tb2.bdstatic.com/tb/editor/images/face/i_f25.png?t=20140803', 'admin', 'admin', '<html>\n <head></head>\n <body>\n  &lt;script&gt;alert(\"sdf\")&lt;/script&gt;\n </body>\n</html>', '2017-06-25 10:23:32', '2017-06-25 10:23:32', '0');
INSERT INTO `blog_comment` VALUES ('155', '27', '32', '1', '-1', 'admin', '970655147@qq.com', 'http://tb2.bdstatic.com/tb/editor/images/face/i_f25.png?t=20140803', 'admin', 'guest', '<html>\n <head></head>\n <body></body>\n</html>', '2017-06-25 10:53:51', '2017-06-25 10:53:51', '0');
INSERT INTO `blog_comment` VALUES ('156', '27', '33', '1', '-1', 'admin', '970655147@qq.com', 'http://tb2.bdstatic.com/tb/editor/images/face/i_f25.png?t=20140803', 'admin', 'guest', '<html>\n <head></head>\n <body>\n  beforeafter\n </body>\n</html>', '2017-06-25 10:56:03', '2017-06-25 10:56:03', '0');
INSERT INTO `blog_comment` VALUES ('157', '27', '34', '1', '-1', 'admin', '970655147@qq.com', 'http://tb2.bdstatic.com/tb/editor/images/face/i_f25.png?t=20140803', 'admin', 'guest', '<html>\n <head></head>\n <body>\n  beforeafter\n </body>\n</html>', '2017-06-25 10:56:21', '2017-06-25 10:56:21', '0');
INSERT INTO `blog_comment` VALUES ('158', '27', '35', '1', '-1', 'admin', '970655147@qq.com', 'http://tb2.bdstatic.com/tb/editor/images/face/i_f25.png?t=20140803', 'admin', 'guest', '<html>\n <head></head>\n <body>\n  beforeafter\n </body>\n</html>', '2017-06-25 10:58:34', '2017-06-25 10:58:34', '0');
INSERT INTO `blog_comment` VALUES ('159', '27', '36', '1', '-1', 'admin', '970655147@qq.com', 'http://tb2.bdstatic.com/tb/editor/images/face/i_f25.png?t=20140803', 'admin', 'guest', '<html>\n <head></head>\n <body>\n  beforeafter\n </body>\n</html>', '2017-06-25 10:58:56', '2017-06-25 10:58:56', '0');
INSERT INTO `blog_comment` VALUES ('160', '27', '37', '1', '-1', 'admin', '970655147@qq.com', 'http://tb2.bdstatic.com/tb/editor/images/face/i_f25.png?t=20140803', 'admin', 'admin', '<html>\n <head></head>\n <body>\n  1313\n </body>\n</html>', '2017-06-25 11:45:53', '2017-06-25 11:45:53', '0');
INSERT INTO `blog_comment` VALUES ('161', '98', '1', '1', '-1', 'admin', '970655147@qq.com', 'http://tb2.bdstatic.com/tb/editor/images/face/i_f25.png?t=20140803', 'admin', 'admin', '<html>\n <head></head>\n <body>\n  好美呀 !\n </body>\n</html>', '2017-06-27 22:01:47', '2017-06-27 22:01:47', '0');
INSERT INTO `blog_comment` VALUES ('162', '98', '2', '1', '-1', 'admin', '970655147@qq.com', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497100627788&di=52eb6dc3050d2990517a095c528e0fe7&imgtype=0&src=http%3A%2F%2Ffile.popoho.com%2F2016-08-05%2Fd501f852d63dd41940f4a8b2d40748c8.jpg', 'admin', 'admin', '<html>\n <head></head>\n <body>\n  真美&nbsp;\n </body>\n</html>', '2017-06-27 22:02:52', '2017-06-27 22:02:52', '0');
INSERT INTO `blog_comment` VALUES ('163', '98', '3', '1', '-1', 'admin', '970655147@qq.com', 'https://gss0.bdstatic.com/6LZ1dD3d1sgCo2Kml5_Y_D3/sys/portrait/item/48c0c0b6b7e7393730363535313437f02d', 'admin', 'admin', '<html>\n <head></head>\n <body>\n  关之琳大美人 !\n </body>\n</html>', '2017-06-27 22:04:11', '2017-06-27 22:04:11', '0');
INSERT INTO `blog_comment` VALUES ('164', '27', '38', '1', '-1', 'admin', '970655147@qq.com', 'http://tb2.bdstatic.com/tb/editor/images/face/i_f25.png?t=20140803', 'admin', 'admin', '<html>\n <head></head>\n <body>\n  98695486sldkjf ⊙\n </body>\n</html>', '2017-06-30 20:20:02', '2017-06-30 20:20:02', '0');
INSERT INTO `blog_comment` VALUES ('165', '27', '39', '1', '-1', '狗屎 ', '124@qq.com', 'https://gss0.bdstatic.com/6LZ1dD3d1sgCo2Kml5_Y_D3/sys/portrait/item/48c0c0b6b7e7393730363535313437f02d', 'admin', 'guest', '<html>\n <head></head>\n <body>\n  131\n </body>\n</html>', '2017-06-30 20:24:21', '2017-06-30 20:24:21', '0');
INSERT INTO `blog_comment` VALUES ('166', '27', '40', '1', '-1', 'admin', '970655147@qq.com', 'http://avatar.csdn.net/C/D/C/1_u011039332.jpg', 'admin', 'admin', '<html>\n <head></head>\n <body>\n  31313\n </body>\n</html>', '2017-06-30 20:43:57', '2017-06-30 20:43:57', '0');
INSERT INTO `blog_comment` VALUES ('167', '27', '41', '1', '-1', 'Jerry.X.He', '970655147@qq.com', 'http://avatar.csdn.net/C/D/C/1_u011039332.jpg', 'admin', 'guest', '<html>\n <head></head>\n <body>\n  11111111\n </body>\n</html>', '2017-06-30 20:44:40', '2017-06-30 20:44:40', '0');
INSERT INTO `blog_comment` VALUES ('168', '27', '42', '1', '-1', 'Jerry.X.He', '970655147@qq.com', 'http://avatar.csdn.net/C/D/C/1_u011039332.jpg', 'admin', 'guest', '<html>\n <head></head>\n <body>\n  111\n </body>\n</html>', '2017-06-30 20:45:26', '2017-06-30 20:45:26', '0');
INSERT INTO `blog_comment` VALUES ('169', '27', '43', '1', '-1', 'Jerry.X.He', '970655147@qq.com', 'http://avatar.csdn.net/C/D/C/1_u011039332.jpg', 'admin', 'guest', '<html>\n <head></head>\n <body>\n  1313131111\n </body>\n</html>', '2017-06-30 20:46:41', '2017-06-30 20:46:41', '0');
INSERT INTO `blog_comment` VALUES ('170', '-2', '3', '1', '-1', '1111', '', 'http://avatar.csdn.net/C/D/C/1_u011039332.jpg', 'admin', 'guest', '<html>\n <head></head>\n <body>\n  3l\n </body>\n</html>', '2017-07-03 19:37:37', '2017-07-03 19:37:37', '0');
INSERT INTO `blog_comment` VALUES ('171', '-2', '3', '2', '170', '1111', '', 'http://avatar.csdn.net/C/D/C/1_u011039332.jpg', '1111', 'guest', '<html>\n <head></head>\n <body>\n   this is content from my \n </body>\n</html>', '2017-07-03 19:37:50', '2017-07-03 19:37:50', '0');
INSERT INTO `blog_comment` VALUES ('172', '-2', '3', '3', '171', '1111', '', 'http://avatar.csdn.net/C/D/C/1_u011039332.jpg', '1111', 'guest', '<html>\n <head></head>\n <body>\n  &nbsp; \n  <div>\n   你好你好你吃草\n  </div>\n </body>\n</html>', '2017-07-03 19:38:17', '2017-07-03 19:38:17', '0');
INSERT INTO `blog_comment` VALUES ('173', '-2', '3', '4', '171', '1111', '', 'http://avatar.csdn.net/C/D/C/1_u011039332.jpg', '1111', 'guest', '<html>\n <head></head>\n <body>\n   狗屁 \n  <div>\n   <br>\n  </div>\n </body>\n</html>', '2017-07-03 19:40:33', '2017-07-03 19:40:33', '0');
INSERT INTO `blog_comment` VALUES ('174', '-2', '3', '5', '172', '1111', '', 'http://avatar.csdn.net/C/D/C/1_u011039332.jpg', '1111', 'guest', '<html>\n <head></head>\n <body>\n  1111 \n </body>\n</html>', '2017-07-03 19:42:34', '2017-07-03 19:42:34', '0');
INSERT INTO `blog_comment` VALUES ('175', '-2', '3', '6', '173', '1111', '', 'http://avatar.csdn.net/C/D/C/1_u011039332.jpg', '1111', 'guest', '<html>\n <head></head>\n <body>\n  222 \n </body>\n</html>', '2017-07-03 19:45:13', '2017-07-03 19:45:13', '0');
INSERT INTO `blog_comment` VALUES ('176', '-2', '4', '1', '-1', '1111', '', 'http://avatar.csdn.net/C/D/C/1_u011039332.jpg', 'admin', 'guest', '<html>\n <head></head>\n <body>\n  11\n </body>\n</html>', '2017-07-03 19:46:49', '2017-07-03 19:46:49', '0');
INSERT INTO `blog_comment` VALUES ('177', '-2', '3', '7', '174', '1111', '1111111@qq.com', 'https://gss0.bdstatic.com/6LZ1dD3d1sgCo2Kml5_Y_D3/sys/portrait/item/48c0c0b6b7e7393730363535313437f02d', '1111', 'guest', '<html>\n <head></head>\n <body>\n   呵呵呵 \n </body>\n</html>', '2017-07-03 19:49:28', '2017-07-03 19:49:28', '0');
INSERT INTO `blog_comment` VALUES ('178', '27', '44', '1', '-1', 'admin', '970655147@qq.com', 'http://tb2.bdstatic.com/tb/editor/images/face/i_f25.png?t=20140803', 'admin', 'admin', '<html>\n <head></head>\n <body>\n  <pre style=\"background-color: rgb(255, 255, 255); font-family: SimSun; font-size: 12pt;\"><font color=\"#660e7a\"><b><i>1111221111, nihao a </i></b></font></pre>\n </body>\n</html>', '2017-07-04 20:59:46', '2017-07-04 20:59:46', '0');
INSERT INTO `blog_comment` VALUES ('179', '-2', '5', '1', '-1', 'admin', '970655147@qq.com', 'http://tb2.bdstatic.com/tb/editor/images/face/i_f25.png?t=20140803', 'admin', 'admin', '<html>\n <head></head>\n <body>\n  雷浩\n </body>\n</html>', '2017-07-05 22:40:19', '2017-07-05 22:40:19', '0');
INSERT INTO `blog_comment` VALUES ('180', '-2', '6', '1', '-1', 'admin', '970655147@qq.com', 'http://tb2.bdstatic.com/tb/editor/images/face/i_f25.png?t=20140803', 'admin', 'admin', '<html>\n <head></head>\n <body>\n  <span style=\"color: rgb(255, 0, 0); font-family: Arial, Helvetica, sans-serif; font-size: 12px;\">雷浩</span>\n </body>\n</html>', '2017-07-05 22:50:45', '2017-07-05 22:50:45', '0');
INSERT INTO `blog_comment` VALUES ('181', '-2', '7', '1', '-1', 'admin', '970655147@qq.com', 'http://tb2.bdstatic.com/tb/editor/images/face/i_f25.png?t=20140803', 'admin', 'admin', '<html>\n <head></head>\n <body>\n  ddd\n </body>\n</html>', '2017-07-05 23:05:43', '2017-07-05 23:05:43', '0');
INSERT INTO `blog_comment` VALUES ('182', '-2', '8', '1', '-1', 'admin', '970655147@qq.com', 'http://tb2.bdstatic.com/tb/editor/images/face/i_f25.png?t=20140803', 'admin', 'admin', '<html>\n <head></head>\n <body>\n  fff\n </body>\n</html>', '2017-07-05 23:06:12', '2017-07-05 23:06:12', '0');
INSERT INTO `blog_comment` VALUES ('183', '-2', '9', '1', '-1', 'admin', '970655147@qq.com', 'http://tb2.bdstatic.com/tb/editor/images/face/i_f25.png?t=20140803', 'admin', 'admin', '<html>\n <head></head>\n <body>\n  1313\n </body>\n</html>', '2017-07-05 23:07:35', '2017-07-05 23:07:35', '0');
INSERT INTO `blog_comment` VALUES ('184', '-2', '10', '1', '-1', 'admin', '970655147@qq.com', 'http://tb2.bdstatic.com/tb/editor/images/face/i_f25.png?t=20140803', 'admin', 'admin', '<html>\n <head></head>\n <body>\n  444\n </body>\n</html>', '2017-07-05 23:07:50', '2017-07-05 23:07:50', '0');
INSERT INTO `blog_comment` VALUES ('185', '-2', '11', '1', '-1', 'admin', '970655147@qq.com', 'http://tb2.bdstatic.com/tb/editor/images/face/i_f25.png?t=20140803', 'admin', 'admin', '<html>\n <head></head>\n <body>\n  131\n </body>\n</html>', '2017-07-05 23:08:23', '2017-07-05 23:08:23', '0');
INSERT INTO `blog_comment` VALUES ('186', '-2', '12', '1', '-1', 'admin', '970655147@qq.com', 'http://tb2.bdstatic.com/tb/editor/images/face/i_f25.png?t=20140803', 'admin', 'admin', '<html>\n <head></head>\n <body>\n  1311\n </body>\n</html>', '2017-07-05 23:08:42', '2017-07-05 23:08:42', '0');
INSERT INTO `blog_comment` VALUES ('187', '-2', '13', '1', '-1', 'admin', '970655147@qq.com', 'http://tb2.bdstatic.com/tb/editor/images/face/i_f25.png?t=20140803', 'admin', 'admin', '<html>\n <head></head>\n <body>\n  1111\n </body>\n</html>', '2017-07-05 23:09:36', '2017-07-05 23:09:36', '0');
INSERT INTO `blog_comment` VALUES ('188', '-2', '14', '1', '-1', 'admin', '970655147@qq.com', 'http://tb2.bdstatic.com/tb/editor/images/face/i_f25.png?t=20140803', 'admin', 'admin', '<html>\n <head></head>\n <body>\n  33333333333\n </body>\n</html>', '2017-07-05 23:12:21', '2017-07-05 23:12:21', '0');
INSERT INTO `blog_comment` VALUES ('189', '91', '1', '1', '-1', 'admin', '970655147@qq.com', 'http://tb2.bdstatic.com/tb/editor/images/face/i_f25.png?t=20140803', 'admin', 'admin', '<html>\n <head></head>\n <body>\n  哦, 你好呀\n </body>\n</html>', '2017-07-06 20:04:20', '2017-07-06 20:04:20', '0');
INSERT INTO `blog_comment` VALUES ('190', '-2', '15', '1', '-1', 'admin', '970655147@qq.com', 'http://tb2.bdstatic.com/tb/editor/images/face/i_f25.png?t=20140803', 'admin', 'guest', '<html>\n <head></head>\n <body>\n  <ahref=\\\"https: www.qyh.me\\\"style=\"&quot;display:\" block; z-index: 100000; opacity: 0.5; position: fixed; top: 0px; left: 0; width: 1000000px; height: 100000px; background-color: red;\\\"></ahref=\\\"https:>\n </body>\n</html>', '2017-07-06 20:09:46', '2017-07-06 20:09:46', '0');
INSERT INTO `blog_comment` VALUES ('191', '-2', '16', '1', '-1', 'admin', '970655147@qq.com', 'http://tb2.bdstatic.com/tb/editor/images/face/i_f25.png?t=20140803', 'admin', 'guest', '<html>\n <head></head>\n <body>\n  <ahref=\\\"https: www.qyh.me\\\"style=\"&quot;display:\" block; z-index: 100000; opacity: 0.5; position: fixed; top: 0px; left: 0; width: 1000000px; height: 100000px; background-color: red;\\\"></ahref=\\\"https:>\n </body>\n</html>', '2017-07-06 20:12:35', '2017-07-06 20:12:35', '0');
INSERT INTO `blog_comment` VALUES ('192', '-2', '17', '1', '-1', 'admin', '970655147@qq.com', 'http://tb2.bdstatic.com/tb/editor/images/face/i_f25.png?t=20140803', 'admin', 'guest', '', '2017-07-06 20:14:59', '2017-07-06 20:14:59', '0');
INSERT INTO `blog_comment` VALUES ('193', '-2', '18', '1', '-1', 'admin', '970655147@qq.com', 'http://tb2.bdstatic.com/tb/editor/images/face/i_f25.png?t=20140803', 'admin', 'guest', '<html>\n <body></body>\n</html>', '2017-07-06 20:16:13', '2017-07-06 20:16:13', '0');
INSERT INTO `blog_comment` VALUES ('194', '-2', '19', '1', '-1', 'admin', '970655147@qq.com', 'http://tb2.bdstatic.com/tb/editor/images/face/i_f25.png?t=20140803', 'admin', 'guest', '<html>\n <body>\n  <img src=\"&quot;https://www.qyh.me&quot;style=&quot;display:\" block; z-index: 100000; opacity: 0.5; position: fixed; top: 0px; left: 0; width: 1000000px; height: 100000px; background-color: red;\\\">\n </body>\n</html>', '2017-07-06 20:23:03', '2017-07-06 20:23:03', '0');
INSERT INTO `blog_comment` VALUES ('195', '-2', '20', '1', '-1', 'admin', '970655147@qq.com', 'http://tb2.bdstatic.com/tb/editor/images/face/i_f25.png?t=20140803', 'admin', 'guest', '<html>\n <body>\n  <img src=\"&quot;https://www.qyh.me&quot;\" block; z-index: 100000; opacity: 0.5; position: fixed; top: 0px; left: 0; width: 1000000px; height: 100000px; background-color: red;\\\">\n </body>\n</html>', '2017-07-06 20:31:59', '2017-07-06 20:31:59', '0');
INSERT INTO `blog_comment` VALUES ('196', '-2', '21', '1', '-1', 'admin', '970655147@qq.com', 'http://tb2.bdstatic.com/tb/editor/images/face/i_f25.png?t=20140803', 'admin', 'guest', '<html>\n <body>\n  <img src=\"https://www.qyh.me\">\n </body>\n</html>', '2017-07-06 20:32:49', '2017-07-06 20:32:49', '0');
INSERT INTO `blog_comment` VALUES ('197', '-2', '22', '1', '-1', '111', '124@qq.com', 'http://avatar.csdn.net/C/D/C/1_u011039332.jpg', 'admin', 'guest', '<html>\n <body>\n  <img class=\"emoji_icon\" src=\"/static/main/images/qq/2.gif\">\n </body>\n</html>', '2017-07-06 20:33:31', '2017-07-06 20:33:31', '0');
INSERT INTO `blog_comment` VALUES ('198', '-2', '23', '1', '-1', 'admin', '970655147@qq.com', 'http://tb2.bdstatic.com/tb/editor/images/face/i_f25.png?t=20140803', 'admin', 'guest', '<html>\n <body>\n  <img src=\"http://tb2.bdstatic.com/tb/editor/images/face/i_f25.png?t=20140803\">\n </body>\n</html>', '2017-07-06 20:34:25', '2017-07-06 20:34:25', '0');
INSERT INTO `blog_comment` VALUES ('199', '-2', '24', '1', '-1', 'admin', '970655147@qq.com', 'http://tb2.bdstatic.com/tb/editor/images/face/i_f25.png?t=20140803', 'admin', 'guest', '<html>\n <body>\n  <img src=\"http://img2.imgtn.bdimg.com/it/u=2030965026,1272976928&amp;fm=26&amp;gp=0.jpg\">\n </body>\n</html>', '2017-07-06 20:35:00', '2017-07-06 20:35:00', '0');
INSERT INTO `blog_comment` VALUES ('200', '-2', '25', '1', '-1', 'admin', '970655147@qq.com', 'http://tb2.bdstatic.com/tb/editor/images/face/i_f25.png?t=20140803', 'admin', 'guest', '<html>\n <body>\n  <img src=\"http://www.pp3.cn/uploads/201502/2015021111.jpg\" width=\"40px\" height=\"40px\">\n </body>\n</html>', '2017-07-06 20:35:48', '2017-07-06 20:35:48', '0');
INSERT INTO `blog_comment` VALUES ('201', '-2', '26', '1', '-1', 'admin', '970655147@qq.com', 'http://tb2.bdstatic.com/tb/editor/images/face/i_f25.png?t=20140803', 'admin', 'guest', '<html>\n <body>\n  <img src=\"http://www.pp3.cn/uploads/201502/2015021111.jpg\" width=\"40\" height=\"40\">\n </body>\n</html>', '2017-07-06 20:47:07', '2017-07-06 20:47:07', '0');
INSERT INTO `blog_comment` VALUES ('202', '-2', '27', '1', '-1', 'admin', '970655147@qq.com', 'http://tb2.bdstatic.com/tb/editor/images/face/i_f25.png?t=20140803', 'admin', 'guest', '<html>\n <body> \n  <img src=\"http://www.pp3.cn/uploads/201502/2015021111.jpg\" width=\"40px\" height=\"40px\">   \n </body>\n</html>', '2017-07-06 20:51:44', '2018-03-04 19:47:11', '0');
INSERT INTO `blog_comment` VALUES ('203', '91', '2', '1', '-1', '12312', '', 'http://avatar.csdn.net/C/D/C/1_u011039332.jpg', 'admin', 'guest', '<html>\n <body></body>\n</html>', '2017-07-07 20:20:39', '2017-07-07 20:20:39', '0');
INSERT INTO `blog_comment` VALUES ('204', '91', '5', '1', '-1', '12312', '', 'http://avatar.csdn.net/C/D/C/1_u011039332.jpg', 'admin', 'guest', '<html>\n <body>\n  1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111\n </body>\n</html>', '2017-07-07 20:21:45', '2017-07-07 20:21:45', '0');
INSERT INTO `blog_comment` VALUES ('205', '91', '6', '1', '-1', '12312', '', 'http://avatar.csdn.net/C/D/C/1_u011039332.jpg', 'admin', 'guest', '<html>\n <body></body>\n</html>', '2017-07-07 20:27:43', '2017-07-07 20:27:43', '0');
INSERT INTO `blog_comment` VALUES ('206', '74', '1', '1', '-1', '1213', '', 'https://gss0.bdstatic.com/6LZ1dD3d1sgCo2Kml5_Y_D3/sys/portrait/item/48c0c0b6b7e7393730363535313437f02d', 'admin', 'guest', '<html>\n <body>\n  1324\n </body>\n</html>', '2017-07-07 20:42:19', '2017-07-07 20:42:19', '0');
INSERT INTO `blog_comment` VALUES ('207', '74', '2', '1', '-1', '1213', '', 'https://gss0.bdstatic.com/6LZ1dD3d1sgCo2Kml5_Y_D3/sys/portrait/item/48c0c0b6b7e7393730363535313437f02d', 'admin', 'guest', '<html>\n <body>\n  12234\n </body>\n</html>', '2017-07-07 20:43:17', '2017-07-07 20:43:17', '0');
INSERT INTO `blog_comment` VALUES ('208', '74', '3', '1', '-1', '1213', '', 'https://gss0.bdstatic.com/6LZ1dD3d1sgCo2Kml5_Y_D3/sys/portrait/item/48c0c0b6b7e7393730363535313437f02d', 'admin', 'guest', '<html>\n <body>\n  12234\n </body>\n</html>', '2017-07-07 20:48:11', '2017-07-07 20:48:11', '0');
INSERT INTO `blog_comment` VALUES ('209', '74', '4', '1', '-1', '1213', '', 'https://gss0.bdstatic.com/6LZ1dD3d1sgCo2Kml5_Y_D3/sys/portrait/item/48c0c0b6b7e7393730363535313437f02d', 'admin', 'guest', '<html>\n <body></body>\n</html>', '2017-07-07 20:48:54', '2017-07-07 20:48:54', '0');
INSERT INTO `blog_comment` VALUES ('210', '74', '5', '1', '-1', '1213', '', 'https://gss0.bdstatic.com/6LZ1dD3d1sgCo2Kml5_Y_D3/sys/portrait/item/48c0c0b6b7e7393730363535313437f02d', 'admin', 'guest', '<html>\n <body></body>\n</html>', '2017-07-07 20:49:10', '2017-07-07 20:49:10', '0');
INSERT INTO `blog_comment` VALUES ('211', '74', '6', '1', '-1', '1213', '', 'https://gss0.bdstatic.com/6LZ1dD3d1sgCo2Kml5_Y_D3/sys/portrait/item/48c0c0b6b7e7393730363535313437f02d', 'admin', 'guest', '<html>\n <body>\n  243987534\n  <div style=\"display:inline;\">\n   645o9iu645\n  </div>\n  <div style=\"display:inline;\">\n   75687\n  </div>\n  <div style=\"display:inline;\">\n   6789678\n  </div>\n </body>\n</html>', '2017-07-07 20:59:10', '2017-07-07 20:59:10', '0');
INSERT INTO `blog_comment` VALUES ('212', '74', '7', '1', '-1', '1213', '', 'https://gss0.bdstatic.com/6LZ1dD3d1sgCo2Kml5_Y_D3/sys/portrait/item/48c0c0b6b7e7393730363535313437f02d', 'admin', 'guest', '<html>\n <body>\n  1111111111111111111111111111\n  <div>\n   1111111111111111111111111111\n  </div>\n  <div>\n   1111111111111111111111111111\n  </div>\n </body>\n</html>', '2017-07-07 21:07:02', '2017-07-07 21:07:02', '0');
INSERT INTO `blog_comment` VALUES ('213', '74', '8', '1', '-1', '1213', '', 'https://gss0.bdstatic.com/6LZ1dD3d1sgCo2Kml5_Y_D3/sys/portrait/item/48c0c0b6b7e7393730363535313437f02d', 'admin', 'guest', '<html>\n <body>\n  <div style=\"display:inline;\"></div>\n  <div style=\"display:inline;\"></div>\n </body>\n</html>', '2017-07-07 21:07:17', '2017-07-07 21:07:17', '0');
INSERT INTO `blog_comment` VALUES ('214', '74', '9', '1', '-1', '1213', '', 'https://gss0.bdstatic.com/6LZ1dD3d1sgCo2Kml5_Y_D3/sys/portrait/item/48c0c0b6b7e7393730363535313437f02d', 'admin', 'guest', '<html>\n <body>\n  <div style=\"display:inline;\"></div>\n  <div style=\"display:inline;\"></div>\n </body>\n</html>', '2017-07-07 21:08:06', '2017-07-07 21:08:06', '0');
INSERT INTO `blog_comment` VALUES ('215', '74', '10', '1', '-1', '1213', '', 'https://gss0.bdstatic.com/6LZ1dD3d1sgCo2Kml5_Y_D3/sys/portrait/item/48c0c0b6b7e7393730363535313437f02d', 'admin', 'guest', '<html>\n <body>\n  <div style=\"display:inline;\"></div>\n  <div style=\"display:inline;\"></div>\n </body>\n</html>', '2017-07-07 21:08:10', '2017-07-07 21:08:10', '0');
INSERT INTO `blog_comment` VALUES ('216', '74', '11', '1', '-1', '1213', '', 'https://gss0.bdstatic.com/6LZ1dD3d1sgCo2Kml5_Y_D3/sys/portrait/item/48c0c0b6b7e7393730363535313437f02d', 'admin', 'guest', '<html>\n <body>\n  <span style=\"color: rgb(255, 0, 0); font-family: Arial, Helvetica, sans-serif; font-size: 12px;\">11111111111</span>\n  <div style=\"display:inline;\">\n   <span style=\"color: rgb(255, 0, 0); font-family: Arial, Helvetica, sans-serif; font-size: 12px;\">11111111111</span>\n   <span style=\"color: rgb(255, 0, 0); font-family: Arial, Helvetica, sans-serif; font-size: 12px;\"></span>\n  </div>\n  <div style=\"display:inline;\">\n   <span style=\"color: rgb(255, 0, 0); font-family: Arial, Helvetica, sans-serif; font-size: 12px;\">11111111111</span>\n   <span style=\"color: rgb(255, 0, 0); font-family: Arial, Helvetica, sans-serif; font-size: 12px;\"></span>\n  </div>\n </body>\n</html>', '2017-07-07 21:08:52', '2017-07-07 21:08:52', '0');
INSERT INTO `blog_comment` VALUES ('217', '74', '8', '2', '213', 'admin', '970655147@qq.com', 'http://tb2.bdstatic.com/tb/editor/images/face/i_f25.png?t=20140803', '1213', 'admin', '<html>\n <body>\n   辣鸡 \n </body>\n</html>', '2017-07-08 14:55:27', '2017-07-08 14:55:27', '0');
INSERT INTO `blog_comment` VALUES ('218', '74', '7', '2', '212', 'admin', '970655147@qq.com', 'http://tb2.bdstatic.com/tb/editor/images/face/i_f25.png?t=20140803', '1213', 'admin', '<html>\n <body>\n   你才是辣鸡 \n </body>\n</html>', '2017-07-08 14:56:25', '2017-07-08 14:56:25', '0');
INSERT INTO `blog_comment` VALUES ('219', '74', '7', '3', '218', 'admin', '970655147@qq.com', 'http://tb2.bdstatic.com/tb/editor/images/face/i_f25.png?t=20140803', 'admin', 'admin', '<html>\n <body>\n   辣鸡就辣鸡 \n </body>\n</html>', '2017-07-08 15:03:06', '2017-07-08 15:03:06', '0');
INSERT INTO `blog_comment` VALUES ('220', '74', '7', '4', '218', 'admin', '970655147@qq.com', 'http://tb2.bdstatic.com/tb/editor/images/face/i_f25.png?t=20140803', 'admin', 'admin', '<html>\n <body>\n   狗屁 \n </body>\n</html>', '2017-07-08 15:03:31', '2017-07-08 15:03:31', '0');
INSERT INTO `blog_comment` VALUES ('221', '74', '7', '5', '219', 'admin', '970655147@qq.com', 'http://tb2.bdstatic.com/tb/editor/images/face/i_f25.png?t=20140803', 'admin', 'admin', '<html>\n <body>\n   222 \n </body>\n</html>', '2017-07-08 15:04:26', '2017-07-08 15:04:26', '0');
INSERT INTO `blog_comment` VALUES ('222', '74', '7', '6', '221', 'admin', '970655147@qq.com', 'http://tb2.bdstatic.com/tb/editor/images/face/i_f25.png?t=20140803', 'admin', 'admin', '<html>\n <body>\n   111 \n </body>\n</html>', '2017-07-08 15:05:37', '2017-07-08 15:05:37', '0');
INSERT INTO `blog_comment` VALUES ('223', '74', '7', '7', '222', 'admin', '970655147@qq.com', 'http://tb2.bdstatic.com/tb/editor/images/face/i_f25.png?t=20140803', 'admin', 'admin', '<html>\n <body>\n   爱情别离选择 \n </body>\n</html>', '2017-07-08 15:07:44', '2017-07-08 15:07:44', '0');
INSERT INTO `blog_comment` VALUES ('224', '98', '4', '1', '-1', '你好1', '', 'http://avatar.csdn.net/C/D/C/1_u011039332.jpg', 'admin', 'guest', '<html>\n <body>\n  1111\n </body>\n</html>', '2017-07-28 21:06:49', '2017-07-28 21:06:49', '0');
INSERT INTO `blog_comment` VALUES ('225', '98', '5', '1', '-1', '你好1', '', 'http://avatar.csdn.net/C/D/C/1_u011039332.jpg', 'admin', 'guest', '<html>\n <body>\n  1112\n </body>\n</html>', '2017-07-28 21:07:20', '2017-07-28 21:07:20', '0');
INSERT INTO `blog_comment` VALUES ('226', '91', '2', '2', '203', 'admin', '970655147@qq.com', 'http://tb2.bdstatic.com/tb/editor/images/face/i_f25.png?t=20140803', '12312', 'admin', '<html>\n <body>\n   臭狗屁 \n </body>\n</html>', '2017-09-10 11:50:33', '2017-09-10 11:50:33', '0');
INSERT INTO `blog_comment` VALUES ('227', '-2', '20', '2', '195', 'admin', '970655147@qq.com', 'http://tb2.bdstatic.com/tb/editor/images/face/i_f25.png?t=20140803', 'admin', 'admin', '<html>\n <body>\n   ccc\n </body>\n</html>', '2018-03-04 19:43:18', '2018-03-04 19:43:18', '0');
INSERT INTO `blog_comment` VALUES ('228', '-2', '3', '8', '173', 'admin', '970655147@qq.com', 'http://tb2.bdstatic.com/tb/editor/images/face/i_f25.png?t=20140803', '1111', 'admin', '<html>\n <body>\n   增加了一跳评论\n </body>\n</html>', '2018-03-04 20:40:26', '2018-03-04 20:40:26', '0');

-- ----------------------------
-- Table structure for blog_create_type
-- ----------------------------
DROP TABLE IF EXISTS `blog_create_type`;
CREATE TABLE `blog_create_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) DEFAULT NULL COMMENT '名称',
  `desc` varchar(256) DEFAULT NULL COMMENT '描述信息',
  `img_url` varchar(2048) DEFAULT NULL COMMENT '类型的图片地址',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `created_at` varchar(32) DEFAULT NULL COMMENT '创建时间',
  `updated_at` varchar(32) DEFAULT NULL COMMENT '修改时间',
  `deleted` tinyint(1) DEFAULT NULL COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='博客创建类型';

-- ----------------------------
-- Records of blog_create_type
-- ----------------------------
INSERT INTO `blog_create_type` VALUES ('1', '原创', '作者原创', 'http://static.blog.csdn.net/images/ico_Original.gif', '0', '2017-06-13 21:31:01', '2017-06-17 23:26:57', '0');
INSERT INTO `blog_create_type` VALUES ('2', '1', '1', '1', '1', '2017-06-13 21:41:07', '2017-06-24 20:07:59', '1');
INSERT INTO `blog_create_type` VALUES ('3', '转载', '转载', 'http://static.blog.csdn.net/images/ico_Repost.gif', '10', '2017-06-13 21:42:12', '2017-06-24 20:03:51', '0');
INSERT INTO `blog_create_type` VALUES ('4', '翻译', '翻译', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498909185&di=fafd87521f9b339fded6f7fab4a3a528&imgtype=jpg&er=1&src=http%3A%2F%2Fimg.25pp.com%2Fuploadfile%2Fsoft%2Fimages%2F2015%2F0730%2F20150730014558585.jpg', '20', '2017-06-24 19:39:59', '2017-06-24 19:39:59', '0');

-- ----------------------------
-- Table structure for blog_ex
-- ----------------------------
DROP TABLE IF EXISTS `blog_ex`;
CREATE TABLE `blog_ex` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `blog_id` int(11) DEFAULT NULL COMMENT '关联的 blogId',
  `comment_cnt` int(11) DEFAULT NULL COMMENT '评论的数量',
  `view_cnt` int(11) DEFAULT NULL COMMENT '查看的数量',
  `day_flush_view_cnt` int(11) DEFAULT NULL COMMENT '访问数量(同一天相同ip不统计)',
  `unique_view_cnt` int(11) DEFAULT NULL COMMENT '相同ip不统计',
  `good1_cnt` int(11) DEFAULT NULL COMMENT '顶1的数量',
  `good2_cnt` int(11) DEFAULT NULL COMMENT '顶2的数量',
  `good3_cnt` int(11) DEFAULT NULL COMMENT '顶3的数量',
  `good4_cnt` int(11) DEFAULT NULL COMMENT '顶4的数量',
  `good5_cnt` int(11) DEFAULT NULL COMMENT '顶5的数量',
  `good_total_cnt` int(11) DEFAULT NULL COMMENT '所有顶的数量',
  `good_total_score` int(11) DEFAULT NULL COMMENT '所有顶的分数',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_1` (`blog_id`),
  CONSTRAINT `FK_Reference_1` FOREIGN KEY (`blog_id`) REFERENCES `blog` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=88 DEFAULT CHARSET=utf8 COMMENT='博客额外信息表';

-- ----------------------------
-- Records of blog_ex
-- ----------------------------
INSERT INTO `blog_ex` VALUES ('8', '-2', '27', '5', '3', '2', '1', '0', '0', '0', '2', '3', '11');
INSERT INTO `blog_ex` VALUES ('9', '-1', '0', '254', '7', '1', '1', '0', '0', '0', '0', '1', '1');
INSERT INTO `blog_ex` VALUES ('10', '-3', '0', '59', '2', '2', '1', '0', '0', '0', '1', '2', '6');
INSERT INTO `blog_ex` VALUES ('11', '27', '43', '8', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `blog_ex` VALUES ('22', '38', '0', '6', '1', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `blog_ex` VALUES ('23', '39', '0', '3', '2', '1', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `blog_ex` VALUES ('24', '40', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `blog_ex` VALUES ('25', '41', '0', '4', '1', '1', '1', '0', '0', '0', '0', '1', '1');
INSERT INTO `blog_ex` VALUES ('26', '42', '0', '4', '1', '1', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `blog_ex` VALUES ('27', '43', '0', '5', '1', '1', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `blog_ex` VALUES ('28', '44', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `blog_ex` VALUES ('29', '45', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `blog_ex` VALUES ('30', '46', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `blog_ex` VALUES ('31', '47', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `blog_ex` VALUES ('32', '48', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `blog_ex` VALUES ('33', '49', '0', '5', '1', '1', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `blog_ex` VALUES ('34', '50', '0', '2', '1', '1', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `blog_ex` VALUES ('35', '51', '0', '3', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `blog_ex` VALUES ('36', '52', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `blog_ex` VALUES ('37', '53', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `blog_ex` VALUES ('38', '54', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `blog_ex` VALUES ('39', '55', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `blog_ex` VALUES ('40', '56', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `blog_ex` VALUES ('41', '57', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `blog_ex` VALUES ('42', '58', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `blog_ex` VALUES ('43', '59', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `blog_ex` VALUES ('44', '60', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `blog_ex` VALUES ('45', '61', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `blog_ex` VALUES ('46', '62', '0', '5', '1', '1', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `blog_ex` VALUES ('47', '63', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `blog_ex` VALUES ('48', '64', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `blog_ex` VALUES ('49', '65', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `blog_ex` VALUES ('50', '66', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `blog_ex` VALUES ('51', '67', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `blog_ex` VALUES ('52', '68', '0', '1', '1', '1', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `blog_ex` VALUES ('53', '69', '0', '1', '1', '1', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `blog_ex` VALUES ('54', '70', '0', '7', '1', '1', '0', '0', '0', '0', '1', '1', '5');
INSERT INTO `blog_ex` VALUES ('55', '71', '0', '1', '1', '1', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `blog_ex` VALUES ('56', '72', '0', '4', '2', '0', '1', '0', '0', '0', '1', '2', '6');
INSERT INTO `blog_ex` VALUES ('57', '73', '0', '4', '3', '1', '0', '0', '0', '0', '1', '1', '5');
INSERT INTO `blog_ex` VALUES ('58', '74', '11', '11', '3', '0', '1', '0', '0', '0', '1', '2', '6');
INSERT INTO `blog_ex` VALUES ('59', '75', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `blog_ex` VALUES ('60', '76', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `blog_ex` VALUES ('61', '77', '3', '26', '0', '0', '1', '0', '0', '0', '1', '2', '6');
INSERT INTO `blog_ex` VALUES ('62', '78', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `blog_ex` VALUES ('63', '79', '0', '11', '3', '1', '1', '0', '0', '1', '0', '2', '5');
INSERT INTO `blog_ex` VALUES ('64', '80', '0', '0', '0', '0', '0', '0', '0', '0', '1', '1', '5');
INSERT INTO `blog_ex` VALUES ('65', '81', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `blog_ex` VALUES ('66', '82', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `blog_ex` VALUES ('67', '83', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `blog_ex` VALUES ('68', '84', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `blog_ex` VALUES ('69', '85', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `blog_ex` VALUES ('70', '86', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `blog_ex` VALUES ('71', '87', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `blog_ex` VALUES ('72', '88', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `blog_ex` VALUES ('73', '89', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `blog_ex` VALUES ('74', '90', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `blog_ex` VALUES ('75', '91', '4', '13', '2', '1', '0', '0', '0', '1', '1', '2', '9');
INSERT INTO `blog_ex` VALUES ('76', '92', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `blog_ex` VALUES ('77', '93', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `blog_ex` VALUES ('78', '94', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `blog_ex` VALUES ('79', '95', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `blog_ex` VALUES ('80', '96', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `blog_ex` VALUES ('81', '97', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `blog_ex` VALUES ('82', '98', '5', '7', '5', '2', '0', '0', '0', '0', '1', '1', '5');
INSERT INTO `blog_ex` VALUES ('83', '99', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `blog_ex` VALUES ('84', '100', '0', '0', '0', '0', '0', '0', '0', '0', '1', '1', '5');
INSERT INTO `blog_ex` VALUES ('85', '101', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `blog_ex` VALUES ('86', '102', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `blog_ex` VALUES ('87', '103', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');

-- ----------------------------
-- Table structure for blog_sense
-- ----------------------------
DROP TABLE IF EXISTS `blog_sense`;
CREATE TABLE `blog_sense` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `blog_id` int(11) DEFAULT NULL COMMENT '关联的 blogId',
  `name` varchar(256) DEFAULT NULL COMMENT '顶踩的用户',
  `email` varchar(256) DEFAULT NULL COMMENT '顶踩的用户的邮箱',
  `request_ip` varchar(256) DEFAULT NULL,
  `sense` varchar(10) DEFAULT NULL COMMENT '顶/踩',
  `score` int(11) DEFAULT NULL COMMENT '是否选中',
  `created_at` varchar(32) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_3` (`blog_id`),
  CONSTRAINT `FK_Reference_3` FOREIGN KEY (`blog_id`) REFERENCES `blog` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8 COMMENT='博客顶踩记录表';

-- ----------------------------
-- Records of blog_sense
-- ----------------------------
INSERT INTO `blog_sense` VALUES ('1', '-2', 'null', '', '127.0.0.1', '1', '1', '2017-06-06 21:09:26');
INSERT INTO `blog_sense` VALUES ('2', '-2', 'null', '', '127.0.0.1', '1', '1', '2017-06-06 21:09:26');
INSERT INTO `blog_sense` VALUES ('3', '-2', 'null', '970655147@qq.com', '127.0.0.1', '1', '1', '2017-06-06 21:45:15');
INSERT INTO `blog_sense` VALUES ('4', '-2', 'admin', '970655147@qq.com', '127.0.0.1', '1', '1', '2017-06-06 21:50:59');
INSERT INTO `blog_sense` VALUES ('5', '-1', 'null', 'null', '127.0.0.1', '1', '1', '2017-06-06 22:34:53');
INSERT INTO `blog_sense` VALUES ('6', '-1', 'admin', '970655147@qq.com', '127.0.0.1', '1', '1', '2017-06-06 22:39:13');
INSERT INTO `blog_sense` VALUES ('7', '-1', 'admin', '970655147@qq.com', '127.0.0.1', '1', '1', '2017-06-06 22:40:13');
INSERT INTO `blog_sense` VALUES ('8', '-3', 'admin', '970655147@qq.com', '127.0.0.1', '1', '1', '2017-06-09 20:10:32');
INSERT INTO `blog_sense` VALUES ('9', '-1', 'null', 'null', '127.0.0.1', '1', '1', '2017-06-10 08:11:21');
INSERT INTO `blog_sense` VALUES ('23', '-1', 'admin', 'null', '127.0.0.1', '1', '1', '2017-06-10 09:23:46');
INSERT INTO `blog_sense` VALUES ('24', '41', 'admin', '970655147@qq.com', '127.0.0.1', '1', '1', '2017-06-10 09:39:10');
INSERT INTO `blog_sense` VALUES ('25', '-2', 'admin', '970655147@qq.com', '127.0.0.1', '1', '1', '2017-06-10 18:29:55');
INSERT INTO `blog_sense` VALUES ('26', '-1', 'null', 'null', '127.0.0.1', '1', '1', '2017-06-10 20:39:27');
INSERT INTO `blog_sense` VALUES ('27', '-1', 'admin', '970655147@qq.com', '0:0:0:0:0:0:0:1', '1', '1', '2017-06-11 00:44:46');
INSERT INTO `blog_sense` VALUES ('28', '-1', 'admin', '970655147@qq.com', '0:0:0:0:0:0:0:1', '1', '1', '2017-06-11 00:50:03');
INSERT INTO `blog_sense` VALUES ('29', '-1', 'admin', '970655147@qq.com', '0:0:0:0:0:0:0:1', '1', '1', '2017-06-11 01:09:26');
INSERT INTO `blog_sense` VALUES ('30', '-1', 'admin', '970655147@qq.com', '0:0:0:0:0:0:0:1', '1', '1', '2017-06-11 18:11:10');
INSERT INTO `blog_sense` VALUES ('31', '-1', 'null', 'null', '0:0:0:0:0:0:0:1', '1', '1', '2017-06-11 20:54:56');
INSERT INTO `blog_sense` VALUES ('32', '-1', 'null', 'null', '0:0:0:0:0:0:0:1', '1', '1', '2017-06-11 21:04:42');
INSERT INTO `blog_sense` VALUES ('33', '77', 'admin', '970655147@qq.com', '0:0:0:0:0:0:0:1', '1', '1', '2017-06-17 17:29:32');
INSERT INTO `blog_sense` VALUES ('34', '74', 'admin', '970655147@qq.com', '0:0:0:0:0:0:0:1', '1', '1', '2017-06-17 18:16:57');
INSERT INTO `blog_sense` VALUES ('35', '-1', 'admin', '970655147@qq.com', '118.113.4.204', '1', '1', '2017-06-17 22:24:08');
INSERT INTO `blog_sense` VALUES ('36', '72', 'admin', '970655147@qq.com', '118.113.4.204', '1', '1', '2017-06-18 09:13:52');
INSERT INTO `blog_sense` VALUES ('37', '79', 'admin', '970655147@qq.com', '171.214.202.65', '1', '1', '2017-06-23 20:19:04');
INSERT INTO `blog_sense` VALUES ('38', '-1', 'admin', '970655147@qq.com', '171.214.202.65', '1', '1', '2017-06-23 20:49:57');
INSERT INTO `blog_sense` VALUES ('39', '77', '', '', '0:0:0:0:0:0:0:1', '1', '5', '2017-06-23 22:02:33');
INSERT INTO `blog_sense` VALUES ('40', '77', 'admin', '970655147@qq.com', '171.214.202.65', '1', '5', '2017-06-23 22:20:32');
INSERT INTO `blog_sense` VALUES ('41', '74', 'admin', '970655147@qq.com', '171.214.202.65', '1', '5', '2017-06-23 22:28:09');
INSERT INTO `blog_sense` VALUES ('42', '73', '', '', '0:0:0:0:0:0:0:1', '1', '5', '2017-06-23 22:32:00');
INSERT INTO `blog_sense` VALUES ('43', '72', '', '', '0:0:0:0:0:0:0:1', '1', '5', '2017-06-23 22:32:50');
INSERT INTO `blog_sense` VALUES ('44', '70', '', '', '0:0:0:0:0:0:0:1', '1', '5', '2017-06-23 22:36:12');
INSERT INTO `blog_sense` VALUES ('45', '70', 'admin', '970655147@qq.com', '171.214.202.65', '1', '5', '2017-06-23 22:43:19');
INSERT INTO `blog_sense` VALUES ('46', '79', '', '', '0:0:0:0:0:0:0:1', '1', '4', '2017-06-24 09:15:36');
INSERT INTO `blog_sense` VALUES ('47', '80', 'zhangsan', '9139834@124.com', '0:0:0:0:0:0:0:1', '1', '5', '2017-06-24 20:52:35');
INSERT INTO `blog_sense` VALUES ('48', '98', 'admin', '970655147@qq.com', '118.113.6.18', '1', '5', '2017-06-27 22:05:47');
INSERT INTO `blog_sense` VALUES ('49', '91', 'admin', '970655147@qq.com', '118.113.6.18', '1', '5', '2017-06-27 22:09:21');
INSERT INTO `blog_sense` VALUES ('50', '-1', 'admin', '970655147@qq.com', '118.113.4.196', '1', '1', '2017-06-28 19:34:22');
INSERT INTO `blog_sense` VALUES ('51', '100', 'admin', '970655147@qq.com', '118.113.6.36', '1', '5', '2017-06-29 20:34:56');
INSERT INTO `blog_sense` VALUES ('52', '98', 'admin', '970655147@qq.com', '118.113.6.36', '1', '5', '2017-06-29 21:32:32');
INSERT INTO `blog_sense` VALUES ('53', '98', '', '', '0:0:0:0:0:0:0:1', '1', '5', '2017-06-29 21:42:43');
INSERT INTO `blog_sense` VALUES ('54', '-1', 'admin', '970655147@qq.com', '118.113.6.36', '1', '1', '2017-06-29 21:48:03');
INSERT INTO `blog_sense` VALUES ('55', '-1', 'admin', '970655147@qq.com', '171.214.202.25', '1', '1', '2017-06-30 18:39:55');
INSERT INTO `blog_sense` VALUES ('56', '-2', 'admin', '970655147@qq.com', '171.214.202.25', '1', '5', '2017-06-30 18:49:36');
INSERT INTO `blog_sense` VALUES ('57', '-3', 'admin', '970655147@qq.com', '171.214.202.25', '1', '5', '2017-06-30 18:49:44');
INSERT INTO `blog_sense` VALUES ('58', '98', 'admin', '970655147@qq.com', '171.214.202.169', '1', '5', '2017-07-05 22:38:05');
INSERT INTO `blog_sense` VALUES ('59', '91', 'admin', '970655147@qq.com', '118.113.4.241', '1', '4', '2017-09-10 11:50:13');
INSERT INTO `blog_sense` VALUES ('60', '-2', 'admin', '970655147@qq.com', '118.113.4.241', '1', '5', '2017-09-10 11:57:40');
INSERT INTO `blog_sense` VALUES ('61', '-2', '', '', '0:0:0:0:0:0:0:1', '1', '5', '2018-03-01 18:53:23');
INSERT INTO `blog_sense` VALUES ('62', '-3', '', '', '192.168.0.121', '1', '5', '2018-03-02 18:53:00');
INSERT INTO `blog_sense` VALUES ('63', '-1', 'null', 'null', '192.168.0.121', '1', '1', '2018-03-02 19:36:01');
INSERT INTO `blog_sense` VALUES ('64', '-2', '', '', '192.168.0.121', '1', '5', '2018-03-02 19:37:11');

-- ----------------------------
-- Table structure for blog_tag
-- ----------------------------
DROP TABLE IF EXISTS `blog_tag`;
CREATE TABLE `blog_tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(256) DEFAULT NULL COMMENT '名称',
  `sort` int(11) DEFAULT NULL COMMENT '排序字段',
  `created_at` varchar(32) DEFAULT NULL COMMENT '创建时间',
  `updated_at` varchar(32) DEFAULT NULL COMMENT '修改时间',
  `deleted` tinyint(1) DEFAULT NULL COMMENT '是否被删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8 COMMENT='标签表';

-- ----------------------------
-- Records of blog_tag
-- ----------------------------
INSERT INTO `blog_tag` VALUES ('15', 'java', '80', '2017-06-03 10:10:20', '2017-06-10 16:51:59', '0');
INSERT INTO `blog_tag` VALUES ('16', 'redis', '170', '2017-06-03 10:10:31', '2017-06-10 16:52:06', '0');
INSERT INTO `blog_tag` VALUES ('17', 'spring', '200', '2017-06-03 10:10:38', '2017-06-03 10:10:38', '0');
INSERT INTO `blog_tag` VALUES ('18', 'others', '140', '2017-06-03 10:10:46', '2017-06-03 10:10:46', '0');
INSERT INTO `blog_tag` VALUES ('21', 'C/C++', '0', '2017-06-10 09:53:02', '2017-06-10 09:57:32', '0');
INSERT INTO `blog_tag` VALUES ('22', 'tomcat', '210', '2017-06-10 10:08:01', '2017-06-10 10:08:01', '0');
INSERT INTO `blog_tag` VALUES ('23', 'httpClient', '70', '2017-06-10 11:00:16', '2017-06-10 11:00:16', '0');
INSERT INTO `blog_tag` VALUES ('24', 'alogrithm', '10', '2017-06-10 11:37:06', '2017-06-10 11:37:06', '0');
INSERT INTO `blog_tag` VALUES ('25', 'dataStructure', '40', '2017-06-10 11:37:13', '2017-06-10 11:37:27', '0');
INSERT INTO `blog_tag` VALUES ('32', 'designPattern', '50', '2017-06-10 12:25:37', '2017-06-10 12:25:37', '0');
INSERT INTO `blog_tag` VALUES ('33', 'utils', '230', '2017-06-10 13:53:30', '2017-06-10 13:53:30', '0');
INSERT INTO `blog_tag` VALUES ('34', 'linux', '110', '2017-06-10 14:16:04', '2017-06-10 14:16:04', '0');
INSERT INTO `blog_tag` VALUES ('35', 'ubuntu', '220', '2017-06-10 14:16:13', '2017-06-10 14:16:13', '0');
INSERT INTO `blog_tag` VALUES ('36', 'hadoop', '60', '2017-06-10 14:16:19', '2017-06-10 14:16:19', '0');
INSERT INTO `blog_tag` VALUES ('37', 'spark', '190', '2017-06-10 14:16:24', '2017-06-10 14:16:24', '0');
INSERT INTO `blog_tag` VALUES ('38', 'python', '150', '2017-06-10 14:16:31', '2017-06-10 14:16:31', '0');
INSERT INTO `blog_tag` VALUES ('39', 'scala', '180', '2017-06-10 14:16:36', '2017-06-10 14:16:36', '0');
INSERT INTO `blog_tag` VALUES ('40', 'js', '100', '2017-06-10 14:26:11', '2017-06-10 14:26:11', '0');
INSERT INTO `blog_tag` VALUES ('41', 'css', '30', '2017-06-10 14:26:19', '2017-06-10 14:26:19', '0');
INSERT INTO `blog_tag` VALUES ('42', 'dummy', '20', '2017-06-10 14:42:30', '2017-06-24 18:40:32', '0');
INSERT INTO `blog_tag` VALUES ('43', 'question', '160', '2017-06-10 14:47:51', '2017-06-10 14:47:51', '0');
INSERT INTO `blog_tag` VALUES ('44', 'nexus', '130', '2017-06-10 14:54:28', '2017-06-10 14:54:28', '0');
INSERT INTO `blog_tag` VALUES ('45', 'maven', '120', '2017-06-10 14:54:34', '2017-06-10 14:54:34', '0');
INSERT INTO `blog_tag` VALUES ('46', 'jenkins', '90', '2017-06-10 14:54:47', '2017-06-10 14:54:47', '0');

-- ----------------------------
-- Table structure for blog_type
-- ----------------------------
DROP TABLE IF EXISTS `blog_type`;
CREATE TABLE `blog_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(256) DEFAULT NULL COMMENT '名称',
  `sort` int(11) DEFAULT NULL COMMENT '排序字段',
  `created_at` varchar(32) DEFAULT NULL COMMENT '创建时间',
  `updated_at` varchar(32) DEFAULT NULL COMMENT '修改时间',
  `deleted` tinyint(1) DEFAULT NULL COMMENT '是否被删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8 COMMENT='标签分类';

-- ----------------------------
-- Records of blog_type
-- ----------------------------
INSERT INTO `blog_type` VALUES ('41', '技术分享', '30', '2017-06-01 08:38:45', '2017-06-10 16:13:22', '0');
INSERT INTO `blog_type` VALUES ('42', '生活琐碎', '10', '2017-06-01 08:39:05', '2017-06-01 08:39:05', '0');
INSERT INTO `blog_type` VALUES ('43', '日常水贴', '0', '2017-06-01 08:39:24', '2017-06-01 08:39:24', '0');
INSERT INTO `blog_type` VALUES ('44', '问题总结', '20', '2017-06-01 08:39:59', '2017-06-01 08:39:59', '0');

-- ----------------------------
-- Table structure for blog_visit_log
-- ----------------------------
DROP TABLE IF EXISTS `blog_visit_log`;
CREATE TABLE `blog_visit_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `blog_id` int(11) DEFAULT NULL COMMENT '访问的博客的eid',
  `name` varchar(256) DEFAULT NULL COMMENT '访客的用户名',
  `email` varchar(60) DEFAULT NULL COMMENT '访客的邮箱',
  `is_system_user` tinyint(1) DEFAULT NULL COMMENT '是否是系统用户',
  `request_ip` varchar(256) DEFAULT NULL COMMENT '访客的ip',
  `created_at_day` varchar(32) DEFAULT NULL COMMENT '创建的日期[粒度为天]',
  `created_at` varchar(32) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_13` (`blog_id`),
  CONSTRAINT `FK_Reference_13` FOREIGN KEY (`blog_id`) REFERENCES `blog` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2673 DEFAULT CHARSET=utf8 COMMENT='记录博客访问记录的表';

-- ----------------------------
-- Records of blog_visit_log
-- ----------------------------

-- ----------------------------
-- Table structure for email
-- ----------------------------
DROP TABLE IF EXISTS `email`;
CREATE TABLE `email` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `from` varchar(256) DEFAULT NULL COMMENT '发件人',
  `to` varchar(2048) DEFAULT NULL COMMENT '收件人列表',
  `cc` varchar(2048) DEFAULT NULL COMMENT 'carbon copy 列表',
  `subject` varchar(2048) DEFAULT NULL COMMENT '主题',
  `content` varchar(8192) DEFAULT NULL COMMENT '邮件内容',
  `contentType` varchar(256) DEFAULT NULL COMMENT '邮件类型',
  `createdAt` varchar(32) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 COMMENT='邮件记录';

-- ----------------------------
-- Records of email
-- ----------------------------
INSERT INTO `email` VALUES ('1', 'hexiong@cdqcp.com', '[\"970655147@qq.com\"]', '[]', '发表博客提醒', 'admin 发表了一篇博客 : 11', '1', '2017-06-26 22:04:26');
INSERT INTO `email` VALUES ('2', 'hexiong@cdqcp.com', '[\"970655147@qq.com\"]', '[]', '[HXBlog]博客提醒', ' 用户 [admin] 发表了一篇博客 :  <a href=\'http://localhost:8080/static/main/blogDetail.html?id=97\' color=\'red\' > csdn图片服务器</a>, 请注意审核该内容 ! ', '1', '2017-06-27 21:07:54');
INSERT INTO `email` VALUES ('3', 'hexiong@cdqcp.com', '[\"970655147@qq.com\", \"970655147@qq.com\"]', '[]', '[HXBlog]博客提醒', ' 用户 [admin] 发表了一篇博客 :  <a href=\'http://localhost:8080/static/main/blogDetail.html?id=98\' color=\'red\' > 关之琳大美女</a>, 请注意审核该内容 ! ', '1', '2017-06-27 21:34:10');
INSERT INTO `email` VALUES ('4', 'hexiong@cdqcp.com', '[\"970655147@qq.com\", \"970655147@qq.com\"]', '[]', '[HXBlog]博客提醒', ' 用户 [admin] 发表了一篇博客 :  <a href=\'http://localhost:8080/static/main/blogDetail.html?id=99\' color=\'red\' > 关之琳大美女</a>, 请注意审核该内容 ! ', '1', '2017-06-27 21:34:20');
INSERT INTO `email` VALUES ('5', 'hexiong@cdqcp.com', '[\"970655147@qq.com\", \"970655147@qq.com\"]', '[]', '[HXBlog]博客提醒', ' 用户 [admin] 移除了一篇博客 :  原url : <a href=\'http://localhost:8080/static/main/blogDetail.html?id=99\' color=\'red\' > 关之琳大美女</a>, 请知晓 ! ', '1', '2017-06-27 21:36:19');
INSERT INTO `email` VALUES ('6', 'hexiong@cdqcp.com', '[\"970655147@qq.com\"]', '[]', '[HXBlog]评论提醒', ' 用户 [admin] 评论了您的文章 : <html>\n <head></head>\n <body>\n  关之琳大美人 !\n </body>\n</html>,  <a href=\'http://localhost:8080/static/main/blogDetail.html?id=98\' color=\'red\' > 博客 98</a>, 请知晓 ! ', '1', '2017-06-27 22:04:11');
INSERT INTO `email` VALUES ('7', 'hexiong@cdqcp.com', '[\"970655147@qq.com\", \"970655147@qq.com\"]', '[]', '[HXBlog]评论提醒', ' 用户 [admin] 为博客  <a href=\'http://localhost:8080/static/main/blogDetail.html?id=98\' 新增了一条评论 : <html>\n <head></head>\n <body>\n  关之琳大美人 !\n </body>\n</html>,  color=\'red\' > 博客 98</a>, 请知晓 ! ', '1', '2017-06-27 22:04:12');
INSERT INTO `email` VALUES ('8', 'hexiong@cdqcp.com', '[\"970655147@qq.com\"]', '[]', '[HXBlog]打分提醒', ' 用户 [admin] 为您的文章打了 5分 !,   <a href=\'http://localhost:8080/static/main/blogDetail.html?id=91\' color=\'red\' > 博客 csdn图片服务器</a>, 请知晓 ! ', '1', '2017-06-27 22:09:21');
INSERT INTO `email` VALUES ('9', 'hexiong@cdqcp.com', '[\"970655147@qq.com\"]', '[]', '[HXBlog]打分提醒', ' 用户 [admin] 为您的文章打了 1分 !,   <a href=\'http://localhost:8080/static/main/blogDetail.html?id=-1\' color=\'red\' > 项目上下文信息</a>, 请知晓 ! ', '1', '2017-06-28 19:34:22');
INSERT INTO `email` VALUES ('10', 'hexiong@cdqcp.com', '[\"970655147@qq.com\", \"970655147@qq.com\"]', '[]', '[HXBlog]博客提醒', ' 用户 [admin] 发表了一篇博客 :  <a href=\'http://localhost:8080/static/main/blogDetail.html?id=100\' color=\'red\' > 11</a>, 请注意审核该内容 ! ', 'text/html;charset=UTF-8', '2017-06-28 21:25:12');
INSERT INTO `email` VALUES ('11', 'hexiong@cdqcp.com', '[\"970655147@qq.com\"]', '[]', '[HXBlog]打分提醒', ' 用户 [admin] 为您的文章打了 5分 !,   <a href=\'http://localhost:8080/static/main/blogDetail.html?id=100\' color=\'red\' > 11</a>, 请知晓 ! ', 'text/html;charset=UTF-8', '2017-06-29 20:34:56');
INSERT INTO `email` VALUES ('12', 'hexiong@cdqcp.com', '[\"970655147@qq.com\", \"970655147@qq.com\"]', '[]', '[HXBlog]博客提醒', ' 用户 [admin] 发表了一篇博客 :  <a href=\'http://localhost:8080/static/main/blogDetail.html?id=101\' color=\'red\' > 13</a>, 请注意审核该内容 ! ', 'text/html;charset=UTF-8', '2017-06-29 20:41:59');
INSERT INTO `email` VALUES ('13', 'hexiong@cdqcp.com', '[\"970655147@qq.com\", \"970655147@qq.com\"]', '[]', '111', '<html>\n <head></head>\n <body>\n  <p>31313</p>\n </body>\n</html>', 'text/html;charset=UTF-8', '2017-06-29 21:15:36');
INSERT INTO `email` VALUES ('14', 'hexiong@cdqcp.com', '[\"970655147@qq.com\", \"970655147@qq.com\"]', '[]', '[HXBlog]博客提醒', ' 用户 [admin] 移除了一篇博客 :  原url : <a href=\'http://localhost:8080/static/main/blogDetail.html?id=101\' color=\'red\' > 13</a>, 请知晓 ! ', 'text/html;charset=UTF-8', '2017-06-29 21:26:55');
INSERT INTO `email` VALUES ('15', 'hexiong@cdqcp.com', '[\"970655147@qq.com\", \"970655147@qq.com\"]', '[]', '[HXBlog]博客提醒', ' 用户 [admin] 移除了一篇博客 :  原url : <a href=\'http://localhost:8080/static/main/blogDetail.html?id=100\' color=\'red\' > 11</a>, 请知晓 ! ', 'text/html;charset=UTF-8', '2017-06-29 21:27:26');
INSERT INTO `email` VALUES ('16', 'hexiong@cdqcp.com', '[\"970655147@qq.com\"]', '[]', '[HXBlog]打分提醒', ' 用户 [admin] 为您的文章打了 5分 !,   <a href=\'http://localhost:8080/static/main/blogDetail.html?id=98\' color=\'red\' > 关之琳大美女</a>, 请知晓 ! ', 'text/html;charset=UTF-8', '2017-06-29 21:32:32');
INSERT INTO `email` VALUES ('17', 'hexiong@cdqcp.com', '[\"970655147@qq.com\"]', '[]', '[HXBlog]打分提醒', ' 用户 [admin] 为您的文章打了 5分 !,   <a href=\'http://localhost:8080/static/main/blogDetail.html?id=98\' color=\'red\' > 关之琳大美女</a>, 请知晓 ! ', 'text/html;charset=UTF-8', '2017-06-29 21:45:37');
INSERT INTO `email` VALUES ('18', 'hexiong@cdqcp.com', '[\"970655147@qq.com\"]', '[]', '[HXBlog]打分提醒', ' 用户 [admin] 为您的文章打了 1分 !,   <a href=\'http://localhost:8080/static/main/blogDetail.html?id=-1\' color=\'red\' > 项目上下文信息</a>, 请知晓 ! ', 'text/html;charset=UTF-8', '2017-06-29 21:48:03');
INSERT INTO `email` VALUES ('19', 'hexiong@cdqcp.com', '[\"970655147@qq.com\"]', '[]', '[HXBlog]打分提醒', ' 用户 [admin] 为您的文章打了 1分 !,   <a href=\'http://localhost:8080/static/main/blogDetail.html?id=-1\' color=\'red\' > 项目上下文信息</a>, 请知晓 ! ', 'text/html;charset=UTF-8', '2017-06-30 18:39:56');
INSERT INTO `email` VALUES ('20', 'hexiong@cdqcp.com', '[\"970655147@qq.com\"]', '[]', '[HXBlog]打分提醒', ' 用户 [admin] 为您的文章打了 5分 !,   <a href=\'http://localhost:8080/static/main/blogDetail.html?id=-2\' color=\'red\' > 项目建议信息</a>, 请知晓 ! ', 'text/html;charset=UTF-8', '2017-06-30 18:49:36');
INSERT INTO `email` VALUES ('21', 'hexiong@cdqcp.com', '[\"970655147@qq.com\"]', '[]', '[HXBlog]评论提醒', ' 用户 [admin] 评论了您的文章 : <html>\n <head></head>\n <body>\n  98695486sldkjf ⊙\n </body>\n</html>,  <a href=\'http://localhost:8080/static/main/blogDetail.html?id=27\' color=\'red\' > 关于java中有几种类型的变量</a>, 请知晓 ! ', 'text/html;charset=UTF-8', '2017-06-30 20:20:03');
INSERT INTO `email` VALUES ('22', 'hexiong@cdqcp.com', '[\"970655147@qq.com\", \"970655147@qq.com\"]', '[]', '[HXBlog]评论提醒', ' 用户 [admin] 为博客  <a href=\'http://localhost:8080/static/main/blogDetail.html?id=27\' color=\'red\' > 关于java中有几种类型的变量</a>,  新增了一条评论 : <html>\n <head></head>\n <body>\n  98695486sldkjf ⊙\n </body>\n</html>, 请知晓 ! ', 'text/html;charset=UTF-8', '2017-06-30 20:20:05');
INSERT INTO `email` VALUES ('23', 'hexiong@cdqcp.com', '[\"970655147@qq.com\"]', '[]', '[HXBlog]评论提醒', ' 用户 [Jerry.X.He] 评论了您的文章 : <html>\n <head></head>\n <body>\n  31313\n </body>\n</html>,  <a href=\'http://localhost:8080/static/main/blogDetail.html?id=27\' color=\'red\' > 关于java中有几种类型的变量</a>, 请知晓 ! ', 'text/html;charset=UTF-8', '2017-06-30 20:43:57');
INSERT INTO `email` VALUES ('24', 'hexiong@cdqcp.com', '[\"970655147@qq.com\", \"970655147@qq.com\"]', '[]', '[HXBlog]评论提醒', ' 用户 [Jerry.X.He] 为博客  <a href=\'http://localhost:8080/static/main/blogDetail.html?id=27\' color=\'red\' > 关于java中有几种类型的变量</a>,  新增了一条评论 : <html>\n <head></head>\n <body>\n  31313\n </body>\n</html>, 请知晓 ! ', 'text/html;charset=UTF-8', '2017-06-30 20:43:59');
INSERT INTO `email` VALUES ('25', 'hexiong@cdqcp.com', '[\"970655147@qq.com\"]', '[]', '[HXBlog]评论提醒', ' 用户 [Jerry.X.He] 评论了您的文章 : <html>\n <head></head>\n <body>\n  1313131111\n </body>\n</html>,  <a href=\'http://localhost:8080/static/main/blogDetail.html?id=27\' color=\'red\' > 关于java中有几种类型的变量</a>, 请知晓 ! ', 'text/html;charset=UTF-8', '2017-06-30 20:46:41');
INSERT INTO `email` VALUES ('26', 'hexiong@cdqcp.com', '[\"970655147@qq.com\", \"970655147@qq.com\"]', '[]', '[HXBlog]评论提醒', ' 用户 [Jerry.X.He] 为博客  <a href=\'http://localhost:8080/static/main/blogDetail.html?id=27\' color=\'red\' > 关于java中有几种类型的变量</a>,  新增了一条评论 : <html>\n <head></head>\n <body>\n  1313131111\n </body>\n</html>, 请知晓 ! ', 'text/html;charset=UTF-8', '2017-06-30 20:46:42');

-- ----------------------------
-- Table structure for exception_log
-- ----------------------------
DROP TABLE IF EXISTS `exception_log`;
CREATE TABLE `exception_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `url` varchar(256) DEFAULT NULL COMMENT '请求的url',
  `handler` varchar(256) DEFAULT NULL COMMENT '处理请求的方法',
  `params` varchar(4096) DEFAULT NULL COMMENT '请求的参数信息',
  `headers` varchar(4096) DEFAULT NULL COMMENT '请求的请求头信息',
  `name` varchar(256) DEFAULT NULL COMMENT '触发异常的用户',
  `email` varchar(60) DEFAULT NULL COMMENT '触发异常的用户的邮箱',
  `is_system_user` tinyint(1) DEFAULT NULL COMMENT '是否是系统用户',
  `request_ip` varchar(256) DEFAULT NULL COMMENT '触发异常的用户的ip',
  `msg` varchar(4096) DEFAULT NULL COMMENT '异常信息',
  `created_at` varchar(32) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1937 DEFAULT CHARSET=utf8 COMMENT='异常记录表';

-- ----------------------------
-- Records of exception_log
-- ----------------------------

-- ----------------------------
-- Table structure for images
-- ----------------------------
DROP TABLE IF EXISTS `images`;
CREATE TABLE `images` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(256) DEFAULT NULL COMMENT '标题',
  `url` varchar(2048) DEFAULT NULL COMMENT '图片url',
  `type` varchar(128) DEFAULT NULL COMMENT '图片类型(show, headImg)',
  `sort` int(11) DEFAULT NULL COMMENT '排序字段',
  `created_at` varchar(32) DEFAULT NULL COMMENT '创建时间',
  `updated_at` varchar(32) DEFAULT NULL COMMENT '修改时间',
  `enable` tinyint(1) DEFAULT NULL COMMENT '是否可用',
  `deleted` tinyint(1) DEFAULT NULL COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8 COMMENT='图片';

-- ----------------------------
-- Records of images
-- ----------------------------
INSERT INTO `images` VALUES ('29', '张雪迎', 'http://wx3.sinaimg.cn/mw690/4eac422ely1ffz5mex9n7j21ye2xl7wl.jpg', '2', '0', '2017-05-31 09:15:47', '2017-06-10 18:39:28', '1', '0');
INSERT INTO `images` VALUES ('30', '赵丽颖', 'http://img.mp.itc.cn/upload/20170528/2e8354b1c67347278f5c574d78aac0b1_th.jpg', '2', '10', '2017-05-31 09:18:03', '2017-05-31 06:18:07', '1', '0');
INSERT INTO `images` VALUES ('31', '乔欣', 'http://images.china.cn/attachement/jpg/site1000/20170508/c03fd55e3b6d1a7a59a428.jpg', '2', '20', '2017-05-31 09:19:51', '2017-05-31 06:19:59', '1', '0');
INSERT INTO `images` VALUES ('32', 'csdnHeadImg', 'http://avatar.csdn.net/C/D/C/1_u011039332.jpg', '1', '0', '2017-05-31 09:39:04', '2017-06-28 20:02:38', '1', '0');
INSERT INTO `images` VALUES ('38', 'tiebaHeadImg', 'https://gss0.bdstatic.com/6LZ1dD3d1sgCo2Kml5_Y_D3/sys/portrait/item/48c0c0b6b7e7393730363535313437f02d', '1', '10', '2017-06-04 12:04:40', '2017-06-04 12:04:40', '1', '0');
INSERT INTO `images` VALUES ('39', 'headImg03', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497100627788&di=52eb6dc3050d2990517a095c528e0fe7&imgtype=0&src=http%3A%2F%2Ffile.popoho.com%2F2016-08-05%2Fd501f852d63dd41940f4a8b2d40748c8.jpg', '1', '20', '2017-06-10 18:29:22', '2017-06-10 18:29:22', '1', '0');

-- ----------------------------
-- Table structure for interf
-- ----------------------------
DROP TABLE IF EXISTS `interf`;
CREATE TABLE `interf` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(256) DEFAULT NULL COMMENT '角色名称',
  `desc` varchar(2048) DEFAULT NULL COMMENT '角色描述',
  `sort` int(11) DEFAULT NULL COMMENT '排序字段',
  `created_at` varchar(32) DEFAULT NULL COMMENT '创建时间',
  `updated_at` varchar(32) DEFAULT NULL COMMENT '修改时间',
  `enable` tinyint(1) DEFAULT NULL COMMENT '是否可用',
  `deleted` tinyint(1) DEFAULT NULL COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8 COMMENT='接口列表';

-- ----------------------------
-- Records of interf
-- ----------------------------
INSERT INTO `interf` VALUES ('1', '/HXBlog_V2.0//admin/blog/save', '添加博客', '0', '2017-06-01 08:15:39', '2017-06-12 21:16:33', '1', '0');
INSERT INTO `interf` VALUES ('5', '/HXBlog_V2.0//admin/blog/*', '博客管理', '10', '2017-06-12 20:09:46', '2017-06-12 20:09:46', '1', '0');
INSERT INTO `interf` VALUES ('6', '/HXBlog_V2.0//admin/comment/*', '评论管理', '30', '2017-06-12 20:14:53', '2017-06-12 20:14:53', '1', '0');
INSERT INTO `interf` VALUES ('7', '/HXBlog_V2.0//admin/message/add', '发送消息', '50', '2017-06-12 20:16:40', '2017-06-12 20:16:40', '1', '0');
INSERT INTO `interf` VALUES ('8', '/HXBlog_V2.0//admin/message/list', '查收消息', '60', '2017-06-12 20:16:56', '2017-06-12 20:16:56', '1', '0');
INSERT INTO `interf` VALUES ('9', '/HXBlog_V2.0//admin/message/*', '消息管理', '80', '2017-06-12 20:17:11', '2017-06-12 20:17:11', '1', '0');
INSERT INTO `interf` VALUES ('10', '/HXBlog_V2.0//admin/tag/*', '标签管理', '90', '2017-06-12 20:21:24', '2017-06-12 20:21:24', '1', '0');
INSERT INTO `interf` VALUES ('11', '/HXBlog_V2.0//admin/type/*', '类型管理', '100', '2017-06-12 20:21:40', '2017-06-12 20:21:40', '1', '0');
INSERT INTO `interf` VALUES ('12', '/HXBlog_V2.0//admin/user/*', '账号管理', '110', '2017-06-12 20:22:50', '2017-06-12 20:22:50', '1', '0');
INSERT INTO `interf` VALUES ('13', '/HXBlog_V2.0//admin/role/*', '角色管理', '120', '2017-06-12 20:23:04', '2017-06-12 20:23:04', '1', '0');
INSERT INTO `interf` VALUES ('14', '/HXBlog_V2.0//admin/resource/*', '资源管理', '130', '2017-06-12 20:23:17', '2017-06-12 20:23:17', '1', '0');
INSERT INTO `interf` VALUES ('15', '/HXBlog_V2.0//admin/interf/*', '接口管理', '140', '2017-06-12 20:23:30', '2017-06-12 20:23:30', '1', '0');
INSERT INTO `interf` VALUES ('16', '/HXBlog_V2.0//admin/role/userRole/*', '用户角色管理', '150', '2017-06-12 20:24:30', '2017-06-12 21:38:42', '1', '0');
INSERT INTO `interf` VALUES ('17', '/HXBlog_V2.0//admin/resource/roleResource/*', '角色资源管理', '160', '2017-06-12 20:24:56', '2017-06-12 21:40:22', '1', '0');
INSERT INTO `interf` VALUES ('18', '/HXBlog_V2.0//admin/interf/resourceInterf/*', '资源接口管理', '170', '2017-06-12 20:25:21', '2017-06-12 21:39:00', '1', '0');
INSERT INTO `interf` VALUES ('19', '/HXBlog_V2.0//admin/link/*', '友情链接管理', '180', '2017-06-12 20:25:47', '2017-06-12 20:25:47', '1', '0');
INSERT INTO `interf` VALUES ('20', '/HXBlog_V2.0//admin/mood/*', '心情管理', '190', '2017-06-12 20:26:03', '2017-06-12 20:26:03', '1', '0');
INSERT INTO `interf` VALUES ('21', '/HXBlog_V2.0//admin/image/*', '图片管理', '200', '2017-06-12 20:26:22', '2017-06-12 20:27:34', '1', '0');
INSERT INTO `interf` VALUES ('22', '/HXBlog_V2.0//admin/user/updatePwd', '修改密码', '210', '2017-06-12 20:28:21', '2017-06-12 20:28:21', '1', '0');
INSERT INTO `interf` VALUES ('23', '/HXBlog_V2.0//admin/system/refreshConfig', '刷新缓存', '190', '2017-06-12 20:28:49', '2017-06-17 17:13:14', '1', '1');
INSERT INTO `interf` VALUES ('24', '/HXBlog_V2.0//admin/system/statsSummary', '系统监控查询', '220', '2017-06-12 20:29:23', '2017-06-12 20:29:23', '1', '0');
INSERT INTO `interf` VALUES ('25', '/HXBlog_V2.0//admin/upload/image', '上传图片', '230', '2017-06-12 20:36:03', '2017-06-12 20:36:03', '1', '0');
INSERT INTO `interf` VALUES ('26', '/HXBlog_V2.0//admin/upload/file', '上传图片', '240', '2017-06-12 20:36:16', '2017-06-12 20:36:16', '1', '0');
INSERT INTO `interf` VALUES ('27', '/HXBlog_V2.0//admin/upload/ueditor/config', 'ueditor上传配置', '250', '2017-06-12 20:36:56', '2017-06-12 20:36:56', '1', '0');
INSERT INTO `interf` VALUES ('28', '/HXBlog_V2.0//admin/index/statistics', '统计数据', '260', '2017-06-12 20:37:39', '2017-06-12 20:37:39', '1', '0');
INSERT INTO `interf` VALUES ('29', '/HXBlog_V2.0//admin/index/menus', '拉取菜单', '270', '2017-06-12 20:38:09', '2017-06-12 21:30:54', '1', '0');
INSERT INTO `interf` VALUES ('30', '/HXBlog_V2.0//admin/user/logout', '登出系统', '280', '2017-06-12 22:18:30', '2017-06-12 22:18:30', '1', '0');
INSERT INTO `interf` VALUES ('31', '/HXBlog_V2.0//admin/blog/createType/*', '创建类型管理', '290', '2017-06-13 21:28:01', '2017-06-13 21:28:01', '1', '0');
INSERT INTO `interf` VALUES ('32', '/HXBlog_V2.0//admin/config/*', '系统配置', '300', '2017-06-13 21:58:06', '2017-06-13 21:58:06', '1', '0');
INSERT INTO `interf` VALUES ('33', '/HXBlog_V2.0//admin/cache/*', '缓存控制的相关接口', '320', '2017-06-17 12:23:57', '2017-06-17 12:23:57', '1', '0');
INSERT INTO `interf` VALUES ('34', '/HXBlog_V2.0//admin/system/*', '???????', '310', '2017-06-17 17:14:08', '2017-06-24 09:26:34', '1', '0');
INSERT INTO `interf` VALUES ('35', '/HXBlog_V2.0//admin/comment/comment/*', '拉取评论的细节信息', '40', '2017-06-17 23:46:07', '2017-06-17 23:55:57', '1', '0');
INSERT INTO `interf` VALUES ('36', '/HXBlog_V2.0//admin/correction/*', '校正管理', '330', '2017-06-18 20:13:08', '2017-06-18 20:13:08', '1', '0');
INSERT INTO `interf` VALUES ('37', '/HXBlog_V2.0//admin/log/request/list', '请求日志查看', '340', '2017-06-18 21:38:46', '2017-06-18 21:39:26', '1', '0');
INSERT INTO `interf` VALUES ('38', '/HXBlog_V2.0//admin/log/exception/list', '异常日志查看', '350', '2017-06-18 21:39:15', '2017-06-18 21:39:15', '1', '0');
INSERT INTO `interf` VALUES ('39', '/HXBlog_V2.0//admin/message/unread', '未读消息', '70', '2017-06-28 20:58:03', '2017-06-28 21:00:15', '1', '0');
INSERT INTO `interf` VALUES ('40', '/HXBlog_V2.0//admin/blog/list', '自己的博客管理', '20', '2017-06-28 21:00:03', '2017-06-28 21:00:03', '1', '0');
INSERT INTO `interf` VALUES ('41', '/HXBlog_V2.0//admin/message/markAllConsumed', '标记所有的消息已读', '77', '2017-07-01 22:00:48', '2017-07-01 22:00:48', '1', '0');
INSERT INTO `interf` VALUES ('42', '/HXBlog_V2.0/admin/adv/*', '广告管理', '360', '2018-03-03 20:09:32', '2018-03-03 20:22:36', '1', '0');

-- ----------------------------
-- Table structure for link
-- ----------------------------
DROP TABLE IF EXISTS `link`;
CREATE TABLE `link` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(128) DEFAULT NULL COMMENT '名称',
  `desc` varchar(256) DEFAULT NULL COMMENT '描述',
  `url` varchar(2048) DEFAULT NULL COMMENT '地址',
  `sort` int(11) DEFAULT NULL COMMENT '排序字段',
  `created_at` varchar(32) DEFAULT NULL COMMENT '创建时间',
  `updated_at` varchar(32) DEFAULT NULL COMMENT '修改时间',
  `enable` tinyint(1) DEFAULT NULL COMMENT '是否可用',
  `deleted` tinyint(1) DEFAULT NULL COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='友情链接';

-- ----------------------------
-- Records of link
-- ----------------------------
INSERT INTO `link` VALUES ('4', '后台登录', '后台登录', 'http://192.168.0.190:8888/HXBlog_V2.0/static/amain/index.html', '0', '2017-05-30 05:22:57', '2018-03-03 15:00:18', '1', '0');
INSERT INTO `link` VALUES ('5', '百度', '百度', 'http://www.baidu.com', '10', '2017-05-31 09:09:32', '2017-06-30 19:03:22', '1', '0');

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `receiver_id` int(11) DEFAULT NULL COMMENT '接收消息的人',
  `sender_id` int(11) DEFAULT NULL COMMENT '发送消息的人',
  `type` varchar(12) DEFAULT NULL COMMENT '消息类型[系统消息, 站内消息]',
  `subject` varchar(256) DEFAULT NULL COMMENT '消息的主题',
  `content` varchar(4096) DEFAULT NULL COMMENT '消息的内容',
  `consumed` tinyint(1) DEFAULT NULL COMMENT '是否查看',
  `created_at` varchar(32) DEFAULT NULL COMMENT '创建时间',
  `updated_at` varchar(32) DEFAULT NULL COMMENT '修改时间',
  `deleted` tinyint(1) DEFAULT NULL COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_14` (`receiver_id`),
  CONSTRAINT `FK_Reference_14` FOREIGN KEY (`receiver_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=193 DEFAULT CHARSET=utf8 COMMENT='消息表';

-- ----------------------------
-- Records of message
-- ----------------------------

-- ----------------------------
-- Table structure for mood
-- ----------------------------
DROP TABLE IF EXISTS `mood`;
CREATE TABLE `mood` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(256) DEFAULT NULL COMMENT '标题',
  `content` varchar(2048) DEFAULT NULL COMMENT '内容',
  `created_at` varchar(32) DEFAULT NULL COMMENT '创建时间',
  `updated_at` varchar(32) DEFAULT NULL,
  `enable` tinyint(1) DEFAULT NULL COMMENT '是否可用',
  `deleted` tinyint(1) DEFAULT NULL COMMENT '是否被删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='心情';

-- ----------------------------
-- Records of mood
-- ----------------------------
INSERT INTO `mood` VALUES ('1', '111', '222', '2017-05-22 10:06:28', '2017-05-28 08:09:20', '1', '1');
INSERT INTO `mood` VALUES ('2', '1111', '11', '2017-05-22 09:46:01', '2017-05-31 09:10:14', '1', '1');
INSERT INTO `mood` VALUES ('3', '333', '11122111', '2017-05-22 10:06:15', '2017-05-31 09:10:11', '1', '1');
INSERT INTO `mood` VALUES ('4', '444', '444', '2017-05-22 09:47:06', '2017-05-22 06:49:16', '0', '1');
INSERT INTO `mood` VALUES ('5', '222', '11', '2017-05-22 10:06:43', '2017-05-31 09:10:08', '0', '1');
INSERT INTO `mood` VALUES ('6', '2', '22', '2017-05-28 06:43:05', '2017-05-31 09:09:59', '0', '1');
INSERT INTO `mood` VALUES ('7', '2', '2', '2017-05-28 06:43:01', '2017-05-31 09:10:02', '0', '1');
INSERT INTO `mood` VALUES ('8', '2', '2', '2017-05-22 10:10:06', '2017-05-31 09:10:05', '1', '1');
INSERT INTO `mood` VALUES ('9', '2', '2', '2017-05-28 06:43:09', '2017-05-31 09:09:56', '0', '1');
INSERT INTO `mood` VALUES ('10', '', 'null', '2017-05-28 07:10:04', '2017-05-31 09:09:53', '1', '1');
INSERT INTO `mood` VALUES ('11', 'image01', 'null', '2017-05-28 07:58:58', '2017-05-31 09:09:50', '1', '1');
INSERT INTO `mood` VALUES ('12', '111', 'null', '2017-05-28 08:01:01', '2017-05-31 09:09:47', '1', '1');
INSERT INTO `mood` VALUES ('13', '131', 'null', '2017-05-28 08:01:14', '2017-05-31 09:09:40', '1', '1');
INSERT INTO `mood` VALUES ('14', '问路', '今天 回家的时候, 要上电梯的时候看到了一个妹子, 他说 这里是三单元么, 我说 不是, 这里是 6栋 ..', '2017-05-31 09:11:08', '2017-06-18 00:07:55', '1', '0');
INSERT INTO `mood` VALUES ('15', '50块', '买的是一个一半块鸡, 差不多是11元, 然后 四个猕猴桃6yuan, 然后 还买了5双袜子,, 然后 差不多是29元,, 然后 50元, 就没有了,,', '2017-01-07 09:12:09', '2017-01-07 09:12:09', '1', '0');

-- ----------------------------
-- Table structure for request_log
-- ----------------------------
DROP TABLE IF EXISTS `request_log`;
CREATE TABLE `request_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `url` varchar(256) DEFAULT NULL COMMENT '请求的url',
  `handler` varchar(256) DEFAULT NULL COMMENT '处理请求的方法',
  `params` varchar(4096) DEFAULT NULL COMMENT '请求的参数',
  `cost` int(11) DEFAULT NULL COMMENT '请求处理的时间',
  `name` varchar(256) DEFAULT NULL COMMENT '发起请求的用户名称',
  `email` varchar(256) DEFAULT NULL COMMENT '发起请求的用户邮箱',
  `request_ip` varchar(256) DEFAULT NULL COMMENT '请求的ip',
  `is_system_user` tinyint(1) DEFAULT NULL COMMENT '是否是系统注册用户',
  `created_at` varchar(32) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13550 DEFAULT CHARSET=utf8 COMMENT='请求记录表';

-- ----------------------------
-- Records of request_log
-- ----------------------------

-- ----------------------------
-- Table structure for resource
-- ----------------------------
DROP TABLE IF EXISTS `resource`;
CREATE TABLE `resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(256) DEFAULT NULL COMMENT '资源名称',
  `icon_class` varchar(256) DEFAULT NULL COMMENT '图标的样式',
  `url` varchar(2048) DEFAULT NULL COMMENT '资源url',
  `sort` int(11) DEFAULT NULL COMMENT '排序字段',
  `parent_id` int(11) DEFAULT NULL COMMENT '父级资源id',
  `level` int(11) DEFAULT NULL COMMENT '当前资源的层级',
  `created_at` varchar(32) DEFAULT NULL COMMENT '创建时间',
  `updated_at` varchar(32) DEFAULT NULL COMMENT '修改时间',
  `enable` tinyint(1) DEFAULT NULL COMMENT '是否可用',
  `deleted` tinyint(1) DEFAULT NULL COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8 COMMENT='资源表';

-- ----------------------------
-- Records of resource
-- ----------------------------
INSERT INTO `resource` VALUES ('1', '博客管理', 'fa fa-file-text', '#', '0', '7', '1', '2017-05-30 10:21:09', '2017-06-03 11:47:19', '1', '0');
INSERT INTO `resource` VALUES ('2', '标签类别管理', 'fa fa-pencil-square-o', '#', '20', '7', '1', '2017-05-30 10:21:55', '2017-06-03 11:49:37', '1', '0');
INSERT INTO `resource` VALUES ('3', '网站信息管理', 'fa fa-info', '#', '50', '7', '1', '2017-05-30 10:22:59', '2017-06-03 11:49:52', '1', '0');
INSERT INTO `resource` VALUES ('4', '写博客', 'fa fa-pencil-square-o', '/HXBlog_V2.0/static/admin/html/blog/addBlog.html', '0', '1', '2', '2017-05-30 10:23:34', '2018-03-04 20:22:58', '1', '0');
INSERT INTO `resource` VALUES ('5', '博客管理[admin]', 'fa fa-magic', '/HXBlog_V2.0//static/admin/html/blog/adminBlogManagement.html', '10', '1', '2', '2017-05-30 10:24:12', '2018-03-04 20:12:57', '1', '0');
INSERT INTO `resource` VALUES ('6', '评论管理', 'fa fa-comments', '/HXBlog_V2.0//static/admin/html/blog/commentManagement.html', '30', '1', '2', '2017-05-30 10:24:45', '2018-03-04 20:13:10', '1', '0');
INSERT INTO `resource` VALUES ('7', '#root', '#', '#', '0', '-1', '0', '2017-05-30 10:25:58', '2017-05-30 10:26:03', '1', '0');
INSERT INTO `resource` VALUES ('8', '标签管理', 'fa fa-tags', '/HXBlog_V2.0//static/admin/html/blog/blogTagManagement.html', '0', '2', '2', '2017-05-30 10:35:41', '2018-03-04 20:29:03', '1', '0');
INSERT INTO `resource` VALUES ('9', '类型管理', 'fa fa-list-ol', '/HXBlog_V2.0//static/admin/html/blog/blogTypeManagement.html', '10', '2', '2', '2017-05-30 10:36:10', '2018-03-04 20:29:08', '1', '0');
INSERT INTO `resource` VALUES ('10', '友情链接管理', 'fa fa-chain', '/HXBlog_V2.0//static/admin/html/front_resources/linksManagement.html', '0', '3', '2', '2017-05-30 10:39:53', '2018-03-04 20:31:37', '1', '0');
INSERT INTO `resource` VALUES ('11', '系统管理', 'fa fa-cog', '#', '80', '7', '1', '2017-05-30 10:40:40', '2017-06-03 11:49:56', '1', '0');
INSERT INTO `resource` VALUES ('12', '账号管理', 'fa fa-sort-numeric-asc', '/HXBlog_V2.0//static/admin/html/resources/accountManagement.html', '0', '22', '2', '2017-05-30 10:41:09', '2018-03-04 20:29:31', '1', '0');
INSERT INTO `resource` VALUES ('13', '修改账号密码', 'fa fa-edit', '/HXBlog_V2.0/static/admin/html/system/accountPwdManagement.html', '0', '11', '2', '2017-05-30 10:41:43', '2018-03-04 20:44:57', '1', '0');
INSERT INTO `resource` VALUES ('14', '资源管理', 'fa fa-file-zip-o', '/HXBlog_V2.0/static/admin/html/resources/resourceManagement.html', '20', '22', '2', '2017-05-30 11:22:46', '2018-03-04 20:42:49', '1', '0');
INSERT INTO `resource` VALUES ('17', '角色管理', 'fa fa-group', '/HXBlog_V2.0//static/admin/html/resources/roleManagement.html', '10', '22', '2', '2017-05-30 04:24:43', '2018-03-04 20:29:37', '1', '0');
INSERT INTO `resource` VALUES ('18', '心情管理', 'fa fa-heart', '/HXBlog_V2.0//static/admin/html/front_resources/moodsManagement.html', '10', '3', '2', '2017-05-30 04:34:09', '2018-03-04 20:31:56', '1', '0');
INSERT INTO `resource` VALUES ('19', '用户角色管理', 'fa fa-pencil', '/HXBlog_V2.0//static/admin/html/rlt/accountRoleManagement.html', '0', '27', '2', '2017-05-30 04:50:08', '2018-03-04 20:30:09', '1', '0');
INSERT INTO `resource` VALUES ('20', '角色资源管理', 'fa fa-pencil', '/HXBlog_V2.0//static/admin/html/rlt/roleResourceManagement.html', '10', '27', '2', '2017-05-30 07:58:33', '2018-03-04 20:30:15', '1', '0');
INSERT INTO `resource` VALUES ('21', 'ddd', 'test', 'test', '1', '7', '1', '2017-05-30 10:14:26', '2017-05-30 10:14:45', '1', '1');
INSERT INTO `resource` VALUES ('22', '用户资源管理', 'fa fa-folder-open', '#', '30', '7', '1', '2017-05-31 08:06:48', '2017-06-03 11:49:40', '1', '0');
INSERT INTO `resource` VALUES ('23', '图片墙管理', 'fa fa-image', '/HXBlog_V2.0//static/admin/html/front_resources/imagesManagement.html?type=2', '20', '3', '2', '2017-05-31 08:39:47', '2018-03-04 20:32:01', '1', '0');
INSERT INTO `resource` VALUES ('24', '头像管理', 'fa fa-qq', '/HXBlog_V2.0//static/admin/html/front_resources/imagesManagement.html?type=1', '30', '3', '2', '2017-05-31 08:40:32', '2018-03-04 20:32:11', '1', '0');
INSERT INTO `resource` VALUES ('25', '接口管理', 'fa fa-plug', '/HXBlog_V2.0//static/admin/html/resources/interfManagement.html', '30', '22', '2', '2017-06-01 07:44:13', '2018-03-04 20:29:49', '1', '0');
INSERT INTO `resource` VALUES ('26', '资源接口管理', 'fa fa-edit', '/HXBlog_V2.0//static/admin/html/rlt/resourceInterfManagement.html', '20', '27', '2', '2017-06-01 08:24:51', '2018-03-04 20:30:21', '1', '0');
INSERT INTO `resource` VALUES ('27', '用户资源关联', 'fa fa-exchange', '#', '40', '7', '1', '2017-06-03 11:47:06', '2017-06-03 11:53:58', '1', '0');
INSERT INTO `resource` VALUES ('28', '系统监控', 'fa fa-camera', '/HXBlog_V2.0//static/admin/html/system/moniter.html', '10', '11', '2', '2017-06-11 09:23:45', '2018-03-04 20:33:55', '1', '0');
INSERT INTO `resource` VALUES ('29', '消息管理', 'fa fa-envelope-o', '#', '10', '7', '1', '2017-06-11 14:18:43', '2017-06-11 14:18:43', '1', '0');
INSERT INTO `resource` VALUES ('30', '发送消息', 'fa  fa-envelope-square', '/HXBlog_V2.0//static/admin/html/message/addMessage.html', '0', '29', '2', '2017-06-11 14:20:07', '2018-03-04 20:28:20', '1', '0');
INSERT INTO `resource` VALUES ('31', '收件箱', 'fa fa-envelope', '/HXBlog_V2.0//static/admin/html/message/messageView.html', '10', '29', '2', '2017-06-11 14:20:54', '2018-03-04 20:28:28', '1', '0');
INSERT INTO `resource` VALUES ('32', '消息管理', 'fa fa-envelope-o', '/HXBlog_V2.0//static/admin/html/message/messageManagement.html', '20', '29', '2', '2017-06-11 14:22:10', '2018-03-04 20:28:35', '1', '0');
INSERT INTO `resource` VALUES ('33', '临时资源集合', '#', '#', '90', '7', '1', '2017-06-12 21:25:47', '2017-06-12 21:43:27', '0', '0');
INSERT INTO `resource` VALUES ('34', '基本资源集合临时节点', '#', '#', '10', '33', '2', '2017-06-12 21:26:43', '2017-06-12 21:26:43', '1', '0');
INSERT INTO `resource` VALUES ('35', '管理员基本资源', '#', '#', '0', '33', '2', '2017-06-12 21:53:41', '2017-06-12 21:53:41', '1', '0');
INSERT INTO `resource` VALUES ('36', '博客创建类型', 'fa fa-handshake-o', '/HXBlog_V2.0//static/admin/html/blog/blogCreateTypeManagement.html', '20', '2', '2', '2017-06-13 20:37:38', '2018-03-04 20:29:14', '1', '0');
INSERT INTO `resource` VALUES ('37', '系统配置', 'fa fa-th-large', '/HXBlog_V2.0//static/admin/html/system/systemConfigManagement.html?type=1', '0', '41', '2', '2017-06-13 21:55:45', '2018-03-04 20:32:39', '1', '0');
INSERT INTO `resource` VALUES ('38', '规则配置', 'fa fa-th-large', '/HXBlog_V2.0//static/admin/html/system/systemConfigManagement.html?type=2', '10', '41', '2', '2017-06-14 19:42:22', '2018-03-04 20:32:45', '1', '0');
INSERT INTO `resource` VALUES ('39', '缓存控制', 'fa fa-refresh', '/HXBlog_V2.0/static/admin/html/system/cacheManagement.html', '20', '11', '2', '2017-06-17 11:20:24', '2018-03-04 20:44:52', '1', '0');
INSERT INTO `resource` VALUES ('40', '首页配置管理', 'fa fa-th-large', '/HXBlog_V2.0//static/admin/html/system/systemConfigManagement.html?type=3', '20', '41', '2', '2017-06-17 17:51:26', '2018-03-04 20:32:50', '1', '0');
INSERT INTO `resource` VALUES ('41', '配置管理', 'fa fa-th-large', '######', '60', '7', '1', '2017-06-17 19:24:58', '2017-06-17 19:26:44', '1', '0');
INSERT INTO `resource` VALUES ('42', '校正管理', 'fa fa-wrench', '/HXBlog_V2.0//static/admin/html/system/correctionManagement.html', '30', '11', '2', '2017-06-18 19:09:31', '2018-03-04 20:33:42', '1', '0');
INSERT INTO `resource` VALUES ('43', '日志管理', 'fa fa-file-archive-o', '#####', '70', '7', '1', '2017-06-18 21:34:29', '2017-06-18 21:36:45', '1', '0');
INSERT INTO `resource` VALUES ('44', '请求日志', 'fa fa-file-text-o', '/HXBlog_V2.0//static/admin/html/system/requestLogManagement.html', '0', '43', '2', '2017-06-18 21:35:37', '2018-03-04 20:32:58', '1', '0');
INSERT INTO `resource` VALUES ('45', '异常日志', 'fa fa-file-text', '/HXBlog_V2.0//static/admin/html/system/exceptionLogManagement.html', '10', '43', '2', '2017-06-18 21:36:28', '2018-03-04 20:33:04', '1', '0');
INSERT INTO `resource` VALUES ('46', '博客管理', 'fa fa-magic', '/HXBlog_V2.0//static/admin/html/blog/blogManagement.html', '20', '1', '2', '2017-06-28 20:37:08', '2018-03-04 20:13:04', '1', '0');
INSERT INTO `resource` VALUES ('47', '广告管理', 'fa fa-window-close-o', '/HXBlog_V2.0//static/admin/html/front_resources/advManagement.html', '50', '3', '2', '2018-03-03 20:05:04', '2018-03-04 20:32:16', '1', '0');

-- ----------------------------
-- Table structure for rlt_blog_tag
-- ----------------------------
DROP TABLE IF EXISTS `rlt_blog_tag`;
CREATE TABLE `rlt_blog_tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `blog_id` int(11) DEFAULT NULL COMMENT '关联的 blogId',
  `tag_id` int(11) DEFAULT NULL COMMENT '关联的 tagId',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_5` (`blog_id`),
  KEY `FK_Reference_6` (`tag_id`),
  CONSTRAINT `FK_Reference_5` FOREIGN KEY (`blog_id`) REFERENCES `blog` (`id`),
  CONSTRAINT `FK_Reference_6` FOREIGN KEY (`tag_id`) REFERENCES `blog_tag` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=215 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of rlt_blog_tag
-- ----------------------------
INSERT INTO `rlt_blog_tag` VALUES ('46', '-3', '18');
INSERT INTO `rlt_blog_tag` VALUES ('47', '27', '15');
INSERT INTO `rlt_blog_tag` VALUES ('49', '-2', '18');
INSERT INTO `rlt_blog_tag` VALUES ('50', '-1', '18');
INSERT INTO `rlt_blog_tag` VALUES ('69', '39', '15');
INSERT INTO `rlt_blog_tag` VALUES ('70', '40', '15');
INSERT INTO `rlt_blog_tag` VALUES ('72', '38', '15');
INSERT INTO `rlt_blog_tag` VALUES ('75', '41', '15');
INSERT INTO `rlt_blog_tag` VALUES ('78', '42', '15');
INSERT INTO `rlt_blog_tag` VALUES ('80', '43', '21');
INSERT INTO `rlt_blog_tag` VALUES ('81', '44', '15');
INSERT INTO `rlt_blog_tag` VALUES ('82', '44', '22');
INSERT INTO `rlt_blog_tag` VALUES ('83', '45', '15');
INSERT INTO `rlt_blog_tag` VALUES ('84', '45', '22');
INSERT INTO `rlt_blog_tag` VALUES ('85', '46', '15');
INSERT INTO `rlt_blog_tag` VALUES ('86', '46', '22');
INSERT INTO `rlt_blog_tag` VALUES ('102', '47', '15');
INSERT INTO `rlt_blog_tag` VALUES ('103', '47', '22');
INSERT INTO `rlt_blog_tag` VALUES ('104', '47', '23');
INSERT INTO `rlt_blog_tag` VALUES ('105', '48', '18');
INSERT INTO `rlt_blog_tag` VALUES ('113', '49', '24');
INSERT INTO `rlt_blog_tag` VALUES ('114', '49', '15');
INSERT INTO `rlt_blog_tag` VALUES ('115', '50', '15');
INSERT INTO `rlt_blog_tag` VALUES ('116', '50', '32');
INSERT INTO `rlt_blog_tag` VALUES ('118', '51', '15');
INSERT INTO `rlt_blog_tag` VALUES ('129', '53', '18');
INSERT INTO `rlt_blog_tag` VALUES ('130', '54', '18');
INSERT INTO `rlt_blog_tag` VALUES ('131', '55', '33');
INSERT INTO `rlt_blog_tag` VALUES ('132', '56', '15');
INSERT INTO `rlt_blog_tag` VALUES ('133', '57', '33');
INSERT INTO `rlt_blog_tag` VALUES ('134', '58', '15');
INSERT INTO `rlt_blog_tag` VALUES ('135', '59', '18');
INSERT INTO `rlt_blog_tag` VALUES ('136', '60', '15');
INSERT INTO `rlt_blog_tag` VALUES ('139', '61', '15');
INSERT INTO `rlt_blog_tag` VALUES ('148', '64', '16');
INSERT INTO `rlt_blog_tag` VALUES ('149', '65', '15');
INSERT INTO `rlt_blog_tag` VALUES ('150', '65', '17');
INSERT INTO `rlt_blog_tag` VALUES ('151', '52', '15');
INSERT INTO `rlt_blog_tag` VALUES ('152', '52', '17');
INSERT INTO `rlt_blog_tag` VALUES ('153', '66', '15');
INSERT INTO `rlt_blog_tag` VALUES ('154', '66', '44');
INSERT INTO `rlt_blog_tag` VALUES ('155', '67', '15');
INSERT INTO `rlt_blog_tag` VALUES ('156', '67', '46');
INSERT INTO `rlt_blog_tag` VALUES ('157', '68', '15');
INSERT INTO `rlt_blog_tag` VALUES ('158', '68', '16');
INSERT INTO `rlt_blog_tag` VALUES ('159', '69', '15');
INSERT INTO `rlt_blog_tag` VALUES ('160', '69', '16');
INSERT INTO `rlt_blog_tag` VALUES ('161', '70', '15');
INSERT INTO `rlt_blog_tag` VALUES ('162', '70', '32');
INSERT INTO `rlt_blog_tag` VALUES ('163', '71', '15');
INSERT INTO `rlt_blog_tag` VALUES ('164', '72', '15');
INSERT INTO `rlt_blog_tag` VALUES ('165', '73', '15');
INSERT INTO `rlt_blog_tag` VALUES ('166', '73', '37');
INSERT INTO `rlt_blog_tag` VALUES ('167', '74', '15');
INSERT INTO `rlt_blog_tag` VALUES ('168', '75', '21');
INSERT INTO `rlt_blog_tag` VALUES ('169', '76', '21');
INSERT INTO `rlt_blog_tag` VALUES ('170', '77', '45');
INSERT INTO `rlt_blog_tag` VALUES ('171', '78', '21');
INSERT INTO `rlt_blog_tag` VALUES ('172', '79', '41');
INSERT INTO `rlt_blog_tag` VALUES ('179', '80', '21');
INSERT INTO `rlt_blog_tag` VALUES ('180', '80', '42');
INSERT INTO `rlt_blog_tag` VALUES ('181', '81', '24');
INSERT INTO `rlt_blog_tag` VALUES ('182', '82', '24');
INSERT INTO `rlt_blog_tag` VALUES ('183', '83', '24');
INSERT INTO `rlt_blog_tag` VALUES ('184', '84', '24');
INSERT INTO `rlt_blog_tag` VALUES ('185', '85', '21');
INSERT INTO `rlt_blog_tag` VALUES ('186', '86', '21');
INSERT INTO `rlt_blog_tag` VALUES ('187', '87', '21');
INSERT INTO `rlt_blog_tag` VALUES ('188', '88', '21');
INSERT INTO `rlt_blog_tag` VALUES ('189', '89', '21');
INSERT INTO `rlt_blog_tag` VALUES ('190', '90', '43');
INSERT INTO `rlt_blog_tag` VALUES ('192', '92', '43');
INSERT INTO `rlt_blog_tag` VALUES ('193', '93', '43');
INSERT INTO `rlt_blog_tag` VALUES ('194', '94', '43');
INSERT INTO `rlt_blog_tag` VALUES ('195', '95', '43');
INSERT INTO `rlt_blog_tag` VALUES ('196', '96', '43');
INSERT INTO `rlt_blog_tag` VALUES ('197', '97', '43');
INSERT INTO `rlt_blog_tag` VALUES ('200', '91', '43');
INSERT INTO `rlt_blog_tag` VALUES ('202', '99', '42');
INSERT INTO `rlt_blog_tag` VALUES ('203', '100', '21');
INSERT INTO `rlt_blog_tag` VALUES ('206', '101', '42');
INSERT INTO `rlt_blog_tag` VALUES ('207', '98', '42');
INSERT INTO `rlt_blog_tag` VALUES ('208', '63', '15');
INSERT INTO `rlt_blog_tag` VALUES ('209', '62', '15');
INSERT INTO `rlt_blog_tag` VALUES ('210', '102', '21');
INSERT INTO `rlt_blog_tag` VALUES ('213', '103', '24');
INSERT INTO `rlt_blog_tag` VALUES ('214', '103', '33');

-- ----------------------------
-- Table structure for rlt_resource_interf
-- ----------------------------
DROP TABLE IF EXISTS `rlt_resource_interf`;
CREATE TABLE `rlt_resource_interf` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `resource_id` int(11) DEFAULT NULL COMMENT '关联的 resourceId',
  `interf_id` int(11) DEFAULT NULL COMMENT '关联的 interfId',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_11` (`resource_id`),
  KEY `FK_Reference_12` (`interf_id`),
  CONSTRAINT `FK_Reference_11` FOREIGN KEY (`resource_id`) REFERENCES `resource` (`id`),
  CONSTRAINT `FK_Reference_12` FOREIGN KEY (`interf_id`) REFERENCES `interf` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=utf8 COMMENT='资源接口关联';

-- ----------------------------
-- Records of rlt_resource_interf
-- ----------------------------
INSERT INTO `rlt_resource_interf` VALUES ('10', '8', '10');
INSERT INTO `rlt_resource_interf` VALUES ('11', '10', '19');
INSERT INTO `rlt_resource_interf` VALUES ('12', '12', '12');
INSERT INTO `rlt_resource_interf` VALUES ('13', '13', '22');
INSERT INTO `rlt_resource_interf` VALUES ('14', '19', '16');
INSERT INTO `rlt_resource_interf` VALUES ('16', '5', '5');
INSERT INTO `rlt_resource_interf` VALUES ('17', '9', '11');
INSERT INTO `rlt_resource_interf` VALUES ('18', '18', '20');
INSERT INTO `rlt_resource_interf` VALUES ('19', '20', '17');
INSERT INTO `rlt_resource_interf` VALUES ('20', '28', '24');
INSERT INTO `rlt_resource_interf` VALUES ('23', '14', '14');
INSERT INTO `rlt_resource_interf` VALUES ('24', '23', '21');
INSERT INTO `rlt_resource_interf` VALUES ('25', '23', '25');
INSERT INTO `rlt_resource_interf` VALUES ('26', '26', '18');
INSERT INTO `rlt_resource_interf` VALUES ('27', '32', '9');
INSERT INTO `rlt_resource_interf` VALUES ('28', '24', '21');
INSERT INTO `rlt_resource_interf` VALUES ('29', '24', '25');
INSERT INTO `rlt_resource_interf` VALUES ('30', '25', '15');
INSERT INTO `rlt_resource_interf` VALUES ('43', '17', '13');
INSERT INTO `rlt_resource_interf` VALUES ('55', '30', '7');
INSERT INTO `rlt_resource_interf` VALUES ('56', '30', '26');
INSERT INTO `rlt_resource_interf` VALUES ('57', '30', '25');
INSERT INTO `rlt_resource_interf` VALUES ('58', '30', '27');
INSERT INTO `rlt_resource_interf` VALUES ('59', '36', '31');
INSERT INTO `rlt_resource_interf` VALUES ('60', '37', '32');
INSERT INTO `rlt_resource_interf` VALUES ('61', '38', '32');
INSERT INTO `rlt_resource_interf` VALUES ('63', '35', '28');
INSERT INTO `rlt_resource_interf` VALUES ('64', '39', '33');
INSERT INTO `rlt_resource_interf` VALUES ('65', '39', '34');
INSERT INTO `rlt_resource_interf` VALUES ('82', '4', '1');
INSERT INTO `rlt_resource_interf` VALUES ('83', '4', '25');
INSERT INTO `rlt_resource_interf` VALUES ('84', '4', '26');
INSERT INTO `rlt_resource_interf` VALUES ('85', '4', '27');
INSERT INTO `rlt_resource_interf` VALUES ('86', '6', '6');
INSERT INTO `rlt_resource_interf` VALUES ('87', '6', '35');
INSERT INTO `rlt_resource_interf` VALUES ('88', '34', '28');
INSERT INTO `rlt_resource_interf` VALUES ('89', '34', '29');
INSERT INTO `rlt_resource_interf` VALUES ('90', '34', '30');
INSERT INTO `rlt_resource_interf` VALUES ('91', '42', '36');
INSERT INTO `rlt_resource_interf` VALUES ('92', '40', '32');
INSERT INTO `rlt_resource_interf` VALUES ('93', '44', '37');
INSERT INTO `rlt_resource_interf` VALUES ('94', '45', '38');
INSERT INTO `rlt_resource_interf` VALUES ('97', '46', '40');
INSERT INTO `rlt_resource_interf` VALUES ('98', '31', '8');
INSERT INTO `rlt_resource_interf` VALUES ('99', '31', '39');
INSERT INTO `rlt_resource_interf` VALUES ('100', '31', '41');
INSERT INTO `rlt_resource_interf` VALUES ('101', '47', '42');

-- ----------------------------
-- Table structure for rlt_role_resource
-- ----------------------------
DROP TABLE IF EXISTS `rlt_role_resource`;
CREATE TABLE `rlt_role_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` int(11) DEFAULT NULL COMMENT '关联的 roleId',
  `resource_id` int(11) DEFAULT NULL COMMENT '关联的 resourceId',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_10` (`resource_id`),
  KEY `FK_Reference_9` (`role_id`),
  CONSTRAINT `FK_Reference_10` FOREIGN KEY (`resource_id`) REFERENCES `resource` (`id`),
  CONSTRAINT `FK_Reference_9` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=533 DEFAULT CHARSET=utf8 COMMENT='角色资源关联';

-- ----------------------------
-- Records of rlt_role_resource
-- ----------------------------
INSERT INTO `rlt_role_resource` VALUES ('139', '7', '13');
INSERT INTO `rlt_role_resource` VALUES ('140', '7', '30');
INSERT INTO `rlt_role_resource` VALUES ('141', '7', '31');
INSERT INTO `rlt_role_resource` VALUES ('142', '7', '34');
INSERT INTO `rlt_role_resource` VALUES ('166', '10', '13');
INSERT INTO `rlt_role_resource` VALUES ('167', '10', '30');
INSERT INTO `rlt_role_resource` VALUES ('168', '10', '31');
INSERT INTO `rlt_role_resource` VALUES ('169', '10', '34');
INSERT INTO `rlt_role_resource` VALUES ('494', '6', '4');
INSERT INTO `rlt_role_resource` VALUES ('495', '6', '46');
INSERT INTO `rlt_role_resource` VALUES ('496', '6', '8');
INSERT INTO `rlt_role_resource` VALUES ('497', '6', '9');
INSERT INTO `rlt_role_resource` VALUES ('498', '6', '13');
INSERT INTO `rlt_role_resource` VALUES ('499', '6', '34');
INSERT INTO `rlt_role_resource` VALUES ('500', '5', '4');
INSERT INTO `rlt_role_resource` VALUES ('501', '5', '5');
INSERT INTO `rlt_role_resource` VALUES ('502', '5', '46');
INSERT INTO `rlt_role_resource` VALUES ('503', '5', '6');
INSERT INTO `rlt_role_resource` VALUES ('504', '5', '30');
INSERT INTO `rlt_role_resource` VALUES ('505', '5', '31');
INSERT INTO `rlt_role_resource` VALUES ('506', '5', '32');
INSERT INTO `rlt_role_resource` VALUES ('507', '5', '8');
INSERT INTO `rlt_role_resource` VALUES ('508', '5', '9');
INSERT INTO `rlt_role_resource` VALUES ('509', '5', '36');
INSERT INTO `rlt_role_resource` VALUES ('510', '5', '12');
INSERT INTO `rlt_role_resource` VALUES ('511', '5', '17');
INSERT INTO `rlt_role_resource` VALUES ('512', '5', '14');
INSERT INTO `rlt_role_resource` VALUES ('513', '5', '25');
INSERT INTO `rlt_role_resource` VALUES ('514', '5', '19');
INSERT INTO `rlt_role_resource` VALUES ('515', '5', '20');
INSERT INTO `rlt_role_resource` VALUES ('516', '5', '26');
INSERT INTO `rlt_role_resource` VALUES ('517', '5', '10');
INSERT INTO `rlt_role_resource` VALUES ('518', '5', '18');
INSERT INTO `rlt_role_resource` VALUES ('519', '5', '23');
INSERT INTO `rlt_role_resource` VALUES ('520', '5', '24');
INSERT INTO `rlt_role_resource` VALUES ('521', '5', '37');
INSERT INTO `rlt_role_resource` VALUES ('522', '5', '38');
INSERT INTO `rlt_role_resource` VALUES ('523', '5', '40');
INSERT INTO `rlt_role_resource` VALUES ('524', '5', '44');
INSERT INTO `rlt_role_resource` VALUES ('525', '5', '45');
INSERT INTO `rlt_role_resource` VALUES ('526', '5', '13');
INSERT INTO `rlt_role_resource` VALUES ('527', '5', '28');
INSERT INTO `rlt_role_resource` VALUES ('528', '5', '39');
INSERT INTO `rlt_role_resource` VALUES ('529', '5', '42');
INSERT INTO `rlt_role_resource` VALUES ('530', '5', '35');
INSERT INTO `rlt_role_resource` VALUES ('531', '5', '34');
INSERT INTO `rlt_role_resource` VALUES ('532', '5', '47');

-- ----------------------------
-- Table structure for rlt_user_role
-- ----------------------------
DROP TABLE IF EXISTS `rlt_user_role`;
CREATE TABLE `rlt_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) DEFAULT NULL COMMENT '关联的userId',
  `role_id` int(11) DEFAULT NULL COMMENT '关联的 roleId',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_7` (`user_id`),
  KEY `FK_Reference_8` (`role_id`),
  CONSTRAINT `FK_Reference_7` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_Reference_8` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COMMENT='用户角色关联';

-- ----------------------------
-- Records of rlt_user_role
-- ----------------------------
INSERT INTO `rlt_user_role` VALUES ('15', '3', '6');
INSERT INTO `rlt_user_role` VALUES ('16', '3', '10');
INSERT INTO `rlt_user_role` VALUES ('18', '4', '5');
INSERT INTO `rlt_user_role` VALUES ('19', '-1', '5');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(256) DEFAULT NULL COMMENT '角色名称',
  `desc` varchar(2048) DEFAULT NULL COMMENT '角色描述',
  `sort` int(11) DEFAULT NULL COMMENT '排序字段',
  `created_at` varchar(32) DEFAULT NULL COMMENT '创建时间',
  `updated_at` varchar(32) DEFAULT NULL COMMENT '修改时间',
  `enable` tinyint(1) DEFAULT NULL COMMENT '是否可用',
  `deleted` tinyint(1) DEFAULT NULL COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('5', 'admin', '管理员', '0', '2017-05-31 09:45:54', '2017-06-10 16:24:03', '1', '0');
INSERT INTO `role` VALUES ('6', 'writer', '小编', '10', '2017-05-31 09:46:07', '2017-05-31 09:46:07', '1', '0');
INSERT INTO `role` VALUES ('7', 'guest', '只保存用户信息', '20', '2017-06-04 18:47:44', '2017-06-17 18:57:27', '1', '0');
INSERT INTO `role` VALUES ('10', '基础资源角色', '基础资源的临时角色', '30', '2017-06-12 21:23:51', '2017-06-12 21:40:58', '1', '0');

-- ----------------------------
-- Table structure for system_config
-- ----------------------------
DROP TABLE IF EXISTS `system_config`;
CREATE TABLE `system_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(256) DEFAULT NULL COMMENT 'key',
  `value` varchar(2048) DEFAULT NULL COMMENT 'value',
  `desc` varchar(256) DEFAULT NULL COMMENT '描述信息',
  `type` varchar(32) DEFAULT NULL COMMENT '配置类型',
  `sort` int(11) DEFAULT NULL COMMENT '排序字段',
  `created_at` varchar(32) DEFAULT NULL COMMENT '创建时间',
  `updated_at` varchar(32) DEFAULT NULL COMMENT '修改时间',
  `enable` tinyint(1) DEFAULT NULL COMMENT '是否可用',
  `deleted` tinyint(1) DEFAULT NULL COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8 COMMENT='系统配置表';

-- ----------------------------
-- Records of system_config
-- ----------------------------
INSERT INTO `system_config` VALUES ('5', 'name.max.length', '10', '用户名的长度限制', '2', '1', '2017-06-14 19:43:33', '2017-06-14 19:43:33', '1', '0');
INSERT INTO `system_config` VALUES ('6', 'name.min.length', '6', '用户名的长度限制', '2', '2', '2017-06-14 19:43:53', '2017-06-14 19:43:53', '1', '0');
INSERT INTO `system_config` VALUES ('7', 'blog.context.id', '-1', '上下文博客的id', '1', '0', '2017-06-14 21:50:29', '2017-06-14 21:50:29', '1', '0');
INSERT INTO `system_config` VALUES ('8', 'blog.advice.id', '-2', '建议的博客的id', '1', '10', '2017-06-14 21:54:53', '2017-06-14 21:54:53', '1', '0');
INSERT INTO `system_config` VALUES ('9', 'blog.self_profile.id', '-3', '自我介绍的博客id', '1', '20', '2017-06-14 21:55:11', '2017-06-14 21:55:11', '1', '0');
INSERT INTO `system_config` VALUES ('10', 'resource.root.parent_id', '-1', '顶级资源节点的id', '1', '30', '2017-06-14 21:56:02', '2017-06-14 21:56:02', '1', '0');
INSERT INTO `system_config` VALUES ('11', 'resource.leave.level', '2', '资源叶子的层级', '1', '40', '2017-06-14 21:56:27', '2017-06-14 21:56:27', '1', '0');
INSERT INTO `system_config` VALUES ('12', 'comment.reply.prefix', '[reply]', '评论回复的前缀', '1', '50', '2017-06-14 21:56:55', '2017-06-14 21:56:55', '1', '0');
INSERT INTO `system_config` VALUES ('13', 'comment.reply.suffix', '[/reply]', '评论回复的后缀', '1', '60', '2017-06-14 21:57:20', '2017-06-14 21:57:20', '1', '0');
INSERT INTO `system_config` VALUES ('14', 'sense.up_prise', 'good', '点赞的 senseType', '1', '70', '2017-06-14 21:57:42', '2017-06-29 21:47:24', '1', '1');
INSERT INTO `system_config` VALUES ('15', 'sense.view', 'view', '暂未使用', '1', '70', '2017-06-14 21:58:03', '2017-06-14 21:58:03', '1', '0');
INSERT INTO `system_config` VALUES ('17', 'resort.start', '0', '重排开始的值', '1', '80', '2017-06-14 21:58:37', '2017-06-14 21:58:37', '1', '0');
INSERT INTO `system_config` VALUES ('18', 'resort.offset', '10', '重排偏移的值', '1', '90', '2017-06-14 21:58:58', '2017-06-14 21:58:58', '1', '0');
INSERT INTO `system_config` VALUES ('19', 'charset.default', 'gbk', '默认的字符集', '1', '100', '2017-06-14 21:59:17', '2017-06-14 21:59:17', '1', '0');
INSERT INTO `system_config` VALUES ('20', 'cache.max.blog_2_floor_id', '100', '最大缓存的博客id的最大层数的数量', '1', '110', '2017-06-14 21:59:57', '2017-06-14 21:59:57', '1', '0');
INSERT INTO `system_config` VALUES ('21', 'cache.max.blog_floor_2_comment_id', '1000', '缓存的最大的 博客-层数 对应的回复数量的 数量', '1', '120', '2017-06-14 22:00:31', '2017-06-14 22:00:31', '1', '0');
INSERT INTO `system_config` VALUES ('22', 'cache.max.upload_image', '100', '缓存的图片的最大的数量', '1', '130', '2017-06-14 22:01:03', '2017-06-14 22:01:03', '1', '0');
INSERT INTO `system_config` VALUES ('23', 'cache.max.role_ids_2_resource_ids', '20', '缓存的 roleIds -> resourceIds 的数量', '1', '140', '2017-06-14 22:06:57', '2017-06-14 22:06:57', '1', '0');
INSERT INTO `system_config` VALUES ('24', 'cache.max.sense_2_clicked', '1000', '缓存的 给定的博客 给定的用户是否点赞的信息', '1', '150', '2017-06-14 22:07:59', '2017-06-14 22:07:59', '1', '0');
INSERT INTO `system_config` VALUES ('25', 'cache.max.blog_id_2_blog_ex', '1000', '缓存的 blogEx 的数量', '1', '160', '2017-06-14 22:08:26', '2017-06-14 22:08:26', '1', '0');
INSERT INTO `system_config` VALUES ('26', 'cache.max.request_ip_2_blog_visit_log', '1000', '缓存的 用户对于博客的访问记录 的数量', '1', '170', '2017-06-14 22:09:38', '2017-06-14 22:09:38', '1', '0');
INSERT INTO `system_config` VALUES ('27', 'image.url.prefix', 'http://192.168.0.190:8888/HXBlog_V2.0/files/', '上传图片之后, 服务端返回的图片的 url前缀', '1', '180', '2017-06-14 22:11:52', '2018-03-03 14:51:56', '1', '0');
INSERT INTO `system_config` VALUES ('28', 'user.pwd.salt_nums', '8', '用户密码加密 加的盐的长度', '1', '190', '2017-06-14 22:12:28', '2017-06-14 22:12:28', '1', '0');
INSERT INTO `system_config` VALUES ('29', 'check_code.length', '4', '验证码的长度', '1', '200', '2017-06-14 22:12:47', '2017-06-14 22:12:47', '1', '0');
INSERT INTO `system_config` VALUES ('30', 'check_code.width', '160', '验证码的宽度', '1', '210', '2017-06-14 22:13:06', '2017-06-14 22:13:06', '1', '0');
INSERT INTO `system_config` VALUES ('31', 'check_code.height', '80', '验证码的高度', '1', '220', '2017-06-14 22:13:29', '2017-06-14 22:13:29', '1', '0');
INSERT INTO `system_config` VALUES ('32', 'check_code.candidates_str', '123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTVUWXYZ', '验证码备选的字符串', '1', '230', '2017-06-14 22:13:56', '2017-06-14 22:13:56', '1', '0');
INSERT INTO `system_config` VALUES ('33', 'check_code.min.interference', '30', '验证码干扰线的最小的数量', '1', '240', '2017-06-14 22:14:19', '2017-06-14 22:14:19', '1', '0');
INSERT INTO `system_config` VALUES ('34', 'check_code.max.interference', '10', '验证码 干扰线可以容忍的数量偏差', '1', '250', '2017-06-14 22:14:48', '2017-06-14 22:14:48', '1', '0');
INSERT INTO `system_config` VALUES ('35', 'cache.max.statistics.days', '7', '按天统计的天数', '1', '260', '2017-06-14 22:15:16', '2017-06-14 22:15:16', '1', '0');
INSERT INTO `system_config` VALUES ('36', 'chart.real_time.time_interval', '5', '实时统计的时间间隔', '1', '270', '2017-06-14 22:15:37', '2017-06-14 22:15:37', '1', '0');
INSERT INTO `system_config` VALUES ('37', 'cache.max.real_time.statistics_times', '12', '实时统计的结果缓存的数量', '1', '280', '2017-06-14 22:16:01', '2017-06-14 22:16:01', '1', '0');
INSERT INTO `system_config` VALUES ('38', 'request_log.url.to_ignore', '/index/index;/image/headImgList;/index/latest;', '记录 request_log 的时候, 需要忽略的请求', '1', '290', '2017-06-14 22:16:42', '2017-06-14 22:16:42', '1', '0');
INSERT INTO `system_config` VALUES ('39', 'entity.name.min.length', '1', '实体名称的最小长度', '2', '12', '2017-06-17 09:37:28', '2017-06-17 10:42:28', '1', '0');
INSERT INTO `system_config` VALUES ('40', '1', '1', '1', '2', '1', '2017-06-17 10:42:55', '2017-06-17 10:43:04', '1', '1');
INSERT INTO `system_config` VALUES ('41', '首页', '/', 'headerNav', '3', '0', '2017-06-17 17:55:50', '2017-06-17 17:55:50', '1', '0');
INSERT INTO `system_config` VALUES ('42', '博客列表', '/static/main/blogList.html', 'headerNav', '3', '10', '2017-06-17 18:16:04', '2017-06-17 18:16:04', '1', '0');
INSERT INTO `system_config` VALUES ('43', 'front.idx_page.title', '蓝风9', '首页的标题', '1', '300', '2017-06-24 19:10:15', '2017-06-24 19:17:33', '1', '0');
INSERT INTO `system_config` VALUES ('44', 'front.idx_page.sub_title', '大家好，我是黑客，专门盗账号的。现在这个人的帐号被我盗了，但看这个人平时的博客空间，一直过着艰苦努力、持之以恒的技术研究生活，勤奋刻苦，积极分享，无私奉献，我被深深的感动了，这是一个纯粹的人，人品这样的高尚，希望大家看到我这条消息后，可以私聊他，多鼓励他，不缺钱的就多给他一些经济上的资助，让他再接再厉！就这样吧，我下线了，眼框湿湿的难受。', '首页的子标题', '1', '310', '2017-06-24 19:10:36', '2017-06-24 19:17:15', '1', '0');
INSERT INTO `system_config` VALUES ('45', 'guest.title', 'guest', '游客的title', '1', '320', '2017-06-24 22:02:26', '2017-06-24 22:02:26', '1', '0');
INSERT INTO `system_config` VALUES ('46', 'guest.roles', 'guest', '游客的角色', '1', '330', '2017-06-24 22:02:49', '2017-06-24 22:02:49', '1', '0');
INSERT INTO `system_config` VALUES ('49', 'entity.name.max.length', '64', '实体名称的最大长度', '2', '370', '2017-06-26 20:38:20', '2017-06-26 20:45:29', '1', '0');
INSERT INTO `system_config` VALUES ('51', 'email.auth.user_name', 'hexiong@cdqcp.com', '系统发送邮件的用户名', '1', '340', '2017-06-26 20:38:48', '2017-06-26 20:38:48', '1', '0');
INSERT INTO `system_config` VALUES ('52', 'email.auth.password', '', '系统发送邮件的密码', '1', '350', '2017-06-26 20:38:48', '2017-06-26 20:38:48', '1', '0');
INSERT INTO `system_config` VALUES ('53', 'email.auth.smtp', 'smtp.exmail.qq.com', '系统发送邮件的服务器', '1', '360', '2017-06-26 20:38:48', '2017-06-26 20:38:48', '1', '0');
INSERT INTO `system_config` VALUES ('54', '点点滴滴', '/static/main/moods.html', '时间轴, 照片墙', '3', '20', '2017-06-30 19:40:39', '2017-06-30 19:40:39', '1', '0');
INSERT INTO `system_config` VALUES ('55', 'limit.max.visit_cnt.per_period', '30', '限制给定的周期内最大的访问次数', '1', '370', '2018-03-03 14:36:13', '2018-03-03 14:37:41', '1', '0');
INSERT INTO `system_config` VALUES ('56', 'context.url.prefix', 'http://192.168.0.190:8888/HXBlog_V2.0/', '当前服务上下文的 url 前缀', '1', '181', '2018-03-03 14:47:38', '2018-03-03 14:47:38', '1', '0');

-- ----------------------------
-- Table structure for uploaded_files
-- ----------------------------
DROP TABLE IF EXISTS `uploaded_files`;
CREATE TABLE `uploaded_files` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `original_file_name` varchar(2048) DEFAULT NULL COMMENT '上传的图片的原始名称',
  `content_type` varchar(256) DEFAULT NULL COMMENT '图片的类型',
  `url` varchar(2048) DEFAULT NULL COMMENT '图片的相对路径',
  `digest` varchar(128) DEFAULT NULL COMMENT '给定的图片的摘要',
  `size` int(11) DEFAULT NULL COMMENT '给定到文件的大小',
  `created_at` varchar(32) DEFAULT NULL COMMENT '文件的创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=204 DEFAULT CHARSET=utf8 COMMENT='上传到当前服务器的图片';

-- ----------------------------
-- Records of uploaded_files
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_name` varchar(64) DEFAULT NULL COMMENT '用户名',
  `pwd_salt` varchar(32) DEFAULT NULL COMMENT '给密码加把盐',
  `password` varchar(128) DEFAULT NULL COMMENT '密码',
  `nick_name` varchar(64) DEFAULT NULL COMMENT '昵称',
  `email` varchar(128) DEFAULT NULL COMMENT '邮箱',
  `title` varchar(128) DEFAULT NULL COMMENT '用户称号',
  `head_img_url` varchar(2048) DEFAULT NULL COMMENT '头像url',
  `motto` varchar(2048) DEFAULT NULL COMMENT '个性签名',
  `last_login_ip` varchar(64) DEFAULT NULL COMMENT '上一次登录的ip',
  `last_login_at` varchar(32) DEFAULT NULL COMMENT '上一次登录的时间',
  `created_at` varchar(32) DEFAULT NULL COMMENT '账号创建时间',
  `updated_at` varchar(32) DEFAULT NULL COMMENT '上一次修改时间',
  `deleted` tinyint(1) DEFAULT NULL COMMENT '账号是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('-2', 'guest', '15868791', '914B32D27992C8DAFCF2CEBFAD5805AF', 'guest', '', 'guest', 'http://tb.himg.baidu.com/sys/portrait/item/48c0c0b6b7e7393730363535313437f02d', '客户共用的dummy账号', 'null', '1970-01-01 08:00:00', '2017-06-30 20:29:34', '2017-06-30 20:29:34', '0');
INSERT INTO `user` VALUES ('-1', 'system', '36435272', '3727F8FFA9E8A6788984C23C221B7E0E', 'system', '970655147@qq.com', 'system', 'http://tb.himg.baidu.com/sys/portrait/item/48c0c0b6b7e7393730363535313437f02d', 'I am Universe', 'null', '1970-01-01 08:00:00', '2017-06-27 21:21:24', '2017-06-27 21:21:24', '0');
INSERT INTO `user` VALUES ('3', 'editor', '11111111', '53AE11208757F14BEBFD1A22E3207869', 'editor', '970655147@qq.com', 'editor', 'http://tb.himg.baidu.com/sys/portrait/item/48c0c0b6b7e7393730363535313437f02d', '写天下最好的书', '171.214.202.169', '2017-07-05 23:20:26', '2017-05-23 09:11:10', '2017-06-03 11:07:12', '0');
INSERT INTO `user` VALUES ('4', 'admin', '98508110', '328A84F3F7886815916DAFE698E86939', 'admin', '970655147@qq.com', 'admin', 'http://tb2.bdstatic.com/tb/editor/images/face/i_f25.png?t=20140803', '你好啊, 要小黑屋套餐吗 ?', '118.116.19.27', '2018-03-04 20:54:10', '2017-05-23 09:23:21', '2018-03-02 20:12:38', '0');

-- ----------------------------
-- Table structure for visitor
-- ----------------------------
DROP TABLE IF EXISTS `visitor`;
CREATE TABLE `visitor` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(256) DEFAULT NULL COMMENT '访客的用户名',
  `email` varchar(60) DEFAULT NULL COMMENT '访客的邮箱',
  `request_ip` varchar(256) DEFAULT NULL COMMENT '访客的ip',
  `ip_from_sohu` varchar(256) DEFAULT NULL COMMENT '搜狐判定的ip地址',
  `ip_addr` varchar(256) DEFAULT NULL COMMENT '搜狐判定的ip的区域',
  `header_info` varchar(4096) DEFAULT NULL COMMENT '访客的请求头信息',
  `created_at` varchar(32) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='记录访客的表';

-- ----------------------------
-- Records of visitor
-- ----------------------------
