package br.jus.stf.processamentoinicial.distribuicao.infra.eventbus;

import br.jus.stf.processamentoinicial.distribuicao.domain.model.Processo;

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
