package com.kamila.food.domain.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;


	public PedidoNaoEncontradoException(String codigoPedido) {
		super(String.format("Não existe um cadastro de pedido com código %s", codigoPedido));
	}

}
