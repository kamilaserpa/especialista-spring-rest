package com.kamila.food.di.notificacao;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.kamila.food.di.modelo.Cliente;

@Primary
@Component
public class NotificadorSMS implements Notificador {
	
	@Override
	public void notificar(Cliente cliente, String mensagem) {
		System.out.printf("Notificando %s por SMS através do telefone %s: %s\n", 
				cliente.getNome(), cliente.getTelefone(), mensagem);
	}

}