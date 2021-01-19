package com.kamila.food.domain.repository;

import java.util.List;

import com.kamila.food.domain.model.Cozinha;

public interface CozinhaRepository {

	List<Cozinha> listar();

	List<Cozinha> consultarPorNome(String nmCozinha);

	Cozinha buscar(Long id);

	Cozinha salvar(Cozinha cozinha);

	void remover(Long idCozinha);

}
