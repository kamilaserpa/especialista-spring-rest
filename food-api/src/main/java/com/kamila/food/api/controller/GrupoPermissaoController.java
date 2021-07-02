package com.kamila.food.api.controller;

import java.util.List;

import com.kamila.food.api.openapi.controller.GrupoPermissaoControllerOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.kamila.food.api.assembler.PermissaoModelAssembler;
import com.kamila.food.api.model.PermissaoModel;
import com.kamila.food.domain.model.Grupo;
import com.kamila.food.domain.service.CadastroGrupoService;

@RestController
@RequestMapping(value = "/grupos/{idGrupo}/permissoes",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoPermissaoController implements GrupoPermissaoControllerOpenApi {

    @Autowired
    private CadastroGrupoService cadastroGrupo;

    @Autowired
    private PermissaoModelAssembler permissaoModelAssembler;

    @GetMapping
    public List<PermissaoModel> listar(@PathVariable Long idGrupo) {
        Grupo grupo = cadastroGrupo.buscarOuFalhar(idGrupo);

        return permissaoModelAssembler.toCollectionModel(grupo.getPermissoes());
    }

    @DeleteMapping("/{idPermissao}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long idGrupo, @PathVariable Long idPermissao) {
        cadastroGrupo.desassociarPermissao(idGrupo, idPermissao);
    }

    @PutMapping("/{idPermissao}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long idGrupo, @PathVariable Long idPermissao) {
        cadastroGrupo.associarPermissao(idGrupo, idPermissao);
    }

}
