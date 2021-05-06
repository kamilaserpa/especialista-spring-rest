package com.kamila.food.infrastructure.service;

import com.kamila.food.domain.filter.VendaDiariaFilter;
import com.kamila.food.domain.model.Pedido;
import com.kamila.food.domain.model.StatusPedido;
import com.kamila.food.domain.model.dto.VendaDiaria;
import com.kamila.food.domain.service.VendaQueryService;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.criteria.Predicate;

@Repository(value = "VendaQueryServiceImpl")
public class VendaQueryServiceImpl implements VendaQueryService {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filter) {
        var builder = manager.getCriteriaBuilder();
        // Tipo de retorno
        var query = builder.createQuery(VendaDiaria.class);
        // Cláusula From
        var root = query.from(Pedido.class);

        var functionDateDataCriacao = builder.function(
                "date", Date.class, root.get("dataCriacao"));

        var selection = builder.construct(VendaDiaria.class,
                functionDateDataCriacao,
                builder.count(root.get("id")),
                builder.sum(root.get("valorTotal")));

        // predicates  e status in(confirmado / entregue)
        var predicates = new ArrayList<>();

        predicates.add(root.get("status").in(
                StatusPedido.CONFIRMADO, StatusPedido.ENTREGUE
        ));

        if (filter.getRestauranteId() != null) {
            predicates.add(builder.equal(root.get("restaurante"), filter.getRestauranteId()));
        }
        if (filter.getDataCriacaoInicio() != null) {
            predicates.add(builder.greaterThanOrEqualTo(
                    root.get("dataCriacao"),
                    filter.getDataCriacaoInicio())
            );
        }
        if (filter.getDataCriacaoFim() != null) {
            predicates.add(builder.lessThanOrEqualTo(
                    root.get("dataCriacao"),
                    filter.getDataCriacaoFim())
            );
        }

        query.select(selection);
        query.where(predicates.toArray(new Predicate[0]));
        query.groupBy(functionDateDataCriacao);

        return manager.createQuery(query).getResultList();
    }

}
