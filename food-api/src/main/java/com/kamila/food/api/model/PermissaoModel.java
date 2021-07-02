package com.kamila.food.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PermissaoModel {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "CONSLTAR_COZINHAS")
    private String nome;

    @ApiModelProperty(example = "Permite consultar cozinhas")
    private String descricao;

}
