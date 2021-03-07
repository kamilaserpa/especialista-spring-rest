package com.kamila.food.api.model.mixin;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kamila.food.domain.model.Restaurante;

public class CozinhaMixin {

	@JsonIgnore
	private List<Restaurante> restaurantes;
	
}
