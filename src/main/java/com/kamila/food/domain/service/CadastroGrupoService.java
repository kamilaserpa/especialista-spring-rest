package com.kamila.food.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kamila.food.domain.exception.EntidadeEmUsoException;
import com.kamila.food.domain.exception.GrupoNaoEncontradoException;
import com.kamila.food.domain.model.Grupo;
import com.kamila.food.domain.model.Permissao;
import com.kamila.food.domain.repository.GrupoRepository;

@Service
public class CadastroGrupoService {

	private static final String MSG_GRUPO_EM_USO= "Grupo de código %d não pode ser removido, pois está em uso.";

	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private CadastroPermissaoService cadastroPermissaoService;
	
	@Transactional
	public Grupo salvar(Grupo grupo) {
		return grupoRepository.save(grupo);
	}
	
	@Transactional
	public void remover(Long idGrupo) {
		try {
			grupoRepository.deleteById(idGrupo);
			grupoRepository.flush();

		} catch (EmptyResultDataAccessException e) {
			throw new GrupoNaoEncontradoException(idGrupo);

		} catch (DataIntegrityViolationException e) {
			// Sem o flush(), o commit do delete poderia ser executado após o bloco de código catch
			throw new EntidadeEmUsoException(
					String.format(MSG_GRUPO_EM_USO, idGrupo));
		}
	}

	public Grupo buscarOuFalhar(Long idGrupo) {
		return grupoRepository.findById(idGrupo).orElseThrow(
				() -> new GrupoNaoEncontradoException(idGrupo));
	}
	
	@Transactional
	public void desassociarPermissao(Long idGrupo, Long idPermissao) {
	    Grupo grupo = buscarOuFalhar(idGrupo);
	    Permissao permissao = cadastroPermissaoService.buscarOuFalhar(idPermissao);
	    
	    grupo.removerPermissao(permissao);
	}
	

	@Transactional
	public void associarPermissao(Long idGrupo, Long idPermissao) {
	    Grupo grupo = buscarOuFalhar(idGrupo);
	    Permissao permissao = cadastroPermissaoService.buscarOuFalhar(idPermissao);
	    
	    grupo.adicionarPermissao(permissao);
	} 

}
