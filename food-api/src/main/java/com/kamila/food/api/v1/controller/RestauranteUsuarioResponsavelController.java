package com.kamila.food.api.v1.controller;

import com.kamila.food.api.v1.FoodLinks;
import com.kamila.food.api.v1.assembler.UsuarioModelAssembler;
import com.kamila.food.api.v1.model.UsuarioModel;
import com.kamila.food.api.v1.openapi.controller.RestauranteUsuarioResponsavelControllerOpenApi;
import com.kamila.food.domain.model.Restaurante;
import com.kamila.food.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/restaurantes/{idRestaurante}/responsaveis",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteUsuarioResponsavelController implements
        RestauranteUsuarioResponsavelControllerOpenApi {

    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @Autowired
    private UsuarioModelAssembler usuarioModelAssembler;

    @Autowired
    private FoodLinks foodLinks;

    @Override
    @GetMapping
    public CollectionModel<UsuarioModel> listar(@PathVariable Long idRestaurante) {
        Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(idRestaurante);

        CollectionModel<UsuarioModel> usuarioModels = usuarioModelAssembler.toCollectionModel(restaurante.getResponsaveis())
                .removeLinks()
                .add(foodLinks.linkToRestauranteResponsaveis(idRestaurante))
                .add(foodLinks.linkToRestauranteUsuarioResponsavelAssociacao(idRestaurante, "associar"));

        usuarioModels.getContent().forEach(usuarioModel -> {
            usuarioModel.add(foodLinks.linkToRestauranteUsuarioResponsavelDesassociacao(
                    idRestaurante, usuarioModel.getId(),
                    "desassociar"));
        });
        return usuarioModels;
    }

    @Override
    @DeleteMapping("/{idUsuario}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> desassociarResponsavel(@PathVariable Long idRestaurante, @PathVariable Long idUsuario) {
        cadastroRestauranteService.desassociarResponsavel(idRestaurante, idUsuario);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PutMapping("/{idUsuario}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associarResponsavel(@PathVariable Long idRestaurante, @PathVariable Long idUsuario) {
        cadastroRestauranteService.associarResponsavel(idRestaurante, idUsuario);
        return ResponseEntity.noContent().build();
    }

}
