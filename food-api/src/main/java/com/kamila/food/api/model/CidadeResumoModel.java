package com.kamila.food.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeResumoModel {

	@ApiModelProperty(example = "1")
	private Long id;

	@ApiModelProperty(example = "Uberlândia")
	private String nome;

	@ApiModelProperty(example = "Minas Gerais")
	private String estado;
	
	// String estado; Faz com que ModelMapper call toString() do Objeto Estado e retorne para o consumidor "estado": "Estado(id=1, nome=Minas Gerais)"
	// Para
	
}
