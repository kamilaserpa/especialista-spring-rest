package com.kamila.food.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

import com.kamila.food.domain.model.Cozinha;

@Component
public class CadastroCozinha {

	@PersistenceContext // Injeta um entity manager
	private EntityManager manager;

	public List<Cozinha> listar() {
		TypedQuery<Cozinha> query = manager.createQuery("from Cozinha", Cozinha.class);
		return query.getResultList();
	}

}
