package com.kamila.food.api.v2.assembler;

import com.kamila.food.api.v2.model.input.CozinhaInputV2;
import com.kamila.food.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CozinhaInputDisassemblerV2 {

    @Autowired
    private ModelMapper modelMapper;
    
    public Cozinha toDomainObject(CozinhaInputV2 cozinhaInputV2) {
        return modelMapper.map(cozinhaInputV2, Cozinha.class);
    }
    
    public void copyToDomainObject(CozinhaInputV2 cozinhaIcozinhaInputV2put, Cozinha cozinha) {
        modelMapper.map(cozinhaIcozinhaInputV2put, cozinha);
    }
    
}