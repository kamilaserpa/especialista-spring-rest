package com.kamila.food.api.v1.assembler;

import com.kamila.food.api.v1.FoodLinks;
import com.kamila.food.api.v1.controller.CozinhaController;
import com.kamila.food.api.v1.model.CozinhaModel;
import com.kamila.food.core.security.FoodSecurity;
import com.kamila.food.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CozinhaModelAssembler extends RepresentationModelAssemblerSupport<Cozinha, CozinhaModel> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private FoodLinks foodLinks;

	@Autowired
	private FoodSecurity foodSecurity;

	public CozinhaModelAssembler() {
		super(CozinhaController.class, CozinhaModel.class);
	}

	@Override
	public CozinhaModel toModel(Cozinha cozinha) {
		CozinhaModel cozinhaModel = createModelWithId(cozinha.getId(), cozinha);
		modelMapper.map(cozinha, cozinhaModel);

		if (foodSecurity.podeConsultarCozinhas()) {
			cozinhaModel.add(foodLinks.linkToCozinhas("cozinhas"));
		}

		return cozinhaModel;
	}

//	@Override
//	public CollectionModel<CozinhaModel> toCollectionModel(Iterable<? extends Cozinha> entities) {
//		return super.toCollectionModel(entities)
//				.add(linkTo(CozinhaController.class).withSelfRel());
//	}

}
