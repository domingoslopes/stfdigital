package br.jus.stf.plataforma.notificacoes.domain.exception;

/**
 * @author Lucas.Rodrigues
 *
 */
public class NotificacaoServiceException extends Exception {

	private static final long serialVersionUID = 5531669082139851891L;
	
	public NotificacaoServiceException() {
		
	}

	public NotificacaoServiceException(String message) {
		super(message);
	}
	
	public NotificacaoServiceException(Throwable t) {
		super(t);
	}
	
	public NotificacaoServiceException(String message, Throwable t) {
		super(message, t);
	}

}
