insert into pilgerdb.account (username, password) values ('Admin1', 'secret1');
insert into pilgerdb.account (username, password) values ('Admin2', 'secret2');
insert into pilgerdb.account (username, password) values ('Admin3', 'secret3');
insert into pilgerdb.account (username, password) values ('Admin4', 'secret4');

insert into pilgerdb.elytronrole (id, rolename) values (1,  'System');
insert into pilgerdb.elytronrole (id, rolename) values (2,  'PowerUser');
insert into pilgerdb.elytronrole (id, rolename) values (3,  'Administrator');
insert into pilgerdb.elytronrole (id, rolename) values (4,  'Users');

insert into pilgerdb.elytronuser (username, elytron_role_id) values ('Admin1', 1);
insert into pilgerdb.elytronuser (username, elytron_role_id) values ('Admin2', 2);

insert into pilgerdb.person (firstname, lastname) values ('Hans-Georg', 'Glöckler1');
insert into pilgerdb.person (firstname, lastname) values ('Alfred', 'Lautwein');
insert into pilgerdb.person (firstname, lastname) values ('Viktor', 'Kessler');
insert into pilgerdb.person (firstname, lastname) values ('Markus', 'Kächele');

insert into pilgerdb.title (listprio, title) values (0, 'Prof.');
insert into pilgerdb.title (listprio, title) values (1, 'PD');
insert into pilgerdb.title (listprio, title) values (2, 'Dr.');
insert into pilgerdb.title (listprio, title) values (3, 'Graf von');
insert into pilgerdb.title (listprio, title) values (4, 'Freiherr von');

insert into pilgerdb.settings(defaultLanguage, defaultTheme, defaultWindowWidth) values ('english', 'Default', '1200px');


