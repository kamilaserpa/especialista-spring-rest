package com.kamila.food.domain.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import org.springframework.data.domain.Persistable;

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

    @MapsId // Foto produto é mapeada a partir do produto Id
    @OneToOne(optional = false, fetch = FetchType.LAZY) // Quando buscar uma foto prosuto entendesse que não deve retornar dados do produto
    @JoinColumn(name = "id_produto", referencedColumnName = "id_produto", insertable = false, updatable = false)
    private Produto produto;
    
    private String nomeArquivo;

    private String descricao;

    private String contentType;

    private Long tamanho;

    public Long getRestauranteId() {
        if (getProduto() != null) {
            return getProduto().getRestaurante().getId();
        }
        return null;
    }

}
