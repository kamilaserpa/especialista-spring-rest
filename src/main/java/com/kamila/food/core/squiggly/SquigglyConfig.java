package com.kamila.food.core.squiggly;

import java.util.Arrays;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bohnman.squiggly.Squiggly;
import com.github.bohnman.squiggly.web.RequestSquigglyContextProvider;
import com.github.bohnman.squiggly.web.SquigglyRequestFilter;

@Configuration
public class SquigglyConfig {

	/**
	 * Toda requisição da API será interceptada pelo Squiggly
	 * @param objectMapper
	 * @return
	 */
	@Bean
	public FilterRegistrationBean<SquigglyRequestFilter> squigglyRequestFilter(ObjectMapper objectMapper) {
		Squiggly.init(objectMapper, new RequestSquigglyContextProvider("campos", null));
		
		// Habilitando interceptador apenas para os endpoint listados
		var urlPatterns = Arrays.asList("/pedidos/*", "/restaurantes/*");
		
		var filterRegistrationBean = new FilterRegistrationBean<SquigglyRequestFilter>();
		filterRegistrationBean.setFilter(new SquigglyRequestFilter());
		filterRegistrationBean.setOrder(1);
		filterRegistrationBean.setUrlPatterns(urlPatterns);
		
		return filterRegistrationBean;
	}
}
