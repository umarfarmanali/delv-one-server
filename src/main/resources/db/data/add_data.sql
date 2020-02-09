insert into customer_type (type) values (1);
insert into customer_type (type) values (2);
insert into customer_type (type) values (3);

insert into policy (id, name) values (1, 'ADD_USER');
insert into policy (id, name) values (2, 'UPD_USER');
insert into policy (id, name) values (3, 'SEE_USER');

insert into role (id, name) values (1, 'ADMIN');
insert into role (id, name) values (2, 'ARM');

insert into role_policy (role_id, policy_id) values (1, 1);
insert into role_policy (role_id, policy_id) values (1, 2);
insert into role_policy (role_id, policy_id) values (1, 3);
insert into role_policy (role_id, policy_id) values (2, 1);
insert into role_policy (role_id, policy_id) values (2, 2);
insert into role_policy (role_id, policy_id) values (2, 3);