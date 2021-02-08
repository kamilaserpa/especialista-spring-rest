package com.kamila.food.api.exceptionhandler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.kamila.food.domain.exception.EntidadeEmUsoException;
import com.kamila.food.domain.exception.EntidadeNaoEncontradaException;
import com.kamila.food.domain.exception.NegocioException;

@ControllerAdvice
public class ApiExceptionHandler {

	/**
	 *  Customiza corpo da resposta. Caso haja alguma exceção dentro de @ExceptionHandler está será interceptada por este método,
	 *  no caso a resposta será um ResponseEntity. HttpStatus 404.
	 * @param e
	 * @return
	 */
	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> tratarEntidadeNaoEncontradaException(EntidadeNaoEncontradaException e) {
		
		Problema problema = Problema.builder()
				.datahora(LocalDateTime.now())
				.mensagem(e.getMessage())
				.build();
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problema);
	}
	
	/**
	 * Alterando representação do erro de NegocioException para classe Problema, e httpStatus 400
	 * @param e
	 * @return
	 */
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> tratarNegocioException(NegocioException e) {
		
		Problema problema = Problema.builder()
				.datahora(LocalDateTime.now())
				.mensagem(e.getMessage())
				.build();
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problema);
	}
	
	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public ResponseEntity<?> tratarHttpMediaTypeNotSupportedException() {
		
		Problema problema = Problema.builder()
				.datahora(LocalDateTime.now())
				.mensagem("O tipo de mídia não é aceito")
				.build();
		
		return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(problema);
	}
	
	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> tratarEntidadeEmUsoException(EntidadeEmUsoException e) {
		
		Problema problema = Problema.builder()
				.datahora(LocalDateTime.now())
				.mensagem(e.getMessage())
				.build();
		
		return ResponseEntity.status(HttpStatus.CONFLICT).body(problema);
	}
	
}
