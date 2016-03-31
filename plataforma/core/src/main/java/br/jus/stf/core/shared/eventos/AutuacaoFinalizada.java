package br.jus.stf.core.shared.eventos;

import br.jus.stf.core.framework.domaindrivendesign.DomainEvent;
import br.jus.stf.core.shared.processo.ProcessoId;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 26.12.2015
 */
public class AutuacaoFinalizada implements DomainEvent<AutuacaoFinalizada> {
	
	private static final long serialVersionUID = 1L;
	
	private ProcessoId processoId;
	
	public AutuacaoFinalizada() {
    	// Usado pelo Jackson durante a conversação de Json para uma nova instância.
	}

    public AutuacaoFinalizada(ProcessoId processoId) {
    	this.processoId = processoId;
    }
    
    public ProcessoId getProcessoId() {
		return processoId;
	}

	@Override
	public boolean sameEventAs(AutuacaoFinalizada other) {
		return getProcessoId().equals(other.getProcessoId());
	}

}
