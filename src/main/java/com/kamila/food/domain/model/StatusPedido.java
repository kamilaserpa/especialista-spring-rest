package com.kamila.food.domain.model;

public enum StatusPedido {

	CRIADO("Criado"),
	CONFIRMADO("Confirmado"),
	ENTREGUE("Entregue"),
	CANCELADO("Cancelado");
	
	// Adicionando descricao egível para humanos ao retornar enum ao consumidor
	private String descricao;
	
	StatusPedido(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDecricao() {
		return this.descricao;
	}
}
