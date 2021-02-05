create table tb_forma_pagamento
(
   id_forma_pagamento bigint not null auto_increment primary key,
   ds_forma_pagamento varchar (60) not null
)
engine= InnoDB default charset= utf8;

create table tb_grupo
(
   id_grupo bigint not null auto_increment primary key,
   nm_grupo varchar (60) not null
)
engine= InnoDB default charset= utf8;

create table tb_grupo_permissao
(
   id_grupo bigint not null,
   id_permissao bigint not null
)
engine= InnoDB default charset= utf8;

create table tb_permissao
(
   id_permissao bigint not null auto_increment primary key,
   ds_permissao varchar (60) not null,
   nm_permissao varchar (100) not null
)
engine= InnoDB default charset= utf8;

create table tb_produto
(
   id_produto bigint not null auto_increment primary key,
   id_restaurante bigint not null,
   nm_produto varchar (80) not null,
   ds_produto text not null,
   preco decimal
   (
      10,
      2
   )
   not null,
   ativo tinyint (1) not null
)
engine= InnoDB default charset= utf8;

create table tb_restaurante
(
   id_restaurante bigint not null auto_increment primary key,
   id_cozinha bigint not null,
   nm_restaurante varchar (80) not null,
   taxa_frete decimal
   (
      10,
      2
   )
   not null,
   data_atualizacao datetime not null,
   data_cadastro datetime not null,
   endereco_id_cidade bigint,
   endereco_cep varchar (9),
   endereco_logradouro varchar (100),
   endereco_numero varchar (20),
   endereco_complemento varchar (60),
   endereco_bairro varchar (60)
)
engine= InnoDB default charset= utf8;

create table tb_restaurante_forma_pagamento
(
   id_restaurante bigint not null,
   id_forma_pagamento bigint not null,
   primary key
   (
      id_restaurante,
      id_forma_pagamento
   )
)
engine= InnoDB default charset= utf8;

create table tb_usuario
(
   id_usuario bigint not null auto_increment primary key,
   nm_usuario varchar (80) not null,
   email varchar (255) not null,
   senha varchar (255) not null,
   data_cadastro datetime not null
)
engine= InnoDB default charset= utf8;

create table tb_usuario_grupo
(
   id_usuario bigint not null,
   id_grupo bigint not null,
   primary key
   (
      id_usuario,
      id_grupo
   )
)
engine= InnoDB default charset= utf8;

alter table tb_grupo_permissao add constraint fk_grupo_permissao_permissao foreign key (id_permissao) references tb_permissao (id_permissao);
alter table tb_grupo_permissao add constraint fk_grupo_permissao_grupo foreign key (id_grupo) references tb_grupo (id_grupo);
alter table tb_produto add constraint fk_produto_restaurante foreign key (id_restaurante) references tb_restaurante (id_restaurante);
alter table tb_restaurante add constraint fk_restaurante_cozinha foreign key (id_cozinha) references tb_cozinha (id_cozinha);
alter table tb_restaurante add constraint fk_restaurante_cidade foreign key (endereco_id_cidade) references tb_cidade (id_cidade);
alter table tb_restaurante_forma_pagamento add constraint fk_rest_forma_pagto_forma_pagto foreign key (id_forma_pagamento) references tb_forma_pagamento (id_forma_pagamento);
alter table tb_restaurante_forma_pagamento add constraint fk_rest_forma_pagto_restaurante foreign key (id_restaurante) references tb_restaurante (id_restaurante);
alter table tb_usuario_grupo add constraint fk_usuario_grupo_grupo foreign key (id_grupo) references tb_grupo (id_grupo);
alter table tb_usuario_grupo add constraint fk_usuario_grupo_usuario foreign key (id_usuario) references tb_usuario (id_usuario);