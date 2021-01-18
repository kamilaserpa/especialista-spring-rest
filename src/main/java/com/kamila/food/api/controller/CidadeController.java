package com.kamila.food.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.kamila.food.domain.exception.EntidadeEmUsoException;
import com.kamila.food.domain.exception.EntidadeNaoEncontradaException;
import com.kamila.food.domain.model.Cidade;
import com.kamila.food.domain.repository.CidadeRepository;
import com.kamila.food.domain.service.CadastroCidadeService;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastroCidadeService cadastroCidadeService;
	
	@GetMapping
	public List<Cidade> listar() {
		return cidadeRepository.listar();
	}
	
	@GetMapping("/{idCidade}")
	public ResponseEntity<Cidade> buscar(@PathVariable Long idCidade) {
		Cidade cidade = cidadeRepository.buscar(idCidade);
		if (cidade != null) {
			return ResponseEntity.ok(cidade);
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> salvar(@RequestBody Cidade cidade) {
		try {
			cidade = cadastroCidadeService.salvar(cidade);
			return ResponseEntity.status(HttpStatus.CREATED).body(cidade);
			
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
			
		}
	}
	
	@PutMapping("/{idCidade}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Cidade> atualizar(@PathVariable Long idCidade, @RequestBody Cidade cidade) {
		Cidade cidadeAtual = cidadeRepository.buscar(idCidade);
		if (cidadeAtual != null) {
			BeanUtils.copyProperties(cidade, cidadeAtual, "id");

			cadastroCidadeService.salvar(cidadeAtual);

			return ResponseEntity.ok(cidadeAtual);
		}
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{idCidade}")
	public ResponseEntity<Cidade> remover(@PathVariable Long idCidade) {
		try {
			cadastroCidadeService.remover(idCidade);
			return ResponseEntity.noContent().build();
			
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
			
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
	
}