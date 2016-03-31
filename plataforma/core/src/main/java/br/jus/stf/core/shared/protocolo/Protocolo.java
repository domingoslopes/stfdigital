package br.jus.stf.core.shared.protocolo;

import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import br.jus.stf.core.framework.domaindrivendesign.EntitySupport;

/**
 * @author Rafael Esdras
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 25.02.2016
 */
@Entity
public class Protocolo extends EntitySupport<Protocolo, ProtocoloId> {
	
	@EmbeddedId
	private ProtocoloId protocoloId;
	
	@Embedded
	private Numero numero;
	
	public Protocolo(ProtocoloId protocoloId, Numero numero) {
		this.protocoloId = protocoloId;
		this.numero = numero;
	}

	@Override
	public ProtocoloId identity() {
		return protocoloId;
	}
	
	public Numero numero() {
		return numero;
	}
	
	@Override
	public String toString() {
		return numero().toString();
	}

}
