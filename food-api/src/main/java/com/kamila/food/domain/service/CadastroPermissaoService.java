package com.kamila.food.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kamila.food.domain.exception.PermissaoNaoEncontradaException;
import com.kamila.food.domain.model.Permissao;
import com.kamila.food.domain.repository.PermissaoRepository;

@Service
public class CadastroPermissaoService {

	@Autowired
	PermissaoRepository permissaoRepository;

	public Permissao buscarOuFalhar(Long idPermissao) {
		return permissaoRepository.findById(idPermissao)
				.orElseThrow(() -> new PermissaoNaoEncontradaException(idPermissao));
	}
	
}
