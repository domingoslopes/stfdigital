package br.jus.stf.processamentoinicial.recursaledistribuicao.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.workflow.interfaces.WorkflowRestResource;
import br.jus.stf.plataforma.workflow.interfaces.commands.IniciarProcessoCommand;
import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.WorkflowAdapter;
import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model.ProcessoRecursal;
import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model.ProcessoSituacao;
import br.jus.stf.shared.ProcessoWorkflow;
import br.jus.stf.shared.ProcessoWorkflowId;


/**
 * @author Lucas.Rodrigues
 *
 */
@Component("processoWorkflowRestAdapter")
public class WorkflowRestAdapter implements WorkflowAdapter {

	@Autowired
	private WorkflowRestResource workflowRestResource;
	
	@Override
    public ProcessoWorkflow iniciarWorkflow(ProcessoRecursal processoRecursal) {
		IniciarProcessoCommand command = new IniciarProcessoCommand();
		command.setMensagem("enviarProcesso");
		command.setStatus(ProcessoSituacao.A_AUTUAR.toString());
		command.setInformacao(processoRecursal.id().toString());
		command.setTipoInformacao(processoRecursal.getClass().getSimpleName());
		command.setDescricao(processoRecursal.identificacao());
		
		Long id = workflowRestResource.iniciar(command);
		return new ProcessoWorkflow(new ProcessoWorkflowId(id), ProcessoSituacao.A_AUTUAR.name());
    }
	
}
