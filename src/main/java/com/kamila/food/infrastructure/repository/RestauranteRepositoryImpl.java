package com.kamila.food.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import com.kamila.food.domain.model.Restaurante;
import com.kamila.food.domain.repository.RestauranteRepository;

@Component
public class RestauranteRepositoryImpl implements RestauranteRepository {

	@PersistenceContext // Injeta um entity manager
	private EntityManager manager;

	@Override
	public List<Restaurante> listar() {
		TypedQuery<Restaurante> query = manager.createQuery("from Restaurante", Restaurante.class);
		return query.getResultList();
	}

	@Override
	public Restaurante buscar(Long id) {
		return manager.find(Restaurante.class, id);
	}

	@Transactional
	@Override
	public Restaurante salvar(Restaurante restaurante) {
		return manager.merge(restaurante);
	}
	
	@Transactional
	@Override
	public void remover(Long idRestaurante) {
		Restaurante restaurante = buscar(idRestaurante);
		if (restaurante == null) {
			throw new EmptyResultDataAccessException(1);
		}
		manager.remove(idRestaurante);
	}

}
