package br.jus.stf.plataforma.shared.certification.domain.model.certificate;

public class CertificateValidationException extends Exception {

	private static final long serialVersionUID = 1L;

	public CertificateValidationException(String msg, Throwable e) {
		super(msg, e);
	}

}
