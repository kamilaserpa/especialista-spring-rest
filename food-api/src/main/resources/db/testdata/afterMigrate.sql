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
delete from tb_restaurante_usuario_responsavel;
delete from tb_pedido;
delete from tb_item_pedido;

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
alter table tb_pedido auto_increment = 1;
alter table tb_item_pedido auto_increment = 1;


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

insert into tb_restaurante (id_restaurante, nm_restaurante, taxa_frete, id_cozinha, data_cadastro, data_atualizacao, ativo, aberto, endereco_id_cidade, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro)
	values (1, 'Thai Gourmet', 10, 1, utc_timestamp, utc_timestamp, true, true, 1, '38400-999', 'Rua João Pinheiro', '1000', 'Centro');
insert into tb_restaurante (id_restaurante, nm_restaurante, taxa_frete, id_cozinha, data_cadastro, data_atualizacao, ativo, aberto) values (2, 'Thai Delivery', 9.50, 1, utc_timestamp, utc_timestamp, true, true);
insert into tb_restaurante (id_restaurante, nm_restaurante, taxa_frete, id_cozinha, data_cadastro, data_atualizacao, ativo, aberto) values (3, 'Tuk Tuk Comida Indiana', 15, 2, utc_timestamp, utc_timestamp, true, true);    
insert into tb_restaurante (id_restaurante, nm_restaurante, taxa_frete, id_cozinha, data_cadastro, data_atualizacao, ativo, aberto) values (4, 'Java Steakhouse', 12, 3, utc_timestamp, utc_timestamp, true, true);
insert into tb_restaurante (id_restaurante, nm_restaurante, taxa_frete, id_cozinha, data_cadastro, data_atualizacao, ativo, aberto) values (5, 'Lanchonete do Tio Sam', 11, 4, utc_timestamp, utc_timestamp, true, true);
insert into tb_restaurante (id_restaurante, nm_restaurante, taxa_frete, id_cozinha, data_cadastro, data_atualizacao, ativo, aberto) values (6, 'Bar da Maria', 6, 4, utc_timestamp, utc_timestamp, true, true);

insert into tb_forma_pagamento (id_forma_pagamento, ds_forma_pagamento, data_atualizacao) values (1, 'Cartão de crédito', utc_timestamp);
insert into tb_forma_pagamento (id_forma_pagamento, ds_forma_pagamento, data_atualizacao) values (2, 'Cartão de débito', utc_timestamp);
insert into tb_forma_pagamento (id_forma_pagamento, ds_forma_pagamento, data_atualizacao) values (3, 'Dinheiro', utc_timestamp);

insert into tb_permissao (id_permissao, nm_permissao, ds_permissao) values (1, 'CONSULTAR_COZINHAS', 'Permite consultar cozinhas');
insert into tb_permissao (id_permissao, nm_permissao, ds_permissao) values (2, 'EDITAR_COZINHAS', 'Permite editar cozinhas');
insert into tb_permissao (id_permissao, nm_permissao, ds_permissao) values (3, 'CONSULTAR_FORMAS_PAGAMENTO', 'Permite consultar formas de pagamento');
insert into tb_permissao (id_permissao, nm_permissao, ds_permissao) values (4, 'EDITAR_FORMAS_PAGAMENTO', 'Permite criar ou editar formas de pagamento');
insert into tb_permissao (id_permissao, nm_permissao, ds_permissao) values (5, 'CONSULTAR_CIDADES', 'Permite consultar cidades');
insert into tb_permissao (id_permissao, nm_permissao, ds_permissao) values (6, 'EDITAR_CIDADES', 'Permite criar ou editar cidades');
insert into tb_permissao (id_permissao, nm_permissao, ds_permissao) values (7, 'CONSULTAR_ESTADOS', 'Permite consultar estados');
insert into tb_permissao (id_permissao, nm_permissao, ds_permissao) values (8, 'EDITAR_ESTADOS', 'Permite criar ou editar estados');
insert into tb_permissao (id_permissao, nm_permissao, ds_permissao) values (9, 'CONSULTAR_USUARIOS', 'Permite consultar usuários');
insert into tb_permissao (id_permissao, nm_permissao, ds_permissao) values (10, 'EDITAR_USUARIOS', 'Permite criar ou editar usuários');
insert into tb_permissao (id_permissao, nm_permissao, ds_permissao) values (11, 'CONSULTAR_RESTAURANTES', 'Permite consultar restaurantes');
insert into tb_permissao (id_permissao, nm_permissao, ds_permissao) values (12, 'EDITAR_RESTAURANTES', 'Permite criar, editar ou gerenciar restaurantes');
insert into tb_permissao (id_permissao, nm_permissao, ds_permissao) values (13, 'CONSULTAR_PRODUTOS', 'Permite consultar produtos');
insert into tb_permissao (id_permissao, nm_permissao, ds_permissao) values (14, 'EDITAR_PRODUTOS', 'Permite criar ou editar produtos');
insert into tb_permissao (id_permissao, nm_permissao, ds_permissao) values (15, 'CONSULTAR_PEDIDOS', 'Permite consultar pedidos');
insert into tb_permissao (id_permissao, nm_permissao, ds_permissao) values (16, 'GERENCIAR_PEDIDOS', 'Permite gerenciar pedidos');
insert into tb_permissao (id_permissao, nm_permissao, ds_permissao) values (17, 'GERAR_RELATORIOS', 'Permite gerar relatórios');

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
insert into tb_produto (nm_produto, ds_produto, preco, ativo, id_restaurante) values ('Maminha completa', '300g de carne. Acompanha baião', 48, 0, 6);

