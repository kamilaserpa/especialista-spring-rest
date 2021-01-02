package com.kamila.food.di.service;

import com.kamila.food.di.modelo.Cliente;

public class ClienteAtivadoEvent {

	private Cliente cliente;

	public ClienteAtivadoEvent(Cliente cliente) {
		super();
		this.cliente = cliente;
	}
	
	public Cliente getCliente() {
		return cliente;
	}
}
