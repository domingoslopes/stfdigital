package br.jus.stf.plataforma.shared.errorhandling;

public class ErrorDto {

	private String name;
	private String type;
	private String message;

	public ErrorDto() {
	}

	public ErrorDto(String message) {
		this.message = message;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
