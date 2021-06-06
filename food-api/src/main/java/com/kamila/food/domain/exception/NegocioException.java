package com.kamila.food.domain.exception;

/**
 * Exceção para erros de negócio não específicos, genéricos
 */

public class NegocioException extends RuntimeException {

	private static final long serialVersionUID = -4789090968896555220L;

	public NegocioException(String mensagem) {
		super(mensagem);
	}
	
	public NegocioException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}

}
