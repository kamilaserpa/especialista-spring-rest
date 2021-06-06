package com.kamila.food.domain.exception;

public class GrupoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = -4789090968896555220L;

	public GrupoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public GrupoNaoEncontradoException(Long idGrupo) {
		this(String.format("Não existe cadastro de grupo com código %d", idGrupo));
	}

}
