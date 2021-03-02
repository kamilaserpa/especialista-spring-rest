package com.kamila.food;

import static org.assertj.core.api.Assertions.assertThat;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.kamila.food.domain.exception.CozinhaNaoEncontradaException;
import com.kamila.food.domain.exception.EntidadeEmUsoException;
import com.kamila.food.domain.model.Cozinha;
import com.kamila.food.domain.service.CadastroCozinhaService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CadastroCozinhaIT {

	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;
	
	@Test
	public void deveAtribuirId_QuandoCadastrarCozinhaComDadosCorretos() {
		// Cenário
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome("Chinesa");
		
		// Ação
		novaCozinha = cadastroCozinhaService.salvar(novaCozinha);
		
		// Validação
		assertThat(novaCozinha).isNotNull();
		assertThat(novaCozinha.getId()).isNotNull();
	}

	/**
	 * Flexibilidade maior em nome de métodos de teste, pois podem ser longos.
	 */
	@Test(expected = ConstraintViolationException.class)
	public void deveFalhar_QuandoCadastroCozinhaSemNome() {
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome(null);
		
		novaCozinha = cadastroCozinhaService.salvar(novaCozinha);
	}

	@Test(expected = EntidadeEmUsoException.class)
	public void deveFalhar_QuandoExcluirCozinhaEmUso() {
		cadastroCozinhaService.remover(1L);
	}
	
	@Test(expected  = CozinhaNaoEncontradaException.class)
	public void deveFalhar_QuandoExcluirCozinhaInexistente() {
		cadastroCozinhaService.remover(100L);		
	}
	
}
