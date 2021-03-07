package com.kamila.food.core.jackson;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.kamila.food.api.model.mixin.CidadeMixin;
import com.kamila.food.api.model.mixin.CozinhaMixin;
import com.kamila.food.api.model.mixin.EnderecoMixin;
import com.kamila.food.api.model.mixin.RestauranteMixin;
import com.kamila.food.domain.model.Cidade;
import com.kamila.food.domain.model.Cozinha;
import com.kamila.food.domain.model.Endereco;
import com.kamila.food.domain.model.Restaurante;

@Component
public class JacksonMixinModule extends SimpleModule {

	private static final long serialVersionUID = 1L;

	public JacksonMixinModule() {
		setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
		setMixInAnnotation(Cidade.class, CidadeMixin.class);
		setMixInAnnotation(Cozinha.class, CozinhaMixin.class);
		setMixInAnnotation(Endereco.class, EnderecoMixin.class);
	}

}
