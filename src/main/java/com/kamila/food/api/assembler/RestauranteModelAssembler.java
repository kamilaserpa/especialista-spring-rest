package com.kamila.food.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kamila.food.api.model.RestauranteModel;
import com.kamila.food.domain.model.Restaurante;

/**
 * Converte Restaurante em RestauranteModel
 */
@Component
public class RestauranteModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;

	public RestauranteModel toModel(Restaurante restaurante) {
		return modelMapper.map(restaurante,  RestauranteModel.class);
	}

	public List<RestauranteModel> toCollectionModel(List<Restaurante> restaurantes) {
		return restaurantes.stream()
				.map(restaurante -> toModel(restaurante))
				.collect(Collectors.toList());
	}

}
