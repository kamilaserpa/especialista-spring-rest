package com.kamila.food.domain.listener;

import com.kamila.food.domain.event.PedidoConfirmadoEvent;
import com.kamila.food.domain.model.Pedido;
import com.kamila.food.domain.service.EnvioEmailService;
import com.kamila.food.domain.service.EnvioEmailService.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class NotificacaoClientePedidoConfirmadoListener {

    @Autowired
    private EnvioEmailService envioEmailService;

    @EventListener // Anotação fundamental para disparar esse método no registro do evento recebido por parâmetro
    public void aoConfirmarPedido(PedidoConfirmadoEvent event) {
        Pedido pedido = event.getPedido();

        var mensagem = Mensagem.builder()
                .assunto(pedido.getRestaurante().getNome() + " - Pedido Confirmado")
                .corpo("pedido-confirmado.html")
                .variavel("pedido", pedido)
                .destinatario(pedido.getCliente().getEmail())
                .build();

        envioEmailService.enviar(mensagem);
    }

}
