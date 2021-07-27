package com.kamila.food.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kamila.food.api.v1.model.EnderecoModel;
import com.kamila.food.api.v1.model.RestauranteModel;
import com.kamila.food.api.v1.model.input.ItemPedidoInput;
import com.kamila.food.domain.model.Endereco;
import com.kamila.food.domain.model.ItemPedido;
import com.kamila.food.domain.model.Restaurante;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();
		
		// Restaurante
		modelMapper.createTypeMap(Restaurante.class, RestauranteModel.class)
				.addMapping(Restaurante::getTaxaFrete, RestauranteModel::setPrecoFrete) // Utilizando referências de métodos
				.addMapping(restSrc -> restSrc.getEndereco().getCidade().getEstado().getNome(),
						(restDest, val) -> restDest.getEndereco().getCidade().setEstado((String) val));

		// Endereço
		var enderecoToEnderecoModelTypeMap = modelMapper.createTypeMap(
				Endereco.class, EnderecoModel.class);

		enderecoToEnderecoModelTypeMap.<String>addMapping(
				enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(),
				(enderecoModelDest, value) -> enderecoModelDest.getCidade().setEstado(value));
		
		// ItemPedido
		modelMapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class)
	    	.addMappings(mapper -> mapper.skip(ItemPedido::setId)); // Ao fazer o mapeameto ignore o setId
		
		return modelMapper;
	}
	
}
