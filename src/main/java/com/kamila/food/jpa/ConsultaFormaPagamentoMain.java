package com.kamila.food.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.kamila.food.FoodApiApplication;
import com.kamila.food.domain.model.FormaPagamento;
import com.kamila.food.domain.repository.FormaPagamentoRepository;

/*
 * Classe main criada para ser executada como JavaApplication, a fim de teste de findAll
 */
public class ConsultaFormaPagamentoMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(FoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);

		FormaPagamentoRepository formaPagamentoRepository = applicationContext.getBean(FormaPagamentoRepository.class);

		List<FormaPagamento> todasFormasPagamentos = formaPagamentoRepository.listar();

		for (FormaPagamento formaPagamento : todasFormasPagamentos) {
			System.out.println(formaPagamento.getDescricao());
		}
	}

}
