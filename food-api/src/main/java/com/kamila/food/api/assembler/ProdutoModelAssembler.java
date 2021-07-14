package com.kamila.food.api.assembler;

import com.kamila.food.api.FoodLinks;
import com.kamila.food.api.controller.RestauranteProdutoController;
import com.kamila.food.api.model.ProdutoModel;
import com.kamila.food.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ProdutoModelAssembler
        extends RepresentationModelAssemblerSupport<Produto, ProdutoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FoodLinks foodLinks;

    public ProdutoModelAssembler() {
        super(RestauranteProdutoController.class, ProdutoModel.class);
    }

    public ProdutoModel toModel(Produto produto) {
        // id do restaurante pois é parâmetro em RequestMapping do Controller
        ProdutoModel produtoModel = createModelWithId(produto.getId(), produto,
                produto.getRestaurante().getId());

        modelMapper.map(produto, produtoModel);

        produtoModel.add(foodLinks.linkToProdutos(produto.getRestaurante().getId(), "produtos"));

        return produtoModel;
    }

}
