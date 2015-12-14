package br.jus.stf.processamentoinicial.distribuicao.domain;

import org.springframework.stereotype.Component;

import br.jus.stf.processamentoinicial.distribuicao.domain.model.Peticao;

@Component
public interface TarefaAdapter {

	void completarDistribuicao(Peticao peticao);
	
}
