package br.jus.stf.plataforma.shared.actions.support;

public class ActionException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ActionException(String message, Throwable originalException) {
		super(message, originalException);
	}

}
