package com.kamila.food.api.openapi.model;

import com.kamila.food.api.model.EstadoModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("EstadosModel")
@Data
public class EstadosModelOpenApi {

    private EstadoEmbeddedModelOpenApi _embedded;

    private Links _links;

    @ApiModel("EstadosEmbeddedModel")
    @Data
    public class EstadoEmbeddedModelOpenApi {
        private List<EstadoModel> estados;
    }

}
