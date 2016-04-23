create table if not exists user_table (
	id int not null auto_increment,
	username char(32),
	password char(32),
	usertype char(8),
	gender char(8),
	email char(32),
	carid char(32),
	primary key(id)
);
commit;