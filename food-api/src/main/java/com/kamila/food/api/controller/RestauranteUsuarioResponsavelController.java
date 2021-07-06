package com.kamila.food.api.controller;

import com.kamila.food.api.assembler.UsuarioModelAssembler;
import com.kamila.food.api.model.UsuarioModel;
import com.kamila.food.api.openapi.controller.RestauranteUsuarioResponsavelControllerOpenApi;
import com.kamila.food.domain.model.Restaurante;
import com.kamila.food.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/restaurantes/{idRestaurante}/responsaveis",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteUsuarioResponsavelController implements
        RestauranteUsuarioResponsavelControllerOpenApi {


    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @Autowired
    private UsuarioModelAssembler usuarioModelAssembler;

    @Override
    @GetMapping
    public CollectionModel<UsuarioModel> listar(@PathVariable Long idRestaurante) {
        Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(idRestaurante);

        return usuarioModelAssembler.toCollectionModel(restaurante.getResponsaveis())
                .removeLinks()
                .add(linkTo(methodOn(RestauranteUsuarioResponsavelController.class)
                        .listar(idRestaurante)).withSelfRel());
    }

    @Override
    @DeleteMapping("/{idUsuario}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociarResponsavel(@PathVariable Long idRestaurante, @PathVariable Long idUsuario) {
        cadastroRestauranteService.desassociarResponsavel(idRestaurante, idUsuario);
    }

    @Override
    @PutMapping("/{idUsuario}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associarResponsavel(@PathVariable Long idRestaurante, @PathVariable Long idUsuario) {
        cadastroRestauranteService.associarResponsavel(idRestaurante, idUsuario);
    }

}
