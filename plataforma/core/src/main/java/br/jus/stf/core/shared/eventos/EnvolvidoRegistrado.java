package br.jus.stf.core.shared.eventos;

import org.apache.commons.lang.builder.EqualsBuilder;

import br.jus.stf.core.framework.domaindrivendesign.DomainEvent;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 29.02.2016
 */
public class EnvolvidoRegistrado implements DomainEvent<EnvolvidoRegistrado> {
	
	private static final long serialVersionUID = 1L;
	
	private Long protocoloId;
	
	private String protocolo;
	
	private String nome;

	public EnvolvidoRegistrado() {
    	// Usado apenas pelo Jackson durante a conversação de Json para uma nova instância.
	}
	
	public EnvolvidoRegistrado(Long protocoloId, String protocolo, String nome) {
		this.protocoloId = protocoloId;
		this.protocolo = protocolo;
		this.nome = nome;
	}
	
	public Long getProtocoloId() {
		return protocoloId;
	}
	
	public String getProtocolo() {
		return protocolo;
	}
	
	public String getNome() {
		return nome;
	}

	@Override
	public boolean sameEventAs(EnvolvidoRegistrado other) {
		return new EqualsBuilder().append(getProtocoloId(), other.getProtocoloId()).append(getNome(), other.getNome()).isEquals();
	}
	
}
