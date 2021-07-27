package com.kamila.food.api.v1.openapi.controller;

import com.kamila.food.api.exceptionhandler.Problem;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;

@Api(tags = "Pedidos")
public interface FluxoPedidoControllerOpenApi {

    @ApiOperation("Confirmação do pedido")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Pedido confirmado com sucesso"),
            @ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class)
    })
    ResponseEntity<Void> confirmar(
            @ApiParam(value = "Código do pedido", example = "335c0344-f4c5-4824-ad8a-944c9e6bdf43",
                    required = true)
                    String codigoPedido);


    @ApiOperation("Registra entrega de pedido")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Entrega de pedido registrada com sucesso"),
            @ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class)
    })
    ResponseEntity<Void> entregar(
            @ApiParam(value = "Código do pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55",
                    required = true)
                    String codigoPedido);


    @ApiOperation("Cancelamento do pedido")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Pedido cancelado com sucesso"),
            @ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class)
    })
    ResponseEntity<Void> cancelar(
            @ApiParam(value = "Código do pedido", example = "335c0344-f4c5-4824-ad8a-944c9e6bdf43",
                    required = true)
                    String codigoPedido);

}
