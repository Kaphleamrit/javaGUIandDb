create database javadb;

use javadb;

create table authTable(
customer_id int not null auto_increment,
username varchar(100) not null,
pass varchar(100) not null,
position varchar(100) not null,
primary key(customer_id)
);

create table myTable (
customer_id int not null auto_increment,
namee varchar(100) not null,
phone varchar(100) not null,
details varchar(100) not null,
primary key(customer_id)
);

insert into authTable(customer_id,username,pass,position) values (1,'admin','admin','manager');
insert into authTable(username,pass,position) values ('nonadmin','nonadmin','customer');

insert into myTable(customer_id, namee, phone, details) values (1,'Ram chhetri','9076545434','good person');
insert into myTable(namee, phone, details) values ('shyam pokhrel','9054652445','with good humour');
insert into myTable(namee, phone, details) values ('hari prasad','90554562445','strong fellow');