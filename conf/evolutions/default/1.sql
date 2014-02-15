# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table answer (
  id                        bigint not null,
  user_id                   bigint not null,
  text                      varchar(255),
  rating                    integer,
  constraint pk_answer primary key (id))
;

create table course (
  id                        bigint not null,
  name                      varchar(255),
  constraint pk_course primary key (id))
;

create table question (
  id                        bigint not null,
  user_id                   bigint not null,
  title                     varchar(255),
  text                      varchar(255),
  rating                    integer,
  constraint pk_question primary key (id))
;

create table user (
  id                        bigint not null,
  name                      varchar(255),
  user_name                 varchar(255),
  constraint pk_user primary key (id))
;


create table user_course (
  user_id                        bigint not null,
  course_id                      bigint not null,
  constraint pk_user_course primary key (user_id, course_id))
;
create sequence answer_seq;

create sequence course_seq;

create sequence question_seq;

create sequence user_seq;

alter table answer add constraint fk_answer_user_1 foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_answer_user_1 on answer (user_id);
alter table question add constraint fk_question_user_2 foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_question_user_2 on question (user_id);



alter table user_course add constraint fk_user_course_user_01 foreign key (user_id) references user (id) on delete restrict on update restrict;

alter table user_course add constraint fk_user_course_course_02 foreign key (course_id) references course (id) on delete restrict on update restrict;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists answer;

drop table if exists course;

drop table if exists question;

drop table if exists user;

drop table if exists user_course;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists answer_seq;

drop sequence if exists course_seq;

drop sequence if exists question_seq;

drop sequence if exists user_seq;

