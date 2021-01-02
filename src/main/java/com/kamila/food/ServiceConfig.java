package com.kamila.food;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kamila.food.di.service.AtivacaoClienteService;

@Configuration
public class ServiceConfig {

	@Bean(initMethod = "init", destroyMethod = "destroy")
	public AtivacaoClienteService ativacaoClienteService() {
		return new AtivacaoClienteService();
	}
	
}
