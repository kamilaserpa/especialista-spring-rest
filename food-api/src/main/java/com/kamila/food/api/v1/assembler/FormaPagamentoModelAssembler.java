package com.kamila.food.api.v1.assembler;

import com.kamila.food.api.v1.FoodLinks;
import com.kamila.food.api.v1.controller.FormaPagamentoController;
import com.kamila.food.api.v1.model.FormaPagamentoModel;
import com.kamila.food.core.security.FoodSecurity;
import com.kamila.food.domain.model.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class FormaPagamentoModelAssembler
        extends RepresentationModelAssemblerSupport<FormaPagamento, FormaPagamentoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FoodLinks foodLinks;

    @Autowired
    private FoodSecurity foodSecurity;

    public FormaPagamentoModelAssembler() {
        super(FormaPagamentoController.class, FormaPagamentoModel.class);
    }

    public FormaPagamentoModel toModel(FormaPagamento formaPagamento) {
        FormaPagamentoModel formaPagamentoModel =
                createModelWithId(formaPagamento.getId(), formaPagamento);

        modelMapper.map(formaPagamento, formaPagamentoModel);

        if (foodSecurity.podeConsultarFormasPagamento()) {
            formaPagamentoModel.add(foodLinks.linkToFormasPagamento("formasPagamento"));
        }

        return formaPagamentoModel;
    }

    // Alterando objeto recebido para Collection, pois List herad de Collection e Set também,
    // logo desse modo o método recebe qualquer coleção
    @Override
    public CollectionModel<FormaPagamentoModel> toCollectionModel(Iterable<? extends FormaPagamento> entities) {
        CollectionModel<FormaPagamentoModel> collectionModel = super.toCollectionModel(entities);

        if (foodSecurity.podeConsultarFormasPagamento()) {
            collectionModel.add(foodLinks.linkToFormasPagamento());
        }

        return collectionModel;
    }

}
