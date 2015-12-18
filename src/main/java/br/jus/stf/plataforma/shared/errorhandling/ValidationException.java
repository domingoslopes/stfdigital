package br.jus.stf.plataforma.shared.errorhandling;

import java.util.List;

import org.springframework.validation.ObjectError;

/**
 * Exceção para erros de validação.
 * 
 * @author Tomas.Godoi
 *
 */
public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private List<ObjectError> validationErrors;

	public ValidationException(List<ObjectError> errors) {
		this.validationErrors = errors;
	}

	public List<ObjectError> getValidationErrors() {
		return validationErrors;
	}

	public void setValidationErrors(List<ObjectError> validationErrors) {
		this.validationErrors = validationErrors;
	}
	
}
