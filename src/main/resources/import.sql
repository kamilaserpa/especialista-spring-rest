insert into tb_cozinha (id_cozinha, nm_cozinha) values (1, 'Tailandesa');
insert into tb_cozinha (id_cozinha, nm_cozinha) values (2, 'Indiana');
insert into tb_cozinha (id_cozinha, nm_cozinha) values (3, 'Brasileira');

insert into tb_restaurante (id_restaurante, nm_restaurante, taxa_frete, id_cozinha) values (1, 'Thai Gourmet', 10, 1);
insert into tb_restaurante (id_restaurante, nm_restaurante, taxa_frete, id_cozinha) values (2, 'Thai Delivery', 9.50, 1);
insert into tb_restaurante (id_restaurante, nm_restaurante, taxa_frete, id_cozinha) values (3, 'Tuk Tuk Comida Indiana', 15, 2);    
insert into tb_restaurante (id_restaurante, nm_restaurante, taxa_frete, id_cozinha) values (4, 'Boa Mesa', 0, 3);    


insert into tb_estado (id_estado, nm_estado) values (1, 'Minas Gerais');
insert into tb_estado (id_estado, nm_estado) values (2, 'São Paulo');
insert into tb_estado (id_estado, nm_estado) values (3, 'Ceará');

insert into tb_cidade (id_cidade, nm_cidade, id_estado) values (1, 'Uberlândia', 1);
insert into tb_cidade (id_cidade, nm_cidade, id_estado) values (2, 'Belo Horizonte', 1);
insert into tb_cidade (id_cidade, nm_cidade, id_estado) values (3, 'São Paulo', 2);
insert into tb_cidade (id_cidade, nm_cidade, id_estado) values (4, 'Campinas', 2);
insert into tb_cidade (id_cidade, nm_cidade, id_estado) values (5, 'Fortaleza', 3);

insert into tb_forma_pagamento (id_forma_pagamento, descricao) values (1, 'Cartão de crédito');
insert into tb_forma_pagamento (id_forma_pagamento, descricao) values (2, 'Cartão de débito');
insert into tb_forma_pagamento (id_forma_pagamento, descricao) values (3, 'Dinheiro');

insert into tb_permissao (id_permissao, nm_permissao, descricao) values (1, 'CONSULTAR_COZINHAS', 'Permite consultar cozinhas');
insert into tb_permissao (id_permissao, nm_permissao, descricao) values (2, 'EDITAR_COZINHAS', 'Permite editar cozinhas');

insert into restaurante_forma_pagamento (id_restaurante, id_forma_pagamento) values (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3);