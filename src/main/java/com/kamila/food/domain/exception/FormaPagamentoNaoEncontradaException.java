package com.kamila.food.domain.exception;

public class FormaPagamentoNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public FormaPagamentoNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	
	public FormaPagamentoNaoEncontradaException(Long idFormaPagamento) {
		this(String.format("Não existe cadastro de forma de pagamento com código %d", idFormaPagamento));
	}

}
