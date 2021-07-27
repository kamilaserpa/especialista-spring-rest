package com.kamila.food.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "enderecos")
@Getter
@Setter
public class EnderecoModel  extends RepresentationModel<EnderecoModel> {

	@ApiModelProperty(example = "38400-000")
	private String cep;

	@ApiModelProperty(example = "Rua Floriano Peixoto")
	private String logradouro;

	@ApiModelProperty(example = "1500")
	private String numero;

	@ApiModelProperty(example = "Apto 901")
	private String complemento;

	@ApiModelProperty(example = "Centro")
	private String bairro;

	private CidadeResumoModel cidade;

}
