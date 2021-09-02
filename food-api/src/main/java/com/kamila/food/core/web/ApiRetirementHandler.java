package com.kamila.food.core.web;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * Intercepta requisições da API e desabilita V1.
 * Habilitado na implementação de WebMvcConfigurer:
 *    @Override
 *    public void addInterceptors(InterceptorRegistry registry) {
 *        registry.addInterceptor(apiRetirementHandler);
 *    }
 */
@Component
public class ApiRetirementHandler extends HandlerInterceptorAdapter {

    /*
    Adiciona header nas respostas de requests para versão V1
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getRequestURI().startsWith("/v1/")) {
            response.setStatus(HttpStatus.GONE.value()); // Retorna 410 - Recurso não existe mais no servidor
            return false; // false - interrompe a execução do método inutilizando a versão V1
        }
        return true;
    }

}
