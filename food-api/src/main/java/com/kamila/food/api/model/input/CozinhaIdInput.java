package com.kamila.food.api.model.input;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CozinhaIdInput {

	@ApiModelProperty(example = "1", required = true)
	@NotNull
	private Long id;

}

