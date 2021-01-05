package com.kamila.food.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.kamila.food.FoodApiApplication;
import com.kamila.food.domain.model.Restaurante;
import com.kamila.food.domain.repository.RestauranteRepository;

/*
 * Classe main criada para ser executada como JavaApplication, a fim de teste de findAll
 */
public class ConsultaRestauranteMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(FoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);

		RestauranteRepository restauranteRepository = applicationContext.getBean(RestauranteRepository.class);

		List<Restaurante> restaurantes = restauranteRepository.listar();

		for (Restaurante r : restaurantes) {
			System.out.printf("%s - %f - %s\n", r.getNmRestaurante(), r.getTaxaFrete(), r.getCozinha().getNmCozinha());
		}
	}

}
