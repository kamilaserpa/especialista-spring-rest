package com.kamila.food.api.openapi.controller;

import com.kamila.food.api.exceptionhandler.Problem;
import com.kamila.food.api.model.UsuarioModel;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Api(tags = "Restaurantes")
public interface RestauranteUsuarioResponsavelControllerOpenApi {

    @ApiOperation("Lista os usuários responsáveis associadas a restaurante")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    CollectionModel<UsuarioModel> listar(
            @ApiParam(value = "ID de um restaurante", example = "1", required = true)
                    Long idRestaurante);


    @ApiOperation("Desassociação de restaurante com usuário responsável")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Desassociação realizada com sucesso"),
            @ApiResponse(code = 404, message = "Restaurante ou usuário não encontrado", response = Problem.class)
    })
    ResponseEntity<Void> desassociarResponsavel(
            @ApiParam(value = "ID de um restaurante", example = "1", required = true)
                    Long idRestaurante,
            @ApiParam(value = "ID ddo usuário", example = "1", required = true)
                    Long idUsuario);


    @ApiOperation("Associação de restaurante com usuário responsável")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Associação realizada com sucesso"),
            @ApiResponse(code = 404, message = "Restaurante ou usuário não encontrado", response = Problem.class)
    })
    ResponseEntity<Void> associarResponsavel(
            @ApiParam(value = "ID de um restaurante", example = "1", required = true)
                    Long idRestaurante,
            @ApiParam(value = "ID ddo usuário", example = "1", required = true)
                    Long idUsuario);

}
