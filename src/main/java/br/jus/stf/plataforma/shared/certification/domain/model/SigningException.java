package br.jus.stf.plataforma.shared.certification.domain.model;

public class SigningException extends Exception {

	private static final long serialVersionUID = -8297840483459027345L;

	public SigningException(Throwable t) {
		super(t);
	}

	public SigningException(String msg) {
		super(msg);
	}

	public SigningException(String msg, Throwable t) {
		super(msg, t);
	}

}
