package com.kamila.food.api;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;

/**
 * Implementação HATEOAS
 */
@UtilityClass // Classe utilitária não pode ser estendida, e os métodos static. Anotação não necessária mas indicada.
public class ResourceUriHelper {


    /**
     * Adiciona header 'Location' com URI para recurso de busca de objeto por id
     * @param resourceId
     */
    public static void addUriInResponseHeader(Object resourceId) {
        // fromCurrentRequestUri: a partir da URI da requisição, ex.: http://localhost:8080/cidade
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(resourceId)
                .toUri();

        HttpServletResponse response = ((ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes()).getResponse();

        // Adicionando URI no header
        response.setHeader(HttpHeaders.LOCATION, uri.toString());
    }

}
