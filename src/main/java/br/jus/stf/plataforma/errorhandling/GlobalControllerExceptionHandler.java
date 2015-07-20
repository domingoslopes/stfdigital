package br.jus.stf.plataforma.errorhandling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

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
	
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
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
    	logger.error(exception.getClass().getName(), exception);
    }
    
}