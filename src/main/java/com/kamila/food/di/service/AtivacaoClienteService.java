package com.kamila.food.di.service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kamila.food.di.modelo.Cliente;
import com.kamila.food.di.notificacao.NivelUrgencia;
import com.kamila.food.di.notificacao.Notificador;
import com.kamila.food.di.notificacao.TipoDoNotificador;

//@Component
public class AtivacaoClienteService {

	@TipoDoNotificador(NivelUrgencia.SEM_URGENCIA)
	@Autowired
	private Notificador notificador;

//	@PostConstruct
	public void init() {
		System.out.println("INIT");
	}
	
//	@PreDestroy
	public void destroy() {
		System.out.println("DESTROY");
	}
	
	public void ativar(Cliente cliente) {
		cliente.ativar();
		
		notificador.notificar(cliente, "Seu cadastro no sistema está ativo!");
	}

}