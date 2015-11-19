package br.jus.stf.plataforma.shared.certification.validation;

public class CertificateValidationException extends Exception {

	private static final long serialVersionUID = 6529159175686438591L;

	public CertificateValidationException(String msg, Throwable e) {
		super(msg, e);
	}

}
