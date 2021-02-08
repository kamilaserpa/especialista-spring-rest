package com.kamila.food.domain.exception;

public abstract class EntidadeNaoEncontradaException extends NegocioException  {

	private static final long serialVersionUID = -4789090968896555220L;

	public EntidadeNaoEncontradaException(String mensagem) {
		super(mensagem);
	}

}
