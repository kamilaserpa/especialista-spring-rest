create table tb_estado (
	id_estado bigint not null auto_increment,
	nm_estado varchar(80) not null,
	primary key(id_estado)
) engine=InnoDB default charset=utf8;

insert into tb_estado (nm_estado) select distinct nome_estado from tb_cidade;

alter table tb_cidade add column id_estado bigint not null;

update tb_cidade c set c.id_estado = (select e.id_estado from tb_estado e where e.nm_estado = c.nome_estado);

alter table tb_cidade add constraint fk_cidade_estado
foreign key (id_estado) references tb_estado (id_estado);

alter table tb_cidade drop column nome_estado;