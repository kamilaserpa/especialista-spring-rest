package com.kamila.food.api.v1.openapi.model;

import com.kamila.food.api.v1.model.UsuarioModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;


@ApiModel("UsuariossModel")
@Data
public class UsuarioModelOpenApi {

    private UsuarioEmbeddedModelOpenApi _embedded;

    private Links _links;

    @ApiModel("UsuariosEmbeddedModel")
    @Data
    public class UsuarioEmbeddedModelOpenApi {
        private List<UsuarioModel> usuarios;
    }

}

