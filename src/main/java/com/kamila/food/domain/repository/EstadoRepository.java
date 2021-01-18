package com.kamila.food.domain.repository;

import java.util.List;

import com.kamila.food.domain.model.Estado;

public interface EstadoRepository {

	List<Estado> listar();

	Estado buscar(Long id);

	Estado salvar(Estado Estado);

	void remover(Long idEstado);

}
