package com.kamila.food.api.controller;

import com.kamila.food.api.assembler.CidadeInputDisassembler;
import com.kamila.food.api.assembler.CidadeModelAssembler;
import com.kamila.food.api.openapi.controller.CidadeControllerOpenApi;
import com.kamila.food.api.model.CidadeModel;
import com.kamila.food.api.model.input.CidadeInput;
import com.kamila.food.domain.exception.EstadoNaoEncontradoException;
import com.kamila.food.domain.exception.NegocioException;
import com.kamila.food.domain.model.Cidade;
import com.kamila.food.domain.repository.CidadeRepository;
import com.kamila.food.domain.service.CadastroCidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeController implements CidadeControllerOpenApi {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CadastroCidadeService cadastroCidadeService;

    @Autowired
    private CidadeModelAssembler cidadeModelAssembler;

    @Autowired
    private CidadeInputDisassembler cidadeInputDisassembler;

    @GetMapping
    public List<CidadeModel> listar() {
        return cidadeModelAssembler.toCollectionModel(cidadeRepository.findAll());
    }

    @Override
    @GetMapping("/{idCidade}")
    public CidadeModel buscar(@PathVariable Long idCidade) {

        Cidade cidade = cadastroCidadeService.buscarOuFalhar(idCidade);
        return cidadeModelAssembler.toModel(cidade);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeModel salvar(@RequestBody @Valid CidadeInput cidadeInput) {
        try {
            Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);
            return cidadeModelAssembler.toModel(cadastroCidadeService.salvar(cidade));
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e); // Alterando código de erro para 400
        }
    }

    @Override
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CidadeModel atualizar(@PathVariable Long id,
                                 @RequestBody @Valid CidadeInput cidadeInput) {

        try {
            Cidade cidadeAtual = cadastroCidadeService.buscarOuFalhar(id);

            cidadeInputDisassembler.copyToDomainObject(cidadeInput, cidadeAtual);

            cidadeAtual = cadastroCidadeService.salvar(cidadeAtual);

            return cidadeModelAssembler.toModel(cidadeAtual);
        } catch (EstadoNaoEncontradoException e) {
            // Caso não existisse tratarNegocioException(), ao inserir a causa oriunda de
            // EstadoNaoEncontradoException
            // essa exceção seria capturada pelo tratarEntidadeNaoEncontradaException().
            // Se a causa não for inserida no constructor não entrará no método
            // tratarEntidadeNaoEncontradaException()
            throw new NegocioException(e.getMessage());
        }
    }

    @Override
    @DeleteMapping("/{idCidade}")
    public void remover(@PathVariable Long idCidade) {
        cadastroCidadeService.remover(idCidade);
    }

}
