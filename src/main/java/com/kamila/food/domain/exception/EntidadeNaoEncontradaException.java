package com.kamila.food.domain.exception;

public class EntidadeNaoEncontradaException extends RuntimeException {

	private static final long serialVersionUID = -4789090968896555220L;

	public EntidadeNaoEncontradaException(String mensagem) {
		super(mensagem);
	}

}
