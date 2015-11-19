package br.jus.stf.processamentoinicial.distribuicao.application;

import br.jus.stf.processamentoinicial.distribuicao.domain.model.Processo;

/**
 * Interface que define os eventos publicados pela aplicação
 * 
 * @author Lucas Rodrigues
 */
public interface ProcessoApplicationEvent {
	
	public void processoDistribuido(Processo processo);
	
}
