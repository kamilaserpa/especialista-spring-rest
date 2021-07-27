package com.kamila.food.api.v1.openapi.controller;

import com.kamila.food.api.exceptionhandler.Problem;
import com.kamila.food.api.v1.model.FotoProdutoModel;
import com.kamila.food.api.v1.model.input.FotoProdutoInput;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Api(tags = "Produtos")
public interface RestauranteProdutoFotoControllerOpenApi {

    @ApiOperation(value = "Busca a foto do produto de um restaurante",
            produces = "application/json, image/jpeg, image/png")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do restaurante ou produto inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Foto de produto não encontrada", response = Problem.class)
    })
    FotoProdutoModel buscar(
            @ApiParam(value = "ID do restaurante", example = "1", required = true)
                    Long idRestaurante,
            @ApiParam(value = "ID do produto", example = "1", required = true)
                    Long idProduto);


    @ApiOperation(value = "Busca a foto do produto de um restaurante", hidden = true)
    ResponseEntity<?> servirFoto(Long idRestaurante, Long idProduto, String acceptHeader)
            throws HttpMediaTypeNotAcceptableException;


    @ApiOperation("Atualiza a foto do produto de um restaurante")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Produto de restaurante não encontrado", response = Problem.class),
            @ApiResponse(code = 201, message = "Foto do produto atualizada")
    })
    FotoProdutoModel atualizarFoto(
            @ApiParam(value = "ID de um restaurante", example = "1", required = true)
                    Long idRestaurante,
            @ApiParam(value = "ID de um produto", example = "1", required = true)
                    Long idProduto,
            @ApiParam(name = "corpo", value = "Representação de uma foto de produto")
                    FotoProdutoInput fotoProdutoInput,
            @ApiParam(value = "Arquivo da foto do produto (máximo 500KB, apenas JPG e PNG)",
                    required = true)
                    MultipartFile arquivo) throws IOException;


    @ApiOperation("Exclui a foto do produto de um restaurante")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Foto do produto excluída"),
            @ApiResponse(code = 400, message = "ID do restaurante ou produto inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Foto de produto não encontrada", response = Problem.class)
    })
    void remover(
            @ApiParam(value = "ID de um restaurante", example = "1", required = true)
                    Long idRestaurante,
            @ApiParam(value = "ID de um produto", example = "1", required = true)
                    Long idProduto);

}