insert into tb_grupo (id_grupo, nm_grupo) values (1, 'Gerente'), (2, 'Vendedor'), (3, 'Secretária'), (4, 'Cadastrador');

# Adiciona todas as permissoes no grupo do gerente
insert into tb_grupo_permissao (id_grupo, id_permissao)
select 1, id_permissao from tb_permissao;

# Adiciona permissoes no grupo do vendedor
insert into tb_grupo_permissao (id_grupo, id_permissao)
select 2, id_permissao from tb_permissao where nm_permissao like 'CONSULTAR_%';

insert into tb_grupo_permissao (id_grupo, id_permissao) values (2, 14);

# Adiciona permissoes no grupo do auxiliar
insert into tb_grupo_permissao (id_grupo, id_permissao)
select 3, id_permissao from tb_permissao where nm_permissao like 'CONSULTAR_%';

# Adiciona permissoes no grupo cadastrador
insert into tb_grupo_permissao (id_grupo, id_permissao)
select 4, id_permissao from tb_permissao where nm_permissao like '%_RESTAURANTES' or nm_permissao like '%_PRODUTOS';

insert into tb_usuario (id_usuario, nm_usuario, email, senha, data_cadastro) values
(1, 'João da Silva', 'joao.ger@kfood.com', '$2a$12$iDLsh5J.zz2RskgJc4RH9uWORIGaSUczp6LWK6n3NrBCCPJgCvOOK', utc_timestamp),
(2, 'Maria Joaquina', 'maria.vnd@kfood.com', '$2a$12$iDLsh5J.zz2RskgJc4RH9uWORIGaSUczp6LWK6n3NrBCCPJgCvOOK', utc_timestamp),
(3, 'José Souza', 'jose.aux@kfood.com', '$2a$12$iDLsh5J.zz2RskgJc4RH9uWORIGaSUczp6LWK6n3NrBCCPJgCvOOK', utc_timestamp),
(4, 'Sebastião Martins', 'sebastiao.cad@kfood.com', '$2a$12$iDLsh5J.zz2RskgJc4RH9uWORIGaSUczp6LWK6n3NrBCCPJgCvOOK', utc_timestamp),
(5, 'Manoel Lima', 'manoel.loja@kfood.com', '$2a$12$iDLsh5J.zz2RskgJc4RH9uWORIGaSUczp6LWK6n3NrBCCPJgCvOOK', utc_timestamp),
(6, 'Débora Mendonça', 'ist.kamila.serpa+teste1@gmail.com', '$2a$12$iDLsh5J.zz2RskgJc4RH9uWORIGaSUczp6LWK6n3NrBCCPJgCvOOK', utc_timestamp),
(7, 'Carlos Vinícios', 'ist.kamila.serpa+teste2@gmail.com', '$2a$12$iDLsh5J.zz2RskgJc4RH9uWORIGaSUczp6LWK6n3NrBCCPJgCvOOK', utc_timestamp);

insert into tb_usuario_grupo(id_usuario, id_grupo) values (1, 1), (1, 2), (2, 3);

