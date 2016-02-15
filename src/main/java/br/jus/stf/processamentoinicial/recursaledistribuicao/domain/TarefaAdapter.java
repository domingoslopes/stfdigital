package br.jus.stf.processamentoinicial.recursaledistribuicao.domain;

import org.springframework.stereotype.Component;

import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model.Processo;
import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model.ProcessoRecursal;
import br.jus.stf.processamentoinicial.suporte.domain.model.Classificacao;

@Component
public interface TarefaAdapter {

	void completarAnalisePressupostosFormais(ProcessoRecursal processo, Classificacao classificacao);

	void completarRevisaoProcessoInapto(ProcessoRecursal processo, Classificacao classificacao);

	void completarAnaliseRepercussaoGeral(ProcessoRecursal processo, boolean repercussaoGeral);

	void completarRevisaoRepercussaoGeral(ProcessoRecursal processo);

	void completarAutuacao(ProcessoRecursal processo);

	void completarDistribuicao(Processo processo);
	
}
