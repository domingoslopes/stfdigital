package br.jus.stf.processamentoinicial.autuacao.infra.eventbus;

import br.jus.stf.processamentoinicial.autuacao.domain.model.Peticao;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PeticaoStatus;
import br.jus.stf.shared.PeticaoId;
import br.jus.stf.shared.ProcessoWorkflowId;

public class PeticaoStatusModificado {

	private Class<? extends Peticao> peticaoClass;
	private PeticaoId peticaoId;
	private ProcessoWorkflowId processoWorkflowId;
	private PeticaoStatus status;

	public PeticaoStatusModificado(Peticao peticao, ProcessoWorkflowId processoWorkflowId, PeticaoStatus status) {
		this.peticaoId = peticao.id();
		this.peticaoClass = peticao.getClass();
		this.processoWorkflowId = processoWorkflowId;
		this.status = status;
	}

	public PeticaoId peticaoId() {
		return peticaoId;
	}

	public Class<? extends Peticao> peticaoClass() {
		return peticaoClass;
	}

	public ProcessoWorkflowId processoWorkflowId() {
		return processoWorkflowId;
	}

	public PeticaoStatus status() {
		return status;
	}
	
}
