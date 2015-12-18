package br.jus.stf.plataforma.shared.errorhandling;

import java.util.List;

public class ErrorMessageDto {

	private List<ErrorDto> errors;

	public ErrorMessageDto(List<ErrorDto> errors) {
		this.errors = errors;
	}

	public List<ErrorDto> getErrors() {
		return errors;
	}

	public void setErrors(List<ErrorDto> errors) {
		this.errors = errors;
	}

}
