package com.kamila.food.domain.listener;

import com.kamila.food.domain.event.PedidoConfirmadoEvent;
import com.kamila.food.domain.model.Pedido;
import com.kamila.food.domain.service.EnvioEmailService;
import com.kamila.food.domain.service.EnvioEmailService.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class NotificacaoClientePedidoConfirmadoListener {

    @Autowired
    private EnvioEmailService envioEmailService;


    // Anotação fundamental para disparar esse método no registro do evento recebido por parâmetro
    // Transactional: o evento é realizado após a transação ser comitada
    // Se a transação já foi commitada não será realizado Rollback
    // caso ocorra exceção no evento de enviar e-mail.
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
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
