package com.kamila.food.domain.repository;

import java.util.List;

import com.kamila.food.domain.model.Permissao;

public interface PermissaoRepository {

	List<Permissao> listar();

	Permissao buscar(Long id);

	Permissao salvar(Permissao Permissao);

	void remover(Permissao Permissao);

}
