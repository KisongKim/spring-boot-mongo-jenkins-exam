alter table CUSTOMER
  drop constraint CUSTOMER_EMAIL_UNIQUE;

drop table if exists GAME;
drop table if exists GAME_INVENTORY;
drop table if exists DELIVERY_ADDRESS;
drop table if exists CUSTOMER;