# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table event (
  id                        bigint auto_increment not null,
  title                     varchar(255),
  date                      varchar(255),
  constraint pk_event primary key (id))
;

create table user (
  email                     varchar(255) not null,
  name                      varchar(255),
  organization_name         varchar(255),
  password                  varchar(255),
  constraint pk_user primary key (email))
;

create sequence user_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists event;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists user_seq;

