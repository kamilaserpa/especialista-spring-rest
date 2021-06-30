package com.kamila.food.api.openapi.controller;

import com.kamila.food.api.exceptionhandler.Problem;
import com.kamila.food.api.model.CozinhaModel;
import com.kamila.food.api.model.input.CozinhaInput;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Api(tags = "Cozinhas")
public interface CozinhaControllerOpenApi {

    @ApiOperation("Lista as cozinhas")
    public Page<CozinhaModel> listar(Pageable pageable);


    @ApiOperation("Busca uma cozinha por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID da cozinha inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
    })
    public CozinhaModel buscar(@ApiParam(value = "ID de uma cozinha", example = "1")
                                       Long idCozinha);


    @ApiOperation("Cadastra uma cozinha")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cozinha cadastrado")
    })
    public CozinhaModel salvar(@ApiParam(name = "corpo", value = "Representação de uma cozinha")
                                       CozinhaInput grupoInput);


    @ApiOperation("Atualiza uma cozinha por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cozinha atualizada"),
            @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
    })
    public CozinhaModel atualizar(@ApiParam(value = "ID de uma cozinha", example = "1")
                                          Long id,
                                  @ApiParam(name = "corpo", value = "Representação de uma cozinha com os novos dados")
                                          CozinhaInput grupoInput);


    @ApiOperation("Exclui uma cozinha por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cozinha excluído"),
            @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
    })
    public void remover(@ApiParam(value = "ID de uma cozinha", example = "1")
                                Long idCozinha);

}

