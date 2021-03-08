package com.kamila.food.api.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestauranteModel {

	private Long id;

	private String nome;
	
	// Observe alteração de nome do atributo e conversão pelo modelMapper continua funcionando sem mais nenhum ajuste
	private BigDecimal frete;
	private CozinhaModel cozinha;
	private Long cozinhaId;
	private String cozinhaNome;
	
}
