package com.kamila.food;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CadastroCozinhaIT {

	@LocalServerPort
	private int port;
	
	/**
	 * Método executado antes de cada método @Test
	 */
	@Before
	public void setUp() {
		/*
		 * Habilitando logging da requisição e da resposta para caso a requisição falhar.
		 * Auxiliando na identificação da causa de erro.
		 */
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/cozinhas";
	}
	
	@Test
	public void deveRetornarStatus200_QuandoConsultarCozinhas() {
		given() // Dado que
			.accept(ContentType.JSON)
		.when() // Quando
			.get()
		.then() // Então
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void deveConter4Cozinhas_QuandoConsultarCozinhas() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.body("", hasSize(4)) // 4 objetos dentro do array
			.body("nome", Matchers.hasItems("Indiana", "Tailandesa")); // Deve possuir estes itens no atributo nome
	}
	
}
