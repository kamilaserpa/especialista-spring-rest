package com.kamila.food.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "grupos")
@Getter
@Setter
public class GrupoModel extends RepresentationModel<GrupoModel> {

	@ApiModelProperty(example = "1")
	private Long id;

	@ApiModelProperty(example = "Gerente")
	private String nome;
	
}
