package com.kamila.food.domain.repository;

import java.util.List;

import com.kamila.food.domain.model.FormaPagamento;

public interface FormaPagamentoRepository {

	List<FormaPagamento> listar();

	FormaPagamento buscar(Long id);

	FormaPagamento salvar(FormaPagamento FormaPagamento);

	void remover(FormaPagamento FormaPagamento);

}
