create database if not exists guuber;

use guuber;

create table if not exists user_table (
	id int not null auto_increment,
	username char(64),
	password char(64),
	usertype char(32),
	email char(64),
	gender char(32),
	carid char(64),
	primary key(id)
);
commit;