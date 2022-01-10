package com.kamila.food.api.v1.openapi;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Documented
@ApiImplicitParams({
        @ApiImplicitParam(
                name = "restauranteId",
                value = "ID do restaurante",
                example = "1",
                dataType = "int"),
        @ApiImplicitParam(
                name = "dataCriacaoInicio",
                value = "Data/hora inicial da criação do pedido",
                example = "2019-12-01T00:00:00Z",
                dataType = "date-time"),
        @ApiImplicitParam(
                name = "dataCriacaoFim",
                value = "Data/hora final da criação do pedido",
                example = "2019-12-02T23:59:59Z",
                dataType = "date-time")
})
public @interface EstatisticasVendasHeaders {
}
