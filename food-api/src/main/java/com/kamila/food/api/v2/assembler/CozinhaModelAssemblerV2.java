package com.kamila.food.api.v2.assembler;

import com.kamila.food.api.v1.controller.CozinhaController;
import com.kamila.food.api.v2.FoodLinksV2;
import com.kamila.food.api.v2.model.CozinhaModelV2;
import com.kamila.food.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CozinhaModelAssemblerV2 extends RepresentationModelAssemblerSupport<Cozinha, CozinhaModelV2> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private FoodLinksV2 foodLinksV2;

	public CozinhaModelAssemblerV2() {
		super(CozinhaController.class, CozinhaModelV2.class);
	}

	@Override
	public CozinhaModelV2 toModel(Cozinha cozinha) {
		CozinhaModelV2 cozinhaModel = createModelWithId(cozinha.getId(), cozinha);
		modelMapper.map(cozinha, cozinhaModel);

		cozinhaModel.add(foodLinksV2.linkToCozinhas("cozinhas"));

		return cozinhaModel;
	}

}
