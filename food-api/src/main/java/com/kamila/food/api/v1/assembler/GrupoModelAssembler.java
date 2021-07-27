package com.kamila.food.api.v1.assembler;

import com.kamila.food.api.v1.FoodLinks;
import com.kamila.food.api.v1.controller.GrupoController;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.kamila.food.api.v1.model.GrupoModel;
import com.kamila.food.domain.model.Grupo;

@Component
public class GrupoModelAssembler extends RepresentationModelAssemblerSupport<Grupo, GrupoModel> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private FoodLinks foodLinks;

	public GrupoModelAssembler() {
		super(GrupoController.class, GrupoModel.class);
	}

	public GrupoModel toModel(Grupo grupo) {
		GrupoModel grupoModel = createModelWithId(grupo.getId(), grupo);
		modelMapper.map(grupo, grupoModel);

		grupoModel.add(foodLinks.linkToGrupos("grupos"));
		grupoModel.add(foodLinks.linkToGrupoPermissoes(grupo.getId(), "permissoes"));

		return grupoModel;
	}

	@Override
	public CollectionModel<GrupoModel> toCollectionModel(Iterable<? extends Grupo> entities) {
		return super.toCollectionModel(entities)
				.add(foodLinks.linkToGrupos());
	}

}
