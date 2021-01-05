package com.kamila.food.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.kamila.food.domain.model.FormaPagamento;
import com.kamila.food.domain.repository.FormaPagamentoRepository;

@Component
public class FormaPagamentoRepositoryImpl implements FormaPagamentoRepository {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<FormaPagamento> listar() {
		return manager.createQuery("from FormaPagamento", FormaPagamento.class).getResultList();
	}

	@Override
	public FormaPagamento buscar(Long id) {
		return manager.find(FormaPagamento.class, id);
	}

	@Transactional
	@Override
	public FormaPagamento salvar(FormaPagamento FormaPagamento) {
		return manager.merge(FormaPagamento);
	}

	@Transactional
	@Override
	public void remover(FormaPagamento FormaPagamento) {
		FormaPagamento = buscar(FormaPagamento.getId());
		manager.remove(FormaPagamento);
	}

}
