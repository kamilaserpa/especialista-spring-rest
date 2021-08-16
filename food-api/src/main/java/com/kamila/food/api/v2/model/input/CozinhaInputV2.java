package com.kamila.food.api.v2.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CozinhaInputV2 {

	@ApiModelProperty(example = "Brasileira", required = true)
	@NotBlank
	private String nome;
	
}
