package com.kamila.food.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeResumoModel {

	private Long id;

	private String nome;
	
	private String estado;
	
	// String estado; Faz com que ModelMapper call toString() do Objeto Estado e retorne para o consumidor "estado": "Estado(id=1, nome=Minas Gerais)"
	// Para
	
}
