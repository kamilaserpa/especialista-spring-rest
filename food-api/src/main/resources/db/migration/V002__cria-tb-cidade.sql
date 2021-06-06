create table tb_cidade (
	id_cidade bigint not null auto_increment,
	nm_cidade varchar(80) not null,
	nome_estado varchar(80) not null,
	primary key(id_cidade)
) engine=InnoDB default charset=utf8;