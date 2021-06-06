package com.kamila.food.domain.listener;

import com.kamila.food.domain.event.PedidoConfirmadoEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class BonificacaoClientePedidoConfirmadoListener {

    @EventListener // Anotação fundamental para disparar esse método no registro do evento recebido por parâmetro
    public void aoConfirmarPedido(PedidoConfirmadoEvent event) {
        System.out.println("Calculando pontos para cliente: "
                + event.getPedido().getCliente().getNome());
    }

}
