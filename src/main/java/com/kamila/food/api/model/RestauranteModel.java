package com.kamila.food.api.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestauranteModel {

	private Long id;

	private String nome;
	
	private BigDecimal precoFrete; // Alteração de "taxaFrete" para "precoFrete" apenas para teste de config do ModelMapper
	
	private CozinhaModel cozinha;
	
	private Boolean ativo;
	
	private EnderecoModel endereco;
	
}
