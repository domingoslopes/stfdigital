package br.jus.stf.processamentoinicial.recursaledistribuicao.application;

import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model.Processo;

/**
 * Interface que define os eventos publicados pela aplicação
 * 
 * @author Lucas Rodrigues
 */
public interface ProcessoApplicationEvent {
	
	public void processoDistribuido(Processo processo);
	
}
