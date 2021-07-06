package com.kamila.food.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

//@JsonFilter("pedidoFilter")
@Relation(collectionRelation = "pedidos")
@Getter
@Setter
public class PedidoResumoModel extends RepresentationModel<PedidoResumoModel> {

	@ApiModelProperty(example = "335c0344-f4c5-4824-ad8a-944c9e6bdf43")
	private String codigo;

	@ApiModelProperty(example = "298.90")
	private BigDecimal subtotal;

	@ApiModelProperty(example = "10.00")
	private BigDecimal taxaFrete;

	@ApiModelProperty(example = "308.90")
	private BigDecimal valorTotal;

	@ApiModelProperty(example = "2021-07-01T13:07:32Z")
	private OffsetDateTime dataCriacao;

	@ApiModelProperty(example = "CRIADO")
	private String status;
	
	private RestauranteResumoModel restaurante;

	private UsuarioModel cliente;

}
