package br.jus.stf.shared;

import br.jus.stf.processamentoinicial.autuacao.domain.model.PeticaoStatus;

public class PeticaoStatusModificado {

	private PeticaoId peticaoId;
	private ProcessoWorkflowId processoWorkflowId;
	private String tipo;
	private PeticaoStatus status;

	public PeticaoStatusModificado(PeticaoId peticaoId, String tipo, ProcessoWorkflowId processoWorkflowId, PeticaoStatus status) {
		this.peticaoId = peticaoId;
		this.tipo = tipo;
		this.processoWorkflowId = processoWorkflowId;
		this.status = status;
	}

	public PeticaoId peticaoId() {
		return peticaoId;
	}
	
	public String tipo() {
		return tipo;
	}

	public ProcessoWorkflowId processoWorkflowId() {
		return processoWorkflowId;
	}

	public PeticaoStatus status() {
		return status;
	}
	
}
