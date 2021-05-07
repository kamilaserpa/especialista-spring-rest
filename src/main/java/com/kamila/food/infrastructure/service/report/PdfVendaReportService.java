package com.kamila.food.infrastructure.service.report;

import com.kamila.food.domain.filter.VendaDiariaFilter;
import com.kamila.food.domain.service.VendaReportService;
import org.springframework.stereotype.Service;

@Service
public class PdfVendaReportService implements VendaReportService {

    @Override
    public byte[] emitirVendasDiarias(VendaDiariaFilter filter, String timeOffset) {
        return new byte[0];
    }

}
