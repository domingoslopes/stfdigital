package br.jus.stf.autuacao.domain;

import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.domain.model.Peticao;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 26.06.2015
 */
@Component
public interface TarefaAdapter {

	void completarAutuacao(Peticao peticao);
	
	void completarPreautuacao(Peticao peticao);
	
	void completarDistribuicao(Peticao peticao);
	
	void completarDevolucao(Peticao peticao);

}
