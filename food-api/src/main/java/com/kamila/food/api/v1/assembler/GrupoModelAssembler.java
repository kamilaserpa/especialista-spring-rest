package com.kamila.food.api.v1.assembler;

import com.kamila.food.api.v1.FoodLinks;
import com.kamila.food.api.v1.controller.GrupoController;
import com.kamila.food.core.security.FoodSecurity;
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

	@Autowired
	private FoodSecurity foodSecurity;

	public GrupoModelAssembler() {
		super(GrupoController.class, GrupoModel.class);
	}

	public GrupoModel toModel(Grupo grupo) {
		GrupoModel grupoModel = createModelWithId(grupo.getId(), grupo);
		modelMapper.map(grupo, grupoModel);

		if (foodSecurity.podeConsultarUsuariosGruposPermissoes()) {
			grupoModel.add(foodLinks.linkToGrupos("grupos"));
			grupoModel.add(foodLinks.linkToGrupoPermissoes(grupo.getId(), "permissoes"));
		}
		return grupoModel;
	}

	@Override
	public CollectionModel<GrupoModel> toCollectionModel(Iterable<? extends Grupo> entities) {
		CollectionModel<GrupoModel> collectionModel = super.toCollectionModel(entities);

		if (foodSecurity.podeConsultarUsuariosGruposPermissoes()) {
			collectionModel.add(foodLinks.linkToGrupos());
		}

		return collectionModel;
	}

}
