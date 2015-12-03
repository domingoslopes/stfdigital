package br.jus.stf.plataforma.shared.certification.infra.itext;

public class DocumentValidationException extends Exception {

	private static final long serialVersionUID = 1L;

	public DocumentValidationException(String message) {
		super(message);
	}

	public DocumentValidationException() {
		super();
	}

	public DocumentValidationException(String message, Throwable cause) {
		super(message, cause);
	}

	public DocumentValidationException(Throwable cause) {
		super(cause);
	}

}
