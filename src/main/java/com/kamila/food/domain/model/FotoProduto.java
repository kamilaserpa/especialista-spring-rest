package com.kamila.food.domain.model;

import com.kamila.food.core.validation.Groups;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_foto_produto")
public class FotoProduto {

    @EqualsAndHashCode.Include
    @Id
    @Column(name = "id_produto")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY) // Quando buscar uma foto prosuto entendesse que não deve retornar dados do produto
    @MapsId // Foto produto é mapeada a partir do produto Id
    private Produto produto;

    private String nomeArquivo;

    private String descricao;

    private String contentType;

    private Long tamanho;

}
