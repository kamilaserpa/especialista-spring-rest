package com.kamila.food.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kamila.food.api.model.CozinhasXmlWrapper;
import com.kamila.food.domain.model.Cozinha;
import com.kamila.food.domain.repository.CozinhaRepository;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@GetMapping
	public List<Cozinha> listar() {
		return cozinhaRepository.listar();
	}
	
	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
	public CozinhasXmlWrapper listarXml() {
		return new CozinhasXmlWrapper(cozinhaRepository.listar());
	}
	
	@GetMapping("/{idCozinha}")
	public ResponseEntity<Cozinha> buscar(@PathVariable Long idCozinha) {
		Cozinha cozinha = cozinhaRepository.buscar(idCozinha);
		
//		return ResponseEntity.status(HttpStatus.OK).body(cozinha);
//		return ResponseEntity.ok(cozinha);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.LOCATION, "http://localhost:8080/cozinhas");
		
		return ResponseEntity.status(HttpStatus.FOUND)
				.headers(headers)
				.build();
	}
	
}
