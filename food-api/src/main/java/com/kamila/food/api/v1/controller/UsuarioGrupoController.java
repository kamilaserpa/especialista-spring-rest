package com.kamila.food.api.v1.controller;

import com.kamila.food.api.v1.FoodLinks;
import com.kamila.food.api.v1.assembler.GrupoModelAssembler;
import com.kamila.food.api.v1.model.GrupoModel;
import com.kamila.food.api.v1.openapi.controller.UsuarioGrupoControllerOpenApi;
import com.kamila.food.core.security.FoodSecurity;
import com.kamila.food.domain.model.Usuario;
import com.kamila.food.domain.service.CadastroUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/usuarios/{idUsuario}/grupos",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioGrupoController implements UsuarioGrupoControllerOpenApi {

    @Autowired
    private CadastroUsuarioService cadastroUsuarioService;

    @Autowired
    private GrupoModelAssembler grupoModelAssembler;

    @Autowired
    private FoodLinks foodLinks;

    @Autowired
    private FoodSecurity foodSecurity;

    @Override
    @GetMapping
    public CollectionModel<GrupoModel> listar(@PathVariable Long idUsuario) {
        Usuario usuario = cadastroUsuarioService.buscarOuFalhar(idUsuario);

        CollectionModel<GrupoModel> grupoModels = grupoModelAssembler.toCollectionModel(usuario.getGrupos())
                .removeLinks();

        if (foodSecurity.podeEditarUsuariosGruposPermissoes()) {
            grupoModels.add(foodLinks.linkToUsuarioGrupoAssociacao(idUsuario, "associar"));

            grupoModels.getContent().forEach(grupoModel -> {
                grupoModel.add(foodLinks.linkToUsuarioGrupoDesassociacao(idUsuario,
                        grupoModel.getId(), "desassociar"));
            });
        }

        return grupoModels;
    }

    @Override
    @DeleteMapping("/{idGrupo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> desassociarGrupo(@PathVariable Long idUsuario, @PathVariable Long idGrupo) {
        cadastroUsuarioService.desassociarGrupo(idUsuario, idGrupo);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PutMapping("/{idGrupo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associarGrupo(@PathVariable Long idUsuario, @PathVariable Long idGrupo) {
        cadastroUsuarioService.associarGrupo(idUsuario, idGrupo);
        return ResponseEntity.noContent().build();
    }

}
