create table tb_restaurante_usuario_responsavel
(
   id_restaurante bigint not null,
   id_usuario bigint not null,
   
   primary key (id_restaurante, id_usuario),
   
   constraint fk_restaurante_usuario_restaurante foreign key (id_restaurante) references tb_restaurante (id_restaurante),
   constraint fk_restaurante_usuario_usuario foreign key (id_usuario) references tb_usuario (id_usuario)
)
engine= InnoDB default charset= utf8;