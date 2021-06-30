package com.kamila.food.api.controller;

import java.util.List;

import javax.validation.Valid;

import com.kamila.food.api.controller.openapi.GrupoControllerOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.kamila.food.api.assembler.GrupoInputDisassembler;
import com.kamila.food.api.assembler.GrupoModelAssembler;
import com.kamila.food.api.model.GrupoModel;
import com.kamila.food.api.model.input.GrupoInput;
import com.kamila.food.domain.exception.EstadoNaoEncontradoException;
import com.kamila.food.domain.exception.GrupoNaoEncontradoException;
import com.kamila.food.domain.exception.NegocioException;
import com.kamila.food.domain.model.Grupo;
import com.kamila.food.domain.repository.GrupoRepository;
import com.kamila.food.domain.service.CadastroGrupoService;

@RestController
@RequestMapping(path = "/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoController implements GrupoControllerOpenApi {

	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private CadastroGrupoService cadastroGrupoService;
	
	@Autowired
	private GrupoModelAssembler grupoModelAssembler;
	
	@Autowired
	private GrupoInputDisassembler grupoInputDisassembler;
	
	@GetMapping
	public List<GrupoModel> listar() {
		return grupoModelAssembler.toCollectionModel(grupoRepository.findAll());
	}
	
	@GetMapping("/{idCidade}")
	public GrupoModel buscar(@PathVariable Long idCidade) {
		Grupo grupo = cadastroGrupoService.buscarOuFalhar(idCidade);
		return grupoModelAssembler.toModel(grupo);

	}
	
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

	@DeleteMapping("/{idCidade}")
	public void remover(@PathVariable Long idGrupo) {
		cadastroGrupoService.remover(idGrupo);
	}
	
}
