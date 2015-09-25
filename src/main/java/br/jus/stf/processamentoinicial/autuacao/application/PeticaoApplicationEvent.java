package br.jus.stf.processamentoinicial.autuacao.application;

import br.jus.stf.processamentoinicial.autuacao.domain.model.Peticao;
import br.jus.stf.processamentoinicial.distribuicao.domain.model.Processo;

/**
 * Interface que define os eventos publicados pela aplicação
 * 
 * @author Lucas Rodrigues
 */
public interface PeticaoApplicationEvent {
	
	public void peticaoRecebida(Peticao peticao);
	
	public void processoDistribuido(Processo processo);
	
}
