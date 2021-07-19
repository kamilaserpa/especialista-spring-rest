package com.kamila.food.api.openapi.controller;

import com.kamila.food.api.exceptionhandler.Problem;
import com.kamila.food.api.model.GrupoModel;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Api(tags = "Usuários")
public interface UsuarioGrupoControllerOpenApi {

    @ApiOperation("Lista os grupos associados a usuário")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
    })
    CollectionModel<GrupoModel> listar(
            @ApiParam(value = "ID de um usuário", example = "1", required = true)
                    Long idUsuario);


    @ApiOperation("Desassociação de grupo com usuário")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Desassociação realizada com sucesso"),
            @ApiResponse(code = 404, message = "Usuário ou grupo não encontrado", response = Problem.class)
    })
    ResponseEntity<Void> desassociarGrupo(
            @ApiParam(value = "ID de um usuário", example = "1", required = true)
                    Long idUsuario,
            @ApiParam(value = "ID de um grupo", example = "1", required = true)
                    Long idGrupo);


    @ApiOperation("Associação de grupo com usuário")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Associação realizada com sucesso"),
            @ApiResponse(code = 404, message = "Usuário ou grupo não encontrado",
                    response = Problem.class)
    })
    ResponseEntity<Void> associarGrupo(
            @ApiParam(value = "ID do usuário", example = "1", required = true)
                    Long usuarioId,

            @ApiParam(value = "ID do grupo", example = "1", required = true)
                    Long grupoId);

}
