package com.kamila.food.di.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kamila.food.di.modelo.Cliente;
import com.kamila.food.di.notificacao.Notificador;

@Component
public class AtivacaoClienteService {

	@Autowired
	private Notificador notificador;

//		@Autowired
//		public AtivacaoClienteService(Notificador notificador) {
//			this.notificador = notificador;
//		}
	//
//		public AtivacaoClienteService(String qualquer) {
//			
//		}

	public void ativar(Cliente cliente) {
		cliente.ativar();

		notificador.notificar(cliente, "Seu cadastro no sistema está ativo!");
	}

//		@Autowired
//		public void setNotificador(Notificador notificador) {
//			this.notificador = notificador;
//		}

}