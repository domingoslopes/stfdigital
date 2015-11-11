package br.jus.stf.processamentoinicial.distribuicao.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.workflow.interfaces.TarefaRestResource;
import br.jus.stf.plataforma.workflow.interfaces.commands.CompletarTarefaCommand;
import br.jus.stf.plataforma.workflow.interfaces.dto.TarefaDto;
import br.jus.stf.processamentoinicial.autuacao.infra.PeticaoStatus;
import br.jus.stf.processamentoinicial.distribuicao.domain.TarefaAdapter;
import br.jus.stf.shared.ProcessoWorkflowId;

/**
 * @author Rafael Alencar
 * 
 * @since 09.11.2015
 */
@Component("processoTarefaRestAdapter")
public class TarefaRestAdapter implements TarefaAdapter {

	@Autowired
	private TarefaRestResource tarefaRestResource;

	@Override
	public void completarDistribuicao(ProcessoWorkflowId processoWorkflowId) {
		completarTarefaPorProcesso(processoWorkflowId, PeticaoStatus.DISTRIBUIDA);	
	}
	
	/**
	 * Completa uma tarefa do processo com um status
	 * 
	 * @param processoWorkflowId
	 */
	private void completarTarefaPorProcesso(ProcessoWorkflowId processoWorkflowId, PeticaoStatus status) {
		CompletarTarefaCommand command = new CompletarTarefaCommand();
		command.setStatus(status.toString());
		TarefaDto dto = tarefaRestResource.consultarPorProcesso(processoWorkflowId.toLong());
		tarefaRestResource.completar(dto.getId(), command);
	}

}
