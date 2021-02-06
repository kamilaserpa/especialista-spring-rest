package com.kamila.food.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EntidadeNaoEncontradaException extends RuntimeException {

	private static final long serialVersionUID = -4789090968896555220L;

	public EntidadeNaoEncontradaException(String mensagem) {
		super(mensagem);
	}

}
