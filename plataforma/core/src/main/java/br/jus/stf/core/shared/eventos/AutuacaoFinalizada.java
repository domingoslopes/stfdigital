package br.jus.stf.core.shared.eventos;

import br.jus.stf.core.framework.domaindrivendesign.DomainEvent;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 26.12.2015
 */
public class AutuacaoFinalizada implements DomainEvent<AutuacaoFinalizada> {
	
	private static final long serialVersionUID = 1L;
	
	private Long processoId;
	
	public AutuacaoFinalizada() {
    	// Usado pelo Jackson durante a conversação de Json para uma nova instância.
	}

    public AutuacaoFinalizada(Long processoId) {
    	this.processoId = processoId;
    }
    
    public Long getProcessoId() {
		return processoId;
	}

	@Override
	public boolean sameEventAs(AutuacaoFinalizada other) {
		return getProcessoId().equals(other.getProcessoId());
	}

}
