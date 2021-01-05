package com.kamila.food.domain.repository;

import java.util.List;

import com.kamila.food.domain.model.Cidade;

public interface CidadeRepository {

	List<Cidade> listar();

	Cidade buscar(Long id);

	Cidade salvar(Cidade Cidade);

	void remover(Cidade Cidade);

}
