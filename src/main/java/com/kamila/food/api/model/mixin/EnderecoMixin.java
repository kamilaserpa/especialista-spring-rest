package com.kamila.food.api.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kamila.food.domain.model.Cidade;

public class EnderecoMixin {

	@JsonIgnore
	private Cidade cidade;

}
