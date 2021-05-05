package com.kamila.food.domain.service;

import com.kamila.food.domain.filter.VendaDiariaFilter;
import com.kamila.food.domain.model.dto.VendaDiaria;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

public interface VendaQueryService {

    List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter vendaDiariaFilter);

}
