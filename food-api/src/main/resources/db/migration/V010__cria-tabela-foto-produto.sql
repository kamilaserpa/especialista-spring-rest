create table tb_foto_produto (
    id_produto bigint not null,
    nome_arquivo varchar(150) not null,
    descricao varchar(150),
    content_type varchar(80) not null,
    tamanho int not null,

    primary key (id_produto),
    constraint fk_foto_produto_produto foreign key (id_produto)
    references tb_produto (id_produto)
) engine=InnoDB default charset=utf8