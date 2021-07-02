package com.kamila.food.api.controller;

import com.kamila.food.api.assembler.GrupoModelAssembler;
import com.kamila.food.api.model.GrupoModel;
import com.kamila.food.domain.model.Usuario;
import com.kamila.food.domain.service.CadastroUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/usuarios/{idUsuario}/grupos")
public class UsuarioGrupoController {

	@Autowired
	private CadastroUsuarioService cadastroUsuarioService;
	
	@Autowired
	private GrupoModelAssembler grupoModelAssembler;
	
	@GetMapping
	public List<GrupoModel> listar(@PathVariable Long idUsuario) {
		Usuario usuario = cadastroUsuarioService.buscarOuFalhar(idUsuario);
		
		return grupoModelAssembler.toCollectionModel(usuario.getGrupos());
	}
	
	@DeleteMapping("/{idGrupo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desassociarGrupo(@PathVariable Long idUsuario, @PathVariable Long idGrupo) {
		cadastroUsuarioService.desassociarGrupo(idUsuario, idGrupo);
	}
	
	@PutMapping("/{idGrupo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associarGrupo(@PathVariable Long idUsuario, @PathVariable Long idGrupo) {
		cadastroUsuarioService.associarGrupo(idUsuario, idGrupo);
	}
	
}
