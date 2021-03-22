package com.kamila.food.api.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestauranteModel {

	private Long id;

	private String nome;
	
	private BigDecimal precoFrete;
	
	private CozinhaModel cozinha;
	
	private Boolean ativo;
	
	private EnderecoModel endereco;
	
}
