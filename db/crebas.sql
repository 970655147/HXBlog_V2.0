/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     5/20/2017 2:06:58 PM                         */
/*==============================================================*/


drop table if exists blog;

drop table if exists blog_comment;

drop table if exists blog_ex;

drop table if exists blog_sense;

drop table if exists blog_tag;

drop table if exists blog_type;

drop table if exists exception_log;

drop table if exists request_log;

drop table if exists rlt_blog_tag;

drop table if exists visitor;

/*==============================================================*/
/* Table: blog                                                  */
/*==============================================================*/
create table blog
(
   id                   int(11) not null auto_increment comment '主键',
   title                varchar(256) comment '标题',
   author               varchar(256) comment '作者',
   cover_url            varchar(2048) comment '封面的url',
   blog_type_id         int(11) comment '博客的类型',
   summary              varchar(2048) comment '摘要',
   content_url          varchar(256) comment '博客的内容的索引',
   created_at           datetime comment '创建的时间',
   created_at_month     varchar(12) comment '创建的月份',
   updated_at           datetime comment '修改的时间',
   primary key (id)
);

alter table blog comment '博客记录表';

/*==============================================================*/
/* Table: blog_comment                                          */
/*==============================================================*/
create table blog_comment
(
   id                   int(11) not null auto_increment comment '主键',
   blog_id              int(11) comment '关联的 blogId',
   floor_id             int(11) comment '评论的层数',
   comment_id           int(11) comment '给定的层的评论的评论id',
   name                 varchar(256) comment '评论的用户的姓名',
   email                varchar(60) comment '评论的用户的邮箱',
   head_img_url         varchar(2048) comment '评论的用户的头像',
   to_user              varchar(256) comment '评论的对象',
   role                 varchar(60) comment '评论的用户的角色',
   content              varchar(2048) comment '评论的内容',
   created_at           datetime comment '创建时间',
   updated_at           datetime comment '修改时间',
   primary key (id)
);

alter table blog_comment comment '博客评论表';

/*==============================================================*/
/* Table: blog_ex                                               */
/*==============================================================*/
create table blog_ex
(
   id                   int(11) not null auto_increment comment '主键',
   blog_id              int(11) comment '关联的 blogId',
   comment_cnt          int(11) comment '评论的数量',
   view_cnt             int(11) comment '查看的数量',
   good_cnt             int(11) comment '顶的数量',
   not_good_cnt         int(11) comment '踩的数量',
   primary key (id)
);

alter table blog_ex comment '博客额外信息表';

/*==============================================================*/
/* Table: blog_sense                                            */
/*==============================================================*/
create table blog_sense
(
   id                   int(11) not null auto_increment,
   blog_id              int(11) comment '关联的 blogId',
   name                 varchar(256) comment '顶踩的用户',
   email                varchar(256) comment '顶踩的用户的邮箱',
   is_good              varchar(10) comment '顶/踩',
   created_at           datetime comment '创建时间',
   primary key (id)
);

alter table blog_sense comment '博客顶踩记录表';

/*==============================================================*/
/* Table: blog_tag                                              */
/*==============================================================*/
create table blog_tag
(
   id                   int(11) not null auto_increment comment '主键',
   name                 varchar(256) comment '名称',
   created_at           datetime comment '创建时间',
   updated_at           datetime comment '修改时间',
   primary key (id)
);

alter table blog_tag comment '标签表';

/*==============================================================*/
/* Table: blog_type                                             */
/*==============================================================*/
create table blog_type
(
   id                   int(11) not null auto_increment comment '主键',
   name                 varchar(256) comment '名称',
   created_at           datetime comment '创建时间',
   updated_at           datetime comment '修改时间',
   primary key (id)
);

alter table blog_type comment '标签分类';

/*==============================================================*/
/* Table: exception_log                                         */
/*==============================================================*/
create table exception_log
(
   id                   int(11) not null auto_increment comment '主键',
   name                 varchar(256) comment '触发异常的用户',
   email                varchar(60) comment '触发异常的用户的邮箱',
   request_ip           varchar(6) comment '触发异常的用户的ip',
   msg                  varchar(2048) comment '异常信息',
   created_at           datetime comment '创建时间',
   primary key (id)
);

alter table exception_log comment '异常记录表';

/*==============================================================*/
/* Table: request_log                                           */
/*==============================================================*/
create table request_log
(
   id                   int(11) not null auto_increment comment '主键',
   url                  varchar(256) comment '请求的url',
   params               varchar(2048) comment '请求的参数',
   cost                 int(11) comment '请求处理的时间',
   name                 varchar(256) comment '发起请求的用户名称',
   email                varchar(256) comment '发起请求的用户邮箱',
   created_at           datetime comment '创建时间',
   primary key (id)
);

alter table request_log comment '请求记录表';

/*==============================================================*/
/* Table: rlt_blog_tag                                          */
/*==============================================================*/
create table rlt_blog_tag
(
   id                   int(11) not null auto_increment comment '主键',
   blog_id              int(11) comment '关联的 blogId',
   tag_id               int(11) comment '关联的 tagId',
   tag_name             varchar(256) comment '关联的 tagName',
   primary key (id)
);

/*==============================================================*/
/* Table: visitor                                               */
/*==============================================================*/
create table visitor
(
   id                   int(11) not null auto_increment comment '主键',
   name                 varchar(256) comment '访客的用户名',
   email                varchar(60) comment '访客的邮箱',
   request_ip           varchar(6) comment '访客的ip',
   header_info          varchar(4096) comment '访客的请求头信息',
   created_at           datetime comment '创建时间',
   primary key (id)
);

alter table visitor comment '记录访客的表';

alter table blog add constraint FK_Reference_4 foreign key (blog_type_id)
      references blog_type (id) on delete restrict on update restrict;

alter table blog_comment add constraint FK_Reference_2 foreign key (blog_id)
      references blog (id) on delete restrict on update restrict;

alter table blog_ex add constraint FK_Reference_1 foreign key (blog_id)
      references blog (id) on delete restrict on update restrict;

alter table blog_sense add constraint FK_Reference_3 foreign key (blog_id)
      references blog (id) on delete restrict on update restrict;

alter table rlt_blog_tag add constraint FK_Reference_5 foreign key (blog_id)
      references blog (id) on delete restrict on update restrict;

alter table rlt_blog_tag add constraint FK_Reference_6 foreign key (tag_id)
      references blog_tag (id) on delete restrict on update restrict;

