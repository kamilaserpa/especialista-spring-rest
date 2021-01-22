package com.kamila.food.infrastructure.repository.spec;

import java.math.BigDecimal;

import org.springframework.data.jpa.domain.Specification;

import com.kamila.food.domain.model.Restaurante;

public class RestauranteSpecs {

	public static Specification<Restaurante> comFreteGratis() {
		return (root, query, builder) -> builder.equal(root.get("taxaFrete"), BigDecimal.ZERO);
	}

	public static Specification<Restaurante> comNomeSemelhante(String nmRestaurante) {
		return (root, query, builder) -> builder.like(root.get("nmRestaurante"), "%" + nmRestaurante + "%" );
	}

}
