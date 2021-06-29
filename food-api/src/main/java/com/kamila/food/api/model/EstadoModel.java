package com.kamila.food.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadoModel {

	@ApiModelProperty(example = "1")
	private Long id;

	@ApiModelProperty(example = "Ceará")
	private String nome;
	
}
