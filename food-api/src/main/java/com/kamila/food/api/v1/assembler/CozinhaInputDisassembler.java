package com.kamila.food.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kamila.food.api.v1.model.input.CozinhaInput;
import com.kamila.food.domain.model.Cozinha;

@Component
public class CozinhaInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;
    
    public Cozinha toDomainObject(CozinhaInput cozinhaInput) {
        return modelMapper.map(cozinhaInput, Cozinha.class);
    }
    
    public void copyToDomainObject(CozinhaInput cozinhaInput, Cozinha cozinha) {
        modelMapper.map(cozinhaInput, cozinha);
    }
    
}