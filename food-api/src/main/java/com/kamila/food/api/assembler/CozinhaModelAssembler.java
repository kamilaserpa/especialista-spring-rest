package com.kamila.food.api.assembler;

import com.kamila.food.api.FoodLinks;
import com.kamila.food.api.controller.CozinhaController;
import com.kamila.food.api.model.CozinhaModel;
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

	public CozinhaModelAssembler() {
		super(CozinhaController.class, CozinhaModel.class);
	}

	@Override
	public CozinhaModel toModel(Cozinha cozinha) {
		CozinhaModel cozinhaModel = createModelWithId(cozinha.getId(), cozinha);
		modelMapper.map(cozinha, cozinhaModel);

		cozinhaModel.add(foodLinks.linkToCozinhas("cozinhas"));

		return cozinhaModel;
	}

//	@Override
//	public CollectionModel<CozinhaModel> toCollectionModel(Iterable<? extends Cozinha> entities) {
//		return super.toCollectionModel(entities)
//				.add(linkTo(CozinhaController.class).withSelfRel());
//	}

}
