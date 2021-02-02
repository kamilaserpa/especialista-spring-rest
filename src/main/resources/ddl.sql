create table tb_cidade (id_cidade bigint not null auto_increment, nm_cidade varchar(255) not null, id_estado bigint not null, primary key (id_cidade)) engine=InnoDB
create table tb_cozinha (id_cozinha bigint not null auto_increment, nm_cozinha varchar(50) not null, primary key (id_cozinha)) engine=InnoDB
create table tb_estado (id_estado bigint not null auto_increment, nm_estado varchar(255) not null, primary key (id_estado)) engine=InnoDB
create table tb_forma_pagamento (id_forma_pagamento bigint not null auto_increment, ds_forma_pagamento varchar(255) not null, primary key (id_forma_pagamento)) engine=InnoDB
create table tb_permissao (id_permissao bigint not null auto_increment, ds_permissao varchar(255) not null, nm_permissao varchar(255) not null, primary key (id_permissao)) engine=InnoDB
create table tb_produto (id_produto bigint not null auto_increment, ativo bit not null, ds_produto varchar(255) not null, nm_produto varchar(255) not null, preco decimal(19,2) not null, id_restaurante bigint not null, primary key (id_produto)) engine=InnoDB
create table tb_restaurante (id_restaurante bigint not null auto_increment, data_atualizacao datetime not null, data_cadastro datetime not null, endereco_bairro varchar(255), endereco_cep varchar(255), endereco_complemento varchar(255), endereco_logradouro varchar(255), endereco_numero varchar(255), nm_restaurante varchar(100) not null, taxa_frete decimal(19,2) not null, id_cozinha bigint not null, endereco_cidade_id bigint, primary key (id_restaurante)) engine=InnoDB
create table tb_restaurante_forma_pagamento (id_restaurante bigint not null, id_forma_pagamento bigint not null) engine=InnoDB
alter table tb_cidade add constraint FK12tfv6wlsvaml7nsmq106hvib foreign key (id_estado) references tb_estado (id_estado)
alter table tb_produto add constraint FKesnslp1am9ticebt0dw7feuul foreign key (id_restaurante) references tb_restaurante (id_restaurante)
alter table tb_restaurante add constraint FKfg14jluyhafigqvd9o3ac1fv6 foreign key (id_cozinha) references tb_cozinha (id_cozinha)
alter table tb_restaurante add constraint FKp5f7gcswjq064qro5pfjoj49r foreign key (endereco_cidade_id) references tb_cidade (id_cidade)
alter table tb_restaurante_forma_pagamento add constraint FKlm4jr5rina9tf2tfivhr76ucy foreign key (id_forma_pagamento) references tb_forma_pagamento (id_forma_pagamento)
alter table tb_restaurante_forma_pagamento add constraint FKr6sv0ukvwx9phf2fe6lm5s3ug foreign key (id_restaurante) references tb_restaurante (id_restaurante)
create table tb_grupo_permissao (id_grupo bigint not null, id_permissao bigint not null) engine=InnoDB
create table tb_cidade (id_cidade bigint not null auto_increment, nm_cidade varchar(255) not null, id_estado bigint not null, primary key (id_cidade)) engine=InnoDB
create table tb_cozinha (id_cozinha bigint not null auto_increment, nm_cozinha varchar(50) not null, primary key (id_cozinha)) engine=InnoDB
create table tb_estado (id_estado bigint not null auto_increment, nm_estado varchar(255) not null, primary key (id_estado)) engine=InnoDB
create table tb_forma_pagamento (id_forma_pagamento bigint not null auto_increment, ds_forma_pagamento varchar(255) not null, primary key (id_forma_pagamento)) engine=InnoDB
create table tb_grupo (id_grupo bigint not null auto_increment, nm_grupo varchar(255) not null, primary key (id_grupo)) engine=InnoDB
create table tb_permissao (id_permissao bigint not null auto_increment, ds_permissao varchar(255) not null, nm_permissao varchar(255) not null, primary key (id_permissao)) engine=InnoDB
create table tb_produto (id_produto bigint not null auto_increment, ativo bit not null, ds_produto varchar(255) not null, nm_produto varchar(255) not null, preco decimal(19,2) not null, id_restaurante bigint not null, primary key (id_produto)) engine=InnoDB
create table tb_restaurante (id_restaurante bigint not null auto_increment, data_atualizacao datetime not null, data_cadastro datetime not null, endereco_bairro varchar(255), endereco_cep varchar(255), endereco_complemento varchar(255), endereco_logradouro varchar(255), endereco_numero varchar(255), nm_restaurante varchar(100) not null, taxa_frete decimal(19,2) not null, id_cozinha bigint not null, id_endereco_cidade bigint, primary key (id_restaurante)) engine=InnoDB
create table tb_restaurante_forma_pagamento (id_restaurante bigint not null, id_forma_pagamento bigint not null) engine=InnoDB
create table tb_usuario (id_usuario bigint not null auto_increment, data_cadastro datetime not null, email varchar(255) not null, nm_usuario varchar(255) not null, senha varchar(255) not null, primary key (id_usuario)) engine=InnoDB
create table tb_usuario_grupo (id_usuario bigint not null, id_grupo bigint not null) engine=InnoDB
alter table tb_grupo_permissao add constraint FKsoosrfcawjrsce7iu7e4xdwed foreign key (id_permissao) references tb_permissao (id_permissao)
alter table tb_grupo_permissao add constraint FKoyoe9rbubu4u0dhh4x6i59n48 foreign key (id_grupo) references tb_grupo (id_grupo)
alter table tb_cidade add constraint FK12tfv6wlsvaml7nsmq106hvib foreign key (id_estado) references tb_estado (id_estado)
alter table tb_produto add constraint FKesnslp1am9ticebt0dw7feuul foreign key (id_restaurante) references tb_restaurante (id_restaurante)
alter table tb_restaurante add constraint FKfg14jluyhafigqvd9o3ac1fv6 foreign key (id_cozinha) references tb_cozinha (id_cozinha)
alter table tb_restaurante add constraint FK176d2wl99dr6c8bdv6kbqom8r foreign key (id_endereco_cidade) references tb_cidade (id_cidade)
alter table tb_restaurante_forma_pagamento add constraint FKlm4jr5rina9tf2tfivhr76ucy foreign key (id_forma_pagamento) references tb_forma_pagamento (id_forma_pagamento)
alter table tb_restaurante_forma_pagamento add constraint FKr6sv0ukvwx9phf2fe6lm5s3ug foreign key (id_restaurante) references tb_restaurante (id_restaurante)
alter table tb_usuario_grupo add constraint FKqxy9455xabfm1m4e88a5kqa1l foreign key (id_grupo) references tb_grupo (id_grupo)
alter table tb_usuario_grupo add constraint FK33trkg7gm3nmi37hpqv4lofek foreign key (id_usuario) references tb_usuario (id_usuario)
