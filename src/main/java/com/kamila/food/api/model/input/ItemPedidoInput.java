package com.kamila.food.api.model.input;

import javax.validation.constraints.PositiveOrZero;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoInput {

	@NotNull
	private Long produtoId;

	@NotNull
	@PositiveOrZero
	private Integer quantidade;

	private String observacao;

}
