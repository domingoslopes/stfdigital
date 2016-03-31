package br.jus.stf.core.shared.eventos;

import org.apache.commons.lang.builder.EqualsBuilder;

import br.jus.stf.core.framework.domaindrivendesign.DomainEvent;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 27.02.2016
 */
public class ProcessoAutuado implements DomainEvent<ProcessoAutuado> {
	
	private static final long serialVersionUID = 1L;
	
	private String processoId;
	
	private String numero;
	
	public ProcessoAutuado() {
    	// Usado apenas pelo Jackson durante a conversação de Json para uma nova instância.
	}

	public ProcessoAutuado(String processoId, String numero) {
		this.processoId = processoId;
		this.numero = numero;
	}
	
	public String getProcessoId() {
		return processoId;
	}
	
	public String getNumero() {
		return numero;
	}

	@Override
	public boolean sameEventAs(ProcessoAutuado other) {
		return new EqualsBuilder().append(getProcessoId(), other.getProcessoId()).append(getNumero(), other.getNumero()).isEquals();
	}
	
}
