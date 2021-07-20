package com.kamila.food.api.openapi.model;

import com.kamila.food.api.model.CozinhaModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("CozinhasModel")
@Getter
@Setter
public class CozinhasModelOpenApi {

    private CozinhasModelOpenApi.CozinhasEmbeddedModelOpenApi _embedded;

    private Links _links;

    private PageModelOpenApi page;

    @ApiModel("CozinhasEmbeddedModel")
    @Data
    public class CozinhasEmbeddedModelOpenApi {
        private List<CozinhaModel> cozinhas;
    }

}
