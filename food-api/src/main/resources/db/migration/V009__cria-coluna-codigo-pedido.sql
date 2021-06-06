alter table tb_pedido add codigo varchar(36) not null after id_pedido;
update tb_pedido set codigo = uuid();
alter table tb_pedido add constraint uk_pedido_codigo unique(codigo);