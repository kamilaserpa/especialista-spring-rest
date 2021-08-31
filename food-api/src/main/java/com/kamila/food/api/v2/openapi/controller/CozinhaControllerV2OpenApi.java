package com.kamila.food.api.v2.openapi.controller;

import com.kamila.food.api.exceptionhandler.Problem;
import com.kamila.food.api.v2.model.CozinhaModelV2;
import com.kamila.food.api.v2.model.input.CozinhaInputV2;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

@Api(tags = "Cozinhas")
public interface CozinhaControllerV2OpenApi {

    @ApiOperation("Lista as cozinhas")
    PagedModel<CozinhaModelV2> listar(Pageable pageable);


    @ApiOperation("Busca uma cozinha por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID da cozinha inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
    })
    CozinhaModelV2 buscar(@ApiParam(value = "ID de uma cozinha", example = "1")
                                       Long idCozinha);


    @ApiOperation("Cadastra uma cozinha")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cozinha cadastrado")
    })
    CozinhaModelV2 salvar(@ApiParam(name = "corpo", value = "Representação de uma cozinha")
                                  CozinhaInputV2 grupoInput);


    @ApiOperation("Atualiza uma cozinha por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cozinha atualizada"),
            @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
    })
    CozinhaModelV2 atualizar(@ApiParam(value = "ID de uma cozinha", example = "1")
                                          Long id,
                                  @ApiParam(name = "corpo", value = "Representação de uma cozinha com os novos dados")
                                          CozinhaInputV2 grupoInput);


    @ApiOperation("Exclui uma cozinha por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cozinha excluído"),
            @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
    })
    void remover(@ApiParam(value = "ID de uma cozinha", example = "1")
                                Long idCozinha);

}

