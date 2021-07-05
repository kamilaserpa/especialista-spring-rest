package com.kamila.food.api.assembler;

import com.kamila.food.api.controller.CidadeController;
import com.kamila.food.api.controller.EstadoController;
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

    public CidadeModelAssembler() {
        // Controlador que gerencia a classe e a classe de representação do modelo
        super(CidadeController.class, CidadeModel.class);
    }

    @Override
    public CidadeModel toModel(Cidade cidade) {
        // createModelWithId já adiciona o link 'self' por padrão
        CidadeModel cidadeModel = createModelWithId(cidade.getId(), cidade);
        modelMapper.map(cidade, cidadeModel);

//        cidadeModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
//                .methodOn(CidadeController.class)
//                .buscar(cidadeModel.getId())
//        ).withSelfRel());

        cidadeModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
                .methodOn(CidadeController.class)
                .listar()
        ).withRel("cidades"));

        cidadeModel.getEstado().add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
                .methodOn(EstadoController.class)
                .buscar(cidadeModel.getEstado().getId())
        ).withSelfRel());

        return cidadeModel;
    }

    @Override
    public CollectionModel<CidadeModel> toCollectionModel(Iterable<? extends Cidade> entities) {
        return super.toCollectionModel(entities)
                .add(WebMvcLinkBuilder.linkTo(CidadeController.class).withSelfRel());
    }

}
