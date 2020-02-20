package com.kamila.food;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kamila.food.di.modelo.Cliente;
import com.kamila.food.di.service.AtivacaoClienteService;

@Controller
public class MeuPrimeiroController {

	private AtivacaoClienteService ativacaoClienteService;

	public MeuPrimeiroController(AtivacaoClienteService ativacaoClienteService) {
		this.ativacaoClienteService = ativacaoClienteService;
	}
	
	@GetMapping("/hello")
	@ResponseBody
	public String hello() {
		Cliente joao = new Cliente("Joao", "joao@teste.com", "85123456789");
		
		ativacaoClienteService.ativar(joao);
		
		return "Hello!";
	}
	
}
