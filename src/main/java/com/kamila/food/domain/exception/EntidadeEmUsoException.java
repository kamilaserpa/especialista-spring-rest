package com.kamila.food.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class EntidadeEmUsoException extends RuntimeException {

	private static final long serialVersionUID = -5042420165811514643L;

	public EntidadeEmUsoException(String mensagem) {
		super(mensagem);
	}

}
