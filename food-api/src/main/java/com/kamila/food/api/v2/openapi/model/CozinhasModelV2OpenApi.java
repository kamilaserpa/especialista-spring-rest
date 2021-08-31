package com.kamila.food.api.v2.openapi.model;

import com.kamila.food.api.v1.openapi.model.PageModelOpenApi;
import com.kamila.food.api.v2.model.CozinhaModelV2;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("CozinhasModel")
@Getter
@Setter
public class CozinhasModelV2OpenApi {

    private CozinhasModelV2OpenApi.CozinhasEmbeddedModelOpenApi _embedded;

    private Links _links;

    private PageModelOpenApi page;

    @ApiModel("CozinhasEmbeddedModel")
    @Data
    public class CozinhasEmbeddedModelOpenApi {
        private List<CozinhaModelV2> cozinhas;
    }

}
