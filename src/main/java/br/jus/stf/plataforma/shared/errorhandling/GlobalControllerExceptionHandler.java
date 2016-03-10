package br.jus.stf.plataforma.shared.errorhandling;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.jus.stf.plataforma.shared.actions.support.ActionException;

/**
 * Intercepta e processa qualquer exceção lançada pelos serviços da API e as trata
 * adequadamente para que o cliente possa interpretá-la corretamente.
 * 
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 17.07.2015
 */
@ControllerAdvice
class GlobalControllerExceptionHandler {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);
    
    /**
     * Captura as exceções lançadas para indicar parâmetros ilegais e retorna
     * o código 400 na resposta ao cliente.
     * 
     * @param exception a exceção indicando parâmetros inválidos
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public ErrorMessageDto handleIllegals(IllegalArgumentException exception) {
    	LOGGER.error(exception.getClass().getName(), exception);
    	
    	return new ErrorMessageDto(Arrays.asList(new ErrorDto(exception.getMessage())));
    }
    
    /**
     * Captura as exceções do mecanismo de ações.
     * 
     * @param exception
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ActionException.class)
    @ResponseBody
    public ErrorMessageDto handleActionExceptions(ActionException exception) {
    	LOGGER.error(exception.getClass().getName(), exception);
    	
    	return new ErrorMessageDto(Arrays.asList(new ErrorDto(exception.getCause().getMessage())));
    }
    
    /**
     * Captura exceções ocorridas devido a problemas de validação.
     * 
     * @param exception a exceção indicando parâmetros inválidos
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    @ResponseBody
    public ErrorMessageDto handleInvalids(ValidationException exception) {
    	LOGGER.error(exception.getClass().getName(), exception);
    	
    	List<ErrorDto> errors = new ArrayList<>();
    	
    	for (ObjectError oe : exception.getValidationErrors()) {
    		ErrorDto error = new ErrorDto();
    		if (oe instanceof FieldError) {
    			FieldError fe = (FieldError)oe;
    			error.setName(fe.getField());
    			error.setMessage(fe.getDefaultMessage());
    			error.setType("field");
    		} else {
	    		error.setName(oe.getObjectName());
	    		error.setType("object");
	    		error.setMessage(oe.getDefaultMessage());
    		}
    		errors.add(error);
    	}

    	return new ErrorMessageDto(errors);
    }
    
    /**
     * Todas as exceções não tratadas nos serviços da API serão repassadas a 
     * esse método. Ele apenas extrairá algumas informações mais relevantes
     * para melhor visualização pelo usuário.
     * 
     * @param exception a exceção não tratada
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public void handle(Exception exception) {
    	LOGGER.error(exception.getClass().getName(), exception);
    }
    
}