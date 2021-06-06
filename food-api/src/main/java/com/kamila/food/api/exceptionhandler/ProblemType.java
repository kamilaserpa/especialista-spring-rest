package com.kamila.food.api.exceptionhandler;

import lombok.Getter;
/**
 * Centraliza type e title da representação de respostas de erro.
 * @author kamila.serpa
 *
 */
@Getter
public enum ProblemType {

	RECURSO_NÃO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado"),
	ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio"),
	ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
	MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem incompreensível"),
	PARAMETRO_INVALIDO("/parametro-invalido", "Parâmetro inválido"),
	ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de sistema"),
	DADOS_INVALIDOS("/dados-invalidos", "Dados inválidos");

	private String title;
	private String uri;

	private ProblemType(String path, String title) {
		this.uri = "https://kamilafood.com.br" + path;
		this.title = title;
	}

}
