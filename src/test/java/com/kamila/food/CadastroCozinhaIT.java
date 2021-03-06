package com.kamila.food;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.kamila.food.domain.model.Cozinha;
import com.kamila.food.domain.repository.CozinhaRepository;
import com.kamila.food.util.DatabaseCleaner;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroCozinhaIT {

	@LocalServerPort
	private int port;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
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
		
		/**
		 *  Antes de cada teste os dados do banco serão deletados e o método prepararDados irá inserir novos valores.
		 *  Assim os métodos de teste são executados sobre a mesma base de dados e não dependem de uma sequência determinada
		 *  ou da execução de outro método
		 */
		databaseCleaner.clearTables();
		prepararDados();
	}
	
	@Test
	public void testeRetornarStatus200_QuandoConsultarCozinhas() {
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
	
	@Test
	public void deveRetornarStatus201_QuandoCadastrarCozinha() {
		given()
			.body("{ \"nome\": \"Chinesa\" }")
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	public void deveRetornarRespostaEStatusCorretos_QuandoConsultarCozinhaExistente() {
		given()
			.pathParam("cozinhaId", 2)
			.accept(ContentType.JSON)
		.when()
			.get("/{cozinhaId}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("nome", equalTo("Americana"));
	}
	
	@Test
	public void deveRetornarStatus404_QuandoConsultarCozinhaInexistente() {
		given()
		.pathParam("cozinhaId", 200)
		.accept(ContentType.JSON)
		.when()
		.get("/{cozinhaId}")
		.then()
		.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	private void prepararDados() {
		Cozinha cozinha1 = new Cozinha();
		cozinha1.setNome("Tailandesa");
		cozinhaRepository.save(cozinha1);
		
		Cozinha cozinha2 = new Cozinha();
		cozinha2.setNome("Americana");
		cozinhaRepository.save(cozinha2);
		
		Cozinha cozinha3 = new Cozinha();
		cozinha3.setNome("Indiana");
		cozinhaRepository.save(cozinha3);
		
		Cozinha cozinha4 = new Cozinha();
		cozinha4.setNome("Argentina");
		cozinhaRepository.save(cozinha4);
	}
	
}
