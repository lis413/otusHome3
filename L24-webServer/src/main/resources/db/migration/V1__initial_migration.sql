create sequence hibernate_sequence start with 1 increment by 1;
create sequence client_sequence start with 1 increment by 1;
create sequence address_sequence start with 1 increment by 1;
create sequence phone_sequence start with 1 increment by 1;

create table address
(
    id int8 generated by default as identity,
     street varchar(255),
      primary key (id)
);
create table client
(
    id int8 not null,
     name varchar(255),
      address_id int8,
       primary key (id)
);
create table phone
(
    id int8 not null,
     number varchar(255),
      client_id int8,
       primary key (id)
);


alter table if exists client add constraint FKb137u2cl2ec0otae32lk5pcl2 foreign key (address_id) references address;
alter table if exists phone add constraint FK3o48ec26lujl3kf01hwqplhn2 foreign key (client_id) references client;


insert into address (street) values ('Matrosova');
insert into address (street) values ('Bogoslovskaya');
insert into client (address_id, name, id) values ((select id from address where street = 'Matrosova'), 'Ilya', 1);
insert into client (address_id, name, id) values ((select id from address where street = 'Bogoslovskaya'), 2, 'Kolya', 2);

insert into phone (client_id, number, id) values (1, 123, 1);
insert into phone (client_id, number, id) values (1, 1234, 2);
insert into phone (client_id, number, id) values (2, 234, 3);
insert into phone (client_id, number, id) values (2, 2345, 4);