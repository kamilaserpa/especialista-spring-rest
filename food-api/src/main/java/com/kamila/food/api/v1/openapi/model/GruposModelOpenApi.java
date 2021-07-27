package com.kamila.food.api.v1.openapi.model;

import com.kamila.food.api.v1.model.GrupoModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("GruposModel")
@Data
public class GruposModelOpenApi {

    private GrupoEmbeddedModelOpenApi _embedded;

    private Links _links;

    @ApiModel("GruposEmbeddedModel")
    @Data
    public class GrupoEmbeddedModelOpenApi {
        private List<GrupoModel> grupos;
    }

}

