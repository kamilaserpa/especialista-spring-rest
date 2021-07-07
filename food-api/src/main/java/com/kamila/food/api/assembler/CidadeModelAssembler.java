package com.kamila.food.api.assembler;

import com.kamila.food.api.FoodLinks;
import com.kamila.food.api.controller.CidadeController;
import com.kamila.food.api.model.CidadeModel;
import com.kamila.food.domain.model.Cidade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class CidadeModelAssembler extends
        RepresentationModelAssemblerSupport<Cidade, CidadeModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FoodLinks foodLinks;

    public CidadeModelAssembler() {
        // Controlador que gerencia a classe e a classe de representação do modelo
        super(CidadeController.class, CidadeModel.class);
    }

    @Override
    public CidadeModel toModel(Cidade cidade) {
        // createModelWithId já adiciona o link 'self' por padrão
        CidadeModel cidadeModel = createModelWithId(cidade.getId(), cidade);
        modelMapper.map(cidade, cidadeModel);

        cidadeModel.add(foodLinks.linkToCidades("cidades"));

        cidadeModel.getEstado().add(foodLinks.linkToEstado(cidadeModel.getEstado().getId()));

        return cidadeModel;
    }

    @Override
    public CollectionModel<CidadeModel> toCollectionModel(Iterable<? extends Cidade> entities) {
        return super.toCollectionModel(entities)
                .add(WebMvcLinkBuilder.linkTo(CidadeController.class).withSelfRel());
    }

}
