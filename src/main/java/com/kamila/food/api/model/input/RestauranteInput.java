package com.kamila.food.api.model.input;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.kamila.food.core.validation.TaxaFrete;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestauranteInput {

	@NotBlank // Verifica se nullo, vazio ou contém apenas espaços
	private String nome;

	@NotNull
	@TaxaFrete
	private BigDecimal taxaFrete;

	@Valid
	@NotNull
	private CozinhaIdInput cozinha;

}
