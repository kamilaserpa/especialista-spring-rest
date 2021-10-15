package com.kamila.food.api.v1.controller;

import com.kamila.food.api.v1.assembler.FormaPagamentoInputDisassembler;
import com.kamila.food.api.v1.assembler.FormaPagamentoModelAssembler;
import com.kamila.food.api.v1.model.FormaPagamentoModel;
import com.kamila.food.api.v1.model.input.FormaPagamentoInput;
import com.kamila.food.api.v1.openapi.controller.FormaPagamentoControllerOpenApi;
import com.kamila.food.core.security.CheckSecurity;
import com.kamila.food.domain.exception.EstadoNaoEncontradoException;
import com.kamila.food.domain.exception.NegocioException;
import com.kamila.food.domain.model.FormaPagamento;
import com.kamila.food.domain.repository.FormaPagamentoRepository;
import com.kamila.food.domain.service.CadastroFormaPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(path = "/v1/formas-pagamento", produces = MediaType.APPLICATION_JSON_VALUE)
public class FormaPagamentoController implements FormaPagamentoControllerOpenApi {

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    @Autowired
    private CadastroFormaPagamentoService cadastroFormaPagamentoService;

    @Autowired
    private FormaPagamentoModelAssembler formaPagamentoModelAssembler;

    @Autowired
    private FormaPagamentoInputDisassembler formaPagamentoInputDisassembler;

    /**
     * Enquanto não houver modificação em alguma tupla da tb_forma_pagamento, consumidor irá usar o cache local, se fresh.
     * Se stale (após maxAge: 10 segundos), a requisição irá  verificar se eTag é a mesma, caso positivo é retornado null e o consumidor utiliza
     * o dado em cache. Cso negativo a consulta é realizada novamente e retornado novo eTag.
     */
    @CheckSecurity.FormaPagamento.PodeConsultar
    @Override
    @GetMapping
    public ResponseEntity<CollectionModel<FormaPagamentoModel>> listar(ServletWebRequest request) {
        // Desabilitando ShallowEtagHeaderFilter
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

        String eTag = "0"; // imaginando eTag padrão para tabela vazia = 0

        OffsetDateTime dataUltimaAtualizacao = formaPagamentoRepository.getDataUltimaAtualizacao();
        if (dataUltimaAtualizacao != null) {
            eTag = String.valueOf(dataUltimaAtualizacao.toEpochSecond());
        }
        if (request.checkNotModified(eTag)) {
            // Se eTag recebida coincide com eTag calculada a execução acaba aqui.
            // É retornado null e o consumidor utiliza o dado em cache
            return null;
        }

        List<FormaPagamento> todasFormasPagamento = formaPagamentoRepository.findAll();
        CollectionModel<FormaPagamentoModel> formasPagamentoModel = formaPagamentoModelAssembler
                .toCollectionModel(todasFormasPagamento);
        // Header de Cache
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
                .eTag(eTag) // ou .header("Etag", eTag)
//                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePrivate())
//                .cacheControl(CacheControl.noCache())
//                .cacheControl(CacheControl.noStore())
                .body(formasPagamentoModel);
    }

    @CheckSecurity.FormaPagamento.PodeConsultar
    @Override
    @GetMapping("/{idFormaPagamento}")
    public ResponseEntity<FormaPagamentoModel> buscar(ServletWebRequest request, @PathVariable Long idFormaPagamento) {

        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
        String eTag = "0";

        OffsetDateTime dataAtualizacao = formaPagamentoRepository
                .getDataUltimaAtualizacaoById(idFormaPagamento);
        if (dataAtualizacao != null) {
            eTag = String.valueOf(dataAtualizacao.toEpochSecond());
        }
        if (request.checkNotModified(eTag)) {
            return null;
        }

        FormaPagamento formaPagamento = cadastroFormaPagamentoService.buscarOuFalhar(idFormaPagamento);
        FormaPagamentoModel formaPagamentoModel = formaPagamentoModelAssembler.toModel(formaPagamento);

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .eTag(eTag)
                .body(formaPagamentoModel);
    }

    @CheckSecurity.FormaPagamento.PodeEditar
    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoModel salvar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
        try {
            FormaPagamento formaPagamento = formaPagamentoInputDisassembler.toDomainObject(formaPagamentoInput);
            return formaPagamentoModelAssembler.toModel(cadastroFormaPagamentoService.salvar(formaPagamento));
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @CheckSecurity.FormaPagamento.PodeEditar
    @Override
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FormaPagamentoModel atualizar(@PathVariable Long id, @RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
        try {
            FormaPagamento formaPagamentoAtual = cadastroFormaPagamentoService.buscarOuFalhar(id);

            formaPagamentoInputDisassembler.copyToDomainObject(formaPagamentoInput, formaPagamentoAtual);

            formaPagamentoAtual = cadastroFormaPagamentoService.salvar(formaPagamentoAtual);

            return formaPagamentoModelAssembler.toModel(formaPagamentoAtual);
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @CheckSecurity.FormaPagamento.PodeEditar
    @Override
    @DeleteMapping("/{idFormaPagamento}")
    public void remover(@PathVariable Long idFormaPagamento) {
        cadastroFormaPagamentoService.remover(idFormaPagamento);
    }

}
