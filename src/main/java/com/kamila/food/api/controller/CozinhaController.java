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
import com.kamila.food.domain.model.Cozinha;
import com.kamila.food.domain.repository.CozinhaRepository;
import com.kamila.food.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;

	@GetMapping
	public List<Cozinha> listar() {
		return cozinhaRepository.findAll();
	}

	@GetMapping("/{idCozinha}")
	public ResponseEntity<Cozinha> buscar(@PathVariable Long idCozinha) {
		Cozinha cozinha = cozinhaRepository.findById(idCozinha).get();
		if (cozinha != null) {
			return ResponseEntity.ok(cozinha);
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cozinha salvar(@RequestBody Cozinha cozinha) {
		return cadastroCozinhaService.salvar(cozinha);
	}

	@PutMapping("/{idCozinha}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Cozinha> atualizar(@PathVariable Long idCozinha, @RequestBody Cozinha cozinha) {
		Cozinha cozinhaAtual = cozinhaRepository.getOne(idCozinha);
		if (cozinhaAtual != null) {
			// Copia propriedades do primeiro objeto no segundo objeto.
			// Ignora o atributo "id" adicionado como terceiro parâmetro
			BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");

			cadastroCozinhaService.salvar(cozinhaAtual);

			return ResponseEntity.ok(cozinhaAtual);
		}
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{idCozinha}")
	public ResponseEntity<Cozinha> remover(@PathVariable Long idCozinha) {
		try {
			cadastroCozinhaService.remover(idCozinha);
			return ResponseEntity.noContent().build();
			
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
			
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}

}
