package com.kamila.food.api.exceptionhandler;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Problema {

	private LocalDateTime datahora;
	
	private String mensagem;
	
}
