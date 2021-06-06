alter table tb_restaurante add ativo tinyint(1) not null;
update tb_restaurante set ativo = true;