package com.kamila.food.api.openapi.controller;

import com.kamila.food.api.exceptionhandler.Problem;
import com.kamila.food.api.model.FormaPagamentoModel;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Api(tags = "Restaurantes")
public interface RestauranteFormaPagamentoControllerOpenApi {

    @ApiOperation("Lista formas de pagamento associadas a restaurante")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    CollectionModel<FormaPagamentoModel> listar(
            @ApiParam(value = "ID de um restaurante", example = "1", required = true)
                    Long idRestaurante);


    @ApiOperation("Desassociação de restaurante com forma de pagamento")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Desassociação realizada com sucesso"),
            @ApiResponse(code = 404, message = "Restaurante ou forma de pagamento não encontrado", response = Problem.class)
    })
    ResponseEntity<Void> desassociar(
            @ApiParam(value = "ID de um restaurante", example = "1", required = true)
                    Long idRestaurante,
            @ApiParam(value = "ID da forma de pagamento", example = "1", required = true)
                    Long idFormaPagamento);


    @ApiOperation("Associação de restaurante com forma de pagamento")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Associação realizada com sucesso"),
            @ApiResponse(code = 404, message = "Restaurante ou forma de pagamento não encontrado", response = Problem.class)
    })
    ResponseEntity<Void> associar( // ResponseEntity<Void> para conseguir utilizar methodOn() em FoodLinks
            @ApiParam(value = "ID de um restaurante", example = "1", required = true)
                    Long idRestaurante,
            @ApiParam(value = "ID da forma de pagamento", example = "1", required = true)
                    Long idFormaPagamento);


}
