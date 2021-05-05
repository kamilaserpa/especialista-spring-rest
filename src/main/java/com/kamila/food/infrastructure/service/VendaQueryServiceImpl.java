package com.kamila.food.infrastructure.service;

import com.kamila.food.domain.filter.VendaDiariaFilter;
import com.kamila.food.domain.model.Pedido;
import com.kamila.food.domain.model.dto.VendaDiaria;
import com.kamila.food.domain.service.VendaQueryService;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

@Repository
public class VendaQueryServiceImpl implements VendaQueryService {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter vendaDiariaFilter) {
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

        query.select(selection);
        query.groupBy(functionDateDataCriacao);

        return manager.createQuery(query).getResultList();
    }

}
