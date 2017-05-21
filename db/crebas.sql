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
   id                   int(11) not null auto_increment comment '����',
   title                varchar(256) comment '����',
   author               varchar(256) comment '����',
   cover_url            varchar(2048) comment '�����url',
   blog_type_id         int(11) comment '���͵�����',
   summary              varchar(2048) comment 'ժҪ',
   content_url          varchar(256) comment '���͵����ݵ�����',
   created_at           datetime comment '������ʱ��',
   created_at_month     varchar(12) comment '�������·�',
   updated_at           datetime comment '�޸ĵ�ʱ��',
   primary key (id)
);

alter table blog comment '���ͼ�¼��';

/*==============================================================*/
/* Table: blog_comment                                          */
/*==============================================================*/
create table blog_comment
(
   id                   int(11) not null auto_increment comment '����',
   blog_id              int(11) comment '������ blogId',
   floor_id             int(11) comment '���۵Ĳ���',
   comment_id           int(11) comment '�����Ĳ�����۵�����id',
   name                 varchar(256) comment '���۵��û�������',
   email                varchar(60) comment '���۵��û�������',
   head_img_url         varchar(2048) comment '���۵��û���ͷ��',
   to_user              varchar(256) comment '���۵Ķ���',
   role                 varchar(60) comment '���۵��û��Ľ�ɫ',
   content              varchar(2048) comment '���۵�����',
   created_at           datetime comment '����ʱ��',
   updated_at           datetime comment '�޸�ʱ��',
   primary key (id)
);

alter table blog_comment comment '�������۱�';

/*==============================================================*/
/* Table: blog_ex                                               */
/*==============================================================*/
create table blog_ex
(
   id                   int(11) not null auto_increment comment '����',
   blog_id              int(11) comment '������ blogId',
   comment_cnt          int(11) comment '���۵�����',
   view_cnt             int(11) comment '�鿴������',
   good_cnt             int(11) comment '��������',
   not_good_cnt         int(11) comment '�ȵ�����',
   primary key (id)
);

alter table blog_ex comment '���Ͷ�����Ϣ��';

/*==============================================================*/
/* Table: blog_sense                                            */
/*==============================================================*/
create table blog_sense
(
   id                   int(11) not null auto_increment,
   blog_id              int(11) comment '������ blogId',
   name                 varchar(256) comment '���ȵ��û�',
   email                varchar(256) comment '���ȵ��û�������',
   is_good              varchar(10) comment '��/��',
   created_at           datetime comment '����ʱ��',
   primary key (id)
);

alter table blog_sense comment '���Ͷ��ȼ�¼��';

/*==============================================================*/
/* Table: blog_tag                                              */
/*==============================================================*/
create table blog_tag
(
   id                   int(11) not null auto_increment comment '����',
   name                 varchar(256) comment '����',
   created_at           datetime comment '����ʱ��',
   updated_at           datetime comment '�޸�ʱ��',
   primary key (id)
);

alter table blog_tag comment '��ǩ��';

/*==============================================================*/
/* Table: blog_type                                             */
/*==============================================================*/
create table blog_type
(
   id                   int(11) not null auto_increment comment '����',
   name                 varchar(256) comment '����',
   created_at           datetime comment '����ʱ��',
   updated_at           datetime comment '�޸�ʱ��',
   primary key (id)
);

alter table blog_type comment '��ǩ����';

/*==============================================================*/
/* Table: exception_log                                         */
/*==============================================================*/
create table exception_log
(
   id                   int(11) not null auto_increment comment '����',
   name                 varchar(256) comment '�����쳣���û�',
   email                varchar(60) comment '�����쳣���û�������',
   request_ip           varchar(6) comment '�����쳣���û���ip',
   msg                  varchar(2048) comment '�쳣��Ϣ',
   created_at           datetime comment '����ʱ��',
   primary key (id)
);

alter table exception_log comment '�쳣��¼��';

/*==============================================================*/
/* Table: request_log                                           */
/*==============================================================*/
create table request_log
(
   id                   int(11) not null auto_increment comment '����',
   url                  varchar(256) comment '�����url',
   params               varchar(2048) comment '����Ĳ���',
   cost                 int(11) comment '�������ʱ��',
   name                 varchar(256) comment '����������û�����',
   email                varchar(256) comment '����������û�����',
   created_at           datetime comment '����ʱ��',
   primary key (id)
);

alter table request_log comment '�����¼��';

/*==============================================================*/
/* Table: rlt_blog_tag                                          */
/*==============================================================*/
create table rlt_blog_tag
(
   id                   int(11) not null auto_increment comment '����',
   blog_id              int(11) comment '������ blogId',
   tag_id               int(11) comment '������ tagId',
   tag_name             varchar(256) comment '������ tagName',
   primary key (id)
);

/*==============================================================*/
/* Table: visitor                                               */
/*==============================================================*/
create table visitor
(
   id                   int(11) not null auto_increment comment '����',
   name                 varchar(256) comment '�ÿ͵��û���',
   email                varchar(60) comment '�ÿ͵�����',
   request_ip           varchar(6) comment '�ÿ͵�ip',
   header_info          varchar(4096) comment '�ÿ͵�����ͷ��Ϣ',
   created_at           datetime comment '����ʱ��',
   primary key (id)
);

alter table visitor comment '��¼�ÿ͵ı�';

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

