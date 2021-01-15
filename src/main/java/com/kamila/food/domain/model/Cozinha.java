package com.kamila.food.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@JsonRootName("cozinha") // Altera o nome do objeto no xml de "Cozinha" para "cozinha"
@Getter
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_cozinha")
public class Cozinha {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_cozinha")
	private Long id;

	// @JsonProperty("titulo") // altera representação nas requisições para "titulo"
	// @JsonIgnore // Ignora este atributo nas requisições
	@Column(name = "nm_cozinha", length = 50, nullable = false)
	private String nmCozinha;

}
