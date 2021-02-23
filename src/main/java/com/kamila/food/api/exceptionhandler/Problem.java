package com.kamila.food.api.exceptionhandler;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Getter;

@JsonInclude(Include.NON_NULL)
@Getter
@Builder
public class Problem {

	private Integer status;
	private String type;
	private String title;
	private String detail;
	
	private String userMessage; // Mensagem destinada ao usuário final
	private LocalDateTime timestamp;
	private List<Object> objects;
	
	@Getter
	@Builder
	public static class Object {
		
		private String name;
		private String userMessage;
		
	}
	
}
