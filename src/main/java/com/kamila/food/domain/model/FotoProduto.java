package com.kamila.food.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

import javax.persistence.*;

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
    @JoinColumn(name = "id_produto", referencedColumnName = "id_produto", insertable = false, updatable = false)
    @MapsId // Foto produto é mapeada a partir do produto Id
    private Produto produto;

    private String nomeArquivo;

    private String descricao;

    private String contentType;

    private Long tamanho;

    public void setId() {
        System.out.println("setting Id");
        this.id = produto.getId();
    }

}
