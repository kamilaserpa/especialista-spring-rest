package com.kamila.food.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.kamila.food.api.assembler.UsuarioModelAssembler;
import com.kamila.food.api.model.UsuarioModel;
import com.kamila.food.domain.model.Restaurante;
import com.kamila.food.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping(value = "/restaurantes/{idRestaurante}/responsaveis")
public class RestauranteUsuarioResponsavelController {


    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;
    
    @Autowired
    private UsuarioModelAssembler usuarioModelAssembler;
    
    @GetMapping
    public List<UsuarioModel> listar(@PathVariable Long idRestaurante) {
    	Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(idRestaurante);
    	
    	return usuarioModelAssembler.toCollectionModel(restaurante.getResponsaveis());
    }
    
    @DeleteMapping("/{idUsuario}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociarResponsavel(@PathVariable Long idRestaurante, @PathVariable Long idUsuario) {
    	cadastroRestauranteService.desassociarResponsavel(idRestaurante, idUsuario);
    }
    
    @PutMapping("/{idUsuario}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associarResponsavel(@PathVariable Long idRestaurante, @PathVariable Long idUsuario) {
    	cadastroRestauranteService.associarResponsavel(idRestaurante, idUsuario);
    }
    
}
