package com.kamila.food.api.assembler;

import com.kamila.food.api.FoodLinks;
import com.kamila.food.api.controller.PedidoController;
import com.kamila.food.api.model.PedidoResumoModel;
import com.kamila.food.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class PedidoResumoModelAssembler
        extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FoodLinks foodLinks;

    public PedidoResumoModelAssembler() {
        super(PedidoController.class, PedidoResumoModel.class);
    }

    @Override
    public PedidoResumoModel toModel(Pedido pedido) {
        PedidoResumoModel pedidoModel = createModelWithId(pedido.getCodigo(), pedido);
        modelMapper.map(pedido, pedidoModel);

        pedidoModel.add(foodLinks.linkToPedidos());

        pedidoModel.getRestaurante().add(
                foodLinks.linkToRestaurante(pedido.getRestaurante().getId()));

        pedidoModel.getCliente().add(foodLinks.linkToUsuario(pedido.getCliente().getId()));
        return pedidoModel;
    }

}
