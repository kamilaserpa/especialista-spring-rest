package com.kamila.food.api.v1.openapi.model;

import com.kamila.food.api.v1.model.FormaPagamentoModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("FormasPagamentoModel")
@Data
public class FormasPagamentoModelOpenApi {

    private FormaPagamentoEmbeddedModelOpenApi _embedded;

    private Links _links;

    @ApiModel("FormasPagamentoEmbeddedModel")
    @Data
    public class FormaPagamentoEmbeddedModelOpenApi {
        private List<FormaPagamentoModel> formasPagamento;
    }

}
