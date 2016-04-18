package br.jus.stf.processamentoinicial.recursaledistribuicao.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.workflow.interfaces.TarefaRestResource;
import br.jus.stf.plataforma.workflow.interfaces.commands.CompletarTarefaCommand;
import br.jus.stf.plataforma.workflow.interfaces.dto.TarefaDto;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PeticaoStatus;
import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.TarefaAdapter;
import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model.Peticao;
import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model.Processo;
import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model.ProcessoRecursal;
import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model.ProcessoSituacao;
import br.jus.stf.processamentoinicial.recursaledistribuicao.infra.eventbus.ProcessoSituacaoModificado;
import br.jus.stf.processamentoinicial.suporte.domain.model.Classificacao;
import br.jus.stf.processamentoinicial.suporte.domain.model.TipoProcesso;
import br.jus.stf.shared.PeticaoStatusModificado;
import br.jus.stf.shared.ProcessoWorkflow;
import br.jus.stf.shared.ProcessoWorkflowId;
import reactor.bus.Event;
import reactor.bus.EventBus;

/**
 * @author Rafael Alencar
 * 
 * @since 09.11.2015
 */
@Component("processoTarefaRestAdapter")
public class TarefaRestAdapter implements TarefaAdapter {

	@Autowired
	private TarefaRestResource tarefaRestResource;
	
	@Autowired
	private PeticaoRestAdapter peticaoRestAdapter;
	
	@Autowired
	private EventBus eventBus;
		
	@Override
	public void completarAnalisePressupostosFormais(ProcessoRecursal processo, Classificacao classificacao) {
		completarTarefa(processo, Classificacao.APTO.equals(classificacao) ? ProcessoSituacao.A_ANALISAR : ProcessoSituacao.A_REVISAR);	
	}
	
	@Override
	public void completarRevisaoProcessoInapto(ProcessoRecursal processo, Classificacao classificacao) {
		completarTarefa(processo, Classificacao.APTO.equals(classificacao) ? ProcessoSituacao.A_ANALISAR : ProcessoSituacao.A_AUTUAR);	
	}
	
	@Override
	public void completarAnaliseRepercussaoGeral(ProcessoRecursal processo, boolean repercussaoGeral) {
		completarTarefa(processo, repercussaoGeral ? ProcessoSituacao.A_REVISAR : ProcessoSituacao.A_AUTUAR);	
	}
	
	@Override
	public void completarRevisaoRepercussaoGeral(ProcessoRecursal processo) {
		completarTarefa(processo, ProcessoSituacao.A_AUTUAR);	
	}
	
	@Override
	public void completarAutuacao(ProcessoRecursal processo) {
		completarTarefa(processo, ProcessoSituacao.AUTUADO);	
	}
	
	@Override
	public void completarDistribuicao(Processo processo) {
		completarTarefa(processo, processo.pecas().isEmpty() ? ProcessoSituacao.PECAS_ORGANIZADAS : ProcessoSituacao.DISTRIBUIDO);
	}
	
	@Override
	public void completarOrganizarPecas(Processo processo) {
		completarTarefa(processo, ProcessoSituacao.PECAS_ORGANIZADAS);
	}
	
	/**
	 * Completa uma tarefa do processo com um status
	 * 
	 * @param processo
	 * @param status
	 */
	private void completarTarefa(Processo processo, ProcessoSituacao situacao) {
		CompletarTarefaCommand command = new CompletarTarefaCommand();
		command.setStatus(situacao.toString());
		
		ProcessoWorkflowId processoWorkflowId = null;
		Peticao peticao = null;
		
		if(processo.peticao() != null) {
			peticao = peticaoRestAdapter.consultar(processo.peticao().toLong());
			processoWorkflowId = peticao.processoWorkflowId();
		} else {
			ProcessoWorkflow workflow = ((ProcessoRecursal)processo).processosWorkflow().iterator().next();
			processoWorkflowId = workflow.id();
		}
		executarComando(processoWorkflowId, command);
		
		if (TipoProcesso.RECURSAL.equals(processo.tipoProcesso())) {
			eventBus.notify("indexadorEventBus", Event.wrap(new ProcessoSituacaoModificado(processo.id(), processo.getClass().getSimpleName(), processoWorkflowId, situacao)));
		} else {
			eventBus.notify("indexadorEventBus", Event.wrap(new PeticaoStatusModificado(peticao.id(), peticao.tipo(), processoWorkflowId, PeticaoStatus.DISTRIBUIDA)));
		}
	}
	
	/**
	 * Executa o comando de completar e retorna a petição associada ao processo
	 * 
     * @param processo
     * @param command
     * @return peticao
     */
    private void executarComando(ProcessoWorkflowId workflowId, CompletarTarefaCommand command) {
		TarefaDto dto = tarefaRestResource.consultarPorProcesso(workflowId.toLong());
		tarefaRestResource.completar(dto.getId(), command);
    }

}
