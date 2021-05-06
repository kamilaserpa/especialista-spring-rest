package com.kamila.food.domain.service;

import com.kamila.food.domain.filter.VendaDiariaFilter;
import com.kamila.food.domain.model.dto.VendaDiaria;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Outra implementação sugerida por tutor utilizando Jpql
 */
@Repository
public class VendaQueryServiceJpql implements VendaQueryService {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro) {
        StringBuilder jpql = new StringBuilder(
                "SELECT new com.kamila.food.domain.model.dto.VendaDiaria(" +
                        "FUNCTION('date', p.dataCriacao), COUNT(p.id), SUM(p.valorTotal)) " +
                        "FROM Pedido p ");

        String where = "";
        boolean setDataCriacaoInicio = false;
        boolean setDataCriacaoFim = false;
        boolean setRestauranteId = false;

        if (filtro.getDataCriacaoInicio() != null) {
            where += "p.dataCriacao >= :dataCriacaoInicio ";
            setDataCriacaoInicio = true;
        }

        if (filtro.getDataCriacaoFim() != null) {
            if (setDataCriacaoInicio) {
                where += "AND ";
            }

            where += "p.dataCriacao <= :dataCriacaoFim ";
            setDataCriacaoFim = true;
        }

        if (filtro.getRestauranteId() != null) {
            if (setDataCriacaoInicio || setDataCriacaoFim) {
                where += "AND ";
            }

            where += "p.restaurante.id = :restauranteId ";
            setRestauranteId = true;
        }

        if (!where.isBlank()) {
            jpql.append("WHERE ").append(where);
        }

        jpql.append("GROUP BY FUNCTION('date', p.dataCriacao)");

        TypedQuery<VendaDiaria> query = manager.createQuery(jpql.toString(), VendaDiaria.class);

        if (setDataCriacaoInicio) {
            query.setParameter("dataCriacaoInicio", filtro.getDataCriacaoInicio());
        }

        if (setDataCriacaoFim) {
            query.setParameter("dataCriacaoFim", filtro.getDataCriacaoFim());
        }

        if (setRestauranteId) {
            query.setParameter("restauranteId", filtro.getRestauranteId());
        }

        return query.getResultList();
    }

}
