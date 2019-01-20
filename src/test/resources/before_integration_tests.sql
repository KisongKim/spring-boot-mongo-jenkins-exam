
create table if not exists GAME (
  ID bigint auto_increment primary key,
  PLATFORM varchar(32) not null,
  RATING varchar(16) not null,
  PUBLISHER varchar(256) not null,
  TITLE varchar(256) not null,
  RELEASE_DATE date not null,
  REGISTER_DATE_TIME timestamp not null);

insert into GAME(ID,PLATFORM,RATING,PUBLISHER,TITLE,RELEASE_DATE,REGISTER_DATE_TIME) values(1, 'PS_4', 'PG_18', 'Activision', 'Destiny 2', current_date(), current_timestamp());
insert into GAME(ID,PLATFORM,RATING,PUBLISHER,TITLE,RELEASE_DATE,REGISTER_DATE_TIME) values(2, 'XBOX_ONE', 'PG_18', 'Activision', 'Destiny 2', current_date(), current_timestamp());
insert into GAME(ID,PLATFORM,RATING,PUBLISHER,TITLE,RELEASE_DATE,REGISTER_DATE_TIME) values(3, 'SWITCH', 'PG_18', 'Nintendo', 'Super Mario', current_date(), current_timestamp());
insert into GAME(ID,PLATFORM,RATING,PUBLISHER,TITLE,RELEASE_DATE,REGISTER_DATE_TIME) values(4, 'PS_4', 'PG_12', 'Sony', 'Horizon Zero Dawn', current_date(), current_timestamp());