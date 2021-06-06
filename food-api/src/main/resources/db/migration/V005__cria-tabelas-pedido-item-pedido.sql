create table tb_pedido (
  id_pedido bigint not null auto_increment,
  subtotal decimal(10,2) not null,
  taxa_frete decimal(10,2) not null,
  valor_total decimal(10,2) not null,

  id_restaurante bigint not null,
  id_usuario_cliente bigint not null,
  id_forma_pagamento bigint not null,
  
  endereco_id_cidade bigint(20) not null,
  endereco_cep varchar(9) not null,
  endereco_logradouro varchar(100) not null,
  endereco_numero varchar(20) not null,
  endereco_complemento varchar(60) null,
  endereco_bairro varchar(60) not null,
  
  status varchar(10) not null,
  data_criacao datetime not null,
  data_confirmacao datetime null,
  data_cancelamento datetime null,
  data_entrega datetime null,

  primary key (id_pedido),

  constraint fk_pedido_restaurante foreign key (id_restaurante) references tb_restaurante (id_restaurante),
  constraint fk_pedido_usuario_cliente foreign key (id_usuario_cliente) references tb_usuario (id_usuario),
  constraint fk_pedido_forma_pagamento foreign key (id_forma_pagamento) references tb_forma_pagamento (id_forma_pagamento)
) engine=InnoDB default charset=utf8;

create table tb_item_pedido (
  id_item_pedido bigint not null auto_increment,
  quantidade smallint(6) not null,
  preco_unitario decimal(10,2) not null,
  preco_total decimal(10,2) not null,
  observacao varchar(255) null,
  id_pedido bigint not null,
  id_produto bigint not null,
  
  primary key (id_item_pedido),
  unique key uk_item_pedido_produto (id_pedido, id_produto),

  constraint fk_item_pedido_pedido foreign key (id_pedido) references tb_pedido (id_pedido),
  constraint fk_item_pedido_produto foreign key (id_produto) references tb_produto (id_produto)
) engine=InnoDB default charset=utf8;