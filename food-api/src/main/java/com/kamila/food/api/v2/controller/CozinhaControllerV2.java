package com.kamila.food.api.v2.controller;

import com.kamila.food.api.v2.assembler.CozinhaInputDisassemblerV2;
import com.kamila.food.api.v2.assembler.CozinhaModelAssemblerV2;
import com.kamila.food.api.v2.model.CozinhaModelV2;
import com.kamila.food.api.v2.model.input.CozinhaInputV2;
import com.kamila.food.domain.model.Cozinha;
import com.kamila.food.domain.repository.CozinhaRepository;
import com.kamila.food.domain.service.CadastroCozinhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/v2/cozinhas", produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaControllerV2 {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private CadastroCozinhaService cadastroCozinhaService;

    @Autowired
    private CozinhaModelAssemblerV2 cozinhaModelAssemblerV2;

    @Autowired
    private CozinhaInputDisassemblerV2 cozinhaInputDisassemblerV2;

    @Autowired
    private PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;

    @GetMapping
    public PagedModel<CozinhaModelV2> listar(@PageableDefault(size = 10) Pageable pageable) { // Alterando padrão size que por default é 20
        Page<Cozinha> cozinhasPage = cozinhaRepository.findAll(pageable);

        // Convertendo Page em PagedModel. Já 'cozinhaModelAssembler' converte Cozinha em CozinhaModel
        PagedModel<CozinhaModelV2> cozinhasPagedModel = pagedResourcesAssembler
                .toModel(cozinhasPage, cozinhaModelAssemblerV2);

        return cozinhasPagedModel;
    }

    @GetMapping("/{idCozinha}")
    public CozinhaModelV2 buscar(@PathVariable Long idCozinha) {
        Cozinha cozinha = cadastroCozinhaService.buscarOuFalhar(idCozinha);

        return cozinhaModelAssemblerV2.toModel(cozinha);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaModelV2 salvar(@RequestBody @Valid CozinhaInputV2 cozinhaInput) {

        Cozinha cozinha = cozinhaInputDisassemblerV2.toDomainObject(cozinhaInput);

        cozinha = cadastroCozinhaService.salvar(cozinha);

        return cozinhaModelAssemblerV2.toModel(cozinha);
    }

    @PutMapping("/{idCozinha}")
    @ResponseStatus(HttpStatus.OK)
    public CozinhaModelV2 atualizar(@PathVariable Long idCozinha, @RequestBody @Valid CozinhaInputV2 cozinhaInput) {
        Cozinha cozinhaAtual = cadastroCozinhaService.buscarOuFalhar(idCozinha);

        cozinhaInputDisassemblerV2.copyToDomainObject(cozinhaInput, cozinhaAtual);

        cozinhaAtual = cadastroCozinhaService.salvar(cozinhaAtual);

        return cozinhaModelAssemblerV2.toModel(cozinhaAtual);
    }

    @DeleteMapping("/{idCozinha}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long idCozinha) {
        cadastroCozinhaService.remover(idCozinha);
    }

}
