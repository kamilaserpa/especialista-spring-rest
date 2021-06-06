alter table tb_restaurante add aberto tinyint(1) not null;
update tb_restaurante set aberto = false;