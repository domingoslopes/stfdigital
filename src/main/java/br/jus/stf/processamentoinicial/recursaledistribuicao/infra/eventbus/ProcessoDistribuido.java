package br.jus.stf.processamentoinicial.recursaledistribuicao.infra.eventbus;

import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model.Processo;

/**
 * @author Lucas.Rodrigues
 *
 */
public class ProcessoDistribuido {

	private Processo processo;
	
	public ProcessoDistribuido(Processo processo) {
		this.processo = processo;
	}
	
	public Processo processo() {
		return this.processo;
	}
	
}
