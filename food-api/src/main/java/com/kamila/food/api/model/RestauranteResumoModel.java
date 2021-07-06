package com.kamila.food.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "restaurantes")
@Getter
@Setter
public class RestauranteResumoModel  extends RepresentationModel<RestauranteResumoModel> {

	@ApiModelProperty("1")
	private Long id;

	@ApiModelProperty("Thai Gourmet")
	private String nome;
	
}
