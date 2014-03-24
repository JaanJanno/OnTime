# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table event (
  id                        bigint auto_increment not null,
  title                     varchar(255),
  date                      varchar(255),
  user_email                varchar(255),
  constraint pk_event primary key (id))
;

create table event_query (
  title                     varchar(255),
  date                      varchar(255),
  korraldaja                varchar(255))
;

create table terrain (
  id                        bigint auto_increment not null,
  constraint pk_terrain primary key (id))
;

create table terrain_object (
  id                        bigint auto_increment not null,
  terrain_type              integer,
  constraint pk_terrain_object primary key (id))
;

create table tribe (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  people_amount             bigint,
  fighting                  tinyint,
  fishing                   tinyint,
  hunting                   tinyint,
  tracking                  tinyint,
  food                      bigint,
  constraint pk_tribe primary key (id))
;

create table user (
  email                     varchar(255) not null,
  name                      varchar(255),
  organization_name         varchar(255),
  password                  varchar(255),
  constraint pk_user primary key (email))
;

create sequence user_seq;

alter table event add constraint fk_event_user_1 foreign key (user_email) references user (email) on delete restrict on update restrict;
create index ix_event_user_1 on event (user_email);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists event;

drop table if exists event_query;

drop table if exists terrain;

drop table if exists terrain_object;

drop table if exists tribe;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists user_seq;

