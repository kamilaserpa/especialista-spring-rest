package com.kamila.food.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "cozinhas")
@Getter
@Setter
public class CozinhaModel extends RepresentationModel<CozinhaModel> {

    @ApiModelProperty(example = "1")
//    @JsonView(RestauranteView.Resumo.class)
    private Long id;

    @ApiModelProperty(example = "Brasileira")
//    @JsonView(RestauranteView.Resumo.class)
    private String nome;

}
