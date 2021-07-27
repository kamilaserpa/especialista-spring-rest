package com.kamila.food.api.v1.openapi.controller;

import com.kamila.food.api.exceptionhandler.Problem;
import com.kamila.food.api.v1.model.UsuarioModel;
import com.kamila.food.api.v1.model.input.SenhaInput;
import com.kamila.food.api.v1.model.input.UsuarioComSenhaInput;
import com.kamila.food.api.v1.model.input.UsuarioInput;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;

@Api(tags = "Usuários")
public interface UsuarioControllerOpenApi {

    @ApiOperation("Lista os usuários")
    CollectionModel<UsuarioModel> listar();


    @ApiOperation("Busca um usuário por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do usuário inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
    })
    UsuarioModel buscar(@ApiParam(value = "ID de um usuário", example = "1", required = true)
                                Long usuarioId);

    @ApiOperation("Cadastra um usuário")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Usuário cadastrado")
    })
    UsuarioModel salvar(@ApiParam(name = "corpo", value = "Representação de um novo usuário", required = true)
                                UsuarioComSenhaInput usuarioInput);


    @ApiOperation("Atualiza um usuário por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Usuário atualizado"),
            @ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
    })
    UsuarioModel atualizar(
            @ApiParam(value = "ID de um usuário", example = "1", required = true)
                    Long usuarioId,
            @ApiParam(name = "corpo", value = "Representação de um usuário com os novos dados", required = true)
                    UsuarioInput usuarioInput);


    @ApiOperation("Atualiza a senha de um usuário")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Senha alterada com sucesso"),
            @ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
    })
    void alterarSenha(
            @ApiParam(value = "ID de um usuário", example = "1", required = true)
                    Long usuarioId,
            @ApiParam(name = "corpo", value = "Representação da senha", example = "1", required = true)
                    SenhaInput senha);

}
