
create table if not exists GAME (
  ID bigint auto_increment primary key,
  PLATFORM varchar(32) not null,
  RATING varchar(16) not null,
  PUBLISHER varchar(256) not null,
  TITLE varchar(256) not null,
  RELEASE_DATE date not null,
  REGISTER_DATE_TIME timestamp not null);

create table if not exists GAME_INVENTORY (
  ID bigint auto_increment primary key,
  GAME_ID bigint not null,
  STOCK int not null,
  VERSION bigint not null,
  UPDATE_DATE_TIME timestamp not null,
  REGISTER_DATE_TIME timestamp not null,
);

create table if not exists CUSTOMER (
  ID bigint auto_increment primary key,
  EMAIL varchar(256) not null,
  PASSWORD varchar(64) not null,
  FAMILY_NAME varchar(64) not null,
  GIVEN_NAME varchar(64) not null,
  REGISTER_DATE_TIME timestamp not null,
);
alter table CUSTOMER add constraint CUSTOMER_EMAIL_UNIQUE unique(EMAIL);

create table if not exists DELIVERY_ADDRESS (
  ID bigint auto_increment primary key,
  CUSTOMER_ID bigint not null,
  CITY varchar(64) not null,
  STREET varchar(64) not null,
  POSTAL_CODE varchar(64) not null,
  FAVORITE boolean not null,
  REGISTER_DATE_TIME timestamp not null,
);

-- GAME
insert into GAME(ID,PLATFORM,RATING,PUBLISHER,TITLE,RELEASE_DATE,REGISTER_DATE_TIME) values(1, 'PS_4', 'PG_18', 'Activision', 'Destiny 2', current_date(), current_timestamp());
insert into GAME(ID,PLATFORM,RATING,PUBLISHER,TITLE,RELEASE_DATE,REGISTER_DATE_TIME) values(2, 'XBOX_ONE', 'PG_18', 'Activision', 'Destiny 2', current_date(), current_timestamp());
insert into GAME(ID,PLATFORM,RATING,PUBLISHER,TITLE,RELEASE_DATE,REGISTER_DATE_TIME) values(3, 'SWITCH', 'PG_18', 'Nintendo', 'Super Mario', current_date(), current_timestamp());
insert into GAME(ID,PLATFORM,RATING,PUBLISHER,TITLE,RELEASE_DATE,REGISTER_DATE_TIME) values(4, 'PS_4', 'PG_12', 'Sony', 'Horizon Zero Dawn', current_date(), current_timestamp());

-- GAME_INVENTORY
insert into GAME_INVENTORY(ID,GAME_ID,STOCK,VERSION,UPDATE_DATE_TIME,REGISTER_DATE_TIME) values(1, 1, 10, 1, current_timestamp(), current_timestamp());
insert into GAME_INVENTORY(ID,GAME_ID,STOCK,VERSION,UPDATE_DATE_TIME,REGISTER_DATE_TIME) values(2, 2, 20, 1, current_timestamp(), current_timestamp());