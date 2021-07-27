package com.kamila.food.api.v1.openapi.model;

import com.kamila.food.api.v1.model.CozinhaModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Classe apenas para fins de documentação
 */
@ApiModel("RestauranteBasicoModel")
@Getter
@Setter
public class RestauranteBasicoModelOpenApi {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Thai Gourmet")
    private String nome;

    @ApiModelProperty(example = "10.00")
    private BigDecimal precoFrete;

    private CozinhaModel cozinha;

}
