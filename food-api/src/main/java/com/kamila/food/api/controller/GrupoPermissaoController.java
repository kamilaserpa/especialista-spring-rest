package com.kamila.food.api.controller;

import com.kamila.food.api.assembler.PermissaoModelAssembler;
import com.kamila.food.api.model.PermissaoModel;
import com.kamila.food.api.openapi.controller.GrupoPermissaoControllerOpenApi;
import com.kamila.food.domain.model.Grupo;
import com.kamila.food.domain.service.CadastroGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/grupos/{idGrupo}/permissoes",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoPermissaoController implements GrupoPermissaoControllerOpenApi {

    @Autowired
    private CadastroGrupoService cadastroGrupo;

    @Autowired
    private PermissaoModelAssembler permissaoModelAssembler;

    @Override
    @GetMapping
    public List<PermissaoModel> listar(@PathVariable Long idGrupo) {
        Grupo grupo = cadastroGrupo.buscarOuFalhar(idGrupo);

        return permissaoModelAssembler.toCollectionModel(grupo.getPermissoes());
    }

    @Override
    @DeleteMapping("/{idPermissao}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long idGrupo, @PathVariable Long idPermissao) {
        cadastroGrupo.desassociarPermissao(idGrupo, idPermissao);
    }

    @Override
    @PutMapping("/{idPermissao}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long idGrupo, @PathVariable Long idPermissao) {
        cadastroGrupo.associarPermissao(idGrupo, idPermissao);
    }

}
