package com.kamila.food.api.v1.assembler;

import com.kamila.food.api.v1.FoodLinks;
import com.kamila.food.api.v1.controller.EstadoController;
import com.kamila.food.api.v1.model.EstadoModel;
import com.kamila.food.core.security.FoodSecurity;
import com.kamila.food.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class EstadoModelAssembler extends RepresentationModelAssemblerSupport<Estado, EstadoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FoodLinks foodLinks;

    @Autowired
    private FoodSecurity foodSecurity;

    public EstadoModelAssembler() {
        super(EstadoController.class, EstadoModel.class);
    }

    @Override
    public EstadoModel toModel(Estado estado) {
        EstadoModel estadoModel = createModelWithId(estado.getId(), estado);
        modelMapper.map(estado, estadoModel);

        if (foodSecurity.podeConsultarEstados()) {
            estadoModel.add(foodLinks.linkToEstados("estados"));
        }
        return estadoModel;
    }

    @Override
    public CollectionModel<EstadoModel> toCollectionModel(Iterable<? extends Estado> entities) {
        CollectionModel<EstadoModel> collectionModel = super.toCollectionModel(entities);

        if (foodSecurity.podeConsultarEstados()) {
            collectionModel.add(foodLinks.linkToEstados());
        }

        return collectionModel;
    }

}
