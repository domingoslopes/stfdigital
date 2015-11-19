package br.jus.stf.processamentoinicial.distribuicao.domain;

import org.springframework.stereotype.Component;

import br.jus.stf.shared.ProcessoWorkflowId;

@Component
public interface TarefaAdapter {

	void completarDistribuicao(ProcessoWorkflowId processoWorkflowId);
	
}
