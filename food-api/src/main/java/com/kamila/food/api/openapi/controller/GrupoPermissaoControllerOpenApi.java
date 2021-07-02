package com.kamila.food.api.openapi.controller;

import com.kamila.food.api.exceptionhandler.Problem;
import com.kamila.food.api.model.PermissaoModel;
import io.swagger.annotations.*;

import java.util.List;

@Api(tags = "Grupos")
public interface GrupoPermissaoControllerOpenApi {

    @ApiOperation("Lista as permissões associadas a um grupo")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do grupo inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
    })
    List<PermissaoModel> listar(
            @ApiParam(value = "ID do grupo", example = "1", required = true)
                    Long idGrupo);


    @ApiOperation("Desassociação de permissão com um grupo")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Desassociação realizada ocm sucesso"),
            @ApiResponse(code = 404, message = "Grupo ou permissão não encontrada", response = Problem.class)
    })
    void desassociar(
            @ApiParam(value = "ID do grupo", example = "1", required = true)
                    Long idGrupo,
            @ApiParam(value = "ID da permissão", example = "1", required = true)
                    Long idPermissao);


    @ApiOperation("Associação de permissão com um grupo")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Associação realizada ocm sucesso"),
            @ApiResponse(code = 404, message = "Grupo ou permissão não encontrada", response = Problem.class)
    })
    void associar(
            @ApiParam(value = "ID do grupo", example = "1", required = true)
                    Long idGrupo,
            @ApiParam(value = "ID da permissão", example = "1", required = true)
                    Long idPermissao);

}
