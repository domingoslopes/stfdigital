package br.jus.stf.core.shared.eventos;

import br.jus.stf.core.framework.domaindrivendesign.DomainEvent;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 20.02.2016
 */
public class RemessaRegistrada implements DomainEvent<RemessaRegistrada> {
	
	private static final long serialVersionUID = 1L;

	private Long protocoloId;

	private String protocolo;

	public RemessaRegistrada() {
    	// Usado apenas pelo Jackson durante a conversação de Json para uma nova instância.
	}
	
	public RemessaRegistrada(Long protocoloId, String protocolo) {
		this.protocoloId = protocoloId;
		this.protocolo = protocolo;
	}
	
	public Long getProtocoloId() {
		return protocoloId;
	}
	
	public String getProtocolo() {
		return protocolo;
	}

	@Override
	public boolean sameEventAs(RemessaRegistrada other) {
		return getProtocoloId().equals(other.getProtocoloId());
	}
	
}
