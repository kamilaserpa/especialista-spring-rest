package com.kamila.food.api.v1.assembler;

import com.kamila.food.api.v1.FoodLinks;
import com.kamila.food.api.v1.controller.CidadeController;
import com.kamila.food.api.v1.model.CidadeModel;
import com.kamila.food.core.security.FoodSecurity;
import com.kamila.food.domain.model.Cidade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CidadeModelAssembler extends
        RepresentationModelAssemblerSupport<Cidade, CidadeModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FoodLinks foodLinks;

    @Autowired
    private FoodSecurity foodSecurity;

    public CidadeModelAssembler() {
        // Controlador que gerencia a classe e a classe de representação do modelo
        super(CidadeController.class, CidadeModel.class);
    }

    @Override
    public CidadeModel toModel(Cidade cidade) {
        // createModelWithId já adiciona o link 'self' por padrão
        CidadeModel cidadeModel = createModelWithId(cidade.getId(), cidade);
        modelMapper.map(cidade, cidadeModel);

        if (foodSecurity.podeConsultarCidades()) {
            cidadeModel.add(foodLinks.linkToCidades("cidades"));
        }

        if (foodSecurity.podeConsultarEstados()) {
            cidadeModel.getEstado().add(foodLinks.linkToEstado(cidadeModel.getEstado().getId()));
        }

        return cidadeModel;
    }

    @Override
    public CollectionModel<CidadeModel> toCollectionModel(Iterable<? extends Cidade> entities) {
        CollectionModel<CidadeModel> collectionModel = super.toCollectionModel(entities);

        if (foodSecurity.podeConsultarCidades()) {
            collectionModel.add(foodLinks.linkToCidades());
        }

        return collectionModel;
    }

}
