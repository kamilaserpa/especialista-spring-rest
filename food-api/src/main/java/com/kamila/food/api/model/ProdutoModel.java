package com.kamila.food.api.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoModel {

	private Long id;

	private String nome;

	private String descricao;

	private BigDecimal preco;

	private Boolean ativo;

}
