package com.kamila.food.api.v1.assembler;

import com.kamila.food.api.v1.FoodLinks;
import com.kamila.food.api.v1.controller.PedidoController;
import com.kamila.food.api.v1.model.PedidoModel;
import com.kamila.food.core.security.FoodSecurity;
import com.kamila.food.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * Classe responsável por adicionar links
 * seguindo padrão HATEOAS
 */
@Component
public class PedidoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FoodLinks foodLinks;

    @Autowired
    private FoodSecurity foodSecurity;

    public PedidoModelAssembler() {
        super(PedidoController.class, PedidoModel.class);
    }

    @Override
    public PedidoModel toModel(Pedido pedido) {
        PedidoModel pedidoModel = createModelWithId(pedido.getCodigo(), pedido);
        modelMapper.map(pedido, pedidoModel);

        // Não utilizado foodSecurity.podePesquisarPedidos(clienteId, restauranteId) aqui,
        // porque na geração do link, não temos o id do cliente e do restaurante
        if (foodSecurity.podePesquisarPedidos()) {
            pedidoModel.add(foodLinks.linkToPedidos("pedidos"));
        }

        // Verifica permissão do usuário de gerenciar pedidos. Para não enviar links que o usuário não pode acessar
        if (foodSecurity.podeGerenciarPedidos(pedido.getCodigo())) {
            // Por estado do pedido
            if (pedido.podeSerConfirmado()) {
                pedidoModel.add(foodLinks.linkToConfirmarPedido(pedido.getCodigo(), "confirmar"));
            }
            if (pedido.podeSerEntregue()) {
                pedidoModel.add(foodLinks.linkToEntregarPedido(pedido.getCodigo(), "entregar"));
            }
            if (pedido.podeSerCancelado()) {
                pedidoModel.add(foodLinks.linkToCancelarPedido(pedido.getCodigo(), "cancelar"));
            }
        }

        if (foodSecurity.podeConsultarRestaurantes()) {
            pedidoModel.getRestaurante().add(
                    foodLinks.linkToRestaurante(pedido.getRestaurante().getId()));
        }

        if (foodSecurity.podeConsultarUsuariosGruposPermissoes()) {
            pedidoModel.getCliente().add(
                    foodLinks.linkToUsuario(pedido.getCliente().getId()));
        }

        if (foodSecurity.podeConsultarFormasPagamento()) {
            pedidoModel.getFormaPagamento().add(
                    foodLinks.linkToFormaPagamento(pedido.getFormaPagamento().getId()));
        }

        if (foodSecurity.podeConsultarCidades()) {
            pedidoModel.getEnderecoEntrega().getCidade().add(
                    foodLinks.linkToCidade(pedido.getEnderecoEntrega().getCidade().getId()));
        }

        // Quem pode consultar restaurantes, também pode consultar os produtos dos restaurantes
        if (foodSecurity.podeConsultarRestaurantes()) {
            pedidoModel.getItens().forEach(item -> {
                item.add(foodLinks.linkToProduto(
                        pedidoModel.getRestaurante().getId(), item.getProdutoId(), "produto"));
            });
        }

        return pedidoModel;
    }


    @Override
    public CollectionModel<PedidoModel> toCollectionModel(Iterable<? extends Pedido> entities) {
        return super.toCollectionModel(entities)
                .add(linkTo(PedidoController.class).withSelfRel());
    }

}
