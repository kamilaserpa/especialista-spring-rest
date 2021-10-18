package com.kamila.food.api.v1.assembler;

import com.kamila.food.api.v1.FoodLinks;
import com.kamila.food.api.v1.controller.RestauranteProdutoFotoController;
import com.kamila.food.api.v1.model.FotoProdutoModel;
import com.kamila.food.core.security.FoodSecurity;
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

    @Autowired
    private FoodSecurity foodSecurity;

    public FotoProdutoModelAssembler() {
        super(RestauranteProdutoFotoController.class, FotoProdutoModel.class);
    }

    public FotoProdutoModel toModel(FotoProduto fotoProduto) {
        FotoProdutoModel fotoProdutoModel = modelMapper.map(fotoProduto, FotoProdutoModel.class);

        // Quem pode consultar restaurantes, também pode consultar os produtos e fotos
        if (foodSecurity.podeConsultarRestaurantes()) {
            fotoProdutoModel.add(foodLinks.linkToFotoProduto(fotoProduto.getRestauranteId(),
                    fotoProduto.getProduto().getId()));

            fotoProdutoModel.add(foodLinks.linkToProduto(fotoProduto.getRestauranteId(),
                    fotoProduto.getProduto().getId(), "produto"));
        }

        return fotoProdutoModel;
    }

}
