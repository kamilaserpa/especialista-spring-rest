package com.kamila.food.core.data;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PageableTranslator {

    /*
    Converte kays recebidas pelo pageable para nomes de atributos da propriedade.
    Exemplo em PedidoController
     */
    public static Pageable translate(Pageable pageable, Map<String, String> fieldsMapping) {
        List<Sort.Order> orders = pageable.getSort().stream()
                .filter(order -> fieldsMapping.containsKey(order.getProperty())) // ignora propriedade inexistente enviada pelo consumidor da aplicação
                .map(order ->
                        new Sort.Order(order.getDirection(),
                                fieldsMapping.get(order.getProperty())))
                .collect(Collectors.toList());

        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by(orders));
    }
}
