package com.kamila.food.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

//@ApiModel(value = "Cidade", description = "Representa uma cidade")
@Getter
@Setter
public class CidadeModel {

	@ApiModelProperty(example = "1")
	private Long id;

	@ApiModelProperty(example = "Fortaleza")
	private String nome;
	
	private EstadoModel estado;
	
}
