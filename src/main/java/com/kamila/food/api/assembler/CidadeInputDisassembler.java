package com.kamila.food.api.assembler;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kamila.food.api.model.input.CidadeInput;
import com.kamila.food.domain.model.Cidade;
import com.kamila.food.domain.model.Estado;

@Component
public class CidadeInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Cidade toDomainModel(@Valid CidadeInput cidadeInput) {
		return modelMapper.map(cidadeInput, Cidade.class);
	}

	public void copyToDomainObject(@Valid CidadeInput cidadeInput, Cidade cidade) {
		// cidade.setEstado(new Estado()); Para evitar org.hibernate.HibernateException: identifier of an instance of 
        // com.algaworks.algafood.domain.model.Estado was altered from 1 to 2
		//  Evitando erro de atribuição de ID em uma cidade com ID preexistente, removendo ID preexistente
		cidade.setEstado(new Estado());
		modelMapper.map(cidadeInput, cidade);
	}
	
}
