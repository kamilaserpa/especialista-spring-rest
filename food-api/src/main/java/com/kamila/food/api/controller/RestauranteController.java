package com.kamila.food.api.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.kamila.food.api.assembler.RestauranteInputDisassembler;
import com.kamila.food.api.assembler.RestauranteModelAssembler;
import com.kamila.food.api.model.RestauranteModel;
import com.kamila.food.api.model.input.RestauranteInput;
import com.kamila.food.api.model.view.RestauranteView;
import com.kamila.food.api.openapi.controller.RestauranteControllerOpenApi;
import com.kamila.food.domain.exception.CidadeNaoEncontradaException;
import com.kamila.food.domain.exception.CozinhaNaoEncontradaException;
import com.kamila.food.domain.exception.NegocioException;
import com.kamila.food.domain.exception.RestauranteNaoEncontradoException;
import com.kamila.food.domain.model.Restaurante;
import com.kamila.food.domain.repository.RestauranteRepository;
import com.kamila.food.domain.service.CadastroRestauranteService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/restaurantes", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteController implements RestauranteControllerOpenApi {

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

//	@GetMapping
//	public MappingJacksonValue listar(@RequestParam(required = false) String projecao) {
//		List<Restaurante> restaurantes = restauranteRepository.findAll();
//		List<RestauranteModel> restaurantesModel = restauranteModelAssembler.toCollectionModel(restaurantes);
//
//		MappingJacksonValue restaurantesWrapper = new MappingJacksonValue(restaurantesModel);
//		restaurantesWrapper.setSerializationView(RestauranteView.Resumo.class);
//
//		if ("apenas-nome".equals(projecao)) { // para evitar NullPointerException caso parametro não recebido
//			restaurantesWrapper.setSerializationView(RestauranteView.ApenasNome.class);
//		} else if ("completo".equals(projecao)) {
//			restaurantesWrapper.setSerializationView(null);
//		}
//
//		return restaurantesWrapper;
//	}

    // Dois métodos semelhantes com serialização resumida dependente do parâmetro "projecao"
    @JsonView(RestauranteView.Resumo.class)
    @GetMapping
    public List<RestauranteModel> listarResumido() {
        return restauranteModelAssembler.toCollectionModel(restauranteRepository.findAll());
    }

    @JsonView(RestauranteView.ApenasNome.class)
    @GetMapping(params = "projecao=apenas-nome")
    public List<RestauranteModel> listarApenasNomes() {
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

    @PutMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativarMultiplos(@RequestBody List<Long> idsRestaurantes) {
        try {
            cadastroRestauranteService.ativar(idsRestaurantes);
        } catch (RestauranteNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativarMultiplos(@RequestBody List<Long> idsRestaurantes) {
        try {
            cadastroRestauranteService.inativar(idsRestaurantes);
        } catch (RestauranteNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{restauranteId}/abertura")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void abrir(@PathVariable Long idRestaurante) {
        cadastroRestauranteService.abrir(idRestaurante);
    }

    @PutMapping("/{restauranteId}/fechamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void fechar(@PathVariable Long restauranteId) {
        cadastroRestauranteService.fechar(restauranteId);
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
