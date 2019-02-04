-- CUSTOMER
insert into CUSTOMER(ID, EMAIL, CUSTOMER_SECRET, FAMILY_NAME, GIVEN_NAME, REGISTER_DATE_TIME)
values (default, 'bruce@test.com', '$2a$11$sTdm0YUj5VlXae9dHD7L0OM5kghMrDDe5KRqvckX9N2ss2NkpdqIW', 'bruce', 'wayne', current_timestamp());

-- OAUTH_CLIENT_DETAILS foo:bar
insert into oauth_client_details (client_id,client_secret,scope,authorized_grant_types,web_server_redirect_uri,
                                  authorities,access_token_validity,refresh_token_validity,additional_information,autoapprove)
values ('foo','$2a$11$1MIc/9cXCICP0LaPgYaJZ.MFNo/LYTclJtNVizLFQ.lQ.B9t0V2YK','read,write',
        'password,authorization_code,refresh_token,client_credentials,implicit',null,null,36000,36000,null,true);