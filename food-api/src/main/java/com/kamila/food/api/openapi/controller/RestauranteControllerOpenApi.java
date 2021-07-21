package com.kamila.food.api.openapi.controller;

import com.kamila.food.api.exceptionhandler.Problem;
import com.kamila.food.api.model.RestauranteApenasNomeModel;
import com.kamila.food.api.model.RestauranteBasicoModel;
import com.kamila.food.api.model.RestauranteModel;
import com.kamila.food.api.model.input.RestauranteInput;
import com.kamila.food.api.openapi.model.RestauranteBasicoModelOpenApi;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = "Restaurantes")
public interface RestauranteControllerOpenApi {

    @ApiOperation(value = "Lista restaurantes")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "Nome da projeção de pedidos", allowableValues = "apenas-nome",
                    name = "projecao", paramType = "query", type = "string")
    })
//	@JsonView(RestauranteView.Resumo.class)
    CollectionModel<RestauranteBasicoModel> listar();

    @ApiOperation(value = "Lista restaurantes", hidden = true)
    CollectionModel<RestauranteApenasNomeModel> listarApenasNomes();


    @ApiOperation("Busca um restaurante por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do restaurante inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    RestauranteModel buscar(@ApiParam(value = "ID de um restaurante", example = "1", required = true)
                                           Long idRestaurante);


    @ApiOperation("Cadastra um restaurante")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Restaurante cadastrado")
    })
    RestauranteModel salvar(@ApiParam(name = "corpo", value = "Representação de um restaurante", required = true)
                                           RestauranteInput restauranteInput);


    @ApiOperation("Atualiza um restaurante por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Restaurante atualizado"),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    RestauranteModel atualizar(
            @ApiParam(value = "ID de um restaurante", example = "1", required = true)
                    Long idRestaurante,

            @ApiParam(name = "corpo", value = "Representação de um restaurante", required = true)
                    RestauranteInput restauranteInput);


    @ApiOperation("Exclui um restaurante por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurante excluído"),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    void remover(
            @ApiParam(value = "ID de um restaurante", example = "1", required = true)
                    Long restauranteId);


    @ApiOperation("Ativa um restaurante por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurante ativado com sucesso"),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    ResponseEntity<Void> ativar(
            @ApiParam(value = "ID de um restaurante", example = "1", required = true)
                    Long restauranteId);


    @ApiOperation("Inativa um restaurante por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurante ativado com sucesso"),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    ResponseEntity<Void> inativar(
            @ApiParam(value = "ID de um restaurante", example = "1", required = true)
                    Long restauranteId);


    @ApiOperation("Ativa múltiplos restaurantes")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurantes ativados com sucesso")
    })
    void ativarMultiplos(
            @ApiParam(name = "corpo", value = "IDs de restaurantes", required = true)
                    List<Long> restauranteIds);


    @ApiOperation("Inativa múltiplos restaurantes")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurantes inativados com sucesso")
    })
    void inativarMultiplos(
            @ApiParam(name = "corpo", value = "IDs de restaurantes", required = true)
                    List<Long> restauranteIds);


    @ApiOperation("Abre um restaurante por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurante aberto com sucesso"),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    ResponseEntity<Void> abrir(
            @ApiParam(value = "ID de um restaurante", example = "1", required = true)
                    Long restauranteId);



    @ApiOperation("Fecha um restaurante por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurante fechado com sucesso"),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    ResponseEntity<Void> fechar(
            @ApiParam(value = "ID de um restaurante", example = "1", required = true)
                    Long restauranteId);


}

