package com.kamila.food.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.kamila.food.domain.model.Estado;
import com.kamila.food.domain.repository.EstadoRepository;
import com.kamila.food.domain.service.CadastroEstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController {

	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CadastroEstadoService cadastroEstadoService;
	
	@GetMapping
	public List<Estado> listar() {
		return estadoRepository.findAll();
	}
	
	@GetMapping("/{idEstado}")
	public Estado buscar(@PathVariable Long idEstado) {
		return cadastroEstadoService.buscarOuFalhar(idEstado);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Estado salvar(@RequestBody @Valid Estado estado) {
		return cadastroEstadoService.salvar(estado);
	}

	@PutMapping("/{idEstado}")
	@ResponseStatus(HttpStatus.OK)
	public Estado atualizar(@PathVariable Long idEstado, @RequestBody @Valid Estado estado) {
		Estado estadoAtual = cadastroEstadoService.buscarOuFalhar(idEstado);
		
		BeanUtils.copyProperties(estado, estadoAtual, "id");

		return cadastroEstadoService.salvar(estadoAtual);
	}

	@DeleteMapping("/{idEstado}")
	public void remover(@PathVariable Long idEstado) {
			cadastroEstadoService.remover(idEstado);
	}
	
}
