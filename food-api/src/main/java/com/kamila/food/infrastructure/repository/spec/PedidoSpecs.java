package com.kamila.food.infrastructure.repository.spec;

import java.util.ArrayList;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.kamila.food.domain.model.Pedido;
import com.kamila.food.domain.filter.PedidoFilter;

public class PedidoSpecs {

	public static Specification<Pedido> usandoFiltro(PedidoFilter filtro) {
		return (root, query, builder) -> {
			
			/**
			 * Para o count é executada a consulta com os mesmos filtros. Para evitar erro na consulta count
			 * por ausência de entidades relacionadas, verifica-se o typo do resultado, se forem pedidos serão executados os fetchs
			 */
			if (Pedido.class.equals(query.getResultType())) {
				// Hibernate realiza apenas uma consulta. Evitando problema do "n+1"
				root.fetch("restaurante").fetch("cozinha");
				root.fetch("cliente");
			}

			var predicates = new ArrayList<Predicate>();
			
			if (filtro.getClienteId() != null) {
				predicates.add(builder.equal(root.get("cliente"), filtro.getClienteId()));
			}
			
			if (filtro.getRestauranteId() != null) {
				predicates.add(builder.equal(root.get("restaurante"), filtro.getRestauranteId()));
			}
			// Criados após ta criação recebida
			if (filtro.getDataCriacaoInicio() != null) {
				predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoInicio()));
			}
			// Pedidos criados antes da data criação recebida
			if (filtro.getDataCriacaoFim() != null) {
				predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoFim()));
			}
			
			return builder.and(predicates.toArray(new Predicate[0]));
		};
	}

}
