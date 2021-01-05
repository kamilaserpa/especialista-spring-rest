package com.kamila.food.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.kamila.food.domain.model.Permissao;
import com.kamila.food.domain.repository.PermissaoRepository;

@Component
public class PermissaoRepositoryImpl implements PermissaoRepository {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Permissao> listar() {
		return manager.createQuery("from Permissao", Permissao.class).getResultList();
	}

	@Override
	public Permissao buscar(Long id) {
		return manager.find(Permissao.class, id);
	}

	@Transactional
	@Override
	public Permissao salvar(Permissao Permissao) {
		return manager.merge(Permissao);
	}

	@Transactional
	@Override
	public void remover(Permissao Permissao) {
		Permissao = buscar(Permissao.getId());
		manager.remove(Permissao);
	}

}
