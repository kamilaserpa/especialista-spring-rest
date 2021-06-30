package com.kamila.food.api.controller.openapi;

import com.kamila.food.api.exceptionhandler.Problem;
import com.kamila.food.api.model.CidadeModel;
import com.kamila.food.api.model.input.CidadeInput;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Api(tags = "Cidades")
public interface CidadeControllerOpenApi {

    @ApiOperation("Lista as cidades")
    public List<CidadeModel> listar();

    @ApiOperation("Busca uma cidade por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID da cidade inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    public CidadeModel buscar(@ApiParam(value = "ID de uma cidade", example = "1")
                                      Long idCidade);

    @ApiOperation("Cadastra uma cidade")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cidade cadastrada")
    })
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeModel salvar(@ApiParam(name = "corpo", value = "Representação de uma cidade")
                                      CidadeInput cidadeInput);

    @ApiOperation("Atualiza uma cidade por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cidade atualizada"),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    @ResponseStatus(HttpStatus.OK)
    public CidadeModel atualizar(@ApiParam(value = "ID de uma cidade", example = "1")
                                         Long id,
                                 @ApiParam(name = "corpo", value = "Representação de uma cidade com os novos dados")
                                         CidadeInput cidadeInput);

    @ApiOperation("Exclui uma cidade por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cidade excluída"),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    public void remover(@ApiParam(value = "ID de uma cidade", example = "1") Long idCidade);

}

