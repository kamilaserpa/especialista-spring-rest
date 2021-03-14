-- Desabilita checagem de foreign keys para permitir a deleção dos dados de todas as tabelas a seguir
set foreign_key_checks = 0;

-- Deleta dados do banco para manter o estado inicial do banco
delete from tb_cidade;
delete from tb_cozinha;
delete from tb_estado;
delete from tb_forma_pagamento;
delete from tb_grupo;
delete from tb_grupo_permissao;
delete from tb_permissao;
delete from tb_produto;
delete from tb_restaurante;
delete from tb_restaurante_forma_pagamento;
delete from tb_usuario;
delete from tb_usuario_grupo;

set foreign_key_checks = 1;

alter table tb_cidade auto_increment = 1;
alter table tb_cozinha auto_increment = 1;
alter table tb_estado auto_increment = 1;
alter table tb_forma_pagamento auto_increment = 1;
alter table tb_grupo auto_increment = 1;
alter table tb_permissao auto_increment = 1;
alter table tb_produto auto_increment = 1;
alter table tb_restaurante auto_increment = 1;
alter table tb_usuario auto_increment = 1;


insert into tb_cozinha (id_cozinha, nm_cozinha) values (1, 'Tailandesa');
insert into tb_cozinha (id_cozinha, nm_cozinha) values (2, 'Indiana');
insert into tb_cozinha (id_cozinha, nm_cozinha) values (3, 'Argentina');
insert into tb_cozinha (id_cozinha, nm_cozinha) values (4, 'Brasileira');

insert into tb_estado (id_estado, nm_estado) values (1, 'Minas Gerais');
insert into tb_estado (id_estado, nm_estado) values (2, 'São Paulo');
insert into tb_estado (id_estado, nm_estado) values (3, 'Ceará');

insert into tb_cidade (id_cidade, nm_cidade, id_estado) values (1, 'Uberlândia', 1);
insert into tb_cidade (id_cidade, nm_cidade, id_estado) values (2, 'Belo Horizonte', 1);
insert into tb_cidade (id_cidade, nm_cidade, id_estado) values (3, 'São Paulo', 2);
insert into tb_cidade (id_cidade, nm_cidade, id_estado) values (4, 'Campinas', 2);
insert into tb_cidade (id_cidade, nm_cidade, id_estado) values (5, 'Fortaleza', 3);

insert into tb_restaurante (id_restaurante, nm_restaurante, taxa_frete, id_cozinha, data_cadastro, data_atualizacao, ativo, endereco_id_cidade, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro)
	values (1, 'Thai Gourmet', 10, 1, utc_timestamp, utc_timestamp, true, 1, '38400-999', 'Rua João Pinheiro', '1000', 'Centro');
insert into tb_restaurante (id_restaurante, nm_restaurante, taxa_frete, id_cozinha, data_cadastro, data_atualizacao, ativo) values (2, 'Thai Delivery', 9.50, 1, utc_timestamp, utc_timestamp, true);
insert into tb_restaurante (id_restaurante, nm_restaurante, taxa_frete, id_cozinha, data_cadastro, data_atualizacao, ativo) values (3, 'Tuk Tuk Comida Indiana', 15, 2, utc_timestamp, utc_timestamp, true);    
insert into tb_restaurante (id_restaurante, nm_restaurante, taxa_frete, id_cozinha, data_cadastro, data_atualizacao, ativo) values (4, 'Java Steakhouse', 12, 3, utc_timestamp, utc_timestamp, true);
insert into tb_restaurante (id_restaurante, nm_restaurante, taxa_frete, id_cozinha, data_cadastro, data_atualizacao, ativo) values (5, 'Lanchonete do Tio Sam', 11, 4, utc_timestamp, utc_timestamp, true);
insert into tb_restaurante (id_restaurante, nm_restaurante, taxa_frete, id_cozinha, data_cadastro, data_atualizacao, ativo) values (6, 'Bar da Maria', 6, 4, utc_timestamp, utc_timestamp, true);

insert into tb_forma_pagamento (id_forma_pagamento, ds_forma_pagamento) values (1, 'Cartão de crédito');
insert into tb_forma_pagamento (id_forma_pagamento, ds_forma_pagamento) values (2, 'Cartão de débito');
insert into tb_forma_pagamento (id_forma_pagamento, ds_forma_pagamento) values (3, 'Dinheiro');

insert into tb_permissao (id_permissao, nm_permissao, ds_permissao) values (1, 'CONSULTAR_COZINHAS', 'Permite consultar cozinhas');
insert into tb_permissao (id_permissao, nm_permissao, ds_permissao) values (2, 'EDITAR_COZINHAS', 'Permite editar cozinhas');

insert into tb_restaurante_forma_pagamento (id_restaurante, id_forma_pagamento) values (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3), (4, 1), (4, 2), (5, 1), (5, 2), (6, 3);

insert into tb_produto (nm_produto, ds_produto, preco, ativo, id_restaurante) values ('Porco com molho agridoce', 'Deliciosa carne suína ao molho especial', 78.90, 1, 1);
insert into tb_produto (nm_produto, ds_produto, preco, ativo, id_restaurante) values ('Camarão tailandês', '16 camarões grandes ao molho picante', 110, 1, 1);

insert into tb_produto (nm_produto, ds_produto, preco, ativo, id_restaurante) values ('Salada picante com carne grelhada', 'Salada de folhas com cortes finos de carne bovina grelhada e nosso molho especial de pimenta vermelha', 87.20, 1, 2);

insert into tb_produto (nm_produto, ds_produto, preco, ativo, id_restaurante) values ('Garlic Naan', 'Pão tradicional indiano com cobertura de alho', 21, 1, 3);
insert into tb_produto (nm_produto, ds_produto, preco, ativo, id_restaurante) values ('Murg Curry', 'Cubos de frango preparados com molho curry e especiarias', 43, 1, 3);

insert into tb_produto (nm_produto, ds_produto, preco, ativo, id_restaurante) values ('Bife Ancho', 'Corte macio e suculento, com dois dedos de espessura, retirado da parte dianteira do contrafilé', 79, 1, 4);
insert into tb_produto (nm_produto, ds_produto, preco, ativo, id_restaurante) values ('T-Bone', 'Corte muito saboroso, com um osso em formato de T, sendo de um lado o contrafilé e do outro o filé mignon', 89, 1, 4);

insert into tb_produto (nm_produto, ds_produto, preco, ativo, id_restaurante) values ('Sanduíche X-Tudo', 'Sandubão com muito queijo, hamburger bovino, bacon, ovo, salada e maionese', 19, 1, 5);

insert into tb_produto (nm_produto, ds_produto, preco, ativo, id_restaurante) values ('Espetinho de Cupim', 'Acompanha farinha, mandioca e vinagrete', 8, 1, 6);