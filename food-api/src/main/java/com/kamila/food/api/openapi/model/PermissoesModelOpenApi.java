package com.kamila.food.api.openapi.model;

import com.kamila.food.api.model.GrupoModel;
import com.kamila.food.api.model.PermissaoModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("PermissoesModel")
@Data
public class PermissoesModelOpenApi {

    private PermissaoEmbeddedModelOpenApi _embedded;

    private Links _links;

    @ApiModel("PermissoesEmbeddedModel")
    @Data
    public class PermissaoEmbeddedModelOpenApi {
        private List<PermissaoModel> permissoes;
    }

}

