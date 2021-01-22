package com.kamila.food.infrastructure.repository.spec;

import java.math.BigDecimal;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.kamila.food.domain.model.Restaurante;

public class RestauranteComFreteGratisSpec implements Specification<Restaurante>{
	
	private static final long serialVersionUID = 3825774615482716685L;

	@Override
	public Predicate toPredicate(Root<Restaurante> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		return builder.equal(root.get("taxaFrete"), BigDecimal.ZERO);
	}

}
