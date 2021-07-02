package com.kamila.food.api.openapi.controller;

import com.kamila.food.api.exceptionhandler.Problem;
import com.kamila.food.api.model.FormaPagamentoModel;
import com.kamila.food.api.model.input.FormaPagamentoInput;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.List;

@Api(tags = "Formas de pagamento")
public interface FormaPagamentoControllerOpenApi {

    @ApiOperation("Lista as formas de pagamento")
    ResponseEntity<List<FormaPagamentoModel>> listar(ServletWebRequest request);


    @ApiOperation("Busca uma forma de pagamento por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID da forma de pagamento inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Forma de Pagamento não encontrada", response = Problem.class)
    })
    ResponseEntity<FormaPagamentoModel> buscar(
            ServletWebRequest request,
            @ApiParam(value = "ID de uma forma de pagamento", example = "1")
                    Long idFormaPagamento);


    @ApiOperation("Cadastra uma forma de pagamento")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Forma de Pagamento cadastrada")
    })
    FormaPagamentoModel salvar(@ApiParam(name = "corpo", value = "Representação de uma forma de pagamento")
                                              FormaPagamentoInput formaPagamentoInput);


    @ApiOperation("Atualiza uma forma de pagamento por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Forma de Pagamento atualizada"),
            @ApiResponse(code = 404, message = "Forma de Pagamento não encontrada", response = Problem.class)
    })
    FormaPagamentoModel atualizar(@ApiParam(value = "ID de uma forma de pagamento", example = "1")
                                                 Long id,
                                         @ApiParam(name = "corpo", value = "Representação de uma forma de pagamento com os novos dados")
                                                 FormaPagamentoInput formaPagamentoInput);


    @ApiOperation("Exclui uma forma de pagamento por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Forma de Pagamento excluída"),
            @ApiResponse(code = 404, message = "Forma de Pagamento não encontrada", response = Problem.class)
    })
    void remover(@ApiParam(value = "ID de uma forma de pagamento", example = "1")
                                Long idFormaPagamento);

}
