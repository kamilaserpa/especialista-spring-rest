package com.kamila.food.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kamila.food.api.model.EnderecoModel;
import com.kamila.food.api.model.RestauranteModel;
import com.kamila.food.domain.model.Endereco;
import com.kamila.food.domain.model.Restaurante;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();
		
		modelMapper.createTypeMap(Restaurante.class, RestauranteModel.class)
				.addMapping(Restaurante::getTaxaFrete, RestauranteModel::setPrecoFrete) // Utilizando referências de métodos
				.addMapping(restSrc -> restSrc.getEndereco().getCidade().getEstado().getNome(),
						(restDest, val) -> restDest.getEndereco().getCidade().setEstado((String) val));

		var enderecoToEnderecoModelTypeMap = modelMapper.createTypeMap(
				Endereco.class, EnderecoModel.class);

		enderecoToEnderecoModelTypeMap.<String>addMapping(
				enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(),
				(enderecoModelDest, value) -> enderecoModelDest.getCidade().setEstado(value));
		
		return modelMapper;
	}
	
}
