package com.kamila.food.domain.exception;

public class EntidadeEmUsoException extends RuntimeException {

	private static final long serialVersionUID = -5042420165811514643L;

	public EntidadeEmUsoException(String mensagem) {
		super(mensagem);
	}

}
