package com.kamila.food.api.v1.assembler;

import com.kamila.food.api.v1.FoodLinks;
import com.kamila.food.api.v1.controller.PedidoController;
import com.kamila.food.api.v1.model.PedidoResumoModel;
import com.kamila.food.core.security.FoodSecurity;
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

    @Autowired
    private FoodSecurity foodSecurity;

    public PedidoResumoModelAssembler() {
        super(PedidoController.class, PedidoResumoModel.class);
    }

    @Override
    public PedidoResumoModel toModel(Pedido pedido) {
        PedidoResumoModel pedidoModel = createModelWithId(pedido.getCodigo(), pedido);
        modelMapper.map(pedido, pedidoModel);

        if (foodSecurity.podePesquisarPedidos()) {
            pedidoModel.add(foodLinks.linkToPedidos("pedidos"));
        }

        if (foodSecurity.podeConsultarRestaurantes()) {
            pedidoModel.getRestaurante().add(
                    foodLinks.linkToRestaurante(pedido.getRestaurante().getId()));
        }

        if (foodSecurity.podeConsultarUsuariosGruposPermissoes()) {
            pedidoModel.getCliente().add(foodLinks.linkToUsuario(pedido.getCliente().getId()));
        }

        return pedidoModel;
    }

}
