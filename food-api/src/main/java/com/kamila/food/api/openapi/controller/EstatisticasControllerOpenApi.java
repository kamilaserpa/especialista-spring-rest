package com.kamila.food.api.openapi.controller;

import com.kamila.food.api.controller.EstatisticasController;
import com.kamila.food.domain.filter.VendaDiariaFilter;
import com.kamila.food.domain.model.dto.VendaDiaria;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = "Estatísticas")
public interface EstatisticasControllerOpenApi {

    @ApiOperation(value = "Estatísticas", hidden = true)
    EstatisticasController.EstatisticasModel estatisticas();


    @ApiOperation("Consulta estatísticas de vendas diárias")
    @ApiImplicitParams({
            // Parâmetros documentados aqui para não inserir anotações de documentação (API)
            // em classe de domínio (VendaDiaria)
            @ApiImplicitParam(name = "restauranteId", value = "ID do restaurante",
                    example = "1", dataType = "int"),
            @ApiImplicitParam(name = "dataCriacaoInicio", value = "Data/hora inicial da criação do pedido",
                    example = "2019-12-01T00:00:00Z", dataType = "date-time"),
            @ApiImplicitParam(name = "dataCriacaoFim", value = "Data/hora final da criação do pedido",
                    example = "2019-12-02T23:59:59Z", dataType = "date-time")
    })
    List<VendaDiaria> consultarVendasDiarias(
            VendaDiariaFilter filtro,
            @ApiParam(value = "Deslocamento de horário a ser ocnsiderado na consulta em relação ao UTC",
                    defaultValue = "+00:00")
                    String timeOffset);

    
    ResponseEntity<byte[]> consultarVendasDiariasPdf(
            VendaDiariaFilter filtro,
            String timeOffset);

}
