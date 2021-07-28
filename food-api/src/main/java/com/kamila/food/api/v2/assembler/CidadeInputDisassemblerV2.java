package com.kamila.food.api.v2.assembler;

import com.kamila.food.api.v2.model.input.CidadeInputV2;
import com.kamila.food.domain.model.Cidade;
import com.kamila.food.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;

@Component
public class CidadeInputDisassemblerV2 {

    @Autowired
    private ModelMapper modelMapper;

    public Cidade toDomainObject(@Valid CidadeInputV2 cidadeInputV2) {
        return modelMapper.map(cidadeInputV2, Cidade.class);
    }

    public void copyToDomainObject(@Valid CidadeInputV2 cidadeInput, Cidade cidade) {
        cidade.setEstado(new Estado());
        modelMapper.map(cidadeInput, cidade);
    }

}
