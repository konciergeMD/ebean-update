# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table bar (
  id                        bigint not null,
  string_var                varchar(255),
  integer_var               integer,
  enum_var                  integer,
  constraint ck_bar_enum_var check (enum_var in (0,1)),
  constraint pk_bar primary key (id))
;

create table foo (
  id                        bigint not null,
  string_var                varchar(255),
  integer_var               integer,
  enum_var                  integer,
  object_var_id             bigint,
  constraint ck_foo_enum_var check (enum_var in (0,1)),
  constraint pk_foo primary key (id))
;

create sequence bar_seq;

create sequence foo_seq;

alter table foo add constraint fk_foo_objectVar_1 foreign key (object_var_id) references bar (id) on delete restrict on update restrict;
create index ix_foo_objectVar_1 on foo (object_var_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists bar;

drop table if exists foo;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists bar_seq;

drop sequence if exists foo_seq;

