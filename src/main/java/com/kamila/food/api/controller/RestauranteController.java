package com.kamila.food.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.kamila.food.api.assembler.RestauranteInputDisassembler;
import com.kamila.food.api.assembler.RestauranteModelAssembler;
import com.kamila.food.api.model.RestauranteModel;
import com.kamila.food.api.model.input.RestauranteInput;
import com.kamila.food.domain.exception.CidadeNaoEncontradaException;
import com.kamila.food.domain.exception.CozinhaNaoEncontradaException;
import com.kamila.food.domain.exception.NegocioException;
import com.kamila.food.domain.model.Restaurante;
import com.kamila.food.domain.repository.RestauranteRepository;
import com.kamila.food.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroRestauranteService cadastroRestauranteService;

//	@Autowired
//	private SmartValidator validator;
	
	@Autowired
	private RestauranteModelAssembler restauranteModelAssembler;
	
	@Autowired
	private RestauranteInputDisassembler restauranteInputDisassembler;
	
	@GetMapping
	public List<RestauranteModel> listar() {
		return restauranteModelAssembler.toCollectionModel(restauranteRepository.findAll());
	}

	@GetMapping("/{idRestaurante}")
	public RestauranteModel buscar(@PathVariable Long idRestaurante) {
		Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(idRestaurante);
		return restauranteModelAssembler.toModel(restaurante);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RestauranteModel salvar(@RequestBody @Valid RestauranteInput restauranteInput) {
		try {
			Restaurante restaurante = restauranteInputDisassembler.toDomainObject(restauranteInput);
			return restauranteModelAssembler.toModel(cadastroRestauranteService.salvar(restaurante));
		} catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	@PutMapping("/{idRestaurante}")
	@ResponseStatus(HttpStatus.OK)
	public RestauranteModel atualizar(@PathVariable Long idRestaurante, @RequestBody @Valid RestauranteInput restauranteInput) {
		try {
			
			Restaurante restauranteAtual = cadastroRestauranteService.buscarOuFalhar(idRestaurante);
			
			restauranteInputDisassembler.copyToDomainObject(restauranteInput, restauranteAtual);

			return restauranteModelAssembler.toModel(cadastroRestauranteService.salvar(restauranteAtual));
		} catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	@DeleteMapping("/{idRestaurante}")
	public void remover(@PathVariable Long idRestaurante) {
		cadastroRestauranteService.remover(idRestaurante);
	}
	
	@PutMapping("/{idRestaurante}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativar(@PathVariable Long idRestaurante) {
		// Opção por PUT por ser um método idenpotente, ainda que executado várias vezes provoca o mesmo resultado
		cadastroRestauranteService.ativar(idRestaurante);
	}
	
	@DeleteMapping("/{idRestaurante}/inativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativar(@PathVariable Long idRestaurante) {
		cadastroRestauranteService.inativar(idRestaurante);
	}
	
	/*
   // Comentando método de atualização parcial, pelo visto de que não é interessante mantê-lo por hora.
	@PatchMapping("/{idRestaurante}")
	@ResponseStatus(HttpStatus.OK)
	public RestauranteModel atualizarParcial(@PathVariable Long idRestaurante, @RequestBody Map<String, Object> campos,
			HttpServletRequest request) {

		Restaurante restauranteAtual = cadastroRestauranteService.buscarOuFalhar(idRestaurante);

		merge(campos, restauranteAtual, request);
		validate(restauranteAtual, "restaurante");
		
		return atualizar(idRestaurante, restauranteAtual);
	}

	private void validate(Restaurante restaurante, String objectName) {
		
		BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurante, objectName);
		// Aciona validação em objecto programaticamente
		validator.validate(restaurante, bindingResult);
		
		if (bindingResult.hasErrors()) {
			throw new ValidacaoException(bindingResult);
		}
	}

	private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino, HttpServletRequest request) {
		ServletServerHttpRequest servletServerHttpRequest = new ServletServerHttpRequest(request);
		
		try {	
			// Utilizando  ObjectMapper para criar objeto tipado Restaurante
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true); // Lança exceção caso recebeu objeto não desserializável
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true); // Padrão do ObjectMapper
			
			Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);
			
			dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
				// findField: Retorna instancia de um campo
				Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
				field.setAccessible(true);
				
				// GetField: Captura o valor do campo
				Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
				
				ReflectionUtils.setField(field, restauranteDestino, novoValor);
			});
		} catch (IllegalArgumentException e) {
			Throwable rootCause = ExceptionUtils.getRootCause(e);
			throw new HttpMessageNotReadableException(e.getMessage(), rootCause, servletServerHttpRequest);
		}
	}
	*/	

}
