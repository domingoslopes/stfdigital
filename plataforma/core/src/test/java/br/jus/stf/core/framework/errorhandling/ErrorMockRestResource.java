package br.jus.stf.core.framework.errorhandling;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Mock para simular o lançamento de exceções por serviços Rest. Usado para testar
 * o mecanismo de tratamento de exceções implementado pelo {@link GlobalControllerExceptionHandler}.
 * 
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 21.12.2015
 * 
 * @see ErrorHandlingIntegrationTests
 */
@RestController
@RequestMapping("/api/errors")
public class ErrorMockRestResource {

    /**
     * Lança os dois tipos de erros mais comum: error interno (500) e requisição inválida (400).
     * 
     * @param code indica que erro deve ser lançado
     */
    @RequestMapping(value = "/{code}", method = RequestMethod.POST)
    public void handle(@PathVariable int code) {
        switch (code) {
            case 400: {
                throw new IllegalArgumentException();
            }
            case 500: {
                throw new NullPointerException();
            }
        }
    }

}
