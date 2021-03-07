package com.kamila.food.api.assembler;

import javax.validation.Valid;

import org.springframework.stereotype.Component;

import com.kamila.food.api.model.input.RestauranteInput;
import com.kamila.food.domain.model.Cozinha;
import com.kamila.food.domain.model.Restaurante;

/**
 * Converte um RestauranteInput em um Restaurante
 */
@Component
public class RestauranteInputDisassembler {

	public Restaurante toDomainModel(@Valid RestauranteInput restauranteInput) {
		Restaurante restaurante = new Restaurante();
		restaurante.setNome(restauranteInput.getNome());
		restaurante.setTaxaFrete(restauranteInput.getTaxaFrete());
		
		Cozinha cozinha = new Cozinha();
		cozinha.setId(restauranteInput.getCozinha().getId());
		
		restaurante.setCozinha(cozinha);
		
		return restaurante;
	}
	
}
