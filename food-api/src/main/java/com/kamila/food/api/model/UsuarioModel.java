package com.kamila.food.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UsuarioModel {

	@ApiModelProperty(example = "1")
	private Long id;

	@ApiModelProperty(example = "João da Silva")
	private String nome;

	@ApiModelProperty(example = "joao.ger@food.com.br")
	private String email;
	
}
