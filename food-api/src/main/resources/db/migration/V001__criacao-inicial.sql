create table tb_cozinha (
	id_cozinha bigint not null auto_increment,
	nm_cozinha varchar(60) not null,
	primary key(id_cozinha)
) engine=InnoDB default charset=utf8;