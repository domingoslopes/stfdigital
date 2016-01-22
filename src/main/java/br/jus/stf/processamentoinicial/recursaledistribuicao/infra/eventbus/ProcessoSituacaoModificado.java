package br.jus.stf.processamentoinicial.recursaledistribuicao.infra.eventbus;

import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model.ProcessoSituacao;
import br.jus.stf.shared.ProcessoId;
import br.jus.stf.shared.ProcessoWorkflowId;


/**
 * @author Lucas.Rodrigues
 *
 */
public class ProcessoSituacaoModificado {
	
	private ProcessoId processoId;
	private ProcessoWorkflowId processoWorkflowId;
	private String tipo;
	private ProcessoSituacao situacao;

	public ProcessoSituacaoModificado(ProcessoId processoId, String tipo, ProcessoWorkflowId processoWorkflowId, ProcessoSituacao situacao) {
		this.processoId = processoId;
		this.tipo = tipo;
		this.processoWorkflowId = processoWorkflowId;
		this.situacao = situacao;
	}

	public ProcessoId processoId() {
		return processoId;
	}
	
	public String tipo() {
		return tipo;
	}

	public ProcessoWorkflowId processoWorkflowId() {
		return processoWorkflowId;
	}

	public ProcessoSituacao situacao() {
		return situacao;
	}
}
