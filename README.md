HXBlog_V2.0
====
一个简单的博客系统, 你可以用来记录日志, 写日记, 总结知识等等


详细请见 
---
1. 简单介绍 : http://blog.csdn.net/u011039332/article/details/75196276

2. 逻辑介绍 : 请查看相关部分的代码


QuickStart
---
1 克隆项目

![design_picure](https://raw.githubusercontent.com/970655147/HXBlog_V2.0/master/resources/readMeRes/1st_clone.png)


2 下载依赖的jar包

![design_picure](https://raw.githubusercontent.com/970655147/HXBlog_V2.0/master/resources/readMeRes/2nd_downloadJars.png)

下载下来之后解压目录结构如下

![design_picure](https://raw.githubusercontent.com/970655147/HXBlog_V2.0/master/resources/readMeRes/2nd_blog_deps.png)

依赖部分解压目录结构如下

![design_picure](https://raw.githubusercontent.com/970655147/HXBlog_V2.0/master/resources/readMeRes/2nd_blog_libs.png)

测试资源部分解压目录结构如下

![design_picure](https://raw.githubusercontent.com/970655147/HXBlog_V2.0/master/resources/readMeRes/2nd_blog_resources.png)

将依赖部分的内容复制到maven 本地仓库
	假设 $MVN_REPO 为maven本地仓库的位置
	1. 在 $MVN_REPO 下面创建 com/hx 层级的文件夹
	2. 将依赖复制到 $MVN_REPO/com/hx 下面 就行了
将测试资源解压到给定的文件夹
	测试资源中包含了两部分内容, 博客的内容数据 + 用户上传的文件信息
	解压到 特定的文件夹之后, 我们这里假设该文件夹为 $RESOURCES

3 解压项目和 依赖的包, 导入工具, 配置环境变量, 添加依赖的包

![design_picure](https://raw.githubusercontent.com/970655147/HXBlog_V2.0/master/resources/readMeRes/3rd_importProj.png)

然后创建数据库, 导入测试数据[resources/HXBlog.sql]

![design_picure](https://raw.githubusercontent.com/970655147/HXBlog_V2.0/master/resources/readMeRes/3rd_loadDbData.png)

更新配置配置信息, 更新 src/main/resources/dev/*.properties, 各个profile 根据自己的实际情况配置, 这里以默认的dev profile举例

![design_picure](https://raw.githubusercontent.com/970655147/HXBlog_V2.0/master/resources/readMeRes/3rd_configConfigProps.png)
![design_picure](https://raw.githubusercontent.com/970655147/HXBlog_V2.0/master/resources/readMeRes/3rd_configDbProps.png)


4 发布项目, 启动服务器

![design_picure](https://raw.githubusercontent.com/970655147/HXBlog_V2.0/master/resources/readMeRes/4th_deploy.png)
![design_picure](https://raw.githubusercontent.com/970655147/HXBlog_V2.0/master/resources/readMeRes/4th_startServer.png)

5 访问项目

![design_picure](https://raw.githubusercontent.com/970655147/HXBlog_V2.0/master/resources/readMeRes/5th_visit.png)

ok 项目的构建到这里就结束了, 至于功能的实现, 业务的处理, 请详见代码!

哦, 对了 还有一个细节的地方, 关于资源的上传与访问
需要结合 files.dir[config.properties] + image.url.prefix[system_config表中配置] 以及对应的资源服务器进行处理
比如 以我本地为例, 配置了 files.dir 为 D:\\tmp\\HXBlog_V2.0Res\\files, 并且配置了 image.url.prefix 为 http://localhost/files/
那么 需要访问资源的话, 还需要一个静态资源的服务器[我本地是nginx]监听80端口, 以及文件系统的 D:\\tmp\\HXBlog_V2.0Res

然后 如果想直接使用 tomcat作为静态资源服务器, 按照策略配置就行了
比如 配置 files.dir 为 : D:\\Program Files\\apache-tomcat-7.0.52\\webapps\\HXBlogRes, 那么需要配置 image.url.prefix 为 http://localhost:8080/HXBlogRes

Refer
---
请详见 resources/developLog.txt
或者 上面的简介的博客
	