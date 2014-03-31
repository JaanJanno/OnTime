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

create table game_event_query (
  msg                       varchar(255),
  count                     bigint)
;

create table special_event (
  id                        bigint auto_increment not null,
  text                      varchar(255),
  tribe_id                  bigint,
  type                      varchar(255),
  constraint pk_special_event primary key (id))
;

create table terrain (
  id                        bigint auto_increment not null,
  width                     integer,
  height                    integer,
  constraint pk_terrain primary key (id))
;

create table terrain_object (
  id                        bigint auto_increment not null,
  x                         integer,
  y                         integer,
  terrain_type              integer,
  terrain_area_id           bigint,
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
  position_id               bigint,
  food                      bigint,
  constraint pk_tribe primary key (id))
;

create table user (
  email                     varchar(255) not null,
  name                      varchar(255),
  organization_name         varchar(255),
  password                  varchar(255),
  tribe_id                  bigint,
  constraint pk_user primary key (email))
;

create table war_event (
  id                        bigint auto_increment not null,
  text                      varchar(255),
  tribe_id                  bigint,
  constraint pk_war_event primary key (id))
;

create sequence user_seq;

alter table event add constraint fk_event_user_1 foreign key (user_email) references user (email) on delete restrict on update restrict;
create index ix_event_user_1 on event (user_email);
alter table special_event add constraint fk_special_event_tribe_2 foreign key (tribe_id) references tribe (id) on delete restrict on update restrict;
create index ix_special_event_tribe_2 on special_event (tribe_id);
alter table terrain_object add constraint fk_terrain_object_terrainArea_3 foreign key (terrain_area_id) references terrain (id) on delete restrict on update restrict;
create index ix_terrain_object_terrainArea_3 on terrain_object (terrain_area_id);
alter table tribe add constraint fk_tribe_position_4 foreign key (position_id) references terrain_object (id) on delete restrict on update restrict;
create index ix_tribe_position_4 on tribe (position_id);
alter table user add constraint fk_user_tribe_5 foreign key (tribe_id) references tribe (id) on delete restrict on update restrict;
create index ix_user_tribe_5 on user (tribe_id);
alter table war_event add constraint fk_war_event_tribe_6 foreign key (tribe_id) references tribe (id) on delete restrict on update restrict;
create index ix_war_event_tribe_6 on war_event (tribe_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists event;

drop table if exists event_query;

drop table if exists game_event_query;

drop table if exists special_event;

drop table if exists terrain;

drop table if exists terrain_object;

drop table if exists tribe;

drop table if exists user;

drop table if exists war_event;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists user_seq;

