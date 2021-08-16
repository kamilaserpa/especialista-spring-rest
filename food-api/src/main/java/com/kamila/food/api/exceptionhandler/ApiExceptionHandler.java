package com.kamila.food.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import com.kamila.food.core.validation.ValidacaoException;
import com.kamila.food.domain.exception.EntidadeEmUsoException;
import com.kamila.food.domain.exception.EntidadeNaoEncontradaException;
import com.kamila.food.domain.exception.NegocioException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	public static final String MSG_ERRO_GENERICA_USUARIO_FINAL = "Ocorreu um erro interno inesperado no sistema. "
			+ "Tente novamente e se o problema persistir, entre em contato com o administrador do sistema.";
	
	@Autowired
	private MessageSource messageSource;
	
	// CUSTOMIZANDO EXCEÇÃO PADRÃO DO SPRING (RESPONSE ENTITY EXCEPTION HANDLER)
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable
	(HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		/* Esse exception handler HttpMediaTypeNotAcceptableException, chama o handleExceptionInternal passando
		null no parâmetro body. Esse método cria um body default com uma mensagem padrão.
		Porém essa requisição acontece quando o cliente solitica arquivo image/png por exemplo,
		e o exceptionHandlerInternal tenta retornar uma representação json do problema na resposta.
		Que o no caso o consumidor não aceita. Portanto o body Problem desse caso não retornará um body,
		apenas status Code. Não altera resultado no consumidor e não cria exceção no código.
		 */

		return ResponseEntity.status(status)
				.headers(headers)
				.build();
	}

	@Override
	protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
	    return handleValidationInternal(ex, ex.getBindingResult(), headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		Throwable rootCause =  ExceptionUtils.getRootCause(ex);
		
		if (rootCause instanceof InvalidFormatException) {
			return handleInvalidFormat((InvalidFormatException) rootCause, headers, status, request);
		}
		
		if (rootCause instanceof PropertyBindingException) {
			return handlePropertyBinding((PropertyBindingException) rootCause, headers, status, request);
		}
		
		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		String detail = "O corpo da requisição está inválido. Verifique erro de sintaxe.";
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
				.build();

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	
	private ResponseEntity<Object> handleInvalidFormat(InvalidFormatException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		String path = joinPath(ex.getPath());

		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;

		String detail = String.format(
				"A propriedade '%s' recebeu o valor '%s', que é de um tipo inválido. "
						+ "Corrija e informe um valor compatível com o tipo %s.",
				path, ex.getValue(), ex.getTargetType().getSimpleName());

		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
				.build();

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}

	
	private ResponseEntity<Object> handlePropertyBinding(PropertyBindingException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		// Método joinPath para reaproveitar em todos os métodos que precisam
	    // concatenar os nomes das propriedades (separando por ".")
	    String path = joinPath(ex.getPath());
				
		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		
		String detail = String.format("A propriedade '%s' não existe. "
				+ "Corrija ou remova a propriedade e tente novamente.", path);

		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
				.build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		if (ex instanceof MethodArgumentTypeMismatchException) {
			return handleMethodArgumentTypeMismatch((MethodArgumentTypeMismatchException) ex, headers, status,
					request);
		}

		return super.handleTypeMismatch(ex, headers, status, request);
	}

	private ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ProblemType problemType = ProblemType.PARAMETRO_INVALIDO;

		String detail = String.format(
				"O parâmetro de url '%s' recebeu o valor '%s' que é de um tipo inválido. "
						+ "Corrija e informe um valor compatível com o tipo '%s'",
				ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
				.build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;

		String detail = String.format("O recurso '%s', que você tentou acessar, é inexistente.",
						ex.getRequestURL());

		Problem problem = createProblemBuilder(status, problemType, detail)
						.userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
						.build();
				
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
	    return handleValidationInternal(ex, ex.getBindingResult(), headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ProblemType problemType = ProblemType.PARAMETRO_INVALIDO;

		String detail = String.format("Problema no parâmetro '%s' com o tipo '%s.", ex.getParameterName(), ex.getParameterType());

		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
				.build();
	
		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		
		String[] methods = ex.getSupportedMethods();
		
		String detail = String.format("Métodos suportados: '%s.", String.join(", ", methods));

		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
				.build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	
	// CAPTURANDO EXCEÇÕES NÃO TRATADAS
	
	/**
	 * Captura exceções não tratadas especificamente
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
		
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		ProblemType problemType = ProblemType.ERRO_DE_SISTEMA;
		String detail = MSG_ERRO_GENERICA_USUARIO_FINAL;

		ex.printStackTrace();

		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(detail)
				.build();

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	// CUSTOMIZANDO EXCEÇÕES CRIADAS NO PROJETO
	
	/**
	 * Customiza corpo da resposta de erro EntidadeNaoEncontradaException para Problem, e HttpStatus 404
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException ex,
			WebRequest request) {

		HttpStatus status = HttpStatus.NOT_FOUND;
		ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
		String detail = ex.getMessage();

		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(detail)
				.build();

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	/**
	 * Customizando representação do erro de NegocioException para classe Problema, e HttpStatus 400
	 */
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> handleNegocio(NegocioException ex,  WebRequest request) {
		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		ProblemType problemType = ProblemType.ERRO_NEGOCIO;
		String detail = ex.getMessage();
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(detail)
				.build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> handleEntidadeEmUso(EntidadeEmUsoException ex, WebRequest request) {
		
		HttpStatus status = HttpStatus.CONFLICT;
		ProblemType problemType = ProblemType.ENTIDADE_EM_USO;
		String detail = ex.getMessage();
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(detail)
				.build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(ValidacaoException.class)
	public ResponseEntity<?> handleValidacaoException(ValidacaoException ex, WebRequest request) {
		return handleValidationInternal(ex, ex.getBindingResult(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	private ResponseEntity<Object> handleValidationInternal(Exception ex, BindingResult bindingResult, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		ProblemType problemType = ProblemType.DADOS_INVALIDOS;

		String detail = "Um ou mais campos estão inválidos. Preencha corretamente e tente novamente.";

		// Inserindo na resposta os campos inválidos e a mensagem indicando o erro de validação
		List<Problem.Object> problemObjects = bindingResult.getAllErrors().stream()
				.map(objectError -> {
					String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
					
					String name = objectError.getObjectName();
					
					if (objectError instanceof FieldError) {
						name = ((FieldError) objectError).getField();
					}
					
					return Problem.Object.builder()				
						.name(name)
						.userMessage(message)
						.build();
				})
				.collect(Collectors.toList());
		
		Problem problem = createProblemBuilder(status, problemType, detail)
						.userMessage(detail)
						.objects(problemObjects)
						.build();
				
		return handleExceptionInternal(ex, problem, headers, status, request);
		
	}
	
	// HELPERS
	
	/**
	 * Se recebe uma string como body, instancia um Problema a insere o body na mensagem.
	 * Caso contrário, insere um problema no body com a ReasonPhrase na mensagem
	 */
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		if (body == null) {
			body = Problem.builder()
					.title(status.getReasonPhrase())
					.status(status.value())
					.timestamp(OffsetDateTime.now())
					.userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
					.build();
			
		} else if (body instanceof String) {
			body = Problem.builder()
					.title((String) body)
					.status(status.value())
					.timestamp(OffsetDateTime.now())
					.userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
					.build();
		}

		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
	
	private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType problemType, String detail) {
		return Problem.builder()
				.status(status.value())
				.type(problemType.getUri())
				.title(problemType.getTitle())
				.timestamp(OffsetDateTime.now())
				.detail(detail);
	}
	
	/**
	 * Concatena nomes das propriedades, separando-as por "."
	 * @param references
	 * @return
	 */
	private String joinPath(List<Reference> references) {
	    return references.stream()
	        .map(ref -> ref.getFieldName())
	        .collect(Collectors.joining("."));
	}   

}
