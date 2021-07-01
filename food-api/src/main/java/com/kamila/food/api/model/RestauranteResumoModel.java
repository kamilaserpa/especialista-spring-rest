package com.kamila.food.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteResumoModel {

	@ApiModelProperty("1")
	private Long id;

	@ApiModelProperty("Thai Gourmet")
	private String nome;
	
}
