package br.jus.stf.core.shared.eventos;

import org.apache.commons.lang.builder.EqualsBuilder;

import br.jus.stf.core.framework.domaindrivendesign.DomainEvent;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 27.02.2016
 */
public class ProcessoRegistrado implements DomainEvent<ProcessoRegistrado> {
	
	private static final long serialVersionUID = 1L;

	private Long protocoloId;
	
	private String processoId;
	
	public ProcessoRegistrado() {
    	// Usado apenas pelo Jackson durante a conversação de Json para uma nova instância.
	}

	public ProcessoRegistrado(Long protocoloId, String processoId) {
		this.protocoloId = protocoloId;
		this.processoId = processoId;
	}

	public Long getProtocoloId() {
		return protocoloId;
	}

	public String getProcessoId() {
		return processoId;
	}

	@Override
	public boolean sameEventAs(ProcessoRegistrado other) {
		return new EqualsBuilder().append(getProtocoloId(), other.getProtocoloId()).append(getProcessoId(), other.getProcessoId()).isEquals();
	}

}
