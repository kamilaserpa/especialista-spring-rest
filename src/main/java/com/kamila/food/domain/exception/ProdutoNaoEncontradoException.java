package com.kamila.food.domain.exception;

public class ProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public ProdutoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

	public ProdutoNaoEncontradoException(Long idRestaurante, Long idProduto) {
		this(String.format("Não existe um cadastro de produto com código %d para o restaurante de código %d", idProduto, idRestaurante));
	}

}
