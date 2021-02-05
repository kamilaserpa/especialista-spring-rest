package com.kamila.food.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

//@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Entidade não encontrada")
public class EntidadeNaoEncontradaException extends ResponseStatusException {

	private static final long serialVersionUID = -4789090968896555220L;

	public EntidadeNaoEncontradaException(HttpStatus status, String mensagem) {
		super(status, mensagem);
	}

	// Código de status padrão caso nenhum seja informado na instanciação da exception
	// Status em um pacote no domain, porém não nas classes de serviço, onde deve haver regras de negócio
	public EntidadeNaoEncontradaException(String mensagem) {
		this(HttpStatus.NOT_FOUND, mensagem);
	}

}
