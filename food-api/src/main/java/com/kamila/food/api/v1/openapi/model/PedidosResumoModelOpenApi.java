package com.kamila.food.api.v1.openapi.model;

import com.kamila.food.api.v1.model.PedidoResumoModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("PedidosResumoModel")
@Getter
@Setter
public class PedidosResumoModelOpenApi {

    private PedidosResumoModelOpenApi.PedidosResumoEmbeddedModelOpenApi _embedded;

    private Links _links;

    private PageModelOpenApi page;

    @ApiModel("PedidosResumoEmbeddedModel")
    @Data
    public class PedidosResumoEmbeddedModelOpenApi {
        private List<PedidoResumoModel> pedidos;
    }

}