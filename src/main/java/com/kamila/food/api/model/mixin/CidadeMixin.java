package com.kamila.food.api.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kamila.food.domain.model.Estado;

public class CidadeMixin {
	
	@JsonIgnoreProperties(value = "nome", allowGetters = true) // Ao desserializar (json to java object) um Restaurante ignora o nome de Cozinha
	private Estado estado;

}
