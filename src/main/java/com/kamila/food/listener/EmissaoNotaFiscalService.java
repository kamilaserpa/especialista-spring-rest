package com.kamila.food.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.kamila.food.di.service.ClienteAtivadoEvent;

@Component
public class EmissaoNotaFiscalService {

	@EventListener
	public void clienteAtivadoListenner(ClienteAtivadoEvent event) {
		System.out.println("Emitindo nota fiscal para o cliente " + event.getCliente().getNome());
	}

}
