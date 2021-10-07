package com.kamila.food.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kamila.food.domain.exception.NegocioException;
import com.kamila.food.domain.exception.UsuarioNaoEncontradoException;
import com.kamila.food.domain.model.Grupo;
import com.kamila.food.domain.model.Usuario;
import com.kamila.food.domain.repository.UsuarioRepository;

@Service
public class CadastroUsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private CadastroGrupoService cadastroGrupoService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Transactional
	public Usuario salvar(Usuario usuario) {
		usuarioRepository.detach(usuario); // Não irá sincronizar as alterações nesse objeto ao final da transação

		Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());

		if (usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) {
			throw new NegocioException(
					String.format("Já existe usuário cadastrado com o e-mail: %s", usuario.getEmail()));
		}

		if (usuario.isNovo()) {
			usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
		}
		
		return usuarioRepository.save(usuario);
	}
	
	@Transactional
	public void alterarSenha(Long usuarioId, String senhaAtual, String novaSenha) {
		Usuario usuario = buscarOuFalhar(usuarioId);

		if (!passwordEncoder.matches(senhaAtual, usuario.getSenha())) {
			throw new NegocioException("Senha atual informada não coincide com a senha do usuário.");
		}
		
		usuario.setSenha(passwordEncoder.encode(novaSenha));
	}

	public Usuario buscarOuFalhar(Long idUsuario) {
		return usuarioRepository.findById(idUsuario)
			.orElseThrow(() -> new UsuarioNaoEncontradoException(idUsuario));
	}

	@Transactional
	public void desassociarGrupo(Long idUsuario, Long idGrupo) {
		Usuario usuario = buscarOuFalhar(idUsuario);
		Grupo grupo = cadastroGrupoService.buscarOuFalhar(idGrupo);
		
		usuario.desassociarGrupo(grupo);
	}

	@Transactional
	public void associarGrupo(Long idUsuario, Long idGrupo) {
		Usuario usuario = buscarOuFalhar(idUsuario);
		Grupo grupo = cadastroGrupoService.buscarOuFalhar(idGrupo);
		
		usuario.associarGrupo(grupo);
	}
	

}