insert into tb_restaurante_usuario_responsavel (id_restaurante, id_usuario) values (1, 5), (3, 5);

insert into tb_pedido (id_pedido, codigo, id_restaurante, id_usuario_cliente, id_forma_pagamento, endereco_id_cidade, endereco_cep, 
    endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro,
    status, data_criacao, subtotal, taxa_frete, valor_total)
values (1, '335c0344-f4c5-4824-ad8a-944c9e6bdf43', 1, 1, 1, 1, '38400-000', 'Rua Floriano Peixoto', '500', 'Apto 801',
'Brasil', 'CRIADO', utc_timestamp, 298.90, 10, 308.90);

insert into tb_item_pedido (id_item_pedido, id_pedido, id_produto, quantidade, preco_unitario, preco_total, observacao)
values (1, 1, 1, 1, 78.9, 78.9, null);

insert into tb_item_pedido (id_item_pedido, id_pedido, id_produto, quantidade, preco_unitario, preco_total, observacao)
values (2, 1, 2, 2, 110, 220, 'Menos picante, por favor');


insert into tb_pedido (id_pedido, codigo, id_restaurante, id_usuario_cliente, id_forma_pagamento,
        endereco_id_cidade, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento,
        endereco_bairro, status, data_criacao, subtotal, taxa_frete, valor_total)
values (2, 'ee2f9cc9-a569-4582-9f01-7e53c4f601b8', 4, 6, 2, 1, '38400-111', 'Rua Acre', '300', 'Casa 2', 'Centro',
'CRIADO', utc_timestamp, 79, 0, 79);

insert into tb_item_pedido (id_item_pedido, id_pedido, id_produto, quantidade, preco_unitario, preco_total, observacao)
values (3, 2, 6, 1, 79, 79, 'Ao ponto');

insert into tb_pedido (id_pedido, codigo, id_restaurante, id_usuario_cliente, id_forma_pagamento,
                    endereco_id_cidade, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento,
                    endereco_bairro, status, data_criacao, data_confirmacao, data_entrega, subtotal, taxa_frete, valor_total)
values (3, 'b5741512-8fbc-47fa-9ac1-b530354fc0ff', 1, 7, 1, 1, '38400-222', 'Rua Natal', '200', null, 'Brasil',
        'CRIADO', '2020-10-30 21:10:00', '2020-10-30 21:10:45', '2020-10-30 21:55:44', 110, 10, 120);

insert into tb_item_pedido (id_item_pedido, id_pedido, id_produto, quantidade, preco_unitario, preco_total, observacao)
values (4, 3, 2, 1, 110, 110, null);


insert into tb_pedido (id_pedido, codigo, id_restaurante, id_usuario_cliente, id_forma_pagamento,
                    endereco_id_cidade, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento,
                    endereco_bairro, status, data_criacao, data_confirmacao, data_entrega, subtotal, taxa_frete, valor_total)
values (4, '5c621c9a-ba61-4454-8631-8aabefe58dc2', 1, 7, 1, 1, '38400-800', 'Rua Fortaleza', '900', 'Apto 504', 'Centro',
        'CRIADO', '2019-11-02 20:34:04', '2019-11-02 20:35:10', '2019-11-02 21:10:32', 174.4, 5, 179.4);

insert into tb_item_pedido (id_item_pedido, id_pedido, id_produto, quantidade, preco_unitario, preco_total, observacao)
values (5, 4, 3, 2, 87.2, 174.4, null);


insert into tb_pedido (id_pedido, codigo, id_restaurante, id_usuario_cliente, id_forma_pagamento,
                    endereco_id_cidade, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento,
                    endereco_bairro, status, data_criacao, data_confirmacao, data_entrega, subtotal, taxa_frete, valor_total)
values (5, '8d774bcf-b238-42f3-aef1-5fb388754d63', 1, 3, 2, 1, '38400-200', 'Rua 10', '930', 'Casa 20', 'Martins',
        'ENTREGUE', '2019-11-03 02:00:30', '2019-11-03 02:01:21', '2019-11-03 02:20:10', 87.2, 10, 97.2);

insert into tb_item_pedido (id_item_pedido, id_pedido, id_produto, quantidade, preco_unitario, preco_total, observacao)
values (6, 5, 3, 1, 87.2, 87.2, null);

