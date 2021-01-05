package com.kamila.food.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.kamila.food.domain.model.Cidade;
import com.kamila.food.domain.repository.CidadeRepository;

@Component
public class CidadeRepositoryImpl implements CidadeRepository {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Cidade> listar() {
		return manager.createQuery("from Cidade", Cidade.class).getResultList();
	}

	@Override
	public Cidade buscar(Long id) {
		return manager.find(Cidade.class, id);
	}

	@Transactional
	@Override
	public Cidade salvar(Cidade Cidade) {
		return manager.merge(Cidade);
	}

	@Transactional
	@Override
	public void remover(Cidade Cidade) {
		Cidade = buscar(Cidade.getId());
		manager.remove(Cidade);
	}

}
