package com.kamila.food.api.v1.controller;

import com.kamila.food.api.v1.assembler.GrupoInputDisassembler;
import com.kamila.food.api.v1.assembler.GrupoModelAssembler;
import com.kamila.food.api.v1.model.GrupoModel;
import com.kamila.food.api.v1.model.input.GrupoInput;
import com.kamila.food.api.v1.openapi.controller.GrupoControllerOpenApi;
import com.kamila.food.core.security.CheckSecurity;
import com.kamila.food.domain.exception.EstadoNaoEncontradoException;
import com.kamila.food.domain.exception.GrupoNaoEncontradoException;
import com.kamila.food.domain.exception.NegocioException;
import com.kamila.food.domain.model.Grupo;
import com.kamila.food.domain.repository.GrupoRepository;
import com.kamila.food.domain.service.CadastroGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/v1/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoController implements GrupoControllerOpenApi {

	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private CadastroGrupoService cadastroGrupoService;
	
	@Autowired
	private GrupoModelAssembler grupoModelAssembler;
	
	@Autowired
	private GrupoInputDisassembler grupoInputDisassembler;

	@CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
	@Override
	@GetMapping
	public CollectionModel<GrupoModel> listar() {
		return grupoModelAssembler.toCollectionModel(grupoRepository.findAll());
	}

	@CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
	@Override
	@GetMapping("/{idGrupo}")
	public GrupoModel buscar(@PathVariable Long idGrupo) {
		Grupo grupo = cadastroGrupoService.buscarOuFalhar(idGrupo);
		return grupoModelAssembler.toModel(grupo);

	}

	@CheckSecurity.UsuariosGruposPermissoes.PodeEditar
	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public GrupoModel salvar(@RequestBody @Valid GrupoInput grupoInput) {
		try {
			Grupo grupo = grupoInputDisassembler.toDomainObject(grupoInput);
			return grupoModelAssembler.toModel(cadastroGrupoService.salvar(grupo));
		} catch (GrupoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e); // Alterando código de erro para 400
		}
	}

	@CheckSecurity.UsuariosGruposPermissoes.PodeEditar
	@Override
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public GrupoModel atualizar(@PathVariable Long id, @RequestBody @Valid GrupoInput grupoInput) {
		try {
			Grupo grupoAtual = cadastroGrupoService.buscarOuFalhar(id);

			grupoInputDisassembler.copoyToDomainObject(grupoInput, grupoAtual);

			grupoAtual = cadastroGrupoService.salvar(grupoAtual);
			
			return grupoModelAssembler.toModel(grupoAtual);
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	@CheckSecurity.UsuariosGruposPermissoes.PodeEditar
	@Override
	@DeleteMapping("/{idCidade}")
	public void remover(@PathVariable Long idGrupo) {
		cadastroGrupoService.remover(idGrupo);
	}
	
}
