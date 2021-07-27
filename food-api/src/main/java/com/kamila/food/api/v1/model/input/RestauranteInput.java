package com.kamila.food.api.v1.model.input;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.kamila.food.core.validation.TaxaFrete;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestauranteInput {

	@ApiModelProperty(example = "Thai Gourmet", required = true)
	@NotBlank // Verifica se nullo, vazio ou contém apenas espaços
	private String nome;

	@ApiModelProperty(example = "10.00", required = true)
	@NotNull
	@TaxaFrete
	private BigDecimal taxaFrete;

	@Valid
	@NotNull
	private CozinhaIdInput cozinha;

	@Valid
	@NotNull
	private EnderecoInput endereco;
	
}
