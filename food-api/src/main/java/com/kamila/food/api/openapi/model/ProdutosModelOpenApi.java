package com.kamila.food.api.openapi.model;

import com.kamila.food.api.model.ProdutoModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("ProdutosModel")
@Data
public class ProdutosModelOpenApi {

    private ProdutoEmbeddedModelOpenApi _embedded;

    private Links _links;

    @ApiModel("ProdutosEmbeddedModel")
    @Data
    public class ProdutoEmbeddedModelOpenApi {
        private List<ProdutoModel> produtos;
    }

}

