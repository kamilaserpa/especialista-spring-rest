package com.kamila.food.domain.service;

import com.kamila.food.domain.filter.VendaDiariaFilter;

public interface VendaReportService {

    byte[] emitirVendasDiarias(VendaDiariaFilter filter, String timeOffset);

}
