insert into pilgerdb.account (uuid, username, password) values (uuid_v4(), 'Admin1', 'secret1');
insert into pilgerdb.account (uuid, username, password) values (uuid_v4(), 'Admin2', 'secret2');
insert into pilgerdb.account (uuid, username, password) values (uuid_v4(), 'Admin3', 'secret3');
insert into pilgerdb.account (uuid, username, password) values (uuid_v4(), 'Admin4', 'secret4');

insert into pilgerdb.elytronrole (id, uuid, rolename) values (1,  uuid_v4(), 'System');
insert into pilgerdb.elytronrole (id, uuid, rolename) values (2,  uuid_v4(), 'PowerUser');
insert into pilgerdb.elytronrole (id, uuid, rolename) values (3,  uuid_v4(), 'Administrator');
insert into pilgerdb.elytronrole (id, uuid, rolename) values (4,  uuid_v4(), 'Users');

insert into pilgerdb.elytronuser (uuid, username, elytron_role_id, comment, defaultLanguage, defaultTheme ) values (uuid_v4(), 'N-U-T', 3, 'New-User-Template', 'english', 'Default');
insert into pilgerdb.elytronuser (uuid, username, elytron_role_id) values (uuid_v4(), 'Admin1', 1);
insert into pilgerdb.elytronuser (uuid, username, elytron_role_id) values (uuid_v4(), 'Admin2', 2);

insert into pilgerdb.person (uuid, firstname, lastname) values (uuid_v4(), 'Hans-Georg', 'Glöckler1');
insert into pilgerdb.person (uuid, firstname, lastname) values (uuid_v4(), 'Alfred', 'Lautwein');
insert into pilgerdb.person (uuid, firstname, lastname) values (uuid_v4(), 'Viktor', 'Kessler');
insert into pilgerdb.person (uuid, firstname, lastname) values (uuid_v4(), 'Markus', 'Kächele');

insert into pilgerdb.address (uuid, postbox, street, zip, city, person_id) values (uuid_v4(), '111', 'Strasse 01', '11111', 'Ulm 01', 1);
insert into pilgerdb.address (uuid, postbox, street, zip, city, person_id) values (uuid_v4(), '222', 'Strasse 02', '22222', 'Ulm 02', 1);
insert into pilgerdb.address (uuid, postbox, street, zip, city, person_id) values (uuid_v4(), '333', 'Strasse 03', '33333', 'Ulm 03', 2);
insert into pilgerdb.address (uuid, postbox, street, zip, city, person_id) values (uuid_v4(), '444', 'Strasse 04', '44444', 'Ulm 04', 2);

insert into pilgerdb.communicationtype (uuid, listPrio, communicationType) values (uuid_v4(), 1, 'Telefon');
insert into pilgerdb.communicationtype (uuid, listPrio, communicationType) values (uuid_v4(), 2, 'Mobil');
insert into pilgerdb.communicationtype (uuid, listPrio, communicationType) values (uuid_v4(), 3, 'Fax');
insert into pilgerdb.communicationtype (uuid, listPrio, communicationType) values (uuid_v4(), 4, 'Email');

insert into pilgerdb.kind (uuid, listPrio, kind) values (uuid_v4(), 1, 'Privat');
insert into pilgerdb.kind (uuid, listPrio, kind) values (uuid_v4(), 2, 'Geschäftlich');

insert into pilgerdb.communication (uuid, communication, COMMUNICATIONTYPE_ID, KIND_ID, person_id) values (uuid_v4(), 'h.g.gloeckler@gmx.de', 4, 1, 1);
insert into pilgerdb.communication (uuid, communication, COMMUNICATIONTYPE_ID, KIND_ID, person_id) values (uuid_v4(), 'viktor@gmx.de', 4, 1, 2);

insert into pilgerdb.title (uuid, listprio, title) values (uuid_v4(), 0, 'Prof.');
insert into pilgerdb.title (uuid, listprio, title) values (uuid_v4(), 1, 'PD');
insert into pilgerdb.title (uuid, listprio, title) values (uuid_v4(), 2, 'Dr.');
insert into pilgerdb.title (uuid, listprio, title) values (uuid_v4(), 3, 'Graf von');
insert into pilgerdb.title (uuid, listprio, title) values (uuid_v4(), 4, 'Freiherr von');

insert into pilgerdb.settings(uuid, defaultLanguage, defaultTheme, defaultWindowWidth) values (uuid_v4(), 'english', 'Default', '1200px');

