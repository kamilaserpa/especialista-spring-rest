package com.kamila.food.di.notificacao;

import com.kamila.food.di.modelo.Cliente;

public interface Notificador {

	void notificar(Cliente cliente, String mensagem);
	
}
