package com.kamila.food.api.exceptionhandler;

import lombok.Getter;
/**
 * Centraliza type e title da representação de respostas de erro.
 * @author kamila.serpa
 *
 */
@Getter
public enum ProblemType {

	ENTIDADE_NAO_ENCONTRADA("/entidade-nao-encontrada", "Entidade não encontrada");

	private String title;
	private String uri;

	private ProblemType(String path, String title) {
		this.uri = "https://kamilafood.com.br" + path;
		this.title = title;
	}

}
