package com.kamila.food.api.v1.controller;

import com.kamila.food.api.v1.assembler.PermissaoModelAssembler;
import com.kamila.food.api.v1.model.PermissaoModel;
import com.kamila.food.api.v1.openapi.controller.PermissaoControllerOpenApi;
import com.kamila.food.domain.repository.PermissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class PermissaoController implements PermissaoControllerOpenApi {

    @Autowired
    private PermissaoRepository permissaoRepository;

    @Autowired
    private PermissaoModelAssembler permissaoModelAssembler;

    @Override
    @GetMapping
    public CollectionModel<PermissaoModel> listar() {
        return permissaoModelAssembler.toCollectionModel(permissaoRepository.findAll());
    }

}
