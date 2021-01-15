package com.kamila.food.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.kamila.food.domain.model.Cozinha;

import lombok.Data;
import lombok.NonNull;

@JacksonXmlRootElement(localName = "cozinhas") // Altera o nome do elemento raiz no xml de "Cozinha" para "cozinha"
@Data
public class CozinhasXmlWrapper {
	
	@JsonProperty("cozinha")
	@JacksonXmlElementWrapper(useWrapping = false) // Desabilita um embrulho
	@NonNull
	private List<Cozinha> cozinhas;
	
}
