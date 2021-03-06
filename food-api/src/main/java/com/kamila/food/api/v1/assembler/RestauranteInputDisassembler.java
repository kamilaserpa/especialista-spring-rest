package com.kamila.food.api.v1.assembler;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kamila.food.api.v1.model.input.RestauranteInput;
import com.kamila.food.domain.model.Cidade;
import com.kamila.food.domain.model.Cozinha;
import com.kamila.food.domain.model.Restaurante;

/**
 * Converte um RestauranteInput em um Restaurante
 */
@Component
public class RestauranteInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Restaurante toDomainObject(@Valid RestauranteInput restauranteInput) {
		return modelMapper.map(restauranteInput, Restaurante.class);
	}
	
	/**
	 * Método para inserir dados de um RestauranteInput em um Restaurante
	 * Como RestauranteInput só possui as propriedades que podem ser atualizadas
	 * não é necessário ignorar determinadas propriedades na atualização de um Restaurante
	 * @param restauranteInput000000
	 * @param restaurante
	 */
	public void copyToDomainObject(RestauranteInput restauranteInput, Restaurante restaurante) {
		// Para evitar org.hibernate.HibernateException: identifier of an instance of
		// com.kamila.food.domain.model.COzinha was altered from 1 to 2
		// Evitando erro de atribuição de ID em uma cozinha com ID preexistente, removendo ID preexistente
		restaurante.setCozinha(new Cozinha());
		
		if (restaurante.getEndereco() != null) {
			restaurante.getEndereco().setCidade(new Cidade());
		}
		
		modelMapper.map(restauranteInput, restaurante);
	}
	
}
