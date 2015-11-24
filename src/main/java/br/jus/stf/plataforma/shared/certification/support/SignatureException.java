package br.jus.stf.plataforma.shared.certification.support;

public class SignatureException extends Exception {

	private static final long serialVersionUID = -8297840483459027345L;

	public SignatureException(Throwable t) {
		super(t);
	}

	public SignatureException(String msg) {
		super(msg);
	}

	public SignatureException(String msg, Throwable t) {
		super(msg, t);
	}

}
