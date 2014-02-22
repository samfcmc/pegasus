# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table admin (
  id                        bigint auto_increment not null,
  user_id                   bigint,
  constraint pk_admin primary key (id))
;

create table answer (
  id                        bigint auto_increment not null,
  text                      varchar(255),
  rating                    integer,
  owner_id                  bigint,
  question_id               bigint,
  constraint pk_answer primary key (id))
;

create table question (
  id                        bigint auto_increment not null,
  title                     varchar(255),
  text                      varchar(255),
  created                   datetime,
  owner_id                  bigint,
  constraint pk_question primary key (id))
;

create table tag (
  id                        bigint auto_increment not null,
  label                     varchar(255),
  description               varchar(255),
  constraint pk_tag primary key (id))
;

create table tag_request (
  id                        bigint auto_increment not null,
  tag_label                 varchar(255),
  tag_description           varchar(255),
  requester_id              bigint,
  constraint pk_tag_request primary key (id))
;

create table user (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  user_name                 varchar(255),
  access_token              varchar(255),
  refresh_token             varchar(255),
  constraint pk_user primary key (id))
;

create table vote (
  user_id                   bigint,
  question_id               bigint,
  value                     integer)
;


create table question_tag (
  question_id                    bigint not null,
  tag_id                         bigint not null,
  constraint pk_question_tag primary key (question_id, tag_id))
;

create table tag_question (
  tag_id                         bigint not null,
  question_id                    bigint not null,
  constraint pk_tag_question primary key (tag_id, question_id))
;

create table tag_user (
  tag_id                         bigint not null,
  user_id                        bigint not null,
  constraint pk_tag_user primary key (tag_id, user_id))
;

create table user_tag (
  user_id                        bigint not null,
  tag_id                         bigint not null,
  constraint pk_user_tag primary key (user_id, tag_id))
;
alter table admin add constraint fk_admin_user_1 foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_admin_user_1 on admin (user_id);
alter table answer add constraint fk_answer_owner_2 foreign key (owner_id) references user (id) on delete restrict on update restrict;
create index ix_answer_owner_2 on answer (owner_id);
alter table answer add constraint fk_answer_question_3 foreign key (question_id) references question (id) on delete restrict on update restrict;
create index ix_answer_question_3 on answer (question_id);
alter table question add constraint fk_question_owner_4 foreign key (owner_id) references user (id) on delete restrict on update restrict;
create index ix_question_owner_4 on question (owner_id);
alter table tag_request add constraint fk_tag_request_requester_5 foreign key (requester_id) references user (id) on delete restrict on update restrict;
create index ix_tag_request_requester_5 on tag_request (requester_id);
alter table vote add constraint fk_vote_user_6 foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_vote_user_6 on vote (user_id);
alter table vote add constraint fk_vote_question_7 foreign key (question_id) references question (id) on delete restrict on update restrict;
create index ix_vote_question_7 on vote (question_id);



alter table question_tag add constraint fk_question_tag_question_01 foreign key (question_id) references question (id) on delete restrict on update restrict;

alter table question_tag add constraint fk_question_tag_tag_02 foreign key (tag_id) references tag (id) on delete restrict on update restrict;

alter table tag_question add constraint fk_tag_question_tag_01 foreign key (tag_id) references tag (id) on delete restrict on update restrict;

alter table tag_question add constraint fk_tag_question_question_02 foreign key (question_id) references question (id) on delete restrict on update restrict;

alter table tag_user add constraint fk_tag_user_tag_01 foreign key (tag_id) references tag (id) on delete restrict on update restrict;

alter table tag_user add constraint fk_tag_user_user_02 foreign key (user_id) references user (id) on delete restrict on update restrict;

alter table user_tag add constraint fk_user_tag_user_01 foreign key (user_id) references user (id) on delete restrict on update restrict;

alter table user_tag add constraint fk_user_tag_tag_02 foreign key (tag_id) references tag (id) on delete restrict on update restrict;

# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table admin;

drop table answer;

drop table question;

drop table question_tag;

drop table tag;

drop table tag_question;

drop table tag_user;

drop table tag_request;

drop table user;

drop table user_tag;

drop table vote;

SET FOREIGN_KEY_CHECKS=1;

