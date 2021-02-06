package com.kamila.food.domain.exception;

public class EstadoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = -4789090968896555220L;

	public EstadoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public EstadoNaoEncontradoException(Long idEstado) {
		this(String.format("Não existe cadastro de estado com código %d.", idEstado));
	}

}
