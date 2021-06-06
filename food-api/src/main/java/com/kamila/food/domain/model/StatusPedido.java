package com.kamila.food.domain.model;

import java.util.Arrays;
import java.util.List;

public enum StatusPedido {

	CRIADO("Criado"),
	CONFIRMADO("Confirmado", CRIADO),
	ENTREGUE("Entregue", CONFIRMADO),
	CANCELADO("Cancelado", CRIADO, CONFIRMADO);
	
	// Adicionando descricao egível para humanos ao retornar enum ao consumidor
	private String descricao;
	
	// Status anterior que permite alteraçao de status dentro da Regra de Negócio
	private List<StatusPedido> statusAnteriores;
	
	StatusPedido(String descricao, StatusPedido... statusAnteriores) { // var args, dado valor opcional
		this.descricao = descricao;
		this.statusAnteriores = Arrays.asList(statusAnteriores);
	}
	
	public String getDecricao() {
		return this.descricao;
	}
	
	// Verifica se o novo status está contido na lista de statusAnteriores permitidos
	public boolean naoPodeAlterarPara(StatusPedido novoStatus) {
		return !novoStatus.statusAnteriores.contains(this);
	}
	
}
