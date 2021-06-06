package com.kamila.food.domain.exception;

public class FotoProdutoNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public FotoProdutoNaoEncontradaException(String mensagem) {
		super(mensagem);
	}

	public FotoProdutoNaoEncontradaException(Long idRestaurante, Long idProduto) {
		this(String.format("Não existe um cadastro de foto de produto com código %d para o restaurante de código %d", idProduto, idRestaurante));
	}

}
