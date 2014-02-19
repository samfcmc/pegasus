# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table answer (
  id                        bigint auto_increment not null,
  question_id               bigint not null,
  text                      varchar(255),
  rating                    integer,
  constraint pk_answer primary key (id))
;

create table course (
  id                        bigint auto_increment not null,
  fenix_id                  varchar(255),
  acronym                   varchar(255),
  name                      varchar(255),
  constraint pk_course primary key (id))
;

create table question (
  id                        bigint auto_increment not null,
  course_id                 bigint not null,
  title                     varchar(255),
  text                      varchar(255),
  rating                    integer,
  constraint pk_question primary key (id))
;

create table user (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  user_name                 varchar(255),
  access_token              varchar(255),
  refresh_token             varchar(255),
  constraint pk_user primary key (id))
;


create table user_course (
  user_id                        bigint not null,
  course_id                      bigint not null,
  constraint pk_user_course primary key (user_id, course_id))
;
alter table answer add constraint fk_answer_question_1 foreign key (question_id) references question (id) on delete restrict on update restrict;
create index ix_answer_question_1 on answer (question_id);
alter table question add constraint fk_question_course_2 foreign key (course_id) references course (id) on delete restrict on update restrict;
create index ix_question_course_2 on question (course_id);



alter table user_course add constraint fk_user_course_user_01 foreign key (user_id) references user (id) on delete restrict on update restrict;

alter table user_course add constraint fk_user_course_course_02 foreign key (course_id) references course (id) on delete restrict on update restrict;

# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table answer;

drop table course;

drop table question;

drop table user;

drop table user_course;

SET FOREIGN_KEY_CHECKS=1;

