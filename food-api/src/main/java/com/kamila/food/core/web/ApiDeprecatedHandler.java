package com.kamila.food.core.web;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Intercepta requisições da API
@Component
public class ApiDeprecatedHandler extends HandlerInterceptorAdapter {

    /*
    Adiciona header nas respostas de requests para versão V1
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getRequestURI().startsWith("/v1")) {
            response.addHeader("X-Food-Deprecated",
                    "Essa versão da API está depreciada e deixará de existir a partir de 01/01/20XX."
                            + "Use a versão mais atual da API.");
        }
        return true; // false interrompe a execução do método
    }
    
}
