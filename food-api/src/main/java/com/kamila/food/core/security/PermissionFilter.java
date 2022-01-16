package com.kamila.food.core.security;

import org.slf4j.MDC;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PermissionFilter extends BasicAuthenticationFilter {

    public PermissionFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {


        MDC.put("token", request.getHeader("Authorization"));
        MDC.put("x.correlation.id", request.getHeader("x-correlation-id"));

        filterChain.doFilter(request, response);
    }

}
