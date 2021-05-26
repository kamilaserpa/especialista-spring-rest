package com.kamila.food.domain.event;

import com.kamila.food.domain.model.Pedido;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PedidoConfirmadoEvent {

    private Pedido pedido;

}
