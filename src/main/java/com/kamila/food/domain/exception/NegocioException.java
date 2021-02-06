package com.kamila.food.domain.exception;

/**
 * Exceção para erros de negócio não específicos, genéricos
 */

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NegocioException extends RuntimeException {

	private static final long serialVersionUID = -4789090968896555220L;

	public NegocioException(String mensagem) {
		super(mensagem);
	}

}
