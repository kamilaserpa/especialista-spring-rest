package com.kamila.food.domain.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_grupo")
public class Grupo {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_grupo")
	private Long id;
	
	@Column(name = "nm_grupo", nullable = false)
	private String nome;
	
	@ManyToMany
	@JoinTable(name = "tb_grupo_permissao", joinColumns = @JoinColumn(name = "id_grupo"),
			inverseJoinColumns = @JoinColumn(name = "id_permissao"))
	private List<Permissao> permissoes = new ArrayList<>();
	
}