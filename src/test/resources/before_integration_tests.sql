create table if not exists GAME
(
  ID                 bigint identity primary key,
  PLATFORM           varchar(32)  not null,
  RATING             varchar(16)  not null,
  PUBLISHER          varchar(256) not null,
  TITLE              varchar(256) not null,
  RELEASE_DATE       date         not null,
  REGISTER_DATE_TIME timestamp    not null
);

create table if not exists GAME_INVENTORY
(
  ID                 bigint identity primary key,
  GAME_ID            bigint    not null,
  STOCK              int       not null,
  VERSION            bigint    not null,
  UPDATE_DATE_TIME   timestamp not null,
  REGISTER_DATE_TIME timestamp not null,
);

create table if not exists CUSTOMER
(
  ID                 bigint identity primary key,
  EMAIL              varchar(256) not null,
  CUSTOMER_SECRET    varchar(256) not null,
  FAMILY_NAME        varchar(64)  not null,
  GIVEN_NAME         varchar(64)  not null,
  REGISTER_DATE_TIME timestamp    not null,
);
alter table CUSTOMER
  add constraint CUSTOMER_EMAIL_UNIQUE unique (EMAIL);

create table if not exists DELIVERY_ADDRESS
(
  ID                 bigint identity primary key,
  CUSTOMER_ID        bigint      not null,
  CITY               varchar(64) not null,
  STREET             varchar(64) not null,
  POSTAL_CODE        varchar(64) not null,
  FAVORITE           boolean     not null,
  REGISTER_DATE_TIME timestamp   not null,
);

drop table if exists oauth_client_details;
create table oauth_client_details
(
  client_id               VARCHAR(256) PRIMARY KEY,
  resource_ids            VARCHAR(256),
  client_secret           VARCHAR(256),
  scope                   VARCHAR(256),
  authorized_grant_types  VARCHAR(256),
  web_server_redirect_uri VARCHAR(256),
  authorities             VARCHAR(256),
  access_token_validity   INTEGER,
  refresh_token_validity  INTEGER,
  additional_information  VARCHAR(4096),
  autoapprove             VARCHAR(256)
);

create table if not exists oauth_client_token
(
  token_id          VARCHAR(256),
  token             LONGVARBINARY,
  authentication_id VARCHAR(256) PRIMARY KEY,
  user_name         VARCHAR(256),
  client_id         VARCHAR(256)
);

create table if not exists oauth_access_token
(
  token_id          VARCHAR(256),
  token             LONGVARBINARY,
  authentication_id VARCHAR(256) PRIMARY KEY,
  user_name         VARCHAR(256),
  client_id         VARCHAR(256),
  authentication    LONGVARBINARY,
  refresh_token     VARCHAR(256)
);

create table if not exists oauth_refresh_token
(
  token_id       VARCHAR(256),
  token          LONGVARBINARY,
  authentication LONGVARBINARY
);

create table if not exists oauth_code
(
  code           VARCHAR(256),
  authentication LONGVARBINARY
);

create table if not exists oauth_approvals
(
  userId         VARCHAR(256),
  clientId       VARCHAR(256),
  scope          VARCHAR(256),
  status         VARCHAR(10),
  expiresAt      TIMESTAMP,
  lastModifiedAt TIMESTAMP
);

-- customized oauth_client_details table
create table if not exists ClientDetails
(
  appId                  VARCHAR(256) PRIMARY KEY,
  resourceIds            VARCHAR(256),
  appSecret              VARCHAR(256),
  scope                  VARCHAR(256),
  grantTypes             VARCHAR(256),
  redirectUrl            VARCHAR(256),
  authorities            VARCHAR(256),
  access_token_validity  INTEGER,
  refresh_token_validity INTEGER,
  additionalInformation  VARCHAR(4096),
  autoApproveScopes      VARCHAR(256)
);

-- GAME
insert into GAME(ID, PLATFORM, RATING, PUBLISHER, TITLE, RELEASE_DATE, REGISTER_DATE_TIME)
values (default, 'PS_4', 'PG_18', 'Activision', 'Destiny 2', current_date(), current_timestamp());
insert into GAME(ID, PLATFORM, RATING, PUBLISHER, TITLE, RELEASE_DATE, REGISTER_DATE_TIME)
values (default, 'XBOX_ONE', 'PG_18', 'Activision', 'Destiny 2', current_date(), current_timestamp());
insert into GAME(ID, PLATFORM, RATING, PUBLISHER, TITLE, RELEASE_DATE, REGISTER_DATE_TIME)
values (default, 'SWITCH', 'PG_18', 'Nintendo', 'Super Mario', current_date(), current_timestamp());
insert into GAME(ID, PLATFORM, RATING, PUBLISHER, TITLE, RELEASE_DATE, REGISTER_DATE_TIME)
values (default, 'PS_4', 'PG_12', 'Sony', 'Horizon Zero Dawn', current_date(), current_timestamp());

-- GAME_INVENTORY
insert into GAME_INVENTORY(ID, GAME_ID, STOCK, VERSION, UPDATE_DATE_TIME, REGISTER_DATE_TIME)
values (default, 1, 10, 1, current_timestamp(), current_timestamp());
insert into GAME_INVENTORY(ID, GAME_ID, STOCK, VERSION, UPDATE_DATE_TIME, REGISTER_DATE_TIME)
values (default, 2, 20, 1, current_timestamp(), current_timestamp());

-- CUSTOMER bruce@test.com:password
insert into CUSTOMER(ID, EMAIL, CUSTOMER_SECRET, FAMILY_NAME, GIVEN_NAME, REGISTER_DATE_TIME)
values (default, 'bruce@test.com', '$2a$11$sTdm0YUj5VlXae9dHD7L0OM5kghMrDDe5KRqvckX9N2ss2NkpdqIW', 'bruce', 'wayne', current_timestamp());

-- DELIVERY_ADDRESS
insert into DELIVERY_ADDRESS(ID, CUSTOMER_ID, CITY, STREET, POSTAL_CODE, FAVORITE, REGISTER_DATE_TIME)
values (default, (select ID from CUSTOMER where EMAIL = 'bruce@test.com'), 'Frankfurt', 'Street 1', '62759', true,
        current_timestamp());
insert into DELIVERY_ADDRESS(ID, CUSTOMER_ID, CITY, STREET, POSTAL_CODE, FAVORITE, REGISTER_DATE_TIME)
values (default, (select ID from CUSTOMER where EMAIL = 'bruce@test.com'), 'Berlin', 'Street A', '72759', true,
        current_timestamp());

-- OAUTH_CLIENT_DETAILS
-- OAuth2 Client Credential Grant foo:bar
insert into oauth_client_details (client_id,client_secret,scope,authorized_grant_types,web_server_redirect_uri,
                                  authorities,access_token_validity,refresh_token_validity,additional_information,autoapprove)
values ('foo','$2a$11$1MIc/9cXCICP0LaPgYaJZ.MFNo/LYTclJtNVizLFQ.lQ.B9t0V2YK','read,write',
        'password,authorization_code,refresh_token,client_credentials,implicit',null,null,36000,36000,null,true);