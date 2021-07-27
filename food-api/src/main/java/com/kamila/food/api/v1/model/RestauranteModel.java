package com.kamila.food.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;

@Relation(collectionRelation = "restaurantes")
@Setter
@Getter
public class RestauranteModel extends RepresentationModel<RestauranteModel> {

	@ApiModelProperty(example = "1")
//	@JsonView({ RestauranteView.Resumo.class, RestauranteView.ApenasNome.class })
	private Long id;

	@ApiModelProperty(example = "Thai Gourmet")
//	@JsonView({ RestauranteView.Resumo.class, RestauranteView.ApenasNome.class })
	private String nome;

	@ApiModelProperty(example = "10.00")
//	@JsonView(RestauranteView.Resumo.class)
	private BigDecimal precoFrete; // Alteração de "taxaFrete" para "precoFrete" apenas para teste de config do ModelMapper
	
//	@JsonView(RestauranteView.Resumo.class)
	private CozinhaModel cozinha;
	
	private Boolean ativo;
	
	private EnderecoModel endereco;
	
	private Boolean aberto;
	
}
