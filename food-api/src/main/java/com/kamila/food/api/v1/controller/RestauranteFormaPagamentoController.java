package com.kamila.food.api.controller;

import com.kamila.food.api.FoodLinks;
import com.kamila.food.api.assembler.FormaPagamentoModelAssembler;
import com.kamila.food.api.model.FormaPagamentoModel;
import com.kamila.food.api.openapi.controller.RestauranteFormaPagamentoControllerOpenApi;
import com.kamila.food.domain.model.Restaurante;
import com.kamila.food.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/restaurantes/{idRestaurante}/formas-pagamento",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteFormaPagamentoController implements RestauranteFormaPagamentoControllerOpenApi {

    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @Autowired
    private FormaPagamentoModelAssembler formaPagamentoModelAssembler;

    @Autowired
    private FoodLinks foodLinks;

    @Override
    @GetMapping
    public CollectionModel<FormaPagamentoModel> listar(@PathVariable Long idRestaurante) {
        Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(idRestaurante);
        CollectionModel<FormaPagamentoModel> formaPagamentoModels = formaPagamentoModelAssembler.toCollectionModel(restaurante.getFormasPagamento())
                .removeLinks()
                .add(foodLinks.linkToRestauranteFormasPagamento(idRestaurante))
                .add(foodLinks.linkToRestauranteFormaPagamentoAssociacao(idRestaurante, "associar"));

        formaPagamentoModels.getContent().forEach(formaPagamentoModel -> {
            formaPagamentoModel.add(foodLinks.linkToRestauranteFormaPagamentoDesassociacao(
                    idRestaurante, formaPagamentoModel.getId(),
                    "desassociar"
            ));
        });

        return formaPagamentoModels;
    }

    @Override
    @DeleteMapping("/{idFormaPagamento}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> desassociar(@PathVariable Long idRestaurante, @PathVariable Long idFormaPagamento) {
        cadastroRestauranteService.desassociarFormaPagamento(idRestaurante, idFormaPagamento);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PutMapping("/{idFormaPagamento}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associar(@PathVariable Long idRestaurante, @PathVariable Long idFormaPagamento) {
        cadastroRestauranteService.associarFormaPagamento(idRestaurante, idFormaPagamento);
        return ResponseEntity.noContent().build();
    }


}
