package br.jus.stf.plataforma.shared.certification.support;

public class AssinaturaExternaException extends Exception {

	private static final long serialVersionUID = -8297840483459027345L;

	public AssinaturaExternaException(Throwable t) {
		super(t);
	}

	public AssinaturaExternaException(String msg) {
		super(msg);
	}

	public AssinaturaExternaException(String msg, Throwable t) {
		super(msg, t);
	}

}
