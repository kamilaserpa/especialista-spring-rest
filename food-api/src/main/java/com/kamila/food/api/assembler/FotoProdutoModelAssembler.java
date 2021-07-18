package com.kamila.food.api.assembler;

import com.kamila.food.api.FoodLinks;
import com.kamila.food.api.controller.RestauranteProdutoFotoController;
import com.kamila.food.api.model.FotoProdutoModel;
import com.kamila.food.domain.model.FotoProduto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class FotoProdutoModelAssembler extends RepresentationModelAssemblerSupport<FotoProduto, FotoProdutoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FoodLinks foodLinks;

    public FotoProdutoModelAssembler() {
        super(RestauranteProdutoFotoController.class, FotoProdutoModel.class);
    }

    public FotoProdutoModel toModel(FotoProduto fotoProduto) {
        FotoProdutoModel fotoProdutoModel = modelMapper.map(fotoProduto, FotoProdutoModel.class);

        fotoProdutoModel.add(foodLinks.linkToFotoProduto(fotoProduto.getRestauranteId(),
                fotoProduto.getProduto().getId()));

        fotoProdutoModel.add(foodLinks.linkToProduto(fotoProduto.getRestauranteId(),
                fotoProduto.getProduto().getId(), "produto"));

        return fotoProdutoModel;
    }

}
