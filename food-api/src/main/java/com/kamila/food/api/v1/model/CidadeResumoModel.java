package com.kamila.food.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "cidades")
@Getter
@Setter
public class CidadeResumoModel  extends RepresentationModel<CidadeResumoModel> {

	@ApiModelProperty(example = "1")
	private Long id;

	@ApiModelProperty(example = "Uberlândia")
	private String nome;

	@ApiModelProperty(example = "Minas Gerais")
	private String estado;
	
	// String estado; Faz com que ModelMapper call toString() do Objeto Estado e retorne para o consumidor "estado": "Estado(id=1, nome=Minas Gerais)"
	// Para
	
}
