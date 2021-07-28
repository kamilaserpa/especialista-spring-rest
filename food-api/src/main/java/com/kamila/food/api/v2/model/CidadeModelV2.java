package com.kamila.food.api.v2.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "cidades")
@Getter
@Setter
public class CidadeModelV2 extends RepresentationModel<CidadeModelV2> {

	@ApiModelProperty(example = "1")
	private Long idCidade;

	@ApiModelProperty(example = "Fortaleza")
	private String nomeCidade;
	
	private Long idEstado;

	@ApiModelProperty(example = "Ceará")
	private String nomeEstado;
	
}
