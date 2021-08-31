package com.kamila.food.api.v2.openapi.model;

import com.kamila.food.api.v2.model.CidadeModelV2;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;


@ApiModel("CidadesModel")
@Data
public class CidadesModelV2OpenApi {

    private com.kamila.food.api.v1.openapi.model.CidadesModelOpenApi.CidadeEmbeddedModelOpenApi _embedded;

    private Links _links;

    @ApiModel("CidadesEmbeddedModel")
    @Data
    public class CidadeEmbeddedModelOpenApi {
        private List<CidadeModelV2> cidades;
    }

}

